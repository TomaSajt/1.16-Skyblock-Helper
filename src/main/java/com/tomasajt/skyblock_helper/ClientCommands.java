package com.tomasajt.skyblock_helper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SH.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientCommands {

	private static Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent
	public static void onClientChatEvent(ClientChatEvent event) {
		String message = event.getOriginalMessage();
		if (message.startsWith("sb!")) {
			switch (message) {
			case "sb!stem":
				StemProtection.isActive = !StemProtection.isActive;
				SH.msg("\u00a7bStem Protection \u00a76" + (StemProtection.isActive ? "[ON]" : "[OFF]"));
				break;
			case "sb!chrono":
				ChronomatronHelper.isAuto = !ChronomatronHelper.isAuto;
				SH.msg("\u00a7bAutomatic Chronomatron \u00a76" + (ChronomatronHelper.isAuto ? "[ON]" : "[OFF]"));
				break;
			case "sb!harp":
				HarpHelper.isAuto = !HarpHelper.isAuto;
				SH.msg("\u00a7bAutomatic Harp \u00a76" + (HarpHelper.isAuto ? "[ON]" : "[OFF]"));
				break;
			default:
				SH.msg("This Skyblock Helper command doesn't exist");
				break;
			}
			event.setCanceled(true);
			mc.ingameGUI.getChatGUI().addToSentMessages(message);
		}
	}
}
