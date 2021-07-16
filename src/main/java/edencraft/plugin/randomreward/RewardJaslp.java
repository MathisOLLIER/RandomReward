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

import java.util.Random;

public class RewardJaslp implements CommandExecutor {
    private final RandomReward randomReward = RandomReward.randomRewardInstance;
    private final String npcName = randomReward.getConfig().getString("Jaspl.npcName");
    private final String item1Type = randomReward.getConfig().getString("Jaspl.item1.Material");
    private final String item2Type = randomReward.getConfig().getString("Jaspl.item2.Material");
    private final String itemName1 = randomReward.getConfig().getString("Jaspl.item1.itemDisplay");
    private final String itemName2 = randomReward.getConfig().getString("Jaspl.item2.itemDisplay");
    private final String enchant1 = randomReward.getConfig().getString("Jaspl.item1.enchant");
    private final String enchant2 = randomReward.getConfig().getString("Jaspl.item2.enchant");
    private final String level1 = randomReward.getConfig().getString("Jaspl.item1.level");
    private final String level2 = randomReward.getConfig().getString("Jaspl.item2.level");
    private final String enchant1Display = randomReward.getConfig().getString("Jaspl.item1.enchantDisplay");
    private final String enchant2Display = randomReward.getConfig().getString("Jaspl.item2.enchantDisplay");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (isOnline(args[0])) {
                Player player = Bukkit.getPlayer(args[0]);
                assert player != null;
                if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                    if (command.getName().equals("rewardjaspl")) {
                        Random random = new Random();
                        int getRandomNumber = random.nextInt(2) + 1;

                        ItemStack item1 = new ItemStack(Material.getMaterial(item1Type));
                        item1.addEnchantment(Enchantment.getByName(enchant1), Integer.valueOf(level1));
                        ItemStack item2 = new ItemStack(Material.getMaterial(item2Type));
                        item2.addEnchantment(Enchantment.getByName(enchant2), Integer.valueOf(level2));

                        String nomNPC = ChatColor.BOLD + npcName + " ";
                        String fire = ChatColor.BOLD + enchant1Display + " ";
                        String tranchant = ChatColor.BOLD + enchant2Display + " ";

                        if (getRandomNumber == 1){
                            player.getInventory().addItem(item1);
                            player.sendMessage(ChatColor.YELLOW + nomNPC + ChatColor.YELLOW + ": Tu as eu " + itemName1 + " avec l'enchantement " + ChatColor.RED + fire + ChatColor.BOLD + getRandomNumber + ChatColor.YELLOW + ", tu en as de la chance !");
                        }else {
                            player.getInventory().addItem(item2);
                            player.sendMessage(ChatColor.YELLOW + nomNPC + ChatColor.YELLOW + ": Tu as eu " + itemName2 + " avec l'enchantement " + ChatColor.RED + tranchant + ChatColor.BOLD + getRandomNumber + ChatColor.YELLOW + ", tu en as de la chance !");
                        }
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
