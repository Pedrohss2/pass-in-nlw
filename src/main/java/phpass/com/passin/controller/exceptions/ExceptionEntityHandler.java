package phpass.com.passin.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import phpass.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import phpass.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import phpass.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import phpass.com.passin.domain.event.exceptions.EventFullException;
import phpass.com.passin.domain.event.exceptions.EventNotFoundException;
import phpass.com.passin.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handlerEventNotFound(EventNotFoundException eventNotFoundException) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity handlerEventFullException(EventFullException eventFullException) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(eventFullException.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException attendeeNotFoundException) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity handleAttendeeAlreadyRegisteredException(AttendeeAlreadyRegisteredException attendeeAlreadyRegisteredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExistsException(CheckInAlreadyExistsException checkInAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


}
