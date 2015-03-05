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
 * File created @[Mar 5, 2015, 10:18:29 AM]
 */
package com.spawck.hs2.init;

import net.minecraft.enchantment.Enchantment;

import com.spawck.hs2.enchantment.EnchantmentAfterlife;
import com.spawck.hs2.enchantment.EnchantmentBlight;
import com.spawck.hs2.enchantment.EnchantmentBloodAttuned;
import com.spawck.hs2.enchantment.EnchantmentBloodletting;
import com.spawck.hs2.enchantment.EnchantmentDecapitate;
import com.spawck.hs2.enchantment.EnchantmentExude;
import com.spawck.hs2.enchantment.EnchantmentHemorrhage;
import com.spawck.hs2.enchantment.EnchantmentSanguinary;
import com.spawck.hs2.enchantment.EnchantmentSoulAttuned;
import com.spawck.hs2.enchantment.EnchantmentSoulsteal;
import com.spawck.hs2.enchantment.EnchantmentVitality;
import com.spawck.hs2.enchantment.EnchantmentWard;
import com.spawck.hs2.handler.ConfigurationHandler;
import com.spawck.hs2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class HSEnchantments
{
	public static final Enchantment soulAttuned = new EnchantmentSoulAttuned(ConfigurationHandler.enchSoulAttunedId);
	public static final Enchantment bloodAttuned = new EnchantmentBloodAttuned(ConfigurationHandler.enchBloodAttunedId);
	public static final Enchantment soulsteal = new EnchantmentSoulsteal(ConfigurationHandler.enchSoulstealId);
	public static final Enchantment bloodletting = new EnchantmentBloodletting(ConfigurationHandler.enchBloodlettingId);
	public static final Enchantment blight = new EnchantmentBlight(ConfigurationHandler.enchBlightId);
	public static final Enchantment hemorrhage = new EnchantmentHemorrhage(ConfigurationHandler.enchHemorrhageId);
	public static final Enchantment vitality = new EnchantmentVitality(ConfigurationHandler.enchVitalityId);
	public static final Enchantment exude = new EnchantmentExude(ConfigurationHandler.enchExudeId);
	public static final Enchantment afterlife = new EnchantmentAfterlife(ConfigurationHandler.enchAfterlifeId);
	public static final Enchantment decapitate = new EnchantmentDecapitate(ConfigurationHandler.enchDecapitateId);
	public static final Enchantment ward = new EnchantmentWard(ConfigurationHandler.enchWardId);
	public static final Enchantment sanguinary = new EnchantmentSanguinary(ConfigurationHandler.enchSanguinaryId);
	
	public static void init()
	{
		//Enchantment.addToBookList(blarg);
	}
}
