package com.umcproject.irecipe.presentation.ui.mypage

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class MypageFragment: BaseFragment<FragmentMypageBinding>() {
    companion object{
        const val TAG = "MypageFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageBinding {
        return FragmentMypageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        //알림창 커스텀
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.mypage_withdraw, null)

        val alertDialog = AlertDialog.Builder(context, R.style.RoundedCornersDialog)
            .setView(view)
            .create()

        val textTitle = view?.findViewById<TextView>(R.id.tv_main_alert)
        val textSubtitle =  view?.findViewById<TextView>(R.id.tv_alert)
        val buttonConfirm = view?.findViewById<Button>(R.id.btn_withdraw)!!
        val buttonClose =  view?.findViewById<Button>(R.id.btn_cancel)!!

        textTitle?.text = "정말 탈퇴하시겠어요?"
        textSubtitle?.text = "탈퇴 버튼 선택 시, 계정은\n" + "삭제되어 복구되지 않습니다."
        buttonConfirm?.text = "탈퇴"

        //알림창 띄우기
        binding.mypageWithdraw.setOnClickListener{
            alertDialog.show()
        }
        //회원탈퇴
        buttonConfirm.setOnClickListener {
            //탈퇴기능
        }
        //취소
        buttonClose.setOnClickListener {
            alertDialog.dismiss()
        }
    }

}