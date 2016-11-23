package com.newmoneyrest.ResourceBuilder.error;

public class CustomExceptions {
	  @SuppressWarnings("serial")
	  public static class InputException extends Exception {
	    public InputException(String message) {
	    super(message);
	    }
	  }

	  @SuppressWarnings("serial")
	public static class NetworkError extends Exception {
	    public NetworkError(String message) {
	      super(message);
	    }
	  }
	  
	  @SuppressWarnings("serial")
	  public static class StockError extends Exception {
	    public StockError(String message) {
	      super(message);
	    }
	  }
	  
	  @SuppressWarnings("serial")
	  public static class ValueNotFoundException extends Exception {
	    public ValueNotFoundException(String message) {
	      super(message);
	    }
	  }
}
