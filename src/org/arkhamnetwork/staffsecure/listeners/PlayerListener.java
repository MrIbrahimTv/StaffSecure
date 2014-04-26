/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.listeners;

import org.arkhamnetwork.staffsecure.StaffSecure;
import org.arkhamnetwork.staffsecure.controllers.PlayerController;
import org.arkhamnetwork.staffsecure.struct.StaffSecureUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author Harry5573
 */
public class PlayerListener implements Listener {
    
    private final StaffSecure plugin = StaffSecure.get();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureJoin(PlayerJoinEvent e) {
        PlayerController.handleLogin(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureMove(PlayerMoveEvent e) {
        if ((e.getFrom().getBlockX() == e.getTo().getBlockX()) && (e.getFrom().getBlockZ() == e.getTo().getBlockZ())) {
            return;
        }

        StaffSecureUser user = plugin.users.get(e.getPlayer().getUniqueId().toString());

        if (user == null || user.isLoggedIn()) {
            return;
        }

        e.setTo(e.getFrom());
        e.getPlayer().sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSecureDropItem(PlayerDropItemEvent e) {
        StaffSecureUser user = plugin.users.get(e.getPlayer().getUniqueId().toString());

        if (user == null || user.isLoggedIn()) {
            return;
        }

        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSecureBreak(BlockBreakEvent e) {
        StaffSecureUser user = plugin.users.get(e.getPlayer().getUniqueId().toString());

        if (user == null || user.isLoggedIn()) {
            return;
        }

        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSecurePlace(BlockPlaceEvent e) {
        StaffSecureUser user = plugin.users.get(e.getPlayer().getUniqueId().toString());

        if (user == null || user.isLoggedIn()) {
            return;
        }

        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        if ((e.getMessage() == null || e.getMessage().split(" ")[0].toLowerCase().equals("/login") || e.getMessage().split(" ")[0].toLowerCase().equals("/password") || e.getMessage().split(" ")[0].toLowerCase().equals("/staffsecure"))) {
            return;
        }

        StaffSecureUser user = plugin.users.get(e.getPlayer().getUniqueId().toString());

        if (user == null || user.isLoggedIn()) {
            return;
        }

        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSecureChat(AsyncPlayerChatEvent e) {
        StaffSecureUser user = plugin.users.get(e.getPlayer().getUniqueId().toString());

        if (user == null || user.isLoggedIn()) {
            return;
        }

        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You need to login! /login <password>");
    }
}
