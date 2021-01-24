package com.tomasajt.skyblock_helper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ChronomatronHelper {

	private static Minecraft mc = Minecraft.getInstance();
	private static boolean listening = true;
	private static List<Integer> indexes = new ArrayList<>();
	private static Screen lastScreen = null;
	private static int cooldown = 0;
	private static int i = 0;
	private static final int waitTime = 10;
	public static boolean isOn = false;

	@SubscribeEvent
	public static void onClientTickEvent(ClientTickEvent event) {
		if (event.phase == Phase.END) {
			Screen screen = mc.currentScreen;
			if (lastScreen != screen) {
				resetValues();
			}
			lastScreen = screen;
			if (screen instanceof ChestScreen) {
				ChestScreen chestScreen = (ChestScreen) screen;
				ChestContainer chestContainer = chestScreen.getContainer();
				chestContainerTickEvent(chestScreen, chestContainer);
			}
		}

	}

	private static void resetValues() {
		listening = true;
		indexes.clear();
		cooldown = 0;
		i = 0;
	}

	private static void chestContainerTickEvent(ChestScreen chestScreen, ChestContainer chestContainer) {
		if (chestScreen.getTitle().getString().startsWith("Chronomatron (")) {
			NonNullList<ItemStack> inv = chestContainer.getInventory();

			if (listening) {
				if (inv.get(49).getItem() == Items.CLOCK) {
					for (int i = 9; i < 18; i++) {
						if (isTerracotta(inv.get(i).getItem())) {
							indexes.add(i+9);
						}
					}
					for (int i = 36; i < 45; i++) {
						if (isTerracotta(inv.get(i).getItem())) {
							indexes.add(i-9);
						}
					}
					listening = false;
					cooldown = waitTime;

				}
			} else {
				if (inv.get(49).getItem() == Items.GLOWSTONE) {
					listening = true;
					cooldown = 0;
					i = 0;
				} else if (isOn) {
					if (cooldown == 0) {
						if (i < indexes.size()) {
							mc.playerController.windowClick(chestContainer.windowId, indexes.get(i), 0, ClickType.PICKUP,
									mc.player);
							cooldown += waitTime;
							i++;
						}
					}
					if (cooldown > 0) {
						cooldown--;
					}
				}
			}
		}
	}

	private static boolean isTerracotta(Item item) {
		return item == Items.TERRACOTTA || item == Items.BLACK_TERRACOTTA || item == Items.BLUE_TERRACOTTA
				|| item == Items.BROWN_TERRACOTTA || item == Items.CYAN_TERRACOTTA || item == Items.GRAY_TERRACOTTA
				|| item == Items.GREEN_TERRACOTTA || item == Items.LIGHT_BLUE_TERRACOTTA
				|| item == Items.LIGHT_GRAY_TERRACOTTA || item == Items.LIME_TERRACOTTA
				|| item == Items.MAGENTA_TERRACOTTA || item == Items.ORANGE_TERRACOTTA || item == Items.PINK_TERRACOTTA
				|| item == Items.PURPLE_TERRACOTTA || item == Items.RED_TERRACOTTA || item == Items.WHITE_TERRACOTTA
				|| item == Items.YELLOW_TERRACOTTA;
	}
}
