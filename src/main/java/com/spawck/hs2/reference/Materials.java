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
 * File created @[Mar 5, 2015, 9:51:47 AM]
 */
package com.spawck.hs2.reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

/**
 * A class that holds custom armor, tool, and block materials.
 * 
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class Materials
{
	public static final class Tools
	{
		public static final Item.ToolMaterial LIVINGMETAL = EnumHelper.addToolMaterial(Names.Materials.LIVINGMETAL, 2, 250, 6.0F, 2, 20);
		public static final Item.ToolMaterial BIOMASS = EnumHelper.addToolMaterial(Names.Materials.BIOMASS, 2, 150, 6.0F, 2, 20);
		public static final Item.ToolMaterial SOUL_REAPER = EnumHelper.addToolMaterial(Names.Materials.SOUL_REAPER, 2, 131, 6.0F, 2, 30);
	}

	public static final class Armor
	{
		public static final ItemArmor.ArmorMaterial LIVINGMETAL = EnumHelper.addArmorMaterial(Names.Materials.LIVINGMETAL, 15, new int[]{2, 6, 5, 2}, 20);
		public static final ItemArmor.ArmorMaterial BIOMASS = EnumHelper.addArmorMaterial(Names.Materials.BIOMASS, 10, new int[]{2, 5, 4, 1}, 20);
	}

	public static final class Blocks {}
}
