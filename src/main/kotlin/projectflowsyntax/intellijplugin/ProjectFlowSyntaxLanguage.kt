package projectflowsyntax.intellijplugin

import com.intellij.lang.Language

object ProjectFlowSyntaxLanguage : Language("ProjectFlowSyntax") {
    @Suppress("UnusedPrivateMember")
    private fun readResolve(): Any = ProjectFlowSyntaxLanguage
}
