package ch.hevs.exception;

public class FootballException extends RuntimeException {

	public FootballException() {
		super();
	}

	public FootballException(String arg0) {
		super(arg0);
	}

	public FootballException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FootballException(Throwable arg0) {
		super(arg0);
	}

}
