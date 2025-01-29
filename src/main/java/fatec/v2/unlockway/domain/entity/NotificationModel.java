package fatec.v2.unlockway.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import fatec.v2.unlockway.domain.entity.patient.PatientModel;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_notifications")
public class NotificationModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;

    private String title;
    private String description;
    @Column(name = "`read`")
    private boolean read;

    private LocalDateTime date;
}
