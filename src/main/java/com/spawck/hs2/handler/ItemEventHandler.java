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
 * File created @[Mar 5, 2015, 10:00:05 AM]
 */
package com.spawck.hs2.handler;

import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemEventHandler
{
	@SubscribeEvent
	public void onItemPickupEvent(EntityItemPickupEvent event)
	{
		if (event.item.getEntityItem().getItem() == Items.golden_apple)
		{
			// TODO
		}
	}
}
