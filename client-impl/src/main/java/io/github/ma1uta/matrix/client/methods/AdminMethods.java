/*
 * Copyright sablintolya@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ma1uta.matrix.client.methods;

import io.github.ma1uta.matrix.client.RequestParams;
import io.github.ma1uta.matrix.client.api.AdminApi;
import io.github.ma1uta.matrix.client.factory.RequestFactory;
import io.github.ma1uta.matrix.client.model.admin.AdminResponse;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Admin methods.
 */
public class AdminMethods extends AbstractMethods {

    public AdminMethods(RequestFactory factory, RequestParams defaultParams) {
        super(factory, defaultParams);
    }

    /**
     * This API may be restricted to only be called by the user being looked up, or by a server admin. Server-local administrator
     * privileges are not specified in this document.
     *
     * @param userId The user mxid
     * @return The user information.
     */
    public CompletableFuture<AdminResponse> whois(String userId) {
        Objects.requireNonNull(userId, "UserId cannot be empty.");

        RequestParams params = defaults().clone().path("userId", userId.toString());
        return factory().get(AdminApi.class, "whois", params, AdminResponse.class);
    }
}
