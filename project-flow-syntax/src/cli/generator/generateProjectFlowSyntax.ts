import {Project} from "../../language/generated/ast.js";
import {extractDestinationAndName} from "../cli-util.js";
import path from "node:path";
import {expandToNode, joinToNode, toString} from "langium/generate";
import fs from "node:fs";
import {astNodeToProjectFlowSyntax} from "./main.js";

/**
 * Rewrite the AST to Project Flow Syntax.
 * @param project
 * @param filePath
 * @param destination
 */
export function generateProjectFlowSyntax(project: Project, filePath: string, destination: string | undefined): string {
    const data = extractDestinationAndName(filePath, destination);
    const generatedFilePath = `${path.join(data.destination, data.name)}.pfs`;

    const fileNode = expandToNode`
        ${joinToNode(project.lines, line => astNodeToProjectFlowSyntax(line), {appendNewLineIfNotEmpty: true})}
    `.appendNewLineIfNotEmpty();

    if (!fs.existsSync(data.destination)) {
        fs.mkdirSync(data.destination, {recursive: true});
    }
    fs.writeFileSync(generatedFilePath, toString(fileNode));
    return generatedFilePath;
}