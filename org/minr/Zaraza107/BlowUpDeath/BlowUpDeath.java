package org.minr.Zaraza107.BlowUpDeath;

import java.io.*;
import java.util.HashMap;
import java.lang.String;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * BlowUpDeath for Bukkit
 *
 * @author Zaraza107
 */
public class BlowUpDeath extends JavaPlugin {
	
    private final BlowUpDeathEntityListener entityListener = new BlowUpDeathEntityListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public static Server server;
    public PermissionHandler Permissions;
    public iProperty settings;
    public boolean enable;
    public boolean explode;
    public boolean spawnMob;
    public boolean permissionsEnabled;
    public float explosionPower;
    public String mobSpawned;
    public int mobAmount;

    public BlowUpDeath() {

    }
    
    public void onEnable() {
    	server = getServer();
    	
        PluginManager pm = server.getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DEATH, ((Listener) (entityListener)), Event.Priority.Monitor, ((Plugin) (this)));
        
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( "[" + pdfFile.getName() + "] (version " + pdfFile.getVersion() + ") is enabled!" );

        setupSettings();
    }
    
    public void onDisable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println("Disabling plugin [" + pdfFile.getName() + "] (version " + pdfFile.getVersion() + ")");
    }
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
    
    public void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions)test).getHandler();
                System.out.println("[BlowUpDeath]" + " Permission system enabled.");
                permissionsEnabled = true;
            } else {
                System.out.println("[BlowUpDeath]" + " was unable to enable Permissions system.");
                permissionsEnabled = false;
            }
        }
    }
    
    public void setupSettings() {
    	String path = "plugins" + File.separator;
    	
    	settings = new iProperty(path + "BlowUpDeath.settings");
    	
    	enable = settings.getBoolean("enable-plugin", true);
    	if(!enable)
    		getServer().getPluginManager().disablePlugin(this);
    	explode = settings.getBoolean("explode-on-death", true);
    	explosionPower = settings.getFloat("explosion-power", 4F);
    	//spawnMob = settings.getBoolean("spawn-mobs-on-death", false);
    	//mobSpawned = settings.getString("spawned-mob", "creeper");
    	//mobAmount = settings.getInt("amount-of-mobs", 1);
    	boolean perm = settings.getBoolean("enable-permissions", true);
    	if(perm)
    		setupPermissions();
    	else
    		permissionsEnabled = false;
    }
}

