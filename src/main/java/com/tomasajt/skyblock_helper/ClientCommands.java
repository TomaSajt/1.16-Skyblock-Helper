package com.tomasajt.skyblock_helper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ClientCommands {

	private static Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent
	public static void onClientChatEvent(ClientChatEvent event) {
		String message = event.getOriginalMessage();
		if (message.startsWith("sb!")) {
			switch (message) {
			case "sb!stem":
				StemProtection.isOn = !StemProtection.isOn;
				SkyblockHelper.msg("\u00a7bStem Protection \u00a76" + (StemProtection.isOn ? "[ON]" : "[OFF]"));
				break;
			case "sb!chrono":
				ChronomatronHelper.isOn = !ChronomatronHelper.isOn;
				SkyblockHelper.msg("\u00a7bAutomatic Chronomatron \u00a76" + (ChronomatronHelper.isOn ? "[ON]" : "[OFF]"));
				break;
			case "sb!harp":
				HarpHelper.isOn = !HarpHelper.isOn;
				SkyblockHelper.msg("\u00a7bAutomatic Harp \u00a76" + (HarpHelper.isOn ? "[ON]" : "[OFF]"));
				break;
			case "sb!titanium":
				TitaniumScanner.isOn = !TitaniumScanner.isOn;
				SkyblockHelper.msg("\u00a7bTitanium Scanner \u00a76" + (TitaniumScanner.isOn ? "[ON]" : "[OFF]"));
				break;
			case "sb!fairy":
				FairSoulFinder.isOn = !FairSoulFinder.isOn;
				SkyblockHelper.msg("\u00a7bFairy Soul Finder \u00a76" + (FairSoulFinder.isOn ? "[ON]" : "[OFF]"));
				break;
			default:
				SkyblockHelper.msg("This Skyblock Helper command doesn't exist");
				break;
			}
			event.setCanceled(true);
			mc.ingameGUI.getChatGUI().addToSentMessages(message);
		}
	}
}
