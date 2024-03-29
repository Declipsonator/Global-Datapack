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
        ArrayList<ResourcePackProvider> added = new ArrayList<>(List.of(new ResourcePackProvider[]{new VanillaDataPackProvider(session.getLevelStorage().getSymlinkFinder()), new FileResourcePackProvider(session.getDirectory(WorldSavePath.DATAPACKS), ResourceType.SERVER_DATA, ResourcePackSource.WORLD, session.getLevelStorage().getSymlinkFinder())}));
        added.add(new FileResourcePackProvider(GlobalDatapack.globalPackFolder, ResourceType.SERVER_DATA,  ResourcePackSource.WORLD, session.getLevelStorage().getSymlinkFinder()));
        return new ResourcePackManager(added.toArray(new ResourcePackProvider[0]));
    }

    @Redirect(method = "createAndStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobal(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

    @Redirect(method = "start(Lnet/minecraft/world/level/storage/LevelStorage$Session;Lcom/mojang/serialization/Dynamic;ZZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobalToo(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

    @Redirect(method = "startNewWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobalAgain(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

    @Redirect(method = "loadForRecreation", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;"))
    public ResourcePackManager addGlobalYetAgain(LevelStorage.Session session) {
        return getResourcePackManager(session);
    }

}
