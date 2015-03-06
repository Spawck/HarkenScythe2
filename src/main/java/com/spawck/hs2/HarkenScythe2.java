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
 * File created @[Mar 5, 2015, 9:46:20 AM]
 */
package com.spawck.hs2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.spawck.hs2.handler.ConfigurationHandler;
import com.spawck.hs2.proxy.CommonProxy;
import com.spawck.hs2.reference.Messages;
import com.spawck.hs2.reference.Reference;
import com.spawck.hs2.util.LogHS;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, certificateFingerprint = Reference.FINGERPRINT, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class HarkenScythe2
{
	@Instance(Reference.MOD_ID)
	public static HarkenScythe2 instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	public File directory;
	
	public static List<String> donnors = new ArrayList<String>();
	
	@EventHandler
	public void invalidFingerprint(FMLFingerprintViolationEvent event)
	{
		if (Reference.FINGERPRINT.equals("@FINGERPRINT@"))
		{
			LogHS.info(Messages.NO_FINGERPRINT_MESSAGE);
		}
		else
		{
			LogHS.warn(Messages.INVALID_FINGERPRINT_MESSAGE);
		}
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		//command registry
		// TODO: ItemAmulet needs help, particle effect util, sounds, ItemKeeper needs to have its unlocalizedNames changed, helper methods for inventory checks, 
		// rename unlocalized names in TierAbilities.class, fix entity textures, fix block/item textures/names, item meta
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//LanguageRegistry.instance().getStringLocalization("", lang);
		
		try
		{
			ConfigurationHandler.initialize(event.getModConfigurationDirectory());
		}
		catch (Exception e)
		{
			LogHS.fatal(Messages.NO_CONFIG);
		}
		finally
		{
			if (ConfigurationHandler.config != null)
			{
				ConfigurationHandler.save();
			}
		}
		
		// packet, proxy
		
		ConfigurationHandler.save();
		
		// gui, blocks, items
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		// tile entities, event handlers, recipes, fuel handlers?
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		final ModContainer container = FMLCommonHandler.instance().findContainerFor(this);
		LanguageRegistry.instance().loadLanguagesFor(container, Side.CLIENT);
		// mo' recipes?!
	}
	
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		// NBT save stuff
	}
}
