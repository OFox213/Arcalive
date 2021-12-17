package com.devstudio.arcalive.ui.fragmet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.devstudio.arcalive.R
import java.net.URISyntaxException

class ArcaViewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_arca_view, container, false)

        val webView = v.findViewById<WebView>(R.id.arcaWebView)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if(webView.url.equals("https://arca.live/channels")){
                    webView.loadUrl("https://arca.live")
                }

                if(newProgress >= 100) {
                    webView.visibility = View.VISIBLE
                    v.findViewById<LinearLayout>(R.id.loadingLayout).visibility = View.GONE
                }
            }

            override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)

            }
        }
        webView.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                webView.loadUrl(url)
                return false
            }
        }
        val webSettings:WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportMultipleWindows(true)
        webSettings.loadWithOverviewMode = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.domStorageEnabled = true
        webView.loadUrl("https://arca.live")


        return v
    }
}