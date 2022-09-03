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

package com.mucheng.editor.theme

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mucheng.editor.base.AbstractTheme
import com.mucheng.editor.manager.EditorStyleManager
import java.io.*

class ThemeUtil {
    companion object {

        fun getDefaultTheme(styleManager: EditorStyleManager): AbstractTheme {
            return MuTheme(styleManager);
        }

        fun getThemeFiles(context: Context): List<String> {
            val f = File(context.filesDir.absolutePath);
            if (!f.exists()) return ArrayList<String>(0);
            return ArrayList<String>(f.list().toList())
        }

        //用这个之前记得调用getThemeFiles获取所有文件
        fun getThemeByFileName(
            styleManager: EditorStyleManager,
            fileName: String,
            context: Context
        ): AbstractTheme {
            if (!File(context.filesDir, fileName).exists()) {
                //No theme named "name" present
                Log.w(
                    "MuCodeEditor",
                    "Cannot find theme named $fileName, use default theme instead!"
                );
                return MuTheme(styleManager);
            }

            var reader = BufferedReader(InputStreamReader(context.openFileInput(fileName)));
            val data = GsonBuilder().enableComplexMapKeySerialization().create().fromJson(
                reader.readLine(),
                ThemeData::class.java
            )
            reader.close()
            //文件名去掉后缀".json"作为BaseTheme的Name
            return BaseTheme(styleManager, fileName.dropLast(5), data);
        }

        fun saveTheme(theme: BaseTheme, context: Context) {
            val writer = BufferedWriter(
                OutputStreamWriter(
                    context.openFileOutput(
                        "${theme.name}.json",
                        Context.MODE_PRIVATE
                    )
                )
            )
            writer.write(
                GsonBuilder().enableComplexMapKeySerialization().create().toJson(theme.data)
            )
            writer.close()
        }
    }
}