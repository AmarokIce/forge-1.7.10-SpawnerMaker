package club.someoneice.spawner;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class SpawnerTileEntity extends TileEntityMobSpawner {
    public final MobSpawnerBaseLogic spawnerBase = new MobSpawnerBaseLogic() {
        public void func_98267_a(int p_98267_1_) {
            SpawnerTileEntity.this.worldObj.addBlockEvent(SpawnerTileEntity.this.xCoord, SpawnerTileEntity.this.yCoord, SpawnerTileEntity.this.zCoord, Blocks.mob_spawner, p_98267_1_, 0);
        }

        public World getSpawnerWorld() {
            return SpawnerTileEntity.this.worldObj;
        }

        public int getSpawnerX() {
            return SpawnerTileEntity.this.xCoord;
        }

        public int getSpawnerY() {
            return SpawnerTileEntity.this.yCoord;
        }

        public int getSpawnerZ() {
            return SpawnerTileEntity.this.zCoord;
        }
        public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart p_98277_1_) {
            super.setRandomEntity(p_98277_1_);

            if (this.getSpawnerWorld() != null) {
                this.getSpawnerWorld().markBlockForUpdate(SpawnerTileEntity.this.xCoord, SpawnerTileEntity.this.yCoord, SpawnerTileEntity.this.zCoord);
            }
        }
    };

    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.spawnerBase.readFromNBT(p_145839_1_);
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        this.spawnerBase.writeToNBT(p_145841_1_);
    }

    public void updateEntity() {
        this.spawnerBase.updateSpawner();
        super.updateEntity();
    }

    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        nbttagcompound.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }

    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
    {
        return this.spawnerBase.setDelayToMin(p_145842_1_) || super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    public MobSpawnerBaseLogic func_145881_a() {
        return this.spawnerBase;
    }

    public void setSpawnerEntity(String EntityName) {
        this.spawnerBase.setEntityName(EntityName);
    }
}
