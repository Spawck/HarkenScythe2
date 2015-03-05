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
 * File created @[Mar 5, 2015, 10:17:58 AM]
 */
package com.spawck.hs2.init;

import com.spawck.hs2.block.BlockBiomass;
import com.spawck.hs2.block.BlockBiomassPlant;
import com.spawck.hs2.block.BlockCreep;
import com.spawck.hs2.block.BlockHS;
import com.spawck.hs2.block.BlockSoulLight;
import com.spawck.hs2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class HSBlocks
{
	public static final BlockHS biomass = new BlockBiomass();
	public static final BlockHS creep = new BlockCreep();
	public static final BlockHS biomassPlant = new BlockBiomassPlant();
	public static final BlockHS soulLight = new BlockSoulLight();
	
	public static void init()
	{
		
	}
}
