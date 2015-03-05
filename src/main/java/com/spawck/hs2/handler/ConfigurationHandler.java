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
 * File created @[Mar 5, 2015, 9:59:06 AM]
 */
package com.spawck.hs2.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * The configuration class for HS2.
 * 
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ConfigurationHandler
{
public static Configuration config;
	
	public static int enchSoulAttunedId;
	public static int enchBloodAttunedId;
	public static int enchSoulstealId;
	public static int enchBloodlettingId;
	public static int enchBlightId;
	public static int enchHemorrhageId;
	public static int enchVitalityId;
	public static int enchExudeId;
	public static int enchAfterlifeId;
	public static int enchDecapitateId;
	public static int enchWardId;
	public static int enchSanguinaryId;
	
	public static void initialize(File file)
	{
		config = new Configuration(file);
		config.load();
		
		config.addCustomCategoryComment("Enchantments", "Enchantment IDs");
		int enchIndex = 63;
		String enchCategory = "Enchantments";
		
		Property enchSoulAt = config.get(enchCategory, "ench_soul_attuned", enchIndex++);
		enchSoulAttunedId = enchSoulAt.getInt();
		
		Property enchBloodAt = config.get(enchCategory, "ench_blood_attuned", enchIndex++);
		enchBloodAttunedId = enchBloodAt.getInt();
		
		Property enchSoulSt = config.get(enchCategory, "ench_soulsteal", enchIndex++);
		enchSoulstealId = enchSoulSt.getInt();
		
		Property enchBloodL = config.get(enchCategory, "ench_bloodletting", enchIndex++);
		enchBloodlettingId = enchBloodL.getInt();
		
		Property enchBlight = config.get(enchCategory, "ench_blight", enchIndex++);
		enchBlightId = enchBlight.getInt();
		
		Property enchHemo = config.get(enchCategory, "ench_hemorrhage", enchIndex++);
		enchHemorrhageId = enchHemo.getInt();
		
		Property enchVit = config.get(enchCategory, "ench_vitality", enchIndex++);
		enchVitalityId = enchVit.getInt();
		
		Property enchEx = config.get(enchCategory, "ench_exude", enchIndex++);
		enchExudeId = enchEx.getInt();
		
		Property enchAfter = config.get(enchCategory, "ench_afterlife", enchIndex++);
		enchAfterlifeId = enchAfter.getInt();
		
		Property enchDecap = config.get(enchCategory, "ench_decapitate", enchIndex++);
		enchDecapitateId = enchDecap.getInt();
		
		Property enchWard = config.get(enchCategory, "ench_ward", enchIndex++);
		enchWardId = enchWard.getInt();
		
		Property enchSang = config.get(enchCategory, "ench_sanguinary", enchIndex++);
		enchSanguinaryId = enchSang.getInt();
		
		config.addCustomCategoryComment("Entities", "Entity IDs");
		
		//
		
		config.save();
	}
	
	public static void save()
	{
		config.save();
	}
}
