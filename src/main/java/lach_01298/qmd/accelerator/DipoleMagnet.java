package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.*;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.util.Util;
import net.minecraft.util.math.BlockPos;

public class DipoleMagnet
{
	private final Accelerator accelerator;
	private BlockPos pos;
	
	private final Long2ObjectMap<IAcceleratorComponent> components = new Long2ObjectOpenHashMap<>();
	private  String type;
	
	
	public DipoleMagnet(Accelerator accelerator, BlockPos pos)
	{
		this.accelerator = accelerator;
		this.pos = pos;
		
		setType();
		addComponents();
	}
	
	private void setType()
	{
		if( accelerator.WORLD.getTileEntity(pos.up()) instanceof TileAcceleratorMagnet)
		{
			TileAcceleratorMagnet magnet =(TileAcceleratorMagnet) accelerator.WORLD.getTileEntity(pos.up());
			type = magnet.name.toUpperCase();
		}
		else if( accelerator.WORLD.getTileEntity(pos.north()) instanceof TileAcceleratorMagnet)
		{
			TileAcceleratorMagnet magnet =(TileAcceleratorMagnet) accelerator.WORLD.getTileEntity(pos.north());
			type = magnet.name.toUpperCase();
		}
		else if( accelerator.WORLD.getTileEntity(pos.east()) instanceof TileAcceleratorMagnet)
		{
			TileAcceleratorMagnet magnet =(TileAcceleratorMagnet) accelerator.WORLD.getTileEntity(pos.east());
			type =magnet.name.toUpperCase();
		}
		else
		{
			Util.getLogger().error("could not set dipole type becuase" + accelerator.WORLD.getTileEntity(pos.up()) +" at "+ pos.up() + " is not a instance of TileAcceleratorMagnet");
		}
		
		
	}
	
	private void addComponents()
	{
		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				for(int z = -1; z <= 1; z++)
				{
					addComponent(pos.add(x, y, z));
				}
			}
		}
		
		
	}
	
	private void addComponent(BlockPos postion)
	{
		if (accelerator.WORLD.getTileEntity(postion) instanceof IAcceleratorComponent)
		{
			components.put(postion.toLong(),(IAcceleratorComponent) accelerator.WORLD.getTileEntity(postion));
		}
		else
		{
			Util.getLogger().error("could not add tile to dipole becuase" + accelerator.WORLD.getTileEntity(postion) +" at "+ postion + " is not a instance of IAcceleratorComponent");
		}
	}

	public BlockPos getPos()
	{
		return pos;
	}
	

	
	public Long2ObjectMap<IAcceleratorComponent> getComponents()
	{
		return components;
	}
	
	public String getType()
	{
		return type;
	}

}
