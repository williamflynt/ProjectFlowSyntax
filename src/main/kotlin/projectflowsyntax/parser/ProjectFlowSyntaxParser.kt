package projectflowsyntax.parser

import io.github.treesitter.ktreesitter.Language
import io.github.treesitter.ktreesitter.Parser
import io.github.treesitter.ktreesitter.Tree

object ProjectFlowSyntaxParser {
    private val pfsLanguagePtr = ProjectFlowSyntaxWrapper.getTSLanguage()
    private val pfsLanguage = Language(pfsLanguagePtr)
    private val parser: Parser = Parser(pfsLanguage)

    fun parse(text: String): Tree = parser.parse(text)

    fun parse(
        text: String,
        oldTree: Tree?,
    ): Tree = parser.parse(text, oldTree)
}
