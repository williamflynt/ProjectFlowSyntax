// Expose to GraalJS.
import {sourceToJsonAst} from "../parser.js";

(globalThis as any).sourceToJsonAst = sourceToJsonAst;
