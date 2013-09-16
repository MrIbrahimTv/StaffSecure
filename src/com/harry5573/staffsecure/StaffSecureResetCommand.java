/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

import java.io.File;
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
public class StaffSecureResetCommand implements CommandExecutor {
    
    public static StaffSecure plugin;
    
    public StaffSecureResetCommand(StaffSecure instance) {
        this.plugin = instance;
    }
    
    private File usersfile = null;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (player != null) {
            player.sendMessage(plugin.getPrefix() + ChatColor.RED + " That is a console ONLY command!");
            return true;
        }
        
        if (commandLabel.equalsIgnoreCase("resetpassword")) {
            if (args.length != 1) {
                System.out.println("[StaffSecure] Usage: /resetpassword <playername>");
                return true;
            }
            
            if (args.length == 1) {
                
                String playername = args[0];
                Player resetplayer = Bukkit.getPlayerExact(playername);
                
                if (resetplayer == null) {
                    System.out.println("[StaffSecure] We could not find the player " + playername + " !");
                    return true;
                }
                
                usersfile = new File("plugins/StaffSecure/Users", resetplayer.getName() + ".yml");
                usersfile.delete();
                
                resetplayer.kickPlayer(ChatColor.GOLD + "Your password has been reset");
                System.out.println("[StaffSecure] You reset the password for " + playername + " !");
            }
        }
        return false;
    } 
}
