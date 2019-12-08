package com.payment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.payment.dto.ErrorResponse;

@Provider
public class NotFoundException extends RuntimeException implements ExceptionMapper<NotFoundException> {

	private static final long serialVersionUID = 1L;


	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(NotFoundException exception) {

		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), Status.NOT_FOUND.getStatusCode());
		return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
	}

}
