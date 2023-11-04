package safronov.apps.food.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import safronov.apps.food.R
import safronov.apps.food.databinding.FragmentMainBinding
import safronov.apps.food.ui.base.FragmentBase

class FragmentMain : FragmentBase() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun createUi(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setup() {
        super.setup()
        val navHost = childFragmentManager.findFragmentById(R.id.main_content_graph_container) as NavHostFragment?
        navHost?.navController?.let {
            binding.mainContentBottomNav.setupWithNavController(it)
        }
    }

    override fun removeUi() {
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMain()
    }

}