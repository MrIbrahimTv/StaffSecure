/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.commands;

import org.arkhamnetwork.staffsecure.StaffSecure;
import org.arkhamnetwork.staffsecure.controllers.ConfigController;
import org.arkhamnetwork.staffsecure.struct.StaffSecureUser;
import org.arkhamnetwork.staffsecure.utils.EncryptionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author devan_000
 */
public class CmdExecutor implements CommandExecutor {
    
    private final StaffSecure plugin = StaffSecure.get();
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        boolean isSenderPlayer = sender instanceof Player;
        
        if (commandLabel.toLowerCase().equals("login")) {
            if (!isSenderPlayer) {
                plugin.log("That is not a console command.");
                return true;
            }
            
            Player player = (Player) sender;
            
            if (!player.hasPermission("staffsecure.staff")) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.AQUA + " You do not need to login.");
                return true;
            }
            
            StaffSecureUser user = plugin.users.get(player.getUniqueId().toString());
            
            if (user.isLoggedIn()) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GOLD + " You are already logged in!");
                return true;
            }
            
            if (user.getConfig().getEncryptedPassword() == null) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You do not have a password set and you need one. Do /password <pass>");
                return true;
            }
            
            if (args.length != 1) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GOLD + " Usage: /login <pass>");
                return true;
            }
            
            if (args.length == 1) {
                if (!user.getConfig().getEncryptedPassword().equals(EncryptionUtils.getMD5(args[0]))) {
                    player.kickPlayer(ChatColor.RED + "You entered an incorrect password");
                    return true;
                }
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GREEN + " Logged in!");
                plugin.users.get(player.getUniqueId().toString()).setLoggedIn(true);
                return true;
            }
        }
        
        if (command.getName().equalsIgnoreCase("password")) {
            if (!isSenderPlayer) {
                plugin.log("That is not a console command.");
                return true;
            }
            
            Player player = (Player) sender;
            
            if (!player.hasPermission("staffsecure.staff")) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.AQUA + " You do not need to set a password!");
                return true;
            }
            
            StaffSecureUser user = plugin.users.get(player.getUniqueId().toString());
            
            if (user.isLoggedIn()) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GOLD + " You are already logged in!");
                return true;
            }
            
            if (user.getConfig().getEncryptedPassword() != null) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " You already have a password set! If you have lost it contact an admin");
                return true;
            }
            
            if (args.length != 1) {
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GOLD + " Usage: /password <pass>");
                return true;
            }
            
            if (args.length == 1) {
                plugin.users.get(player.getUniqueId().toString()).getConfig().setPassword(EncryptionUtils.getMD5(args[0]));
                player.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.AQUA + " Password set! You must now /login");
                return true;
            }
        }
        
        if (commandLabel.toLowerCase().equals("staffsecure")) {
            if (!sender.hasPermission("staffsecure.admin")) {
                sender.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.AQUA + " You do not have permission to use staffsecure commands.");
                return true;
            }
            
            if (args.length != 1) {
                sender.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " Usage: /staffsecure <reload>");
                return true;
            }
            
            if (args.length == 1) {
                if (args[0].contains("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.GOLD + " Plugins config reloaded!");
                    return true;
                } else {
                    sender.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " Usage: /staffsecure <reload>");
                    return true;
                }
            }
        }

        if (commandLabel.equalsIgnoreCase("resetpassword")) {
            if (isSenderPlayer) {
                sender.sendMessage(plugin.configuration.getMessagePrefix() + ChatColor.RED + " That is not a player command.");
                return true;
            }

            if (args.length != 1) {
                plugin.log("Usage: /resetpassword <playername>");
                return true;
            }

            if (args.length == 1) {
                Player toReset = Bukkit.getPlayerExact(args[0]);

                if (toReset == null) {
                    plugin.log("We could not find the player " + args[0] + " !");
                    return true;
                }

                ConfigController.getUserFile(toReset.getUniqueId().toString()).delete();
                toReset.kickPlayer(ChatColor.GOLD + "Your password has been reset");

                plugin.users.remove(toReset.getUniqueId().toString());

                plugin.log("You reset the password for " + toReset.getName() + " !");
            }
        }

        return false;
    }

}
