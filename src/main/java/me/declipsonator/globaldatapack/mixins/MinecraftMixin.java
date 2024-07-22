package me.declipsonator.globaldatapack.mixins;

import me.declipsonator.globaldatapack.GlobalDatapack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.*;
import net.minecraft.server.SaveLoader;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.HashSet;

@Mixin(MinecraftClient.class)
public class MinecraftMixin {
    @ModifyVariable(method = "startIntegratedServer", at = @At("HEAD"), index = 2, argsOnly = true)
    private ResourcePackManager startIntegratedServer(ResourcePackManager value, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, boolean newWorld) {
        ArrayList<String> datapacks = new ArrayList<>(value.getIds());

        ArrayList<ResourcePackProvider> providers = new ArrayList<>(((ResourcePackManagerAccessor) value).getProviders());
        providers.add(new FileResourcePackProvider(GlobalDatapack.availablePackFolder, ResourceType.SERVER_DATA,  ResourcePackSource.NONE, session.getLevelStorage().getSymlinkFinder()));
        ((ResourcePackManagerAccessor) value).setProviders(new HashSet<>(providers));
        saveLoader.dataPackContents().refresh();
        value.scanPacks();

        ArrayList<String> newDatapacks = new ArrayList<>(value.getIds());

        newDatapacks.removeAll(datapacks);
        GlobalDatapack.LOG.info("HERE: ");
        newDatapacks.forEach(profile -> GlobalDatapack.LOG.info(value.getProfile(profile).getDisplayName()));
        newDatapacks.forEach(profile -> value.disable(profile));


        value.getEnabledProfiles().forEach(profile -> GlobalDatapack.LOG.info(profile.getDisplayName()));

        return value;
    }

}
