package com.tomasajt.skyblock_helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(SH.MOD_ID)
public class SH {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "skyblock_helper";

	public SH() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	public static void msg(Object message) {
		Minecraft mc = Minecraft.getInstance();
		mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(message.toString()));
	}
}
