package lach_01298.qmd.proxy;

import lach_01298.qmd.*;
import lach_01298.qmd.item.QMDArmour;
import lach_01298.qmd.render.*;
import lach_01298.qmd.render.entity.BeamRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.*;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

import static lach_01298.qmd.config.QMDConfig.clientPreInit;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		super.preInit(preEvent);
		clientPreInit();
		QMDRenderHandler.init();
		MinecraftForge.EVENT_BUS.register(DrillBlockRenderHandler.INSTANCE);
		
		
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		ItemColors itemcolors = Minecraft.getMinecraft().getItemColors();
		itemcolors.registerItemColorHandler(new IItemColor()
		{
			public int colorMultiplier(ItemStack stack, int tintIndex)
			{
				return tintIndex > 0 ? -1 : ((ItemArmor) stack.getItem()).getColor(stack);
			}
		}, QMDArmour.helm_hev, QMDArmour.chest_hev, QMDArmour.legs_hev, QMDArmour.boots_hev);

	}

	@Override
	public void postInit(FMLPostInitializationEvent postEvent)
	{
		super.postInit(postEvent);
		MinecraftForge.EVENT_BUS.register(new QMDTooltipHandler());
		MinecraftForge.EVENT_BUS.register(new ArmPositionHandler());
		MinecraftForge.EVENT_BUS.register(new BeamRenderer());
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(DrillBlockRenderHandler.INSTANCE);
	}


	@Override
	public EntityPlayer getPlayerClient()
	{
		return Minecraft.getMinecraft().player;
	}


	@Override
	public void registerFluidBlockRendering(Block block, String name)
	{
		super.registerFluidBlockRendering(block, name);
		FluidStateMapper mapper = new FluidStateMapper(name);

		Item item = Item.getItemFromBlock(block);
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, mapper);

		// ModelLoader.setCustomStateMapper(block, new
		// StateMap.Builder().ignore(block.LEVEL).build());
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
	{
		public final ModelResourceLocation location;

		public FluidStateMapper(String name)
		{
			location = new ModelResourceLocation(QMD.MOD_ID + ":fluids", name);
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state)
		{
			return location;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return location;
		}
	}
}
