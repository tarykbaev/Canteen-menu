package kg.turar.arykbaev.canteenmenu.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kg.turar.arykbaev.canteenmenu.R
import kg.turar.arykbaev.canteenmenu.connection.NetworkConnection
import kotlinx.android.synthetic.main.activity_food.*
import java.util.Observer

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.extras?.get("url").toString()
        val food = intent.extras?.get("food").toString()

        this.title = food

        initWebView()
        loadUrl(url)

        web_view_refresh.setOnRefreshListener { web_view.reload() }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        web_view.settings.javaScriptEnabled = true
        web_view.settings.loadWithOverviewMode = true
        web_view.settings.useWideViewPort = true
        web_view.settings.domStorageEnabled = true

        web_view.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                web_view_refresh.isRefreshing = false
            }
        }
    }

    private fun loadUrl(url: String) {
        web_view.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}