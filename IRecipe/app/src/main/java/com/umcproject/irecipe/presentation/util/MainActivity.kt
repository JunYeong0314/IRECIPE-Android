package com.umcproject.irecipe.presentation.util

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ActivityMainBinding
import com.umcproject.irecipe.presentation.ui.chat.ChatBotActivity
import com.umcproject.irecipe.presentation.ui.chat.ChatFragment
import com.umcproject.irecipe.presentation.ui.community.CommunityFragment
import com.umcproject.irecipe.presentation.ui.community.comment.CommentFragment
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.ui.mypage.MypageFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorFragment
import com.umcproject.irecipe.presentation.util.Util.popFragment
import com.umcproject.irecipe.presentation.util.Util.showAnimatedFragment
import com.umcproject.irecipe.presentation.util.onboarding.OnboardingActivity

class MainActivity: BaseActivity<ActivityMainBinding>({ActivityMainBinding.inflate(it)}) {
    companion object{
        const val TAG = "MainActivity"
    }
    private val tagList = listOf(
        HomeFragment.TAG, ChatFragment.TAG, RefrigeratorFragment.TAG,
        CommunityFragment.TAG, MypageFragment.TAG
    )
    private val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNav() // 바텀바 설정
        initFragment() // 초기화면 설정
        onClickBackBtn() // 이전버튼 로직
    }

    private fun initBottomNav(){
        binding.btmMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_frag_home -> {
                    hideTitle()
                    HomeFragment().changeFragment(HomeFragment.TAG)
                    hideFragment(HomeFragment.TAG)
                }
                R.id.nav_frag_chat -> {
                    //showTitle(getString(R.string.title_chat))
                    val intent = Intent(this, ChatBotActivity::class.java)
                    startActivity(intent)
//                    ChatFragment().changeFragment(ChatFragment.TAG)
//                    hideFragment(ChatFragment.TAG)
//                    binding.btmMain.visibility = View.GONE //바텀바 숨기기
                }
                R.id.nav_frag_refrigerator -> {
                    showTitle(getString(R.string.title_ref), false)
                    RefrigeratorFragment(
                        onClickDetail = { title-> showTitle(title, true) },
                        onClickBackBtn = { title-> showTitle(title, false) }
                    ).changeFragment(RefrigeratorFragment.TAG)
                    hideFragment(RefrigeratorFragment.TAG)
                }
                R.id.nav_frag_community -> {
                    showTitle(getString(R.string.title_community), false)
                    CommentFragment().changeFragment(CommentFragment.TAG)
                    hideFragment(CommunityFragment.TAG)
                }
                R.id.nav_frag_mypage -> {
                    showTitle(getString(R.string.title_mypage), false)
                    MypageFragment().changeFragment(MypageFragment.TAG)
                    hideFragment(MypageFragment.TAG)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initFragment(){
        val transaction = manager.beginTransaction()
            .add(R.id.fv_main, HomeFragment(), HomeFragment.TAG)
        transaction.commit()
    }
    private fun Fragment.changeFragment(tag: String) {
        val fragment = manager.findFragmentByTag(tag)

        if(fragment != null){
            manager.beginTransaction().show(fragment).commit()
        }else{
            manager.beginTransaction().add(R.id.fv_main, this, tag).commit()
        }
    }

    private fun hideFragment(currentTag: String){
        tagList.forEach { tag->
            val fragment = manager.findFragmentByTag(tag)
            if(tag != currentTag && fragment != null){
                manager.beginTransaction().hide(fragment).commit()
            }
        }
    }

    private fun showTitle(title: String, isBackBtn: Boolean) {
        if(isBackBtn) binding.ibtnBack.visibility = View.VISIBLE
        else binding.ibtnBack.visibility = View.GONE

        binding.flTitle.visibility = View.VISIBLE
        binding.tvTitle.text = title
    }

    private fun hideTitle() {
        binding.flTitle.visibility = View.GONE
    }

    private fun onClickBackBtn(){
        binding.ibtnBack.setOnClickListener { popFragment(this@MainActivity) }
    }
}