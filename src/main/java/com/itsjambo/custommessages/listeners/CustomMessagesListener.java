package com.itsjambo.custommessages.listeners;

import com.itsjambo.custommessages.CustomMessages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

public class CustomMessagesListener implements Listener {
    private final CustomMessages plugin;

    public CustomMessagesListener(CustomMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("join.enabled", true)) {
            String message = config.getString("join.message", "&a{player} has joined the game!");
            String formatted = message.replace("{player}", event.getPlayer().getName());
            plugin.getServer().broadcastMessage(plugin.colorMessage(formatted));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("quit.enabled", true)) {
            String message = config.getString("quit.message", "&c{player} has left the game!");
            String formatted = message.replace("{player}", event.getPlayer().getName());
            plugin.getServer().broadcastMessage(plugin.colorMessage(formatted));
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        Player player = event.getEntity();
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("death.enabled", true)) {
            String message = config.getString("death.message", "&c{player} died.");
            String killer = getKillerName(player);
            String cause = getDeathCause(player);
            String formatted = message
                    .replace("{player}", player.getName())
                    .replace("{killer}", killer)
                    .replace("{cause}", cause);
            plugin.getServer().broadcastMessage(plugin.colorMessage(formatted));
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("chat.enabled", true)) {
            String message = config.getString("chat.message", "&e{player}: {message}");
            String formatted = message
                    .replace("{player}", event.getPlayer().getName())
                    .replace("{message}", event.getMessage());
            plugin.getServer().broadcastMessage(plugin.colorMessage(formatted));
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("kick.enabled", true)) {
            String message = config.getString("kick.message", "&c{player} has been kicked from the server.");
            String formatted = message.replace("{player}", event.getPlayer().getName());
            plugin.getServer().broadcastMessage(plugin.colorMessage(formatted));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("respawn.enabled", true)) {
            String message = config.getString("respawn.message", "&a{player} has respawned.");
            String formatted = message.replace("{player}", event.getPlayer().getName());
            plugin.getServer().broadcastMessage(plugin.colorMessage(formatted));
        }
    }


    private String getKillerName(Player player) {
        if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent event) {
            if (event.getDamager() instanceof Player killer) {
                return killer.getName();
            }
        }
        return "";
    }

    private String getDeathCause(Player player) {
        return player.getLastDamageCause() != null ?
                player.getLastDamageCause().getCause().name().toLowerCase() : "unknown";
    }
}