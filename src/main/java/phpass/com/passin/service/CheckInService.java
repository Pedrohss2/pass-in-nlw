package phpass.com.passin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phpass.com.passin.domain.attendee.Attendee;
import phpass.com.passin.domain.checkin.CheckIn;
import phpass.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import phpass.com.passin.repository.CheckInRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExist(attendee.getId());

        CheckIn checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(checkIn);
    }

    private void verifyCheckInExist(String attendeeId) {
        Optional<CheckIn> checkIn = this.getCheckIn(attendeeId);
        if(checkIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
       return this.checkInRepository.findByAttendeeId(attendeeId);
    }

}
