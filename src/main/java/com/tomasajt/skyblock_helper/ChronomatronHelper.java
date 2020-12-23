package com.tomasajt.skyblock_helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
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

@EventBusSubscriber(modid = SkyblockHelper.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ChronomatronHelper {

	private static Minecraft mc = Minecraft.getInstance();
	private static boolean listening = true;
	private static List<String> notes = new ArrayList<>();
	private static String text = "";
	private static Screen lastScreen = null;
	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT) {
			Screen screen = mc.currentScreen;
			if (lastScreen != screen) {
				resetValues();
			}
			lastScreen = screen;
			if (screen instanceof ContainerScreen<?>) {
				Container container = ((ContainerScreen<?>) screen).getContainer();
				ContainerScreen<?> containerScreen = (ContainerScreen<?>) screen;
				if (container instanceof ChestContainer) {
					chestContainerTickEvent(containerScreen, container);
				}
			}
		}
	}

	private static void resetValues() {
		listening = true;
		notes.clear();
		text = "";
	}

	private static void chestContainerTickEvent(ContainerScreen<?> containerScreen, Container container) {
		if (containerScreen.getTitle().getString().startsWith("Chronomatron (")) {
			NonNullList<ItemStack> inv = container.getInventory();
			if (listening) {
				if (inv.get(49).getItem() == Items.CLOCK) {
					/* */if (inv.get(12).getItem() == Items.RED_TERRACOTTA) {
						text += TextFormatting.RED;
						notes.add("Red");
					} else if (inv.get(13).getItem() == Items.BLUE_TERRACOTTA) {
						text += TextFormatting.BLUE;
						notes.add("Blue");
					} else if (inv.get(14).getItem() == Items.LIME_TERRACOTTA) {
						text += TextFormatting.GREEN; notes.add("Green");
					} else {
						text += TextFormatting.WHITE;
						notes.add("???");
					}
					text += "\u2588";
					listening = false;
				}
			} else {
				if (inv.get(49).getItem() == Items.GLOWSTONE) {
					listening = true;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onDrawScreenEvent(GuiScreenEvent.DrawScreenEvent.Post event) {
		mc.fontRenderer.drawString(event.getMatrixStack(), text, 30, 30, Color.WHITE.getRGB());
	}
}
