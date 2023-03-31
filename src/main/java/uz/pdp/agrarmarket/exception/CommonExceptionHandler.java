package uz.pdp.agrarmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<FieldErrorResponse> handleBindingException(BindException e) {
        return Stream.concat(
                e.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> FieldErrorResponse.builder()
                                .field(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .code(fieldError.getCode())
                                .build()
                        ),
                e.getBindingResult().getGlobalErrors().stream()
                        .map(globalError -> FieldErrorResponse.builder()
                                .field(globalError.getObjectName())
                                .message(globalError.getDefaultMessage())
                                .code(globalError.getCode())
                                .build())
        ).toList();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldErrorResponse handleObjectNotException(RecordNotFoundException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("ObjectNotFound")
                .build();
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public FieldErrorResponse handleObjectAlreadyExist(RecordAlreadyExistException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code(HttpStatus.ALREADY_REPORTED.toString())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("User not found")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public FieldErrorResponse handleAccessTokenTimeExceededException(Exception e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code(" Token time out")
                .build();
    }
    @ExceptionHandler(SmsSendingFailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public FieldErrorResponse handleAccessTokenTimeExceededException(SmsSendingFailException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("Can not send sms to your phone number ")
                .build();
    }
}
