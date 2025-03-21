package projectflowsyntax.parser

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import java.io.File

class LangiumParser(jsLibPath: String) {
    // Create a GraalJS context.
    private val context: Context = Context.newBuilder("js")
        .build()
    // Cache the parse function.
    private val parseFunction: org.graalvm.polyglot.Value;

    init {
        val jsLib = File(jsLibPath)
        if (!jsLib.exists()) {
            throw IllegalArgumentException("JavaScript library not found at $jsLibPath")
        }
        val source = Source.newBuilder("js", jsLib).build()
        context.eval(source)
        parseFunction = context.getBindings("js").getMember("sourceToJsonAst")
    }

    fun parse(input: String): org.graalvm.polyglot.Value {
        return parseFunction.execute(input)
    }

    fun close() {
        context.close()
    }
}
