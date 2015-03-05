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
 * File created @[Mar 5, 2015, 10:23:47 AM]
 */
package com.spawck.hs2.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.spawck.hs2.reference.HSTab;
import com.spawck.hs2.reference.Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemHS extends Item
{
	public ItemHS()
	{
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(HSTab.HS_TAB);
		this.setNoRepair();
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", Textures.RESOURCE_PREFIX, this.getModifiedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		return String.format("item.%s%s", Textures.RESOURCE_PREFIX, this.getModifiedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon(this.getModifiedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	/**
	 * Removes that funky junk added to an unlocalized item name.
	 * 
	 * @param name the name of the item
	 * @return the name without the funky junk
	 */
	protected String getModifiedUnlocalizedName(String name)
	{
		return name.substring(name.indexOf(".") + 1);
	}
}
