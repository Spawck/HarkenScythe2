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
 * File created @[Mar 5, 2015, 9:56:11 AM]
 */
package com.spawck.hs2.util;

import org.apache.logging.log4j.Level;

import com.spawck.hs2.reference.Reference;

import cpw.mods.fml.common.FMLLog;

/**
 * A log class to simplify HS2 console messages.
 * 
 * @author spawck
 * Email: spawck@autistici.org
 *
 */
public class LogHS
{
	public static void log(Level level, Object object)
	{
		FMLLog.log(Reference.NAME, level, String.valueOf(object));
	}
	
	public static void all(Object object)
	{
		log(Level.ALL, object);
	}
	
	public static void debug(Object object)
	{
		log(Level.DEBUG, object);
	}
	
	public static void error(Object object)
	{
		log(Level.ERROR, object);
	}
	
	public static void fatal(Object object)
	{
		log(Level.FATAL, object);
	}
	
	public static void info(Object object)
	{
		log(Level.INFO, object);
	}
	
	public static void off(Object object)
	{
		log(Level.OFF, object);
	}
	
	public static void trace(Object object)
	{
		log(Level.TRACE, object);
	}
	
	public static void warn(Object object)
	{
		log(Level.WARN, object);
	}
}
