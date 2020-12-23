package com.tomasajt.skyblock_helper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SkyblockHelper.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientCommands {

	private static Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent
	public static void onClientChatEvent(ClientChatEvent event) {
		String message = event.getOriginalMessage();
		if (message.equals("sb!stem")) {
			event.setCanceled(true);
			mc.ingameGUI.getChatGUI().addToSentMessages(message);
			StemProtection.isActive = !StemProtection.isActive;
			SkyblockHelper.sendMessage("\u00a7bStem Protection \u00a76" + (StemProtection.isActive ? "[ON]" : "[OFF]"));

		}
	}
}
