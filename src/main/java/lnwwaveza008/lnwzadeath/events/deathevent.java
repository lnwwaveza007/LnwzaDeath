package lnwwaveza008.lnwzadeath.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import lnwwaveza008.lnwzadeath.LnwzaDeath;
import lnwwaveza008.lnwzadeath.datasaver.playerdropsdata;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import net.objecthunter.exp4j.Expression;
import java.util.*;

public class deathevent implements Listener {

    private static Plugin pl = LnwzaDeath.getPlugin(LnwzaDeath.class);
    //Config Value
    private static List<String> blacklistworld = null;
    private static Boolean allowopendc = null;
    private static Boolean sentwhendestory = null;
    private static Boolean removeitemwhendeathagain = null;
    private static Boolean uptradeprice = null;
    private static Boolean disableblackglass = null;
    private static Boolean expdropwhendeath = null;
    private static Integer dctimeout = 0;
    private static Integer staterdeathpoints = 0;
    private static Integer firsttradeprice = 0;
    private static Expression tradepriceex = null;
    //Message Value
    private static String PluginPrefix = null;
    private static String ReloadMsg = null;
    private static String NoPermsMsg = null;
    private static String RemoveOldChest1 = null;
    private static String RemoveOldChest2 = null;
    private static String ChestTimeOut = null;
    private static String WhenBreakChest = null;
    private static String CancelInteract = null;
    private static String TradeComplete = null;
    private static String DontHaveDeathCoins = null;
    private static String PlayerDontOnline = null;
    private static String CantSetThat = null;
    private static String SetComplete = null;
    private static String WrongCommands = null;
    private static List<String> HelpCommands = null;
    //Gui Value
    private static ItemStack coinviewer = null;
    private static List<String> Itemlores = null;


    //Get Config Value
    public Integer getStarterpoints(){
        return staterdeathpoints;
    }

    public Integer getFirsttradeprice(){
        return firsttradeprice;
    }

    public Boolean getuptradeprice(){
        return uptradeprice;
    }

    public Boolean getDisableblackglass(){
        return disableblackglass;
    }

    public ItemStack getCoinviewerItem(){
        return coinviewer.clone();
    }

    public List<String> getItemLores(){
        return Itemlores;
    }

    public Expression getTradepriceex(){
        return tradepriceex;
    }

    //Get Message Value
    public String getPluginPrefix() {
        return PluginPrefix;
    }

    public String getNoPermsMsg() {
        return NoPermsMsg;
    }

    public String getChestTimeOut() {
        return ChestTimeOut;
    }

    public String getReloadMsg() {
        return ReloadMsg;
    }

    public String getTradeComplete() {
        return TradeComplete;
    }

    public String getDontHaveDeathCoins() {
        return DontHaveDeathCoins;
    }

    public String getPlayerDontOnline() {
        return PlayerDontOnline;
    }

    public String getCantSetThat() {
        return CantSetThat;
    }

    public String getSetComplete() {
        return SetComplete;
    }

    public String getWrongCommands() {
        return WrongCommands;
    }

    public List<String> getHelpCommands() { return HelpCommands; }

