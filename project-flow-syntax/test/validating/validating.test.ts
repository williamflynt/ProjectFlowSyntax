import { beforeAll, describe, expect, test } from "vitest";
import { EmptyFileSystem, type LangiumDocument } from "langium";
import { expandToString as s } from "langium/generate";
import { parseHelper } from "langium/test";
import type { Diagnostic } from "vscode-languageserver-types";
import { createProjectFlowSyntaxServices } from "../../src/language/project-flow-syntax-module.js";
import { Project } from "../../src/language/generated/ast.js";
import { documentIsValid } from "../util.js";

let services: ReturnType<typeof createProjectFlowSyntaxServices>;
let parse:    ReturnType<typeof parseHelper<Project>>;
let document: LangiumDocument<Project> | undefined;

beforeAll(async () => {
    services = createProjectFlowSyntaxServices(EmptyFileSystem);
    const doParse = parseHelper<Project>(services.ProjectFlowSyntax);
    parse = (input: string) => doParse(input, { validation: true });
});

// TODO: Validate an example of all line types/parse rules with business logic that should be applied.
// TODO: Parse a document with cyclic deps and check for error messages on the right line.

describe('Validating', () => {
  
    test('check no errors', async () => {
        document = await parse(`
            person Langium
        `);

        expect(
            // here we first check for validity of the parsed document object by means of the reusable function
            //  'checkDocumentValid()' to sort out (critical) typos first,
            // and then evaluate the diagnostics by converting them into human readable strings;
            // note that 'toHaveLength()' works for arrays and strings alike ;-)
            documentIsValid(document) || document?.diagnostics?.map(diagnosticToString)?.join('\n')
        ).toHaveLength(0);
    });

    test('check capital letter validation', async () => {
        document = await parse(`
            person langium
        `);

        expect(
            documentIsValid(document) || document?.diagnostics?.map(diagnosticToString)?.join('\n')
        ).toEqual(
            // 'expect.stringContaining()' makes our test robust against future additions of further validation rules
            expect.stringContaining(s`
                [1:19..1:26]: Person name should start with a capital.
            `)
        );
    });
});

function diagnosticToString(d: Diagnostic) {
    return `[${d.range.start.line}:${d.range.start.character}..${d.range.end.line}:${d.range.end.character}]: ${d.message}`;
}
