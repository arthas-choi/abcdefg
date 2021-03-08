package org.keycloak.authentication.authenticators.broker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FinOpsCompany {
  private String authYn;
  private String cmpnId;
  private String cmpnNm;
  private String cmpnUtcTmzn;
  private String service;

  public String getAuthYn() {
    return authYn;
  }

  public void setAuthYn(String authYn) {
    this.authYn = authYn;
  }

  public String getCmpnId() {
    return cmpnId;
  }

  public void setCmpnId(String cmpnId) {
    this.cmpnId = cmpnId;
  }

  public String getCmpnNm() {
    return cmpnNm;
  }

  public void setCmpnNm(String cmpnNm) {
    this.cmpnNm = cmpnNm;
  }

  public String getCmpnUtcTmzn() {
    return cmpnUtcTmzn;
  }

  public void setCmpnUtcTmzn(String cmpnUtcTmzn) {
    this.cmpnUtcTmzn = cmpnUtcTmzn;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }
}
