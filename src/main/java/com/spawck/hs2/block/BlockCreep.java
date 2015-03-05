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
 * File created @[Mar 5, 2015, 10:22:11 AM]
 */
package com.spawck.hs2.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.spawck.hs2.reference.Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class BlockCreep extends BlockHS
{
	private IIcon[] iconBuffer;

	public BlockCreep()
	{
		super(Material.sand);
		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		this.iconBuffer = new IIcon[4];

		this.iconBuffer[0] = ir.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName() + "_top");
		this.iconBuffer[1] = ir.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName() + "_tilled");
		this.iconBuffer[2] = ir.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName() + "_tilledBloodied");
		this.iconBuffer[3] = ir.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName() + "_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 1 ? this.iconBuffer[meta] : side == 0 ? this.iconBuffer[3] : this.iconBuffer[3];
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			if ((world.getBlockLightValue(x, y + 1, z) < 4) && (world.getBlockLightOpacity(x, y + 1, z) > 2))
			{
				world.setBlock(x, y, z, Blocks.soul_sand);
			}
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int loop = 0; loop < 4; loop++)
				{
					int randX = x + random.nextInt(3) - 1;
					int randY = y + random.nextInt(5) - 3;
					int randZ = z + random.nextInt(3) - 1;
					
					Block block = world.getBlock(randX, randY + 1, randZ);

					if ((world.getBlock(randX, randY, randZ) == Blocks.soul_sand) && (world.getBlockLightValue(randX, randY + 1, randZ) >= 4) && (world.getBlockLightOpacity(randX, randY + 1, randZ) <= 2))
					{
						world.setBlock(randX, randY, randZ, this);
					}
				}
			}
		}

		int meta = world.getBlockMetadata(x, y, z);
		Material plantOnTop = world.getBlock(x, y + 1, z).getMaterial();

		if ((meta >= 1) && (plantOnTop != Material.plants))
		{
			world.setBlockMetadataWithNotify(x, y, z, --meta, 2);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float size = 0.125F;
		
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - size, z + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		entity.motionX *= 0.4D;
		entity.motionZ *= 0.4D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		super.randomDisplayTick(world, x, y, z, random);

		if (random.nextInt(10) == 0)
		{
			world.spawnParticle("townaura", x + random.nextFloat(), y + 1.1F, z + random.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemFromBlock(Blocks.soul_sand);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
}
