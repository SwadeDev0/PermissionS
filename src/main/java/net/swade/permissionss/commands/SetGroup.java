package net.swade.permissionss.commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.swade.permissionss.Utils;
import net.swade.permissionss.event.PlayerGroupChangeEvent;
import net.swade.permissionss.group.Group;
import net.swade.permissionss.group.GroupManager;

public class SetGroup extends Command {
    public SetGroup() {
        super("setgroup", Utils.translate("commands.set_group.description"));
        setUsage(Utils.translate("commands.set_group.usage"));
        setPermission("permissionss.setgroup");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)){
            return false;
        }

        if (strings.length != 2){
            commandSender.sendMessage(getUsage());
            return false;
        }

        Group group = GroupManager.getGroup(strings[1]);
        if (group == null){
            commandSender.sendMessage(Utils.translate("commands.set_group.not_found", strings[1]));;
            return false;
        }

        String name = strings[0];
        Player player = Server.getInstance().getPlayer(strings[0]);
        if (player != null){
            Group oldGroup = GroupManager.getPlayerGroup(player);
            PlayerGroupChangeEvent event = new PlayerGroupChangeEvent(player, group, oldGroup, commandSender);
            Server.getInstance().getPluginManager().callEvent(event);
            name = player.getName();
        }
        GroupManager.setPlayerGroup(name, group);
        commandSender.sendMessage(Utils.translate("commands.set_group.success", name, group.getName()));
        return true;
    }
}
