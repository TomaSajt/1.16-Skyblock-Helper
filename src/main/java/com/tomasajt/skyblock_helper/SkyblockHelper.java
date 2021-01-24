package com.tomasajt.skyblock_helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.common.Mod;

@Mod(SkyblockHelper.MOD_ID)
public class SkyblockHelper {
	public static final String MOD_ID = "skyblock_helper";

	public static void msg(Object message) {
		Minecraft mc = Minecraft.getInstance();
		mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(message.toString()));
	}
}
