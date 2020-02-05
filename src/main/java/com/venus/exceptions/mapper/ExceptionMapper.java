package com.venus.exceptions.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.validation.FieldError;

import com.venus.config.GlobalMapperConfig;
import com.venus.exceptions.VenusException;
import com.venus.exceptions.dto.ErrorCodeResponse;
import com.venus.exceptions.dto.ErrorResponse;
import com.venus.exceptions.dto.ValidationErrorResponse;
import com.venus.exceptions.enums.ErrorCode;

@Mapper(config = GlobalMapperConfig.class)
public interface ExceptionMapper {

    @Mapping(target = "code", qualifiedByName = "mapCode")
    @Mapping(target = "validationErrors", ignore = true)
    ErrorResponse mapOne(VenusException ex);

    default ErrorResponse mapOne(ErrorCode code, String message) {
        return mapOne(code, message, null);
    }

    default ErrorResponse mapOne(ErrorCode code, String message, List<FieldError> fieldErrors) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(mapCode(code));
        response.setMessage(message);
        response.setValidationErrors(mapValidationErrors(fieldErrors));
        return response;
    }

    @Named("mapCode")
    default ErrorCodeResponse mapCode(ErrorCode code) {
        ErrorCode c = code != null ? code : ErrorCode.UNSPECIFIED;
        return new ErrorCodeResponse(c.name(), c.getValue());
    }

    default List<ValidationErrorResponse> mapValidationErrors(List<FieldError> errors) {
        if (errors == null)
            return null;
        return errors.stream().map(err -> new ValidationErrorResponse(err.getField(), String.valueOf(err.getRejectedValue()), err.getDefaultMessage())).collect(Collectors.toList());
    }
}
