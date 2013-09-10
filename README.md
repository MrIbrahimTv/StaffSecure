StaffSecure By Harry5573

What it does?

It gives you the option to make your staff choose a password they must enter when they login. But does not make players have one. This can prevent staff accounts from being hacked. Say you want your staff to be secure incase there account got hacked? Well now they can be safer!

If you want a player to have to set a password when they login give them the following permission:
- staffsecure.staff

If you want a player to have access to staffsecure admins commands give them the following permission:
- staffsecure.admin

Installation: 
1) Download the securestaff.jar from bukkit 
2) Drag it into your servers plugins folder 
3) Reload/Restart your server 
4) Give the players you want to have to login the permission 
5) DONE! :)

Commands: 
/password <password> - staffsecure.staff
/login <password> - staffsecure.staff
/staffsecure <reload> - staffsecure.admin

PLEASE NOTE: If a user loses there password they must ask a admin to reset it in the config.yml and then reload the plugin.

Configuration <config.yml>: 

StaffSecure Config By Harry5573
Prefix == Plugins Prefix Before Messages
Motdenable == Should the plugin send a message on every login to the player saying "Server running staff secure by harry5573"
RelogCheck == Should players have to relogin every relog?
prefix: '&f[&cStaffSecure&f]'
motdenable: true
relogcheck: false
Please NOTE: If your player does not have a password set they must set one!

SOURCE CODE: https://github.com/Harry5573OP/StaffSecure

UPCOMING:
- MySQL Storage
- Better Way Of Storing Passwords