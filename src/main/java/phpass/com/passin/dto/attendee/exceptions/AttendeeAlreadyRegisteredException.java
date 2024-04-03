package phpass.com.passin.dto.attendee.exceptions;

public class AttendeeAlreadyRegisteredException extends RuntimeException {

    public AttendeeAlreadyRegisteredException(String message) {
        super(message);
    }

}
