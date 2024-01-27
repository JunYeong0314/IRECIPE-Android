package com.umcproject.irecipe.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.umcproject.irecipe.R

object Util {
    fun showFragment(activity: FragmentActivity, fragment: Fragment, tag: String){
        val transaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.horizon_enter_front,
                    R.anim.hotizon_exit_back,
                    R.anim.horizon_enter_back,
                    R.anim.horizon_exit_front
                )
                .replace(R.id.fv_signUp, fragment, tag)
        transaction.addToBackStack(tag).commitAllowingStateLoss()
    }

    fun popFragment(activity: FragmentActivity){
        activity.supportFragmentManager.popBackStack()
    }
}