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
 * File created @[Mar 5, 2015, 10:37:13 AM]
 */
package com.spawck.hs2.entity;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSEnchantments;
import com.spawck.hs2.init.HSItems;
import com.spawck.hs2.item.ItemScythe;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class EntitySpectralMinerEvil extends EntityMob
{
	public int soulAge = 0;
	
	private boolean soul1;
	private boolean soul2;
	private boolean soul3;
	private boolean soul4;
	private boolean soul5;
	
	Side side = FMLCommonHandler.instance().getEffectiveSide();

	private static Item[] randomPickaxe = new Item[5];
	private static ItemStack[] randomDrops = new ItemStack[6];

	public EntitySpectralMinerEvil(World world)
	{
		super(world);
		// TODO this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_miner_evil.png";
		//this.moveSpeed = 0.3F; substituted 0.3D below instead of adding a move speed
		this.isImmuneToFire = true;
		this.getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.3D, false)); 
		this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.3D));
		this.tasks.addTask(3, new EntityAIWander(this, 0.3D));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 0.3D, false));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.experienceValue = 5;
		this.soul1 = true;
		this.soul2 = true;
		this.soul3 = true;
		this.soul4 = true;
		this.soul5 = true;
		this.setCurrentItemOrArmor(0, new ItemStack(getRandomPickaxe(), 1));
		this.setHealth(50.F);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setShort("Age", (short) this.soulAge);
		tagCompound.setBoolean("Soul1", this.soul1);
		tagCompound.setBoolean("Soul2", this.soul2);
		tagCompound.setBoolean("Soul3", this.soul3);
		tagCompound.setBoolean("Soul4", this.soul4);
		tagCompound.setBoolean("Soul5", this.soul5);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		this.soulAge = tagCompound.getShort("Age");
		this.soul1 = tagCompound.getBoolean("Soul1");
		this.soul2 = tagCompound.getBoolean("Soul2");
		this.soul3 = tagCompound.getBoolean("Soul3");
		this.soul4 = tagCompound.getBoolean("Soul4");
		this.soul5 = tagCompound.getBoolean("Soul5");
	}

	// TODO
	public int getAttackStrength(Entity par1Entity)
	{
		ItemStack itemstack = getHeldItem();
		int i = 5;

		if (itemstack != null)
		{
			i += itemstack.getDamageVsEntity(this);
		}

		return i;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void attackEntity(Entity entity, float amount)
	{
		if ((this.attackTime <= 0) && (amount < 1.2F) && (entity.boundingBox.maxY > this.boundingBox.minY) && (entity.boundingBox.minY < this.boundingBox.maxY))
		{
			this.attackTime = 20;
			attackEntityAsMob(entity);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if (super.attackEntityAsMob(entity))
		{
			if ((entity instanceof EntityLiving))
			{
				byte b0 = 0;

				if (this.worldObj.difficultySetting.getDifficultyId() > 1)
				{
					b0 = 30;

					if (this.worldObj.difficultySetting.getDifficultyId() == 2)
					{
						b0 = 60;
					}
					else if (this.worldObj.difficultySetting.getDifficultyId() == 3)
					{
						b0 = 120;
					}
				}

				if (b0 > 0)
				{
					((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, b0 * 20, 0));
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		if ((isSoulStealingAttack(damageSource) == true) && (super.attackEntityFrom(damageSource, amount)))
		{
			this.soulAge = 0;
			this.soulStolen();
			Entity entity = damageSource.getEntity();

			if ((this.riddenByEntity != entity) && (this.ridingEntity != entity))
			{
				if (entity != this)
				{
					this.entityToAttack = entity;
				}

				return true;
			}

			return true;
		}

		return false;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.soulAge += 1;

		if ((this.soulAge >= 6000) && (!this.worldObj.isRemote))
		{
			this.setDead();

			if (!this.worldObj.isRemote)
			{
				EntitySoulLost newSoulLost = new EntitySoulLost(this.worldObj, (int) Math.round(this.posX - 0.5D), (int) Math.round(this.posY + 0.5D), (int) Math.round(this.posZ), 0);
				this.worldObj.spawnEntityInWorld(newSoulLost);
			}
		}
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.zombie.unfect";
	}

	@Override
	protected String getHurtSound()
	{
		return "random.classic_hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.wither.shoot";
	}

	@Override
	public void setDead()
	{
		super.setDead();
	}

	@Override
	protected void dropFewItems(boolean hitByPlayer, int lootingLevel)
	{
		int dropRateWithLooting = this.rand.nextInt(2 + lootingLevel);

		for (int loop = 0; loop < dropRateWithLooting; loop++)
		{
			ItemStack coal = new ItemStack(Items.coal, 1);
			Random random = new Random();
			coal = randomDrops[random.nextInt(randomDrops.length)];
			this.entityDropItem(coal, 1);
		}

		dropRateWithLooting = this.rand.nextInt(2 + lootingLevel);

		for (int loop = 0; loop < dropRateWithLooting; loop++)
		{
			this.dropItem(HSItems.ectoplasm, 1);
		}
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		this.dropItem(Items.diamond, 1);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.coal;
	}

	protected Item getRandomPickaxe()
	{
		Item newPickaxe = Items.wooden_pickaxe;
		Random random = new Random();
		newPickaxe = randomPickaxe[random.nextInt(randomPickaxe.length)];

		return newPickaxe;
	}

	protected boolean isSoulStealingAttack(DamageSource damageSource)
	{
		if ((damageSource.getEntity() instanceof EntityLiving))
		{
			EntityLivingBase attacker = (EntityLivingBase) damageSource.getEntity();

			if (attacker.getEquipmentInSlot(0) != null)
			{
				if ((attacker.getEquipmentInSlot(0).getItem() instanceof ItemScythe))
				{
					return true;
				}
			}

			if (EnchantmentHelper.getEnchantmentLevel(HSEnchantments.soulsteal.effectId, attacker.getEquipmentInSlot(0)) > 0)
			{
				return true;
			}
		}

		return false;
	}

	public void soulStolen()
	{
		if ((this.getHealth() < 50) && (this.soul1 == true))
		{
			this.createSlimes();
			this.soul1 = false;
		}
		if ((this.getHealth() < 40) && (this.soul2 == true))
		{
			this.createSlimes();
			this.soul2 = false;
		}
		if ((this.getHealth() < 30) && (this.soul3 == true))
		{
			this.createSlimes();
			this.soul3 = false;
		}
		if ((this.getHealth() < 20) && (this.soul4 == true))
		{
			this.createSlimes();
			this.soul4 = false;
		}
		if ((this.getHealth() < 10) && (this.soul5 == true))
		{
			this.createSlimes();
			this.soul5 = false;
		}
		if (this.getHealth() < 1)
		{
			this.createSlimes();
		}
	}

	public void createSlimes()
	{
		for (int loop = 0; loop < 1; loop++)
		{
			float var4 = (loop % 2 - 0.5F) * 2.0F / 4.0F;
			float var5 = (loop / 2 - 0.5F) * 2.0F / 4.0F;

			EntitySlimeSoul newSlime = new EntitySlimeSoul(this.worldObj);
			newSlime.setLocationAndAngles(this.posX + var4, this.posY + 0.5D, this.posZ + var5, this.rand.nextFloat() * 360.0F, 0.0F);
			newSlime.setSlimeSize(1);
			newSlime.setResourceWorth(2);
			this.worldObj.spawnEntityInWorld(newSlime);
		}
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (!this.worldObj.isRemote)
		{
			for (int entityList = 0; entityList < this.worldObj.loadedEntityList.size(); entityList++)
			{
				Entity entity = this;
				
				if (this.side == Side.CLIENT)
				{
					entity = (Entity)this.worldObj.getLoadedEntityList().get(entityList);
				}
				if (this.side == Side.SERVER)
				{
					entity = (Entity)this.worldObj.loadedEntityList.get(entityList);
				}

				if ((!entity.isDead) && (getDistanceSqToEntity(entity) < 100.0D) && ((entity instanceof EntityPlayer)))
				{
					if (!((EntityPlayer) entity).isPotionActive(Potion.nightVision))
					{
						((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 0));
					}
				}
			}
		}
	}

	static
	{
		randomPickaxe[0] = Items.wooden_pickaxe;
		randomPickaxe[1] = Items.stone_pickaxe;
		randomPickaxe[2] = Items.iron_pickaxe;
		randomPickaxe[3] = Items.golden_pickaxe;
		randomPickaxe[4] = Items.diamond_pickaxe;

		randomDrops[0] = new ItemStack(Items.coal, 1);
		randomDrops[1] = new ItemStack(Blocks.iron_ore, 1);
		randomDrops[2] = new ItemStack(Blocks.gold_ore, 1);
		randomDrops[3] = new ItemStack(Blocks.torch, 1);
		randomDrops[4] = new ItemStack(Items.redstone, 1);
		randomDrops[5] = new ItemStack(Items.clay_ball, 1);
	}
}
