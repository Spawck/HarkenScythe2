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
 * File created @[Mar 5, 2015, 3:50:12 PM]
 */
package com.spawck.hs2.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.spawck.hs2.reference.HSTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemArmorHS extends ItemArmor
{
	private static final int[] maxDamageArray = { 11, 16, 15, 13 };

	@SideOnly(Side.CLIENT)
	private IIcon field_94605_cw;
	public final int armorType;
	public final int renderIndex;
	private final ItemArmor.ArmorMaterial material;

	public ItemArmorHS(ItemArmor.ArmorMaterial armorMat, int renderIndex, int type) 
	{
		super(armorMat, renderIndex, type);
		this.material = armorMat;
		this.armorType = type;
		this.renderIndex = renderIndex;
		this.setCreativeTab(HSTab.HS_TAB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("mod_HarkenScythe:" + getUnlocalizedName());
	}

	/*
	public String getArmorTextureFile(ItemStack itemstack)
	{
		if ((itemstack.itemID == mod_HarkenScythe.HSHelmetLivingmetal.itemID) || (itemstack.itemID == mod_HarkenScythe.HSPlateLivingmetal.itemID) || (itemstack.itemID == mod_HarkenScythe.HSBootsLivingmetal.itemID))
		{
			return "/mods/mod_HarkenScythe/textures/models/armor/livingmetal/livingmetal_1.png";
		}
		if (itemstack.itemID == mod_HarkenScythe.HSLegsLivingmetal.itemID)
		{
			return "/mods/mod_HarkenScythe/textures/models/armor/livingmetal/livingmetal_2.png";
		}

		if ((itemstack.itemID == mod_HarkenScythe.HSHelmetBiomass.itemID) || (itemstack.itemID == mod_HarkenScythe.HSPlateBiomass.itemID) || (itemstack.itemID == mod_HarkenScythe.HSBootsBiomass.itemID))
		{
			return "/mods/mod_HarkenScythe/textures/models/armor/biomass/biomass_1.png";
		}
		if (itemstack.itemID == mod_HarkenScythe.HSLegsBiomass.itemID)
		{
			return "/mods/mod_HarkenScythe/textures/models/armor/biomass/biomass_2.png";
		}

		return "/mods/mod_HarkenScythe/textures/models/armor/livingmetal/livingmetal_1.png";
	}*/

	@Override
	public boolean getIsRepairable(ItemStack repairedItem, ItemStack repairingItem)
	{
		return this.material.func_151685_b()/*getArmorCraftingMaterial*/ == repairingItem.getItem() ? true : super.getIsRepairable(repairedItem, repairingItem);
	}
}
