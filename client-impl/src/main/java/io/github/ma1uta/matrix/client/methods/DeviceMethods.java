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
import io.github.ma1uta.matrix.client.api.DeviceApi;
import io.github.ma1uta.matrix.client.factory.RequestFactory;
import io.github.ma1uta.matrix.client.model.account.AuthenticationData;
import io.github.ma1uta.matrix.client.model.device.Device;
import io.github.ma1uta.matrix.client.model.device.DeviceUpdateRequest;
import io.github.ma1uta.matrix.client.model.device.DevicesDeleteRequest;
import io.github.ma1uta.matrix.client.model.device.DevicesResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Admin methods.
 */
public class DeviceMethods extends AbstractMethods {

    public DeviceMethods(RequestFactory factory, RequestParams defaultParams) {
        super(factory, defaultParams);
    }

    /**
     * Gets information about all devices for the current user.
     *
     * @return The devices.
     */
    public CompletableFuture<List<Device>> devices() {
        return factory().get(DeviceApi.class, "devices", defaults(), DevicesResponse.class).thenApply(DevicesResponse::getDevices);
    }

    /**
     * Gets information on a single device, by device id.
     *
     * @param deviceId The device id.
     * @return The device information.
     */
    public CompletableFuture<Device> device(String deviceId) {
        Objects.requireNonNull(deviceId, "DeviceId cannot be empty.");

        RequestParams params = defaults().clone().path("deviceId", deviceId);
        return factory().get(DeviceApi.class, "device", params, Device.class);
    }

    /**
     * Updates the metadata on the given device.
     *
     * @param deviceId    The device id.
     * @param displayName A new device display name.
     * @return The empty response.
     */
    public CompletableFuture<EmptyResponse> update(String deviceId, String displayName) {
        Objects.requireNonNull(deviceId, "DeviceId cannot be empty.");

        RequestParams params = defaults().clone().path("deviceId", deviceId);
        DeviceUpdateRequest request = new DeviceUpdateRequest();
        request.setDisplayName(displayName);
        return factory().put(DeviceApi.class, "updateDevice", params, request, EmptyResponse.class);
    }

    /**
     * Delete the current device and invalidate this access token.
     *
     * @param auth Additional authentication information for the user-interactive authentication API.
     * @return The empty response.
     */
    public CompletableFuture<EmptyResponse> delete(AuthenticationData auth) {
        DevicesDeleteRequest request = new DevicesDeleteRequest();
        request.setAuth(auth);
        List<String> devices = Collections.singletonList(defaults().getDeviceId());
        request.setDevices(devices);
        return factory().post(DeviceApi.class, "deleteDevices", defaults(), request, EmptyResponse.class)
            .thenApply(r -> {
                defaults().deviceId(null);
                defaults().accessToken(null);
                return r;
            });
    }

    /**
     * Deletes the given devices, and invalidates any access token associated with them.
     *
     * @param request Devices to delete and additional authentication data.
     * @return The empty response.
     */
    public CompletableFuture<EmptyResponse> deleteDevices(DevicesDeleteRequest request) {
        String error = "Devices cannot be empty.";
        Objects.requireNonNull(request.getDevices(), error);
        if (request.getDevices().isEmpty()) {
            throw new NullPointerException(error);
        }

        return factory().post(DeviceApi.class, "deleteDevices", defaults(), request, EmptyResponse.class)
            .thenApply(r -> {
                if (request.getDevices().contains(defaults().getDeviceId())) {
                    defaults().deviceId(null);
                    defaults().accessToken(null);
                }
                return r;
            });
    }
}
