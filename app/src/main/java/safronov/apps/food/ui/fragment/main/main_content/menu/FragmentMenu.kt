package safronov.apps.food.ui.fragment.main.main_content.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.food.R
import safronov.apps.food.app.App
import safronov.apps.food.databinding.BottomSheetDialogItemBinding
import safronov.apps.food.databinding.FragmentMenuBinding
import safronov.apps.food.system_settings.ui.bottom_sheet.BottomSheet
import safronov.apps.food.ui.base.FragmentBase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.fragment.main.main_content.menu.rcv.RcvBanners
import safronov.apps.food.ui.fragment.main.main_content.menu.rcv.RcvFoodCategories
import safronov.apps.food.ui.fragment.main.main_content.menu.rcv.RcvFoodCategoriesInt
import safronov.apps.food.ui.fragment.main.main_content.menu.rcv.RcvFoods
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModel
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModelFactory
import javax.inject.Inject

class FragmentMenu : FragmentBase(), RcvFoodCategoriesInt {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val rcvFoodCategories = RcvFoodCategories(this)
    private val rcvBanners = RcvBanners()
    private val rcvFoods = RcvFoods()

    @Inject
    lateinit var fragmentMenuViewModelFactory: FragmentMenuViewModelFactory
    private var fragmentMenuViewModel: FragmentMenuViewModel? = null

    @Inject
    lateinit var dispatchersList: DispatchersList

    @Inject
    lateinit var bottomSheet: BottomSheet

    override fun createUi(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setup() {
        super.setup()
        (requireActivity().applicationContext as App).getAppComponent()?.inject(this)
        fragmentMenuViewModel = ViewModelProvider(this, fragmentMenuViewModelFactory)
            .get(FragmentMenuViewModel::class.java)
        fragmentMenuViewModel?.loadPage()
        setupRcv()
    }

    private fun setupRcv() {
        binding.rcvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvCategories.adapter = rcvFoodCategories
        binding.rcvBannerItems.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvBannerItems.adapter = rcvBanners
        binding.rcvMenuItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvMenuItems.adapter = rcvFoods
    }

    override fun uiCreated(view: View, savedInstanceState: Bundle?) {
        super.uiCreated(view, savedInstanceState)
        observeFoodCategories()
        observeCurrentFoodCategory()
        observeBanners()
        observeFoods()
        observeException()
    }

    private fun observeFoodCategories() = viewLifecycleOwner.lifecycleScope.launch(dispatchersList.ui()) {
        fragmentMenuViewModel?.getFoodCategories()?.collect {
            rcvFoodCategories.submitList(it)
        }
    }

    private fun observeCurrentFoodCategory() = viewLifecycleOwner.lifecycleScope.launch(dispatchersList.ui()) {
        fragmentMenuViewModel?.getCurrentFoodCategory()?.collect {
            it?.let {
                rcvFoodCategories.saveCurrentFoodCategory(it)
                fragmentMenuViewModel?.loadFoodsByFoodCategory(it)
            }
        }
    }

    private fun observeBanners() = viewLifecycleOwner.lifecycleScope.launch(dispatchersList.ui()) {
        fragmentMenuViewModel?.getBanners()?.collect {
            rcvBanners.submitList(it)
        }
    }

    private fun observeFoods() = viewLifecycleOwner.lifecycleScope.launch(dispatchersList.ui()) {
        fragmentMenuViewModel?.getFoods()?.collect {
            rcvFoods.submitList(it ?: emptyList())
        }
    }

    private fun observeException() = viewLifecycleOwner.lifecycleScope.launch(dispatchersList.ui()) {
        fragmentMenuViewModel?.getIsException()?.collect {
            if (it != null) {
                val bottomView = BottomSheetDialogItemBinding.inflate(layoutInflater)
                bottomView.tvTitle.text = getString(R.string.error)
                bottomView.tvDescription.text = getString(R.string.error_description)
                bottomView.btnOk.setOnClickListener {
                    bottomSheet.dismissBottomSheet()
                }
                bottomSheet.showBottomSheet(requireActivity(), bottomView.root)
            }
        }
    }

    override fun onFoodCategoryClick(foodCategoryItem: FoodCategoryItem) {
        fragmentMenuViewModel?.saveCurrentFoodCategory(foodCategoryItem)
    }

    override fun removeUi() {
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMenu()
    }

}