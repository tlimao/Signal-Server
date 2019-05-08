package org.whispersystems.textsecuregcm.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class TestAccountConfiguration {

  @JsonProperty
  @NotEmpty
  private String email;

  @JsonProperty
  @NotNull
  private int code;

  public String getEmail() {
    return email;
  }

  public int getCode() {
    return code;
  }
}