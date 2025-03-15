import {LangiumDocument} from "langium";
import {expandToString as s} from "langium/generate";
import {isProject, Project} from "../src/language/generated/ast.js";

export const checkDocumentValid = (document: LangiumDocument): string | undefined => {
    return document.parseResult.parserErrors.length && s`
        Parser errors:
          ${document.parseResult.parserErrors.map(e => e.message).join('\n  ')}
    `
        || document.parseResult.value === undefined && `ParseResult is 'undefined'.`
        || !isProject(document.parseResult.value) && `Root AST object is a ${document.parseResult.value.$type}, expected a '${Project}'.`
        || undefined;
}
