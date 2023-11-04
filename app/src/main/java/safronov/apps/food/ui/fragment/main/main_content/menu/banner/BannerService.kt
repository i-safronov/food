package safronov.apps.food.ui.fragment.main.main_content.menu.banner

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import safronov.apps.food.R

interface BannerService {

    fun getBanners(): List<Banner>

    class Base(
        private val context: Context
    ): BannerService {

        override fun getBanners(): List<Banner> {
            return listOf(
                Banner(img = AppCompatResources.getDrawable(context, R.drawable.banner1)),
                Banner(img = AppCompatResources.getDrawable(context, R.drawable.banner2)),
                Banner(img = AppCompatResources.getDrawable(context, R.drawable.banner3)),
                Banner(img = AppCompatResources.getDrawable(context, R.drawable.banner1)),
                Banner(img = AppCompatResources.getDrawable(context, R.drawable.banner2)),
                Banner(img = AppCompatResources.getDrawable(context, R.drawable.banner3))
            )
        }

    }

}