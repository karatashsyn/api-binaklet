package dto.requests.transporter;

import lombok.Data;

@Data
public class UserCommentCreateRequest {
    String comment;
    Long transporterId;
}
