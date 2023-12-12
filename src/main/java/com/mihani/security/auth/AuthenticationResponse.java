package com.mihani.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("_userId")
  private Integer userId;

  @JsonProperty("_firstName")
  private String firstName;

  @JsonProperty("_lastName")
  private String lastName;

  @JsonProperty("_role")
  private String role;



}
