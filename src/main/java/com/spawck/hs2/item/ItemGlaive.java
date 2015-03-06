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
 * File created @[Mar 5, 2015, 4:33:50 PM]
 */
package com.spawck.hs2.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.spawck.hs2.init.HSBlocks;
import com.spawck.hs2.reference.HSTab;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemGlaive extends ItemSword
{
	private static Random rand = new Random();
	Side side = FMLCommonHandler.instance().getEffectiveSide();
	private float weaponDamage;
	private final Item.ToolMaterial toolMaterial;
	private static Block[] blocksEffectiveAgainst = new Block[0];
	public float glaiveKnockback;
	public boolean glaiveCharged;
	private int specialNumber;
	public int bloodPoolChance;

	public ItemGlaive(Item.ToolMaterial toolMat, int type, int bloodChance)
	{
		super(toolMat);
		this.maxStackSize = 1;
		this.toolMaterial = toolMat;
		this.weaponDamage = (3 + toolMat.getDamageVsEntity());
		this.setMaxDamage(toolMat.getMaxUses());
		this.glaiveKnockback = 0.2F;
		this.specialNumber = type;
		this.bloodPoolChance = bloodChance;
		this.glaiveCharged = false;
		this.setCreativeTab(HSTab.HS_TAB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon("mod_HarkenScythe:" + getUnlocalizedName());
	}

	@Override
	public float func_150893_a(ItemStack is, Block block) /* getStrVsBlock */
	{
		return block == Blocks.web ? 15.0F : 1.5F;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase attacked, EntityLivingBase attacker)
	{
		attacked.addVelocity(-MathHelper.sin(attacked.rotationYaw * 3.141593F / 180.0F) * this.glaiveKnockback * 0.5F, 0.1D, MathHelper.cos(attacked.rotationYaw * 3.141593F / 180.0F) * this.glaiveKnockback * 0.5F);
		
		if (this.glaiveCharged == true)
		{
			itemstack.damageItem(3, attacker);
			
			return true;
		}

		itemstack.damageItem(1, attacker);
		
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World world, Block block, int x, int y, int z, EntityLivingBase living)
	{
		is.damageItem(3, living);
		
		return true;
	}

	@Override
	public float func_150931_i() /* getDamageVsEntity */
	{
		if (this.glaiveCharged == true)
		{
			return this.weaponDamage * 2;
		}
		
		return this.weaponDamage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if (player.getCurrentEquippedItem() != null)
		{
			player.setItemInUse(is, getMaxItemUseDuration(is));
			player.worldObj.playSoundAtEntity(player, "random.breath", 1.0F, 0.9F);
		}
		
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int itemInUseCount)
	{
		int duration = this.getMaxItemUseDuration(is) - itemInUseCount;

		if (duration >= 24)
		{
			this.glaiveCharged = true;
			
			for (int entityList = 0; entityList < world.loadedEntityList.size(); entityList++)
			{
				Entity entity = player;
				
				if (this.side == Side.CLIENT)
				{
					entity = (Entity)world.getLoadedEntityList().get(entityList);
				}
				
				if (this.side == Side.SERVER)
				{
					entity = (Entity)world.loadedEntityList.get(entityList);
				}

				if ((entity != player) && (!entity.isDead) && (player.getDistanceSqToEntity(entity) < 25.0D) && ((entity instanceof EntityLiving)))
				{
					if ((entity instanceof EntityTameable)) 
					{
						EntityTameable entityTamed = (EntityTameable)entity;
						if ((entityTamed.isTamed() == true) && (entityTamed.getAttackTarget() != player));
					}
					else 
					{
						EntityLiving entityliving = (EntityLiving)entity;
						Vec3 lookVec = player.getLook(1.0F).normalize();
						Vec3 newVec = Vec3.createVectorHelper(entity.posX - player.posX, entity.boundingBox.minY + entity.height / 2.0F - (player.posY + player.getEyeHeight()), entity.posZ - player.posZ);
						//entity.worldObj.getWorldVec3Pool().getVecFromPool
						double vecLength = newVec.lengthVector();
						newVec = newVec.normalize();
						double dotProduct = lookVec.dotProduct(newVec);
					
						if (dotProduct > 0.9D - 0.025D / vecLength)
						{
							player.attackTargetEntityWithCurrentItem(entityliving);
						}
					}
				}
			}
		
			player.worldObj.playSoundAtEntity(player, "mob.irongolem.throw", 1.0F, 1.0F);
			player.swingItem();
			this.glaiveCharged = false;
		}
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack is)
	{
		return false;
	}

	@Override
	public int getItemEnchantability()
	{
		return this.toolMaterial.getEnchantability();
	}

	@Override
	public boolean getIsRepairable(ItemStack repairedStack, ItemStack repairingStack)
	{
		if (this.specialNumber == 0)
		{
			return this.toolMaterial.getRepairItemStack() == repairingStack ? true : super.getIsRepairable(repairedStack, repairingStack);
		}
		
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (side != 1)
			return false;
		
		if ((player.canPlayerEdit(x, y, z, side, is)) && (player.canPlayerEdit(x, y, z, side, is)))
		{
			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);

			if ((block == HSBlocks.creep) && (meta == 0))
			{
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);
				Block farmland = Blocks.farmland;
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, farmland.stepSound.soundName, (farmland.stepSound.getVolume() + 1.0F) / 2.0F, farmland.stepSound.getPitch() * 0.8F);
				// TODO: soundName or that wonky looking one?
				is.damageItem(1, player);
				
				return true;
			}

			return false;
		}

		return false;
	}
}
