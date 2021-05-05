package net.gudenau.minecraft.gudutils.utils;

import java.util.Optional;
import java.util.function.Consumer;

public final class MiscUtil{
    private MiscUtil(){}
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<T> present, Runnable absent){
        if(optional.isPresent()){
            present.accept(optional.get());
        }else{
            absent.run();
        }
    }
}
