package top.szzz666.FreeSit.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockStairs;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static top.szzz666.FreeSit.FreeSitMain.ec;
import static top.szzz666.FreeSit.tools.taskUtil.Async;


public class Listeners implements Listener {
    public static final List<String> disabled = ec.get("disabled");
    private final Map<Long, Long> doubleTap = new HashMap<>();

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        doubleTap.remove(event.getPlayer().getId());
    }

    @EventHandler
    public void onTouch(PlayerInteractEvent event) {
        Async(() -> {
            Player player = event.getPlayer();

            if (player.riding != null ||
                    player.isSneaking() ||
                    event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK ||
                    disabled.contains(player.getName())) {
                return;
            }

            Block block = event.getBlock();
            if (block instanceof BlockStairs) {
                if (block.y > player.y + 2 || block.y < player.y - 2) return;
                if (block.distanceSquared(player) > 100) return;
                if ((block.getDamage() & 4) != 0 || block.up().isSolid()) return;

                long playerId = player.getId();
                Long last = doubleTap.get(playerId);
                if (last == null) {
                    doubleTap.put(playerId, System.currentTimeMillis());
                    player.sendPopup(ec.getString("touch-stairs-popup"));
                    return;
                }

                if (System.currentTimeMillis() - last < 500) {
                    Entity minecart = Entity.createEntity("Chair", block.add(0.5, 0, 0.5));
                    player.setPosition(minecart);
                    minecart.mountEntity(player);
                    minecart.spawnToAll();
                    doubleTap.remove(playerId);
                } else {
                    doubleTap.put(playerId, System.currentTimeMillis());
                    player.sendPopup(ec.getString("touch-stairs-popup"));
                }
            }
        });
    }
}
