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
 * File created @[Mar 5, 2015, 10:25:10 AM]
 */
package com.spawck.hs2.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemAmulet extends ItemHS
{
	public ChunkCoordinates timeToSpawn1;
	public ChunkCoordinates timeToSpawn2;
	
	private int specialNumber; // is this a certain item
	private int counter;
	private int slow;
	private int soulKeeperCost;
	
	Side side = FMLCommonHandler.instance().getEffectiveSide();

	public ItemAmulet(int num)
	{
		this.setMaxDamage(22);
		this.specialNumber = num;
		this.counter = 0;
		this.slow = 0;
		this.soulKeeperCost = 5;
		
		if (num == 2) 
			this.soulKeeperCost = 10;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack is)
	{
		if (is.isItemEnchanted()) 
			return EnumRarity.epic;
		
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) // TODO: Add shifting functionality
	{
		int stored = is.getMaxDamage() - is.getItemDamage();
		String pural = "";
		
		if (stored > 1) 
			pural = "s";
		
		if (is.getItemDamage() == 22)
		{
			list.add("Teleport back to resting place");
			list.add("Cost: 10 Souls");
			list.add("Soul Attuned");
		} 
		else if (is.getItemDamage() == 23)
		{
			list.add("Teleport back to resting place");
			list.add("Cost: 20 Blood");
			list.add("Blood Attuned");
		}/* TODO: Re-add
		else if (EnchantmentHelper.getEnchantmentLevel(HarkenScythe2.HSSoulAttunedAug.effectId, is) > 0)
		{
			list.add("Teleport back to resting place");
			list.add("Cost: 10 Souls");
		}
		else if (EnchantmentHelper.getEnchantmentLevel(HarkenScythe2.HSBloodAttunedAug.effectId, is) > 0)
		{
			list.add("Teleport back to resting place");
			list.add("Cost: 20 Blood");
		}*/
		else 
		{
			list.add("Teleport back to resting place");
			list.add("Cost: 5 Souls and 10 Blood");
		}
	}

	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer player)
	{
		int startDamage = is.getItemDamage();
		
		/* TODO: Re-add
		if (startDamage == 22)
		{
			is.addEnchantment(HarkenScythe2.HSSoulAttunedAug, 1);
			is.setItemDamage(0);
		}
		if (startDamage == 23)
		{
			is.addEnchantment(HarkenScythe2.HSBloodAttunedAug, 1);
			is.setItemDamage(0);
		}*/
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 20;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.none;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		int[] amuletData = amuletCostCheck(is, player);
		int reqMeet = amuletData[0];
		int soulCost = amuletData[1];
		int bloodCost = amuletData[2];

		if (reqMeet == 1)
		{
			if (player.capabilities.isCreativeMode != true)
			{
				boolean go = false;
				this.slow += 1;

				player.setItemInUse(is, getMaxItemUseDuration(is));

				int itemDamage = is.getItemDamage();

				if ((itemDamage >= 2) && (itemDamage < 10))
				{
					world.spawnParticle("depthsuspend", player.posX + 0.9D, player.posY - 1.6D, player.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("depthsuspend", player.posX + 0.8D, player.posY - 1.6D, player.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("depthsuspend", player.posX + 0.7D, player.posY - 1.6D, player.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
					player.worldObj.playSoundAtEntity(player, "random.breath", 1.0F, 1.0F);
				}

				if ((itemDamage >= 4) && (itemDamage < 10))
				{
					world.spawnParticle("flame", player.posX - 0.9D, player.posY - 1.6D, player.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("flame", player.posX - 0.7D, player.posY - 1.6D, player.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("flame", player.posX - 0.8D, player.posY - 1.6D, player.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
				}

				if ((itemDamage >= 6) && (itemDamage < 10))
				{
					world.spawnParticle("spell", player.posX - 0.9D, player.posY - 1.6D, player.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("spell", player.posX - 0.7D, player.posY - 1.6D, player.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("spell", player.posX - 0.8D, player.posY - 1.6D, player.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
				}

				if ((itemDamage >= 8) && (itemDamage < 10))
				{
					world.spawnParticle("splash", player.posX + 0.9D, player.posY - 1.6D, player.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("splash", player.posX + 0.8D, player.posY - 1.6D, player.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("splash", player.posX + 0.7D, player.posY - 1.6D, player.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
				}

				if ((itemDamage == 10) || (itemDamage == 14) || (itemDamage == 18) || (itemDamage == 20))
				{
					this.amuletEffectText(world, player);
				}

				if ((itemDamage == 12) || (itemDamage == 16) || (itemDamage == 18) || (itemDamage == 20))
				{
					this.amuletEffectText2(world, player);
				}

				if (itemDamage >= 10)
				{
					this.amuletEffectElements(world, player);
				}

				if (itemDamage >= 16)
				{
					this.amuletEffect(world, player);
				}

				if (this.side == Side.CLIENT)
				{
					Minecraft mc = FMLClientHandler.instance().getClient();
					
					if (mc.isIntegratedServerRunning() == true)
					{
						if (this.slow == (this.counter + 1) * 2)
						{
							go = true;
						}
					}
				}
				else if (this.slow == (this.counter + 1) * 1)
				{
					go = true;
				}

				if (go == true)
				{
					this.counter += 1;
					this.counter += 1;

					player.getCurrentEquippedItem().damageItem(2, player);
					is.setItemDamage(this.counter);

					if (itemDamage >= 20) 
					{
						this.onPlayerStoppedUsing(is, world, player, itemDamage); return is;
					}
					
					itemDamage = is.getItemDamage();

					if ((itemDamage >= 10) && (itemDamage < 12))
					{
						world.playSoundAtEntity(player, "portal.portal", 1.0F, 1.0F);
					}
					
					return is;
				}
				
				return is;
			}

			if (player.capabilities.isCreativeMode == true)
			{
				this.counter += 1;

				is.setItemDamage(this.counter);
				int itemDamage = is.getItemDamage();

				if (itemDamage >= 20)
				{
					this.onPlayerStoppedUsing(is, world, player, itemDamage); return is;
				}
			
				player.setItemInUse(is, getMaxItemUseDuration(is));

				if ((itemDamage >= 1) && (itemDamage < 10))
				{
					world.spawnParticle("depthsuspend", player.posX + 0.9D, player.posY - 1.6D, player.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("depthsuspend", player.posX + 0.8D, player.posY - 1.6D, player.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("depthsuspend", player.posX + 0.7D, player.posY - 1.6D, player.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
					player.worldObj.playSoundAtEntity(player, "random.breath", 1.0F, 1.0F);
				}

				if ((itemDamage >= 3) && (itemDamage < 10))
				{
					world.spawnParticle("flame", player.posX - 0.9D, player.posY - 1.6D, player.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("flame", player.posX - 0.7D, player.posY - 1.6D, player.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("flame", player.posX - 0.8D, player.posY - 1.6D, player.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
				}

				if ((itemDamage >= 5) && (itemDamage < 10))
				{
					world.spawnParticle("spell", player.posX - 0.9D, player.posY - 1.6D, player.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("spell", player.posX - 0.7D, player.posY - 1.6D, player.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("spell", player.posX - 0.8D, player.posY - 1.6D, player.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
				}

				if ((itemDamage >= 7) && (itemDamage < 10))
				{
					world.spawnParticle("splash", player.posX + 0.9D, player.posY - 1.6D, player.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("splash", player.posX + 0.8D, player.posY - 1.6D, player.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("splash", player.posX + 0.7D, player.posY - 1.6D, player.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
				}

				if ((itemDamage == 7) || (itemDamage == 11) || (itemDamage == 15) || (itemDamage == 17) || (itemDamage == 19))
				{
					this.amuletEffectText(world, player);
				}

				if ((itemDamage == 9) || (itemDamage == 13) || (itemDamage == 15) || (itemDamage == 17) || (itemDamage == 19))
				{
					this.amuletEffectText2(world, player);
				}

				if (this.side == Side.CLIENT)
				{
					Minecraft mc = FMLClientHandler.instance().getClient();
					
					if (mc.isIntegratedServerRunning() == true)
					{
						this.counter -= 1;
					}
				}

				this.counter += 1;
				is.setItemDamage(this.counter);
				itemDamage = is.getItemDamage();

				if ((itemDamage >= 10) && (itemDamage < 12))
				{
					world.playSoundAtEntity(player, "portal.portal", 1.0F, 1.0F);
				}

				if (itemDamage >= 10)
				{
					this.amuletEffectElements(world, player);
				}

				if (itemDamage >= 15)
				{
					this.amuletEffect(world, player);
				}

				return is;
			}
		}
		
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int itemInUseCount)
	{
		int[] amuletData = amuletCostCheck(is, player);
		int reqMeet = amuletData[0];
		int soulCost = amuletData[1];
		int bloodCost = amuletData[2];

		float counterf = this.counter;

		if (counterf >= 19.0F)
		{
			ChunkCoordinates timeToSpawn1 = player.getBedLocation(player.dimension); // TODO: Don't know if this works. Replaced getBedLoation()
			ChunkCoordinates timeToSpawn2 = player.playerLocation;

			if (timeToSpawn1 != null) // TODO: Uhhhh...packets, please
			{
				if ((player.dimension != 0) && (player.dimension != 1) && (this.specialNumber == 2) && (reqMeet == 1))
				{
					this.amuletActivate(soulCost, bloodCost, player);
					((EntityPlayerMP)player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)player, 0);
					player.setPositionAndUpdate(timeToSpawn1.posX + 0.5D, timeToSpawn1.posY + 2, timeToSpawn1.posZ + 0.5D);
					world.playSoundAtEntity(player, "portal.travel", 0.2F, 1.0F);
					this.amuletEffect(world, player);
					this.counter = 0;
					this.slow = 0;
					is.setItemDamage(0);
					return;
				}

				if ((player.dimension == -1) && (player.dimension != 0) && (this.specialNumber == 1) && (reqMeet == 1))
				{
					this.amuletActivate(soulCost, bloodCost, player);
					((EntityPlayerMP)player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)player, 0);
					player.setPositionAndUpdate(timeToSpawn1.posX + 0.5D, timeToSpawn1.posY + 2, timeToSpawn1.posZ + 0.5D);
					world.playSoundAtEntity(player, "portal.travel", 0.2F, 1.0F);
					this.amuletEffect(world, player);
					this.counter = 0;
					this.slow = 0;
					is.setItemDamage(0);
					return;
				}

				if ((world.getBlock(timeToSpawn1.posX, timeToSpawn1.posY, timeToSpawn1.posZ) == Blocks.bed) && ((this.specialNumber == 0) || (this.specialNumber == 2)) && (reqMeet == 1))
				{
					this.amuletActivate(soulCost, bloodCost, player);
					player.setPositionAndUpdate(timeToSpawn1.posX + 0.5D, timeToSpawn1.posY + 2, timeToSpawn1.posZ + 0.5D);
					world.playSoundAtEntity(player, "portal.travel", 0.2F, 1.0F);
					this.amuletEffect(world, player);
					this.counter = 0;
					this.slow = 0;
					is.setItemDamage(0);
					return;
				}
			}
		}
		
		this.counter = 0;
		this.slow = 0;
		is.setItemDamage(0);
		player.worldObj.playSoundAtEntity(player, "random.fizz", 1.0F, 1.0F);
	}

	private static int[] amuletCostCheck(ItemStack is, EntityPlayer player)
	{
		int[] amuletItem = new int[3];
		amuletItem[0] = 0;
		amuletItem[1] = 0;
		amuletItem[2] = 0;

		int soulCost = 5;
		int bloodCost = 10;

		/* TODO
		if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, is) > 0)
		{
			soulCost = 10;
			bloodCost = 0;
		}
		if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, is) > 0)
		{
			soulCost = 0;
			bloodCost = 20;
		}

		if ((ItemHSKeeper.soulkeeperCheck(player, soulCost, false) == true) && (ItemHSKeeper.bloodkeeperCheck(player, bloodCost, false) == true))
		{
			amuletItem[0] = 1;
			amuletItem[1] = soulCost;
			amuletItem[2] = bloodCost;
			return amuletItem;
		}*/
		
		return amuletItem;
	}

	private void amuletActivate(int soulCost, int bloodCost, EntityPlayer player)
	{
		if (soulCost > 0)
		{
			boolean soulkeeperUsed = false;
			//boolean bloodkeeperUsed = false;

			for (int inventorySize = 0; inventorySize < player.inventory.mainInventory.length; inventorySize++)
			{
				if (player.inventory.mainInventory[inventorySize] != null)
				{
					if ("item.HSSoulkeeper".equals(player.inventory.mainInventory[inventorySize].getItem().getUnlocalizedName())) // TODO: Double-check this
					{
						int soulsLeft = player.inventory.mainInventory[inventorySize].getMaxDamage() - player.inventory.mainInventory[inventorySize].getItemDamage();
						
						if (soulsLeft >= soulCost)
						{
							player.inventory.mainInventory[inventorySize].damageItem(soulCost, player);
							soulkeeperUsed = true;

							if (player.inventory.mainInventory[inventorySize].getItemDamage() < player.inventory.mainInventory[inventorySize].getMaxDamage())
								break;
							
							// TODO: player.inventory.mainInventory[inventorySize] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1); break;
						}
					}
				}
			}

			if (!soulkeeperUsed)
			{
				for (int inventorySize = 0; inventorySize < player.inventory.mainInventory.length; inventorySize++)
				{
					if (player.inventory.mainInventory[inventorySize] != null)
					{
						//boolean bloodkeeperUsed;
						//int inventorySize; TODO: What's goin' on here?
						int jk;
						
						if ("item.HSSoulVessel".equals(player.inventory.mainInventory[inventorySize].getItem().getUnlocalizedName()))
						{
							int soulsLeft = player.inventory.mainInventory[inventorySize].getMaxDamage() - player.inventory.mainInventory[inventorySize].getItemDamage();
							if (soulsLeft >= soulCost)
							{
								player.inventory.mainInventory[inventorySize].damageItem(soulCost, player);

								if (player.inventory.mainInventory[inventorySize].getItemDamage() < player.inventory.mainInventory[inventorySize].getMaxDamage())
									break;
								
								// TODO: player.inventory.mainInventory[jk] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1); break;
							}
						}
					}
				}
			}
		}

		if (bloodCost > 0)
		{
			boolean bloodkeeperUsed = false;

			for (int jk = 0; jk < player.inventory.mainInventory.length; jk++)
			{
				if (player.inventory.mainInventory[jk] != null)
				{
					if ("item.HSBloodkeeper".equals(player.inventory.mainInventory[jk].getItem().getUnlocalizedName()))
					{
						int soulsLeft = player.inventory.mainInventory[jk].getMaxDamage() - player.inventory.mainInventory[jk].getItemDamage();
						if (soulsLeft >= bloodCost)
						{
							player.inventory.mainInventory[jk].damageItem(bloodCost, player);
							bloodkeeperUsed = true;

							if (player.inventory.mainInventory[jk].getItemDamage() < player.inventory.mainInventory[jk].getMaxDamage())
								break;
							
							// TODO: player.inventory.mainInventory[jk] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1); break;
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
								
								// TODO: player.inventory.mainInventory[jk] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1); break;
							}
						}
					}
				}
			}
		}
	}

	// TODO: Maybe make a particle util class?
	private void amuletEffectText(World world, EntityPlayer player)
	{
		world.spawnParticle("enchantmenttable", player.posX, player.posY, player.posZ + 0.4D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", player.posX + 0.4D, player.posY, player.posZ, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", player.posX, player.posY, player.posZ - 0.4D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", player.posX - 0.4D, player.posY, player.posZ, 0.0D, 0.0D, 0.0D);
	}

	private void amuletEffectText2(World world, EntityPlayer player)
	{
		world.spawnParticle("enchantmenttable", player.posX + 0.2D, player.posY + 0.2D, player.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", player.posX + 0.2D, player.posY + 0.2D, player.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", player.posX - 0.2D, player.posY + 0.2D, player.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", player.posX - 0.2D, player.posY + 0.2D, player.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
	}

	private void amuletEffectElements(World world, EntityPlayer player)
	{
		world.spawnParticle("depthsuspend", player.posX + 0.9D, player.posY - 1.6D, player.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("splash", player.posX + 0.9D, player.posY - 1.6D, player.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("spell", player.posX - 0.9D, player.posY - 1.6D, player.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", player.posX - 0.9D, player.posY - 1.6D, player.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("depthsuspend", player.posX + 0.8D, player.posY - 1.6D, player.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("splash", player.posX + 0.8D, player.posY - 1.6D, player.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("spell", player.posX - 0.7D, player.posY - 1.6D, player.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", player.posX - 0.7D, player.posY - 1.6D, player.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("depthsuspend", player.posX + 0.7D, player.posY - 1.6D, player.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("splash", player.posX + 0.7D, player.posY - 1.6D, player.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("spell", player.posX - 0.8D, player.posY - 1.6D, player.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", player.posX - 0.8D, player.posY - 1.6D, player.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
	}

	private void amuletEffect(World world, EntityPlayer player)
	{
		world.spawnParticle("portal", player.posX + 0.3D, player.posY - 0.3D, player.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX + 0.3D, player.posY - 0.3D, player.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX - 0.3D, player.posY - 0.3D, player.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX - 0.3D, player.posY - 0.3D, player.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX + 0.3D, player.posY - 0.9D, player.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX + 0.3D, player.posY - 0.9D, player.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX - 0.3D, player.posY - 0.9D, player.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("portal", player.posX - 0.3D, player.posY - 0.9D, player.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
	}
}
