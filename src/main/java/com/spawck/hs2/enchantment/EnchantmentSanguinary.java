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
 * File created @[Mar 5, 2015, 10:43:20 AM]
 */
package com.spawck.hs2.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

import com.spawck.hs2.reference.Names;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class EnchantmentSanguinary extends Enchantment
{
	public EnchantmentSanguinary(int effectId)
	{
		super(effectId, 1, EnumEnchantmentType.weapon);
		this.setName(Names.Enchantments.SANGUINARY);
	}

	@Override
	public int getMinEnchantability(int par1)
	{
		return 100;
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return 100;
	}

	@Override
	public int getMaxLevel()
	{
		return 3;
	}
}
