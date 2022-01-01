package com.devstudio.arcalive.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.devstudio.arcalive.R
import com.devstudio.arcalive.ui.fragmet.ArcaViewFragment
import com.devstudio.arcalive.ui.fragmet.MainFragmentStateAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager2 = findViewById<ViewPager2>(R.id.mainViewpager)
        viewPager2.adapter = MainFragmentStateAdapter(this)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 1){
                    viewPager2.isUserInputEnabled = false
                }
            }
        })
    }

    fun exitAlertDialog() {
            AlertDialog.Builder(this@MainActivity, R.style.Theme_AppCompat_Dialog_Alert).apply {
                setTitle("앱 종료")
                setMessage("아카라이브 앱을 종료하시겠습니까?")
                setPositiveButton("종료", DialogInterface.OnClickListener { dialog, which -> finish() })
                setNegativeButton("돌아가기", DialogInterface.OnClickListener { dialog, which ->  })
            }.show()
    }
}