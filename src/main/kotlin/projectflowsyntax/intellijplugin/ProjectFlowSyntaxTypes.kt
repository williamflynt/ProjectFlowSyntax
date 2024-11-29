package projectflowsyntax.intellijplugin

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType

object ProjectFlowSyntaxTypes {
    val SOURCE: IElementType = ProjectFlowSyntaxElementType("SOURCE")
    val COMMANDS: IElementType = ProjectFlowSyntaxElementType("COMMANDS")
    val INTERACTIVE_SESSION: IElementType = ProjectFlowSyntaxElementType("INTERACTIVE_SESSION")
    val COMMAND: IElementType = ProjectFlowSyntaxElementType("COMMAND")
    val COMMENT: IElementType = ProjectFlowSyntaxElementType("COMMENT")
    val COMMENT_TEXT: IElementType = ProjectFlowSyntaxElementType("COMMENT_TEXT")
    val DEPENDENCY: IElementType = ProjectFlowSyntaxElementType("DEPENDENCY")
    val TASK_SPLIT_OPERATION: IElementType = ProjectFlowSyntaxElementType("TASK_SPLIT_OPERATION")
    val ENTITY_CREATE_OR_UPDATE: IElementType = ProjectFlowSyntaxElementType("ENTITY_CREATE_OR_UPDATE")
    val ENTITY_REMOVE: IElementType = ProjectFlowSyntaxElementType("ENTITY_REMOVE")
    val TASK_EXPLODE_IMPLODE: IElementType = ProjectFlowSyntaxElementType("TASK_EXPLODE_IMPLODE")
    val CLUSTER_OPERATION: IElementType = ProjectFlowSyntaxElementType("CLUSTER_OPERAITON")
    val RESOURCE_ASSIGNMENT: IElementType = ProjectFlowSyntaxElementType("RESOURCE_ASSIGNMENT")
    val ENTITY: IElementType = ProjectFlowSyntaxElementType("ENTITY")
    val TASKS: IElementType = ProjectFlowSyntaxElementType("TASKS")
    val TASK: IElementType = ProjectFlowSyntaxElementType("TASK")
    val MILESTONE: IElementType = ProjectFlowSyntaxElementType("MILESTONE")
    val RESOURCE: IElementType = ProjectFlowSyntaxElementType("RESOURCE")
    val RESOURCES: IElementType = ProjectFlowSyntaxElementType("RESOURCES")
    val ATTRIBUTES: IElementType = ProjectFlowSyntaxElementType("ATTRIBUTES")
    val ATTRIBUTE: IElementType = ProjectFlowSyntaxElementType("ATTRIBUTE")
    val ATTRIBUTE_KEY: IElementType = ProjectFlowSyntaxElementType("ATTRIBUTE_KEY")
    val QUOTED_VALUE: IElementType = ProjectFlowSyntaxElementType("QUOTED_VALUE")
    val VALUE: IElementType = ProjectFlowSyntaxElementType("VALUE")
    val VALUE_BETWEEN_QUOTES: IElementType = ProjectFlowSyntaxElementType("VALUE_BETWEEN_QUOTES")
    val RESPONSE: IElementType = ProjectFlowSyntaxElementType("RESPONSE")
    val IDENTIFIER: IElementType = ProjectFlowSyntaxElementType("IDENTIFIER")
    val CLUSTER_SIGIL: IElementType = ProjectFlowSyntaxElementType("CLUSTER_SIGIL")
    val COMMENT_SIGIL: IElementType = ProjectFlowSyntaxElementType("COMMENT_SIGIL")
    val MILESTONE_SIGIL: IElementType = ProjectFlowSyntaxElementType("MILESTONE_SIGIL")
    val NEW_TASK_SIGIL: IElementType = ProjectFlowSyntaxElementType("NEW_TASK_SIGIL")
    val RESOURCE_SIGIL: IElementType = ProjectFlowSyntaxElementType("RESOURCE_SIGIL")
    val EXPLODE_OP: IElementType = ProjectFlowSyntaxElementType("EXPLODE_OP")
    val IMPLODE_OP: IElementType = ProjectFlowSyntaxElementType("IMPLODE_OP")
    val REQUIRED_BY_OP: IElementType = ProjectFlowSyntaxElementType("REQUIRED_BY_OP")
    val NEGATION_OP: IElementType = ProjectFlowSyntaxElementType("NEGATION_OP")
    val KV_SEPARATOR: IElementType = ProjectFlowSyntaxElementType("KV_SEPARATOR")
    val SEPARATOR: IElementType = ProjectFlowSyntaxElementType("SEPARATOR")
    val WHITESPACE: IElementType = ProjectFlowSyntaxElementType("WHITESPACE")
    val NEWLINE: IElementType = ProjectFlowSyntaxElementType("NEWLINE")
    val LEFT_BRACKET: IElementType = ProjectFlowSyntaxElementType("LEFT_BRACKET")
    val RIGHT_BRACKET: IElementType = ProjectFlowSyntaxElementType("RIGHT_BRACKET")
    val DOUBLE_QUOTE: IElementType = ProjectFlowSyntaxElementType("DOUBLE_QUOTE")
    val NUMBER: IElementType = ProjectFlowSyntaxElementType("NUMBER")

