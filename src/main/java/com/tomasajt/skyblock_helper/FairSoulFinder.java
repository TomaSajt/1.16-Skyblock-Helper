package com.tomasajt.skyblock_helper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FairSoulFinder {

	static Minecraft mc = Minecraft.getInstance();
	public static boolean isOn = false;
	private static boolean wasOn = isOn;

	@SubscribeEvent
	public static void onLivingTick(ClientTickEvent event) {
		if (event.phase == Phase.START && mc.world != null) {
			if (wasOn && !isOn) {
				for (Entity entity : mc.world.getAllEntities()) {
					if (entity instanceof ArmorStandEntity) {
						entity.setGlowing(false);
					}
				}
			}
			if (isOn) {
				for (Entity entity : mc.world.getAllEntities()) {
					if (entity instanceof ArmorStandEntity) {
						entity.setGlowing(true);
					}
				}
			}
			wasOn = isOn;
		}
		
	}
}
