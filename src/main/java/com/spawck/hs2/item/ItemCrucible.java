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
 * File created @[Mar 5, 2015, 5:28:43 PM]
 */
package com.spawck.hs2.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemCrucible extends Item
{
	private Block spawnBlock;

	public ItemCrucible(int par1, Block par2Block)
	{
		setCreativeTab(CreativeTabs.tabMisc);
		this.spawnBlock = par2Block;
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon("mod_HarkenScythe:" + getUnlocalizedName());
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlock(x, y, z);

		if ((block == Blocks.snow) && ((world.getBlockMetadata(x, y, z) & 0x7 /*I believe that's just 7*/) < 1))
		{
			side = 1;
		}
		else if ((block != Blocks.vine) && (block != Blocks.tallgrass) && (block != Blocks.deadbush))
		{
			if (side == 0)
			{
				y--;
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
		}

		if (!player.canPlayerEdit(x, y, z, side, is))
		{
			return false;
		}
		if (is.stackSize == 0)
		{
			return false;
		}

		if (world.canPlaceEntityOnSide(this.spawnBlock, x, y, z, false, side, (Entity)null, is))
		{
			Block spawnBlock = this.spawnBlock;
			int meta = spawnBlock.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);

			if (world.setBlock(x, y, z, this.spawnBlock, meta, 3))
			{
				if (world.getBlock(x, y, z) == this.spawnBlock)
				{
					this.spawnBlock.onBlockPlacedBy(world, x, y, z, player, is);
					this.spawnBlock.onPostBlockPlaced(world, x, y, z, meta);
				}

				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, spawnBlock.stepSound.func_150496_b()/*TODO: getPlaceSound()*/, (spawnBlock.stepSound.getVolume() + 1.0F) / 2.0F, spawnBlock.stepSound.getPitch() * 0.8F);
				is.stackSize -= 1;
			}
		}

		return true;
	}
}
