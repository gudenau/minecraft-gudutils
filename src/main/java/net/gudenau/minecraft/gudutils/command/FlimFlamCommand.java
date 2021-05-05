package net.gudenau.minecraft.gudutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.gudenau.minecraft.gudutils.GudUtilsCCA;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamAction;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public final class FlimFlamCommand{
    private FlimFlamCommand(){}
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(CommandManager
            .literal("gud_utils_flimflam")
            .requires((source)->source.hasPermissionLevel(2))
            .then(CommandManager.argument("targets", EntityArgumentType.entities())
                .then(CommandManager.argument("effect", FlimFlamArgumentType.effects())
                    .executes((context)->
                        execute(context.getSource(), EntityArgumentType.getEntities(context, "targets"), FlimFlamArgumentType.getEffect(context, "effect"))
                    )
                )
            )
        );
    }
    
    private static int execute(ServerCommandSource source, Collection<? extends Entity> targets, FlimFlamAction effect){
        var count = (int)targets.stream()
            .filter((entity)->entity instanceof PlayerEntity)
            .map((entity)->(PlayerEntity)entity)
            .filter(GudUtilsCCA.FLIM_FLAM::isProvidedBy)
            .filter(effect::apply)
            .count();
    
        if(count == 1){
            source.sendFeedback(new TranslatableText("commands.gud_utils.flim_flam.success.single"), true);
        }else if(count > 1){
            source.sendFeedback(new TranslatableText("commands.gud_utils.flim_flam.success.multiple", count), true);
        }else{
            source.sendFeedback(new TranslatableText("commands.gud_utils.flim_flam.failure"), true);
        }
        
        return count;
    }
    
    private static final class FlimFlamArgumentType implements ArgumentType<FlimFlamAction>{
        private static final Collection<String> EXAMPLES = Arrays.asList("gud_utils:broken_tool", "gud_utils:shuffle");
        public static final DynamicCommandExceptionType UNKNOWN_EFECT_STATUS = new DynamicCommandExceptionType(
            (object)->new TranslatableText("command.gud_utils.flim_flam.unknown", object)
        );
        
        public static FlimFlamArgumentType effects(){
            return new FlimFlamArgumentType();
        }
        
        public static FlimFlamAction getEffect(CommandContext<ServerCommandSource> context, String name){
            return context.getArgument(name, FlimFlamAction.class);
        }
        
        public FlimFlamAction parse(StringReader stringReader) throws CommandSyntaxException{
            Identifier identifier = Identifier.fromCommandInput(stringReader);
            return FlimFlamRegistry.getInstance().getOrEmpty(identifier)
                .orElseThrow(()->UNKNOWN_EFECT_STATUS.create(identifier));
        }
        
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder){
            return CommandSource.suggestIdentifiers(FlimFlamRegistry.getInstance().getIds(), builder);
        }
        
        public Collection<String> getExamples(){
            return EXAMPLES;
        }
    }
    
}
