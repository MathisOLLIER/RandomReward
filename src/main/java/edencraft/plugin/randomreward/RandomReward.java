package edencraft.plugin.randomreward;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTradeKey Reloaded"));
            }
        }
        return true;
    }
}
