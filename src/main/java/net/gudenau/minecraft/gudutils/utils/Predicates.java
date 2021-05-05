package net.gudenau.minecraft.gudutils.utils;

public final class Predicates{
    private Predicates(){}
    
    public static <A, B, C, D> boolean never(A a, B b, C c, D d){
        return false;
    }
}
