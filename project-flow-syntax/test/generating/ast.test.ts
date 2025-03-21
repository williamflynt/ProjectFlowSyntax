import {beforeAll, beforeEach, describe, expect, test} from "vitest";
import {EmptyFileSystem, type LangiumDocument} from "langium";
import {parseHelper} from "langium/test";
import {createProjectFlowSyntaxServices} from "../../src/language/project-flow-syntax-module.js";
import {Project} from "../../src/language/generated/ast.js";
import {documentIsValid, findPfsFiles} from "../util.js";
import {generateJsonAst} from "../../src/cli/generator/generateJsonAst.js";
import fs from "fs";

let samples: string[];
let services: ReturnType<typeof createProjectFlowSyntaxServices>;
let parse:    ReturnType<typeof parseHelper<Project>>;
let document: LangiumDocument<Project> | undefined;

beforeAll(async () => {
    samples = findPfsFiles('samples');
    services = createProjectFlowSyntaxServices(EmptyFileSystem);
    const doParse = parseHelper<Project>(services.ProjectFlowSyntax);
    parse = (input: string) => doParse(input, { validation: true });
});

beforeEach(async () => {
    services.ProjectFlowSyntax.validation.ProjectFlowSyntaxValidator.resetDependencyNodes()
})

describe('Generate AST', () => {
    test('test can generate AST for all samples without top level errors', async () => {
        for (const sample of samples) {
            const content = fs.readFileSync(sample).toString()
            document = await parse(content);
            const validity = documentIsValid(document);
            expect(validity.isValid).toStrictEqual(true);
            expect(validity.errors.length).toStrictEqual(0);
            const generated = generateJsonAst(document.parseResult.value);
            expect(generated.length).toBeGreaterThan(2); // "{}"
            const asMap = JSON.parse(generated);
            // Strip the comment lines and empty lines from the document before counting newlines.
            const documentNewLines = content.split('\n').filter(line => !line.startsWith('#') && line.trim().length > 0).length;
            const astLines = asMap.lines.length;
            expect(astLines).toStrictEqual(documentNewLines);
            console.info(`[OK] ${sample}`)
        }
    });
});
