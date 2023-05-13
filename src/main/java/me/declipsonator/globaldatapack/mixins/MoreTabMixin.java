package me.declipsonator.globaldatapack.mixins;

import me.declipsonator.globaldatapack.GlobalDatapack;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.screen.world.CreateWorldScreen$MoreTab")
public class MoreTabMixin {
    private static final Text GLOBAL_DATA_PACKS_TEXT = Text.of("Global Data Packs");


    @Redirect(method="<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 2))
    public <T extends Widget> T createAdder(GridWidget.Adder instance, T widget) {
        T value = instance.add(widget);
        instance.add(ButtonWidget.builder(GLOBAL_DATA_PACKS_TEXT, button -> Util.getOperatingSystem().open(GlobalDatapack.globalPackFolder.toUri())).width(210).build());
        return value;
    }

}
