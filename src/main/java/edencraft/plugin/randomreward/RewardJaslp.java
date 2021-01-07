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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class RewardJaslp implements CommandExecutor {
    private final RandomReward randomReward;

    public RewardJaslp(RandomReward randomReward) {
        this.randomReward = randomReward;
    }

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

                        ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD);
                        item1.addEnchantment(Enchantment.FIRE_ASPECT, 1);
                        ItemStack item2 = new ItemStack(Material.DIAMOND_SWORD);
                        item2.addEnchantment(Enchantment.DAMAGE_ALL, 2);

                        String nomNPC = ChatColor.BOLD + "Jaspl ";
                        String fire = ChatColor.BOLD + "Aura de Feu ";
                        String tranchant = ChatColor.BOLD + "Tranchant ";

                        if (getRandomNumber == 1){
                            player.getInventory().addItem(item1);
                            player.sendMessage(ChatColor.YELLOW + nomNPC + ChatColor.YELLOW + ": Tu as eu un épée avec l'enchantement " + ChatColor.RED + fire + ChatColor.BOLD + getRandomNumber + ChatColor.YELLOW + ", tu en as de la chance !");
                        }else {
                            player.getInventory().addItem(item2);
                            player.sendMessage(ChatColor.YELLOW + nomNPC + ChatColor.YELLOW + ": Tu as eu un pioche avec l'enchantement " + ChatColor.RED + tranchant + ChatColor.BOLD + getRandomNumber + ChatColor.YELLOW + ", tu en as de la chance !");
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
