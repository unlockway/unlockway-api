package fatec.v2.unlockway.api.dto.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNotificationsDTO {
    private UUID id;
    private String title;
    private String description;
    private boolean read;
    private LocalDateTime date;
}
