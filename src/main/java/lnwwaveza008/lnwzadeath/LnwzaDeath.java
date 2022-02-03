package lnwwaveza008.lnwzadeath;

import lnwwaveza008.lnwzadeath.commands.commands;
import lnwwaveza008.lnwzadeath.commands.tabcomplete;
import lnwwaveza008.lnwzadeath.datasaver.plugindata;
import lnwwaveza008.lnwzadeath.events.deathevent;
import lnwwaveza008.lnwzadeath.events.guievent;
import lnwwaveza008.lnwzadeath.timermanager.TimerManagerMain;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class LnwzaDeath extends JavaPlugin {

    TimerManagerMain timerManagerMain;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "=====================================================");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "              LnwzaDeath just started!");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "                  Version : 1.0");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "=====================================================");
        //Commands
        getCommand("LnwzaDeath").setExecutor(new commands());
        getCommand("LnwzaDeath").setTabCompleter(new tabcomplete());
        //Event
        getServer().getPluginManager().registerEvents(new deathevent(), this);
        getServer().getPluginManager().registerEvents(new guievent(), this);
        //Config
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        if (!(plugindata.Check())){
            plugindata.create();
        }
        //Reload
        deathevent deathevent = new deathevent();
        deathevent.onReload();
        //Cooldown
        timerManagerMain = new TimerManagerMain(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "=====================================================");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "              LnwzaDeath just stopped!");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "                  Version : 1.0");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "=====================================================");
    }

    public TimerManagerMain getTimer(){
        return timerManagerMain;
    }

}
