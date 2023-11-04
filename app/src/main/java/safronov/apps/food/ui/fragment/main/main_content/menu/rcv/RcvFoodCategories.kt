package safronov.apps.food.ui.fragment.main.main_content.menu.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.food.R
import safronov.apps.food.databinding.RcvCategoryItemBinding

interface RcvFoodCategoriesInt {
    fun onFoodCategoryClick(foodCategoryItem: FoodCategoryItem)
}

class RcvFoodCategories(
    private val rcvFoodCategoriesInt: RcvFoodCategoriesInt
): ListAdapter<FoodCategoryItem, RcvFoodCategories.FoodCategoriesViewHolder>(FoodCategoriesDiffUtil()) {

    private var selectedFoodCategoryItem: FoodCategoryItem? = null

    inner class FoodCategoriesViewHolder(
        private val binding: RcvCategoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: FoodCategoryItem) {
            if (selectedFoodCategoryItem?.idCategory == item.idCategory) {
                binding.root.background = AppCompatResources.getDrawable(binding.root.context, R.drawable.accent_back_with_ripple)
                binding.tvTitle.setTextColor(binding.root.context.getColor(R.color.accent_color))
            } else {
                binding.root.background = AppCompatResources.getDrawable(binding.root.context,R.drawable.no_accent_back_with_ripple)
                binding.tvTitle.setTextColor(binding.root.context.getColor(R.color.hint))
            }
            binding.tvTitle.text = item.strCategory

            itemView.setOnClickListener {
                rcvFoodCategoriesInt.onFoodCategoryClick(item)
            }
        }
    }

    class FoodCategoriesDiffUtil: DiffUtil.ItemCallback<FoodCategoryItem>() {
        override fun areItemsTheSame(
            oldItem: FoodCategoryItem,
            newItem: FoodCategoryItem
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: FoodCategoryItem,
            newItem: FoodCategoryItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RcvCategoryItemBinding.inflate(inflater, parent, false)
        return FoodCategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodCategoriesViewHolder, position: Int) {
        holder.bindView(currentList[holder.adapterPosition])
    }

    fun saveCurrentFoodCategory(foodCategoryItem: FoodCategoryItem) {
        selectedFoodCategoryItem = foodCategoryItem
        notifyDataSetChanged()
    }

}