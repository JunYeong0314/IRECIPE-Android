package com.umcproject.irecipe.presentation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ActivityMainBinding
import com.umcproject.irecipe.presentation.ui.chat.ChatFragment
import com.umcproject.irecipe.presentation.ui.community.CommunityFragment
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.ui.mypage.MypageFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorFragment

class MainActivity: BaseActivity<ActivityMainBinding>({ActivityMainBinding.inflate(it)}) {
    private val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNav() // 바텀바 설정
        initFragment() // 초기화면 설정
    }

    private fun initBottomNav(){
        binding.btmMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_frag_home -> {
                    HomeFragment().changeFragment()
                }
                R.id.nav_frag_chat -> {
                    ChatFragment().changeFragment()
                }
                R.id.nav_frag_refrigerator -> {
                    RefrigeratorFragment().changeFragment()
                }
                R.id.nav_frag_community -> {
                    CommunityFragment().changeFragment()
                }
                R.id.nav_frag_mypage -> {
                    MypageFragment().changeFragment()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initFragment(){
        val transaction = manager.beginTransaction()
            .add(R.id.fv_main, HomeFragment())
        transaction.commit()
    }
    private fun Fragment.changeFragment() {
        manager.beginTransaction().replace(R.id.fv_main, this).commit()
    }
}