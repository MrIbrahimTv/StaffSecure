/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.controllers;

import org.arkhamnetwork.staffsecure.StaffSecure;
import org.arkhamnetwork.staffsecure.struct.StaffSecureUser;
import org.arkhamnetwork.staffsecure.struct.StaffSecureUserConfig;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author devan_000
 */
public class PlayerController {

    private static final StaffSecure plugin = StaffSecure.get();

    public static void handleLogin(Player player) {
        if (plugin.configuration.isMotdEnabled()) {
            player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GOLD + " This server is running staffsecure by harry5573!");
        }

        if (!player.hasPermission("staffsecure.staff")) {
            return;
        }

        if (!ConfigController.getUserFile(player.getUniqueId().toString()).exists()) {
            ConfigController.createUserFile(player.getUniqueId().toString());
        }

        if (plugin.users.get(player.getUniqueId().toString()) == null) {
            plugin.users.put(player.getUniqueId().toString(), getUser(player));
        }

        String previousIp = plugin.users.get(player.getUniqueId().toString()).getConfig().getPlayerIP();
        
        if (previousIp == null) {
            previousIp = "0.0.0.0";
        }
        
        plugin.users.get(player.getUniqueId().toString()).getConfig().setIP(player.getAddress().toString().split(":")[0].replace("/", ""));

        boolean hasIpChanged = !player.getAddress().toString().split(":")[0].replace("/", "").equals(previousIp);

        if (plugin.users.get(player.getUniqueId().toString()).getConfig().getEncryptedPassword() == null) {
            player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You do not have a password set and you need one. Do /password <pass>");
        }

        if (hasIpChanged) {
            player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.YELLOW + " Your IP has changed since the last time you logged in.");
        }

        if (plugin.configuration.isForceLoginOnRelogIfIpChange() && (hasIpChanged)) {
            plugin.users.get(player.getUniqueId().toString()).setLoggedIn(false);
            if (plugin.users.get(player.getUniqueId().toString()).getConfig().getEncryptedPassword() != null) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
            }
            return;
        }

        if (plugin.configuration.isForceLoginOnRelog()) {
            plugin.users.get(player.getUniqueId().toString()).setLoggedIn(false);
            if (plugin.users.get(player.getUniqueId().toString()).getConfig().getEncryptedPassword() != null) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
            }
            return;
        }
    }

    private static StaffSecureUser getUser(Player player) {
        return new StaffSecureUser(player.getName(), player.getUniqueId().toString(), new StaffSecureUserConfig(YamlConfiguration.loadConfiguration(ConfigController.getUserFile(player.getUniqueId().toString())), ConfigController.getUserFile(player.getUniqueId().toString())));
    }
}
