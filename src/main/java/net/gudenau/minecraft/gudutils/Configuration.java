package net.gudenau.minecraft.gudutils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.loader.api.FabricLoader;

public final class Configuration{
    private Configuration(){}
    
    private static final Set<Config<?>> CONFIG_SET = new HashSet<>();
    
    public static final BooleanConfig ELEVATOR_ENABLE = newConfig("elevator.enable", true, "Whether or not elevators should be enabled.");
    public static final Config<Integer> ELEVATOR_DISTANCE = newConfig("elevator.distance", 16, "The maximum amount of air blocks between elevators before they no longer link.");
    public static final BooleanConfig ELEVATOR_COLOR_MATCH = newConfig("elevator.colorMatch", true, "Makes it so only elevators of the same color will link.");
    
    public static final BooleanConfig ENCHANTMENT_INFO = newConfig("enchantmentInfo.enable", true, "Enable the tooltip with info on gudUtils' enchantments.");
    
    public static final BooleanConfig COLOR_RUNE_ENABLE = newConfig("colorRune.enable", true, "Enable the \"color rune\" items.");
    
    public static final BooleanConfig ATTRACTION_ENABLE = newConfig("attraction.enable", true, "Whether or not the \"attraction\" curse is enabled.");
    public static final BooleanConfig BREAKING_ENABLE = newConfig("breaking.enable", true, "Whether or not the \"breaking\" curse is enabled.");
    public static final BooleanConfig FLIGHT_ENABLE = newConfig("flight.enable", true, "Whether or not the \"flight\" enchantment is enabled.");
    public static final BooleanConfig FLIM_FLAM_ENABLE = newConfig("flimfFam.enable", true, "Whether or not Flim-Flam is enabled.");
    public static final BooleanConfig ICE_ENABLE = newConfig("ice.enable", true, "Whether or not \"ice\" curse is enabled.");
    public static final BooleanConfig SWIFT_STRIKE_ENABLE = newConfig("swiftStrike.enable", true, "Whether or not the \"Swift Strike\" enchantment is enabled.");
    public static final BooleanConfig SWIFTNESS_ENABLE = newConfig("swiftness.enable", true, "Whether or not the \"Swiftness\" enchantment is enabled.");
    public static final BooleanConfig USELESS_ENABLE = newConfig("uselessEnchant.enable", true, "Whether or not the \"useless\" enchantment is enabled.");
    public static final BooleanConfig WARDING_ENABLE = newConfig("wardingEnchant.enable", true, "Whether or not the \"warding\" enchantment is enabled.");
    
    private static BooleanConfig newConfig(String key, boolean defaultValue, String comment){
        var config = new BooleanConfig(key, defaultValue, comment);
        CONFIG_SET.add(config);
        return config;
    }
    
    private static Config<Integer> newConfig(String key, int defaultValue, String comment){
        return newConfig(key, defaultValue, Integer::parseInt, comment);
    }
    
    private static <T> Config<T> newConfig(String key, T defaultValue, Function<String, T> parser, String comment){
        var config = new Config<>(key, defaultValue, parser, comment.trim());
        CONFIG_SET.add(config);
        return config;
    }
    
    private static Path getConfigPath(){
        return FabricLoader.getInstance().getConfigDir()
            .resolve("gud").resolve("utils.cfg");
    }
    
    public static void load(){
        var configPath = getConfigPath();
        if(!Files.isRegularFile(configPath)){
            save();
            return;
        }
        
        var remainingConfigs = new HashMap<String, Config<?>>();
        for(Config<?> config : CONFIG_SET){
            remainingConfigs.put(config.key, config);
        }
        
        try(var reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)){
            reader.lines()
                // Remove comments
                .filter((line)->!line.startsWith("#"))
                // Split the configs
                .map((line)->line.trim().split("=", 2))
                // Remove the ones that don't have a value, takes care of empty lines too
                .filter((split)->split.length == 2)
                // Parse
                .sequential().forEach((split)->{
                    var key = split[0].trim();
                    var value = split[1].trim();
                    
                    var config = remainingConfigs.remove(key);
                    if(config == null){
                        return;
                    }
                    config.parse(value);
                });
        }catch(IOException e){
            throw new RuntimeException("Failed to read configuration from disk", e);
        }
        
        if(!remainingConfigs.isEmpty()){
            save();
        }
    }
    
    private static void save(){
        var path = getConfigPath();
        try{
            Files.createDirectories(path.getParent());
            try(var writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE)){
                CONFIG_SET.stream().sequential()
                    // Sort the configurations so that the keys are all grouped together
                    .sorted((a, b)->{
                        var spltA = a.key.split("\\.");
                        var spltB = b.key.split("\\.");
            
                        for(var i = 0; i < spltA.length && i < spltB.length; i++){
                            var splitDatumA = spltA[i];
                            var splitDatumB = spltB[i];
                            
                            // Special case for enable, just because
                            var enabledA = splitDatumA.equals("enable");
                            var enabledB = splitDatumB.equals("enable");
                            if(enabledA || enabledB){
                                if(enabledA && enabledB){
                                    continue;
                                }
                                return enabledA ? -1 : 1;
                            }
                            
                            var comparison = splitDatumA.compareToIgnoreCase(splitDatumB);
                            if(comparison != 0){
                                return comparison;
                            }
                        }
            
                        return Integer.compare(spltA.length, spltB.length);
                    })
                    // Generate the strings to write
                    .map((config)->{
                        var builder = new StringBuilder();
                        for(String line : config.comment.trim().split("\r")){
                            builder.append("# ").append(line).append('\n');
                        }
                        builder.append(config.key).append('=').append(config.value).append('\n');
                        return builder.toString();
                    })
                    .forEach((options)->{
                        try{
                            writer.write(options);
                        }catch(IOException e){
                            throw new UncheckedIOException(e);
                        }
                    });
            }
        }catch(IOException | UncheckedIOException e){
            Throwable exception = e;
            if(exception instanceof UncheckedIOException){
                exception = ((UncheckedIOException)e).getCause();
            }
            throw new RuntimeException("Failed to save configuration file", exception);
        }
    }
    
    public static class Config<T>{
        private final String key;
        private final Function<String, T> parser;
        private final String comment;
        private T value;
        
        public Config(String key, T defaultValue, Function<String, T> parser, String comment){
            this.key = key;
            value = defaultValue;
            this.parser = parser;
            this.comment = comment;
        }
        
        public T get(){
            return value;
        }
        
        private void parse(String value){
            this.value = parser.apply(value);
        }
    }
    
    public static class BooleanConfig extends Config<Boolean>{
        public BooleanConfig(String key, Boolean defaultValue, String comment){
            super(key, defaultValue, Boolean::parseBoolean, comment);
        }
        
        public <T> Optional<T> optional(Supplier<T> supplier){
            return get() ? Optional.of(supplier.get()) : Optional.empty();
        }
        
        public <T> T ifElse(Supplier<T> enabled, Supplier<T> disabled){
            if(get()){
                return enabled == null ? null : enabled.get();
            }else{
                return disabled == null ? null : disabled.get();
            }
        }
    }
}
