package lnwwaveza008.lnwzadeath.timermanager;

import lnwwaveza008.lnwzadeath.LnwzaDeath;
import lnwwaveza008.lnwzadeath.datasaver.playerdropsdata;
import lnwwaveza008.lnwzadeath.datasaver.plugindata;
import lnwwaveza008.lnwzadeath.events.deathevent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static org.bukkit.Material.AIR;

public class TimerManagerMain {

    public TimerManagerMain(LnwzaDeath cd){
        new BukkitRunnable(){
            @Override
            public void run(){
                //cd.getServer().getConsoleSender().sendMessage(PlayerCooldown.toString());
                if (plugindata.Check()) {
                    if (plugindata.get().getConfigurationSection("Time") != null) {
                        for (String key : plugindata.get().getConfigurationSection("Time").getKeys(false)) {
                            Long endtime = Long.valueOf(plugindata.get().get("Time." + key + ".End").toString());
                            if (System.currentTimeMillis() >= endtime) {
                                //Removev Data
                                plugindata.get().set("Time." + key, null);
                                plugindata.save();
                                //Set Data
                                playerdropsdata.load(Bukkit.getPlayer(UUID.fromString(key)));
                                //Check Is Chest is now removed or not?
                                if (playerdropsdata.get().getLocation("ChestBlock").getBlock().getType().equals(Material.CHEST)) {
                                    Location loc = playerdropsdata.get().getLocation("ChestBlock");
                                    Chest chest = (Chest) loc.getBlock().getState();
                                    if (!(chest.getInventory().isEmpty())) {
                                        List<ItemStack> drops = Arrays.asList(chest.getInventory().getContents());
                                        deathevent deathevnt = new deathevent();
                                        playerdropsdata.get().set("TradePrice", deathevnt.getFirsttradeprice());
                                        playerdropsdata.get().set("DeathLoot", drops);
                                        playerdropsdata.save();
                                        //Set Block
                                        if (loc.getBlock().getType().equals(Material.CHEST)) {
                                            loc.getBlock().setType(AIR);
                                        }
                                        //Message
                                        Bukkit.getPlayer(UUID.fromString(key)).sendMessage(deathevnt.getChestTimeOut());
                                    }
                                }
                                continue;
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(cd, 0, 20);
    }

    public void addPlayerToCD(Player player, Integer time){
        Long endtime = System.currentTimeMillis() + (1000 * time);
        plugindata.get().set("Time." + player.getUniqueId() + ".End",endtime);
        plugindata.save();
    }

    //public boolean isPlayerCD(Player player){
       // return PlayerCooldown.containsKey(player.getUniqueId());
    //}
}
