package net.gudenau.minecraft.gudutils;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.gudenau.minecraft.gudutils.cca.FlimFlamComponent;
import net.minecraft.util.Identifier;

import static net.gudenau.minecraft.gudutils.GudUtils.MOD_ID;

public final class GudUtilsCCA implements EntityComponentInitializer{
    public static final ComponentKey<FlimFlamComponent> FLIM_FLAM = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "flim_flam"), FlimFlamComponent.class);
    
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry){
        registry.registerForPlayers(FLIM_FLAM, FlimFlamComponent.Impl::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
