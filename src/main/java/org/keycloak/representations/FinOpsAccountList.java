package org.keycloak.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class FinOpsAccountList {
  @JsonProperty("accId")
  protected String accId;
  @JsonProperty("alias")
  protected String alias;
  @JsonProperty("hlthYn")
  protected String hlthYn;

  public String getAccId() {
    return accId;
  }

  public void setAccId(String accId) {
    this.accId = accId;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getHlthYn() {
    return hlthYn;
  }

  public void setHlthYn(String hlthYn) {
    this.hlthYn = hlthYn;
  }
}
