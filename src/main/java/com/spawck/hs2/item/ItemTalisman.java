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
 * File created @[Mar 5, 2015, 3:50:39 PM]
 */
package com.spawck.hs2.item;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSEnchantments;
import com.spawck.hs2.init.HSItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemTalisman extends ItemHS
{
	private int specialNumber;
	private int soulCost;
	private int bloodCost;

	public ItemTalisman(int type)
	{
		this.setMaxDamage(1);
		this.specialNumber = type;
		
		if (type == 1) 
			this.setMaxDamage(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		if (par1ItemStack.isItemEnchanted()) return EnumRarity.epic;
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
	{
		int stored = is.getMaxDamage() - is.getItemDamage();
		String pural = "";
		
		if (stored > 1) 
			pural = "s";
		
		if ((is.getItemDamage() == 1) && (is.getItem() == HSItems.talisman))
		{
			list.add("Breaks after " + (stored + is.getItemDamage()) + " resurrection" + pural);
			list.add("Cost: 20 Souls");
			list.add("Soul Attuned");
		} 
		else if ((is.getItemDamage() == 2) && (is.getItem() == HSItems.talisman))
		{
			list.add("Breaks after " + (stored + is.getItemDamage()) + " resurrection" + pural);
			list.add("Cost: 40 Blood");
			list.add("Blood Attuned");
		}
		else if ((is.getItemDamage() == 5) && (is.getItem() == HSItems.talismanEthereal))
		{
			if (stored > 1) pural = "s";
			list.add("Breaks after " + (stored + is.getItemDamage()) + " resurrection" + pural);
			list.add("Cost: 20 Souls");
			list.add("Soul Attuned");
		} 
		else if ((is.getItemDamage() == 6) && (is.getItem() == HSItems.talismanEthereal))
		{
			if (stored > 1) pural = "s";
			list.add("Breaks after " + (stored + is.getItemDamage()) + " resurrection" + pural);
			list.add("Cost: 40 Blood");
			list.add("Blood Attuned");
		}
		else if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.soulAttuned.effectId, is) > 0)
		{
			list.add("Breaks after " + stored + " resurrection" + pural);
			list.add("Added Effect: Valor");
			list.add("Cost: 20 Souls");
		}
		else if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.bloodAttuned.effectId, is) > 0)
		{
			list.add("Breaks after " + stored + " resurrection" + pural);
			list.add("Added Effect: Last Stand");
			list.add("Cost: 40 Blood");
		}
		else 
		{
			list.add("Breaks after " + stored + " resurrection" + pural);
			list.add("Cost: 10 Souls and 20 Blood");
		}
	}

	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer player)
	{
		int startDamage = is.getItemDamage();
		
		if ((startDamage == 1) || (startDamage == 5))
		{
			is.addEnchantment(HSEnchantments.soulAttuned, 1);
			is.setItemDamage(0);
		}
		if ((startDamage == 2) || (startDamage == 6))
		{
			is.addEnchantment(HSEnchantments.bloodAttuned, 1);
			is.setItemDamage(0);
		}
	}
	
	// TODO: Unlocalized name changes
	public static int talismanActivate(int damage, EntityPlayer player)
	{
		int[] talismanData = talismanCostCheck(player);
		int reqMeet = talismanData[0];
		int talismanSlot = talismanData[1];
		int soulCost = talismanData[2];
		int bloodCost = talismanData[3];

		if (reqMeet == 1)
		{
			if (soulCost > 0)
			{
				boolean soulkeeperUsed = false;

				for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
				{
					if (player.inventory.mainInventory[invSize] != null)
					{
						if ("item.HSSoulkeeper".equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
						{
							int soulsLeft = player.inventory.mainInventory[invSize].getMaxDamage() - player.inventory.mainInventory[invSize].getItemDamage();
							
							if (soulsLeft >= soulCost)
							{
								player.inventory.mainInventory[invSize].damageItem(soulCost, player);
								soulkeeperUsed = true;

								if (player.inventory.mainInventory[invSize].getItemDamage() < player.inventory.mainInventory[invSize].getMaxDamage())
									break;
								
								player.inventory.mainInventory[invSize] = new ItemStack(HSItems.essenceKeeper, 1); 
								break;
							}
						}
					}
				}

				if (!soulkeeperUsed)
				{
					for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
					{
						if (player.inventory.mainInventory[invSize] != null)
						{
							boolean bloodkeeperUsed;
							//int jk;
							//int jk;
							if ("item.HSSoulVessel".equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
							{
								int soulsLeft = player.inventory.mainInventory[invSize].getMaxDamage() - player.inventory.mainInventory[invSize].getItemDamage();
								if (soulsLeft >= soulCost)
								{
									player.inventory.mainInventory[invSize].damageItem(soulCost, player);

									if (player.inventory.mainInventory[invSize].getItemDamage() < player.inventory.mainInventory[invSize].getMaxDamage())
										break;
									
									player.inventory.mainInventory[invSize] = new ItemStack(HSItems.essenceVessel, 1); break;
								}
							}
						}
					}
				}
			}

			if (bloodCost > 0)
			{
				boolean bloodkeeperUsed = false;

				for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
				{
					if (player.inventory.mainInventory[invSize] != null)
					{
						if ("item.HSBloodkeeper".equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
						{
							int soulsLeft = player.inventory.mainInventory[invSize].getMaxDamage() - player.inventory.mainInventory[invSize].getItemDamage();
							
							if (soulsLeft >= bloodCost)
							{
								player.inventory.mainInventory[invSize].damageItem(bloodCost, player);
								bloodkeeperUsed = true;

								if (player.inventory.mainInventory[invSize].getItemDamage() < player.inventory.mainInventory[invSize].getMaxDamage())
									break;
								
								player.inventory.mainInventory[invSize] = new ItemStack(HSItems.essenceKeeper, 1); break;
							}
						}
					}
				}

				if (!bloodkeeperUsed)
				{
					for (int jk = 0; jk < player.inventory.mainInventory.length; jk++)
					{
						if (player.inventory.mainInventory[jk] != null)
						{
							if ("item.HSBloodVessel".equals(player.inventory.mainInventory[jk].getItem().getUnlocalizedName()))
							{
								int soulsLeft = player.inventory.mainInventory[jk].getMaxDamage() - player.inventory.mainInventory[jk].getItemDamage();
								
								if (soulsLeft >= bloodCost)
								{
									player.inventory.mainInventory[jk].damageItem(bloodCost, player);

									if (player.inventory.mainInventory[jk].getItemDamage() < player.inventory.mainInventory[jk].getMaxDamage())
										break;
								
									player.inventory.mainInventory[jk] = new ItemStack(HSItems.essenceVessel, 1); break;
								}
							}
						}
					}
				}
			}

			if ("item.HSTalisman".equals(player.inventory.mainInventory[talismanSlot].getItem().getUnlocalizedName()))
			{
				talismanSpecial(player.inventory.mainInventory[talismanSlot], player);
				player.heal(player.getMaxHealth());
				player.getFoodStats().addStats(20, 0.0F);
				player.worldObj.playSoundAtEntity(player, "random.breath", 1.0F, 1.0F);
				player.inventory.consumeInventoryItem(player.inventory.mainInventory[talismanSlot].getItem());
				player.worldObj.playSoundAtEntity(player, "random.glass", 1.0F, 1.0F);
				
				return 0;
			}

			if ("item.HSTalismanEthereal".equals(player.inventory.mainInventory[talismanSlot].getItem().getUnlocalizedName()))
			{
				talismanSpecial(player.inventory.mainInventory[talismanSlot], player);
				player.heal(player.getMaxHealth());
				player.getFoodStats().addStats(20, 0.0F);
				player.worldObj.playSoundAtEntity(player, "random.breath", 1.0F, 1.0F);
				player.inventory.mainInventory[talismanSlot].damageItem(1, player);

				if (player.inventory.mainInventory[talismanSlot].getItemDamage() >= 5)
				{
					player.inventory.consumeInventoryItem(player.inventory.mainInventory[talismanSlot].getItem());
					player.worldObj.playSoundAtEntity(player, "random.glass", 1.0F, 1.0F);
				}
				
				return 0;
			}
		}
		
		return damage;
	}

	public static int[] talismanCostCheck(EntityPlayer player)
	{
		int[] talismanItem = new int[4];
		
		talismanItem[0] = 0;
		talismanItem[1] = 0;
		talismanItem[2] = 0;
		talismanItem[3] = 0;

		for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
		{
			if (player.inventory.mainInventory[invSize] != null)
			{
				if ("item.HSTalisman".equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
				{
					int soulCost = 10;
					int bloodCost = 20;
				
					if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.soulAttuned.effectId, player.inventory.mainInventory[invSize]) > 0)
					{
						soulCost = 20;
						bloodCost = 0;
					}
					
					if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.bloodAttuned.effectId, player.inventory.mainInventory[invSize]) > 0)
					{
						soulCost = 0;
						bloodCost = 40;
					}

					if ((ItemKeeper.soulkeeperCheck(player, soulCost, true) == true) && (ItemKeeper.bloodkeeperCheck(player, bloodCost, true) == true))
					{
						talismanItem[0] = 1;
						talismanItem[1] = invSize;
						talismanItem[2] = soulCost;
						talismanItem[3] = bloodCost;
						
						return talismanItem;
					}
				}
			}

		}

		for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
		{
			if (player.inventory.mainInventory[invSize] != null)
			{
				if ("item.HSTalismanEthereal".equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName()))
				{
					int soulCost = 10;
					int bloodCost = 20;
					
					if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.soulAttuned.effectId, player.inventory.mainInventory[invSize]) > 0)
					{
						soulCost = 20;
						bloodCost = 0;
					}
					
					if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.bloodAttuned.effectId, player.inventory.mainInventory[invSize]) > 0)
					{
						soulCost = 0;
						bloodCost = 40;
					}

					if ((ItemKeeper.soulkeeperCheck(player, soulCost, true) == true) && (ItemKeeper.bloodkeeperCheck(player, bloodCost, true) == true))
					{
						talismanItem[0] = 1;
						talismanItem[1] = invSize;
						talismanItem[2] = soulCost;
						talismanItem[3] = bloodCost;
						
						return talismanItem;
					}
				}
			}
		}
		
		return talismanItem;
	}

	public static void talismanSpecial(ItemStack is, EntityPlayer player)
	{
		player.clearActivePotions();
		player.extinguish();
		
		if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.soulAttuned.effectId, is) > 0)
		{
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 0));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 600, 0));
			
			return;
		}

		if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.bloodAttuned.effectId, is) > 0)
		{
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 600, 0));
			
			return;
		}
	}
}
