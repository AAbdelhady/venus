package com.venus.exceptions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.venus.config.GlobalMapperConfig;
import com.venus.exceptions.AbstractException;
import com.venus.exceptions.dto.ErrorCodeResponse;
import com.venus.exceptions.dto.ErrorResponse;
import com.venus.exceptions.enums.ErrorCode;

@Mapper(config = GlobalMapperConfig.class)
public interface ExceptionMapper {

    @Mapping(target = "code", qualifiedByName = "mapCode")
    ErrorResponse mapOne(AbstractException ex);

    default ErrorResponse mapOne(ErrorCode code, String message) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(mapCode(code));
        response.setMessage(message);
        return response;
    }

    @Named("mapCode")
    default ErrorCodeResponse mapCode(ErrorCode code) {
        ErrorCode c = code != null ? code : ErrorCode.UNSPECIFIED;
        return new ErrorCodeResponse(c.name(), c.getValue());
    }
}
