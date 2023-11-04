package safronov.apps.food.ui.fragment.main.main_content.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import safronov.apps.food.R
import safronov.apps.food.app.App
import safronov.apps.food.databinding.FragmentMenuBinding
import safronov.apps.food.ui.base.FragmentBase
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModel
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModelFactory
import javax.inject.Inject

//TODO handle exception - go to fragment exception

class FragmentMenu : FragmentBase() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fragmentMenuViewModelFactory: FragmentMenuViewModelFactory
    private var fragmentMenuViewModel: FragmentMenuViewModel? = null

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
    }

    override fun uiCreated(view: View, savedInstanceState: Bundle?) {
        super.uiCreated(view, savedInstanceState)
//        observeFoodCategories()
//        observeFoods()
//        observeCurrentFoodCategory()
    }

    private fun observeFoodCategories() {
//        binding.rcvCategories
    }

    override fun removeUi() {
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMenu()
    }

}