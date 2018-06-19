package com.creativemd.littletiles.client.tiles;

import com.creativemd.creativecore.client.rendering.RenderCubeObject;
import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.ColorUtils;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.littletiles.LittleTilesConfig;
import com.creativemd.littletiles.common.api.ILittleTile;
import com.creativemd.littletiles.common.tiles.vec.LittleTileBox;
import com.creativemd.littletiles.common.utils.grid.LittleGridContext;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LittleRenderingCube extends RenderCubeObject {
	
	public LittleTileBox box;
	
	public LittleRenderingCube(CubeObject cube, LittleTileBox box, Block block, int meta) {
		super(cube, block, meta);
		this.box = box;
	}
	
	public void renderCubeLines(double x, double y, double z, float red, float green, float blue, float alpha)
	{
		RenderGlobal.drawBoundingBox(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z, red, green, blue, alpha);
	}
	
	public void renderCubePreview(double x, double y, double z, ILittleTile iTile)
	{
		Vec3d size = getSize();
		double cubeX = x+minX+size.x/2D;
		
		double cubeY = y+minY+size.y/2D;
		
		double cubeZ = z+minZ+size.z/2D;
		
		Vec3d color = ColorUtils.IntToVec(this.color);
		RenderHelper3D.renderBlock(cubeX, cubeY, cubeZ, size.x, size.y, size.z, 0, 0, 0, color.x, color.y, color.z, (Math.sin(System.nanoTime()/200000000D)*0.2+0.5) * iTile.getPreviewAlphaFactor());
	}
}
