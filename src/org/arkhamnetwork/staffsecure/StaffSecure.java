/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure;

import org.arkhamnetwork.staffsecure.struct.StaffSecureUser;
import java.util.HashMap;
import org.arkhamnetwork.staffsecure.commands.CmdExecutor;
import org.arkhamnetwork.staffsecure.controllers.ConfigController;
import org.arkhamnetwork.staffsecure.controllers.PlayerController;
import org.arkhamnetwork.staffsecure.listeners.PlayerListener;
import org.arkhamnetwork.staffsecure.struct.Config;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class StaffSecure extends JavaPlugin {

    private static StaffSecure plugin;

    public static StaffSecure get() {
        return plugin;
    }

    public HashMap<String, StaffSecureUser> users = new HashMap<>();
    public Config configuration = null;

    @Override
    public void onEnable() {
        plugin = this;
        try {

            users.clear();

            log("Loading plugin version " + this.getDescription().getVersion());

            ConfigController.onEnable();

            registerCommands();
            registerListeners();

            for (Player player : getServer().getOnlinePlayers()) {
                PlayerController.handleLogin(player);
            }

        } catch (Exception ex) {
            log("Error while enabling staffsecure version " + this.getDescription().getVersion());
        }
    }

    @Override
    public void onDisable() {
        log("Stopping plugin version " + this.getDescription().getVersion());
    }

    public void log(String message) {
        getLogger().info(message);
    }

    private void registerCommands() {
        getCommand("login").setExecutor(new CmdExecutor());
        getCommand("staffsecure").setExecutor(new CmdExecutor());
        getCommand("password").setExecutor(new CmdExecutor());
        getCommand("resetpassword").setExecutor(new CmdExecutor());
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerListener(), this);
    }

}
