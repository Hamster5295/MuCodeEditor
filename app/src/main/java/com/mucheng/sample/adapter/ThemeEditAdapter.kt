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

package com.mucheng.sample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.mucheng.editor.base.AbstractTheme
import com.mucheng.editor.token.ThemeToken
import com.mucheng.sample.R
import com.mucheng.sample.callback.ThemeEditCallback
import com.mucheng.sample.data.ThemeItem

@Suppress("PropertyName")
class ThemeEditAdapter(
    private val context: Context,
    private val theme: AbstractTheme,
    private val themeItemList: List<ThemeItem>
) : RecyclerView.Adapter<ThemeEditAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: MaterialTextView = itemView.findViewById(R.id.textView)
        val colorView1: View = itemView.findViewById(R.id.color_view_1)
        val color_bg: CardView = itemView.findViewById(R.id.color_bg)
    }

    private val inflater by lazy { LayoutInflater.from(context) }

    private var themeEditCallback: ThemeEditCallback? = null

    fun setThemeEditCallback(themeEditCallback: ThemeEditCallback) {
        this.themeEditCallback = themeEditCallback
    }

    @Suppress("unused")
    fun getThemeEditCallback(): ThemeEditCallback? {
        return themeEditCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflater.inflate(
                R.layout.layout_theme_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val themeItem = themeItemList[position]
        holder.color_bg.setOnClickListener {
            themeEditCallback?.onThemeEdit(themeItem, position)
        }
        holder.title.text = themeItem.title
        holder.colorView1.setBackgroundColor(themeItem.initColor.hexColor)
    }

    override fun getItemCount(): Int {
        return themeItemList.size
    }

}