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
 * File created @[Mar 5, 2015, 10:21:29 AM]
 */
package com.spawck.hs2.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.init.HSItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class BlockBiomassPlant extends BlockHS
{
	private IIcon[] iconBuffer;

	public BlockBiomassPlant()
	{
		super(Material.sand);
		this.setTickRandomly(true);
		float size = 0.5F;
		this.setBlockBounds(0.5F - size, 0.0F, 0.5F - size, 0.5F + size, 0.25F, 0.5F + size);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) // TODO
	{
		this.iconBuffer = new IIcon[4];

		this.iconBuffer[0] = ir.registerIcon("mod_HarkenScythe:" + getUnlocalizedName() + "_1");
		this.iconBuffer[1] = ir.registerIcon("mod_HarkenScythe:" + getUnlocalizedName() + "_2");
		this.iconBuffer[2] = ir.registerIcon("mod_HarkenScythe:" + getUnlocalizedName() + "_3");
		this.iconBuffer[3] = ir.registerIcon("mod_HarkenScythe:" + getUnlocalizedName() + "_4");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) // TODO: Double-check this one
	{
		return this.iconBuffer[meta];
	}

	@Override
	public int getRenderType()
	{
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);

		if (world.getBlockLightValue(x, y + 1, z) >= 9)
		{
			int meta = world.getBlockMetadata(x, y, z);

			if (meta < 3)
			{
				float growthRate = this.getGrowthRate(world, x, y, z);

				if (random.nextInt((int)(25.0F / growthRate) + 1) == 0)
				{
					meta++;
					world.setBlockMetadataWithNotify(x, y, z, meta, 2);
				}

				if (world.getBlockMetadata(x, y, z) == 3)
				{
					world.setBlockMetadataWithNotify(x, y - 1, z, 1, 2);
				}
			}
		}
	}

	public void fertilize(World world, int x, int y, int z)
	{
		int randMeta = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);

		if (randMeta > 3)
		{
			randMeta = 3;
		}

		world.setBlockMetadataWithNotify(x, y, z, randMeta, 2);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);

		if (!this.canBlockStay(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		Block soil = world.getBlock(x, y - 1, z); // TODO: Eh?

		return ((world.getFullBlockLightValue(x, y, z) >= 8) || (world.canBlockSeeTheSky(x, y, z))) && (soil != null) && (soil == HSBlocks.creep);
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return HSItems.biomassSeed;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return new ItemStack(HSItems.biomassSeed, 1);
	}

	private float getGrowthRate(World world, int x, int y, int z)
	{
		float growthRate = 1.0F;
		
		Block zm1 = world.getBlock(x, y, z - 1);
		Block zp1 = world.getBlock(x, y, z + 1);
		Block xm1 = world.getBlock(x - 1, y, z);
		Block xp1 = world.getBlock(x + 1, y, z);
		
		Block xm1zm1 = world.getBlock(x - 1, y, z - 1);
		Block xp1zm1 = world.getBlock(x + 1, y, z - 1);
		Block xp1zp1 = world.getBlock(x + 1, y, z + 1);
		Block xm1zp1 = world.getBlock(x - 1, y, z + 1);
		
		boolean flag = (xm1 == this) || (xp1 == this);
		boolean flag1 = (zm1 == this) || (zp1 == this);
		boolean flag2 = (xm1zm1 == this) || (xp1zm1 == this) || (xp1zp1 == this) || (xm1zp1 == this);

		for (int xRadius = x - 1; xRadius <= x + 1; xRadius++)
		{
			for (int zRadius = z - 1; zRadius <= z + 1; zRadius++)
			{
				Block block = world.getBlock(xRadius, y - 1, zRadius);
				int meta = world.getBlockMetadata(xRadius, y - 1, zRadius);
				float rateAdjuster = 0.0F;

				if ((block != null) && (block == HSBlocks.creep) && (meta == 2))
				{
					rateAdjuster = 3.0F;
				}

				if ((xRadius != x) || (zRadius != z))
				{
					rateAdjuster /= 4.0F;
				}

				growthRate += rateAdjuster;
			}
		}

		if ((flag2) || ((flag) && (flag1)))
		{
			growthRate /= 2.0F;
		}

		return growthRate;
	}
}
