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
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.mucheng.sample.adapter.FileSelectorAdapter
import com.mucheng.sample.data.FileItem
import com.mucheng.sample.data.FileItem.Companion.DIR
import com.mucheng.sample.data.FileItem.Companion.FILE
import java.io.File

class FileSelectorActivity : AppCompatActivity() {

    companion object {
        const val RESULT_CODE = 200
    }

    private var currentPath = Environment.getExternalStorageDirectory().absolutePath

    private val fileItems: MutableList<FileItem> = ArrayList()

    private val adapter by lazy {
        FileSelectorAdapter(this, fileItems) { fileItem, _ ->
            val type = fileItem.type
            if (type == DIR) {
                val path = fileItem.path
                currentPath = path

                scanPath()
                notifyDataSetChanged()
            } else {
                val intent = Intent()
                intent.putExtra("path", fileItem.path)
                setResult(RESULT_CODE, intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_selector)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        scanPath()

        val recyclerView: RecyclerView = findViewById(R.id.fileSelectorRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private fun scanPath() {
        fileItems.clear()
        val currentFile = File(currentPath)
        val files = currentFile.listFiles() ?: return

        for (file in files) {
            val type = if (file.isFile) {
                FILE
            } else {
                DIR
            }
            fileItems.add(FileItem(file.name, type, file.absolutePath))
        }

        fileItems.sortWith { o1, o2 ->
            if (o1.type == DIR && o2.type == FILE) {
                -1
            } else if (o1.type == FILE && o2.type == DIR) {
                1
            } else {
                0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_file_selector, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> {
                finish()
            }

            R.id.back -> {
                val file = File(currentPath)
                val parent = file.parent
                if (parent != null) {
                    currentPath = parent
                    scanPath()
                    notifyDataSetChanged()
                }
            }

        }
        return true
    }

}