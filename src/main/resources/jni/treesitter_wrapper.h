#ifndef TREE_SITTER_WRAPPER_H
#define TREE_SITTER_WRAPPER_H

#include <jni.h>
#include "tree_sitter/api.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL Java_projectflowsyntax_parser_ProjectFlowSyntaxWrapper_getTSLanguage(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif

#endif
