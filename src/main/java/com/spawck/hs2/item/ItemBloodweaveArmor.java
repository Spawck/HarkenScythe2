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
 * File created @[Mar 5, 2015, 5:47:49 PM]
 */
package com.spawck.hs2.item;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemBloodweaveArmor extends ItemArmor
{
	Side side = FMLCommonHandler.instance().getEffectiveSide();
	private static final int[] maxDamageArray = { 11, 16, 15, 13 };

	@SideOnly(Side.CLIENT)
	private IIcon field_94605_cw;
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;
	private final ItemArmor.ArmorMaterial material;

	public ItemBloodweaveArmor(ItemArmor.ArmorMaterial par2ItemArmor.ArmorMaterial, int par3, int par4) 
	{
		super(par1, par2ItemArmor.ArmorMaterial, par3, par4);
		this.material = par2ItemArmor.ArmorMaterial;
		this.armorType = par4;
		this.renderIndex = par3;
		this.damageReduceAmount = par2ItemArmor.ArmorMaterial.getDamageReductionAmount(par4);
		setMaxDamage(par2ItemArmor.ArmorMaterial.getDurability(par4));
		this.maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabCombat);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		if (this.material == ItemArmor.ArmorMaterial.CLOTH)
		{
			this.field_94605_cw = par1IconRegister.registerIcon("mod_HarkenScythe:" + getUnlocalizedName() + "_overlay");
		}

		this.itemIcon = par1IconRegister.registerIcon("mod_HarkenScythe:" + getUnlocalizedName());
	}

	public String getArmorTextureFile(ItemStack itemstack)
	{
		if ((itemstack.itemID == mod_HarkenScythe.HSHelmetBloodweave.itemID) || (itemstack.itemID == mod_HarkenScythe.HSPlateBloodweave.itemID) || (itemstack.itemID == mod_HarkenScythe.HSBootsBloodweave.itemID))
		{
			int myColorIs = getColor(itemstack);
			if (myColorIs == 1644825) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_black.png";
			if (myColorIs == 10040115) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_red.png";
			if (myColorIs == 6717235) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_green.png";
			if (myColorIs == 6704179) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_brown.png";
			if (myColorIs == 15892389) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_pink.png";
			if (myColorIs == 6724056) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_lightblue.png";
			if (myColorIs == 14188339) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_orange.png";
			if (myColorIs == 8339378) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_purple.png";
			if (myColorIs == 5013401) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_cyan.png";
			if (myColorIs == 3361970) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_blue.png";
			if (myColorIs == 11685080) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_magenta.png";
			if (myColorIs == 15066419) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_yellow.png";
			if (myColorIs == 8375321) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_lime.png";
			if (myColorIs == 5000268) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_gray.png";
			if (myColorIs == 10066329) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_silver.png";
			if (myColorIs == 16777215) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_white.png";
			return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_default.png";
		}
		if (itemstack.itemID == mod_HarkenScythe.HSLegsBloodweave.itemID)
		{
			int myColorIs = getColor(itemstack);
			if (myColorIs == 1644825) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_black.png";
			if (myColorIs == 10040115) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_red.png";
			if (myColorIs == 6717235) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_green.png";
			if (myColorIs == 6704179) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_brown.png";
			if (myColorIs == 15892389) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_pink.png";
			if (myColorIs == 6724056) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_lightblue.png";
			if (myColorIs == 14188339) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_orange.png";
			if (myColorIs == 8339378) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_purple.png";
			if (myColorIs == 5013401) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_cyan.png";
			if (myColorIs == 3361970) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_blue.png";
			if (myColorIs == 11685080) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_magenta.png";
			if (myColorIs == 15066419) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_yellow.png";
			if (myColorIs == 8375321) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_lime.png";
			if (myColorIs == 5000268) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_gray.png";
			if (myColorIs == 10066329) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_silver.png";
			if (myColorIs == 16777215) return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_white.png";
			return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_2_default.png";
		}
		return "/mods/mod_HarkenScythe/textures/models/armor/bloodweave/bloodweave_1_default.png";
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par2 > 0)
		{
			return 12632256;
		}

		int j = getColor(par1ItemStack);

		if (j < 0)
		{
			j = 10944540;
		}

		return j;
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return this.material == ItemArmor.ArmorMaterial.CLOTH;
	}

	public ItemArmor.ArmorMaterial getArmorMaterial()
	{
		return this.material;
	}

	public boolean hasColor(ItemStack par1ItemStack)
	{
		return !par1ItemStack.getTagCompound().hasKey("display") ? false : !par1ItemStack.hasTagCompound() ? false : this.material != ItemArmor.ArmorMaterial.CLOTH ? false : par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color");
	}

	public int getColor(ItemStack par1ItemStack)
	{
		if (this.material != ItemArmor.ArmorMaterial.CLOTH)
		{
			return -1;
		}

		NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();

		if (nbttagcompound == null)
		{
			return 10944540;
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
		return nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : nbttagcompound1 == null ? 10944540 : 10944540;
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? this.field_94605_cw : super.getIconFromDamageForRenderPass(par1, par2);
	}

	public void removeColor(ItemStack par1ItemStack)
	{
		if (this.material == ItemArmor.ArmorMaterial.CLOTH)
		{
			NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();

			if (nbttagcompound != null)
			{
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

				if (nbttagcompound1.hasKey("color"))
				{
					nbttagcompound1.removeTag("color");
				}
			}
		}
	}

	public void func_82813_b(ItemStack par1ItemStack, int par2)
	{
		if (this.material != ItemArmor.ArmorMaterial.CLOTH)
		{
			throw new UnsupportedOperationException("Can't dye non-leather!");
		}

		NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();

		if (nbttagcompound == null)
		{
			nbttagcompound = new NBTTagCompound();
			par1ItemStack.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display"))
		{
			nbttagcompound.setCompoundTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", par2);
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if (par2ItemStack.itemID == mod_HarkenScythe.HSBloodweaveClothBlock.blockID) return true;
		return false;
	}

	public int getItemEnchantability()
	{
		return this.material.getEnchantability();
	}

	static int[] getMaxDamageArray()
	{
		return maxDamageArray;
	}

	public static int bloodweaveArmorCheck(EntityPlayer entityplayer)
	{
		int bloodweaveArmorBonus = 0;

		for (int i1 = 0; i1 < 4; i1++)
		{
			if (entityplayer.inventory.armorItemInSlot(i1) != null)
			{
				ItemStack bloodweaveArmor = entityplayer.inventory.armorItemInSlot(i1);

				if ("item.HSHelmetBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
				if ("item.HSChestplateBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
				if ("item.HSLeggingsBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
				if ("item.HSBootsBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}

				if ("item.HSHelmetSpectralBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
				if ("item.HSChestplateSpectralBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
				if ("item.HSLeggingsSpectralBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
				if ("item.HSBootsSpectralBloodweave".equals(bloodweaveArmor.getItemName()))
				{
					bloodweaveArmorBonus++;
				}
			}
		}
		return bloodweaveArmorBonus;
	}
}
