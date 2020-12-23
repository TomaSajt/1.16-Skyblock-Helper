package com.tomasajt.skyblock_helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(SkyblockHelper.MOD_ID)
public class SkyblockHelper {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "skyblock_helper";

	public SkyblockHelper() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void sendMessage(Object message) {
		Minecraft mc = Minecraft.getInstance();
		mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(message.toString()));
	}
	
}
