package com.tomasajt.skyblock_helper;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class SHKeybinds {
	public static List<KeyBinding> keyBindings = new ArrayList<>();
	public static KeyBinding keyBindingScan = new KeyBinding("key.sh.scan",
			KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_KP_DIVIDE, "key.categories.sh");
	static {
		keyBindings.add(keyBindingScan);
	}
	@SubscribeEvent
	public static void onClientSetupEvent(FMLClientSetupEvent event) {
		for (KeyBinding keyBinding : keyBindings) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}
}
