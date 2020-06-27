package kg.turar.arykbaev.canteenmenu


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kg.turar.arykbaev.canteenmenu.adapter.ViewPagerAdapter
import kg.turar.arykbaev.canteenmenu.connection.NetworkConnection
import kg.turar.arykbaev.canteenmenu.date.CurrentDate
import kg.turar.arykbaev.canteenmenu.parsers.MenuSource
import kg.turar.arykbaev.canteenmenu.types.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var networkConnection: NetworkConnection
    private lateinit var parent: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initNetworkConnection()
        networkConnectionMessage()
        initViewPager()
        initMenu()

        swipe_refresh.setOnRefreshListener { initMenu() }
    }

    private fun initNetworkConnection() {
        networkConnection = NetworkConnection(applicationContext)
        parent = findViewById(R.id.main_parent)
    }

    private fun networkConnectionMessage() {
        var first = false
        networkConnection.observe(this, Observer {
            if (!it) {
                lostSnackbar()
                first = true
            } else if (first) {
                availableSnackbar()
                initMenu()
                first = false
            }
        })
    }

    private fun availableSnackbar() {
        progress_bar.visibility = ProgressBar.INVISIBLE
        val snackbar = Snackbar.make(parent, getString(R.string.available), Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.colorConnection))
        centerTextSnackbar(snackbar)
        snackbar.show()
    }

    private fun lostSnackbar() {
        progress_bar.visibility = ProgressBar.INVISIBLE
        val snackbar = Snackbar.make(parent, getString(R.string.lost), Snackbar.LENGTH_INDEFINITE)
        centerTextSnackbar(snackbar)
        snackbar.show()
    }

    private fun centerTextSnackbar(snackbar: Snackbar) {
        val view = snackbar.view
        val textView =
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        } else {
            textView.gravity = Gravity.CENTER
        }
    }

    private fun initViewPager() {
        viewPagerAdapter = ViewPagerAdapter()
        view_pager.adapter = viewPagerAdapter
    }

    private fun initMenu() {
        MenuSource.getItemMenu()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progress_bar.visibility = ProgressBar.VISIBLE
                val date = CurrentDate.getCurrentDate()
                val left: Int
                val right: Int
                val index = currentDay(date, it)
                var item = 1
                if (index != null) {
                    when (index) {
                        1 -> {
                            left = 1; right = 3
                            item = 0
                        }
                        it.size - 1 -> {
                            left = index - 1; right = index + 1
                        }
                        else -> {
                            left = index - 1; right = index + 2
                        }
                    }

                    setData(it.subList(left, right), item)
                } else {
                    toDayNotWorkDialog()
                }
                swipe_refresh.isRefreshing = false
                progress_bar.visibility = ProgressBar.INVISIBLE
            }, {
                swipe_refresh.isRefreshing = false
            })
    }

    private fun setData(list: List<Menu>, item: Int) {
        viewPagerAdapter.submitItemList(list)
        viewPagerAdapter.notifyDataSetChanged()
        view_pager.setCurrentItem(item, false)
    }

    private fun currentDay(date: String, list: List<Menu>): Int? {
        for ((i, v) in list.withIndex()) {
            if (v.equals(date)) return i
        }
        return null
    }

    private fun toDayNotWorkDialog() {
        MaterialDialog(this).show {
            customView(R.layout.layout_dialog)
        }
    }
}