import { beforeAll, describe, expect, test } from "vitest";
import { EmptyFileSystem, type LangiumDocument } from "langium";
import { expandToString as s } from "langium/generate";
import { parseHelper } from "langium/test";
import { createProjectFlowSyntaxServices } from "../../src/language/project-flow-syntax-module.js";
import { Project } from "../../src/language/generated/ast.js";
import { checkDocumentValid } from "../util.js";

let services: ReturnType<typeof createProjectFlowSyntaxServices>;
let parse:    ReturnType<typeof parseHelper<Project>>;
let document: LangiumDocument<Project> | undefined;

beforeAll(async () => {
    services = createProjectFlowSyntaxServices(EmptyFileSystem);
    parse = parseHelper<Project>(services.ProjectFlowSyntax);

    // activate the following if your linking test requires elements from a built-in library, for example
    // await services.shared.workspace.WorkspaceManager.initializeWorkspace([]);
});

describe('Parsing tests', () => {

    test('parse simple model', async () => {
        document = await parse(`
            person Langium
            Hello Langium!
        `);

        // check for absence of parser errors the classic way:
        //  deactivated, find a much more human readable way below!
        // expect(document.parseResult.parserErrors).toHaveLength(0);

        expect(
            // here we use a (tagged) template expression to create a human readable representation
            //  of the AST part we are interested in and that is to be compared to our expectation;
            // prior to the tagged template expression we check for validity of the parsed document object
            //  by means of the reusable function 'checkDocumentValid()' to sort out (critical) typos first;
            checkDocumentValid(document) || s`
                Persons:
                  ${document.parseResult.value?.lines?.map(p => p.$type)?.join('\n  ')}
                Greetings to:
                  ${document.parseResult.value?.lines?.map(g => g.$type)?.join('\n  ')}
            `
        ).toBe(s`
            Persons:
              Langium
            Greetings to:
              Langium
        `);
    });
});
