package me.declipsonator.globaldatapack.mixins;

import com.mojang.datafixers.DataFixer;
import me.declipsonator.globaldatapack.GlobalDatapack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.ApiServices;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow public abstract CompletableFuture<Void> reloadResources(Collection<String> dataPacks);

    @Inject(method="<init>", at=@At("TAIL"))
    public void startUp(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        reloadResources(dataPackManager.getEnabledIds());
    }

    @Redirect(method="loadDataPacks(Lnet/minecraft/resource/ResourcePackManager;Lnet/minecraft/resource/DataConfiguration;ZZ)Lnet/minecraft/resource/DataConfiguration;",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackSource;canBeEnabledLater()Z"),
                    to = @At(value = "INVOKE", target = "Ljava/util/Set;isEmpty()Z")
            ),
            at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    private static boolean addDataPack(Set<String> instance, Object o) {
        ArrayList<String> availableDatapacks = getAvailablePacks();
        String pack = (String) o;
        if(availableDatapacks.contains(pack)) return true;
        instance.add(pack);
        return true;
    }

    @Unique
    private static ArrayList<String> getAvailablePacks() {
        ArrayList<String> fileNames = new ArrayList<>();
        File directory = GlobalDatapack.availablePackFolder.toFile();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() || file.isDirectory()) {
                    fileNames.add("file/" + file.getName());
                }
            }
        }

        return fileNames;
    }
}
