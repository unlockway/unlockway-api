package fatec.v2.unlockway.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_history")
public class HistoryModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private PatientRoutineModel routine;

    @Column(name = "routine_meal_id")
    private UUID routineMealId;
    private boolean ingested;
    private LocalDate date;
}