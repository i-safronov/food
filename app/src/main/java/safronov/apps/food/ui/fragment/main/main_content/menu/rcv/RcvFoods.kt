package safronov.apps.food.ui.fragment.main.main_content.menu.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.food.R
import safronov.apps.food.databinding.RcvMenuItemBinding

class RcvFoods: RecyclerView.Adapter<RcvFoods.FoodsViewHolder>() {

    private var currentList = listOf<FoodItem>()

    class FoodsViewHolder(
        private val binding: RcvMenuItemBinding
    ): ViewHolder(binding.root) {
        fun bindView(item: FoodItem) {
            Glide.with(binding.root).load(item.strMealThumb).into(binding.foodImg)
            binding.tvTitle.text = item.strMeal
            binding.tvDescription.text = binding.root.context.getString(R.string.lorem_ipsum)
            binding.btnPrice.text = binding.root.context.getString(R.string.default_price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RcvMenuItemBinding.inflate(inflater, parent, false)
        return FoodsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.bindView(currentList[holder.adapterPosition])
    }

    fun submitList(list: List<FoodItem>) {
        currentList = list
        notifyDataSetChanged()
    }

}