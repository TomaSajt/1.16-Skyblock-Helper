package com.tomasajt.skyblock_helper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SkyblockHelper.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class DisablePlacement {

	@SubscribeEvent
	public static void onPlayerBlockPlaced(PlayerInteractEvent.RightClickBlock event) {
		if (event.getSide() == LogicalSide.CLIENT) {
			ItemStack itemStack = event.getItemStack();
			String name = itemStack.getDisplayName().getString();
			if (/**/name.startsWith("Personal Compactor") || /**/
					name.startsWith("Enchanted")) {
				event.setCanceled(true);
			}
		}
	}
}
