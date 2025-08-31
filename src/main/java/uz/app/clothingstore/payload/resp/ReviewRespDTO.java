package uz.app.clothingstore.payload.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ReviewRespDTO {
    private UUID id;
    private Long productId;
    private int rating;
    private String comment;
    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
}
