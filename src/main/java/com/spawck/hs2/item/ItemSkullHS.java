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
 * File created @[Mar 5, 2015, 4:12:50 PM]
 */
package com.spawck.hs2.item;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import com.spawck.hs2.init.HSBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemSkullHS extends ItemArmor implements ISpecialArmor
{
	private static final String[] skullTypes = {"Spider", "Cave Spider", "Enderman", "Pigman Zombie", "Villager Zombie", "Villager", "Cow", "Mooshroom", "Pig", "Squid", "Sheep", "Wolf", "Ocelot", "Ender Dragon", "Bat", "Chicken"};
	public static final String[] unlocNames = {"item.HSSkull_spider", "item.HSSkull_cavespider", "item.HSSkull_enderman", "item.HSSkull_pigzombie", "item.HSSkull_zombie_villager", "item.HSSkull_villager", "item.HSSkull_cow", "item.HSSkull_mooshroom", "item.HSSkull_pig", "item.HSSkull_squid", "item.HSSkull_sheep", "item.HSSkull_wolf", "item.HSSkull_ozelot", "item.HSSkull_enderdragon", "item.HSSkull_bat", "item.HSSkull_chicken"};
	private static final String[] skullNames = {"spider", "cavespider", "enderman", "pigmanzombie", "villagerzombie", "villager", "cow", "mooshroom", "pig", "squid", "sheep", "wolf", "ocelot", "enderdragon", "bat", "chicken"};

	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;
	private final ItemArmor.ArmorMaterial material;

	public ItemSkullHS(int par1, ItemArmor.ArmorMaterial armorMat, int renderIndex, int type)
	{
		super(armorMat, renderIndex, type);
		this.maxStackSize = 64;
		this.material = armorMat;
		this.armorType = type;
		this.renderIndex = renderIndex;
		this.damageReduceAmount = 0;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.iconArray = new IIcon[unlocNames.length];

		for (int i = 0; i < unlocNames.length; i++)
		{
			this.iconArray[i] = ir.registerIcon("mod_HarkenScythe:" + unlocNames[i]);
		}
	}

	public String getArmorTextureFile(ItemStack itemstack)
	{
		int var2 = itemstack.getItemDamage();

		if ((var2 < 0) || (var2 >= skullTypes.length))
		{
			var2 = 0;
		}

		return "/mods/mod_HarkenScythe/textures/models/mob/skulls/" + skullNames[var2] + ".png";
	}

	@Override
	public ItemArmor.ArmorMaterial getArmorMaterial()
	{
		return this.material;
	}

	@Override
	public boolean getIsRepairable(ItemStack repairedItem, ItemStack repairingItem)
	{
		return false;
	}

	@Override
	public int getItemEnchantability()
	{
		return this.material.getEnchantability();
	}

	public ISpecialArmor.ArmorProperties getProperties(EntityLiving player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		return new ISpecialArmor.ArmorProperties(0, 0.0D, 2147483647);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		return this.damageReduceAmount;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (side == 0)
		{
			return false;
		}
		
		if (!world.getBlock(x, y, z).getMaterial().isSolid())
		{
			return false;
		}

		if (side == 1)
		{
			y++;
		}

		if (side == 2)
		{
			z--;
		}

		if (side == 3)
		{
			z++;
		}

		if (side == 4)
		{
			x--;
		}

		if (side == 5)
		{
			x++;
		}

		if (!player.canPlayerEdit(x, y, z, side, is))
		{
			return false;
		}
		
		if (!HSBlocks.skull.canPlaceBlockAt(world, x, y, z))
		{
			return false;
		}

		world.setBlock(x, y, z, HSBlocks.skull, side, 2);
		int rot = 0;

		if (side == 1)
		{
			rot = MathHelper.floor_double(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 0xF;
		}

		TileEntity te = world.getTileEntity(x, y, z);

		if ((te != null) && ((te instanceof TileEntitySkullHS)))
		{
			String owner = "";

			if ((is.hasTagCompound()) && (is.getTagCompound().hasKey("SkullOwner")))
			{
				owner = is.getTagCompound().getString("SkullOwner");
			}

			((TileEntitySkullHS)te).setSkullType(is.getItemDamage(), owner);
			((TileEntitySkullHS)te).setSkullRotation(rot);
		}

		is.stackSize -= 1;
		
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List tab)
	{
		for (int var4 = 0; var4 < skullTypes.length; var4++)
		{
			tab.add(new ItemStack(item, 1, var4));
		}
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		if ((meta < 0) || (meta >= skullTypes.length))
		{
			meta = 0;
		}

		return this.iconArray[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int i = is.getItemDamage();

		if ((i < 0) || (i >= skullTypes.length))
		{
			i = 0;
		}

		return super.getUnlocalizedName() + "." + skullTypes[i];
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		return skullTypes[is.getItemDamage()] + " Head";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		return is;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		return new ISpecialArmor.ArmorProperties(0, 0.0D, 2147483647);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		stack.damageItem(damage, entity);	
	}
}
