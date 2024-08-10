package projectflowsyntax.intellijplugin

import com.intellij.lexer.LexerBase
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.tree.IElementType
import io.github.treesitter.ktreesitter.Node
import io.github.treesitter.ktreesitter.Tree
import projectflowsyntax.parser.ProjectFlowSyntaxParser

class ProjectFlowSyntaxLexerAdapter : LexerBase() {
    private val log = Logger.getInstance(ProjectFlowSyntaxLexerAdapter::class.java)

    private var buffer: CharSequence = ""
    private var startOffset: Int = 0
    private var endOffset: Int = 0
    private var currentPosition: Int = 0
    private var currentToken: Node? = null

    private lateinit var tree: Tree

    override fun start(
        buffer: CharSequence,
        startOffset: Int,
        endOffset: Int,
        initialState: Int,
    ) {
        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        this.currentPosition = startOffset

        tree = ProjectFlowSyntaxParser.parse(buffer.toString())
        currentToken = findNextToken(tree.rootNode, 0)
        while (currentToken != null && currentToken!!.startByte < startOffset.toUInt()) {
            currentToken = findNextToken(currentToken, currentToken!!.endByte.toInt())
        }

        log.debug("Lexer started:", "startOffset=$startOffset, endOffset=$endOffset")
    }

    override fun getState(): Int = 0

    override fun getTokenType(): IElementType? = currentToken?.let { node -> nodeTypeToType(node.type) }

    override fun getTokenStart(): Int = currentToken?.startByte?.toInt() ?: currentPosition

    override fun getTokenEnd(): Int = currentToken?.endByte?.toInt() ?: currentPosition

    override fun advance() {
        if (currentToken == null) return

        val previousPosition = currentPosition
        currentPosition = currentToken!!.endByte.toInt()
        currentToken = findNextToken(currentToken, currentPosition)

        log.debug(
            "advance():",
            "previousPosition=$previousPosition, newPosition=$currentPosition, currentToken=$currentToken",
        )
    }

    override fun getBufferSequence(): CharSequence = buffer

    override fun getBufferEnd(): Int = endOffset
}

@Suppress("ReturnCount")
fun findNextToken(
    node: Node?,
    position: Int,
): Node? {
    if (node == null) return null

    // Traverse down to the first leaf using the nextChild extension method
    var child = node.nextChild(position)
    while (child != null && child.startByte.toInt() <= position && child.endByte.toInt() > position) {
        val found = findNextToken(child, position)
        if (found != null) return found
        child = child.nextSibling
    }

    if (node.startByte.toInt() == position && node.endByte.toInt() > position) {
        return node
    }

    // Traverse siblings
    var sibling = node.nextSibling
    while (sibling != null) {
        val found = findNextToken(sibling, position)
        if (found != null) return found
        sibling = sibling.nextSibling
    }

    // Traverse back up to the parent
    var parent = node.parent
    while (parent != null) {
        if (parent.endByte.toInt() > position) {
            return findNextToken(parent, position)
        }
        parent = parent.parent
    }

    return null
}

fun Node.nextChild(position: Int): Node? {
    for (i in 0 until childCount.toInt()) {
        val child = child(i.toUInt())
        if (child != null && child.startByte.toInt() >= position) {
            return child
        }
    }
    return null
}
