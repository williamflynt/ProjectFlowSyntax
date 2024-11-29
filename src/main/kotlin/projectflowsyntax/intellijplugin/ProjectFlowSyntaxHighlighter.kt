package projectflowsyntax.intellijplugin

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType


object ProjectFlowSyntaxHighlighterKeys {
    val COMMENT = TextAttributesKey.createTextAttributesKey(
        "PFS_COMMENT",
        DefaultLanguageHighlighterColors.LINE_COMMENT
    )
    val ATTRIBUTE_KEY = TextAttributesKey.createTextAttributesKey(
        "PFS_ATTRIBUTE_KEY",
        DefaultLanguageHighlighterColors.IDENTIFIER
    )
    val KV_SEPARATOR = TextAttributesKey.createTextAttributesKey(
        "PFS_KV_SEPARATOR",
        DefaultLanguageHighlighterColors.IDENTIFIER
    )
    val QUOTED_VALUE = TextAttributesKey.createTextAttributesKey(
        "PFS_QUOTED_VALUE",
        DefaultLanguageHighlighterColors.STRING
    )
    val VALUE = TextAttributesKey.createTextAttributesKey(
        "PFS_VALUE",
        DefaultLanguageHighlighterColors.STRING
    )
    val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
        "PFS_IDENTIFIER",
        DefaultLanguageHighlighterColors.IDENTIFIER
    )
    val CLUSTER_SIGIL = TextAttributesKey.createTextAttributesKey(
        "PFS_CLUSTER_SIGIL",
        DefaultLanguageHighlighterColors.CONSTANT
    )
    val COMMENT_SIGIL = TextAttributesKey.createTextAttributesKey(
        "PFS_COMMENT_SIGIL",
        DefaultLanguageHighlighterColors.LINE_COMMENT
    )
    val MILESTONE_SIGIL = TextAttributesKey.createTextAttributesKey(
        "PFS_MILESTONE_SIGIL",
        DefaultLanguageHighlighterColors.CONSTANT
    )
    val NEW_TASK_SIGIL = TextAttributesKey.createTextAttributesKey(
        "PFS_NEW_TASK_SIGIL",
        DefaultLanguageHighlighterColors.CONSTANT
    )
    val RESOURCE_SIGIL = TextAttributesKey.createTextAttributesKey(
        "PFS_RESOURCE_SIGIL",
        DefaultLanguageHighlighterColors.HIGHLIGHTED_REFERENCE
    )
    val IMPLODE_OP = TextAttributesKey.createTextAttributesKey(
        "PFS_IMPLODE_OP",
        DefaultLanguageHighlighterColors.KEYWORD
    )
    val EXPLODE_OP = TextAttributesKey.createTextAttributesKey(
        "PFS_EXPLODE_OP",
        DefaultLanguageHighlighterColors.KEYWORD
    )
    val REQUIRED_BY_OP = TextAttributesKey.createTextAttributesKey(
        "PFS_REQUIRED_BY_OP",
        DefaultLanguageHighlighterColors.KEYWORD
    )
    val NEGATION_OP = TextAttributesKey.createTextAttributesKey(
        "PFS_NEGATION_OP",
        DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL
    )
    val SEPARATOR = TextAttributesKey.createTextAttributesKey(
        "PFS_SEPARATOR",
        DefaultLanguageHighlighterColors.COMMA
    )
    val LEFT_BRACKET = TextAttributesKey.createTextAttributesKey(
        "PFS_LEFT_BRACKET",
        DefaultLanguageHighlighterColors.BRACKETS
    )
    val RIGHT_BRACKET = TextAttributesKey.createTextAttributesKey(
        "PFS_RIGHT_BRACKET",
        DefaultLanguageHighlighterColors.BRACKETS
    )
    val DOUBLE_QUOTE = TextAttributesKey.createTextAttributesKey(
        "PFS_DOUBLE_QUOTE",
        DefaultLanguageHighlighterColors.STRING
    )
    val NUMBER = TextAttributesKey.createTextAttributesKey(
        "PFS_NUMBER",
        DefaultLanguageHighlighterColors.NUMBER
    )
    val WHITESPACE = TextAttributesKey.createTextAttributesKey(
        "PFS_WHITESPACE",
        DefaultLanguageHighlighterColors.CLASS_NAME
    )
    val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
        "PFS_BAD_CHARACTER",
        DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE
    )
}


class ProjectFlowSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = ProjectFlowSyntaxLexerAdapter()

    @Suppress("CyclomaticComplexMethod")
    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        when (tokenType) {
            ProjectFlowSyntaxTypes.COMMENT -> arrayOf(ProjectFlowSyntaxHighlighterKeys.COMMENT)
            ProjectFlowSyntaxTypes.COMMENT_TEXT -> arrayOf(ProjectFlowSyntaxHighlighterKeys.COMMENT)
            ProjectFlowSyntaxTypes.KV_SEPARATOR -> arrayOf(ProjectFlowSyntaxHighlighterKeys.KV_SEPARATOR)
            ProjectFlowSyntaxTypes.ATTRIBUTE_KEY -> arrayOf(ProjectFlowSyntaxHighlighterKeys.ATTRIBUTE_KEY)
            ProjectFlowSyntaxTypes.QUOTED_VALUE -> arrayOf(ProjectFlowSyntaxHighlighterKeys.QUOTED_VALUE)
            ProjectFlowSyntaxTypes.VALUE -> arrayOf(ProjectFlowSyntaxHighlighterKeys.VALUE)
            ProjectFlowSyntaxTypes.VALUE_BETWEEN_QUOTES -> arrayOf(ProjectFlowSyntaxHighlighterKeys.VALUE)
            ProjectFlowSyntaxTypes.IDENTIFIER -> arrayOf(ProjectFlowSyntaxHighlighterKeys.IDENTIFIER)
            ProjectFlowSyntaxTypes.CLUSTER_SIGIL -> arrayOf(ProjectFlowSyntaxHighlighterKeys.CLUSTER_SIGIL)
            ProjectFlowSyntaxTypes.COMMENT_SIGIL -> arrayOf(ProjectFlowSyntaxHighlighterKeys.COMMENT_SIGIL)
            ProjectFlowSyntaxTypes.MILESTONE_SIGIL -> arrayOf(ProjectFlowSyntaxHighlighterKeys.MILESTONE_SIGIL)
            ProjectFlowSyntaxTypes.NEW_TASK_SIGIL -> arrayOf(ProjectFlowSyntaxHighlighterKeys.NEW_TASK_SIGIL)
            ProjectFlowSyntaxTypes.RESOURCE_SIGIL -> arrayOf(ProjectFlowSyntaxHighlighterKeys.RESOURCE_SIGIL)
            ProjectFlowSyntaxTypes.IMPLODE_OP -> arrayOf(ProjectFlowSyntaxHighlighterKeys.IMPLODE_OP)
            ProjectFlowSyntaxTypes.EXPLODE_OP -> arrayOf(ProjectFlowSyntaxHighlighterKeys.EXPLODE_OP)
            ProjectFlowSyntaxTypes.REQUIRED_BY_OP -> arrayOf(ProjectFlowSyntaxHighlighterKeys.REQUIRED_BY_OP)
            ProjectFlowSyntaxTypes.NEGATION_OP -> arrayOf(ProjectFlowSyntaxHighlighterKeys.NEGATION_OP)
            ProjectFlowSyntaxTypes.SEPARATOR -> arrayOf(ProjectFlowSyntaxHighlighterKeys.SEPARATOR)
            ProjectFlowSyntaxTypes.LEFT_BRACKET -> arrayOf(ProjectFlowSyntaxHighlighterKeys.LEFT_BRACKET)
            ProjectFlowSyntaxTypes.RIGHT_BRACKET -> arrayOf(ProjectFlowSyntaxHighlighterKeys.RIGHT_BRACKET)
            ProjectFlowSyntaxTypes.DOUBLE_QUOTE -> arrayOf(ProjectFlowSyntaxHighlighterKeys.DOUBLE_QUOTE)
            ProjectFlowSyntaxTypes.NUMBER -> arrayOf(ProjectFlowSyntaxHighlighterKeys.NUMBER)
            ProjectFlowSyntaxTypes.WHITESPACE -> arrayOf(ProjectFlowSyntaxHighlighterKeys.WHITESPACE)
            else -> arrayOf(ProjectFlowSyntaxHighlighterKeys.BAD_CHARACTER)
        }
}
