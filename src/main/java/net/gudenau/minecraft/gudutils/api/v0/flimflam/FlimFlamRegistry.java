package net.gudenau.minecraft.gudutils.api.v0.flimflam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.gudenau.minecraft.gudutils.impl.FlimFlamRegistryImpl;
import net.minecraft.util.Identifier;

public interface FlimFlamRegistry{
    static FlimFlamRegistry getInstance(){
        return FlimFlamRegistryImpl.INSTANCE;
    }
    
    void register(Identifier name, FlimFlamAction flimFlam);
    List<FlimFlamAction> getActions();
    Optional<FlimFlamAction> getOrEmpty(Identifier identifier);
    Set<Identifier> getIds();
}
