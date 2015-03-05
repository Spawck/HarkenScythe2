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
 * File created @[Mar 5, 2015, 10:34:20 AM]
 */
package com.spawck.hs2.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
public class EntitySoulCulled extends EntitySoul
{
	public int innerRotation;
	public int aS;
	private int soulWorth;
	private String aH;
	public int soulAge = 0;
	public String fallenSoulName;

	public EntitySoulCulled(World world)
	{
		super(world);
		this.aH = "/mod_HarkenScythe/HarkenScytheTex/soul/soulculled.png"; // TODO
		this.innerRotation = 0;
		this.preventEntitySpawning = false;
		this.setSize(0.5F, 0.5F);
		this.aS = 5;
		this.innerRotation = this.rand.nextInt(100000);
		this.soulWorth = 0;
		this.setHealth(5.0F);
	}

	public EntitySoulCulled(World world, double x, double y, double z, int worth, EntityLiving living)
	{
		this(world);
		this.setPosition(x, y, z);
		this.soulWorth = worth;
		this.fallenSoulName = getFallenSoulName(living);
	}

	@Override
	public int getSoulWorth()
	{
		return this.soulWorth;
	}

	@Override
	public String getSoulTexture()
	{
		return this.aH = "/mods/mod_HarkenScythe/textures/models/soul/soulculled.png";
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
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setShort("Age", (short)this.soulAge);
		tagCompound.setShort("Value", (short)this.soulWorth);
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

	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack heldItem = player.inventory.getCurrentItem();
		
		if (heldItem == null) return false;

		if (((heldItem.getItem() == HSItems.soulKeeper) && (heldItem.getItemDamage() != 0)) || (heldItem.getItem() == HSItems.essenceKeeper) || ((heldItem.getItem() == HSItems.soulVessel) && (heldItem.getItemDamage() != 0)) || (heldItem.getItem() == HSItems.essenceVessel))
		{
			ItemKeeper.soulkeeperFillCheck(player, this.soulWorth);
			TierAbilities.sTALivingmetal(player, 10);
			this.setDead();
			
			return true;
		}

		return super.interact(player);
	}
}
