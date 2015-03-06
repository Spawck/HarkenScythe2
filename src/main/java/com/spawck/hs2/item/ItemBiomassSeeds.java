package com.spawck.hs2.item;

import com.spawck.hs2.init.HSBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBiomassSeeds extends ItemHS
{
	private int specialNumber;

	public ItemBiomassSeeds(int type)
	{
		this.specialNumber = type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack is)
	{
		if (this.specialNumber == 2) 
			return true;
		
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack is)
	{
		if (this.specialNumber == 2) 
			return EnumRarity.epic;
		
		return EnumRarity.common;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		if (this.specialNumber == 0) 
			return false;
		
		if (side != 1)
		{
			return false;
		}
		
		if ((player.canPlayerEdit(x, y, z, side, is)))// && (player.canPlayerEdit(x, y, z, side, is)))
		{
			Block block = world.getBlock(x, y, z);
			int i2 = world.getBlockMetadata(x, y, z);

			if (this.specialNumber == 1)
			{
				if (block == Blocks.soul_sand)
				{
					world.setBlock(x, y, z, HSBlocks.creep);
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "mob.slime.attack", 0.3F, 0.5F);

					if (player.capabilities.isCreativeMode)
					{
						return true;
					}

					is.stackSize -= 1;
					return true;
				}

			}

			if (this.specialNumber == 2)
			{
				if ((block == HSBlocks.creep) && (i2 >= 1))
				{
					world.setBlock(x, y + 1, z, HSBlocks.biomassPlant);

					if (player.capabilities.isCreativeMode)
					{
						return true;
					}

					is.stackSize -= 1;
					return true;
				}
			}
		}
	
		return false;
	}
}
