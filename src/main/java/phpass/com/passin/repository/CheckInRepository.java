package phpass.com.passin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phpass.com.passin.domain.checkin.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
