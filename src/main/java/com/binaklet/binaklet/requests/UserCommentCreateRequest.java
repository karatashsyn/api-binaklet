package com.binaklet.binaklet.requests;

import lombok.Data;

@Data
public class UserCommentCreateRequest {
    String comment;
    Long transporterId;
}
