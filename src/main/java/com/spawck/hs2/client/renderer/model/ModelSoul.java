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
 * File created @[Mar 5, 2015, 10:32:08 AM]
 */
package com.spawck.hs2.client.renderer.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
@SideOnly(Side.CLIENT)
public class ModelSoul extends ModelBase
{
	private ModelRenderer cube;
	private ModelRenderer glass = new ModelRenderer(this, "glass");
	private ModelRenderer base;
	private Minecraft mc;

	public ModelSoul()
	{
		this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		this.cube = new ModelRenderer(this, "cube");
		this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, -0.5F, 0.0F);

		if (this.base != null)
		{
			this.base.render(par7);
		}

		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.8F + par4, 0.0F);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		this.glass.render(par7);
		float var8 = 0.875F;
		GL11.glScalef(var8, var8, var8);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
		this.glass.render(par7);
		GL11.glScalef(var8, var8, var8);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
		this.cube.render(par7);
		GL11.glDepthFunc(514);
		GL11.glDisable(2896);
		GL11.glEnable(3042);
		GL11.glBlendFunc(768, 1);
		float var14 = 0.76F;
		GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
		GL11.glMatrixMode(5890);
		GL11.glPushMatrix();
		float var15 = 0.125F;
		GL11.glScalef(var15, var15, var15);
		float var16 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
		GL11.glTranslatef(var16, 0.0F, 0.0F);
		GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
		this.cube.render(par7);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(var15, var15, var15);
		var16 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
		GL11.glTranslatef(-var16, 0.0F, 0.0F);
		GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
		this.cube.render(par7);
		GL11.glPopMatrix();
		GL11.glMatrixMode(5888);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glDepthFunc(515);
		GL11.glPopMatrix();
	}
}
