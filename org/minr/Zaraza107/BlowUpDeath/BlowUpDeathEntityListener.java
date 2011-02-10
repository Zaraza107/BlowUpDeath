package org.minr.Zaraza107.BlowUpDeath;

import java.lang.reflect.Field;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.CraftWorld;
import net.minecraft.server.WorldServer;

public class BlowUpDeathEntityListener extends EntityListener{

	private final BlowUpDeath plugin;
	
    public BlowUpDeathEntityListener(final BlowUpDeath plugin) {
        this.plugin = plugin;
    }
    
    public void onEntityDeath(EntityDeathEvent event) {
    	if(plugin.enable && event.getEntity() instanceof Player) {
    		Player player = (Player)event.getEntity();
    		if(plugin.Permissions.has(player, "blow.up.death")) {
    			try {
    				CraftWorld w = (CraftWorld)player.getWorld();
    				Field f = CraftWorld.class.getDeclaredField("world");
    		
    				f.setAccessible(true);
    				WorldServer world = (WorldServer)f.get(w);
    			
    				double x = (double)player.getLocation().getX();
    				double y = (double)player.getLocation().getY();
    				double z = (double)player.getLocation().getZ();
    		
    				world.a(null, x, y, z, plugin.explosionPower);
    			
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
}
