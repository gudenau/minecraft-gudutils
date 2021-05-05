package net.gudenau.minecraft.gudutils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.gudenau.minecraft.gudutils.block.entity.ElevatorBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import static net.gudenau.minecraft.gudutils.GudUtils.MOD_ID;
import static net.minecraft.client.render.VertexFormats.POSITION_COLOR_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_QUADS;

@Environment(EnvType.CLIENT)
public final class GudUtilsClient implements ClientModInitializer{
    @Override
    public void onInitializeClient(){
        initBlockColors();
        RenderLayers.init();
    }
    
    private void initBlockColors(){
        var registry = ColorProviderRegistry.BLOCK;
        GudUtils.Blocks.ELEVATOR.ifPresent((value)->registry.register((state, world, pos, tintIndex)->{
            var entity = world == null ? null : world.getBlockEntity(pos);
            if(entity instanceof ElevatorBlockEntity){
                return ((ElevatorBlockEntity)entity).getColor() | 0xFF_00_00_00;
            }
            return -1;
        }, value));
    }
    
    public static final class RenderLayers{
        private static final Identifier COLORED_ITEM_GLINT = new Identifier(MOD_ID, "textures/misc/colored_item_glint.png");
        
        public static final RenderLayer COLORED_ARMOR_GLINT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_armor_glint", POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .texturing(RenderPhase.getGlintTexturing())
                    .layering(RenderPhase.getViewOffsetZLayering())
                    .build(false)), null
        );
        public static final RenderLayer COLORED_ARMOR_ENTITY_GLINT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_armor_entity_glint", VertexFormats.POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .texturing(RenderPhase.getEntityGlintTexturing())
                    .layering(RenderPhase.getViewOffsetZLayering())
                    .build(false)), null
        );
        public static final RenderLayer COLORED_GLINT_TRANSLUCENT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_glint_translucent", POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(
                    new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .texturing(RenderPhase.getGlintTexturing())
                    .target(RenderPhase.getItemTarget())
                    .build(false)), null
        );
        public static final RenderLayer COLORED_GLINT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_glint", POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(
                    new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .texturing(RenderPhase.getGlintTexturing())
                    .build(false)), null
        );
        public static final RenderLayer COLORED_DIRECT_GLINT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_glint_direct", VertexFormats.POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(
                    new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .texturing(RenderPhase.getGlintTexturing())
                    .build(false)), null
        );
        public static final RenderLayer COLORED_ENTITY_GLINT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_entity_glint", VertexFormats.POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(
                    new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .target(RenderPhase.getItemTarget())
                    .texturing(RenderPhase.getEntityGlintTexturing())
                    .build(false)), null
        );
        public static final RenderLayer COLORED_DIRECT_ENTITY_GLINT = Configuration.COLOR_RUNE_ENABLE.ifElse(()->
            RenderLayer.of("gud_utils_colored_entity_glint_direct", VertexFormats.POSITION_COLOR_TEXTURE, GL_QUADS, 256,
                RenderLayer.MultiPhaseParameters.builder().texture(
                    new RenderPhase.Texture(COLORED_ITEM_GLINT, true, false))
                    .writeMaskState(RenderPhase.getColorMask())
                    .cull(RenderPhase.getDisableCulling())
                    .depthTest(RenderPhase.getEqualDepthTest())
                    .transparency(RenderPhase.getGlintTransparency())
                    .texturing(RenderPhase.getEntityGlintTexturing())
                    .build(false)), null
        );
        /*
        GLINT_TRANSLUCENT = of("glint_translucent", VertexFormats.POSITION_TEXTURE, 7, 256, RenderLayer.MultiPhaseParameters.builder().texture(new Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(ITEM_TARGET).build(false));
        GLINT = of("glint", VertexFormats.POSITION_TEXTURE, 7, 256, RenderLayer.MultiPhaseParameters.builder().texture(new Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
        DIRECT_GLINT = of("glint_direct", VertexFormats.POSITION_TEXTURE, 7, 256, RenderLayer.MultiPhaseParameters.builder().texture(new Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
        ENTITY_GLINT = of("entity_glint", VertexFormats.POSITION_TEXTURE, 7, 256, RenderLayer.MultiPhaseParameters.builder().texture(new Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).target(ITEM_TARGET).texturing(ENTITY_GLINT_TEXTURING).build(false));
        DIRECT_ENTITY_GLINT = of("entity_glint_direct", VertexFormats.POSITION_TEXTURE, 7, 256, RenderLayer.MultiPhaseParameters.builder().texture(new Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(false));
         */
    
        private static void init(){
        
        }
    }
}
