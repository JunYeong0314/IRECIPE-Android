package com.umcproject.irecipe.presentation.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.umcproject.irecipe.R

object Util {
    fun showHorizontalFragment(id: Int, activity: FragmentActivity, fragment: Fragment, tag: String){
        val transaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.horizon_enter_front,
                    R.anim.hotizon_exit_back,
                    R.anim.horizon_enter_back,
                    R.anim.horizon_exit_front
                )
                .add(id, fragment, tag)
        transaction.addToBackStack(tag).commit()
    }

    fun showVerticalFragment(id: Int, activity: FragmentActivity, fragment: Fragment, tag: String){
        val transaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.vertical_enter_front,
                    R.anim.vertical_exit_back,
                    R.anim.none,
                    R.anim.vertical_exit_front
                )
                .add(id, fragment, tag)
        transaction.addToBackStack(tag).commit()
    }

    fun showFragment(id: Int, activity: FragmentActivity, fragment: Fragment, tag: String){
        val transaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
                .add(id, fragment, tag)
        transaction.addToBackStack(tag).commit()
    }

    fun showNoStackFragment(id: Int, activity: FragmentActivity, fragment: Fragment, tag: String){
        val transaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
                .add(id, fragment, tag)
        transaction.commit()
    }

    fun popFragment(activity: FragmentActivity){
        activity.supportFragmentManager.popBackStack()
    }

    fun touchHideKeyboard(activity: Activity){
        if(activity.currentFocus != null){
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}