/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.controllers;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import org.arkhamnetwork.staffsecure.StaffSecure;
import org.arkhamnetwork.staffsecure.struct.Config;
import org.arkhamnetwork.staffsecure.utils.MessageUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author devan_000
 */
public class ConfigController {

      private static final StaffSecure plugin = StaffSecure.get();
      private static final File usersFolder = new File(plugin.getDataFolder() + File.separator + "Users");

      public static void onEnable() {
            plugin.saveDefaultConfig();

            if (!usersFolder.exists()) {
                  usersFolder.mkdir();
            }

            plugin.configuration = getConfig(plugin.getConfig());
      }

      private static Config getConfig(FileConfiguration baseConfig) {
            return new Config(MessageUtils.translateToColorCode(baseConfig.getString("messages.prefix")), baseConfig.getBoolean("motdenable"), baseConfig.getBoolean("forcelogin.relogipchange"), baseConfig.getBoolean("forcelogin.relog"));
      }

      public static File getUserFile(String userUUID) {
            return new File(usersFolder, userUUID + ".yml");
      }

      public static void createUserFile(String userUUID) {
            try {
                  getUserFile(userUUID).createNewFile();
                  FileConfiguration userConfig = YamlConfiguration.loadConfiguration(getUserFile(userUUID));

                  userConfig.set("lastloggedip", InetAddress.getByName("0.0.0.0"));
                  userConfig.set("loggedintolastloggedip", Boolean.FALSE);
            } catch (IOException ex) {
            }
      }
}
