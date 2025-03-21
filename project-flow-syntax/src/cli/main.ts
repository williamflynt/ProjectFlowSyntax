import type { Project } from '../language/generated/ast.js';
import chalk from 'chalk';
import { Command } from 'commander';
import { ProjectFlowSyntaxLanguageMetaData } from '../language/generated/module.js';
import { createProjectFlowSyntaxServices } from '../language/project-flow-syntax-module.js';
import { extractAstNode } from './cli-util.js';
import { NodeFileSystem } from 'langium/node';
import * as url from 'node:url';
import * as fs from 'node:fs/promises';
import * as path from 'node:path';
import {generateJsonAst} from "./generator/generateJsonAst.js";
const __dirname = url.fileURLToPath(new URL('.', import.meta.url));

const packagePath = path.resolve(__dirname, '..', '..', 'package.json');
const packageContent = await fs.readFile(packagePath, 'utf-8');

export const generateAction = async (fileName: string, opts: GenerateOptions): Promise<void> => {
    const services = createProjectFlowSyntaxServices(NodeFileSystem).ProjectFlowSyntax;
    const model = await extractAstNode<Project>(fileName, services);
    const jsonAstString = generateJsonAst(model);
    console.log(chalk.green(`JavaScript code generated successfully!`));
    console.log(jsonAstString);
};

export type GenerateOptions = {
    destination?: string;
}

export default function(): void {
    const program = new Command();

    program.version(JSON.parse(packageContent).version);

    const fileExtensions = ProjectFlowSyntaxLanguageMetaData.fileExtensions.join(', ');
    program
        .command('generate')
        .argument('<file>', `source file (possible file extensions: ${fileExtensions})`)
        .option('-d, --destination <dir>', 'destination directory of generating')
        .description('generates an AST representation of the source file in JSON format and prints to console')
        .action(generateAction);

    program.parse(process.argv);
}
