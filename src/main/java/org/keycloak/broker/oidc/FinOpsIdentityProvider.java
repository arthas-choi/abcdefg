/**
 * Copyright 2020 Martin Böhmer and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.keycloak.broker.oidc;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.OAuthErrorException;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.common.ClientConnection;
import org.keycloak.events.Errors;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.FinOpsAccessTokenResponse;
import org.keycloak.representations.FinOpsAccessTokenWrapper;
import org.keycloak.services.ErrorPage;
import org.keycloak.services.messages.Messages;
import org.keycloak.util.JsonSerialization;

public class FinOpsIdentityProvider extends OIDCIdentityProvider {

  protected static final Logger logger = Logger.getLogger(FinOpsIdentityProvider.class);


  public static final String DEFAULT_SCOPE = "oidc";
  public static final String EXPIRES_IN = "access_token";

  public FinOpsIdentityProvider(KeycloakSession session, OIDCIdentityProviderConfig config) {
    super(session, config);
  }


  @Override
  protected String getDefaultScopes() {
    return DEFAULT_SCOPE;
  }

  @Override
  public Object callback(RealmModel realm, AuthenticationCallback callback, EventBuilder event) {
    return new FinOpsEndpoint(callback, realm, event);
  }

  @Override
  public BrokeredIdentityContext getFederatedIdentity(String response) {
    FinOpsAccessTokenResponse tokenResponse = null;
    try {
      FinOpsAccessTokenWrapper tokenResponseWrapper = JsonSerialization.readValue(response, FinOpsAccessTokenWrapper.class);
      tokenResponse = tokenResponseWrapper.getResult();

    } catch (IOException e) {
      throw new IdentityBrokerException("Could not decode access token response.", e);
    }

    BrokeredIdentityContext identity = new BrokeredIdentityContext(tokenResponse.getUserEmail());
    identity.setId(tokenResponse.getUserId());
    identity.setEmail(tokenResponse.getUserEmail());
    identity.setBrokerUserId(getConfig().getAlias() + "." + tokenResponse.getUserId());
    identity.setUsername(tokenResponse.getUserEmail());

    return identity;
  }

  @Override
  public void preprocessFederatedIdentity(KeycloakSession session, RealmModel realm, BrokeredIdentityContext context) {

  }

  protected class FinOpsEndpoint extends Endpoint {

    @Context
    protected KeycloakSession session;

    @Context
    protected ClientConnection clientConnection;

    @Context
    protected HttpHeaders headers;

    public FinOpsEndpoint(AuthenticationCallback callback, RealmModel realm, EventBuilder event) {
      super(callback, realm, event);

    }

    @GET
    public Response authResponse(@QueryParam(AbstractOAuth2IdentityProvider.OAUTH2_PARAMETER_STATE) String state,
        @QueryParam(AbstractOAuth2IdentityProvider.OAUTH2_PARAMETER_ACCESS_TOKEN) String authorizationCode,
        @QueryParam(OAuth2Constants.ERROR) String error) {
      if (error != null) {
        logger.error(error + " for broker login " + getConfig().getProviderId());
        if (error.equals(ACCESS_DENIED)) {
          return callback.cancelled(state);
        } else if (error.equals(OAuthErrorException.LOGIN_REQUIRED) || error.equals(OAuthErrorException.INTERACTION_REQUIRED)) {
          return callback.error(state, error);
        } else {
          return callback.error(state, Messages.IDENTITY_PROVIDER_UNEXPECTED_ERROR);
        }
      }

      try {

        if (authorizationCode != null) {
          String response = generateTokenRequest(authorizationCode).asString();

//          String response = authorizationCode;
          BrokeredIdentityContext federatedIdentity = getFederatedIdentity(response);
          federatedIdentity.getContextData().put("BROKER_NONCE", state);

          AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
          accessTokenResponse.setExpiresIn(1L);
          accessTokenResponse.setToken(authorizationCode);
          accessTokenResponse.setIdToken(authorizationCode);
          accessTokenResponse.setRefreshToken(authorizationCode);

          federatedIdentity.getContextData().put(OIDCIdentityProvider.FEDERATED_ACCESS_TOKEN_RESPONSE, accessTokenResponse);

          if (getConfig().isStoreToken()) {
            // make sure that token wasn't already set by getFederatedIdentity();
            // want to be able to allow provider to set the token itself.
            if (federatedIdentity.getToken() == null) {
              federatedIdentity.setToken(authorizationCode);
            }
          }

          federatedIdentity.setIdpConfig(getConfig());
          federatedIdentity.setIdp(FinOpsIdentityProvider.this);
          federatedIdentity.setCode(state);

          return callback.authenticated(federatedIdentity);
        }
      } catch (WebApplicationException e) {
        return e.getResponse();
      } catch (Exception e) {
        logger.error("Failed to make identity provider oauth callback", e);
      }
      event.event(EventType.LOGIN);
      event.error(Errors.IDENTITY_PROVIDER_LOGIN_FAILURE);
      return ErrorPage.error(session, null, Response.Status.BAD_GATEWAY, Messages.IDENTITY_PROVIDER_UNEXPECTED_ERROR);
    }

    public SimpleHttp generateTokenRequest(String authorizationCode) {
      KeycloakContext context = this.session.getContext();
      SimpleHttp tokenRequest = SimpleHttp.doGet(FinOpsIdentityProvider.this.getConfig().getTokenUrl(), this.session);
      return tokenRequest.auth(authorizationCode);
    }

  }

}
