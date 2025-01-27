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

import io.github.ma1uta.matrix.EmptyResponse;
import io.github.ma1uta.matrix.client.RequestParams;
import io.github.ma1uta.matrix.client.api.ReportApi;
import io.github.ma1uta.matrix.client.factory.RequestFactory;
import io.github.ma1uta.matrix.client.model.report.ReportRequest;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Report methods.
 */
public class ReportMethods extends AbstractMethods {

    public ReportMethods(RequestFactory factory, RequestParams defaultParams) {
        super(factory, defaultParams);
    }

    /**
     * Reports an event as inappropriate to the server, which may then notify the appropriate people.
     *
     * @param roomId  The room in which the event being reported is located.
     * @param eventId The event to report.
     * @param reason  The reason the content is being reported. May be blank.
     * @param score   The score to rate this content as where -100 is most offensive and 0 is inoffensive.
     * @return The empty response.
     */
    public CompletableFuture<EmptyResponse> report(String roomId, String eventId, String reason, Integer score) {
        Objects.requireNonNull(roomId, "RoomId cannot be empty.");
        Objects.requireNonNull(eventId, "EventId cannot be empty.");
        Objects.requireNonNull(reason, "Reason cannot be empty.");
        Objects.requireNonNull(score, "Score cannot be empty.");

        RequestParams params = defaults().clone()
            .path("roomId", roomId)
            .path("eventId", eventId);
        ReportRequest request = new ReportRequest();
        request.setReason(reason);
        request.setScore(score);
        return factory().post(ReportApi.class, "report", params, reason, EmptyResponse.class);
    }
}
