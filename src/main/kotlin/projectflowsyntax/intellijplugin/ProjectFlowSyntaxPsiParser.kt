package projectflowsyntax.intellijplugin

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType
import io.github.treesitter.ktreesitter.Node
import projectflowsyntax.parser.ProjectFlowSyntaxParser

class ProjectFlowSyntaxPsiParser : PsiParser {
    override fun parse(
        root: IElementType,
        builder: PsiBuilder,
    ): ASTNode {
        val marker = builder.mark()

        val tree = ProjectFlowSyntaxParser.parse(builder.originalText.toString(), null)
        val rootNode = tree.rootNode

        parseNode(rootNode, builder)

        while (!builder.eof()) {
            builder.advanceLexer()
        }

        marker.done(root)
        return builder.treeBuilt
    }

    private fun parseNode(
        node: Node,
        builder: PsiBuilder,
    ) {
        val marker = builder.mark()
        builder.advanceLexer()

        for (child in node.children) {
            parseNode(child, builder)
        }

        marker.done(getElementType(node))
    }

    private fun getElementType(node: Node): IElementType = nodeTypeToType(node.type)
}
