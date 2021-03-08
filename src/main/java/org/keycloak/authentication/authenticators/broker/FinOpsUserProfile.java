package org.keycloak.authentication.authenticators.broker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FinOpsUserProfile {
  @JsonProperty("siteCd")
  private String siteCd;
  @JsonProperty("userEmail")
  private String userEmail;
  @JsonProperty("userGrpDesc")
  private String userGrpDesc;
  @JsonProperty("userGrpId")
  private String userGrpId;
  @JsonProperty("userGrpNm")
  private String userGrpNm;
  @JsonProperty("userGrpTypeCd")
  private String userGrpTypeCd;
  @JsonProperty("userGrpTypeNm")
  private String userGrpTypeNm;
  @JsonProperty("userId")
  private String userId;
  @JsonProperty("userNm")
  private String userNm;
  @JsonProperty("cmpnList")
  private List<FinOpsCompany> cmpnList;

  public String getSiteCd() {
    return siteCd;
  }

  public void setSiteCd(String siteCd) {
    this.siteCd = siteCd;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserGrpDesc() {
    return userGrpDesc;
  }

  public void setUserGrpDesc(String userGrpDesc) {
    this.userGrpDesc = userGrpDesc;
  }

  public String getUserGrpId() {
    return userGrpId;
  }

  public void setUserGrpId(String userGrpId) {
    this.userGrpId = userGrpId;
  }

  public String getUserGrpNm() {
    return userGrpNm;
  }

  public void setUserGrpNm(String userGrpNm) {
    this.userGrpNm = userGrpNm;
  }

  public String getUserGrpTypeCd() {
    return userGrpTypeCd;
  }

  public void setUserGrpTypeCd(String userGrpTypeCd) {
    this.userGrpTypeCd = userGrpTypeCd;
  }

  public String getUserGrpTypeNm() {
    return userGrpTypeNm;
  }

  public void setUserGrpTypeNm(String userGrpTypeNm) {
    this.userGrpTypeNm = userGrpTypeNm;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserNm() {
    return userNm;
  }

  public void setUserNm(String userNm) {
    this.userNm = userNm;
  }

  public List<FinOpsCompany> getCmpnList() {
    return cmpnList;
  }

  public void setCmpnList(List<FinOpsCompany> cmpnList) {
    this.cmpnList = cmpnList;
  }
}
