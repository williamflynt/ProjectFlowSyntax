package projectflowsyntax.parser

object ProjectFlowSyntaxWrapper {
    init {
        System.loadLibrary("treesitter_wrapper")
    }

    external fun getTSLanguage(): Long
}
