package net.gudenau.minecraft.gudutils.utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Optional;
import net.gudenau.minecraft.gudutils.accessor.DyeColorAccessor;
import net.minecraft.util.DyeColor;

public final class ColorUtils{
    private ColorUtils(){}
    
    private static final Int2ObjectMap<DyeColor> DYE_COLORS = new Int2ObjectOpenHashMap<>();
    static{
        for(var value : DyeColor.values()){
            DYE_COLORS.put(value.getColor(), value);
        }
    }
    
    public static Optional<DyeColor> getDye(int color){
        return Optional.ofNullable(DYE_COLORS.get(color));
    }
}
