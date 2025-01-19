package gym.controller.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransactionDto {
    private Long equipmentId;
    private Long customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
