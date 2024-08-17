package com.binaklet.binaklet.dto.responses;


import com.binaklet.binaklet.dto.responses.auth.MeDTO;
import com.binaklet.binaklet.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private MeDTO user;
    private String message;
}
