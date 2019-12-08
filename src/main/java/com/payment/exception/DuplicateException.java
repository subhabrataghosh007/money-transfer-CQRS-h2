package com.payment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.payment.dto.ErrorResponse;

@Provider
public class DuplicateException extends RuntimeException implements ExceptionMapper<DuplicateException> {

	
	private static final long serialVersionUID = 1L;

	public DuplicateException() {
		super();
	}

	public DuplicateException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(DuplicateException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), Status.CONFLICT.getStatusCode());
		return Response.status(Status.CONFLICT).entity(errorResponse).build();
	}

}
