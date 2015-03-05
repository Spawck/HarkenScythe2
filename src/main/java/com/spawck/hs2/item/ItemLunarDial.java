/**
 * This class was created by <spawck> as part of the Harken Scythe 2 
 * mod for Minecraft.
 *
 * Harken Scythe 2 is open-source and distributed under the 
 * GNU GPL v2 License.
 * (https://www.gnu.org/licenses/gpl-2.0.html)
 *
 * Harken Scythe 2 is based on the original Harken Scythe mod created 
 * by Jade_Knightblazer:
 * 
 * Harken Scythe (c) Jade_Knightblazer 2012-2013
 * (http://bit.ly/18EyAZo)
 *
 * File created @[Mar 5, 2015, 10:27:29 AM]
 */
package com.spawck.hs2.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.spawck.hs2.reference.Names;
import com.spawck.hs2.reference.Textures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemLunarDial extends ItemHS
{
	private int moonPhase;
	private IIcon[] iconBuffer;
	Side side = FMLCommonHandler.instance().getEffectiveSide();
	
	public ItemLunarDial()
	{
		this.moonPhase = 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.iconBuffer = new IIcon[8];
		
		for (int subset = 0; subset < this.iconBuffer.length; subset++)
		{
			this.iconBuffer[subset] = ir.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.LUNAR_DIAL + "_" + subset);
		}
		
		this.itemIcon = this.iconBuffer[this.moonPhase];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack is)
	{
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int tick, boolean isHolding)
	{
		if (this.side == Side.CLIENT)
		{
			if ((this.moonPhase != world.getMoonPhase()) && (entity.dimension == 0))
			{
				this.moonPhase = world.getMoonPhase();
				this.itemIcon = this.iconBuffer[this.moonPhase];
			}
		}
	}
}
