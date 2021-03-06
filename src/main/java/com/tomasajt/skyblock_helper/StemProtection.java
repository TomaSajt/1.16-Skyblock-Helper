package com.tomasajt.skyblock_helper;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class StemProtection {

	private static Minecraft mc = Minecraft.getInstance();
	public static boolean isOn = true;
	private static int cooldown = 0;

	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
	}

	@SubscribeEvent
	public static void onClickInputEvent(InputEvent.ClickInputEvent event) {
		if (event.getKeyBinding() == mc.gameSettings.keyBindAttack) {
			if (isOn) {
				Block block = mc.world.getBlockState(new BlockPos(mc.objectMouseOver.getHitVec())).getBlock();
				if (/**/block == Blocks.PUMPKIN_STEM || /**/
						block == Blocks.ATTACHED_PUMPKIN_STEM || /**/
						block == Blocks.MELON_STEM || /**/
						block == Blocks.ATTACHED_MELON_STEM) {
					event.setCanceled(true);
					if (cooldown == 0) {
						cooldown += 20;
						SkyblockHelper.msg("Cannot break stems while Stem Protection is ON");
					}
				}
			}
		}
	}
}
