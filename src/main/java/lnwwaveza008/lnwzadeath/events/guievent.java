package lnwwaveza008.lnwzadeath.events;

import com.google.common.collect.Iterables;
import lnwwaveza008.lnwzadeath.LnwzaDeath;
import lnwwaveza008.lnwzadeath.datasaver.playerdropsdata;
import lnwwaveza008.lnwzadeath.datasaver.plugindata;
import net.objecthunter.exp4j.Expression;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class guievent implements Listener {
    private static Plugin pl = LnwzaDeath.getPlugin(LnwzaDeath.class);

    @EventHandler(priority = EventPriority.HIGH)
    public static void onDeathGuiClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "LnwzaDeath's Recovery")){
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (e.getRawSlot() < 45){
                if (e.getCurrentItem() != null){
                    ItemStack ClickedItem = e.getCurrentItem();
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                    playerdropsdata.load(p);
                    if (playerdropsdata.get().getInt("DeathPoints") >= playerdropsdata.get().getInt("TradePrice")){
                        deathevent deathevent = new deathevent();
                        //Remove Point
                        playerdropsdata.get().set("DeathPoints", playerdropsdata.get().getInt("DeathPoints") - playerdropsdata.get().getInt("TradePrice"));
                        if (deathevent.getuptradeprice() == true) {
                            Expression ex = deathevent.getTradepriceex();
                            Integer price = (int) ex.setVariable("x", playerdropsdata.get().getInt("TradePrice")).evaluate();
                            playerdropsdata.get().set("TradePrice",price);
                        }
                        //Get Item
                        ItemStack item = ClickedItem.clone();
                        ItemMeta itemmeta = item.getItemMeta();
                        if (itemmeta.getLore().size() == deathevent.getItemLores().size()){
                            itemmeta.setLore(null);
                        }else {
                            List<String> itemlore = itemmeta.getLore();
                            //Integer oldlore = itemmeta.getLore().size() - deathevent.getItemLores().size();
                            for (int i = 0; i <= deathevent.getItemLores().size(); i++) {
                                itemlore.remove(itemlore.size() - 1);
                            }
                            itemmeta.setLore(itemlore);
                        }
                        item.setItemMeta(itemmeta);
                        //RemoveItem
                        playerdropsdata.get().getList("DeathLoot").remove(item);
                        e.getCurrentItem().setAmount(0);
                        //
                        playerdropsdata.save();
                        p.getInventory().addItem(item);
                        //Set Coinviewer
                        ItemStack coinviewer = deathevent.getCoinviewerItem();
                        List<String> lore = new ArrayList<>();
                        for(String s : coinviewer.getItemMeta().getLore()) {
                            lore.add(s.replace("{X}",playerdropsdata.get().getString("DeathPoints")));
                        }
                        coinviewer.setLore(lore);
                        e.getInventory().setItem(49, coinviewer);
                        //Reset Item Price
                        ItemStack[] dropsitem = playerdropsdata.get().getList("DeathLoot").toArray(new ItemStack[0]);
                        for (int i = 0; i < 45; i++){
                            e.getInventory().setItem(i, null);
                        }
                        for (int i = 0; i < dropsitem.length; i++) {
                            if (dropsitem[i] != null){
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
                                    ArrayList<String> lorenew = new ArrayList();
                                    List<String> newlore = deathevent.getItemLores();
                                    for(String s : newlore) {
                                        lorenew.add(s.replace("{X}",playerdropsdata.get().getString("TradePrice")));
                                    }
                                    dropsmeta.setLore(lorenew);
                                }
                                dropsitem[i].setItemMeta(dropsmeta);
                                e.getInventory().setItem(i, dropsitem[i]);
                            }
                        }
                        //
                        p.sendMessage(deathevent.getTradeComplete());
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                    }else{
                        deathevent deathevent = new deathevent();
                        p.sendMessage(deathevent.getDontHaveDeathCoins());
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    }
                }
            }
        }
    }
}
