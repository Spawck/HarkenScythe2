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
 * File created @[Mar 5, 2015, 10:22:55 AM]
 */
package com.spawck.hs2.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.spawck.hs2.entity.EntitySoul;
import com.spawck.hs2.entity.EntitySoulLost;
import com.spawck.hs2.entity.EntitySpectralMiner;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class BlockSoulLight extends BlockHS
{
	public BlockSoulLight()
	{
		super(Material.circuits);
		this.setTickRandomly(true);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 0.01F, 0.01F);
		this.setLightLevel(0.5F);
	}

	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			double size = 1.0D;
			AxisAlignedBB aABB = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(size, size, size);
			aABB.maxY = world.getHeight();
			List list = world.getEntitiesWithinAABB(EntityLiving.class, aABB);
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityLiving living = (EntityLiving)iterator.next();
				
				if (((living instanceof EntitySoul)) || ((living instanceof EntitySoulLost)))
				{
					return;
				}

				if (((living instanceof EntitySpectralMiner)) && (living.getHeldItem() != null) && (living.getHeldItem().getItem() == Item.getItemFromBlock(Blocks.torch)))
				{
					return;
				}

			}

			world.setBlockToAir(x, y, z);
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (random.nextFloat() > 0.2F)
		{
			double size = 1.0D;
			AxisAlignedBB aABB = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(size, size, size);
			aABB.maxY = world.getHeight();
			List list = world.getEntitiesWithinAABB(EntityLiving.class, aABB);
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityLiving living = (EntityLiving)iterator.next();

				if (((living instanceof EntitySpectralMiner)) && (living.getHeldItem() != null) && (living.getHeldItem().getItem() == Item.getItemFromBlock(Blocks.torch)))
				{
					return;
				}
			}

			world.setBlockToAir(x, y, z);
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean canCollideCheck(int meta, boolean isHoldingBoat)
	{
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return false;
	}

	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y - 1, z);
		//Block block = Block.blocksList[var5];
		return (block != null) && ((block.isLeaves(world, x, y - 1, z)) || (block.isOpaqueCube())) ? world.getBlock(x, y - 1, z).getMaterial().blocksMovement() : false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		this.canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		if (!this.canPlaceBlockAt(world, x, y, z))
		{
			world.setBlockToAir(x, y, z);
			
			return false;
		}
		
		return false;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
}
