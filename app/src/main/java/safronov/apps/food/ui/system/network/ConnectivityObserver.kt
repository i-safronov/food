package safronov.apps.food.ui.system.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>
    fun isAccessToNetwork(): Boolean

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

}