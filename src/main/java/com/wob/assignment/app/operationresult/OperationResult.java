package com.wob.assignment.app.operationresult;

/**
 * Generic base class for operation results.
 * 
 * @author matezoltankiss
 *
 * @param <T> type of the result of the operation
 */
public class OperationResult<T> {

	private final boolean isSucces;
	private final String message;
	private final T result;

	public OperationResult(final boolean isSuccess, final String message, final T value) {
		this.isSucces = isSuccess;
		this.message = message;
		this.result = value;
	}

	public boolean isSuccess() {
		return this.isSucces;
	}

	public String getMessage() {
		return this.message;
	}

	public T getResult() {
		return this.result;
	}

}
