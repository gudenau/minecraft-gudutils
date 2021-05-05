package net.gudenau.minecraft.gudutils.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public final class OverrideVertexConsumers{
    private static final float BYTE_TO_FLOAT = 0.00390625F;
    
    private OverrideVertexConsumers(){}
    
    public static VertexConsumer color(VertexConsumer consumer, int color){
        return new Color(consumer, color);
    }
    
    private static final class Color implements VertexConsumer{
        private final VertexConsumer consumer;
        private final int color;
    
        public Color(VertexConsumer consumer, int color){
            this.consumer = consumer;
            this.color = color;
        }
    
        @Override
        public VertexConsumer vertex(double x, double y, double z){
            return consumer.vertex(x, y, z);
        }
    
        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha){
            return consumer.color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, color >> 24);
        }
    
        @Override
        public VertexConsumer texture(float u, float v){
            return consumer.texture(u, v);
        }
    
        @Override
        public VertexConsumer overlay(int u, int v){
            return consumer.overlay(u, v);
        }
    
        @Override
        public VertexConsumer light(int u, int v){
            return consumer.light(u, v);
        }
    
        @Override
        public VertexConsumer normal(float x, float y, float z){
            return consumer.normal(x, y, z);
        }
    
        @Override
        public void next(){
            consumer.next();
        }
        
        @Override
        public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ){
            consumer.vertex(x, y, z, ((color >> 16) & 0xFF) * BYTE_TO_FLOAT, ((color >> 8) & 0xFF) * BYTE_TO_FLOAT, (color & 0xFF) * BYTE_TO_FLOAT, (color >> 24) * BYTE_TO_FLOAT, u, v, overlay, light, normalX, normalY, normalZ);
        }
    
        @Override
        public VertexConsumer color(float red, float green, float blue, float alpha){
            return consumer.color(((color >> 16) & 0xFF) * BYTE_TO_FLOAT, ((color >> 8) & 0xFF) * BYTE_TO_FLOAT, (color & 0xFF) * BYTE_TO_FLOAT, (color >> 24) * BYTE_TO_FLOAT);
        }
    
        @Override
        public VertexConsumer light(int uv){
            return consumer.light(uv);
        }
    
        @Override
        public VertexConsumer overlay(int uv){
            return consumer.overlay(uv);
        }
    
        @Override
        public void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float red, float green, float blue, int light, int overlay){
            consumer.quad(matrixEntry, quad, ((color >> 16) & 0xFF) * BYTE_TO_FLOAT, ((color >> 8) & 0xFF) * BYTE_TO_FLOAT, (color & 0xFF) * BYTE_TO_FLOAT, light, overlay);
        }
    
        @Override
        public void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, boolean useQuadColorData){
            consumer.quad(matrixEntry, quad, brightnesses, ((color >> 16) & 0xFF) * BYTE_TO_FLOAT, ((color >> 8) & 0xFF) * BYTE_TO_FLOAT, (color & 0xFF) * BYTE_TO_FLOAT, lights, overlay, useQuadColorData);
        }
    
        @Override
        public VertexConsumer vertex(Matrix4f matrix, float x, float y, float z){
            return consumer.vertex(matrix, x, y, z);
        }
    
        @Override
        public VertexConsumer normal(Matrix3f matrix, float x, float y, float z){
            return consumer.normal(matrix, x, y, z);
        }
    }
}
