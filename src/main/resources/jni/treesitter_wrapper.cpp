#include "treesitter_wrapper.h"
#include "tree_sitter/api.h"

extern "C" TSLanguage *tree_sitter_project_flow_syntax();

JNIEXPORT jlong JNICALL Java_projectflowsyntax_parser_ProjectFlowSyntaxWrapper_getTSLanguage(JNIEnv *env, jobject obj) {
    return (jlong) tree_sitter_project_flow_syntax();
}
