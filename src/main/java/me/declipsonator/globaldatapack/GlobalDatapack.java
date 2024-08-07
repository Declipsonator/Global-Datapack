package me.declipsonator.globaldatapack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class GlobalDatapack implements ModInitializer {
    public static final Logger LOG = LogManager.getLogger();
    public static final Path globalPackFolder = FabricLoader.getInstance().getGameDir().resolve("datapacks");
    public static final Path availablePackFolder = FabricLoader.getInstance().getGameDir().resolve("available_datapacks");

    @Override
    public void onInitialize() {
        try {
            globalPackFolder.toFile().mkdirs();
            availablePackFolder.toFile().mkdirs();
        } catch (SecurityException e) {
            LOG.error(e);
        }

        LOG.info("Initialized GlobalDatapack");
    }


}
