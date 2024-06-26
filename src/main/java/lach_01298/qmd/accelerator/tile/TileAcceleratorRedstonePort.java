package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.block.BlockAcceleratorRedstonePort;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

import static nc.block.property.BlockProperties.ACTIVE;

public class TileAcceleratorRedstonePort extends TileAcceleratorPart
{

	private int redstoneLevel =0;

	public TileAcceleratorRedstonePort()
	{
		super(CuboidalPartPositionType.WALL);


	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
	

	}



	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockAcceleratorRedstonePort)
		{
			((BlockAcceleratorRedstonePort) getBlockType()).setState(isActive, this);
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	


	// IMultitoolLogic

	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing,
	                              float hitX, float hitY, float hitZ)
	{
		
		
		if (player.isSneaking())
		{

		}
		else
		{
			if (getMultiblock() != null && world.getBlockState(pos).getValue(ACTIVE) != null)
			{
				if (world.getBlockState(pos).getValue(ACTIVE).booleanValue())
				{
					setRedstoneLevel(0);
					updateBlockState(false);
					getMultiblock().checkIfMachineIsWhole();
					player.sendMessage(new TextComponentString(Lang.localize("qmd.block.redstone_port_toggle") + " "
							+ TextFormatting.DARK_AQUA + Lang.localize("qmd.block.redstone_port_toggle.input") + " "
							+ TextFormatting.WHITE + Lang.localize("qmd.block.redstone_port_toggle.mode")));
				}
				else
				{
					setRedstoneLevel(0);
					updateBlockState(true);
					getMultiblock().checkIfMachineIsWhole();
					player.sendMessage(new TextComponentString(Lang.localize("qmd.block.redstone_port_toggle") + " "
							+ TextFormatting.RED + Lang.localize("qmd.block.redstone_port_toggle.output") + " "
							+ TextFormatting.WHITE + Lang.localize("qmd.block.redstone_port_toggle.mode")));
				}
				markDirtyAndNotify();
				return true;
			}
		}
		return super.onUseMultitool(multitoolStack, player, world, facing, hitX, hitY, hitZ);
	}

	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		nbt.setInteger("redstoneLevel", redstoneLevel);

		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		redstoneLevel = nbt.getInteger("redstoneLevel");
	}

	
	public int getredstoneLevel()
    {
        return redstoneLevel;
    }

    public void setRedstoneLevel(int redstoneLevel)
    {
        if(this.redstoneLevel !=redstoneLevel)
        {
	    	this.redstoneLevel = MathHelper.clamp(redstoneLevel,0,15);
	        this.world.updateComparatorOutputLevel(pos, this.blockType);
	        this.world.notifyNeighborsOfStateChange(pos, this.blockType, true);
        }
    }
	
	
	// Capability

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		return super.getCapability(capability, side);
	}



}
