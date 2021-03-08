package org.keycloak.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class FinOpsCompanyVendor {
  @JsonProperty("accountList")
  protected Set<FinOpsAccountList> accountList;

  @JsonProperty("cloudVndrId")
  protected String cloudVndrId;

  @JsonProperty("cloudVndrNm")
  protected String cloudVndrNm;

  public Set<FinOpsAccountList> getAccountList() {
    return accountList;
  }

  public void setAccountList(Set<FinOpsAccountList> accountList) {
    this.accountList = accountList;
  }

  public String getCloudVndrId() {
    return cloudVndrId;
  }

  public void setCloudVndrId(String cloudVndrId) {
    this.cloudVndrId = cloudVndrId;
  }

  public String getCloudVndrNm() {
    return cloudVndrNm;
  }

  public void setCloudVndrNm(String cloudVndrNm) {
    this.cloudVndrNm = cloudVndrNm;
  }
}
