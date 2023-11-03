package safronov.apps.food.ui.base.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersList {

    fun ui(): CoroutineDispatcher
    fun io(): CoroutineDispatcher

    class Base: DispatchersList {
        override fun ui(): CoroutineDispatcher {
            return Dispatchers.Main
        }

        override fun io(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }

}