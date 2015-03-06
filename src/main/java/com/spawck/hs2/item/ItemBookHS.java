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
 * File created @[Mar 5, 2015, 4:05:47 PM]
 */
package com.spawck.hs2.item;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.spawck.hs2.entity.EntitySoul;
import com.spawck.hs2.entity.EntitySoulLost;
import com.spawck.hs2.init.HSItems;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemBookHS extends ItemHS
{
	private Icon iconReady;
	private Icon iconNormal;
	private int specialNumber;
	private int counter;
	private int slow;
	Side side = FMLCommonHandler.instance().getEffectiveSide();

	public ItemBookHS(int j)
	{
		this.specialNumber = j;
		this.counter = 0;
		this.slow = 0;

		if (j == 2) this.setMaxDamage(32);
		if (j == 3) this.maxStackSize = 5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack is)
	{
		if (this.specialNumber == 3) 
			return false;
		
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack is)
	{
		return EnumRarity.epic;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
	{
		if (this.specialNumber == 0)
		{
			list.add("A book containing religious texts");
			list.add("and instructions for magical rituals");
		}
		if (this.specialNumber == 1)
		{
			list.add("A book containing religious texts");
			list.add("and instructions for sacrificial rituals");
		}
		if (this.specialNumber == 2)
		{
			list.add("The Book of the Dead: Bound in human");
			list.add("flesh and inked in blood, this ancient");
			list.add("Samarian text contained bizarre");
			list.add("burial rights, funeral incantations,");
			list.add("and demon resurrection passages.");
		}
		if (this.specialNumber == 3)
		{
			list.add("A Missing page of the Necronomicon");
			list.add("writen in ancient Samarian text.");
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 20;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		if (this.specialNumber == 2)
		{
			return EnumAction.block;
		}
		
		return EnumAction.none;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if (this.specialNumber == 2)
		{
			int[] necronomiconData = necronomiconCostCheck(is, player);
			int reqMeet = necronomiconData[0];
			int bloodCost = necronomiconData[1];

			if (reqMeet == 1)
			{
				if (player.capabilities.isCreativeMode != true)
				{
					boolean go = false;
					this.slow += 1;

					player.setItemInUse(is, getMaxItemUseDuration(is));

					int i = is.getItemDamage();

					if (i == 4)
					{
						world.playSoundAtEntity(player, "mob.endermen.stare", 5.0F, 1.0F);
					}

					if ((i == 2) || (i == 6) || (i == 10) || (i == 14) || (i == 18) || (i == 22) || (i == 26) || (i == 30))
					{
						amuletEffectText(world, player);
					}

					if ((i == 4) || (i == 8) || (i == 12) || (i == 16) || (i == 20) || (i == 24) || (i == 28) || (i == 30))
					{
						amuletEffectText2(world, player);
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

						if (i >= 30) { onPlayerStoppedUsing(is, world, player, i); return is; }
						return is;
					}
					return is;
				}

				if (player.capabilities.isCreativeMode == true)
				{
					this.counter += 1;

					is.setItemDamage(this.counter);
					int i = is.getItemDamage();

					if (i >= 30) { onPlayerStoppedUsing(is, world, player, i); return is;
					}
					player.setItemInUse(is, getMaxItemUseDuration(is));

					if ((i == 4) || (i == 5))
					{
						world.playSoundAtEntity(player, "mob.endermen.stare", 5.0F, 1.0F);
					}

					if ((i == 3) || (i == 7) || (i == 11) || (i == 15) || (i == 19) || (i == 23) || (i == 27) || (i == 29))
					{
						amuletEffectText(world, player);
					}

					if ((i == 5) || (i == 9) || (i == 13) || (i == 17) || (i == 17) || (i == 21) || (i == 25) || (i == 29))
					{
						amuletEffectText2(world, player);
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
					return is;
				}
			}
		}
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int counter)
	{
		if (this.specialNumber == 2)
		{
			int playFailSound = 0;

			float l = counter;

			if (l >= 29.0F)
			{
				for (int i1 = 0; i1 < world.loadedEntityList.size(); i1++)
				{
					int[] necronomiconData = necronomiconCostCheck(is, player);
					int reqMeet = necronomiconData[0];
					int bloodCost = necronomiconData[1];

					Entity entity = player;
					if (this.side == Side.CLIENT)
					{
						entity = (Entity)world.getLoadedEntityList().get(i1);
					}
					if (this.side == Side.SERVER)
					{
						entity = (Entity)world.loadedEntityList.get(i1);
					}

					if (((entity instanceof EntitySoul)) && (player.getDistanceSqToEntity(entity) < 5.0D) && (reqMeet == 1))
					{
						EntitySoul newSoul = (EntitySoul)entity;

						if (EntitySoul.createSpectralPet(newSoul.fallenSoulName, world, entity.posX, entity.posY, entity.posZ, Item.itemRand, Boolean.valueOf(false)) == true)
						{
							necronomiconActivate(bloodCost, player);
							playFailSound++;
							player.worldObj.playSoundAtEntity(player, "mob.zombie.unfect", 1.0F, 1.0F);
							entity.setDead();
						}

					}

					if (((entity instanceof EntitySoulLost)) && (player.getDistanceSqToEntity(entity) < 5.0D) && (reqMeet == 1))
					{
						EntitySoulLost newSoul = (EntitySoulLost)entity;

						if (EntitySoulLost.createSpectralPet(world, entity.posX, entity.posY, entity.posZ, Item.itemRand) == true)
						{
							necronomiconActivate(bloodCost, player);
							playFailSound++;
							player.worldObj.playSoundAtEntity(player, "mob.zombie.unfect", 1.0F, 1.0F);
							entity.setDead();
						}
					}
				}
			}

			this.counter = 0;
			this.slow = 0;
			is.setItemDamage(0);
			if (playFailSound == 0) player.worldObj.playSoundAtEntity(player, "mob.zombie.infect", 1.0F, 1.0F);
			return;
		}
	}

	private void amuletEffectText(World world, EntityPlayer entityplayer)
	{
		world.spawnParticle("enchantmenttable", entityplayer.posX, entityplayer.posY, entityplayer.posZ + 0.4D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", entityplayer.posX + 0.4D, entityplayer.posY, entityplayer.posZ, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", entityplayer.posX, entityplayer.posY, entityplayer.posZ - 0.4D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", entityplayer.posX - 0.4D, entityplayer.posY, entityplayer.posZ, 0.0D, 0.0D, 0.0D);
	}

	private void amuletEffectText2(World world, EntityPlayer entityplayer)
	{
		world.spawnParticle("enchantmenttable", entityplayer.posX + 0.2D, entityplayer.posY + 0.2D, entityplayer.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", entityplayer.posX + 0.2D, entityplayer.posY + 0.2D, entityplayer.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", entityplayer.posX - 0.2D, entityplayer.posY + 0.2D, entityplayer.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("enchantmenttable", entityplayer.posX - 0.2D, entityplayer.posY + 0.2D, entityplayer.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
	}

	public static int[] necronomiconCostCheck(ItemStack itemstack, EntityPlayer entityplayer)
	{
		int[] necronomiconItem = new int[3];
		necronomiconItem[0] = 0;
		necronomiconItem[1] = 0;

		int bloodCost = 2;

		if (ItemKeeper.bloodkeeperCheck(entityplayer, bloodCost, false) == true)
		{
			necronomiconItem[0] = 1;
			necronomiconItem[1] = bloodCost;
			return necronomiconItem;
		}
		return necronomiconItem;
	}

	public void necronomiconActivate(int bloodCost, EntityPlayer entityplayer)
	{
		if (bloodCost > 0)
		{
			boolean bloodkeeperUsed = false;

			for (int jk = 0; jk < entityplayer.inventory.mainInventory.length; jk++)
			{
				if (entityplayer.inventory.mainInventory[jk] != null)
				{
					if ("item.HSBloodkeeper".equals(entityplayer.inventory.mainInventory[jk].getItem().getUnlocalizedName()))
					{
						int soulsLeft = entityplayer.inventory.mainInventory[jk].getMaxDamage() - entityplayer.inventory.mainInventory[jk].getItemDamage();
						
						if (soulsLeft >= bloodCost)
						{
							entityplayer.inventory.mainInventory[jk].damageItem(bloodCost, entityplayer);
							bloodkeeperUsed = true;

							if (entityplayer.inventory.mainInventory[jk].getItemDamage() < entityplayer.inventory.mainInventory[jk].getMaxDamage())
								break;
							
							entityplayer.inventory.mainInventory[jk] = new ItemStack(HSItems.essenceKeeper, 1); 
							break;
						}

					}

				}

			}

			if (!bloodkeeperUsed)
			{
				for (int jk = 0; jk < entityplayer.inventory.mainInventory.length; jk++)
				{
					if (entityplayer.inventory.mainInventory[jk] != null)
					{
						if ("item.HSBloodVessel".equals(entityplayer.inventory.mainInventory[jk].getItem().getUnlocalizedName()))
						{
							int soulsLeft = entityplayer.inventory.mainInventory[jk].getMaxDamage() - entityplayer.inventory.mainInventory[jk].getItemDamage();
							if (soulsLeft >= bloodCost)
							{
								entityplayer.inventory.mainInventory[jk].damageItem(bloodCost, entityplayer);

								if (entityplayer.inventory.mainInventory[jk].getItemDamage() < entityplayer.inventory.mainInventory[jk].getMaxDamage())
									break;
								
								entityplayer.inventory.mainInventory[jk] = new ItemStack(HSItems.essenceVessel, 1); break;
							}
						}
					}
				}
			}
		}
	}
}
