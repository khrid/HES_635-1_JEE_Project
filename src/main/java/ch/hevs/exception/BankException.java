package ch.hevs.exception;

public class BankException extends RuntimeException {

	public BankException() {
		super();
	}

	public BankException(String arg0) {
		super(arg0);
	}

	public BankException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BankException(Throwable arg0) {
		super(arg0);
	}

}
