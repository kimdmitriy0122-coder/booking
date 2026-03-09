package com.example.j_booking.handler;

import com.example.j_booking.constants.exceptions.ExceptionCode;
import com.example.j_booking.dto.error.CommonErrorResponse;
import com.example.j_booking.exceptions.BookingAlreadyPayedException;
import com.example.j_booking.exceptions.BookingRecordWithSuchPaymentIdNotExistException;
import com.example.j_booking.exceptions.InvalidCheckoutDate;
import com.example.j_booking.exceptions.NoSuchRoomException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

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

    @ExceptionHandler(BookingRecordWithSuchPaymentIdNotExistException.class)
    public ResponseEntity<CommonErrorResponse> handleBookingRecordWithSuchPaymentIdNotExist(BookingRecordWithSuchPaymentIdNotExistException exception){
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        ExceptionCode.PAYMENT_DOESNT_EXISTS.getCode(),
                        ExceptionCode.PAYMENT_DOESNT_EXISTS.getMessage()
                ));
    }

    @ExceptionHandler(BookingAlreadyPayedException.class)
    public ResponseEntity<CommonErrorResponse> handleBookingAlreadyPayedException(BookingAlreadyPayedException exception){
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        ExceptionCode.BOOKING_ALREADY_PAYED.getCode(),
                        ExceptionCode.BOOKING_ALREADY_PAYED.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        ExceptionCode.NOT_READABLE_REQUEST.getCode(),
                        ExceptionCode.NOT_READABLE_REQUEST.getMessage() + ". " + exception.getMessage()
                ));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<CommonErrorResponse> handleResourceAccessException(ResourceAccessException exception){
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        ExceptionCode.RESOURCE_ACCESS.getCode(),
                        ExceptionCode.RESOURCE_ACCESS.getMessage() + ". " + exception.getMessage()
                ));
    }
}
