package com.devstudio.arcalive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.devstudio.arcalive.ui.fragmet.MainFragmentStateAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager2 = findViewById<ViewPager2>(R.id.mainViewpager)
        viewPager2.adapter = MainFragmentStateAdapter(this)
        //viewPager2.isUserInputEnabled = false  스와이프 막기

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 1){
                    viewPager2.isUserInputEnabled = false
                }
            }
        })
    }
}