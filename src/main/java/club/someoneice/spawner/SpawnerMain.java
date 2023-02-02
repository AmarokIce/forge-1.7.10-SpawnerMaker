package club.someoneice.spawner;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;


@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION)
public class SpawnerMain {
    static Block spawner = new Spawner();
    @Mod.EventHandler
    public void Initialization(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(SpawnerTileEntity.class, "better_mob_spawner_tile");

        MinecraftForge.EVENT_BUS.register(new SpawnerMain());
        FMLCommonHandler.instance().bus().register(new SpawnerMain());
    }

    @SubscribeEvent
    public void onPlayerUse(PlayerInteractEvent event) {
        if (!event.world.isRemote) {
            if (!event.entityPlayer.capabilities.isCreativeMode && event.entityPlayer.getHeldItem().getItem() != Items.stick ) return;
            if (getBlockIsMobSpawner(event.world.getBlock(event.x, event.y, event.z))) {
                event.world.setBlock(event.x, event.y, event.z, spawner);
                event.world.setTileEntity(event.x, event.y, event.z, new SpawnerTileEntity());
            }
        }
    }

    private boolean getBlockIsMobSpawner(Block block) {
        return block instanceof BlockMobSpawner && !(block instanceof Spawner);
    }
}
