package safronov.apps.food.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import safronov.apps.food.ui.exception.UiException
import java.lang.RuntimeException

abstract class FragmentBase: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = createUi(inflater, container, savedInstanceState)
        setup()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            uiCreated(view, savedInstanceState)
        } catch (e: RuntimeException) {
            Log.e(TAG, "onViewCreated, $e")
            handleException(e as UiException)
        }
    }

    override fun onDestroyView() {
        try {
            removeUi()
        } catch (e: RuntimeException) {
            Log.e(TAG, "onDestroyView, $e")
            handleException(e as UiException)
        }
        super.onDestroyView()
    }


    abstract fun createUi(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    abstract fun removeUi()
    open fun handleException(uiException: UiException) {  }
    open fun setup() {  }
    open fun uiCreated(view: View, savedInstanceState: Bundle?) {  }

    companion object {
        const val TAG = "sfrLog"
    }

}