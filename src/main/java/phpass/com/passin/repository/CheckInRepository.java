package phpass.com.passin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phpass.com.passin.domain.checkin.CheckIn;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByAttendeeId(String attendId);
}
