package safronov.apps.food.ui.system.network

interface ConnectivityObserver {

    fun isAccessToNetwork(): Boolean

}