    val STRING: IElementType = ProjectFlowSyntaxElementType("STRING") // Not in the grammar.
    val UNKNOWN: IElementType = ProjectFlowSyntaxElementType("UNKNOWN") // Not in the grammar.

    object Factory {
        fun createElement(node: ASTNode): PsiElement = ProjectFlowSyntaxPsiElement(node)
    }
}

class ProjectFlowSyntaxElementType(
    debugName: String,
) : IElementType(debugName, ProjectFlowSyntaxLanguage)

open class ProjectFlowSyntaxPsiElement(
    node: ASTNode,
) : ASTWrapperPsiElement(node)

@Suppress("CyclomaticComplexMethod")
fun nodeTypeToType(t: String): IElementType =
    when (t) {
        "ERROR" -> ProjectFlowSyntaxTypes.UNKNOWN

        "comment" -> ProjectFlowSyntaxTypes.COMMENT
        "comment_text" -> ProjectFlowSyntaxTypes.COMMENT_TEXT
        "attribute_key" -> ProjectFlowSyntaxTypes.ATTRIBUTE_KEY
        "kv_separator" -> ProjectFlowSyntaxTypes.KV_SEPARATOR
        "quoted_value" -> ProjectFlowSyntaxTypes.QUOTED_VALUE
        "value" -> ProjectFlowSyntaxTypes.VALUE
        "value_between_quotes" -> ProjectFlowSyntaxTypes.VALUE_BETWEEN_QUOTES
        "identifier" -> ProjectFlowSyntaxTypes.IDENTIFIER
        "cluster_sigil" -> ProjectFlowSyntaxTypes.CLUSTER_SIGIL
        "comment_sigil" -> ProjectFlowSyntaxTypes.COMMENT_SIGIL
        "milestone_sigil" -> ProjectFlowSyntaxTypes.MILESTONE_SIGIL
        "new_task_sigil" -> ProjectFlowSyntaxTypes.NEW_TASK_SIGIL
        "resource_sigil" -> ProjectFlowSyntaxTypes.RESOURCE_SIGIL
        "implode_op" -> ProjectFlowSyntaxTypes.IMPLODE_OP
        "explode_op" -> ProjectFlowSyntaxTypes.EXPLODE_OP
        "required_by_op" -> ProjectFlowSyntaxTypes.REQUIRED_BY_OP
        "negation_op" -> ProjectFlowSyntaxTypes.NEGATION_OP
        "separator" -> ProjectFlowSyntaxTypes.SEPARATOR
        "left_bracket" -> ProjectFlowSyntaxTypes.LEFT_BRACKET
        "right_bracket" -> ProjectFlowSyntaxTypes.RIGHT_BRACKET
        "double_quote" -> ProjectFlowSyntaxTypes.DOUBLE_QUOTE
        "number" -> ProjectFlowSyntaxTypes.NUMBER
        "whitespace" -> ProjectFlowSyntaxTypes.WHITESPACE

        else -> ProjectFlowSyntaxTypes.UNKNOWN
    }
