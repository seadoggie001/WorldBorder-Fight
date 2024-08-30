package com.seadoggie.worldborderfight.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPlayerDeathCallback {
    Event<ServerPlayerDeathCallback> EVENT = EventFactory.createArrayBacked(ServerPlayerDeathCallback.class,
            (listeners) -> (player, damageSource) -> {
                for(ServerPlayerDeathCallback listener : listeners){
                    listener.onPlayerDeath(player, damageSource);
                }
            });

    void onPlayerDeath(ServerPlayerEntity player, DamageSource damageSource);
}
