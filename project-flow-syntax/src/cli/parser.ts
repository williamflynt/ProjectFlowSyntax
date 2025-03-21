import {createProjectFlowSyntaxServices} from "../language/project-flow-syntax-module.js";
import {EmptyFileSystem} from "langium";
import {generateJsonAst} from "./generator/generateJsonAst.js";
import {Project} from "../language/generated/ast.js";

const services = createProjectFlowSyntaxServices({...EmptyFileSystem});
const parser = services.ProjectFlowSyntax.parser.LangiumParser;

// Convert source to JSON AST - synchronous version without validations.
export const sourceToJsonAst = (source: string): string => {
    const project = parser.parse<Project>(source);
    if (project.parserErrors?.length > 0) {
        return JSON.stringify({errors: project.parserErrors});
    }
    return generateJsonAst(project.value)
};
