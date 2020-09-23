package com.creativemd.littletiles.client.render.cache;

import java.nio.ByteBuffer;

import com.creativemd.creativecore.client.rendering.model.BufferBuilderUtils;

import net.minecraft.client.renderer.BufferBuilder;

public class BufferHolder {
	
	public static final Object BUFFER_CHANGE_LOCK = new Object();
	
	public final int length;
	public final int vertexCount;
	public final LayeredRenderBufferCache parent;
	public final int layer;
	
	private ChunkBlockLayerManager manager;
	private ByteBuffer byteBuffer;
	private int index = -1;
	private boolean removed = false;
	
	public BufferHolder(LayeredRenderBufferCache parent, int layer, BufferBuilder buffer) {
		this.parent = parent;
		this.layer = layer;
		this.length = BufferBuilderUtils.getBufferSizeByte(buffer);
		this.vertexCount = buffer.getVertexCount();
		this.byteBuffer = buffer.getByteBuffer();
	}
	
	public BufferHolder(LayeredRenderBufferCache parent, int layer, ByteBuffer buffer, int byteSize, int count) {
		this.parent = parent;
		this.layer = layer;
		this.length = byteSize;
		this.vertexCount = count;
		this.byteBuffer = buffer;
	}
	
	public ChunkBlockLayerManager getManager() {
		return manager;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void add(BufferBuilder builder) {
		int index = BufferBuilderUtils.getBufferSizeByte(builder);
		if (!hasBufferInRAM())
			manager.backToRAM();
		this.index = index;
		BufferBuilderUtils.addBuffer(builder, getBuffer(), length, vertexCount);
	}
	
	public boolean hasBufferInRAM() {
		return byteBuffer != null;
	}
	
	public void useVRAM(ChunkBlockLayerManager manager) {
		this.manager = manager;
		this.byteBuffer = null;
	}
	
	public void useRAM(ByteBuffer buffer) {
		this.byteBuffer = buffer;
		this.index = -1;
		this.manager = null;
	}
	
	public ByteBuffer getBuffer() {
		if (byteBuffer != null)
			return byteBuffer;
		if (index != -1) {
			manager.backToRAM();
			return byteBuffer;
		}
		throw new IllegalStateException("Index of VRAM buffer is not set!");
	}
	
	public void backToRAM() {
		if (manager != null)
			manager.backToRAM();
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void remove() {
		parent.remove(layer);
	}
	
	public void onRemoved() {
		byteBuffer = null;
		manager = null;
		index = -1;
		removed = true;
	}
}
