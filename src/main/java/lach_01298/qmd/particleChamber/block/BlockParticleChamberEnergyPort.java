package lach_01298.qmd.particleChamber.block;

import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockParticleChamberEnergyPort extends BlockParticleChamberPart
{

	public BlockParticleChamberEnergyPort()
	{
		super();
		
	}







	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileParticleChamberEnergyPort();
	}



	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;
		return rightClickOnPart(world, pos, player, hand, facing);
	}
	
}
