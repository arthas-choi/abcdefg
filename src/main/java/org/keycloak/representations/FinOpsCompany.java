package org.keycloak.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class FinOpsCompany {
  @JsonProperty("service")
  protected String service;
  @JsonProperty("cmpnUtcTmzn")
  protected String TimeZone;
  @JsonProperty("cmpnNm")
  protected String companyName;
  @JsonProperty("cmpnId")
  protected String companyId;
  @JsonProperty("authYn")
  protected String authYn;
  @JsonProperty("vendor")
  protected Set<FinOpsCompanyVendor> vendor;

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getTimeZone() {
    return TimeZone;
  }

  public void setTimeZone(String timeZone) {
    TimeZone = timeZone;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getAuthYn() {
    return authYn;
  }

  public void setAuthYn(String authYn) {
    this.authYn = authYn;
  }

  public Set<FinOpsCompanyVendor> getVendor() {
    return vendor;
  }

  public void setVendor(Set<FinOpsCompanyVendor> vendor) {
    this.vendor = vendor;
  }
}
