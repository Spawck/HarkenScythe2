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
 * File created @[Mar 5, 2015, 10:28:09 AM]
 */
package com.spawck.hs2.item;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.init.HSItems;
import com.spawck.hs2.reference.Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemKeeper extends ItemHS
{
	public static final String[] keeperFillIconNameArray = {"_0", "_1", "_2", "_3"};
	private IIcon[] iconArray;
	private int specialNumber;
	private String keeperType;
	public int flaskDrinkCount = 0;

	private static int[] soulBuffs = new int[11];
	private static int[] soulDebuffs;

	public ItemKeeper(int num, String type)
	{
		this.setMaxDamage(20);
		this.specialNumber = num;
		this.keeperType = type;
		this.flaskDrinkCount = 0;
		
		if ((num == 2) || (num == 3) || (num == 4)) 
			this.setMaxDamage(40);
		if ((num == 1) || (num == 2)) 
			this.setContainerItem(HSItems.essenceKeeper);
		if (num == 5) 
			this.setMaxDamage(80);
		if ((num == 4) || (num == 5)) 
			this.setContainerItem(HSItems.essenceVessel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) // TODO
	{
		if ((this.specialNumber == 0) || (this.specialNumber == 3))
		{
			this.itemIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName());
		}
		else 
		{
			this.itemIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName() + keeperFillIconNameArray[3]);
			this.iconArray = new IIcon[keeperFillIconNameArray.length];

			for (int size = 0; size < this.iconArray.length; size++)
			{
				this.iconArray[size] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + getUnlocalizedName() + keeperFillIconNameArray[size]);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if ((this.specialNumber == 1) || (this.specialNumber == 4) || (this.specialNumber == 2) || (this.specialNumber == 5))
		{
			int quarter = getMaxDamage() / 4;

			if (damage == 0) 
				return this.itemIcon = this.iconArray[3];
			if (damage > quarter * 2) 
				return this.itemIcon = this.iconArray[0];
			if (damage > quarter) 
				return this.itemIcon = this.iconArray[1];
			
			return this.itemIcon = this.iconArray[2];
		}
		
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack is)
	{
		if ((this.specialNumber == 0) || (this.specialNumber == 3)) 
			return false;
		
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack is)
	{
		if ((this.specialNumber == 0) || (this.specialNumber == 3)) 
			return EnumRarity.common;
	
		return EnumRarity.epic;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) // move this to event handlers?
	{
		if ((this.specialNumber != 0) && (this.specialNumber != 3))
		{
			int stored = is.getMaxDamage() - is.getItemDamage();
			int storedMax = is.getMaxDamage();
			list.add("Stored " + this.keeperType + ": " + stored + " / " + storedMax);
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		if ((this.specialNumber == 0) || (this.specialNumber == 3))
		{
			return EnumAction.none;
		}
		
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 32;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if ((this.specialNumber != 0) && (this.specialNumber != 3))
		{
			if (((this.specialNumber == 1) && (this.flaskDrinkCount >= is.getMaxDamage() - is.getItemDamage())) || ((this.specialNumber == 2) && (this.flaskDrinkCount * 2 >= is.getMaxDamage() - is.getItemDamage())) || ((this.specialNumber == 4) && (this.flaskDrinkCount >= is.getMaxDamage() - is.getItemDamage())) || ((this.specialNumber == 5) && (this.flaskDrinkCount * 2 >= is.getMaxDamage() - is.getItemDamage())))
			{
				player.stopUsingItem();
				this.onPlayerStoppedUsing(is, world, player, is.getMaxItemUseDuration());
				return is;
			}

			player.setItemInUse(is, getMaxItemUseDuration(is));
			return is;
		}
		
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int itemInUseCount)
	{
		if (!world.isRemote)
		{
			if (((this.specialNumber == 1) && (this.flaskDrinkCount != 0)) || ((this.specialNumber == 2) && (this.flaskDrinkCount != 0)) || ((this.specialNumber == 4) && (this.flaskDrinkCount != 0)) || ((this.specialNumber == 5) && (this.flaskDrinkCount != 0)))
			{
				if ((this.specialNumber == 1) || (this.specialNumber == 4))
				{
					is.setItemDamage(is.getItemDamage() + this.flaskDrinkCount);
					soulFeastingEffect(player, this.flaskDrinkCount);
				}
				if ((this.specialNumber == 2) || (this.specialNumber == 5))
				{
					is.setItemDamage(is.getItemDamage() + this.flaskDrinkCount * 2);
					bloodDrinkingEffect(player, this.flaskDrinkCount);
				}

				this.flaskDrinkCount = 0;

				if (is.getItemDamage() >= is.getMaxDamage())
				{
					if ((this.specialNumber == 1) || (this.specialNumber == 2))
					{
						ItemStack newKeeper = new ItemStack(HSItems.essenceKeeper, 1);
						ItemStack heldItem = player.inventory.getCurrentItem();
						// TODO: var10.getItem(). = HSItems.essenceKeeper;
						heldItem.setItemDamage(0);
					}

					if ((this.specialNumber == 4) || (this.specialNumber == 5))
					{
						ItemStack newKeeper = new ItemStack(HSItems.essenceVessel, 1);
						ItemStack heldItem = player.inventory.getCurrentItem();
						// TODO: var10.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
						heldItem.setItemDamage(0);
					}
				}
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			this.flaskDrinkCount += 1;

			if ((this.specialNumber == 2) || (this.specialNumber == 5))
				this.bloodDrinkingHealth(player, 1);
		}
		
		return is;
	}

	@Override
	public ItemStack getContainerItem(ItemStack is)
	{
		if ((this.specialNumber == 1) || (this.specialNumber == 2)) 
			return is = new ItemStack(HSItems.essenceKeeper);
		
		if ((this.specialNumber == 4) || (this.specialNumber == 5))
		{
			if (is.getItemDamage() >= is.getMaxDamage() / 2)
				return is = new ItemStack(HSItems.essenceVessel);
			
			is.setItemDamage(is.getItemDamage() + is.getMaxDamage() / 2);
		}
		
		return is;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if ((this.specialNumber == 2) || (this.specialNumber == 5))
		{
			if (side != 1)
			{
				return false;
			}
			
			if ((player.canPlayerEdit(x, y, z, side, is)) && (player.canPlayerEdit(x, y, z, side, is)))
			{
				Block block = world.getBlock(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);

				if ((block == HSBlocks.creep) && (meta == 1))
				{
					world.setBlockMetadataWithNotify(x, y, z, 2, 2);
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "liquid.swim", 0.05F, 0.05F);
					is.damageItem(1, player);
					ItemStack heldItem = player.inventory.getCurrentItem();

					if (heldItem.getItem() == HSItems.bloodKeeper)
					{
						if (heldItem.getMaxDamage() - heldItem.getItemDamage() == 0)
						{
							// TODO: heldItem.itemID = mod_HarkenScythe.HSEssenceKeeper.itemID;
							heldItem.setItemDamage(-heldItem.getMaxDamage());
						}
						
						return true;
					}

					if (heldItem.getItem() == HSItems.bloodVessel)
					{
						if (heldItem.getMaxDamage() - heldItem.getItemDamage() == 0)
						{
							// TODO: heldItem.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
							heldItem.setItemDamage(-heldItem.getMaxDamage());
						}
						
						return true;
					}
					
					return true;
				}
			}
		}
		
		return false;
	}

	public void soulFeastingEffect(EntityPlayer player, int amount)
	{
		int soulweaveArmorBonus = ItemSoulweaveArmor.soulweaveArmorCheck(player);
		int soulRank = 0;
		soulRank += soulweaveArmorBonus;
		
		if ((soulRank < 4) && (amount >= 5)) 
			soulRank += amount / 5;
		if (soulRank > 3) 
			soulRank = 3;

		int soulDuration = 410;
		int soulweaveArmorDurationBonus = 300 * soulweaveArmorBonus;
		soulDuration += 205 * amount + soulweaveArmorDurationBonus;
		int hungerRank = 0;
		
		if (amount >= 10) 
			hungerRank += amount / 10;
		if (hungerRank > 3) 
			hungerRank = 3;
		
		int hungerDuration = 205;
		hungerDuration += 102 * amount;
		int soulRandomEffect = soulFeastingRandomEffect(soulweaveArmorBonus);
		player.clearActivePotions();
		player.addPotionEffect(new PotionEffect(soulRandomEffect, soulDuration, soulRank));
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, hungerDuration, hungerRank));
	}

	public int soulFeastingRandomEffect(int soulweaveArmorCount)
	{
		Random diceRoller = new Random();
		Random diceRoller2 = new Random();
		int soulBuffRandom = diceRoller.nextInt(100) + 1;
		int soulEffectID = 0;

		if ((soulBuffRandom >= 50) && (soulweaveArmorCount < 4))
		{
			int soulEffectRandom = diceRoller2.nextInt(soulDebuffs.length);
			soulEffectID = soulDebuffs[soulEffectRandom];
		}
		else 
		{
			int soulEffectRandom = diceRoller2.nextInt(soulBuffs.length);
			soulEffectID = soulBuffs[soulEffectRandom];
		}

		return soulEffectID;
	}

	public void bloodDrinkingEffect(EntityPlayer player, int amount)
	{
		int bloodweaveArmorBonus = ItemBloodweaveArmor.bloodweaveArmorCheck(player);
		int bloodRank = 0;
		bloodRank += bloodweaveArmorBonus;
		
		if ((bloodRank < 4) && (amount >= 5)) 
			bloodRank += amount / 5;
		if (bloodRank > 3) 
			bloodRank = 3;

		int bloodDuration = 103;
		int bloodweaveArmorDurationBonus = 100 * bloodweaveArmorBonus;
		bloodDuration += 103 * amount + bloodweaveArmorDurationBonus;
		int hungerRank = 0;
		
		if (amount >= 10) 
			hungerRank += amount / 10;
		if (hungerRank > 3) 
			hungerRank = 3;
		
		int hungerDuration = 43;
		hungerDuration += 43 * amount;
		player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, bloodDuration, bloodRank));
		
		if (bloodweaveArmorBonus >= 4) 
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, bloodDuration, 0));

		player.addPotionEffect(new PotionEffect(Potion.hunger.id, hungerDuration, hungerRank));
	}

	public void bloodDrinkingHealth(EntityPlayer player, int amount)
	{
		float healedAmount = player.getMaxHealth() - player.getHealth();
		int bloodweaveArmorHealthBonus = 0;

		if (healedAmount > 0)
		{
			if (healedAmount >= amount + bloodweaveArmorHealthBonus) 
				player.heal(amount + bloodweaveArmorHealthBonus);
			else
				player.heal(healedAmount);
		}
	}

	public static boolean soulkeeperFillCheck(EntityPlayer player, int fillAmount)
	{
		if (fillAmount == 0) 
			return false;
	
		if ((player.inventory.hasItem(HSItems.soulKeeper)) || (player.inventory.hasItem(HSItems.essenceKeeper)) || (player.inventory.hasItem(HSItems.soulVessel)) || (player.inventory.hasItem(HSItems.essenceVessel)))
		{
			int fillPool = fillAmount;
			ItemStack heldItem = player.inventory.getCurrentItem();

			if (heldItem.getItem() == HSItems.soulVessel)
			{
				int fillingRoom = heldItem.getItemDamage();
				
				if (fillingRoom >= fillPool)
				{
					heldItem.damageItem(-fillPool, player);
					return true;
				}

				fillPool -= fillingRoom;
				heldItem.damageItem(-fillingRoom, player);
			}

			if (heldItem.getItem() == HSItems.essenceVessel)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(HSItems.soulVessel, 1);
				ItemStack myNewSoulkeeper = player.inventory.getCurrentItem();
				int fillingRoom = myNewSoulkeeper.getMaxDamage();
				
				if (fillingRoom >= fillPool)
				{
					myNewSoulkeeper.damageItem(fillingRoom - fillPool, player);
					return true;
				}

				fillPool -= fillingRoom;
				myNewSoulkeeper.damageItem(fillPool - fillingRoom, player);
			}

			if (heldItem.getItem() == HSItems.soulKeeper)
			{
				int fillingRoom = heldItem.getItemDamage();
				if (fillingRoom >= fillPool)
				{
					heldItem.damageItem(-fillPool, player);
					return true;
				}

				fillPool -= fillingRoom;
				heldItem.damageItem(-fillingRoom, player);
			}

			if (heldItem.getItem() == HSItems.essenceKeeper)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(HSItems.soulKeeper, 1);
				ItemStack myNewSoulkeeper = player.inventory.getCurrentItem();
				int fillingRoom = myNewSoulkeeper.getMaxDamage();
				
				if (fillingRoom >= fillPool)
				{
					myNewSoulkeeper.damageItem(fillingRoom - fillPool, player);
					return true;
				}

				fillPool -= fillingRoom;
				myNewSoulkeeper.damageItem(fillPool - fillingRoom, player);
			}

			for (int invSize = 0; invSize < player.inventory.mainInventory.length; invSize++)
			{
				if (player.inventory.mainInventory[invSize] != null)
				{
					if ("item.HSSoulVessel".equals(player.inventory.mainInventory[invSize].getItem().getUnlocalizedName())) // TODO: UHHHUHUHUHUHUH
					{
						int fillingRoom = player.inventory.mainInventory[invSize].getItemDamage();
						
						if (fillingRoom >= fillPool)
						{
							player.inventory.mainInventory[invSize].damageItem(-fillPool, player);
							return true;
						}

						fillPool -= fillingRoom;
						player.inventory.mainInventory[invSize].damageItem(-fillingRoom, player);
					}
				}
			}

			for (int j = 0; j < player.inventory.mainInventory.length; j++)
			{
				if (player.inventory.mainInventory[j] != null)
				{
					if ("item.HSEssenceVessel".equals(player.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						player.inventory.mainInventory[j] = new ItemStack(HSItems.soulVessel, 1);
						int fillingRoom = player.inventory.mainInventory[j].getMaxDamage();
						
						if (fillingRoom >= fillPool)
						{
							player.inventory.mainInventory[j].damageItem(fillingRoom - fillPool, player);
							return true;
						}

						fillPool -= fillingRoom;
						player.inventory.mainInventory[j].damageItem(fillPool - fillingRoom, player);
					}
				}
			}

			for (int j = 0; j < player.inventory.mainInventory.length; j++)
			{
				if (player.inventory.mainInventory[j] != null)
				{
					if ("item.HSSoulkeeper".equals(player.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						int fillingRoom = player.inventory.mainInventory[j].getItemDamage();
					
						if (fillingRoom >= fillPool)
						{
							player.inventory.mainInventory[j].damageItem(-fillPool, player);
							return true;
						}

						fillPool -= fillingRoom;
						player.inventory.mainInventory[j].damageItem(-fillingRoom, player);
					}
				}

			}

			for (int j = 0; j < player.inventory.mainInventory.length; j++)
			{
				if (player.inventory.mainInventory[j] != null)
				{
					if ("item.HSEssenceKeeper".equals(player.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						player.inventory.mainInventory[j] = new ItemStack(HSItems.soulKeeper, 1);
						int fillingRoom = player.inventory.mainInventory[j].getMaxDamage();
						
						if (fillingRoom >= fillPool)
						{
							player.inventory.mainInventory[j].damageItem(fillingRoom - fillPool, player);
							return true;
						}

						fillPool -= fillingRoom;
						player.inventory.mainInventory[j].damageItem(fillPool - fillingRoom, player);
					}
				}
			}
		}

		return false;
	}

	public static boolean bloodkeeperFillCheck(EntityPlayer entityplayer, int fillAmount)
	{
		if (fillAmount == 0) 
			return false;
		
		if ((entityplayer.inventory.hasItem(HSItems.bloodKeeper)) || (entityplayer.inventory.hasItem(HSItems.essenceKeeper)) || (entityplayer.inventory.hasItem(HSItems.bloodVessel)) || (entityplayer.inventory.hasItem(HSItems.essenceVessel)))
		{
			int fillPool = fillAmount;
			ItemStack myCurrentItem = entityplayer.inventory.getCurrentItem();

			if (myCurrentItem.getItem() == HSItems.bloodVessel)
			{
				int fillingRoom = myCurrentItem.getItemDamage();
				if (fillingRoom >= fillPool)
				{
					myCurrentItem.damageItem(-fillPool, entityplayer);
					return true;
				}

				fillPool -= fillingRoom;
				myCurrentItem.damageItem(-fillingRoom, entityplayer);
			}

			if (myCurrentItem.getItem() == HSItems.essenceVessel)
			{
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = new ItemStack(HSItems.bloodVessel, 1);
				ItemStack myNewBloodkeeper = entityplayer.inventory.getCurrentItem();

				int fillingRoom = myNewBloodkeeper.getMaxDamage();
				if (fillingRoom >= fillPool)
				{
					myNewBloodkeeper.damageItem(fillingRoom - fillPool, entityplayer);
					return true;
				}

				fillPool -= fillingRoom;
				myNewBloodkeeper.damageItem(fillPool - fillingRoom, entityplayer);
			}

			if (myCurrentItem.getItem() == HSItems.bloodKeeper)
			{
				int fillingRoom = myCurrentItem.getItemDamage();
				if (fillingRoom >= fillPool)
				{
					myCurrentItem.damageItem(-fillPool, entityplayer);
					return true;
				}

				fillPool -= fillingRoom;
				myCurrentItem.damageItem(-fillingRoom, entityplayer);
			}

			if (myCurrentItem.getItem() == HSItems.essenceKeeper)
			{
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = new ItemStack(HSItems.bloodKeeper, 1);
				ItemStack myNewBloodkeeper = entityplayer.inventory.getCurrentItem();

				int fillingRoom = myNewBloodkeeper.getMaxDamage();
				if (fillingRoom >= fillPool)
				{
					myNewBloodkeeper.damageItem(fillingRoom - fillPool, entityplayer);
					return true;
				}

				fillPool -= fillingRoom;
				myNewBloodkeeper.damageItem(fillPool - fillingRoom, entityplayer);
			}

			for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
			{
				if (entityplayer.inventory.mainInventory[j] != null)
				{
					if ("item.HSBloodVessel".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						int fillingRoom = entityplayer.inventory.mainInventory[j].getItemDamage();
						if (fillingRoom >= fillPool)
						{
							entityplayer.inventory.mainInventory[j].damageItem(-fillPool, entityplayer);
							return true;
						}

						fillPool -= fillingRoom;
						entityplayer.inventory.mainInventory[j].damageItem(-fillingRoom, entityplayer);
					}

				}

			}

			for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
			{
				if (entityplayer.inventory.mainInventory[j] != null)
				{
					if ("item.HSEssenceVessel".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						entityplayer.inventory.mainInventory[j] = new ItemStack(HSItems.bloodVessel, 1);
						int fillingRoom = entityplayer.inventory.mainInventory[j].getMaxDamage();
						
						if (fillingRoom >= fillPool)
						{
							entityplayer.inventory.mainInventory[j].damageItem(fillingRoom - fillPool, entityplayer);
							return true;
						}

						fillPool -= fillingRoom;
						entityplayer.inventory.mainInventory[j].damageItem(fillPool - fillingRoom, entityplayer);
					}
				}
			}

			for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
			{
				if (entityplayer.inventory.mainInventory[j] != null)
				{
					if ("item.HSBloodkeeper".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						int fillingRoom = entityplayer.inventory.mainInventory[j].getItemDamage();
						if (fillingRoom >= fillPool)
						{
							entityplayer.inventory.mainInventory[j].damageItem(-fillPool, entityplayer);
							return true;
						}

						fillPool -= fillingRoom;
						entityplayer.inventory.mainInventory[j].damageItem(-fillingRoom, entityplayer);
					}

				}

			}

			for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
			{
				if (entityplayer.inventory.mainInventory[j] != null)
				{
					if ("item.HSEssenceKeeper".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
					{
						entityplayer.inventory.mainInventory[j] = new ItemStack(HSItems.bloodKeeper, 1);
						int fillingRoom = entityplayer.inventory.mainInventory[j].getMaxDamage();
						
						if (fillingRoom >= fillPool)
						{
							entityplayer.inventory.mainInventory[j].damageItem(fillingRoom - fillPool, entityplayer);
							return true;
						}

						fillPool -= fillingRoom;
						entityplayer.inventory.mainInventory[j].damageItem(fillPool - fillingRoom, entityplayer);
					}
				}
			}
		}

		return false;
	}

	public static boolean soulkeeperCheck(EntityPlayer entityplayer, int reqAmount, boolean isTalisman)
	{
		if (reqAmount == 0) return true;

		if ((entityplayer.inventory.hasItem(HSItems.soulKeeper)) || (entityplayer.inventory.hasItem(HSItems.soulVessel)))
		{
			if ((entityplayer.inventory.hasItem(HSItems.soulKeeper)) && (isTalisman == true))
			{
				for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
				{
					if (entityplayer.inventory.mainInventory[j] != null)
					{
						if ("item.HSSoulkeeper".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
						{
							int soulsLeft = entityplayer.inventory.mainInventory[j].getMaxDamage() - entityplayer.inventory.mainInventory[j].getItemDamage();
							if (soulsLeft >= reqAmount)
							{
								return true;
							}
						}
					}
				}

			}

			if (entityplayer.inventory.hasItem(HSItems.soulVessel))
			{
				for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
				{
					if (entityplayer.inventory.mainInventory[j] != null)
					{
						if ("item.HSSoulVessel".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
						{
							int soulsLeft = entityplayer.inventory.mainInventory[j].getMaxDamage() - entityplayer.inventory.mainInventory[j].getItemDamage();
							if (soulsLeft >= reqAmount)
							{
								return true;
							}
						}
					}
				}

			}

			if ((entityplayer.inventory.hasItem(HSItems.soulKeeper)) && (!isTalisman))
			{
				for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
				{
					if (entityplayer.inventory.mainInventory[j] != null)
					{
						if ("item.HSSoulkeeper".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
						{
							int soulsLeft = entityplayer.inventory.mainInventory[j].getMaxDamage() - entityplayer.inventory.mainInventory[j].getItemDamage();
							if (soulsLeft >= reqAmount)
							{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean bloodkeeperCheck(EntityPlayer entityplayer, int reqAmount, boolean isTalisman)
	{
		if (reqAmount == 0) return true;

		if ((entityplayer.inventory.hasItem(HSItems.bloodKeeper)) || (entityplayer.inventory.hasItem(HSItems.bloodVessel)))
		{
			if ((entityplayer.inventory.hasItem(HSItems.bloodKeeper)) && (isTalisman == true))
			{
				for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
				{
					if (entityplayer.inventory.mainInventory[j] != null)
					{
						if ("item.HSBloodkeeper".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
						{
							int bloodLeft = entityplayer.inventory.mainInventory[j].getMaxDamage() - entityplayer.inventory.mainInventory[j].getItemDamage();
							if (bloodLeft >= reqAmount)
							{
								return true;
							}
						}
					}
				}

			}

			if (entityplayer.inventory.hasItem(HSItems.bloodVessel))
			{
				for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
				{
					if (entityplayer.inventory.mainInventory[j] != null)
					{
						if ("item.HSBloodVessel".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
						{
							int bloodLeft = entityplayer.inventory.mainInventory[j].getMaxDamage() - entityplayer.inventory.mainInventory[j].getItemDamage();
							if (bloodLeft >= reqAmount)
							{
								return true;
							}
						}
					}
				}

			}

			if ((entityplayer.inventory.hasItem(HSItems.bloodKeeper)) && (!isTalisman))
			{
				for (int j = 0; j < entityplayer.inventory.mainInventory.length; j++)
				{
					if (entityplayer.inventory.mainInventory[j] != null)
					{
						if ("item.HSBloodkeeper".equals(entityplayer.inventory.mainInventory[j].getItem().getUnlocalizedName()))
						{
							int bloodLeft = entityplayer.inventory.mainInventory[j].getMaxDamage() - entityplayer.inventory.mainInventory[j].getItemDamage();
							if (bloodLeft >= reqAmount)
							{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	static
	{
		soulBuffs[0] = 1;
		soulBuffs[1] = 3;
		soulBuffs[2] = 5;
		soulBuffs[3] = 6;
		soulBuffs[4] = 8;
		soulBuffs[5] = 10;
		soulBuffs[6] = 11;
		soulBuffs[7] = 12;
		soulBuffs[8] = 13;
		soulBuffs[9] = 14;
		soulBuffs[10] = 16;
		
		soulDebuffs = new int[8];
		soulDebuffs[0] = 2;
		soulDebuffs[1] = 4;
		soulDebuffs[2] = 7;
		soulDebuffs[3] = 9;
		soulDebuffs[4] = 15;
		soulDebuffs[5] = 18;
		soulDebuffs[6] = 19;
		soulDebuffs[7] = 20;
	}
}
