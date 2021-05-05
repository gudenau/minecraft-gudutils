package net.gudenau.minecraft.gudutils.impl;

import java.util.*;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamAction;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamRegistry;
import net.gudenau.minecraft.gudutils.flimflam.BrokenToolFlimFlam;
import net.gudenau.minecraft.gudutils.flimflam.ShuffleFilmFlam;
import net.minecraft.util.Identifier;

import static net.gudenau.minecraft.gudutils.GudUtils.MOD_ID;

public final class FlimFlamRegistryImpl implements FlimFlamRegistry{
    public static final FlimFlamRegistry INSTANCE = new FlimFlamRegistryImpl();
    
    private final List<FlimFlamAction> flimFlams = new ArrayList<>();
    private final List<FlimFlamAction> roFlimFlams = Collections.unmodifiableList(flimFlams);
    private final Map<Identifier, FlimFlamAction> nameActionMap = new HashMap<>();
    
    private FlimFlamRegistryImpl(){
        register("shuffle", new ShuffleFilmFlam());
        register("broken_tool", new BrokenToolFlimFlam());
    }
    
    private void register(String name, FlimFlamAction flimFlam){
        register(new Identifier(MOD_ID, name), flimFlam);
    }
    
    @Override
    public void register(Identifier name, FlimFlamAction flimFlam){
        if(nameActionMap.putIfAbsent(name, flimFlam) != null){
            throw new IllegalStateException("Failed to register Flim-Flam " + name + ", already registered");
        }
        flimFlams.add(flimFlam);
    }
    
    @Override
    public List<FlimFlamAction> getActions(){
        return roFlimFlams;
    }
    
    @Override
    public Optional<FlimFlamAction> getOrEmpty(Identifier identifier){
        return Optional.ofNullable(nameActionMap.get(identifier));
    }
    
    @Override
    public Set<Identifier> getIds(){
        return Collections.unmodifiableSet(nameActionMap.keySet());
    }
}
