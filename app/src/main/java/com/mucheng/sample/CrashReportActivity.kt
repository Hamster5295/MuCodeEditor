/*
 * CN:
 * 作者：SuMuCheng
 * 我的 QQ: 3578557729
 * Github 主页：https://github.com/CaiMuCheng
 * 项目主页: https://github.com/CaiMuCheng/MuCodeEditor
 *
 * 你可以免费使用、商用以下代码，也可以基于以下代码做出修改，但是必须在你的项目中标注出处
 * 例如：在你 APP 的设置中添加 “关于编辑器” 一栏，其中标注作者以及此编辑器的 Github 主页
 *
 * 此代码使用 MPL 2.0 开源许可证，你必须标注作者信息
 * 若你要修改文件，请勿删除此注释
 * 若你违反以上条例我们有权向您提起诉讼!
 *
 * EN:
 * Author: SuMuCheng
 * My QQ-Number: 3578557729
 * Github Homepage: https://github.com/CaiMuCheng
 * Project Homepage: https://github.com/CaiMuCheng/MuCodeEditor
 *
 * You can use the following code for free, commercial use, or make modifications based on the following code, but you must mark the source in your project.
 * For example: add an "About Editor" column in your app's settings, which identifies the author and the Github home page of this editor.
 *
 * This code uses the MPL 2.0 open source license, you must mark the author information.
 * Do not delete this comment if you want to modify the file.
 * If you violate the above regulations we have the right to sue you!
 */

package com.mucheng.sample

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.google.android.material.textview.MaterialTextView

class CrashReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_handler)

        var error: CharSequence =
            (intent.getStringExtra("Error") ?: "NULL").replace("... [0-9]+ more".toRegex(), "")
        val mainlyRegex = "Caused by: .+".toRegex()
        val range = mainlyRegex.findAll(error).map { it.range }

        range.forEach {
            error = SpannableString(error).also { spanText ->
                val first = it.first
                val last = it.last + 1
                spanText[first..last] = ForegroundColorSpan(Color.RED)
            }
        }

        val view: MaterialTextView = findViewById(R.id.error)
        view.text = error
    }

    override fun onBackPressed() {
        // 阻止回退
        Toast.makeText(this, "崩溃已发生，不允许返回", Toast.LENGTH_SHORT).show()
    }


}