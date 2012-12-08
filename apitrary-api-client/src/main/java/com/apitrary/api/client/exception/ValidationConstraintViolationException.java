package com.apitrary.api.client.exception;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class ValidationConstraintViolationException extends ClientException {
	private static final long serialVersionUID = 8653296771499098292L;

	private static String lineSeparator = System.getProperty("line.separator");
	private List<ConstraintViolation> constraintViolations = new LinkedList<ConstraintViolation>();

	/**
	 * <p>
	 * Constructor for ValidationConstraintViolationException.
	 * </p>
	 *
	 * @param th
	 *            a {@link java.lang.Throwable} object.
	 */
	public ValidationConstraintViolationException(Throwable th) {
		super(th);
	}

	/**
	 * <p>
	 * Constructor for ValidationConstraintViolationException.
	 * </p>
	 *
	 * @param violations
	 *            a {@link java.util.List} object.
	 */
	public ValidationConstraintViolationException(List<ConstraintViolation> violations) {
		super(inquireMessage(violations));
		this.constraintViolations = violations;
	}

	/**
	 * <p>
	 * Constructor for ValidationConstraintViolationException.
	 * </p>
	 *
	 * @param pre
	 *            a {@link java.lang.String} object.
	 * @param violations
	 *            a {@link java.util.List} object.
	 */
	public ValidationConstraintViolationException(String pre, List<ConstraintViolation> violations) {
		super(concat(pre, inquireMessage(violations)));
		this.constraintViolations = violations;
	}

	/**
	 * <p>
	 * Getter for the field <code>constraintViolations</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ConstraintViolation> getConstraintViolations() {
		return constraintViolations;
	}

	/**
	 * 
	 * @param violations
	 * @return
	 */
	private static String inquireMessage(List<ConstraintViolation> violations) {
		String message = null;
		for (ConstraintViolation constraintViolation : violations) {
			message = concat(message, constraintViolation.toString());
		}
		return message;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static String concat(String a, String b) {
		return (a != null && !a.isEmpty() ? a + lineSeparator : "") + b;
	}

	/**
	 * 
	 * @author Denis Neuling (denisneuling@gmail.com)
	 */
	public static class ConstraintViolation {
		private String hint;
		private Field field;

		private ConstraintViolation(String hint, Field field) {
			this.hint = hint;
			this.field = field;
		}

		/**
		 * 
		 * @param hint
		 * @param field
		 * @return
		 */
		public static ConstraintViolation newConstraintViolation(String hint, Field field) {
			return new ConstraintViolation(hint, field);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "\t" + hint + " " + field;
		}
	}
}