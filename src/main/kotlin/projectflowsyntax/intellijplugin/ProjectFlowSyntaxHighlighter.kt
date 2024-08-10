package projectflowsyntax.intellijplugin

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class ProjectFlowSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = ProjectFlowSyntaxLexerAdapter()

    @Suppress("CyclomaticComplexMethod")
    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        when (tokenType) {
            ProjectFlowSyntaxTypes.COMMENT -> arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)
            ProjectFlowSyntaxTypes.COMMENT_TEXT -> arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)
            ProjectFlowSyntaxTypes.ATTRIBUTE_KEY -> arrayOf(DefaultLanguageHighlighterColors.CONSTANT)
            ProjectFlowSyntaxTypes.QUOTED_VALUE -> arrayOf(DefaultLanguageHighlighterColors.STRING)
            ProjectFlowSyntaxTypes.VALUE -> arrayOf(DefaultLanguageHighlighterColors.STRING)
            ProjectFlowSyntaxTypes.VALUE_BETWEEN_QUOTES -> arrayOf(DefaultLanguageHighlighterColors.STRING)
            ProjectFlowSyntaxTypes.IDENTIFIER -> arrayOf(DefaultLanguageHighlighterColors.CLASS_NAME)
            ProjectFlowSyntaxTypes.CLUSTER_SIGIL -> arrayOf(DefaultLanguageHighlighterColors.CONSTANT)
            ProjectFlowSyntaxTypes.COMMENT_SIGIL -> arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)
            ProjectFlowSyntaxTypes.MILESTONE_SIGIL -> arrayOf(DefaultLanguageHighlighterColors.CONSTANT)
            ProjectFlowSyntaxTypes.NEW_TASK_SIGIL -> arrayOf(DefaultLanguageHighlighterColors.CONSTANT)
            ProjectFlowSyntaxTypes.RESOURCE_SIGIL -> arrayOf(DefaultLanguageHighlighterColors.HIGHLIGHTED_REFERENCE)
            ProjectFlowSyntaxTypes.IMPLODE_OP -> arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
            ProjectFlowSyntaxTypes.EXPLODE_OP -> arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
            ProjectFlowSyntaxTypes.REQUIRED_BY_OP -> arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
            ProjectFlowSyntaxTypes.NEGATION_OP -> arrayOf(DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL)
            ProjectFlowSyntaxTypes.LEFT_BRACKET -> arrayOf(DefaultLanguageHighlighterColors.BRACKETS)
            ProjectFlowSyntaxTypes.RIGHT_BRACKET -> arrayOf(DefaultLanguageHighlighterColors.BRACKETS)
            ProjectFlowSyntaxTypes.DOUBLE_QUOTE -> arrayOf(DefaultLanguageHighlighterColors.STRING)
            ProjectFlowSyntaxTypes.NUMBER -> arrayOf(DefaultLanguageHighlighterColors.NUMBER)

            else -> arrayOf(DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)
        }
}
