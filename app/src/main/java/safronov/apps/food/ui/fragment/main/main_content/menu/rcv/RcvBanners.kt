package safronov.apps.food.ui.fragment.main.main_content.menu.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import safronov.apps.food.ui.fragment.main.main_content.menu.banner.Banner
import safronov.apps.food.databinding.RcvBannerItemBinding

class RcvBanners: RecyclerView.Adapter<RcvBanners.BannerViewHolder>() {

    private var banners = listOf<Banner>()

    class BannerViewHolder(
        private val binding: RcvBannerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindView(banner: Banner) {
            binding.barnnerImg.setImageDrawable(banner.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RcvBannerItemBinding.inflate(inflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return banners.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bindView(banners[holder.adapterPosition])
    }

    fun submitList(list: List<Banner>) {
        banners = list
        notifyDataSetChanged()
    }

}