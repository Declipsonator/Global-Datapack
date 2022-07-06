package me.declipsonator.globaldatapack.mixins;

import me.declipsonator.globaldatapack.GlobalDatapack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.*;
import net.minecraft.server.integrated.IntegratedServer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Nullable private IntegratedServer server;

    @ModifyVariable(method = "startIntegratedServer", at = @At(value = "HEAD"), index = 3, argsOnly = true)
    private ResourcePackManager addGlobal(ResourcePackManager value) {
        ArrayList<ResourcePackProvider> added = new ArrayList<>(((ResourcePackManagerAccessor) value).getProviders());

        added.add(new FileResourcePackProvider(GlobalDatapack.globalPackFolder.toFile(), ResourcePackSource.PACK_SOURCE_WORLD));
        return new ResourcePackManager(ResourceType.SERVER_DATA,
                added.toArray(new ResourcePackProvider[0]));

    }
}
