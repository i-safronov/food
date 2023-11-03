package safronov.apps.food.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import safronov.apps.food.ui.base.coroutines.DispatchersList
import java.lang.RuntimeException

interface ViewModelBase {

    fun <T: Any> asyncWork(
        prepareUI: () -> Unit,
        doWork: suspend () -> T,
        showUI: (data: T) -> Unit,
        handleException: (RuntimeException) -> Unit
    )

    class Base(
        private val dispatchersList: DispatchersList
    ): ViewModel(), ViewModelBase {
        override fun <T: Any> asyncWork(
            prepareUI: () -> Unit,
            doWork: suspend () -> T,
            showUI: (data: T) -> Unit,
            handleException: (RuntimeException) -> Unit
        ) {
            try {
                viewModelScope.launch {
                    prepareUI.invoke()
                    withContext(dispatchersList.io()) {
                        val result = doWork.invoke()
                        withContext(dispatchersList.ui()) {
                            showUI.invoke(result)
                        }
                    }
                }
            } catch (e: RuntimeException) {
                handleException.invoke(e)
            }
        }
    }

}

