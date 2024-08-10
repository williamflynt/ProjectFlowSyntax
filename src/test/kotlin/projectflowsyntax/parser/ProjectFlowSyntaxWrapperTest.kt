package projectflowsyntax.parser

import io.github.treesitter.ktreesitter.Language
import io.github.treesitter.ktreesitter.Parser

class ProjectFlowSyntaxWrapperTest {
    @org.junit.jupiter.api.Test
    fun getTSLanguage() {
        val language = Language(ProjectFlowSyntaxWrapper.getTSLanguage())
        val parser = Parser(language)
        val tree = parser.parse("A > B\nB > C")
        val rootNode = tree.rootNode

        assert(rootNode.type == "source")
        assert(rootNode.startPoint.column == 0.toUInt())
        assert(rootNode.endPoint.column == 5.toUInt())
    }
}
