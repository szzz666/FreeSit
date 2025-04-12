package top.szzz666.FreeSit.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import top.szzz666.FreeSit.form.easy_form.Custom;
import top.szzz666.FreeSit.form.easy_form.Simple;

import java.util.Arrays;

import static top.szzz666.FreeSit.FreeSitMain.ec;
import static top.szzz666.FreeSit.event.Listeners.disabled;
import static top.szzz666.FreeSit.form.easy_form.Modal.tipsModal;


public class MyForm {
    public static void Form(Player player) {
        Custom form = new Custom("FreeSit设置", true);
        form.add("双击坐台阶功能", new ElementToggle("双击坐台阶功能", !disabled.contains(player.getName())));
        form.setSubmit(() -> {
            boolean doubleClick = form.getToggleRes("双击坐台阶功能");
            if (doubleClick) {
                disabled.remove(player.getName());
                ec.save();
                player.sendMessage(ec.getString("command-enabled"));
            } else {
                disabled.add(player.getName());
                ec.save();
                player.sendMessage(ec.getString("command-disabled"));
            }
        });
        form.show(player);
    }
}
