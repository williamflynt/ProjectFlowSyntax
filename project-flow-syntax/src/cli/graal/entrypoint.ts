// Expose to GraalJS.
import {syncParse} from "../syncParse.js";

(globalThis as any).sourceToJsonAst = syncParse;
