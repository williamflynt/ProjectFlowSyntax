import type { ValidationAcceptor, ValidationChecks } from 'langium';
import type { ProjectFlowSyntaxAstType, Line } from './generated/ast.js';
import type { ProjectFlowSyntaxServices } from './project-flow-syntax-module.js';

/**
 * Register custom validation checks.
 */
export function registerValidationChecks(services: ProjectFlowSyntaxServices) {
    const registry = services.validation.ValidationRegistry;
    const validator = services.validation.ProjectFlowSyntaxValidator;
    const checks: ValidationChecks<ProjectFlowSyntaxAstType> = {
        Line: validator.checkPersonStartsWithCapital
    };
    registry.register(checks, validator);
}

/**
 * Implementation of custom validations.
 */
export class ProjectFlowSyntaxValidator {

    checkPersonStartsWithCapital(line: Line, accept: ValidationAcceptor): void {
        if (line.$type) {
            const firstChar = line.$type.substring(0, 1);
            if (firstChar.toUpperCase() !== firstChar) {
                accept('warning', 'Person name should start with a capital.', { node: line, property: '$type' });
            }
        }
    }
}
