package edencraft.plugin.randomreward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

public class RewardViktor implements CommandExecutor {
    private final RandomReward randomReward = RandomReward.randomRewardInstance;
    private final String npcName = randomReward.getConfig().getString("viktor.npcName");
    private final String itemType = randomReward.getConfig().getString("viktor.item.material");
    private final String enchantDisplay = randomReward.getConfig().getString("viktor.item.enchantDisplay");
    private final String itemDisplay = randomReward.getConfig().getString("viktor.item.itemDisplay");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (isOnline(args[0])) {
                Player player = Bukkit.getPlayer(args[0]);
                assert player != null;
                if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                    if (command.getName().equals("rewardviktor")) {
                        Random random = new Random();
                        int getRandomNumber = random.nextInt(3) + 1;
                        ItemStack item = new ItemStack(Material.getMaterial(itemType));
                        item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, getRandomNumber);
                        player.getInventory().addItem(item);
                        String nomNPC = ChatColor.BOLD + npcName + " ";
                        String fortune = ChatColor.BOLD + enchantDisplay + " ";
                        player.sendMessage(ChatColor.YELLOW + nomNPC + ChatColor.YELLOW + ": Tu as eu " + itemDisplay + " avec l'enchantement " + ChatColor.RED + fortune + ChatColor.BOLD + getRandomNumber + ChatColor.YELLOW + ", p'tit veinard...");
                    }
                    return true;
                }
                return true;
            }
        }
        return true;
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
