/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.staffsecure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class StaffSecure extends JavaPlugin {

    public static StaffSecure plugin;
    //Is not logged in Array
    public List<String> isNotLoggedIn = new ArrayList<String>();
    public List<String> isLoggedIn = new ArrayList<String>();
    //Classes
    public StaffSecureEventListener slistener;
    public StaffSecureCommandListener sexecutor;
    //Prefix
    private String prefix = null;
    //Users folder
    File userfolder;
    //Userdate files
    private FileConfiguration users = null;
    private File usersfile = null;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            System.out.println("=================================");
            System.out.println("[StaffSecure] Loading plugin version " + this.getDescription().getVersion());

            this.saveDefaultConfig();

            this.slistener = new StaffSecureEventListener(this);
            this.sexecutor = new StaffSecureCommandListener(this);

            Bukkit.getServer().getPluginManager().registerEvents(new StaffSecureEventListener(this), this);
            Bukkit.getServer().getPluginManager().registerEvents(new CommandChecks(this), this);

            //Convert the null prefix now that we have loaded the config
            prefix = this.getConfig().getString("prefix").replaceAll("(&([a-f0-9]))", "\u00A7$2");

            this.isNotLoggedIn.clear();

            //Commands
            this.getCommand("login").setExecutor(new StaffSecureCommandListener(this));
            this.getCommand("password").setExecutor(new StaffSecureCommandListener(this));
            this.getCommand("staffsecure").setExecutor(new StaffSecureCommandListener(this));
            this.getCommand("resetpassword").setExecutor(new StaffSecureResetCommand(this));

            //Folder
            this.setupFolders();

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

    public void setupFolders() {
        userfolder = new File(this.getDataFolder() + File.separator + "Users");
        if (!userfolder.exists()) {
            System.out.println("[StaffSecure] Users folder did not exist.... Creating");
            userfolder.mkdirs();
            System.out.println("[StaffSecure] Users folder created!");
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void attemptLogin(Player p, String password) {
        String passwordhash = (String) this.getUserFile(p).get("password");
        String saidpasswordhash = StaffSecureMethods.getMD5(password);
        
        if (!passwordhash.equalsIgnoreCase(saidpasswordhash)) {
            p.kickPlayer(ChatColor.RED + "Incorrect password");
            return;
        }
        this.loginPlayer(p);
    }

    public void loginPlayer(Player p) {
        p.sendMessage(this.getPrefix() + ChatColor.GREEN + " Logged in!");
        
        this.isNotLoggedIn.remove(p.getName());
        this.isLoggedIn.add(p.getName());
    }

    public void fileCheckPlayer(Player p) {
        usersfile = new File("plugins/StaffSecure/Users", p.getName() + ".yml");
        if (!usersfile.exists()) {
            try {
                usersfile.createNewFile();
            } catch (IOException ex) {
                System.out.println("[StaffSecure] Could not create users.yml");
            }
        }
    }

    public FileConfiguration getUserFile(Player p) {
        usersfile = new File("plugins/StaffSecure/Users", p.getName() + ".yml");
        users = YamlConfiguration.loadConfiguration(usersfile);
        return users;
    }

    public void saveUserFile(Player p) {
        usersfile = new File("plugins/StaffSecure/Users", p.getName() + ".yml");

        this.saveFile(usersfile, users);
    }

    public static void saveFile(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setPassword(Player p, String password) {
        String passwordnew = StaffSecureMethods.getMD5(password);
        this.getUserFile(p).set("password", passwordnew);
        this.saveUserFile(p);
        
        p.sendMessage(plugin.getPrefix() + ChatColor.AQUA + " Password set! You must now /login");
    }
}
