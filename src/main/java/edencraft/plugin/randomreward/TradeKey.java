package edencraft.plugin.randomreward;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.enchantments.Enchantment.ARROW_INFINITE;


public class TradeKey implements CommandExecutor {
    private RandomReward randomReward = RandomReward.randomRewardInstance;
    private final String defaultKey = ChatColor.WHITE + "Clé" + " " + ChatColor.GREEN + "Quête" + " ";
    private final String keyT1 = randomReward.getConfig().getString("keyNamesOf.1");
    private final String keyT2 = randomReward.getConfig().getString("keyNamesOf.2");
    private final String noKeyFound = randomReward.getConfig().getString("noKeyFoundInInventory");
    private final String keyTraded = randomReward.getConfig().getString("keyTraded");
    private final String edenCraftPrefix = RandomReward.edenCraftPrefix;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (isOnline(args[1])) {

                Player player = Bukkit.getPlayer(args[1]);
                assert player != null;

                if (sender.isOp() || sender instanceof ConsoleCommandSender) {

                    if (args[0].equalsIgnoreCase("T1")) {
                        startTrade(player, "T1", "T2", defaultKey + keyT1, 15);
                    } else if(args[0].equalsIgnoreCase("T2")) {
                        startTrade(player, "T2", "T3", defaultKey + keyT2, 5);
                    }

                    return true;
                }
                return true;
            }
        }
        return true;
    }

    private void startTrade(Player player, String keySold, String keyBought, String keyToTrade, int requiredKeyForTrade) {
        PlayerInventory inventory = player.getInventory();

        if (!inventory.isEmpty()){

            boolean hasQuestKey = false;
            int keyCounter = 0;
            List<ItemStack> itemsToBeRemove = new ArrayList<>();

            for(ItemStack item : inventory) {
                if(item != null && item.getItemMeta() != null) {
                    if(item.getItemMeta().getDisplayName().equals(keyToTrade)
                            && item.getType().equals(Material.TRIPWIRE_HOOK)
                            && item.containsEnchantment(ARROW_INFINITE)
                            && item.getEnchantmentLevel(ARROW_INFINITE) == 10){
                        keyCounter = keyCounter + item.getAmount();
                        hasQuestKey = true;
                        itemsToBeRemove.add(item);
                    }
                }
            }
            if(hasQuestKey) {
                if(keyCounter >= requiredKeyForTrade) {
                    RemoveKey(player, itemsToBeRemove, requiredKeyForTrade);
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "cr give to " + player.getName() + " Quête" + keyBought);
                    assert keyTraded != null;
                    player.sendMessage(edenCraftPrefix + keyTraded
                            .replace("{keyBought}", keyBought)
                            .replace("{requiredKeyForTrade}", String.valueOf(requiredKeyForTrade))
                            .replace("{keySold}", keySold));
                } else {
                    player.sendMessage(String.format("&4%s Il te manques %s %s", edenCraftPrefix, requiredKeyForTrade - keyCounter, keyToTrade));
                }
            } else {
                player.sendMessage(edenCraftPrefix + noKeyFound + " " + keyToTrade);
            }
        } else {
            player.sendMessage(edenCraftPrefix + noKeyFound + " " + keyToTrade);
        }
    }

    private void RemoveKey(Player player, List<ItemStack> itemsToBeRemove, int requiredItemToRemove) {
        for (ItemStack item : itemsToBeRemove) {
            if (requiredItemToRemove < 0) {
                break;
            }
            if (item.getAmount() <= requiredItemToRemove) {
                requiredItemToRemove -= item.getAmount();
                player.getInventory().removeItem(item);
            } else {
                item.setAmount(item.getAmount() - requiredItemToRemove);
                requiredItemToRemove -= item.getAmount() - requiredItemToRemove;
            }
        }
    }

    private boolean isOnline(String player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
