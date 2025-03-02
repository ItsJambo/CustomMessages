package com.itsjambo.custommessages;

import com.itsjambo.custommessages.listeners.CustomMessagesListener;
import com.itsjambo.custommessages.utils.GradientParser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class CustomMessages extends JavaPlugin {

    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        saveDefaultConfig();

        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new CustomMessagesListener(this), this);
        Objects.requireNonNull(getCommand("cmessages-reload")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cmessages-reload")) {
            this.reloadConfig();
            sender.sendMessage(GradientParser.parse("&#08FB52[✔] Config reloaded!"));
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String colorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}