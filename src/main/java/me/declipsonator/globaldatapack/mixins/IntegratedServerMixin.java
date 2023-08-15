package me.declipsonator.globaldatapack.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    @Inject(method = "setupServer", at = @At("HEAD"))
    public void onSetup(CallbackInfoReturnable<Boolean> cir) {
        ((IntegratedServer)(Object)this).reloadResources(((MinecraftServer)(Object)this).getDataPackManager().getEnabledNames());
    }
}
