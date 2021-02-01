package edencraft.plugin.randomreward;

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
    private final RandomReward randomReward;
    private String key = ChatColor.WHITE + "Clé" + " " + ChatColor.GREEN + "Quête" + " " + ChatColor.GREEN + "T1";
    private String edenCraftPrefix = RandomReward.edenCraftPrefix;

    public TradeKey (RandomReward randomReward) {
        this.randomReward = randomReward;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            if (isOnline(args[0])) {

                Player player = Bukkit.getPlayer(args[0]);
                assert player != null;

                if (sender.isOp() || sender instanceof ConsoleCommandSender) {

                    if (args[1].equalsIgnoreCase("T1")) {
                        startTrade(player, "T1", "T2", 15);
                    } else if(args[1].equalsIgnoreCase("T2")) {
                        startTrade(player, "T2", "T3", 5);
                    }

                    return true;
                }
                return true;
            }
        }
        return true;
    }

    private void startTrade(Player player, String keySold, String keyBought, int requiredKeyForTrade) {
        PlayerInventory inventory = player.getInventory();

        if (!inventory.isEmpty()){

            boolean hasQuestKey = false;
            int keyCounter = 0;
            List<ItemStack> itemsToBeRemove = new ArrayList<>();

            for(ItemStack item : inventory) {
                if(item != null && item.getItemMeta() != null) {
                    if(item.getItemMeta().getDisplayName().equals(key)
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
                    player.sendMessage(edenCraftPrefix + "Tu as reçu 1 clé " + keyBought + " contre " + requiredKeyForTrade + " clé(s) " + keySold);
                } else {
                    player.sendMessage(edenCraftPrefix + "Tu n'as pas assez de clé quête" + keySold);
                }
            } else {
                player.sendMessage(edenCraftPrefix + "Tu n'as pas de clé quête");
            }
        } else {
            player.sendMessage(edenCraftPrefix + "Ton inventaire est vide, tu n'as pas de clé quête");
        }
    }

    private void RemoveKey(Player player, List<ItemStack> itemsToBeRemove, int requiredItemToRemove) {
        for (ItemStack item : itemsToBeRemove) {
            if (requiredItemToRemove > 0) {
                if (item.getAmount() <= requiredItemToRemove) {
                    requiredItemToRemove -= item.getAmount();
                    player.getInventory().removeItem(item);
                } else {
                    item.setAmount(item.getAmount() - requiredItemToRemove);
                    requiredItemToRemove -= item.getAmount() - requiredItemToRemove;
                }
            } else {
                break;
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
