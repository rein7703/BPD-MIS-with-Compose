package com.example.bpdmiscompose

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TextAndIcon(
    val text: String,
    val icon: ImageVector,
    val onClickAction : () -> Unit = {},
    val destination : String = "",
)

data class ButtonInfo(
    val text : String ,
    val backgroundColor: Color,
    val textColor : Color,
    val outlined : Boolean = false,
    val onButtonClick : () -> Unit = {}
)
/*
enum class BankStaffScreen(@StringRes val title:Int){

    ChangePassword(title = R.string.change_password_header),
    ChangePasswordSuccess(title = R.string.selamat),
    Landing(title = R.string.app_name),
    Pemda(title = R.string.pemda_screen)
}*/








