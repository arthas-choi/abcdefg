/**
 * Copyright 2020 Martin Böhmer
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.broker.finops;

import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

/**
 * Factory for {@link FinOpsIdentityProvider}.
 *
 * @author Martin Böhmer
 */
public class FinOpsIdentityProviderFactory
        extends AbstractIdentityProviderFactory<FinOpsIdentityProvider> {

    public static final String PROVIDER_ID = "hiorgsrv";

    @Override
    public String getName() {
        return "FinOps-SSO";
    }

    @Override
    public FinOpsIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new FinOpsIdentityProvider(session, new FinOpsProviderConfig(model));
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

}
