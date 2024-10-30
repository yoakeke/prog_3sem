package ru.samarina.MySecondTestAppSpringBoot.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.samarina.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.samarina.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.samarina.MySecondTestAppSpringBoot.model.*;
import ru.samarina.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.samarina.MySecondTestAppSpringBoot.service.ValidationService;
import ru.samarina.MySecondTestAppSpringBoot.util.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService1;
    private final ModifyResponseService modifyResponseService2;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService1,
                        @Qualifier("ModifyOperationUidResponseService") ModifyResponseService modifyResponseService2) {
        this.validationService = validationService;
        this.modifyResponseService1 = modifyResponseService1;
        this.modifyResponseService2 = modifyResponseService2;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback (@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            log.info("validation...");
            validationService.isValid(bindingResult);
            log.info("validation success");
        } catch (ValidationFailedException e) {
            log.error(e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            httpStatus = HttpStatus.BAD_REQUEST;
            log.info("validation failed");
        } catch (UnsupportedCodeException e) {
            log.error(e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            httpStatus = HttpStatus.BAD_REQUEST;
            log.info("validation failed");
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.info("validation failed");
        }
        response = modifyResponseService1.modify(response);
        response = modifyResponseService2.modify(response);

        log.info("response: {}", response);
        return new ResponseEntity<>(response, httpStatus);
    }
}