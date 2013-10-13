/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 *
 * @author Harry5573
 */
public class CommandChecks implements Listener {
    
    public static StaffSecure plugin;
    
    public CommandChecks(StaffSecure instance) {
        this.plugin = instance;
    }
    
    @EventHandler(priority= EventPriority.LOWEST)
    public void PlayerCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        
        Boolean isChecksOn = plugin.getConfig().getBoolean("commandchecks");
        
        //Check that the checks enabled
        if (isChecksOn == false) {
            return;
        }
        
        String[] split = msg.split(" ");
        
        if (!split[0].equalsIgnoreCase("/op")) {
            return;
        }
        
        if (split.length != 3) {
            p.sendMessage(plugin.getPrefix() + ChatColor.RED + " Usage: /op <name> <password>");
            e.setCancelled(true);
            return;
        }
        String passwordop = (String) plugin.getConfig().get("passwordop");
        
        if (split[2].equalsIgnoreCase(passwordop)) {
            p.sendMessage(plugin.getPrefix() + ChatColor.GREEN + " Used OP command succesfuly.");
            e.setCancelled(true);
            //Send the op command after we cancel
            
            String playertoop = split[1];
            Bukkit.getServer().dispatchCommand(p, "op " + playertoop);
            return;
        }
        p.sendMessage(plugin.getPrefix() + ChatColor.RED + " Incorrect password");
        e.setCancelled(true);

    }
    
}
