package com.bancvue.exception.mapper;

import com.bancvue.rest.Envelope;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;

@Provider
public class InvalidEntityExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		Envelope<List<ValidationError>> envelope =
				new Envelope.Builder<>(ValidationHelper.constraintViolationToValidationErrors(exception)).build();
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(envelope).build();
	}
}
