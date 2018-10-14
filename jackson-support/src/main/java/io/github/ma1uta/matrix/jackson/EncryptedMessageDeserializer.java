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

package io.github.ma1uta.matrix.jackson;

import static io.github.ma1uta.matrix.Event.Encryption.MEGOLM;
import static io.github.ma1uta.matrix.Event.Encryption.OLM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.ma1uta.matrix.event.content.RoomEncryptedContent;
import io.github.ma1uta.matrix.event.encrypted.MegolmEncryptedContent;
import io.github.ma1uta.matrix.event.encrypted.OlmEncryptedContent;
import io.github.ma1uta.matrix.event.encrypted.RawEncryptedContent;

/**
 * The room message deserializer.
 */
public class EncryptedMessageDeserializer {

    /**
     * Deserialize the room message.
     *
     * @param node  the json node with the message.
     * @param codec the json codec.
     * @return deserialized value or null.
     * @throws JsonProcessingException when cannot deserialize the room message.
     */
    public RoomEncryptedContent deserialize(JsonNode node, ObjectCodec codec) throws JsonProcessingException {
        if (node == null) {
            return null;
        }

        JsonNode algorithmNode = node.get("algorithm");
        if (algorithmNode == null || !algorithmNode.isTextual()) {
            return new RawEncryptedContent(node, null);
        }
        String algorithm = algorithmNode.asText();

        switch (algorithm) {
            case MEGOLM:
                return codec.treeToValue(node, MegolmEncryptedContent.class);
            case OLM:
                return codec.treeToValue(node, OlmEncryptedContent.class);
            default:
                return new RawEncryptedContent(node, algorithm);
        }
    }
}