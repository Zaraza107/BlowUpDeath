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
    public boolean permissionsEnabled;
    public float explosionPower;

    public BlowUpDeath(PluginLoader pluginLoader, Server instance,
            PluginDescriptionFile desc, File folder, File plugin,
            ClassLoader cLoader) throws IOException {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);

        server = instance;
    }
    
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
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
    	explosionPower = settings.getFloat("explosion-power", 4F);
    	boolean perm = settings.getBoolean("enable-permissions", true);
    	if(perm)
    		setupPermissions();
    	else
    		permissionsEnabled = false;
    }
}

