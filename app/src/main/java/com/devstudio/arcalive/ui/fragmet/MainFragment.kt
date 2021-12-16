package com.devstudio.arcalive.ui.fragmet

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.devstudio.arcalive.R
import com.devstudio.arcalive.util.pref
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val context = container?.context
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        val noticeText = v.findViewById<TextView>(R.id.appNotice_text)
        noticeText.isSelected = true
        noticeText.text = "테스트 공지입니다 꼭 필수로 확인할 필요는 없데수용 asdfjasldjglajsdglkasdgj"

        val chipGroup = v.findViewById<ChipGroup>(R.id.recentSearchChipGroup)
        v.findViewById<EditText>(R.id.channelSearchEditText).setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                //검색 리스너
                if(p1 == EditorInfo.IME_ACTION_SEARCH){
                    //이곳에 검색 추가
                    return true
                }
                return false
            }
        })

        addRecentSearch(chipGroup, "원신")
        addRecentSearch(chipGroup, "이터널 리턴")
        addRecentSearch(chipGroup, "유머")
        addRecentSearch(chipGroup, "유머1")
        addRecentSearch(chipGroup, "유머2")
        addRecentSearch(chipGroup, "유머3")
        addRecentSearch(chipGroup, "유머4")
        addRecentSearch(chipGroup, "유머5")
        addRecentSearch(chipGroup, "유머5")
        addRecentSearch(chipGroup, "유머5")

        return v
    }

    fun addRecentSearch(chipGroup: ChipGroup, text : String){
        val chip = Chip(activity)
        chip.text = text
        chip.isCheckable = true
        chip.isCheckedIconVisible = false
        chip.setOnClickListener {
            
        }
        chipGroup.addView(chip)
    }

    fun loadRecentSearched(){
        val jsonString = pref(requireContext()).get("", "")
    }
}