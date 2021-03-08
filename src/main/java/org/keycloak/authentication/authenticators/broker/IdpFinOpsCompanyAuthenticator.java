package org.keycloak.authentication.authenticators.broker;

import static org.keycloak.authentication.authenticators.broker.AbstractIdpAuthenticator.BROKERED_CONTEXT_NOTE;
import static org.keycloak.authentication.authenticators.broker.util.PostBrokerLoginConstants.PBL_BROKERED_IDENTITY_CONTEXT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.AuthenticationFlowException;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.broker.util.SerializedBrokeredIdentityContext;
import org.keycloak.broker.oidc.OIDCIdentityProvider;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.sessions.AuthenticationSessionModel;

public class IdpFinOpsCompanyAuthenticator implements Authenticator {

  private static final Logger logger = Logger.getLogger(
      org.keycloak.authentication.authenticators.broker.IdpFinOpsCompanyAuthenticator.class);

  private static final List<String> DEV_OS_ROLE = Arrays.asList(
      "DefaultClientRole",
      "Default_AdminRole",
      "instanceTemplates_create",
      "instanceTemplates_read",
      "instanceTemplates_update",
      "instanceTemplates_delete",
      "requestInstances_create",
      "requestInstances_read",
      "requestInstances_update",
      "requestInstances_delete",
      "requestInstances_execute",
      "credentials_create",
      "credentials_read",
      "credentials_update",
      "credentials_delete",
      "inventories_create",
      "inventories_read",
      "inventories_update",
      "inventories_delete",
      "projects_create",
      "projects_read",
      "projects_update",
      "projects_delete",
      "jobs_read",
      "provisioning_create",
      "provisioning_read",
      "provisioning_update",
      "provisioning_delete",
      "provisioning_execute",
      "configuration_create",
      "configuration_read",
      "configuration_update",
      "configuration_delete",
      "configuration_execute",
      "resourceTopology_create",
      "resourceTopology_read",
      "resourceTopology_update",
      "resourceTopology_delete",
      "provisioningCatalog_create",
      "provisioningCatalog_read",
      "provisioningCatalog_update",
      "provisioningCatalog_delete",
      "toolchains_create",
      "toolchains_read",
      "toolchains_update",
      "toolchains_delete",
      "workspace_create",
      "workspace_read",
      "workspace_update",
      "workspace_delete",
      "workspace_execute",
      "workflowCatalog_create",
      "workflowCatalog_read",
      "workflowCatalog_update",
      "workflowCatalog_delete",
      "workflowCatalog_execute",
      "schedules_create",
      "schedules_read",
      "schedules_update",
      "schedules_delete",
      "schedules_execute"
  );

  @Override
  public void authenticate(AuthenticationFlowContext authenticationFlowContext) {
    AuthenticationSessionModel authSession = authenticationFlowContext.getAuthenticationSession();

    SerializedBrokeredIdentityContext serializedCtx = null;
    if (isFirstBroker(authSession)) {
      serializedCtx = SerializedBrokeredIdentityContext.readFromAuthenticationSession(authSession, BROKERED_CONTEXT_NOTE);
    } else {
      serializedCtx = SerializedBrokeredIdentityContext
          .readFromAuthenticationSession(authSession, PBL_BROKERED_IDENTITY_CONTEXT);
    }

    if (serializedCtx == null) {
      throw new AuthenticationFlowException("Not found serialized context in clientSession",
          AuthenticationFlowError.IDENTITY_PROVIDER_ERROR);
    }
    BrokeredIdentityContext brokerContext = serializedCtx.deserialize(authenticationFlowContext.getSession(), authSession);

    AccessTokenResponse accessTokenResponse = (AccessTokenResponse) brokerContext.getContextData()
        .get(OIDCIdentityProvider.FEDERATED_ACCESS_TOKEN_RESPONSE);
    KeycloakSession session = authenticationFlowContext.getSession();
    RealmModel realmModel = authenticationFlowContext.getRealm();

    String token = accessTokenResponse.getToken();
    SimpleHttp tokenRequest = SimpleHttp.doGet("https://servicedev.opsnow.com/api_v2.0/authgroup/companyList/portal", session);
    try {
      FinOpsCompnayListReponse companyList = tokenRequest.auth(token).asJson(FinOpsCompnayListReponse.class);
      if (!companyList.getResult().getCmpnList().isEmpty()) {
        for (FinOpsCompany finOpsCompany : companyList.getResult().getCmpnList()) {
          ClientModel companyClientModel = null;
          Optional<ClientModel> clientModelOptional = session.clients().getClientsStream(realmModel)
              .filter(clientModel -> clientModel.getClientId().equals(finOpsCompany.getCmpnId())).findAny();
          if (!clientModelOptional.isPresent()) {
            companyClientModel = session.clients().addClient(realmModel, finOpsCompany.getCmpnId());
            companyClientModel.setName(finOpsCompany.getCmpnNm());
            initializeClientRole(companyClientModel);
          } else {
            companyClientModel = clientModelOptional.get();
          }
          RoleModel roleModel = companyClientModel.getRole("DefaultClientRole");
          UserModel userModel = authenticationFlowContext.getAuthenticationSession().getAuthenticatedUser();
          userModel.grantRole(roleModel);

        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      authenticationFlowContext.attempted();
    }
    authenticationFlowContext.success();
  }

  @Override
  public void action(AuthenticationFlowContext authenticationFlowContext) {

  }

  @Override
  public boolean requiresUser() {
    return false;
  }

  @Override
  public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
    return true;
  }

  @Override
  public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

  }

  private void initializeClientRole(ClientModel clientModel) {
    for (String roleName : DEV_OS_ROLE) {
      clientModel.addRole(roleName);
    }
  }

  private boolean isFirstBroker(AuthenticationSessionModel authenticationSessionModel) {
    return authenticationSessionModel.getAuthNote(BROKERED_CONTEXT_NOTE) != null;
  }

  @Override
  public void close() {

  }
}
