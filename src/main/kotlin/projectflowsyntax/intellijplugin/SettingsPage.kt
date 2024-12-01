package projectflowsyntax.intellijplugin
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.IconLoader
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.Icon

internal class SettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon {
        return IconLoader.getIcon("/icons/projectFlowSyntaxIcon.svg", javaClass)
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return ProjectFlowSyntaxHighlighter()
    }

    @Suppress("LongMethod")
    override fun getDemoText(): String {
        return DemoText.content.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor> {
        return Util.DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "ProjectFlowSyntax"
    }

    object Util {
        val DESCRIPTORS =
            arrayOf(
                AttributesDescriptor("Comment", ProjectFlowSyntaxHighlighterKeys.COMMENT),
                AttributesDescriptor("Key value separator :", ProjectFlowSyntaxHighlighterKeys.KV_SEPARATOR),
                AttributesDescriptor("Attribute", ProjectFlowSyntaxHighlighterKeys.ATTRIBUTE_KEY),
                AttributesDescriptor("Quoted text", ProjectFlowSyntaxHighlighterKeys.QUOTED_VALUE),
                AttributesDescriptor("Value", ProjectFlowSyntaxHighlighterKeys.VALUE),
                AttributesDescriptor("Identifier", ProjectFlowSyntaxHighlighterKeys.IDENTIFIER),
                AttributesDescriptor("Sigil for Cluster @", ProjectFlowSyntaxHighlighterKeys.CLUSTER_SIGIL),
                AttributesDescriptor("Sigil for Comment #", ProjectFlowSyntaxHighlighterKeys.COMMENT_SIGIL),
                AttributesDescriptor("Sigil for Milestone %", ProjectFlowSyntaxHighlighterKeys.MILESTONE_SIGIL),
                AttributesDescriptor("Sigil for NewTask *", ProjectFlowSyntaxHighlighterKeys.NEW_TASK_SIGIL),
                AttributesDescriptor("Sigil for Resource $", ProjectFlowSyntaxHighlighterKeys.RESOURCE_SIGIL),
                AttributesDescriptor("Operator for Negation ~", ProjectFlowSyntaxHighlighterKeys.NEGATION_OP),
                AttributesDescriptor("Operator for Explode !", ProjectFlowSyntaxHighlighterKeys.EXPLODE_OP),
                AttributesDescriptor("Operator for Implode ~!", ProjectFlowSyntaxHighlighterKeys.IMPLODE_OP),
                AttributesDescriptor("Operator for RequiredBy >", ProjectFlowSyntaxHighlighterKeys.REQUIRED_BY_OP),
                AttributesDescriptor("Separator ,", ProjectFlowSyntaxHighlighterKeys.SEPARATOR),
                AttributesDescriptor("Bracket left (", ProjectFlowSyntaxHighlighterKeys.LEFT_BRACKET),
                AttributesDescriptor("Bracket right )", ProjectFlowSyntaxHighlighterKeys.RIGHT_BRACKET),
                AttributesDescriptor("Double quotes \"", ProjectFlowSyntaxHighlighterKeys.DOUBLE_QUOTE),
                AttributesDescriptor("Number 0-9", ProjectFlowSyntaxHighlighterKeys.NUMBER),
                AttributesDescriptor("Whitespace", ProjectFlowSyntaxHighlighterKeys.WHITESPACE),
                AttributesDescriptor("Bad character", ProjectFlowSyntaxHighlighterKeys.BAD_CHARACTER),
            )
    }
}

object DemoText {
    @JvmStatic
    val content: String = String(Files.readAllBytes(Paths.get("src/main/resources/DemoText.pfs")))
}