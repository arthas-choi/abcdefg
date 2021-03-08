package org.keycloak.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinOpsAccessTokenWrapper {
  @JsonProperty("result")
  FinOpsAccessTokenResponse result;
  @JsonProperty("status")
  String status;

  public FinOpsAccessTokenResponse getResult() {
    return result;
  }

  public void setResult(FinOpsAccessTokenResponse result) {
    this.result = result;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
