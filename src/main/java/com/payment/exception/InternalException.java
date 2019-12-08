package com.payment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.payment.dto.ErrorResponse;

@Provider
public class InternalException extends RuntimeException implements ExceptionMapper<InternalException> {

	
	private static final long serialVersionUID = 1L;

	public InternalException() {
		super();
	}

	public InternalException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(InternalException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	}

}
