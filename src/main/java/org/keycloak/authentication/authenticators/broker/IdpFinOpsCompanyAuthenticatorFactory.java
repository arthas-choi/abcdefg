package org.keycloak.authentication.authenticators.broker;

import java.util.Collections;
import java.util.List;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class IdpFinOpsCompanyAuthenticatorFactory implements AuthenticatorFactory {

  public static final String PROVIDER_ID = "idp-finOps-company";
  static IdpFinOpsCompanyAuthenticator SINGLETON = new IdpFinOpsCompanyAuthenticator();

  @Override
  public Authenticator create(KeycloakSession keycloakSession) {
    return SINGLETON;
  }

  @Override
  public void init(Config.Scope scope) {

  }

  @Override
  public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

  }

  @Override
  public void close() {

  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public String getReferenceCategory() {
    return "FinOpsCompany";
  }

  @Override
  public boolean isConfigurable() {
    return true;
  }


  @Override
  public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES;
  }

  @Override
  public String getDisplayType() {
    return "FinOps Company";
  }

  @Override
  public String getHelpText() {
    return "User Company List Map to Clients";
  }

  @Override
  public boolean isUserSetupAllowed() {
    return false;
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return Collections.emptyList();
  }


}
