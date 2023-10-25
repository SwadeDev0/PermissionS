package net.swade.permissionss;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;
import net.swade.permissionss.commands.PermissionSCommands;
import net.swade.permissionss.group.GroupManager;
import net.swade.permissionss.listener.PlayerListener;
import net.swade.permissionss.permission.PermissionManager;
import net.swade.permissionss.task.ExpireDateCheckTask;

import java.util.ArrayList;

public class Main extends PluginBase {

    @Getter private static Main instance;
    @Getter private String playerConfigPath;

    @Override
    public void onLoad() {
        instance = this;
        playerConfigPath  = getDataFolder().getPath() + "/players.json";
        saveDefaultConfig();
        saveResource("groups.yml");
    }

    @Override
    public void onEnable() {
        PermissionSCommands.init();
        GroupManager.load();
        getLogger().info("Groups Loaded");
        getLogger().info("Groups size: " + GroupManager.groups.size());
        PermissionManager.addPermsToOnlinePlayers();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getScheduler().scheduleRepeatingTask(new ExpireDateCheckTask(), 72000);
    }

    @Override
    public void onDisable() {
        PermissionManager.removePermissions();
    }

    public void registerPlayer(String name){
        Config config = new Config(playerConfigPath, 1);
        Profile profile = new Profile(name, GroupManager.getDefaultGroup().getId(), new ArrayList<>(), -1);
        config.set(name.toLowerCase(), profile.toString());
        config.save();
    }
}