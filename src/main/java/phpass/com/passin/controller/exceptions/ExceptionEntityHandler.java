package phpass.com.passin.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import phpass.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import phpass.com.passin.domain.event.exceptions.EventNotFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handlerEventNotFound(EventNotFoundException eventNotFoundException) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException attendeeNotFoundException) {
        return ResponseEntity.notFound().build();
    }




}
