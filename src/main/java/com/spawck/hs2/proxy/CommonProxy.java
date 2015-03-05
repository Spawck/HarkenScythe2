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
 * File created @[Mar 5, 2015, 10:08:06 AM]
 */
package com.spawck.hs2.proxy;

import net.minecraft.world.World;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class CommonProxy
{
	public void registerRenderers()
	{
		//tick handlers
	}
	
	public int addArmor(String armor)
	{
		return 0;
	}
	
	public World getClientWorld()
	{
		return null;
	}
	
	public void registerRenderInfo() {}
}
