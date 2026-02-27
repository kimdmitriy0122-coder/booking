package com.example.j_booking.handler;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.exceptions.ExceptionCode;
import com.example.j_booking.dto.error.CommonErrorResponse;
import com.example.j_booking.exceptions.InvalidCheckoutDate;
import com.example.j_booking.exceptions.NoSuchRoomException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        errorMessage.append(ExceptionCode.NOT_VALID_REQUEST.getMessage());
        errorMessage.append(" ");
        if(!fieldErrors.isEmpty()){
            errorMessage.append(
                    fieldErrors
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .toList()
            );
        }
        else if(!globalErrors.isEmpty()){
            errorMessage
                    .append(" ")
                    .append(exception
                    .getBindingResult()
                    .getGlobalErrors()
                    .stream()
                    .map(err -> err.getObjectName() + ": " + err.getDefaultMessage())
                    .toList()
            );
        }


        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        ExceptionCode.NOT_VALID_REQUEST.getCode(),
                        errorMessage.toString()
                ));
    }

    @ExceptionHandler(NoSuchRoomException.class)
    public ResponseEntity<CommonErrorResponse> handleNoSuchRoomException(NoSuchRoomException exception) {
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        ExceptionCode.NO_SUCH_ROOM.getCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(InvalidCheckoutDate.class)
    public ResponseEntity<CommonErrorResponse> handleInvalidCheckoutDate(){
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                    ExceptionCode.INVALID_CHECKOUT_DATE.getCode(),
                    ExceptionCode.INVALID_CHECKOUT_DATE.getMessage()
                ));
    }
}
