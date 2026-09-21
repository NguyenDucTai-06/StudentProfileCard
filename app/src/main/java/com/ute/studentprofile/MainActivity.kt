package com.ute.studentprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.model.Student
import com.ute.studentprofile.utils.showConfirmDialog
import com.ute.studentprofile.utils.toAcademicRanking
import com.ute.studentprofile.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentStudent: Student? = Student(
        id = "2415053122240",
        name = "Nguyễn Đức Tài",
        className = "126LTTD03",
        email = "ngductai1012@ute.udn.vn",
        gpa = 3.2,
        phone = "0947203605"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindStudentData(currentStudent)
        
        binding.btnUpdateGpa.setOnClickListener {
            val student = currentStudent ?: return@setOnClickListener
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()

            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast("Điểm GPA không hợp lệ!")
                return@setOnClickListener
            }

            currentStudent = student.copy(gpa = newGpa)
            bindStudentData(currentStudent)
            toast("Cập nhật điểm thành công!")
        }

        binding.btnCall.setOnClickListener {
            val phone = currentStudent?.phone
            if (!phone.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phone")
                }
                startActivity(intent)
            }
        }

        binding.btnDelete.setOnClickListener {
            showConfirmDialog(
                title = "Xác nhận xóa",
                message = "Bạn có chắc chắn muốn xóa hồ sơ sinh viên này không?"
            ) {
                currentStudent = null
                bindStudentData(null)
                toast("Đã xóa hồ sơ thành công!")
            }
        }
    }

    private fun bindStudentData(student: Student?) {
        if (student != null) {
            binding.cardProfile.visibility = View.VISIBLE
            with(binding) {
                tvName.text = student.name
                tvStudentId.text = "MSSV: ${student.id} | LHP: ${student.className}"
                tvPhone.text = "SĐT: ${student.phone}"
                tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
                edtNewGpa.setText(student.gpa.toString())
            }
        } else {
            binding.cardProfile.visibility = View.GONE
        }
    }
}