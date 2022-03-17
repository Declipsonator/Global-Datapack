package me.declipsonator.globaldatapack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class GlobalDatapack implements ModInitializer {
    public static Logger LOG = LogManager.getLogger();
    public static File globalPackFolder = new File(FabricLoader.getInstance().getGameDir().toString() + "\\datapacks");


    @Override
    public void onInitialize() {
        try {
            globalPackFolder.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info("Initialized GlobalDatapack");
    }
}
