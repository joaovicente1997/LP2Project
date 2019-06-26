package edu.ufp.esocial.api.util;

public class InvalidParsingStringException extends RuntimeException {
	private static final long serialVersionUID = 2474087220288863024L;

	public InvalidParsingStringException(String className) {
		super("Can't create object from class " + className + ". Not enough parameters in String.");
	}
}
