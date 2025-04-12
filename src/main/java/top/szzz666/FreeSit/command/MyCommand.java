package top.szzz666.FreeSit.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Position;

import static top.szzz666.FreeSit.FreeSitMain.ec;
import static top.szzz666.FreeSit.FreeSitMain.plugin;
import static top.szzz666.FreeSit.config.MyConfig.initConfig;
import static top.szzz666.FreeSit.form.MyForm.Form;
import static top.szzz666.FreeSit.tools.taskUtil.Async;


public class MyCommand extends Command {
    public MyCommand() {
        super(ec.getString("command"), plugin.getName() + "插件");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Async(() -> {
            if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                if (args.length == 1 && args[0].equals("reload")) {
                    initConfig();
                    sender.sendMessage(plugin.getName() + "插件配置已重新加载");
                }
            }
            if (sender instanceof Player player) {
                if (args.length == 0) {
                    Position position = player.getPosition();
                    position.setY(position.getY() - 0.5);
                    Entity minecart = Entity.createEntity("Chair", position);
                    player.setPosition(minecart);
                    minecart.mountEntity(player);
                    minecart.spawnToAll();
                }
                if (args.length == 1 && args[0].equals("set")) {
                    Form(player);
                }
            }
        });
        return false;
    }

}
