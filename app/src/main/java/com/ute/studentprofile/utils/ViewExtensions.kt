package com.ute.studentprofile.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun Double.toAcademicRanking(): String = when {
    this >= 3.6 -> "Xuất sắc!!"
    this >= 3.2 -> "Giỏi"
    this >= 2.5 -> "Khá"
    else -> "Trung bình"
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showConfirmDialog(title: String, message: String, onConfirm: () -> Unit) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("Đồng ý") { _, _ -> onConfirm() }
        setNegativeButton("Hủy", null)
    }.show()
}