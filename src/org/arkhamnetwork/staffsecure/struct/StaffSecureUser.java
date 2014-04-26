/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arkhamnetwork.staffsecure.struct;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author devan_000
 */
public class StaffSecureUser {

    @Getter
    private String playerName;
    @Getter
    private String playerUUID;
    @Getter
    private StaffSecureUserConfig config;
    @Getter
    @Setter
    private boolean isLoggedIn;

    public StaffSecureUser(String playerName, String playerUUID, StaffSecureUserConfig config) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.config = config;
        this.isLoggedIn = false;
    }

}
