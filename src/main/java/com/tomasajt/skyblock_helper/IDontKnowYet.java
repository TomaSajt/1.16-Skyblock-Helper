package com.tomasajt.skyblock_helper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SH.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class IDontKnowYet {
	
	
	@SubscribeEvent
	public static void bruh(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT) {
			SH.msg(event.player.getName());
		}
	}
}
