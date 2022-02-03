package lnwwaveza008.lnwzadeath.datasaver;

import lnwwaveza008.lnwzadeath.LnwzaDeath;
import lnwwaveza008.lnwzadeath.events.deathevent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class plugindata {
    private static Plugin pl = LnwzaDeath.getPlugin(LnwzaDeath.class);
    static File cfile;
    static FileConfiguration config;
    static File folder = new File("plugins/LnwzaDeath/");
    static File df = pl.getDataFolder();

    public static void create() {
        cfile = new File("plugins/LnwzaDeath/Data.yml");
        if (!df.exists()) df.mkdir();
        if (!folder.exists()) folder.mkdir();
        if (!cfile.exists()) {
            try {
                cfile.createNewFile();
            } catch(Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error creating " + cfile.getName() + "!");
            }
        }
        config = YamlConfiguration.loadConfiguration(cfile);
        config.set("warnings", "0");
    }

    public static Boolean Check(){
        cfile = new File("plugins/LnwzaDeath/Data.yml");
        if (!cfile.exists()){
            return false;
        }else{
            return  true;
        }
    }

    public static File getfolder() {
        return folder;
    }

    public static File getfile() {
        return cfile;
    }

    public static FileConfiguration get() {
        cfile = new File("plugins/LnwzaDeath/Data.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        return config;
    }

    public static void save() {
        try {
            config.save(cfile);
        } catch(Exception e) {
            deathevent deathevent = new deathevent();
            Bukkit.broadcast(deathevent.getPluginPrefix()+ChatColor.RED + "Error saving " + cfile.getName() + "!", "ChatColor.ErrorMsgs");
        }
    }
}
