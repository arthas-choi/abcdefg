package org.keycloak.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class FinOpsAccessTokenResponse {

  @JsonProperty("cmpnList")
  protected Set<FinOpsCompany> companyList;

  @JsonProperty("userNm")
  protected String userName;

  @JsonProperty("userId")
  protected String userId;

  @JsonProperty("userGrpTypeNm")
  protected String userGroupTypeName;

  @JsonProperty("userGrpTypeCd")
  protected String userGroupTypeCode;

  @JsonProperty("userGrpNm")
  protected String userGroupName;

  @JsonProperty("userGrpId")
  protected String userGroupId;

  @JsonProperty("userGrpDesc")
  protected String userGroupDescription;

  @JsonProperty("userEmail")
  protected String userEmail;

  @JsonProperty("siteCd")
  protected String siteCode;

  public Set<FinOpsCompany> getCompanyList() {
    return companyList;
  }

  public void setCompanyList(Set<FinOpsCompany> companyList) {
    this.companyList = companyList;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserGroupTypeName() {
    return userGroupTypeName;
  }

  public void setUserGroupTypeName(String userGroupTypeName) {
    this.userGroupTypeName = userGroupTypeName;
  }

  public String getUserGroupTypeCode() {
    return userGroupTypeCode;
  }

  public void setUserGroupTypeCode(String userGroupTypeCode) {
    this.userGroupTypeCode = userGroupTypeCode;
  }

  public String getUserGroupName() {
    return userGroupName;
  }

  public void setUserGroupName(String userGroupName) {
    this.userGroupName = userGroupName;
  }

  public String getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
  }

  public String getUserGroupDescription() {
    return userGroupDescription;
  }

  public void setUserGroupDescription(String userGroupDescription) {
    this.userGroupDescription = userGroupDescription;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getSiteCode() {
    return siteCode;
  }

  public void setSiteCode(String siteCode) {
    this.siteCode = siteCode;
  }
}
