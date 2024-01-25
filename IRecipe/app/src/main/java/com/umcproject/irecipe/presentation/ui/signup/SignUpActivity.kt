package com.umcproject.irecipe.presentation.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ActivitySignupBinding
import com.umcproject.irecipe.presentation.ui.signup.step.FirstStepFragment
import com.umcproject.irecipe.presentation.util.BaseActivity
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util.removeFragment

class SignUpActivity: BaseActivity<ActivitySignupBinding>({ ActivitySignupBinding.inflate(it) }) {
    private val manager = supportFragmentManager
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isLastComplete.observe(this@SignUpActivity, Observer {
            if(it){
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        initFragment()
    }

    private fun initFragment(){
        val transaction = manager.beginTransaction()
            .add(R.id.fv_signUp, FirstStepFragment(viewModel = viewModel))
        transaction.commit()
    }
}

