package lach_01298.qmd.fluid;

import com.google.common.collect.Sets;
import net.minecraftforge.fluids.*;

import java.util.*;

public class CellFluids
{

	static Set<String> cellFluids = Sets.newHashSet();
	static Set<Fluid> currentCellFluids;

	public static boolean addFluid(Fluid fluid)
    {
        if(fluid == null)
        {
            return false;
        }
        if (!FluidRegistry.isFluidRegistered(fluid))
        {
        	 return false;
        }
        return cellFluids.add(fluid.getName());
    }
	
	public static Set<Fluid> getFluids()
	{
		if (currentCellFluids == null)
		{
			Set<Fluid> tmp = Sets.newHashSet();
			for (String fluidName : cellFluids)
			{
				tmp.add(FluidRegistry.getFluid(fluidName));
			}
			currentCellFluids = Collections.unmodifiableSet(tmp);
		}
		return currentCellFluids;
	}

	public static boolean hasFluid(Fluid fluid)
	{
		return cellFluids.contains(fluid.getName());
	}
}
