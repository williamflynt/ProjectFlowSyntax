// Load the generated parser code.
load('src/main/resources/parser.js');
// Export required functions to the global scope.
globalThis.parse = async function(input) {
    return await globalThis.sourceToJsonAst(input);
};
