package phpass.com.passin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phpass.com.passin.domain.attendee.Attendee;
import phpass.com.passin.domain.event.Event;

import java.util.List;
import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
    List<Attendee> findByEventId(String eventId);


    Optional<Attendee> findByEventIdAndEmail(String eventId, String email1);
}
