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

            if (!plugin.getConfig().contains(player.getName())) {
                player.sendMessage(plugin.getPrefix() + ChatColor.RED + " You do not have a password set and you need one. Do /password <pass>");
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " Usage: /login <pass>");
                return true;
            }

            if (args.length == 1) {

                String password = (String) plugin.getConfig().get(player.getName());

                if (!args[0].equalsIgnoreCase(password)) {
                    player.kickPlayer(ChatColor.RED + "Incorrect password");
                    return true;
                }

                player.sendMessage(plugin.getPrefix() + ChatColor.GREEN + " Logged in!");
                plugin.isnotloggedin.remove(player);
                return true;
            }
        }

            if (command.getName().equalsIgnoreCase("password")) {
                if (!player.hasPermission("staffsecure.staff")) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.AQUA + " You do not need to set a password silly!");
                    return true;
                }

                if (plugin.getConfig().contains(player.getName())) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.RED + " You already have a password set! If you have lost it contact an admin");
                    return true;
                }

                if (args.length != 1) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.GOLD + " Usage: /password <pass>");
                    return true;
                }

                if (args.length == 1) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.AQUA + " Password set! You must now /login");
                    plugin.getConfig().set(player.getName(), args[0]);
                    plugin.saveConfig();
                    return true;
                }
            }
        return false;
    }
}
