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
 * File created @[Mar 5, 2015, 10:09:35 AM]
 */
package com.spawck.hs2.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.spawck.hs2.reference.HSTab;
import com.spawck.hs2.reference.Textures;
import com.spawck.hs2.tileentity.TileEntityHS;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class BlockHS extends Block
{
	public BlockHS(Material mat)
	{
		super(mat);
		this.setCreativeTab(HSTab.HS_TAB);
	}

	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, getModifiedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		this.blockIcon = ir.registerIcon(String.format("%s", getModifiedUnlocalizedName(this.getUnlocalizedName())));
	}

	protected String getModifiedUnlocalizedName(String name)
	{
		return name.substring(name.indexOf(".") + 1);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		this.dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack is)
	{
		if (world.getTileEntity(x, y, z) instanceof TileEntityHS)
		{
			int direction = 0;
			int facing = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			
			if (facing == 0)
			{
				direction = ForgeDirection.NORTH.ordinal();
			}
			else if (facing == 1)
			{
				direction = ForgeDirection.EAST.ordinal();
			}
			else if (facing == 2)
			{
				direction = ForgeDirection.SOUTH.ordinal();
			}
			else if (facing == 3)
			{
				direction = ForgeDirection.WEST.ordinal();
			}
			
			if (is.hasDisplayName())
			{
				((TileEntityHS)world.getTileEntity(x, y, z)).setCustomName(is.getDisplayName());
			}
			
			((TileEntityHS)world.getTileEntity(x, y, z)).setOrientation(direction);
		}
	}
	
	protected void dropInventory(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (!(tileEntity instanceof IInventory))
		{
			return;
		}
		
		IInventory inventory = (IInventory)tileEntity;
		
		for (int inventorySize = 0; inventorySize < inventory.getSizeInventory(); inventorySize++)
		{
			ItemStack is = inventory.getStackInSlot(inventorySize);
			
			if (is != null && is.stackSize > 0)
			{
				Random rand = new Random();
				float dX = rand.nextFloat() * 0.8F + 0.1F;
				float dY = rand.nextFloat() * 0.8F + 0.1F;
				float dZ = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, is.copy());
				
				if (is.hasTagCompound())
				{
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) is.getTagCompound().copy());
				}
				
				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				is.stackSize = 0;
			}
		}
	}
}
