/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

import static com.harry5573.staffsecure.StaffSecureEventListener.plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class StaffSecureCommandListener implements CommandExecutor {

    public static StaffSecure plugin;

    public StaffSecureCommandListener(StaffSecure instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (player == null) {
            System.out.println("[StaffSecure] That is not a console command.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("login")) {
            if (!player.hasPermission("staffsecure.staff")) {
                player.sendMessage(plugin.getPrefix() + ChatColor.AQUA + " You do not need to loggin silly!");
                return true;
            }
  
            if (!plugin.isNotLoggedIn.contains(player.getName())) {
                player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " You are already logged in!");
                return true;
            }


            if (!plugin.getUserFile(player).contains("password")) {
                player.sendMessage(plugin.getPrefix() + ChatColor.RED + " You do not have a password set and you need one. Do /password <pass>");
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " Usage: /login <pass>");
                return true;
            }

            if (args.length == 1) {
                String attemptpass = args[0];
                plugin.attemptLogin(player, attemptpass);
            }
        }
        
        if (command.getName().equalsIgnoreCase("staffsecure")) {
            
            if (!player.hasPermission("staffsecure.admin")) {
                player.sendMessage(plugin.getPrefix() + ChatColor.AQUA + " You do not have permission to use staffsecure commands.");
                return true;
            }
            
            if (args.length != 1) {
                player.sendMessage(plugin.getPrefix() + ChatColor.RED + " Usage: /staffsecure <reload>");
                return true;
            }
            
            if (args.length == 1) {
                if (args[0].contains("reload")) {
                    plugin.reloadConfig();
                    player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " Plugins config reloaded!");
                    return true;
                } else {
                    player.sendMessage(plugin.getPrefix() + ChatColor.RED + " Usage: /staffsecure <reload>");
                    return true;
                }
            }
        }

            if (command.getName().equalsIgnoreCase("password")) {
                if (!player.hasPermission("staffsecure.staff")) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.AQUA + " You do not need to set a password silly!");
                    return true;
                }
                
                if (!plugin.isNotLoggedIn.contains(player.getName())) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " You are already logged in!");
                    return true;
                }

                if (plugin.getUserFile(player).contains("password")) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.RED + " You already have a password set! If you have lost it contact an admin");
                    return true;
                }

                if (args.length != 1) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " Usage: /password <pass>");
                    return true;
                }

                if (args.length == 1) {
                    plugin.setPassword(player, args[0]);
                    return true;
                }
            }
        return false;
    }
}
