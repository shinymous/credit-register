package com.cred.register.controller;

import com.cred.register.dto.ErrorDTO;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(headers = {"Accept=application/json,application/xml"}, produces = "application/json")
@ApiResponses(value = {@ApiResponse(code = 400, message = "Default Error", response = ErrorDTO.class)})
public interface NewCreditAPI {
}