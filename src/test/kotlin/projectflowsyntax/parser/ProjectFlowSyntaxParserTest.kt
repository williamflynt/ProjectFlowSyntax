package projectflowsyntax.parser

import kotlin.io.path.Path
import kotlin.io.path.readBytes

class ProjectFlowSyntaxParserTest {
    val webAppPath = Path("src/test/resources/samples/webapp.pfs")
    val webAppContent = webAppPath.readBytes().decodeToString()

    @org.junit.jupiter.api.Test
    fun parseWebApp() {
        val tree = ProjectFlowSyntaxParser.parse(webAppContent)
        val rootNode = tree.rootNode

        println(rootNode.type)
        println(rootNode.startPoint.column)
        println(rootNode.endPoint.column)

        assert(rootNode.type == "source")
        assert(rootNode.startPoint.column == 0.toUInt())
        assert(rootNode.endPoint.column == 0.toUInt()) // Ends in newline.

        val cursor = tree.walk()
        assert(cursor.currentNode.type != "ERROR")
    }

    @org.junit.jupiter.api.Test
    fun parseExplodeStatement() {
        val srcLine = "MyId ! 5"
        val tree = ProjectFlowSyntaxParser.parse(srcLine)
        val rootNode = tree.rootNode

        println(rootNode.type)
        println(rootNode.startPoint.column)
        println(rootNode.endPoint.column)

        assert(rootNode.type == "source")
        assert(rootNode.startPoint.column == 0.toUInt())
        assert(rootNode.endPoint.column == srcLine.length.toUInt()) // Is length of source snippet.

        val opType = rootNode.children[0].children[0].children[0].children[2].type
        assert(opType == "explode_op")

        val cursor = tree.walk()
        assert(cursor.currentNode.type != "ERROR")
    }
}
