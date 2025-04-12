package top.szzz666.FreeSit.config;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static top.szzz666.FreeSit.FreeSitMain.ec;
import static top.szzz666.FreeSit.FreeSitMain.plugin;

public class MyConfig {
    public static void initConfig() {
        ec = new EasyConfig("config.yml", plugin);
        ec.add("command", "sit");
        ec.add("touch-stairs-popup", "双击可坐在台阶上");
        ec.add("command-enabled", "双击坐台阶功能已启用");
        ec.add("command-disabled", "双击坐台阶功能已禁用");
        ec.add("disabled", new ArrayList<String>());
        ec.load();
    }

}
