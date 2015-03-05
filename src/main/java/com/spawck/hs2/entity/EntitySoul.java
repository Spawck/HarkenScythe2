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
 * File created @[Mar 5, 2015, 10:33:56 AM]
 */
package com.spawck.hs2.entity;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.init.HSItems;
import com.spawck.hs2.item.ItemKeeper;
import com.spawck.hs2.util.TierAbilities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class EntitySoul extends EntityLiving
{
	public int innerRotation;
	public int aS;
	private int soulWorth;
	private String aH;
	public int soulAge = 0;
	public String fallenSoulName;

	public EntitySoul(World par1World)
	{
		super(par1World);
		this.aH = "/mod_HarkenScythe/HarkenScytheTex/soul/soulcommon.png"; // TODO
		this.innerRotation = 0;
		this.preventEntitySpawning = false;
		this.setSize(0.5F, 0.5F);
		this.aS = 5;
		this.innerRotation = this.rand.nextInt(100000);
		this.soulWorth = 0;
		this.fallenSoulName = "null";
		this.setHealth(5.0F);
	}

	public EntitySoul(World par1World, double par2, double par4, double par6, int par7, EntityLiving par8)
	{
		this(par1World);
		setPosition(par2, par4, par6);
		this.soulWorth = par7;
		this.fallenSoulName = getFallenSoulName(par8);
	}

	public int getSoulWorth()
	{
		return this.soulWorth;
	}

	// TODO
	public String getSoulTexture()
	{
		return this.aH = "/mods/mod_HarkenScythe/textures/models/soul/soulcommon.png";
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(8, Integer.valueOf(this.aS));
	}

	@Override
	public void onUpdate()
	{
		this.moveStrafing = (this.moveForward = 0.0F);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.innerRotation += 1;
		this.dataWatcher.updateObject(8, Integer.valueOf(this.aS));

		if ((this.worldObj.isAirBlock((int) Math.round(this.posX), (int) Math.round(this.posY - 1.0D), (int) Math.round(this.posZ))) && (this.worldObj.isAirBlock((int) Math.round(this.posX), (int) Math.round(this.posY - 2.0D), (int) Math.round(this.posZ))))
		{
			this.posY -= 1.0D;
		}

		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.posY);
		int var3 = MathHelper.floor_double(this.posZ);

		/*
		 * TODO: if ((this.worldObj.getBlock(var1, var2, var3) !=
		 * mod_HarkenScythe.HSSoulLight.blockID) &&
		 * (this.worldObj.getBlock(var1, var2, var3) == Blocks.air)) {
		 * this.worldObj.setBlock(var1, var2, var3,
		 * mod_HarkenScythe.HSSoulLight.blockID); }
		 */

		this.soulAge += 1;

		if (this.soulAge >= 1000)
		{
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setShort("Age", (short) this.soulAge);
		tagCompound.setShort("Value", (short) this.soulWorth);
		tagCompound.setString("fallenSoul", this.fallenSoulName);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		this.soulAge = tagCompound.getShort("Age");
		this.soulWorth = tagCompound.getShort("Value");

		if (tagCompound.hasKey("fallenSoul"))
		{
			this.fallenSoulName = tagCompound.getString("fallenSoul");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount)
	{
		if (damageSource.getEntity() instanceof EntityHarbinger)
		{
			return super.attackEntityFrom(damageSource, amount);
		}

		return false;
	}

	@Override
	protected boolean interact(EntityPlayer player)
	{
		ItemStack heldItem = player.inventory.getCurrentItem();

		if (heldItem == null)
			return false;

		if (((heldItem.getItem() == HSItems.soulKeeper) && (heldItem.getItemDamage() != 0)) || (heldItem.getItem() == HSItems.essenceKeeper) || ((heldItem.getItem() == HSItems.soulVessel) && (heldItem.getItemDamage() != 0)) || (heldItem.getItem() == HSItems.essenceVessel))
		{
			ItemKeeper.soulkeeperFillCheck(player, this.soulWorth);
			TierAbilities.sTALivingmetal(player, 2);
			this.setDead();

			return true;
		}

		return super.interactFirst(player); // TODO: First or regular?
	}

	@Override
	public void setDead()
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);

		if (this.worldObj.getBlock(x, y, z) == HSBlocks.soulLight)
		{
			this.worldObj.setBlockToAir(x, y, z);
		}

		playSound("mob.wither.shoot", 0.1F, 1.0F);
		super.setDead();
	}

	public String getFallenSoulName(EntityLivingBase living)
	{
		String fallenSoulName = "null";

		if ((living instanceof EntityChicken))
			return fallenSoulName = "Chicken";
		if ((living instanceof EntityCow))
			return fallenSoulName = "Cow";
		if ((living instanceof EntitySheep))
			return fallenSoulName = "Sheep";
		if ((living instanceof EntityBat))
			return fallenSoulName = "Bat";
		if ((living instanceof EntityPig))
			return fallenSoulName = "Pig";
		if ((living instanceof EntityWolf))
			return fallenSoulName = "Wolf";
		if ((living instanceof EntityOcelot))
		{
			EntityOcelot newOcelot = (EntityOcelot) living;

			if (newOcelot.getTameSkin() == 1)
				return fallenSoulName = "Cat_Black";
			if (newOcelot.getTameSkin() == 2)
				return fallenSoulName = "Cat_Red";
			if (newOcelot.getTameSkin() == 3)
				return fallenSoulName = "Cat_Siamese";

			return fallenSoulName = "Ocelot";
		}
		if ((living instanceof EntitySquid))
			return fallenSoulName = "Squid";
		if ((living instanceof EntityVillager))
			return fallenSoulName = "Villager";
		if ((living instanceof EntityCreeper))
			return fallenSoulName = "Creeper";
		if ((living instanceof EntityZombie))
		{
			if ((living instanceof EntityPigZombie))
				return fallenSoulName = "PigZombie";

			return fallenSoulName = "Zombie";
		}
		if ((living instanceof EntitySkeleton))
		{
			EntitySkeleton newSkeleton = (EntitySkeleton) living;

			if (newSkeleton.getSkeletonType() == 1)
				return fallenSoulName = "Skeleton_Wither";

			return fallenSoulName = "Skeleton";
		}
		if ((living instanceof EntityWitch))
			return fallenSoulName = "Witch";
		if ((living instanceof EntitySpider))
		{
			if ((living instanceof EntityCaveSpider))
				return fallenSoulName = "CaveSpider";

			return fallenSoulName = "Spider";
		}
		if ((living instanceof EntitySilverfish))
			return fallenSoulName = "Silverfish";
		if ((living instanceof EntityEnderman))
			return fallenSoulName = "Enderman";
		if ((living instanceof EntityBlaze))
			return fallenSoulName = "Blaze";
		if ((living instanceof EntityGhast))
			return fallenSoulName = "Ghast";

		return fallenSoulName;
	}

	public static boolean createSpectralPet(String fallenSoulName, World world, double x, double y, double z, Random rand, Boolean needsMaster)
	{
		if ("null".equals(fallenSoulName))
			return false;

		if ("Chicken".equals(fallenSoulName))
		{
			EntityHSSpectralChicken newmob = new EntityHSSpectralChicken(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);

			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}

			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Cow".equals(fallenSoulName))
		{
			EntityHSSpectralCow newmob = new EntityHSSpectralCow(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Sheep".equals(fallenSoulName))
		{
			EntityHSSpectralSheep newmob = new EntityHSSpectralSheep(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Bat".equals(fallenSoulName))
		{
			EntityHSSpectralBat newmob = new EntityHSSpectralBat(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Pig".equals(fallenSoulName))
		{
			EntityHSSpectralPig newmob = new EntityHSSpectralPig(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Wolf".equals(fallenSoulName))
		{
			EntityHSSpectralWolf newmob = new EntityHSSpectralWolf(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Cat_Black".equals(fallenSoulName))
		{
			EntityHSSpectralOcelot newmob = new EntityHSSpectralOcelot(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			newmob.setTameSkin(1);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Cat_Red".equals(fallenSoulName))
		{
			EntityHSSpectralOcelot newmob = new EntityHSSpectralOcelot(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			newmob.setTameSkin(2);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
		
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Cat_Siamese".equals(fallenSoulName))
		{
			EntityHSSpectralOcelot newmob = new EntityHSSpectralOcelot(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			newmob.setTameSkin(3);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Ocelot".equals(fallenSoulName))
		{
			EntityHSSpectralOcelot newmob = new EntityHSSpectralOcelot(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Squid".equals(fallenSoulName))
		{
			EntityHSSpectralSquid newmob = new EntityHSSpectralSquid(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Villager".equals(fallenSoulName))
		{
			EntityHSSpectralVillager newmob = new EntityHSSpectralVillager(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Creeper".equals(fallenSoulName))
		{
			EntityHSSpectralCreeper newmob = new EntityHSSpectralCreeper(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Zombie".equals(fallenSoulName))
		{
			EntityHSSpectralZombie newmob = new EntityHSSpectralZombie(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("PigZombie".equals(fallenSoulName))
		{
			EntityHSSpectralPigZombie newmob = new EntityHSSpectralPigZombie(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Skeleton".equals(fallenSoulName))
		{
			EntityHSSpectralSkeleton newmob = new EntityHSSpectralSkeleton(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);
			
			return true;
		}
		if ("Skeleton_Wither".equals(fallenSoulName))
		{
			EntityHSSpectralSkeletonWither newmob = new EntityHSSpectralSkeletonWither(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Witch".equals(fallenSoulName))
		{
			EntityHSSpectralWitch newmob = new EntityHSSpectralWitch(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
		
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Spider".equals(fallenSoulName))
		{
			EntityHSSpectralSpider newmob = new EntityHSSpectralSpider(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("CaveSpider".equals(fallenSoulName))
		{
			EntityHSSpectralCaveSpider newmob = new EntityHSSpectralCaveSpider(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Silverfish".equals(fallenSoulName))
		{
			EntityHSSpectralSilverfish newmob = new EntityHSSpectralSilverfish(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
		
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Enderman".equals(fallenSoulName))
		{
			EntityHSSpectralEnderman newmob = new EntityHSSpectralEnderman(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
			
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Blaze".equals(fallenSoulName))
		{
			EntityHSSpectralBlaze newmob = new EntityHSSpectralBlaze(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
		
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}
		if ("Ghast".equals(fallenSoulName))
		{
			EntityHSSpectralGhast newmob = new EntityHSSpectralGhast(world);
			float var4 = 2.0F;
			float var5 = 2.0F;
			newmob.setLocationAndAngles(x, y + 0.5D, z, rand.nextFloat() * 360.0F, 0.0F);
		
			if (needsMaster.booleanValue() == true)
			{
				newmob.isHarbingerMinon = 1;
				newmob.setEntityHealth(15);
			}
			
			world.spawnEntityInWorld(newmob);

			return true;
		}

		return false;
	}
}
