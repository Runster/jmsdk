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

package io.github.ma1uta.matrix.bot.command;

import io.github.ma1uta.matrix.Event;
import io.github.ma1uta.matrix.bot.BotConfig;
import io.github.ma1uta.matrix.bot.BotDao;
import io.github.ma1uta.matrix.bot.BotHolder;
import io.github.ma1uta.matrix.bot.Command;
import io.github.ma1uta.matrix.bot.PersistentService;
import io.github.ma1uta.matrix.client.MatrixClient;

/**
 * Set new prefix or show current.
 *
 * @param <C> bot configuration.
 * @param <D> bot dao.
 * @param <S> bot service.
 * @param <E> extra data.
 */
public class Prefix<C extends BotConfig, D extends BotDao<C>, S extends PersistentService<D>, E> implements Command<C, D, S, E> {

    @Override
    public String name() {
        return "prefix";
    }

    @Override
    public boolean invoke(BotHolder<C, D, S, E> holder, String roomId, Event event, String arguments) {
        C config = holder.getConfig();
        MatrixClient matrixClient = holder.getMatrixClient();
        if (config.getOwner() != null && !config.getOwner().equals(event.getSender())) {
            return false;
        }
        if (arguments == null || arguments.trim().isEmpty()) {
            String prefix = config.getPrefix();
            matrixClient.event().sendNotice(roomId, prefix == null ? "!" : prefix);
        } else {
            config.setPrefix(arguments);
        }
        return true;
    }

    @Override
    public String help() {
        return "set new prefix or show current (invoked only by owner).";
    }

    @Override
    public String usage() {
        return "prefix [<prefix>]";
    }
}
