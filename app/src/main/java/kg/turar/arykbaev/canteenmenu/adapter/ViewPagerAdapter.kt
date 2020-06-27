package kg.turar.arykbaev.canteenmenu.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kg.turar.arykbaev.canteenmenu.R
import kg.turar.arykbaev.canteenmenu.activities.FoodActivity
import kg.turar.arykbaev.canteenmenu.date.CurrentDate
import kg.turar.arykbaev.canteenmenu.parsers.ImageSource
import kg.turar.arykbaev.canteenmenu.types.Menu
import kotlinx.android.synthetic.main.layout_menu_item.view.*
import kotlin.collections.ArrayList

class ViewPagerAdapter() :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerAdapterHolder>() {
    private var itemList: List<Menu> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapterHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_menu_item, parent, false)
        return ViewPagerAdapterHolder(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapterHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun submitItemList(list: List<Menu>) {
        itemList = list
    }


    class ViewPagerAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image1: ImageView = itemView.food_image1
        private val image2: ImageView = itemView.food_image2
        private val image3: ImageView = itemView.food_image3
        private val image4: ImageView = itemView.food_image4
        private val name1: TextView = itemView.food_name1
        private val name2: TextView = itemView.food_name2
        private val name3: TextView = itemView.food_name3
        private val name4: TextView = itemView.food_name4
        private val calorie1: TextView = itemView.food_calorie1
        private val calorie2: TextView = itemView.food_calorie2
        private val calorie3: TextView = itemView.food_calorie3
        private val calorie4: TextView = itemView.food_calorie4
        private val card1: CardView = itemView.cardView4 as CardView
        private val card2: CardView = itemView.cardView3 as CardView
        private val card3: CardView = itemView.cardView2 as CardView
        private val card4: CardView = itemView.cardView as CardView
        private val date: TextView = itemView.toolbar_text

        private val context = itemView.context


        fun bind(menu: Menu) {
            name1.text = menu.name[0]; name2.text = menu.name[1]
            name3.text = menu.name[2]; name4.text = menu.name[3]
            calorie1.text = menu.calorie[0]; calorie2.text = menu.calorie[1]
            calorie3.text = menu.calorie[2]; calorie4.text = menu.calorie[3]


            card1.setOnClickListener { startFoodActivity(menu.url[0], menu.name[0]) }
            card2.setOnClickListener { startFoodActivity(menu.url[1], menu.name[1]) }
            card4.setOnClickListener { startFoodActivity(menu.url[2], menu.name[2]) }
            card3.setOnClickListener { startFoodActivity(menu.url[3], menu.name[3]) }

            date.text = CurrentDate.getDate(context, menu.date)

            imageLoad(menu.url[0], image1)
            imageLoad(menu.url[1], image2)
            imageLoad(menu.url[2], image3)
            imageLoad(menu.url[3], image4)
        }

        private fun imageLoad(url: String, imageView: ImageView) {
            ImageSource.getImageUrl(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Picasso.get().load(it).into(imageView)
                }, {
                    Log.e("ViewPagerAdapterHolder", "bind: ${it.localizedMessage}")
                })
        }

        private fun startFoodActivity(url: String, food: String) {
            context.startActivity(
                Intent(context, FoodActivity::class.java)
                    .putExtra("url", url).putExtra("food", food),
                ActivityOptions.makeSceneTransitionAnimation(context as Activity?).toBundle()
            )
        }
    }
}
