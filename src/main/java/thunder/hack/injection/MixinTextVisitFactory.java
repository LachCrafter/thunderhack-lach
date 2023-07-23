package thunder.hack.injection;


import thunder.hack.Thunderhack;
import thunder.hack.modules.misc.NameProtect;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import thunder.hack.utility.Util;

@Mixin(value = {TextVisitFactory.class})
public class MixinTextVisitFactory {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z", ordinal = 0), method = {"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z" }, index = 0)
    private static String adjustText(String text) {
        return protect(text);
    }

    private static String protect(String string) {
        if (!Thunderhack.moduleManager.get(NameProtect.class).isEnabled() || Util.mc.player == null)
            return string;
        String me = Util.mc.getSession().getUsername();
        if (string.contains(me))
            return string.replace(me, Thunderhack.moduleManager.get(NameProtect.class).newName.getValue());

        return string;
    }
}