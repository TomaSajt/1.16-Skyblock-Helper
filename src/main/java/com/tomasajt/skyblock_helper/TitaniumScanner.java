package com.tomasajt.skyblock_helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class TitaniumScanner {

	static Minecraft mc = Minecraft.getInstance();
	static Set<BlockPos> titan = new HashSet<>();
	public static boolean isOn = true;
	private static boolean wasOn = isOn;

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event) {
		if (isOn) {
			IChunk c = event.getChunk();
			ChunkPos pos = c.getPos();
			for (int xx = 0; xx < 16; xx++) {
				for (int zz = 0; zz < 16; zz++) {
					for (int yy = 0; yy < 256; yy++) {
						Block block = c.getBlockState(new BlockPos(xx, yy, zz)).getBlock();
						if (block.getBlock() == Blocks.POLISHED_DIORITE) {
							titan.add(new BlockPos(pos.x * 16 + xx, yy, pos.z * 16 + zz));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event) {
		if (event.phase == Phase.END) {
			if (wasOn && !isOn) {
				titan.clear();
			}
			if (!wasOn && isOn) {
				rescanWorld();
			}
			if (isOn && mc.world != null ) {

				titan.removeIf(blockPos -> {
					boolean remove = false;
					if (mc.world.getChunkProvider().isChunkLoaded(new ChunkPos(blockPos))) {
						if (mc.world.getBlockState(blockPos).getBlock() != Blocks.POLISHED_DIORITE) {
							remove = true;
						}
					} else {
						remove = true;
					}
					return remove;
				});

				if (SHKeybinds.keyBindingScan.isPressed()) {
					rescanWorld();
				}

			}
			wasOn = isOn;
		}

	}
	
	private static void rescanWorld() {
		titan.clear();
		ClientPlayerEntity player = mc.player;
		BlockPos playerPos = player.getPosition();
		int playerChunkPosX = playerPos.getX() >> 4;
		int playerChunkPosZ = playerPos.getZ() >> 4;
		ClientChunkProvider provider = mc.world.getChunkProvider();
		List<Chunk> chunks = new ArrayList<>();
		int renderDistance = mc.gameSettings.renderDistanceChunks;
		for (int i = -renderDistance; i <= renderDistance; i++) {
			for (int j = -renderDistance; j <= renderDistance; j++) {
				Chunk asd = provider.getChunkNow(playerChunkPosX + i, playerChunkPosZ + j);
				if (asd != null) {
					chunks.add(asd);
				}
			}
		}
		for (int currentChunk = 0; currentChunk < chunks.size(); currentChunk++) {
			Chunk c = chunks.get(currentChunk);
			ChunkPos pos = c.getPos();
			for (int xx = 0; xx < 16; xx++) {
				for (int zz = 0; zz < 16; zz++) {
					for (int yy = 0; yy < 256; yy++) {
						Block block = c.getBlockState(new BlockPos(xx, yy, zz)).getBlock();
						if (block.getBlock() == Blocks.POLISHED_DIORITE) {
							titan.add(new BlockPos(pos.x * 16 + xx, yy, pos.z * 16 + zz));
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onWorldRender(RenderWorldLastEvent event) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.disableLighting();
		RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		RenderSystem.color4f(1, 1, 1, 0.5f);
		RenderSystem.lineWidth(2);
		RenderSystem.disableTexture();
		RenderSystem.depthMask(false);
		RenderSystem.disableDepthTest();
		MatrixStack matrixStack = event.getMatrixStack();
		for (BlockPos blockPos : titan) {
			matrixStack.push();
			Vector3d camPos = mc.getRenderManager().info.getProjectedView();
			matrixStack.translate(-camPos.x, -camPos.y, -camPos.z);
			Matrix4f matrix4f = matrixStack.getLast().getMatrix();
			float minX = (float) blockPos.getX();
			float minY = (float) blockPos.getY();
			float minZ = (float) blockPos.getZ();
			float maxX = (float) blockPos.getX() + 1;
			float maxY = (float) blockPos.getY() + 1;
			float maxZ = (float) blockPos.getZ() + 1;
			buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
			buffer.pos(matrix4f, minX, minY, minZ).endVertex();
			buffer.pos(matrix4f, minX, minY, maxZ).endVertex();
			buffer.pos(matrix4f, minX, minY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, minY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, minY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, minY, minZ).endVertex();
			buffer.pos(matrix4f, maxX, minY, minZ).endVertex();
			buffer.pos(matrix4f, minX, minY, minZ).endVertex();
			buffer.pos(matrix4f, minX, maxY, minZ).endVertex();
			buffer.pos(matrix4f, minX, maxY, maxZ).endVertex();
			buffer.pos(matrix4f, minX, maxY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, maxY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, maxY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, maxY, minZ).endVertex();
			buffer.pos(matrix4f, maxX, maxY, minZ).endVertex();
			buffer.pos(matrix4f, minX, maxY, minZ).endVertex();
			buffer.pos(matrix4f, minX, minY, minZ).endVertex();
			buffer.pos(matrix4f, minX, maxY, minZ).endVertex();
			buffer.pos(matrix4f, minX, minY, maxZ).endVertex();
			buffer.pos(matrix4f, minX, maxY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, minY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, maxY, maxZ).endVertex();
			buffer.pos(matrix4f, maxX, minY, minZ).endVertex();
			buffer.pos(matrix4f, maxX, maxY, minZ).endVertex();
			tessellator.draw();
			matrixStack.pop();
		}
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.enableLighting();
		RenderSystem.disableBlend();

	}
}
