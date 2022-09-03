package com.mucheng.editor.language.php

import com.mucheng.editor.base.IAutoCompletionHelper
import com.mucheng.editor.base.lang.AbstractBasicLanguage
import com.mucheng.editor.base.lexer.AbstractLexer
import com.mucheng.editor.data.AutoCompletionItem
import com.mucheng.editor.sample.helper.DefaultAutoCompletionHelper
import com.mucheng.editor.token.ThemeToken
import com.mucheng.editor.view.MuCodeEditor

@Suppress("SpellCheckingInspection")
object PhpLanguageWrapper : AbstractBasicLanguage() {

    private lateinit var editor: MuCodeEditor

    private val operatorTokenMap: Map<Char, ThemeToken> = hashMapOf(
        '+' to PhpToken.PLUS,
        '-' to PhpToken.MINUS,
        '*' to PhpToken.MULTI,
        '/' to PhpToken.DIV,
        ':' to PhpToken.COLON,
        '!' to PhpToken.NOT,
        '%' to PhpToken.MOD,
        '^' to PhpToken.XOR,
        '&' to PhpToken.AND,
        '?' to PhpToken.QUESTION,
        '~' to PhpToken.COMP,
        '.' to PhpToken.DOT,
        ',' to PhpToken.COMMA,
        ';' to PhpToken.SEMICOLON,
        '=' to PhpToken.EQUALS,
        '(' to PhpToken.LEFT_PARENTHESIS,
        ')' to PhpToken.RIGHT_PARENTHESIS,
        '[' to PhpToken.LEFT_BRACKET,
        ']' to PhpToken.RIGHT_BRACKET,
        '{' to PhpToken.LEFT_BRACE,
        '}' to PhpToken.RIGHT_BRACE,
        '|' to PhpToken.OR,
        '<' to PhpToken.LESS_THAN,
        '>' to PhpToken.MORE_THAN
    )

    private val keywordTokenMap: Map<String, ThemeToken> = hashMapOf(
        "__halt_compiler" to PhpToken.HALT_COMPILER,
        "abstract" to PhpToken.ABSTRACT,
        "and" to PhpToken.AND_KEYWORD,
        "array" to PhpToken.ARRAY,
        "as" to PhpToken.AS,
        "break" to PhpToken.BREAK,
        "callable" to PhpToken.CALLABLE,
        "case" to PhpToken.CASE,
        "catch" to PhpToken.CATCH,
        "class" to PhpToken.CLASS,
        "clone" to PhpToken.CLONE,
        "const" to PhpToken.CONST,
        "continue" to PhpToken.CONTINUE,
        "declare" to PhpToken.DECLARE,
        "default" to PhpToken.DEFAULT,
        "die" to PhpToken.DIE,
        "do" to PhpToken.DO,
        "echo" to PhpToken.ECHO,
        "else" to PhpToken.ELSE,
        "elseif" to PhpToken.ELSEIF,
        "empty" to PhpToken.EMPTY,
        "enddeclare" to PhpToken.ENDDECLARE,
        "endfor" to PhpToken.ENDFOR,
        "endforeach" to PhpToken.ENDFOREACH,
        "endif" to PhpToken.ENDIF,
        "endswitch" to PhpToken.ENDSWITCH,
        "endwhile" to PhpToken.ENDWHILE,
        "eval" to PhpToken.EVAL,
        "exit" to PhpToken.EXIT,
        "extends" to PhpToken.EXTENDS,
        "final" to PhpToken.FINAL,
        "finally" to PhpToken.FINALLY,
        "fn" to PhpToken.FN,
        "for" to PhpToken.FOR,
        "foreach" to PhpToken.FOREACH,
        "function" to PhpToken.FUNCTION,
        "global" to PhpToken.GLOBAL,
        "goto" to PhpToken.GOTO,
        "if" to PhpToken.IF,
        "implements" to PhpToken.IMPLEMENTS,
        "include" to PhpToken.INCLUDE,
        "include_once" to PhpToken.INCLUDE_ONCE,
        "instanceof" to PhpToken.INSTANCEOF,
        "insteadof" to PhpToken.INSTEADOF,
        "interface" to PhpToken.INTERFACE,
        "isset" to PhpToken.ISSET,
        "list" to PhpToken.LIST,
        "match" to PhpToken.MATCH,
        "namespace" to PhpToken.NAMESPACE,
        "new" to PhpToken.NEW,
        "or" to PhpToken.OR_KEYWORD,
        "print" to PhpToken.PRINT,
        "private" to PhpToken.PRIVATE,
        "protected" to PhpToken.PROTECTED,
        "public" to PhpToken.PUBLIC,
        "readonly" to PhpToken.READONLY,
        "require" to PhpToken.REQUIRE,
        "require_once" to PhpToken.REQUIRE_ONCE,
        "return" to PhpToken.RETURN,
        "static" to PhpToken.STATIC,
        "switch" to PhpToken.SWITCH,
        "throw" to PhpToken.THROW,
        "trait" to PhpToken.TRAIT,
        "try" to PhpToken.TRY,
        "unset" to PhpToken.UNSET,
        "use" to PhpToken.USE,
        "var" to PhpToken.VAR,
        "while" to PhpToken.WHILE,
        "xor" to PhpToken.XOR_KEYWORD,
        "yield" to PhpToken.YIELD,
        "from" to PhpToken.FROM
    )

    private val specialTokenMap: Map<String, ThemeToken> = emptyMap()

    private val autoCompletionHelper = DefaultAutoCompletionHelper()

    private val userDefaultAutoCompletionItems: MutableList<AutoCompletionItem> = ArrayList()

    override fun getLexer(): AbstractLexer? {
        return null
    }

    override fun doSpan(): Boolean {
        return true
    }

    override fun setEditor(editor: MuCodeEditor) {
        this.editor = editor
    }

    override fun getEditor(): MuCodeEditor {
        return editor
    }

    override fun getOperatorTokenMap(): Map<Char, ThemeToken> {
        return operatorTokenMap
    }

    override fun getKeywordTokenMap(): Map<String, ThemeToken> {
        return keywordTokenMap
    }

    override fun getSpecialTokenMap(): Map<String, ThemeToken> {
        return specialTokenMap
    }

    override fun getAutoCompletionHelper(): IAutoCompletionHelper {
        return autoCompletionHelper
    }

    override fun getUserDefinedAutoCompletionItems(): List<AutoCompletionItem> {
        return userDefaultAutoCompletionItems
    }

    override fun addUserDefinedAutoCompletionItem(autoCompletionItem: AutoCompletionItem) {
        userDefaultAutoCompletionItems.add(autoCompletionItem)
    }

    override fun addUserDefinedAutoCompletionItems(autoCompletionItems: List<AutoCompletionItem>) {
        userDefaultAutoCompletionItems.addAll(autoCompletionItems)
    }

    override fun clearUserDefinedAutoCompletionItems() {
        userDefaultAutoCompletionItems.clear()
    }

}