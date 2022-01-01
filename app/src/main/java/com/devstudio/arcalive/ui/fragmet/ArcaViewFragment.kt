package com.devstudio.arcalive.ui.fragmet

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import androidx.webkit.WebViewFeature
import com.devstudio.arcalive.R
import com.devstudio.arcalive.ui.MainActivity
import java.net.URISyntaxException
import android.widget.Toast


import android.content.pm.PackageManager
import androidx.browser.customtabs.CustomTabsIntent
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.concurrent.thread


class ArcaViewFragment : Fragment() {

    private lateinit var callback : OnBackPressedCallback
    private lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container?.layoutTransition?.setAnimateParentHierarchy(false)
        val v = inflater.inflate(R.layout.fragment_arca_view, container, false)

        webView = v.findViewById(R.id.arcaWebView)


        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request!!.url.toString()
                Log.d("test","L0O Loaded $url")
                if (url.startsWith("sms:")) {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    startActivity(intent)
                } else if (url.startsWith("mailto:")) {
                    var intent: Intent? = null
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }
                    view!!.context.startActivity(intent)
                } else if (url.startsWith("intent:")) {
                    try {
                        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        val existPackage = intent.getPackage()
                            ?.let { context?.packageManager?.getLaunchIntentForPackage(it) }
                        if (existPackage != null) {
                            startActivity(intent)
                        } else {
                            val marketIntent = Intent(Intent.ACTION_VIEW)
                            marketIntent.data = Uri.parse("market://details?id=" + intent.getPackage())
                            startActivity(marketIntent)
                        }
                        return true
                    } catch (e: Exception) {
                        Log.d("test", "intent part Error")
                        e.printStackTrace()
                    }
                }  else if (url.startsWith("market://")) {
                    try {
                        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        if (intent != null) {
                            startActivity(intent)
                        }
                        return true;
                    } catch (e:URISyntaxException) {
                        e.printStackTrace()
                    }
                }  else {
                    webView.loadUrl(url)
                }
                return true
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if(webView.url.equals("https://arca.live/channels")){ //채널 검색 액티비티 보여주기
                    webView.loadUrl("https://arca.live")
                }

                if(newProgress >= 100) {
                    webView.visibility = View.VISIBLE
                    v.findViewById<LinearLayout>(R.id.loadingLayout).visibility = View.GONE
                }
            }

            override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
                webView.requestFocusNodeHref(resultMsg)
                val url = resultMsg?.data?.getString("url")
                Log.d("test","L0O onCreateWindow url $url")
                if(url != null && url.contains("arca.live")) {
                    webView.loadUrl(url)
                } else {
                    val builder = CustomTabsIntent.Builder().apply {
                        setShowTitle(true)
                    }
                    val tabIntent = builder.build()
                    tabIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    tabIntent.launchUrl(activity!!.applicationContext, Uri.parse(url))
                }

                return true
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
        if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)){
            WebSettingsCompat.setForceDark(webSettings, FORCE_DARK_ON)
        }
        webView.loadUrl("https://arca.live")

        //initializeCookieManager()

        return v
    }

    fun initializeCookieManager(){
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        Log.d("test", "L0O Cookie ${cookieManager.getCookie("arca.live")}")
        cookieManager.setCookie(".arca.live", "display-config={%22font.family%22:%22%22%2C%22font.size%22:%2215px%22%2C%22theme.background%22:%22dark%22%2C%22theme.dark%22:%22%22%2C%22theme.width%22:%221300%22%2C%22theme.navbar%22:%22scroll%22%2C%22sidebar.content%22:%22%22%2C%22notification.muteMedia%22:%22show%22%2C%22notification.badge%22:%22default%22}")
        val cookies = cookieManager.getCookie("arca.live")
        if(cookies!=null){
            val tempCookie = cookies.split(";")
            for(i in tempCookie.indices){
                if(tempCookie[i].contains("display-config")){
                    val selectedCookie = tempCookie[i]
                    val editTarget = selectedCookie.substring(selectedCookie.indexOf("=") + 1, selectedCookie.length)
                    val decodedCookie = URLDecoder.decode(editTarget, "UTF-8")
                    val jsonEditor = JSONObject(decodedCookie)
                    //cookieManager.setCookie(".arca.live", "display-config=${jsonEditor.toString().replace("\"", "%22").replace(",", "%2C")}")
                    Log.d("test", "L0O CookieFin ${cookieManager.getCookie("arca.live")}")
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(checkBackOrExit()){
                    (activity as MainActivity).exitAlertDialog()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    fun checkBackOrExit() : Boolean{
        return if(webView.canGoBack()) {
            webView.goBack()
            false
        } else {
            true
        }
    }


}