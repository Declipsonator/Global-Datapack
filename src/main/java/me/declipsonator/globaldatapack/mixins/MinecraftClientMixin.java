package me.declipsonator.globaldatapack.mixins;

import me.declipsonator.globaldatapack.GlobalDatapack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.*;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "createServerDataManager", at = @At("HEAD"), cancellable = true)
    private static void addGlobal(LevelStorage.Session session, CallbackInfoReturnable<ResourcePackManager> cir) {
        cir.setReturnValue(new ResourcePackManager(ResourceType.SERVER_DATA, new VanillaDataPackProvider(),
                new FileResourcePackProvider(session.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD),
                new FileResourcePackProvider(GlobalDatapack.globalPackFolder, ResourcePackSource.PACK_SOURCE_WORLD)));
        cir.cancel();
    }
}
