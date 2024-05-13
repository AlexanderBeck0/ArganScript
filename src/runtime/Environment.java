package runtime;

import java.util.HashMap;

public class Environment {
	private final Environment parent;
	private final HashMap<String, RuntimeValue> variables;

	public Environment() {
		this(null);
	}

	public Environment(Environment parent) {
		this.parent = parent;
		this.variables = new HashMap<>();
	}

	/**
	 * Declares a variable.
	 *
	 * @param name  The name of the variable
	 * @param value The value of the variable
	 * @return The created variable
	 * @implNote Does not check for {@code name} in any parent {@linkplain Environment Environments}
	 * @see #assignVariable(String, RuntimeValue)
	 * @see #lookupVariable(String) 
	 */
	public RuntimeValue declareVariable(String name, RuntimeValue value) {
		// TODO: Add name regexp checking
		if (this.variables.containsKey(name)) {
			System.err.println("Variable" + name + " already exists!");
			System.exit(1);
		}
		this.variables.put(name, value);
		return value;
	}

	/**
	 * Sets a variables value to {@code value}
	 *
	 * @param name  The name of the variable
	 * @param value The value of the variable
	 * @return The new variable value
	 * @see #declareVariable(String, RuntimeValue)
	 * @see #lookupVariable(String) 
	 */
	public RuntimeValue assignVariable(String name, RuntimeValue value) {
		Environment resolvedEnvironment = resolve(name);

		resolvedEnvironment.variables.put(name, value);
		return value;
	}

	/**
	 * Gets the variable {@code name}
	 *
	 * @param name  The name of the variable
	 * @return {@code name}'s value, or null if it does not exist
	 * @see #declareVariable(String, RuntimeValue)
	 * @see #assignVariable(String, RuntimeValue) 
	 */
	public RuntimeValue lookupVariable(String name) {
		Environment resolvedEnvironment = resolve(name);
		if (resolvedEnvironment != null) {
			return resolvedEnvironment.variables.get(name);
		}
		return new NullValue();
	}

	/**
	 * Recursively resolves the {@linkplain Environment} that contains the variable {@code name}
	 *
	 * @param name The name of the variable to resolve
	 * @return The {@linkplain Environment} that contains {@code name}
	 * @implNote Checks locally first, then checks parents
	 */
	public Environment resolve(String name) {
		// Assumes that the variable must already exist.
		// Should this be changed in the future, do not check if it exists locally, however you must check if it exists in the parent.
		if (this.variables.containsKey(name)) {
			return this;
		}
		if (this.parent == null) {
			System.err.println("Variable " + name + " does not exist!");
			System.exit(1);
		}
		return this.parent.resolve(name);
	}
}
