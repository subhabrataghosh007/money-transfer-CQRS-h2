package com.payment.validation;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.payment.exception.BadRequestException;

public class CustomeValidation {

	public static <T> void validateWallet(T obj) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> errors = validator.validate(obj);
		
		if (errors.size() > 0) {
			String message = errors.stream().map(m -> m.getMessage()).collect(Collectors.joining(" || && || "));

			throw new BadRequestException(message);
		}
	}
}
