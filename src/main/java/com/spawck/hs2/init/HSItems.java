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
 * File created @[Mar 5, 2015, 10:19:09 AM]
 */
package com.spawck.hs2.init;

import net.minecraft.item.Item;

import com.spawck.hs2.item.ItemKeeper;
import com.spawck.hs2.item.ItemScythe;
import com.spawck.hs2.reference.Materials;
import com.spawck.hs2.reference.Names;
import com.spawck.hs2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class HSItems
{
	public static final ItemKeeper essenceKeeper = new ItemKeeper(0, "Empty");
	public static final ItemKeeper soulKeeper = new ItemKeeper(1, "Souls");
	public static final ItemKeeper bloodKeeper = new ItemKeeper(2, "Blood");
	public static final ItemKeeper essenceVessel = new ItemKeeper(3, "Empty");
	public static final ItemKeeper soulVessel = new ItemKeeper(4, "Souls");
	public static final ItemKeeper bloodVessel = new ItemKeeper(5, "Blood");
	
	
	public static final ItemScythe woodScythe = new ItemScythe(Item.ToolMaterial.WOOD, 0);
	public static final ItemScythe stoneScythe = new ItemScythe(Item.ToolMaterial.STONE, 0);
	public static final ItemScythe ironScythe = new ItemScythe(Item.ToolMaterial.IRON, 0);
	public static final ItemScythe goldScythe = new ItemScythe(Item.ToolMaterial.GOLD, 0);
	public static final ItemScythe diamondScythe = new ItemScythe(Item.ToolMaterial.EMERALD, 0);
	public static final ItemScythe livingmetalScythe = new ItemScythe(Materials.Tools.LIVINGMETAL, 0); 
	public static final ItemScythe biomassScythe = new ItemScythe(Materials.Tools.BIOMASS, 0);
	public static final ItemScythe soulReaperScythe = new ItemScythe(Materials.Tools.LIVINGMETAL, 1);
	
	/*
	 * public static final String ESSENCE_KEEPER = "essenceKeeper";
		public static final String SOUL_KEEPER = "soulKeeper";
		public static final String BLOOD_KEEPER = "bloodKeeper";
		public static final String ESSENCE_VESSEL = "essenceVessel";
		public static final String SOUL_VESSEL = "soulVessel";
		public static final String BLOOD_VESSEL = "bloodVessel";
		public static final String SHADOW_BOOK = "shadowBook";
		public static final String CARNAGE_BOOK = "carnageBook";
		public static final String NECRONOMICON = "necronomicon";
		public static final String NECRONOMICON_PAGE = "necronomiconPage";
		public static final String SKULL = "skull";
		public static final String TALISMAN = "talisman";
		public static final String ETHEREAL_TALISMAN = "etherealTalisman";
		public static final String OVERWORLD_AMULET = "overworldAmulet";
		public static final String NETHER_AMULET = "netherAmulet";
		public static final String ETHEREAL_AMULET = "etherealAmulet";
		public static final String LIVINGMETAL_INGOT = "livingmetalIngot";
		public static final String BIOMASS = "biomass";
		public static final String ECTOPLASM = "ectoplasm";
		public static final String ABYSS_FRAGMENT = "abyssFragment";
		public static final String SPECTRAL_DYE = "spectralDye";
		public static final String CREEP_BALL = "creepBall";
		public static final String BIOMASS_SEED = "biomassSeed";
		public static final String BIOMASS_SEED_GERMINATED = "biomassSeedGerminated";
		public static final String SOUL_CRUCIBLE = "itemCrucibleSoul";
		public static final String BLOOD_CRUCIBLE = "itemBloodCrucible";
		public static final String LUNAR_DIAL = "lunarDial";
	 */
	
	
	public static void init()
	{
		GameRegistry.registerItem(woodScythe, Names.Weapons.WOOD_SCYTHE);
		GameRegistry.registerItem(stoneScythe, Names.Weapons.STONE_SCYTHE);
		GameRegistry.registerItem(ironScythe, Names.Weapons.IRON_SCYTHE);
		GameRegistry.registerItem(goldScythe, Names.Weapons.GOLD_SCYTHE);
		GameRegistry.registerItem(diamondScythe, Names.Weapons.DIAMOND_SCYTHE);
		GameRegistry.registerItem(livingmetalScythe, Names.Weapons.LIVINGMETAL_SCYTHE);
		GameRegistry.registerItem(biomassScythe, Names.Weapons.BIOMASS_SCYTHE);
		GameRegistry.registerItem(soulReaperScythe, Names.Weapons.SOUL_REAPER_SCYTHE);
	}
}