    public void onReload(){
        //=============== Get Settings ================
        blacklistworld = (List<String>) pl.getConfig().getList("blacklist-world");
        allowopendc = pl.getConfig().getBoolean("allow-open-deathchest");
        sentwhendestory = pl.getConfig().getBoolean("sent-when-destroy");
        removeitemwhendeathagain = pl.getConfig().getBoolean("removeitem-when-death-again");
        uptradeprice = pl.getConfig().getBoolean("up-trade-price");
        disableblackglass = pl.getConfig().getBoolean("disable-black-glass");
        expdropwhendeath = pl.getConfig().getBoolean("exp-not-drop-when-death");
        dctimeout = pl.getConfig().getInt("deathchest-timeout");
        staterdeathpoints = pl.getConfig().getInt("stater-deathpoints");
        firsttradeprice = pl.getConfig().getInt("first-trade-price");
        tradepriceex = new ExpressionBuilder(pl.getConfig().getString("equation-price")).variables("x").build();
        //=============== Get Message ===================
        PluginPrefix = ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Prefix"));
        ReloadMsg = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix + pl.getConfig().getString("Reload"));
        NoPermsMsg = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix + pl.getConfig().getString("NoPerm"));
        RemoveOldChest1 = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix + pl.getConfig().getString("RemoveOldChest1"));
        RemoveOldChest2 = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix + pl.getConfig().getString("RemoveOldChest2"));
        ChestTimeOut = ChatColor.translateAlternateColorCodes('&' , PluginPrefix + pl.getConfig().getString("ChestTimeOut"));
        WhenBreakChest = ChatColor.translateAlternateColorCodes('&', PluginPrefix + pl.getConfig().getString("WhenBreakChest"));
        CancelInteract = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("CancelInteract"));
        TradeComplete = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("TradeComplete"));
        DontHaveDeathCoins = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("DontHaveDeathCoins"));
        PlayerDontOnline = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("PlayerDontOnline"));
        CantSetThat = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("CantSetThat"));
        SetComplete = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("SetComplete"));
        WrongCommands = ChatColor.translateAlternateColorCodes('&' ,PluginPrefix +pl.getConfig().getString("WrongCommands"));
        //Help Commands
        List<String> helpcommands = new ArrayList<>();
        for(String s : pl.getConfig().getStringList("HelpCommands")) {
            helpcommands.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        HelpCommands = helpcommands;
        //=========== Get Gui ================
        //> Coinviewers
        //Set Skull
        coinviewer = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta coinviewermeta = (SkullMeta) coinviewer.getItemMeta();
        //SetSkin
        coinviewermeta.setPlayerProfile(Bukkit.createProfile(UUID.randomUUID(), null));
        PlayerProfile plprofile = coinviewermeta.getPlayerProfile();
        plprofile.getProperties().add(new ProfileProperty("textures", pl.getConfig().getString("coinviewers.texturevalue")));
        coinviewermeta.setPlayerProfile(plprofile);
        //SetName And Lores
        coinviewermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("coinviewers.name")));
        List<String> lore = new ArrayList<>();
        for(String s : pl.getConfig().getStringList("coinviewers.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        coinviewermeta.setLore(lore);
        coinviewer.setItemMeta(coinviewermeta);
        //> ItemLores
        Itemlores = new ArrayList<>();
        for(String s : pl.getConfig().getStringList("itemlores.lore")) {
            Itemlores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
    }

    //Death Event
    @EventHandler(priority = EventPriority.HIGH)
    public static void onDeath(PlayerDeathEvent e){
        if (e.getEntity() instanceof Player){
            Player p = e.getEntity();
            if (p.isOnline()){
                if (!(blacklistworld.isEmpty())){
                    for (int i = 0; i < blacklistworld.size(); i++) {
                        if (p.getWorld().getName().equalsIgnoreCase(blacklistworld.get(i))){
                            return;
                        }
                    }
                }
                //Check player's inventory is empty or not?
                if (p.getInventory().isEmpty()){
                    return;
                }
                //
                List<ItemStack> drops = new ArrayList<>(e.getDrops());
                //Save Data to Yml
                if (!playerdropsdata.Check(p)) {
                    playerdropsdata.create(p);
                }else{
                    playerdropsdata.load(p);
                }
                //Check player's have old chest or not?
                if (playerdropsdata.get().getLocation("ChestBlock") != null) {
                    if (playerdropsdata.get().getLocation("ChestBlock").getBlock().getType().equals(Material.CHEST)) {
                        if (removeitemwhendeathagain == false) {
                            Chest chest = (Chest) playerdropsdata.get().getLocation("ChestBlock").getBlock().getState();
                            List<ItemStack> chestdrops = Arrays.asList(chest.getInventory().getContents());
                            playerdropsdata.get().set("DeathLoot", chestdrops);
                            playerdropsdata.get().getLocation("ChestBlock").getBlock().setType(Material.AIR);
                            playerdropsdata.save();
                            p.sendMessage(RemoveOldChest1);
                        } else {
                            playerdropsdata.get().getLocation("ChestBlock").getBlock().setType(Material.AIR);
                            p.sendMessage(RemoveOldChest2);
                        }
                    }
                }
                playerdropsdata.get().set("DeathLootData", drops);
                playerdropsdata.save();
                //
                e.getDrops().clear();
                Block chestblock = p.getLocation().getBlock();
                chestblock.setType(Material.CHEST);
                if (chestblock.getType().equals(Material.CHEST)) {
                    Chest chest = (Chest) chestblock.getState();
                    for (int i = 0; i < drops.size(); i++) {
                        chest.getBlockInventory().addItem(drops.get(i));
                    }
                }
                //Save Chest Location
                playerdropsdata.get().set("ChestBlock", chestblock.getLocation());
                if (expdropwhendeath){
                    e.setKeepLevel(true);
                    e.setShouldDropExperience(false);
                }
                playerdropsdata.save();
                //
                LnwzaDeath.getPlugin(LnwzaDeath.class).getTimer().addPlayerToCD(p, dctimeout);
                //Save Data To Chest
                PersistentDataContainer data = chestblock.getChunk().getPersistentDataContainer();
                data.set(new NamespacedKey(LnwzaDeath.getPlugin(LnwzaDeath.class), "deathchest"), PersistentDataType.INTEGER, 1);
                data.set(new NamespacedKey(LnwzaDeath.getPlugin(LnwzaDeath.class), "deathchestowner"), PersistentDataType.STRING, p.getUniqueId().toString());
            }
        }
    }

    //Open Chest Event and Break
    @EventHandler(priority = EventPriority.HIGH)
    public static void onOpenDC(PlayerInteractEvent e){
        if (allowopendc == false) {
            if (!(e.getPlayer().isOp())) {
                if (!(e.getClickedBlock() == null)) {
                    if (e.getClickedBlock().getType().equals(Material.CHEST)) {
                        PersistentDataContainer data = e.getClickedBlock().getChunk().getPersistentDataContainer();
                        if ((data.has(new NamespacedKey(LnwzaDeath.getPlugin(LnwzaDeath.class), "deathchest"), PersistentDataType.INTEGER))) {
                            if (playerdropsdata.Check(e.getPlayer())) {
                                playerdropsdata.load(e.getPlayer());
                                if (!(playerdropsdata.get().get("ChestBlock").equals(e.getClickedBlock().getLocation()))) {
                                    e.setCancelled(true);
                                    e.getPlayer().sendMessage(CancelInteract);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //On Break
    @EventHandler(priority = EventPriority.HIGH)
    public static void onDCBreak(BlockBreakEvent e){
        if (sentwhendestory == true) {
            if (e.getPlayer() != null) {
                if (e.getPlayer().isOnline()) {
                    if (e.getBlock().getType().equals(Material.CHEST)) {
                        Chest chest = (Chest) e.getBlock().getState();
                        if (!(chest.getBlockInventory().isEmpty())) {
                            PersistentDataContainer data = e.getBlock().getChunk().getPersistentDataContainer();
                            if ((data.has(new NamespacedKey(LnwzaDeath.getPlugin(LnwzaDeath.class), "deathchest"), PersistentDataType.INTEGER))) {
                                e.setDropItems(false);
                                //e.getPlayer().sendMessage(Bukkit.getPlayer(UUID.fromString(data.get(new NamespacedKey(LnwzaDeath.getPlugin(LnwzaDeath.class), "deathchestowner"), PersistentDataType.STRING))).getName());
                                Player dataplayer = Bukkit.getPlayer(UUID.fromString(data.get(new NamespacedKey(LnwzaDeath.getPlugin(LnwzaDeath.class), "deathchestowner"), PersistentDataType.STRING)));
                                List<ItemStack> drops = Arrays.asList(chest.getInventory().getContents());
                                playerdropsdata.load(dataplayer);
                                playerdropsdata.get().set("TradePrice", firsttradeprice);
                                playerdropsdata.get().set("DeathLoot", drops);
                                playerdropsdata.save();
                                if (dataplayer.isOnline()){
                                    dataplayer.sendMessage(WhenBreakChest);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
