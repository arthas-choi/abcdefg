package org.keycloak.authentication.authenticators.broker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FinOpsCompnayListReponse {
  private FinOpsUserProfile result;
  private String status;

  public FinOpsUserProfile getResult() {
    return result;
  }

  public void setResult(FinOpsUserProfile result) {
    this.result = result;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
