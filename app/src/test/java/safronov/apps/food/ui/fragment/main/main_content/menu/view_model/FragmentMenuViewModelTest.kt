package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import org.junit.Assert.*

/*
* actions:
* - get data from internet when network is available
* - get data when network is unavailable(should get data from database)
* - get data from internet when network is available but when calling request, network has been unavailable(should cancel job to get data from
* internet and get data from database)
* - get data from database(when network is unavailable) but when calling request, should get data from database 
* */

class FragmentMenuViewModelTest