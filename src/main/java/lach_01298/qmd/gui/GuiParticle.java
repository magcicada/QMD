package lach_01298.qmd.gui;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
import nc.util.Lang;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.util.*;

public class GuiParticle
{
	private int width = 16;
	private int height = 16;
	private final GuiContainer screen;
	
	
	public GuiParticle(GuiContainer screen)
	{
		this.screen = screen;
	}
	
	public void drawParticleStack(ParticleStack particleStack, int x, int y)
	{
		if(particleStack == null)
		{
			return;
		}
		if(particleStack.getParticle() == null)
		{
			return;
		}
		
		
		GlStateManager.disableLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
		screen.mc.getTextureManager().bindTexture(particleStack.getParticle().getTexture());
		screen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height,width,height);
	}
	

	private void drawToolTip(ParticleStack stack,int mouseX, int mouseY, boolean showFocus)
	{
		List<String> text = new ArrayList<String>();
		text.add(TextFormatting.WHITE + Lang.localize(stack.getParticle().getUnlocalizedName()));
		text.add(TextFormatting.GRAY + Lang.localize("gui.qmd.particlestack.amount",Units.getSIFormat(stack.getAmount(),"pu")));
		text.add(TextFormatting.GRAY + Lang.localize("gui.qmd.particlestack.mean_energy",Units.getParticleEnergy(stack.getMeanEnergy())));
		if(showFocus)
		{
			DecimalFormat df = new DecimalFormat("#.####");
			text.add(TextFormatting.GRAY + Lang.localize("gui.qmd.particlestack.focus",df.format(stack.getFocus())));
		}
		screen.drawHoveringText(text, mouseX, mouseY);
		
	}
	
	
	
	public void drawToolTipBox(ParticleStack particleStack, int x, int y,int mouseX, int mouseY)
	{
		if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
		{
			
			drawToolTip(particleStack, mouseX, mouseY, false);
		}
	}
	
	public void drawToolTipBoxwithFocus(ParticleStack particleStack, int x, int y,int mouseX, int mouseY)
	{
		if(particleStack != null)
		{
			if(particleStack.getParticle() != null)
			{
				if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
				{
					
					drawToolTip(particleStack, mouseX, mouseY, true);
				}
			}
		}
		
	}
	

	
	
	
	
	
	
}
