package phpass.com.passin.domain.attendee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import phpass.com.passin.domain.event.Event;

import java.time.LocalDateTime;

@Table(name = "attendees")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendee {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
