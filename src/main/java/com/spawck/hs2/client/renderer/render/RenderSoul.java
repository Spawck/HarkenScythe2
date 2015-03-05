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
 * File created @[Mar 5, 2015, 10:32:58 AM]
 */
package com.spawck.hs2.client.renderer.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.spawck.hs2.client.renderer.model.ModelSoul;
import com.spawck.hs2.entity.EntitySoul;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
@SideOnly(Side.CLIENT)
public class RenderSoul extends Render
{
	private int field_76996_a = -1;
	private ModelBase soulModel;

	public RenderSoul()
	{
		this.shadowSize = 0.3F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}

	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderSoulCube((EntitySoul)entity, par2, par4, par6, par8, par9);
	}
	
	private void doRenderSoulCube(EntitySoul soul, double par2, double par4, double par6, float par8, float par9)
	{
		if (this.field_76996_a != 1)
		{
			this.soulModel = new ModelSoul(); // 0.0F, true removed (were they really necessary? They were never called)
			this.field_76996_a = 1;
		}

		float var10 = soul.innerRotation + par9;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		// TODO:loadTexture(par1EntityHSSoul.getSoulTexture());
		float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
		var11 += var11 * var11;
		this.soulModel.render(soul, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
