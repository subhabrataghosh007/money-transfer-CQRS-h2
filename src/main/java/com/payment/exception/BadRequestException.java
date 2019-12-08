package com.payment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.payment.dto.ErrorResponse;

@Provider
public class BadRequestException extends RuntimeException implements ExceptionMapper<BadRequestException> {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(BadRequestException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), Status.BAD_REQUEST.getStatusCode());
		return Response.status(Status.BAD_REQUEST).entity(errorResponse).build();
	}

}
