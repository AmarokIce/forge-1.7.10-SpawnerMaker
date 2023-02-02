package club.someoneice.spawner;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class Spawner extends BlockMobSpawner {
    private final Random random = new Random();

    public Spawner() {
        this.setBlockTextureName("minecraft:mob_spawner");
        GameRegistry.registerBlock(this, "better_mob_spawner");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new SpawnerTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int o, float px, float py, float pz) {
        if (!player.capabilities.isCreativeMode || player.getHeldItem() == null) {
            return false;
        }

        if (world.getTileEntity(x, y, z) instanceof SpawnerTileEntity) {
            SpawnerTileEntity spawner = (SpawnerTileEntity) world.getTileEntity(x, y, z);
            EntityList.EntityEggInfo eggInfo = getSpawnedEgg(player.getHeldItem());
            if (eggInfo != null) {
                spawner.setSpawnerEntity(EntityList.getStringFromID(eggInfo.spawnedID));
                return true;
            } else return false;
        }
        return false;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public Item getItemDropped(int count, Random render, int mt) {
        return null;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
        return 15 + random.nextInt(15) + random.nextInt(15);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    protected EntityList.EntityEggInfo getSpawnedEgg(ItemStack itemStack) {
        int damage = itemStack.getItemDamage();
        if (EntityList.entityEggs.containsKey(damage))
            return (EntityList.EntityEggInfo) EntityList.entityEggs.get(damage);
        else return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemById(0);
    }
}
