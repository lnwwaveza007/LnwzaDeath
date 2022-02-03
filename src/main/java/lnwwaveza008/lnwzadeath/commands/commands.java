package lnwwaveza008.lnwzadeath.commands;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import lnwwaveza008.lnwzadeath.LnwzaDeath;
import lnwwaveza008.lnwzadeath.datasaver.playerdropsdata;
import lnwwaveza008.lnwzadeath.events.deathevent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class commands implements CommandExecutor {

    private static Plugin pl = LnwzaDeath.getPlugin(LnwzaDeath.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0){
            deathevent deathevent = new deathevent();
            sender.sendMessage(deathevent.getWrongCommands());
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            deathevent deathevent = new deathevent();
            //Check Perm
            if (sender instanceof Player){
                if (!(sender.hasPermission("lnwzadeath.admin"))){
                    sender.sendMessage(deathevent.getNoPermsMsg());
                    return true;
                }
            }
            //
            pl.reloadConfig();
            deathevent.onReload();
            sender.sendMessage(deathevent.getReloadMsg());
        }else if (args[0].equalsIgnoreCase("gui")){
            if (sender instanceof Player){
                deathevent deathevent = new deathevent();
                Player p = (Player) sender;
                //Check Deathpoints & TradePrice
                playerdropsdata.load(p.getPlayer());
                if (playerdropsdata.get().get("DeathPoints") == null){
                    playerdropsdata.get().set("DeathPoints", deathevent.getStarterpoints());
                    playerdropsdata.save();
                }
                if (playerdropsdata.get().get("TradePrice") == null){
                    playerdropsdata.get().set("TradePrice", deathevent.getFirsttradeprice());
                    playerdropsdata.save();
                }
                Inventory gui = Bukkit.createInventory(p, 54, ChatColor.AQUA + "LnwzaDeath's Recovery");
                //Set and Get Drop Items
                if (playerdropsdata.Check(p.getPlayer())) {
                    if (playerdropsdata.get().getList("DeathLoot") != null) {
                        ItemStack[] dropsitem = playerdropsdata.get().getList("DeathLoot").toArray(new ItemStack[0]);
                        for (int i = 0; i < dropsitem.length; i++) {
                            if (dropsitem[i] != null) {
                                ItemMeta dropsmeta = dropsitem[i].getItemMeta();
                                List<String> lores = dropsmeta.getLore();
                                if (lores != null) {
                                    lores.add(" ");
                                    List<String> newlore = deathevent.getItemLores();
                                    for(String s : newlore) {
                                        lores.add(s.replace("{X}",playerdropsdata.get().getString("TradePrice")));
                                    }
                                    dropsmeta.setLore(lores);
                                } else {
                                    ArrayList<String> lore = new ArrayList();
                                    List<String> newlore = deathevent.getItemLores();
                                    for(String s : newlore) {
                                        lore.add(s.replace("{X}",playerdropsdata.get().getString("TradePrice")));
                                    }
                                    dropsmeta.setLore(lore);
                                }
                                dropsitem[i].setItemMeta(dropsmeta);
                            }
                        }
                        gui.setContents(dropsitem);
                    }
                }else{
                    playerdropsdata.create(p.getPlayer());
                }
                //Set Skull
                ItemStack coinviewer = deathevent.getCoinviewerItem();
                List<String> lore = new ArrayList<>();
                for(String s : coinviewer.getItemMeta().getLore()) {
                    lore.add(s.replace("{X}",playerdropsdata.get().getString("DeathPoints")));
                }
                coinviewer.setLore(lore);
                //Set Frame
                if (deathevent.getDisableblackglass() == false) {
                    ItemStack blackglass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    ItemMeta blackglassmeta = blackglass.getItemMeta();
                    blackglassmeta.setDisplayName(ChatColor.WHITE + " ");
                    blackglass.setItemMeta(blackglassmeta);
                    for (int i = 45; i < 54; i++) {
                        gui.setItem(i, blackglass);
                    }
                }
                gui.setItem(49, coinviewer);
                p.openInventory(gui);
            }
        }else if (args[0].equalsIgnoreCase("set")) {
            deathevent deathevent = new deathevent();
            //Check Perms
            if (sender instanceof Player) {
                if (!(sender.hasPermission("lnwzadeath.admin"))) {
                    sender.sendMessage(deathevent.getNoPermsMsg());
                    return true;
                }
            }
            //
            if (args.length == 3) {
                if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
                    Player p = Bukkit.getPlayer(args[1]);
                    if (Integer.valueOf(args[2]) >= 0) {
                        playerdropsdata.load(p);
                        playerdropsdata.get().set("DeathPoints", Integer.valueOf(args[2]));
                        playerdropsdata.save();
                        sender.sendMessage(deathevent.getSetComplete().replace("{X}", args[1]));
                    } else {
                        sender.sendMessage(deathevent.getCantSetThat());
                    }
                } else {
                    sender.sendMessage(deathevent.getPlayerDontOnline());
                }
            } else {
                sender.sendMessage(deathevent.getWrongCommands());
            }
        }else if (args[0].equalsIgnoreCase("Help")){
            deathevent deathevent = new deathevent();
            for (String s : deathevent.getHelpCommands()){
                sender.sendMessage(s);
            }
        }else{
            deathevent deathevent = new deathevent();
            sender.sendMessage(deathevent.getWrongCommands());
        }
        return true;
    }

}
