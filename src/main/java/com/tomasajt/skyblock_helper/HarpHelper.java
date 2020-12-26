package com.tomasajt.skyblock_helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SH.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class HarpHelper {

	private static Minecraft mc = Minecraft.getInstance();
	public static boolean isAuto = false;
	public static Item[] prevItems = new Item[36];

	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT && isAuto) {
			Screen screen = mc.currentScreen;
			if (screen instanceof ChestScreen) {
				ChestScreen chestScreen = (ChestScreen) screen;
				ChestContainer chestContainer = chestScreen.getContainer();
				chestContainerTickEvent(chestScreen, chestContainer);
			}
		}

	}

	private static void chestContainerTickEvent(ChestScreen chestScreen, ChestContainer chestContainer) {
		if (chestScreen.getTitle().getString().startsWith("Harp -")) {
			NonNullList<ItemStack> inv = chestContainer.getInventory();
			if (haveLinesChanged(inv)) {
				if (prevItems[28] == Items.PINK_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 37, 0, ClickType.PICKUP, mc.player);
				} else if (prevItems[29] == Items.YELLOW_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 38, 0, ClickType.PICKUP, mc.player);
				} else if (prevItems[30] == Items.LIME_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 39, 0, ClickType.PICKUP, mc.player);
				} else if (prevItems[31] == Items.GREEN_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 40, 0, ClickType.PICKUP, mc.player);
				} else if (prevItems[32] == Items.PURPLE_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 41, 0, ClickType.PICKUP, mc.player);
				} else if (prevItems[33] == Items.BLUE_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 42, 0, ClickType.PICKUP, mc.player);
				} else if (prevItems[34] == Items.LIGHT_BLUE_WOOL) {
					mc.playerController.windowClick(chestContainer.windowId, 43, 0, ClickType.PICKUP, mc.player);
				}
			}
			setPrevItems(inv);
		}
	}

	private static void setPrevItems(NonNullList<ItemStack> inv) {
		for (int i = 0; i < prevItems.length; i++) {
			prevItems[i] = inv.get(i).getItem();
		}
	}

	private static boolean haveLinesChanged(NonNullList<ItemStack> inv) {
		for (int i = 0; i < prevItems.length; i++) {
			if (prevItems[i] != inv.get(i).getItem()) {
				return true;
			}
		}
		return false;
	}
}
