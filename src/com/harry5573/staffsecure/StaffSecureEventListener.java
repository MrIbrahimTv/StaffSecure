/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

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
public class StaffSecureEventListener implements Listener {
    
    public static StaffSecure plugin;
    
    public StaffSecureEventListener(StaffSecure instance) {
        this.plugin = instance;
    }
    
    //
    //Login passblock
    //
    @EventHandler(priority= EventPriority.HIGHEST)
    public void onSecureJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        
        Boolean isMotdOn = plugin.getConfig().getBoolean("motdenable");
        
        if (isMotdOn) {
            player.sendMessage(ChatColor.GREEN + "This server is running staffsecure by harry5573!");
        }
        
        //Return if they do not have the needed permission
        if (!player.hasPermission("staffsecure.staff")) {
            return;
        }
        
        plugin.fileCheckPlayer(player);
        
        Boolean isCheckOn = plugin.getConfig().getBoolean("relogcheck");
    
        //Dont addthem if there allready in and checks off
        if (plugin.isLoggedIn.contains(player.getName()) && (isCheckOn == false)) {
            return;
        }

        if (!plugin.isNotLoggedIn.contains(player.getName())) {
            plugin.isNotLoggedIn.add(player.getName());
        }

        if (!plugin.getUserFile(player).contains("password")) {
            player.sendMessage(plugin.getPrefix() + ChatColor.RED + " You do not have a password set and you need one. Do /password <pass>");
            return;
        }

        player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " Do /login <password> to regain your powers!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSecureMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (plugin.isNotLoggedIn.contains(p.getName())) {
            if ((e.getFrom().getBlockX() != e.getTo().getBlockX()) || (e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
                p.teleport(e.getFrom());
                p.sendMessage(plugin.getPrefix() + ChatColor.RED + " You need to login! /login <password>");
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (plugin.isNotLoggedIn.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(plugin.getPrefix() + ChatColor.RED + " You need to login! /login <password>");
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (plugin.isNotLoggedIn.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(plugin.getPrefix() + ChatColor.RED + " You need to login! /login <password>");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecurePlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (plugin.isNotLoggedIn.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(plugin.getPrefix() + ChatColor.RED + " You need to login! /login <password>");
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        
        if ((e.getMessage().contains("login") || e.getMessage().contains("password"))) {
            return;
        }
        
        if (plugin.isNotLoggedIn.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(plugin.getPrefix() + ChatColor.RED + " You need to login! /login <password>");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSecureChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (plugin.isNotLoggedIn.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(plugin.getPrefix() + ChatColor.RED + " You need to login! /login <password>");
        }
    }
}
