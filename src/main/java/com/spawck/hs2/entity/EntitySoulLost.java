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
 * File created @[Mar 5, 2015, 10:35:31 AM]
 */
package com.spawck.hs2.entity;

import java.util.Random;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.init.HSItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class EntitySoulLost extends EntityMob
{
	public int innerRotation;
	public int aS;
	public int soulAge = 0;
	private int soulWorth;
	private String aH;
	private int specialNumber;

	public EntitySoulLost(World world)
	{
		super(world);
		this.aH = "/mod_HarkenScythe/HarkenScytheTex/soul/soullost.png";
		this.innerRotation = 0;
		this.preventEntitySpawning = false;
		this.setSize(0.5F, 0.5F);
		this.aS = 5;
		this.innerRotation = this.rand.nextInt(100000);
		this.soulWorth = 0;
		this.setHealth(5.0F);
	}

	public EntitySoulLost(World world, double x, double y, double z, int worth)
	{
		this(world);
		this.setPosition(x, y, z);
		this.soulWorth = worth;
	}

	public int getSoulWorth()
	{
		return this.soulWorth;
	}

	public String getSoulTexture()
	{
		return this.aH = "/mods/mod_HarkenScythe/textures/models/soul/soullost.png";
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
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.innerRotation += 1;
		this.dataWatcher.updateObject(8, Integer.valueOf(this.aS));
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.posY);
		int var3 = MathHelper.floor_double(this.posZ);

		if ((this.worldObj.getBlock(var1, var2, var3) != HSBlocks.soulLight) && (this.worldObj.getBlock(var1, var2, var3) == Blocks.air))
		{
			this.worldObj.setBlock(var1, var2, var3, HSBlocks.soulLight);
		}

		if ((!this.worldObj.isRemote) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL))
		{
			setDead();
		}

		this.soulAge += 1;
		
		if (this.soulAge >= 6000)
		{
			setDead();
		}

		if ((this.worldObj.canBlockSeeTheSky(var1, var2, var3) == true) || (this.posY >= 60.0D))
		{
			setDead();
		}
	}

	@Override
	public boolean getCanSpawnHere()
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);

		if ((this.worldObj.canBlockSeeTheSky(x, y, z) == true) || (this.posY >= 60.0D))
		{
			return false;
		}
		
		return super.getCanSpawnHere();
	}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 2;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount)
	{
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setShort("Age", (short)this.soulAge);
		tagCompound.setShort("Value", (short)this.soulWorth);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		this.soulAge = tagCompound.getShort("Age");
		this.soulWorth = tagCompound.getShort("Value");
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
	public boolean interact(EntityPlayer player)
	{
		ItemStack heldItem = player.inventory.getCurrentItem();
		
		if (heldItem == null) 
			return false;

		if (((heldItem.getItem() == HSItems.soulKeeper) && (heldItem.getItemDamage() != 0)) || (heldItem.getItem() == HSItems.essenceKeeper) || ((heldItem.getItem() == HSItems.soulVessel) && (heldItem.getItemDamage() != 0)) || (heldItem.getItem() == HSItems.essenceVessel))
		{
			if (!player.worldObj.isRemote)
			{
				EntitySpectralMinerEvil newSpectralMiner = new EntitySpectralMinerEvil(this.worldObj);
				newSpectralMiner.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
				this.worldObj.spawnEntityInWorld(newSpectralMiner);
				this.setDead();
			}
			
			return true;
		}

		return super.interact(player);
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

		this.playSound("mob.wither.shoot", 0.1F, 1.0F);
		super.setDead();
	}

	public static boolean createSpectralPet(World world, double x, double y, double z, Random random)
	{
		EntitySpectralMiner newmob = new EntitySpectralMiner(world);
		//float var4 = 2.0F;
		//float var5 = 2.0F;
		newmob.setLocationAndAngles(x, y + 0.5D, z, random.nextFloat() * 360.0F, 0.0F);
		world.spawnEntityInWorld(newmob);
		return true;
	}
}
