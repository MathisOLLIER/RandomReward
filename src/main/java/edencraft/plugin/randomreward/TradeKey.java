package edencraft.plugin.randomreward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.bukkit.enchantments.Enchantment.ARROW_INFINITE;


public class TradeKey implements CommandExecutor {
    private final RandomReward randomReward;
    private String key = ChatColor.WHITE + "Clé" + " " + ChatColor.GREEN + "Quête" + " " + ChatColor.GREEN + "T1";

    public TradeKey (RandomReward randomReward) {
        this.randomReward = randomReward;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (isOnline(args[0])) {
                Player player = Bukkit.getPlayer(args[0]);
                assert player != null;
                if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                    if (command.getName().equals("tradekey")) {
                        PlayerInventory inventory = player.getInventory();
                        if (!inventory.isEmpty()){
                            boolean hasQuestKey = false;
                            int keyCounter = 0;
                            List<ItemStack> itemsToBeRemove = new ArrayList<>();;
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
                                int numberOfT1KeyForT2 = 15;
                                if(keyCounter >= numberOfT1KeyForT2) {
                                    RemoveKey(player, itemsToBeRemove, numberOfT1KeyForT2);
                                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                                    Bukkit.dispatchCommand(console, "cr give to " + player.getName() + " QuêteT2");
                                    player.sendMessage("Tu as recu 1 clé T2 contre " + numberOfT1KeyForT2 + " clés T1");
                                } else {
                                    player.sendMessage("pas assez de clé quete.");
                                }
                            } else {
                                player.sendMessage("pas de clé quete.");
                            }
                        }else {
                            player.sendMessage("Ton inventaire est vide.");
                        }
                    }
                    return true;
                }
                return true;
            }
        }
        return true;
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
