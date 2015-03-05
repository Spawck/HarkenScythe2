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
 * File created @[Mar 5, 2015, 9:55:49 AM]
 */
package com.spawck.hs2.util;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class TierAbilities
{
	private static String[] livingmetalEquipmentArmorCheck = new String[14];
	private static String[] biomassEquipmentArmorCheck = new String[14];

	public static void sTALivingmetal(EntityPlayer player, int repairAmount)
	{
		for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
		{
			if (player.inventory.mainInventory[invSize] != null)
			{
				for (int lmLoop = 0; lmLoop < livingmetalEquipmentArmorCheck.length; lmLoop++)
				{
					if (livingmetalEquipmentArmorCheck[lmLoop].equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
					{
						int damagedAmount = player.inventory.mainInventory[invSize].getItemDamage();
						
						if (damagedAmount > repairAmount)
						{
							player.inventory.mainInventory[invSize].damageItem(-repairAmount, player);
						}
						else 
						{
							player.inventory.mainInventory[invSize].damageItem(-damagedAmount, player);
						}
					}
				}
			}
		}

		for (int armorInvSize = 0; armorInvSize < 4; armorInvSize++)
		{
			if (player.inventory.armorItemInSlot(armorInvSize) != null)
			{
				for (int lmArmorLoop = 7; lmArmorLoop <= 10; lmArmorLoop++)
				{
					if (livingmetalEquipmentArmorCheck[lmArmorLoop].equals(player.inventory.armorItemInSlot(armorInvSize).getItem().getUnlocalizedName()))
					{
						int damagedAmount = player.inventory.armorItemInSlot(armorInvSize).getItemDamage();
						
						if (damagedAmount > repairAmount)
						{
							player.inventory.armorItemInSlot(armorInvSize).damageItem(-repairAmount, player);
						}
						else
						{
							player.inventory.armorItemInSlot(armorInvSize).damageItem(-damagedAmount, player);
						}
					}
				}
			}
		}
	}

	public static void sTABiomass(EntityPlayer player, int repairAmount)
	{
		for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
		{
			if (player.inventory.mainInventory[invSize] != null)
			{
				for (int lmLoop = 0; lmLoop < biomassEquipmentArmorCheck.length; lmLoop++)
				{
					if (biomassEquipmentArmorCheck[lmLoop].equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
					{
						int damagedAmount = player.inventory.mainInventory[invSize].getItemDamage();
						
						if (damagedAmount > repairAmount)
						{
							player.inventory.mainInventory[invSize].damageItem(-repairAmount, player);
						}
						else 
						{
							player.inventory.mainInventory[invSize].damageItem(-damagedAmount, player);
						}
					}
				}
			}
		}

		for (int armorInvSize = 0; armorInvSize < 4; armorInvSize++)
		{
			if (player.inventory.armorItemInSlot(armorInvSize) != null)
			{
				for (int lmArmorLoop = 7; lmArmorLoop <= 10; lmArmorLoop++)
				{
					if (biomassEquipmentArmorCheck[lmArmorLoop].equals(player.inventory.armorItemInSlot(armorInvSize).getItem().getUnlocalizedName()))
					{
						int damagedAmount = player.inventory.armorItemInSlot(armorInvSize).getItemDamage();
						
						if (damagedAmount > repairAmount)
						{
							player.inventory.armorItemInSlot(armorInvSize).damageItem(-repairAmount, player);
						}
						else
						{
							player.inventory.armorItemInSlot(armorInvSize).damageItem(-damagedAmount, player);
						}
					}
				}
			}
		}
	}

	static
	{
		livingmetalEquipmentArmorCheck[0] = "item.HSScytheLivingmetal";
		livingmetalEquipmentArmorCheck[1] = "item.HSGlaiveLivingmetal";
		livingmetalEquipmentArmorCheck[2] = "item.HSSwordLivingmetal";
		livingmetalEquipmentArmorCheck[3] = "item.HSSpadeLivingmetal";
		livingmetalEquipmentArmorCheck[4] = "item.HSPickaxeLivingmetal";
		livingmetalEquipmentArmorCheck[5] = "item.HSAxeLivingmetal";
		livingmetalEquipmentArmorCheck[6] = "item.HSHoeLivingmetal";
		livingmetalEquipmentArmorCheck[7] = "item.HSHelmetLivingmetal";
		livingmetalEquipmentArmorCheck[8] = "item.HSChestplateLivingmetal";
		livingmetalEquipmentArmorCheck[9] = "item.HSLeggingsLivingmetal";
		livingmetalEquipmentArmorCheck[10] = "item.HSBootsLivingmetal";
		livingmetalEquipmentArmorCheck[11] = "item.ASShieldLivingmetal";
		livingmetalEquipmentArmorCheck[12] = "item.ASShieldLivingmetalGilded";
		livingmetalEquipmentArmorCheck[13] = "item.HSGaintSwordLivingmetal";

		//biomassEquipmentArmorCheck = new String[14];
		biomassEquipmentArmorCheck[0] = "item.HSScytheBiomass";
		biomassEquipmentArmorCheck[1] = "item.HSGlaiveBiomass";
		biomassEquipmentArmorCheck[2] = "item.HSSwordBiomass";
		biomassEquipmentArmorCheck[3] = "item.HSSpadeBiomass";
		biomassEquipmentArmorCheck[4] = "item.HSPickaxeBiomass";
		biomassEquipmentArmorCheck[5] = "item.HSAxeBiomass";
		biomassEquipmentArmorCheck[6] = "item.HSHoeBiomass";
		biomassEquipmentArmorCheck[7] = "item.HSHelmetBiomass";
		biomassEquipmentArmorCheck[8] = "item.HSChestplateBiomass";
		biomassEquipmentArmorCheck[9] = "item.HSLeggingsBiomass";
		biomassEquipmentArmorCheck[10] = "item.HSBootsBiomass";
		biomassEquipmentArmorCheck[11] = "item.ASShieldBiomass";
		biomassEquipmentArmorCheck[12] = "item.ASShieldBiomassGilded";
		biomassEquipmentArmorCheck[13] = "item.HSGaintSwordBiomass";
	}
}
