package uz.pdp.agrarmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public FieldErrorResponse handleUserAlreadyExistException(UserAlreadyExist e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("User already exist")
                .build();
    }

    @ExceptionHandler(TimeExceededException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FieldErrorResponse handleTimeExceededExceptionException(TimeExceededException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("ReFresh token valid time end")
                .build();
    }

    @ExceptionHandler(RefreshTokeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldErrorResponse handleRefreshTokeNotFoundException(RefreshTokeNotFound e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("ReFresh token not found")
                .build();
    }

    @ExceptionHandler(UserNotVerified.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FieldErrorResponse handleUserNotVerifiedException(UserNotVerified e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("User account not verified")
                .build();
    }

    @ExceptionHandler(VerificationCodeLiveTimeEnd.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FieldErrorResponse handleVerificationCodeLiveTimeEndException(VerificationCodeLiveTimeEnd e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("Verification code live time end")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldErrorResponse handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("Validation error")
                .build();
    }

//Token ni exception ni ushlash uchun
//            @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_GATEWAY)
//    public FieldErrorResponse handleAccessTokenTimeExceededException(Exception e) {
//        return FieldErrorResponse.builder()
//                .message(e.getMessage())
//                .code(" Token time out")
//                .build();
//    }
    @ExceptionHandler(SmsSendingFailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public FieldErrorResponse handleAccessTokenTimeExceededException(SmsSendingFailException e) {
        return FieldErrorResponse.builder()
                .message(e.getMessage())
                .code("Can not send sms to your phone number ")
                .build();
    }
}
