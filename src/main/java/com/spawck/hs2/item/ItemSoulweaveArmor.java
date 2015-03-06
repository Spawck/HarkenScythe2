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
 * File created @[Mar 5, 2015, 5:40:16 PM]
 */
package com.spawck.hs2.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.init.HSItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemSoulweaveArmor extends ItemArmor
{
	Side side = FMLCommonHandler.instance().getEffectiveSide();
	private static final int[] maxDamageArray = { 11, 16, 15, 13 };

	@SideOnly(Side.CLIENT)
	private IIcon field_94605_cw;
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;
	private final ItemArmor.ArmorMaterial material;

	public ItemSoulweaveArmor(ItemArmor.ArmorMaterial armorMat, int par3, int par4)
	{
		super(armorMat, par3, par4);
		this.material = armorMat;
		this.armorType = par4;
		this.renderIndex = par3;
		this.damageReduceAmount = armorMat.getDamageReductionAmount(par4);
		setMaxDamage(armorMat.getDurability(par4));
		this.maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
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
		if ((itemstack.getItem() == HSItems.soulweaveHelmet) || (itemstack.getItem() == HSItems.soulweavePlate) || (itemstack.getItem() == HSItems.soulweaveBoots))
		{
			int myColorIs = this.getColor(itemstack);
			
			if (myColorIs == 1644825) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_black.png";
			if (myColorIs == 10040115) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_red.png";
			if (myColorIs == 6717235) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_green.png";
			if (myColorIs == 6704179) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_brown.png";
			if (myColorIs == 15892389) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_pink.png";
			if (myColorIs == 6724056) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_lightblue.png";
			if (myColorIs == 14188339) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_orange.png";
			if (myColorIs == 8339378) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_purple.png";
			if (myColorIs == 5013401) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_cyan.png";
			if (myColorIs == 3361970) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_blue.png";
			if (myColorIs == 11685080) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_magenta.png";
			if (myColorIs == 15066419) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_yellow.png";
			if (myColorIs == 8375321) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_lime.png";
			if (myColorIs == 5000268) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_gray.png";
			if (myColorIs == 10066329) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_silver.png";
			if (myColorIs == 16777215) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_white.png";
			
			return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_default.png";
		}
		if (itemstack.getItem() == HSItems.soulweaveLegs)
		{
			int myColorIs = getColor(itemstack);
			
			if (myColorIs == 1644825) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_black.png";
			if (myColorIs == 10040115) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_red.png";
			if (myColorIs == 6717235) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_green.png";
			if (myColorIs == 6704179) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_brown.png";
			if (myColorIs == 15892389) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_pink.png";
			if (myColorIs == 6724056) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_lightblue.png";
			if (myColorIs == 14188339) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_orange.png";
			if (myColorIs == 8339378) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_purple.png";
			if (myColorIs == 5013401) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_cyan.png";
			if (myColorIs == 3361970) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_blue.png";
			if (myColorIs == 11685080) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_magenta.png";
			if (myColorIs == 15066419) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_yellow.png";
			if (myColorIs == 8375321) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_lime.png";
			if (myColorIs == 5000268) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_gray.png";
			if (myColorIs == 10066329) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_silver.png";
			if (myColorIs == 16777215) return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_white.png";
			
			return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_default.png";
		}
		
		return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_default.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int par2)
	{
		if (par2 > 0)
		{
			return 16777215;
		}

		int j = getColor(is);

		if (j < 0)
		{
			j = 16777215;
		}

		return j;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return this.material == ItemArmor.ArmorMaterial.CLOTH;
	}

	@Override
	public ItemArmor.ArmorMaterial getArmorMaterial()
	{
		return this.material;
	}

	@Override
	public boolean hasColor(ItemStack is)
	{
		return !is.getTagCompound().hasKey("display") ? false : !is.hasTagCompound() ? false : this.material != ItemArmor.ArmorMaterial.CLOTH ? false : is.getTagCompound().getCompoundTag("display").hasKey("color");
	}

	@Override
	public int getColor(ItemStack is)
	{
		if (this.material != ItemArmor.ArmorMaterial.CLOTH)
		{
			return -1;
		}

		NBTTagCompound tagCompound = is.getTagCompound();

		if (tagCompound == null)
		{
			return 2603484;
		}

		NBTTagCompound nbttagcompound1 = tagCompound.getCompoundTag("display");
		return nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : nbttagcompound1 == null ? 2603484 : 2603484;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? this.field_94605_cw : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	public void removeColor(ItemStack is)
	{
		if (this.material == ItemArmor.ArmorMaterial.CLOTH)
		{
			NBTTagCompound nbttagcompound = is.getTagCompound();

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

	@Override
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
			nbttagcompound.setTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", par2);
	}

	@Override
	public boolean getIsRepairable(ItemStack repairedStack, ItemStack repairingStack)
	{
		if (repairingStack.getItem() == HSBlocks.soulweaveCloth) 
			return true;
		
		return false;
	}

	@Override
	public int getItemEnchantability()
	{
		return this.material.getEnchantability();
	}

	static int[] getMaxDamageArray()
	{
		return maxDamageArray;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		int i = (this.armorType - 3) * -1;

		ItemStack itemstack1 = par3EntityPlayer.inventory.armorItemInSlot(i);

		if (itemstack1 == null)
		{
			par3EntityPlayer.inventory.armorInventory[i] = par1ItemStack.copy();
			par1ItemStack.stackSize = 0;
		}
		return par1ItemStack;
	}

	public static int soulweaveArmorCheck(EntityPlayer player)
	{
		int soulweaveArmorBonus = 0;

		for (int i1 = 0; i1 < 4; i1++)
		{
			if (player.inventory.armorItemInSlot(i1) != null)
			{
				ItemStack SoulweaveArmor = player.inventory.armorItemInSlot(i1);

				if ("item.HSHelmetSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
				if ("item.HSChestplateSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
				if ("item.HSLeggingsSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
				if ("item.HSBootsSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}

				if ("item.HSHelmetSpectralSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
				if ("item.HSChestplateSpectralSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
				if ("item.HSLeggingsSpectralSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
				if ("item.HSBootsSpectralSoulweave".equals(SoulweaveArmor.getDisplayName()))
				{
					soulweaveArmorBonus++;
				}
			}
		}
	
		return soulweaveArmorBonus;
	}
}
