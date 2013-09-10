/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class StaffSecure extends JavaPlugin {
    
    public static StaffSecure plugin;
    
    //Is not logged in Array
    ArrayList<Player> isnotloggedin = new ArrayList<Player>();
    ArrayList<String> isloggedin = new ArrayList<String>();
    
    //Classes
    public StaffSecureEventListener slistener;
    public StaffSecureCommandListener sexecutor;
    
    //Prefix
    private String prefix = null;

    @Override
    public void onEnable() {

        try {
            System.out.println("=================================");
            System.out.println("[StaffSecure] Loading plugin version " + this.getDescription().getVersion());

            this.saveDefaultConfig();

            this.slistener = new StaffSecureEventListener(this);
            this.sexecutor = new StaffSecureCommandListener(this);

            Bukkit.getServer().getPluginManager().registerEvents(new StaffSecureEventListener(this), this);

            //Convert the null prefix now that we have loaded the config
            prefix = this.getConfig().getString("prefix").replaceAll("(&([a-f0-9]))", "\u00A7$2");
            
            this.isloggedin.clear();
            this.isnotloggedin.clear();
            
            //Commands
            this.getCommand("login").setExecutor(new StaffSecureCommandListener(this));
            this.getCommand("password").setExecutor(new StaffSecureCommandListener(this));
            this.getCommand("staffsecure").setExecutor(new StaffSecureCommandListener(this));

            System.out.println("=================================");
        } catch (Exception ex) {
            System.out.println("[StaffSecure] Error while enabling staffsecure version " + this.getDescription().getVersion());
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        System.out.println("=================================");
        System.out.println("[StaffSecure] Stopping plugin version " + this.getDescription().getVersion());
        System.out.println("=================================");
    }
    
    public String getPrefix() {
        return this.prefix;
    }
}
