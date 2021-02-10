package edencraft.plugin.randomreward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

public final class RandomReward extends JavaPlugin {
    public static String edenCraftPrefix = ChatColor.LIGHT_PURPLE + "Eden" + ChatColor.YELLOW + "Craft" + ChatColor.WHITE + ChatColor.BOLD + " » " + ChatColor.RESET;
    public static RandomReward randomRewardInstance;

    @Override
    public void onEnable() {
        randomRewardInstance = this;
        this.saveDefaultConfig();
        getCommand("rewardviktor").setExecutor(new RewardViktor());
        getCommand("rewardjaspl").setExecutor(new RewardJaslp());
        getCommand("tradekey").setExecutor(new TradeKey());
        getCommand("randomreward").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                this.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRandomReward Reloaded"));
            }
        }
        return true;
    }
}
