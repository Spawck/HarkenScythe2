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
 * File created @[Mar 5, 2015, 10:35:59 AM]
 */
package com.spawck.hs2.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.init.HSItems;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class EntitySpectralMiner extends EntityTameable implements IRangedAttackMob
{
	private float field_70926_e;
	private float field_70924_f;
	public int soulAge = 0;
	public int petDamageTamed;
	public int petDamageReg;
	public float bI = 0.3F;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 0.25F, 20, 60, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, this.bI, true);
	private EntityAILeapAtTarget aiLeapAtTarget = new EntityAILeapAtTarget(this, 0.4F);

	public EntitySpectralMiner(World world)
	{
		super(world);
		this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_miner.png";
		setSize(0.8F, 1.8F);
		this.bI = 0.3F;
		getNavigator().setBreakDoors(true);
		getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(5, new EntityAIHSSpectralMinerFollowOwner(this, this.bI, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAITempt(this, 0.25F, HSItems.creepBall, false));
		this.tasks.addTask(7, new EntityAIWander(this, this.bI));
		this.tasks.addTask(8, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
		this.tasks.addTask(11, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.experienceValue = 0;
		this.petDamageReg = 1;
		this.petDamageTamed = 2;
		
		if (this.isTamed())
			this.setHealth(20.0F);
		else
			this.setHealth(10.0F);

		if ((world != null) && (!world.isRemote))
		{
			setCombatTask();
		}
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public void setAttackTarget(EntityLivingBase par1EntityLiving)
	{
		super.setAttackTarget(par1EntityLiving);
	}

	@Override
	protected void updateAITick()
	{
		this.dataWatcher.updateObject(18, Float.valueOf(getHealth()));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(18, new Float(getHealth()));
		this.dataWatcher.addObject(19, new Byte((byte)0));
		this.dataWatcher.addObject(20, new Byte((byte)BlockColored.func_150031_c(1))); // TODO: Maybe the other unnamed function? .getBlockFromDye(1)));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setShort("SAge", (short)this.soulAge);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		this.soulAge = tagCompound.getShort("SAge");
		this.setCombatTask();
	}

	@Override
	protected boolean canDespawn()
	{
		return !isTamed();
	}

	@Override
	protected String getHurtSound()
	{
		return "random.classic_hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "damage.fall";
	}

	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.playSound("mob.wolf.step", 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	public boolean func_70922_bv()
	{
		return this.dataWatcher.getWatchableObjectByte(19) == 1;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.field_70924_f = this.field_70926_e;

		if (func_70922_bv())
		{
			this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
		}
		else
		{
			this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
		}

		if (func_70922_bv())
		{
			this.numTicksToChaseTarget = 10;
		}

		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);
		
		ItemStack heldItem = this.getHeldItem();

		if ((this.worldObj.getBlock(x, y + 1, z) != HSBlocks.soulLight) && (this.worldObj.getBlock(x, y + 1, z) == Blocks.air) && (heldItem != null))
		{
			if (heldItem.getItem() == Item.getItemFromBlock(Blocks.torch))
			{
				this.worldObj.setBlock(x, y + 1, z, HSBlocks.soulLight);
			}
		}

		this.soulAge += 1;

		if (this.soulAge >= 200)
		{
			this.soulAge = 0;

			if (this.getHealth() == 1)
			{
				attackEntityFrom(DamageSource.starve, 1000);
			}
			else 
			{
				float health = this.getHealth() - 1;
				this.setHealth(health);
			
				if ((this.getHealth() <= 5) && (isTamed()))
				{
					this.generateRandomParticles("angryVillager");
				}
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}

		Entity entity = damageSource.getEntity();
		this.aiSit.setSitting(false);

		if ((entity instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			if ((player.getPersistentID().compareTo(getOwner())) && (!this.worldObj.isRemote)) // TODO
			{
				return false;
			}
		}

		if ((entity != null) && (!(entity instanceof EntityPlayer)) && (!(entity instanceof EntityArrow)))
		{
			amount = (amount + 1) / 2;
		}

		return super.attackEntityFrom(damageSource, amount);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if ((isTamed()) && (getHeldItem() != null) && (getHeldItem().getItem() == Item.getItemFromBlock(Blocks.torch)))
		{
			entity.setFire(2);
		}

		int damage = isTamed() ? this.petDamageTamed : this.petDamageReg;
		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
	}

	public int getAttackStrength(Entity entity)
	{
		ItemStack heldItem = this.getHeldItem();
		int damage = isTamed() ? this.petDamageTamed : this.petDamageReg;

		if (heldItem != null)
		{
			damage += heldItem.getDamageVsEntity(this) / 2; // TODO
		}
		
		return damage;
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack playerHeldItem = player.inventory.getCurrentItem();
		ItemStack minerHeldItem = this.getHeldItem();

		if (this.isTamed())
		{
			if (playerHeldItem != null)
			{
				if ((this.dataWatcher.getWatchableObjectInt(18) < 20) && (!isPotionActive(Potion.regeneration)))
				{
					if ((playerHeldItem.getItem() == HSItems.bloodKeeper) || (playerHeldItem.getItem() == HSItems.bloodVessel))
					{
						playerHeldItem.damageItem(1, player);
						this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));

						if (playerHeldItem.getItem() == HSItems.bloodKeeper)
						{
							if (playerHeldItem.getMaxDamage() - playerHeldItem.getItemDamage() == 0)
							{
								//playerHeldItem.getItem() = HSItems.essenceKeeper;
								playerHeldItem.setItemDamage(-playerHeldItem.getMaxDamage());
							}
							
							return true;
						}

						if (playerHeldItem.getItem() == HSItems.bloodVessel)
						{
							if (playerHeldItem.getMaxDamage() - playerHeldItem.getItemDamage() == 0)
							{
								// TODO playerHeldItem.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
								playerHeldItem.setItemDamage(-playerHeldItem.getMaxDamage());
							}
							
							return true;
						}
					}
				}

				if ((((playerHeldItem.getItem() instanceof ItemBow)) && (minerHeldItem == null)) || (((playerHeldItem.getItem() instanceof ItemSword)) && (minerHeldItem == null)))
				{
					if (!this.worldObj.isRemote)
					{
						ItemStack newWeapon = new ItemStack(playerHeldItem.getItem(), 1, playerHeldItem.getItemDamage());
						this.setCurrentItemOrArmor(0, newWeapon);
						this.setCombatTask();
					}

					if (!player.capabilities.isCreativeMode)
					{
						playerHeldItem.stackSize -= 1;
					}

					if (playerHeldItem.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
					}
				}

				/* TODO: From the Asgard Shield crossover. Not needed here.
				 * 
				if (((playerHeldItem.getItem() instanceof ItemArmor)) && (!"mod_AsgardShield.common.ItemAsgardShield".equals(playerHeldItem.getItem().getClass().getName()))) 
				{
					if (!this.worldObj.isRemote)
					{
						ItemStack newArmor = new ItemStack(playerHeldItem.getItem(), 1, playerHeldItem.getItemDamage());

						int i = getArmorPosition(newArmor) - 1;
						ItemStack currentArmor = getCurrentArmor(i);

						if (currentArmor == null)
						{
							setCurrentItemOrArmor(i + 1, newArmor.copy());

							if (!player.capabilities.isCreativeMode)
							{
								playerHeldItem.stackSize -= 1;
							}

							if (playerHeldItem.stackSize <= 0)
							{
								player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
							}
						}
					}
				}*/

				if ((playerHeldItem.getItem() == Item.getItemFromBlock(Blocks.torch)) && (minerHeldItem == null))
				{
					if (!this.worldObj.isRemote)
					{
						ItemStack newTorch = new ItemStack(playerHeldItem.getItem(), 1);
						this.setCurrentItemOrArmor(0, newTorch);
						this.setCombatTask();
					}

					if (!player.capabilities.isCreativeMode)
					{
						playerHeldItem.stackSize -= 1;
					}

					if (playerHeldItem.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
					}
				}
			}
			else if ((player.username.equalsIgnoreCase(getOwner())) && (!this.worldObj.isRemote) && (minerHeldItem != null) && (!player.isSneaking())) // TODO
			{
				if (((minerHeldItem.getItem() instanceof ItemBow)) || ((minerHeldItem.getItem() instanceof ItemSword)))
				{
					ItemStack oldWeapon = new ItemStack(minerHeldItem.getItem(), 1, minerHeldItem.getItemDamage());
					player.setCurrentItemOrArmor(0, oldWeapon);
					setCurrentItemOrArmor(0, null);
					setCombatTask();
				}

			}

			if ((player.username.equalsIgnoreCase(getOwner())) && (!this.worldObj.isRemote) && (minerHeldItem != null) && (!player.isSneaking())) // TODO
			{
				if (minerHeldItem.getItem() == Item.getItemFromBlock(Blocks.torch))
				{
					Block oldCBlock = Block.getBlockFromItem(minerHeldItem.getItem());
					ItemStack oldBlock = new ItemStack(oldCBlock, 1);
					
					if (player.inventory.addItemStackToInventory(oldBlock));
					
					this.setCurrentItemOrArmor(0, null);

					int x = MathHelper.floor_double(this.posX);
					int y = MathHelper.floor_double(this.posY);
					int z = MathHelper.floor_double(this.posZ);

					if (this.worldObj.getBlock(x, y + 1, z) == HSBlocks.soulLight)
					{
						this.worldObj.setBlockToAir(x, y + 1, z);
					}
				}
			}

			if ((player.username.equalsIgnoreCase(getOwner())) && (!this.worldObj.isRemote) && (player.isSneaking())) // TODO
			{
				this.aiSit.setSitting(!isSitting());
				this.isJumping = false;
				setPathToEntity((PathEntity)null);
			}
		}
		else if ((playerHeldItem != null) && (playerHeldItem.getItem() == HSItems.creepBall))
		{
			if (!player.capabilities.isCreativeMode)
			{
				playerHeldItem.stackSize -= 1;
			}

			if (playerHeldItem.stackSize <= 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
			}

			if (!this.worldObj.isRemote)
			{
				setTamed(true);
				setPathToEntity((PathEntity)null);
				setAttackTarget((EntityLiving)null);
				this.aiSit.setSitting(true);
				this.setHealth(20.0F);
				setOwner(player.username); // TODO
				playTameEffect(true);
				this.worldObj.setEntityState(this, (byte)7);
			}

			return true;
		}

		return super.interact(player);
	}

	// TODO
	public void func_70918_i(boolean par1)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(19);

		if (par1)
		{
			this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
		}
		else
		{
			this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityAgeable)
	{
		return null;
	}

	@Override
	public void setDead()
	{
		super.setDead();
	}

	// TODO: move to util class
	private void generateRandomParticles(String particleName)
	{
		double d0 = this.rand.nextGaussian() * 0.02D;
		double d1 = this.rand.nextGaussian() * 0.02D;
		double d2 = this.rand.nextGaussian() * 0.02D;
		this.worldObj.spawnParticle(particleName, this.posX, this.posY + 0.5D, this.posZ, d0, d1, d2);
	}

	// TODO
	public void initCreature()
	{
		this.tasks.addTask(3, this.aiLeapAtTarget);
		this.tasks.addTask(4, this.aiAttackOnCollide);
	}

	// TODO
	public void attackEntityWithRangedAttack(EntityLiving par1EntityLiving, float par2)
	{
		EntityArrow arrow = new EntityArrow(this.worldObj, this, par1EntityLiving, 1.6F, 14 - this.worldObj.difficultySetting.getDifficultyId() * 4);
		int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
		int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
		arrow.setDamage(par2 * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);

		if (powerLevel > 0)
		{
			arrow.setDamage(arrow.getDamage() + powerLevel * 0.5D + 0.5D);
		}

		if (punchLevel > 0)
		{
			arrow.setKnockbackStrength(punchLevel);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0)
		{
			arrow.setFire(100);
		}

		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(arrow);
	}

	// TODO
	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		this.tasks.removeTask(this.aiLeapAtTarget);
		ItemStack itemstack = getHeldItem();

		if ((itemstack != null) && ((itemstack.getItem() instanceof ItemBow)))
		{
			this.tasks.addTask(4, this.aiArrowAttack);
		}
		else if ((itemstack != null) && ((itemstack.getItem() instanceof ItemSword)))
		{
			this.tasks.addTask(3, this.aiLeapAtTarget);
			this.tasks.addTask(4, this.aiAttackOnCollide);
		}
		else
		{
			this.tasks.addTask(4, this.aiAttackOnCollide);
		}
	}

	@Override
	protected void dropFewItems(boolean hitByPlayer, int lootingLevel)
	{
		ItemStack is = this.getHeldItem();

		if (((is != null) && ((is.getItem() instanceof ItemBow))) || ((is != null) && ((is.getItem() instanceof ItemSword))))
		{
			if (is.getItemDamage() + 50 < is.getMaxDamage())
			{
				ItemStack oldWeapon = new ItemStack(is.getItem(), 1, is.getItemDamage() + 50);
				this.entityDropItem(oldWeapon, 0.0F);
			}
		}

		for (int loop = 0; loop < 4; loop++)
		{
			ItemStack wornArmor = this.getEquipmentInSlot(loop);
		
			if (wornArmor != null)
			{
				if (wornArmor.getItemDamage() + 50 < wornArmor.getMaxDamage())
				{
					ItemStack oldArmor = new ItemStack(wornArmor.getItem(), 1, wornArmor.getItemDamage() + 50);
					this.entityDropItem(oldArmor, 0.0F);
				}
			}
		}
	}

	@Override
	public int getTotalArmorValue()
	{
		int armorVal = super.getTotalArmorValue() + 2;

		if (armorVal > 20)
		{
			armorVal = 20;
		}

		return armorVal;
	}
}
