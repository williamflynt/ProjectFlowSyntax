package projectflowsyntax.intellijplugin

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class ProjectFlowSyntaxParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = ProjectFlowSyntaxLexerAdapter()

    override fun createParser(project: Project?): PsiParser = ProjectFlowSyntaxPsiParser()

    override fun getFileNodeType(): IFileElementType = ProjectFlowSyntaxFileElementType

    override fun getCommentTokens(): TokenSet = TokenSet.create(ProjectFlowSyntaxTypes.COMMENT)

    override fun getStringLiteralElements(): TokenSet = TokenSet.create(ProjectFlowSyntaxTypes.STRING)

    override fun createElement(node: ASTNode): PsiElement = ProjectFlowSyntaxTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = ProjectFlowSyntaxFile(viewProvider)

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("ParserDefinition.SpaceRequirements.MAY", "com.intellij.lang.ParserDefinition"),
    )
    override fun spaceExistanceTypeBetweenTokens(
        left: ASTNode?,
        right: ASTNode?,
    ): ParserDefinition.SpaceRequirements = ParserDefinition.SpaceRequirements.MAY

    override fun getWhitespaceTokens(): TokenSet = TokenSet.create(TokenType.WHITE_SPACE)
}
