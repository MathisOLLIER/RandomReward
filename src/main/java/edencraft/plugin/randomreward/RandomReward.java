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
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import java.util.Random;

public final class RandomReward extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() || sender instanceof ConsoleCommandSender) {
            Random random = new Random();
            int randomInt = 3;
            int getRandomNumber = random.nextInt(randomInt) + 1;

            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, getRandomNumber);

            Player player = Bukkit.getPlayer(args[0]);
            if (command.getName().equals("rewardviktor")) {
                player.getInventory().addItem(item);
                String nomNPC = ChatColor.BOLD + "Viktor ";
                String fortune = ChatColor.BOLD + "Fortune ";
                player.sendMessage(ChatColor.YELLOW + nomNPC + ChatColor.YELLOW + ": Tu as eu un pioche avec l'enchantement " + ChatColor.RED + fortune + ChatColor.BOLD + getRandomNumber + ChatColor.YELLOW + ", p'tit veinard...");
            }
            return true;
        }
        return true;
    }
}
