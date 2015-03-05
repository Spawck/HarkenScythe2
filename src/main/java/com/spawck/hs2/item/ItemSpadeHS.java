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
 * File created @[Mar 5, 2015, 10:30:52 AM]
 */
package com.spawck.hs2.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import com.google.common.collect.Sets;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemSpadeHS extends ItemToolHS
{
	private static final Set blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	
	public ItemSpadeHS(Item.ToolMaterial toolMat)
	{
		super(1.0F, toolMat, blocksEffectiveAgainst);
	}
	
	public boolean func_150897_b(Block block) /* getStrVsBlock */
    {
        return block == Blocks.snow_layer ? true : block == Blocks.snow;
    }
}
