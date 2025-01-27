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
import io.github.ma1uta.matrix.client.api.UserDirectoryApi;
import io.github.ma1uta.matrix.client.factory.RequestFactory;
import io.github.ma1uta.matrix.client.model.userdirectory.SearchRequest;
import io.github.ma1uta.matrix.client.model.userdirectory.SearchResponse;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * User directory methods.
 */
public class UserDirectoryMethods extends AbstractMethods {

    public UserDirectoryMethods(RequestFactory factory, RequestParams defaultParams) {
        super(factory, defaultParams);
    }

    /**
     * This API performs a server-side search over all users registered on the server. It searches user ID and displayname
     * case-insensitively for users that you share a room with or that are in public rooms.
     *
     * @param request The search request.
     * @return The result of the search.
     */
    public CompletableFuture<SearchResponse> search(SearchRequest request) {
        Objects.requireNonNull(request.getSearchTerm(), "SearchTerm cannot be empty.");

        return factory().post(UserDirectoryApi.class, "searchUsers", defaults(), request, SearchResponse.class);
    }
}
