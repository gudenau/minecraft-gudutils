package net.gudenau.minecraft.gudutils.api.v0.wiki;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public interface EnchantmentInfo{
    Text getDescription(int level);
}
