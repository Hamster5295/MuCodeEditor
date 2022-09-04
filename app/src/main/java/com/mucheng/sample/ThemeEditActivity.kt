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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mucheng.editor.color.Color
import com.mucheng.editor.theme.BaseTheme
import com.mucheng.editor.theme.ThemeUtil
import com.mucheng.editor.token.ThemeToken
import com.mucheng.sample.adapter.ThemeEditAdapter
import com.mucheng.sample.callback.ThemeEditCallback
import com.mucheng.sample.data.ThemeItem
import com.mucheng.sample.databinding.ActivityThemeEditBinding


//此类仅作为演示使用，不怎么能实现正常功能
@Suppress("LocalVariableName")
class ThemeEditActivity : AppCompatActivity(), ThemeEditCallback {

    private lateinit var viewBinding: ActivityThemeEditBinding

    private val themeItemList: MutableList<ThemeItem> = ArrayList()

    private val adapter by lazy {
        fetchThemeItemList()
        ThemeEditAdapter(
            this, MainActivity.editor.styleManager.theme, themeItemList
        ).apply {
            setThemeEditCallback(this@ThemeEditActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityThemeEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val toolbar = viewBinding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val recyclerView = viewBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_theme_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.reset_theme -> {
                val editor = MainActivity.editor
                val styleManager = editor.styleManager
                val defaultTheme = ThemeUtil.getDefaultTheme(styleManager) as BaseTheme
                styleManager.replaceTheme(defaultTheme)

                ThemeUtil.saveTheme(defaultTheme, this)
                fetchThemeItemList()
                adapter.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchThemeItemList() {
        themeItemList.clear()
        themeItemList.addAll(
            listOf(
                getThemeItem("背景颜色", ThemeToken.BACKGROUND_COLOR_TOKEN),
                getThemeItem("行号颜色", ThemeToken.LINE_NUMBER_COLOR_TOKEN),
                getThemeItem("行号分割线颜色", ThemeToken.LINE_NUMBER_DIVIDING_LINE_COLOR_TOKEN),
                getThemeItem("光标颜色", ThemeToken.CURSOR_COLOR_TOKEN),
                getThemeItem("文本选中处理背景色", ThemeToken.TEXT_SELECT_HANDLE_BACKGROUND_COLOR_TOKEN),
                getThemeItem("文本选中角标颜色", ThemeToken.TEXT_SELECT_HANDLE_COLOR_TOKEN),
                getThemeItem("标识符颜色", ThemeToken.IDENTIFIER_COLOR_TOKEN),
                getThemeItem("关键字颜色", ThemeToken.KEYWORD_COLOR),
                getThemeItem("注释颜色", ThemeToken.COMMENT_COLOR),
                getThemeItem("字符串颜色", ThemeToken.STRING_COLOR),
                getThemeItem("符合颜色", ThemeToken.SYMBOL_COLOR),
                getThemeItem("特殊值颜色", ThemeToken.SPECIAL_COLOR),
                getThemeItem("数值颜色", ThemeToken.NUMERICAL_VALUE_COLOR)
            )
        )
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun getThemeItem(title: String, themeToken: ThemeToken): ThemeItem {
        return ThemeItem(title, themeToken, MainActivity.editor.getTheme().getColor(themeToken))
    }

    private fun showColorSelectorDialog(themeItem: ThemeItem, position: Int) {
        val themeToken = themeItem.themeToken
        ColorPickerDialogBuilder
            .with(this)
            .setTitle("Choose color")
            .initialColor(MainActivity.editor.styleManager.theme.getColor(themeToken).hexColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(
                "ok"
            ) { _, selectedColor, _ ->
                val selectedColorInlined = Color(selectedColor)
                themeItem.initColor = selectedColorInlined
                (MainActivity.editor.styleManager.theme as BaseTheme).setColor(
                    themeToken,
                    selectedColorInlined
                )

                ThemeUtil.saveTheme(MainActivity.editor.styleManager.theme as BaseTheme, this)
                adapter.notifyItemChanged(position)
            }
            .setNegativeButton("cancel", null)
            .build()
            .show()
    }

    override fun onThemeEdit(item: ThemeItem, position: Int) {
        showColorSelectorDialog(item, position)
    }

}