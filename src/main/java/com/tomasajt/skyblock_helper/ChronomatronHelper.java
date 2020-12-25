package com.tomasajt.skyblock_helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SH.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ChronomatronHelper {

	private static Minecraft mc = Minecraft.getInstance();
	private static boolean listening = true;
	private static List<String> colors = new ArrayList<>();
	private static String text = "";
	private static Screen lastScreen = null;
	private static int cooldown = 0;
	private static int i = 0;
	private static Random r = new Random();
	private static final int waitTime = 30;
	public static boolean isAuto = false;

	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT) {
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
		colors.clear();
		text = "";
		cooldown = 0;
		i = 0;
	}

	private static void chestContainerTickEvent(ChestScreen chestScreen, ChestContainer chestContainer) {
		if (chestScreen.getTitle().getString().startsWith("Chronomatron (")) {
			NonNullList<ItemStack> inv = chestContainer.getInventory();

			if (listening) {
				if (inv.get(49).getItem() == Items.CLOCK) {
					/*   */if (inv.get(12).getItem() == Items.RED_TERRACOTTA) {
						text += TextFormatting.RED;
						colors.add("Red");
					} else if (inv.get(13).getItem() == Items.BLUE_TERRACOTTA) {
						text += TextFormatting.BLUE;
						colors.add("Blue");
					} else if (inv.get(14).getItem() == Items.LIME_TERRACOTTA) {
						text += TextFormatting.GREEN;
						colors.add("Green");
					} else {
						text += TextFormatting.WHITE;
						colors.add(null);
					}
					text += "\u2588";
					listening = false;
					cooldown = waitTime;

				}
			} else {
				if (inv.get(49).getItem() == Items.GLOWSTONE) {
					listening = true;
					cooldown = 0;
					i = 0;
				} else if (isAuto) {
					if (cooldown == 0) {
						if (i < colors.size()) {
							int id;
							switch (colors.get(i)) {
							case "Red":
								id = 21;
								break;
							case "Blue":
								id = 22;
								break;
							case "Green":
								id = 23;
								break;
							default:
								id = r.nextInt(3) + 21;
								break;
							}
							mc.playerController.windowClick(chestContainer.windowId, id, 0, ClickType.PICKUP, mc.player);
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

	@SubscribeEvent
	public static void onDrawScreenEvent(GuiScreenEvent.DrawScreenEvent.Post event) {
		mc.fontRenderer.drawString(event.getMatrixStack(), text, 30, 30, Color.WHITE.getRGB());
	}
}
