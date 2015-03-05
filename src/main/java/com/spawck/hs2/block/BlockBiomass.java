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
 * File created @[Mar 5, 2015, 10:20:59 AM]
 */
package com.spawck.hs2.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class BlockBiomass extends BlockHS
{
	public BlockBiomass()
	{
		super(Material.plants);
		this.setTickRandomly(true);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess iBlockAcess, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
	{
		return true;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);
		this.onNeighborBlockChange(world, x, y, z, world.getBlock(x, y, z));
		Block bloodPoolBlock = world.getBlock(x, y + 1, z);
		int bloodGrowthStage = world.getBlockMetadata(x, y + 1, z);

		if (world.isAirBlock(x, y + 1, z))
		{
			float growthRate = getGrowthRate(world, x, y, z);

			if (random.nextInt((int)(25.0F / growthRate) + 1) == 0)
			{
				// TODO: world.setBlock(x, y + 1, z, mod_HarkenScythe.HSSplashBloodBlock, 4, 2);
				return;
			}
		}
		/* TODO
		if (bloodPoolBlock == mod_HarkenScythe.HSSplashBloodBlock)
		{
			if (bloodGrowthStage == 4)
			{
				float f = getGrowthRate(world, x, y, z);

				if (random.nextInt((int)(25.0F / f) + 1) == 0)
				{
					Random diceRoller = new Random();
					int randBlood = diceRoller.nextInt(2);
					bloodGrowthStage = bloodGrowthStage - 1 - randBlood;

					world.setBlockMetadataWithNotify(x, y + 1, z, bloodGrowthStage, 2);
				}
			}
		}*/
	}

	private float getGrowthRate(World world, int x, int y, int z)
	{
		float growthRate = 1.0F;
		
		Block blockZM1 = world.getBlock(x, y, z - 1);
		Block blockZP1 = world.getBlock(x, y, z + 1);
		Block blockXM1 = world.getBlock(x - 1, y, z);
		Block blockXP1 = world.getBlock(x + 1, y, z);
		
		Block blockXM1ZM1 = world.getBlock(x - 1, y, z - 1);
		Block blockXP1ZM1 = world.getBlock(x + 1, y, z - 1);
		Block blockXP1ZP1 = world.getBlock(x + 1, y, z + 1);
		Block blockXM1ZP1 = world.getBlock(x - 1, y, z + 1);
		
		boolean xm1xp1Check = (blockXM1 == this) || (blockXP1 == this);
		boolean zm1zp1Check = (blockZM1 == this) || (blockZP1 == this);
		
		boolean radialCheck = (blockXM1ZM1 == this) || (blockXP1ZM1 == this) || (blockXP1ZP1 == this) || (blockXM1ZP1 == this);

		for (int xRange = x - 1; xRange <= x + 1; xRange++)
		{
			for (int zRange = z - 1; zRange <= z + 1; zRange++)
			{
				float rateAdjuster = 1.0F;

				if ((xRange != x) || (zRange != z))
				{
					rateAdjuster /= 4.0F;
				}

				growthRate += rateAdjuster;
			}
		}

		if ((radialCheck) || ((xm1xp1Check) && (zm1zp1Check)))
		{
			growthRate /= 2.0F;
		}

		return growthRate;
	}
}
