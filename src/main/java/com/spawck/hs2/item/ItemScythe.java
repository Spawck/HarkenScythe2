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
 * File created @[Mar 5, 2015, 10:24:12 AM]
 */
package com.spawck.hs2.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import com.spawck.hs2.reference.HSTab;
import com.spawck.hs2.reference.Textures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class ItemScythe extends ItemSword
{
	private static Random random = new Random();
	Side side = FMLCommonHandler.instance().getEffectiveSide();
	private float weaponDamage;
	private final Item.ToolMaterial toolMaterial;
	private static Block[] blocksEffectiveAgainst = new Block[0];
	public float knockback;
	public boolean isCharged;
	private int soulReaperNum; // 1 = soul reaper, 0 if not

	public ItemScythe(Item.ToolMaterial toolMat, int srn)
	{
		super(toolMat);
		this.toolMaterial = toolMat;
		this.setMaxStackSize(1);
		this.weaponDamage = (3 + toolMat.getDamageVsEntity());
		this.setMaxDamage(toolMat.getMaxUses());
		this.knockback = 0.1F;
		this.isCharged = false;
		this.setCreativeTab(HSTab.HS_TAB);
		this.soulReaperNum = srn;
	}

	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", Textures.RESOURCE_PREFIX, this.getModifiedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		return String.format("item.%s%s", Textures.RESOURCE_PREFIX, this.getModifiedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon(this.getModifiedUnlocalizedName(super.getUnlocalizedName()) + "_" + this.soulReaperNum);
	}

	protected String getModifiedUnlocalizedName(String name)
	{
		return name.substring(name.indexOf(".") + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack is)
	{
		if (this.soulReaperNum == 1) 
		{
			return EnumRarity.epic;
		}

		return EnumRarity.common;
	}

	
	@Override
	public float func_150893_a(ItemStack is, Block block) /* getStrVsBlock */
	{
		if (block == Blocks.web)
		{
			return 15.0F;
		}
		else
		{
			Material mat = block.getMaterial();
			return mat != Material.plants && mat != Material.vine && mat != Material.coral && mat != Material.leaves && mat != Material.gourd ? 1.0F : 1.5F;
		}
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase attacked, EntityLivingBase attacker)
	{
		attacked.addVelocity(-MathHelper.sin(attacked.rotationYaw * (float)Math.PI / 180.0F) * this.knockback * 0.5F, 0.1D, MathHelper.cos(attacked.rotationYaw * (float)Math.PI / 180.0F) * this.knockback * 0.5F);

		if (this.isCharged == true)
		{
			is.damageItem(3, attacker);
			return true;
		}

		is.damageItem(1, attacker);
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
		if (this.isCharged == true)
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
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int tick)
	{
		int duration = getMaxItemUseDuration(is) - tick;

		if (duration >= 24)
		{
			this.isCharged = true;

			for (int entList = 0; entList < world.loadedEntityList.size(); entList++)
			{
				Entity entity = player;

				if (this.side == Side.CLIENT)
				{
					entity = (Entity)world.getLoadedEntityList().get(entList);
				}

				if (this.side == Side.SERVER)
				{
					entity = (Entity)world.loadedEntityList.get(entList);
				}

				if ((entity != player) && (!entity.isDead) && (player.getDistanceSqToEntity(entity) < 15.0D) && (entity instanceof EntityLiving))
				{
					if ((entity instanceof EntityTameable)) 
					{
						EntityTameable entityTamed = (EntityTameable)entity;

						if ((entityTamed.isTamed() == true) && (entityTamed.getAttackTarget() != player));
					} 
					else 
					{
						player.attackTargetEntityWithCurrentItem(entity);
					}
				}
			}

			player.worldObj.playSoundAtEntity(player, "mob.irongolem.throw", 1.0F, 1.0F);
			player.swingItem();
			this.isCharged = false;
		}
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!player.canPlayerEdit(x, y, z, side, is))
		{
			return false;
		}

		UseHoeEvent event = new UseHoeEvent(player, is, world, x, y, z);

		if (MinecraftForge.EVENT_BUS.post(event))
		{
			return false;
		}

		if (event.getResult() == Event.Result.ALLOW)
		{
			is.damageItem(1, player);
			return true;
		}

		int meta = world.getBlockMetadata(x, y, z);
		Block blockXYZ = world.getBlock(x, y, z);
		Block blockXY1Z = world.getBlock(x, y + 1, z);
		Block blockXY2Z = world.getBlock(x, y + 2, z);

		if (((side == 0) || (blockXYZ != Blocks.reeds)) && (blockXYZ != Blocks.wheat))// && (var11 != mod_HarkenScythe.HSBiomassPlant.blockID)) TODO: Re-add
		{
			return false;
		}

		int cropYield = 1;

		if (blockXY1Z == blockXYZ) 
		{
			cropYield = 2;
		}

		if (blockXY2Z == blockXYZ) 
		{
			cropYield = 3;
		}

		if (blockXYZ == Blocks.reeds)
		{
			player.dropPlayerItemWithRandomChoice(new ItemStack(Items.reeds, cropYield), false);
		}

		if (blockXYZ == Blocks.wheat)
		{
			if (meta == 7)
			{
				player.dropPlayerItemWithRandomChoice(new ItemStack(Items.wheat, 2), false);
			}
			
			player.dropPlayerItemWithRandomChoice(new ItemStack(Items.wheat_seeds, 1), false);
		}

		/* TODO: Re-add
		if (blockXYZ == mod_HarkenScythe.HSBiomassPlant.blockID)
		{
			if (meta == 3)
			{
				player.dropOneItem(new ItemStack(mod_HarkenScythe.HSBiomass, 2));
			}
			player.dropOneItem(new ItemStack(mod_HarkenScythe.HSBiomassSeed, 1));
		}*/

		for (int count = 0; count < 10; count++)
		{
			Random rand = new Random();
			Vec3 vec1 = Vec3.createVectorHelper((rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			vec1.rotateAroundX(-player.rotationPitch * 3.141593F / 180.0F);
			vec1.rotateAroundY(-player.rotationYaw * 3.141593F / 180.0F);
			Vec3 vec2 = Vec3.createVectorHelper((rand.nextFloat() - 0.5D) * 0.3D, -rand.nextFloat() * 0.6D - 0.3D, 0.6D);
			vec2.rotateAroundX(-player.rotationPitch * 3.141593F / 180.0F);
			vec2.rotateAroundY(-player.rotationYaw * 3.141593F / 180.0F);
			vec2 = vec2.addVector(player.posX, player.posY + player.getEyeHeight(), player.posZ);
			player.worldObj.spawnParticle("iconcrack_" + Items.wheat, x + 0.5D, y + 0.5D, z + 0.5D, vec1.xCoord, vec1.yCoord + 0.05D, vec1.zCoord);
		}
		
		player.swingItem();
		Block block = Blocks.reeds;
		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.func_150496_b()/*getStepSound*/, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

		if (world.isRemote)
		{
			return true;
		}

		world.setBlockToAir(x, y, z);
		is.damageItem(cropYield * 3, player);
		
		return true;
	}

	@Override
	public boolean func_150897_b(Block block) /* canHarvestBlock */
	{
		return false;
	}

	@Override
	public int getItemEnchantability()
	{
		return this.toolMaterial.getEnchantability();
	}

	@Override
	public boolean getIsRepairable(ItemStack is1, ItemStack is2)
	{
		if ((this.soulReaperNum == 0) || (this.soulReaperNum == 1))
		{
			return this.toolMaterial.customCraftingMaterial == is2.getItem() ? true : super.getIsRepairable(is1, is2);
		}
		
		return false;
	}
}
