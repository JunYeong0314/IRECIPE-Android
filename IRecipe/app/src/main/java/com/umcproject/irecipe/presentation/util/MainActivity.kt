package com.umcproject.irecipe.presentation.util

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ActivityMainBinding
import com.umcproject.irecipe.presentation.ui.chat.ChatBotActivity
import com.umcproject.irecipe.presentation.ui.community.CommunityFragment
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.ui.mypage.MypageFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorFragment
import com.umcproject.irecipe.presentation.util.Util.popFragment
import com.umcproject.irecipe.presentation.util.factory.MainFragmentFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>({ActivityMainBinding.inflate(it)}) {
    private var id=0 // 채팅 Activity 이동 후 태그 기록
    companion object{
        const val TAG = "MainActivity"
    }
    private val tagList = listOf(
        HomeFragment.TAG, RefrigeratorFragment.TAG,
        CommunityFragment.TAG, MypageFragment.TAG
    )
    private val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        setFragmentFactory()
        super.onCreate(savedInstanceState)

        showTitle(getString(R.string.title_home), false)
        initBottomNav() // 바텀바 설정
        onClickBackBtn() // 이전버튼 로직
    }

    private fun initBottomNav(){
        binding.btmMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_frag_home -> {
                    id = R.id.nav_frag_home
                    showTitle(getString(R.string.title_home), false)
                    HomeFragment(
                        onClickDetail = { title-> showTitle(title, true) },
                        onClickBackBtn = { title-> showTitle(title, false) },
                        onHideBottomBar = { hideBottomBar() },
                        onShowBottomBar = { showBottomBar() }
                    ).changeFragment(HomeFragment.TAG)
                    hideFragment(HomeFragment.TAG)
                }
                R.id.nav_frag_chat -> {
                    val intent = Intent(this, ChatBotActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_frag_refrigerator -> {
                    id = R.id.nav_frag_refrigerator
                    showTitle(getString(R.string.title_ref), false)
                    RefrigeratorFragment(
                        onClickDetail = { title-> showTitle(title, true) },
                        onClickBackBtn = { title-> showTitle(title, false) }
                    ).changeFragment(RefrigeratorFragment.TAG)
                    hideFragment(RefrigeratorFragment.TAG)
                }
                R.id.nav_frag_community -> {
                    id = R.id.nav_frag_community
                    showTitle(getString(R.string.title_community), false)
                    CommunityFragment(
                        onClickDetail = { title-> showTitle(title, true) }, // 상세페이지 들어갔을때
                        onClickBackBtn = { title-> showTitle(title, false) }, // 상세페이지 나갔을 때
                        onHideBottomBar = { hideBottomBar() },
                        onShowBottomBar = { showBottomBar() }
                    ).changeFragment(CommunityFragment.TAG)
                    hideFragment(CommunityFragment.TAG)
                }
                R.id.nav_frag_mypage -> {
                    id = R.id.nav_frag_mypage
                    showTitle(getString(R.string.title_mypage), false)
                    MypageFragment(
                        onClickDetail = { title-> showTitle(title, true) },
                        onClickBackBtn = { title-> showTitle(title, false) },
                        onHideBottomBar = { hideBottomBar() },
                        onShowBottomBar = { showBottomBar() }
                    ).changeFragment(MypageFragment.TAG)
                    hideFragment(MypageFragment.TAG)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initFragment(){
        id = R.id.nav_frag_home
        val transaction = manager.beginTransaction()
            .add(R.id.fv_main, HomeFragment(
                onClickDetail = { title-> showTitle(title, true) },
                onClickBackBtn = { title-> showTitle(title, false) },
                onHideBottomBar = { hideBottomBar() },
                onShowBottomBar = { showBottomBar() }))
        transaction.commit()
    }

    private fun Fragment.changeFragment(tag: String) {
        val findFragment = manager.findFragmentByTag(tag)
        val fragment: Fragment =
            when(tag){
                HomeFragment.TAG -> { manager.fragmentFactory.instantiate(classLoader, HomeFragment::class.java.name) }
                RefrigeratorFragment.TAG -> { manager.fragmentFactory.instantiate(classLoader, RefrigeratorFragment::class.java.name) }
                CommunityFragment.TAG -> { manager.fragmentFactory.instantiate(classLoader, CommunityFragment::class.java.name) }
                MypageFragment.TAG -> { manager.fragmentFactory.instantiate(classLoader, MypageFragment::class.java.name) }
                else -> { return }
            }

        if(findFragment != null) manager.beginTransaction().show(findFragment).commit()
        else manager.beginTransaction().add(R.id.fv_main, fragment, tag).commit()

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

    private fun onClickBackBtn(){
        binding.ibtnBack.setOnClickListener { popFragment(this@MainActivity) }
    }

    private fun hideBottomBar(){
        binding.btmMain.visibility = View.GONE
    }

    private fun showBottomBar(){
        binding.btmMain.visibility = View.VISIBLE
    }

    private fun setFragmentFactory(){
        manager.fragmentFactory = MainFragmentFactory(
            onClickDetail = { title-> showTitle(title, true) },
            onClickBackBtn = { title-> showTitle(title, false) },
            onHideBottomBar = { hideBottomBar() },
            onShowBottomBar = { showBottomBar() }
        )
    }

    override fun onResume() {
        super.onResume()
        if(id != 0){
            binding.btmMain.selectedItemId = id // 채팅 이전화면 활성화
        }else{
            initFragment()
        }

    }

}