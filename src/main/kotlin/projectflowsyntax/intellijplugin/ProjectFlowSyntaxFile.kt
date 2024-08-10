package projectflowsyntax.intellijplugin

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.tree.IFileElementType
import javax.swing.Icon

class ProjectFlowSyntaxFile(
    viewProvider: FileViewProvider,
) : PsiFileBase(viewProvider, ProjectFlowSyntaxLanguage) {
    override fun getFileType() = ProjectFlowSyntaxFileType()

    override fun toString() = "Project Flow Syntax file"
}

class ProjectFlowSyntaxFileType : LanguageFileType(ProjectFlowSyntaxLanguage) {
    override fun getName() = "Project Flow Syntax file"

    override fun getDescription() = "The file type for PFS project description language."

    override fun getDefaultExtension() = "pfs"

    override fun getIcon(): Icon = ProjectFlowSyntaxIcons.FILE
}

object ProjectFlowSyntaxFileElementType : IFileElementType(ProjectFlowSyntaxLanguage)
