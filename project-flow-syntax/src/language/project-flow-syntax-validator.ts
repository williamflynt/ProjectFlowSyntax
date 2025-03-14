import type { ValidationAcceptor, ValidationChecks } from 'langium';
import type { ProjectFlowSyntaxAstType, Person } from './generated/ast.js';
import type { ProjectFlowSyntaxServices } from './project-flow-syntax-module.js';

/**
 * Register custom validation checks.
 */
export function registerValidationChecks(services: ProjectFlowSyntaxServices) {
    const registry = services.validation.ValidationRegistry;
    const validator = services.validation.ProjectFlowSyntaxValidator;
    const checks: ValidationChecks<ProjectFlowSyntaxAstType> = {
        Person: validator.checkPersonStartsWithCapital
    };
    registry.register(checks, validator);
}

/**
 * Implementation of custom validations.
 */
export class ProjectFlowSyntaxValidator {

    checkPersonStartsWithCapital(person: Person, accept: ValidationAcceptor): void {
        if (person.name) {
            const firstChar = person.name.substring(0, 1);
            if (firstChar.toUpperCase() !== firstChar) {
                accept('warning', 'Person name should start with a capital.', { node: person, property: 'name' });
            }
        }
    }

}
