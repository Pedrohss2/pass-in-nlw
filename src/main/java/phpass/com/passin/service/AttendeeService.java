package phpass.com.passin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phpass.com.passin.domain.attendee.Attendee;
import phpass.com.passin.domain.checkin.CheckIn;
import phpass.com.passin.dto.attendee.AttendeeDetails;
import phpass.com.passin.dto.attendee.AttendeesListResponseDTO;
import phpass.com.passin.dto.attendee.exceptions.AttendeeAlreadyRegisteredException;
import phpass.com.passin.repository.AttendeeRepository;
import phpass.com.passin.repository.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetails = attendees.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetails);
    }

    public Attendee registerAttendee(Attendee attendee) {
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public void verifyAttendeeSubscription(String eventId, String email) {
        Optional<Attendee> attendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if(attendeeRegistered.isPresent()) throw new AttendeeAlreadyRegisteredException("Attendee is already registry");
    }

}
