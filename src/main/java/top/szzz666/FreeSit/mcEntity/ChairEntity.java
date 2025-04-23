package top.szzz666.FreeSit.mcEntity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityRideable;
import cn.nukkit.entity.item.EntityMinecartEmpty;
import cn.nukkit.entity.item.EntityVehicle;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3f;
import cn.nukkit.nbt.tag.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class ChairEntity extends EntityVehicle implements EntityRideable {
    public ChairEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_INVISIBLE, true, false);
    }

    @Override
    public int getNetworkId() {
        return EntityMinecartEmpty.NETWORK_ID;
    }

    @Override
    public boolean onUpdate(int tick) {
        return false;
    }

    @Override
    public boolean dismountEntity(Entity entity, boolean sendLinks) {
        if (super.dismountEntity(entity, sendLinks)) {
            this.close();
            return true;
        }
        return false;
    }

    @Override
    public Vector3f getMountedOffset(Entity entity) {
        return new Vector3f(0.0f, 1.5f, 0.0f);
    }

    @Override
    public void close() {
        super.close();
        if (this.passengers != null && !this.passengers.isEmpty()) {
            List<Entity> copy = new ArrayList<>(this.passengers.size());
            copy.addAll(this.passengers);
            for (Entity passenger : copy) {
                this.dismountEntity(passenger);
            }
        }
    }
}
