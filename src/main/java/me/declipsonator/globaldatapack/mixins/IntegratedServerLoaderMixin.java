package me.declipsonator.globaldatapack.mixins;

import me.declipsonator.globaldatapack.GlobalDatapack;
import net.minecraft.resource.*;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {
    @Unique
    @NotNull
    private ResourcePackManager getResourcePackManager(LevelStorage.Session session) {
        ArrayList<ResourcePackProvider> added = new ArrayList<>(List.of(new ResourcePackProvider[]{new VanillaDataPackProvider(), new FileResourcePackProvider(session.getDirectory(WorldSavePath.DATAPACKS), ResourceType.SERVER_DATA, ResourcePackSource.WORLD)}));
        added.add(new FileResourcePackProvider(GlobalDatapack.globalPackFolder, ResourceType.SERVER_DATA,  ResourcePackSource.WORLD));
        return new ResourcePackManager(added.toArray(new ResourcePackProvider[0]));
    }

    @Redirect(method = "createAndStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobal(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

    @Redirect(method = "start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobalToo(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

    @Redirect(method = "start(Lnet/minecraft/world/level/storage/LevelStorage$Session;Lnet/minecraft/server/DataPackContents;Lnet/minecraft/registry/CombinedDynamicRegistries;Lnet/minecraft/world/SaveProperties;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobalAgain(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

}
