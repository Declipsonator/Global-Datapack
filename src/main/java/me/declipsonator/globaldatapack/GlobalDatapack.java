package me.declipsonator.globaldatapack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class GlobalDatapack implements ModInitializer {
    public static final Logger LOG = LogManager.getLogger();
    public static final Path globalPackFolder = FabricLoader.getInstance().getGameDir().resolve("datapacks");
    public static final Path availablePackFolder = FabricLoader.getInstance().getGameDir().resolve("available_datapacks");
    public static final File globalDataPackConfig = FabricLoader.getInstance().getConfigDir().resolve("globaldatapack.json").toFile();
    public static JsonObject config = new JsonObject();


    @Override
    public void onInitialize() {
        try {
            globalPackFolder.toFile().mkdirs();
            availablePackFolder.toFile().mkdirs();
            globalDataPackConfig.createNewFile();
            if (!globalDataPackConfig.exists()) {
                globalDataPackConfig.createNewFile();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Files.write(globalDataPackConfig.toPath(), gson.toJson(config).getBytes());
            } else {
                String json = Files.readString(globalDataPackConfig.toPath());
                config = (JsonObject) JsonParser.parseString(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Files.write(globalDataPackConfig.toPath(), gson.toJson(config).getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        LOG.info("Initialized GlobalDatapack");
    }


}
