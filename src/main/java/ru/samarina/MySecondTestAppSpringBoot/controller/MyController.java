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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@RequestMapping("/lr4")
@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService){
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;

    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);
        Date requestDateTime = new Date();

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
        modifyResponseService.modify(response);
        log.info("response: {}", response);

        DateFormat dateFormat = DateTimeUtil.getCustomFormat();
        try {
            Date date1 = dateFormat.parse(request.getSystemTime());
            Date date2 = new Date();
            long dt = date2.getTime() - date1.getTime();
            log.info("Time difference between Service 1 and Service 2 (ms): {}", dt);
        } catch (ParseException e) {
            log.error("Failed to parse system time from request: {}", request.getSystemTime(), e);
        }

        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }
}