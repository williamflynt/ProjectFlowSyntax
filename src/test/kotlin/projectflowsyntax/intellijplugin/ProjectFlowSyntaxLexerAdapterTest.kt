package projectflowsyntax.intellijplugin

import com.intellij.openapi.editor.ex.util.ValidatingLexerWrapper
import io.github.treesitter.ktreesitter.Node
import org.junit.jupiter.api.Test
import projectflowsyntax.parser.ProjectFlowSyntaxParser
import kotlin.io.path.Path
import kotlin.io.path.readBytes

val webAppPath = Path("src/test/resources/samples/webapp.pfs")
val webAppContent = webAppPath.readBytes().decodeToString()

class ProjectFlowSyntaxLexerAdapterTest {
    private val lexer = ProjectFlowSyntaxLexerAdapter()

    @Test
    fun start() {
        lexer.start(webAppContent)
        assert(lexer.bufferEnd == webAppContent.length)
    }

    @Test
    fun startWithOffset() {
        val wrapped = ValidatingLexerWrapper(lexer)
        wrapped.start(webAppContent, 225, webAppContent.length)
        assert(wrapped.tokenType == ProjectFlowSyntaxTypes.COMMENT_SIGIL)
    }

    @Test
    fun validatingLexerWrapper() {
        val wrapped = ValidatingLexerWrapper(lexer)
        wrapped.start(webAppContent, 0, webAppContent.length, 0)
        while (wrapped.currentPosition.offset < webAppContent.length) {
            wrapped.advance()
        }
    }
}

class ProjectFlowSyntaxLexerAdapterFindNextTokenTest {
    @Test
    fun findNextToken() {
        val tokens: MutableList<Node> = mutableListOf()
        val tree = ProjectFlowSyntaxParser.parse(webAppContent)
        var node: Node? = tree.rootNode
        var position = 0
        while (true) {
            node = findNextToken(node, position)
            if (node == null) {
                break
            }
            tokens.add(node)
            position = node.endByte.toInt()
        }
        // This is hardcoded and may change if the grammar test file changes.
        val testTokenSize = 754
        check(tokens.size == testTokenSize) { "expected $testTokenSize, got ${tokens.size}" }
    }
}
