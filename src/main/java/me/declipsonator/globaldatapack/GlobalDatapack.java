package me.declipsonator.globaldatapack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class GlobalDatapack implements ModInitializer {
    public static Logger LOG = LogManager.getLogger();
    public static Path globalPackFolder = FabricLoader.getInstance().getGameDir().resolve("datapacks");


    @Override
    public void onInitialize() {
        try {
            globalPackFolder.toFile().mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info("Initialized GlobalDatapack");
    }
}
