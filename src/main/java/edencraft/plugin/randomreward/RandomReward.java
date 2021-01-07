package edencraft.plugin.randomreward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class RandomReward extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("rewardviktor").setExecutor(new RewardViktor(this));
        getCommand("rewardjaspl").setExecutor(new RewardJaslp(this));
    }

    @Override
    public void onDisable() {
    }
}
