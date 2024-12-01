#!/usr/bin/env bash

BASE_DIR=$(dirname "$0")
BASE_DIR=$(realpath "$BASE_DIR")

cd "$BASE_DIR/../grammar" &&
tree-sitter parse "$BASE_DIR/../src/test/resources/samples/webapp.pfs"