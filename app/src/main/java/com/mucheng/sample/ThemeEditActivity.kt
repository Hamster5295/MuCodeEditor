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

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mucheng.editor.color.Color
import com.mucheng.editor.theme.BaseTheme
import com.mucheng.editor.theme.ThemeUtil
import com.mucheng.editor.token.ThemeToken


//此类仅作为演示使用，不怎么能实现正常功能
class ThemeEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_edit)

        //是的没错，这是一个被用作色块的Textview
        val colorView1 = findViewById<TextView>(R.id.color_view_1);
        val color_bg = findViewById<CardView>(R.id.color_bg);
        color_bg.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(MainActivity.editor.styleManager.theme.getColor(ThemeToken.BACKGROUND_COLOR_TOKEN).hexColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { _, selectedColor, _ ->
                    colorView1.setBackgroundColor(selectedColor)
                    (MainActivity.editor.styleManager.theme as BaseTheme).setColor(
                        ThemeToken.BACKGROUND_COLOR_TOKEN,
                        Color(selectedColor)
                    )

                    ThemeUtil.saveTheme(MainActivity.editor.styleManager.theme as BaseTheme,this)
                }
                .setNegativeButton("cancel", DialogInterface.OnClickListener { _, _ -> })
                .build()
                .show()
        }

        colorView1.setBackgroundColor(MainActivity.editor.styleManager.theme.getColor(ThemeToken.BACKGROUND_COLOR_TOKEN).hexColor)
    }
}