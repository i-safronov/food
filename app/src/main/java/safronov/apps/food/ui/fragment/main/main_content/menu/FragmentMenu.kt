package safronov.apps.food.ui.fragment.main.main_content.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import safronov.apps.food.databinding.FragmentMenuBinding
import safronov.apps.food.ui.base.FragmentBase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.fragment.main.main_content.menu.rcv.RcvFoodCategories
import safronov.apps.food.ui.fragment.main.main_content.menu.rcv.RcvFoodCategoriesInt
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModel
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModelFactory
import javax.inject.Inject

//TODO handle exception - go to fragment exception
//TODO show selected category from view model

class FragmentMenu : FragmentBase(), RcvFoodCategoriesInt {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val rcvFoodCategories = RcvFoodCategories(this)

    @Inject
    lateinit var fragmentMenuViewModelFactory: FragmentMenuViewModelFactory
    private var fragmentMenuViewModel: FragmentMenuViewModel? = null

    @Inject
    lateinit var dispatchersList: DispatchersList

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
        fragmentMenuViewModel?.loadFoodsCategoriesAndFoods()
        binding.rcvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvCategories.adapter = rcvFoodCategories
    }

    override fun uiCreated(view: View, savedInstanceState: Bundle?) {
        super.uiCreated(view, savedInstanceState)
        observeFoodCategories()
        observeCurrentFoodCategory()
//        observeFoods()
//        observeCurrentFoodCategory()
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