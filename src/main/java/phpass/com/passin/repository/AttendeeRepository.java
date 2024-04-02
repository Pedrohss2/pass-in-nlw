package phpass.com.passin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phpass.com.passin.domain.attendee.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
