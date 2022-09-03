package com.mucheng.editor.language.php

import androidx.annotation.Keep
import com.mucheng.editor.language.html.HtmlLanguage

@Keep
@Suppress("LeakingThis")
open class PhpLanguage : HtmlLanguage() {

    companion object {

        @Volatile
        @JvmStatic
        private var instance: PhpLanguage? = null

        @JvmStatic
        fun getInstance(): HtmlLanguage {
            if (instance == null) {
                synchronized(PhpLanguage::class.java) {
                    if (instance == null) {
                        instance = PhpLanguage()
                    }
                }
            }
            return instance!!
        }

    }

    private val phpLexer = PhpLexer(this)

    override fun getLexer(): PhpLexer {
        return phpLexer
    }

}