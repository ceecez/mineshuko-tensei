package mineshuko.tensei;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineshukoTensei implements ModInitializer {
	public static final String MOD_ID = "mineshuko-tensei";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}
Identifier CUTSCENE_DIM = new Identifier("mineshuko-tensei", "cutscene_dimension");

ServerWorld cutsceneWorld = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, CUTSCENE_DIM));
if (cutsceneWorld != null) {
        player.teleport(cutsceneWorld, 0.5, 100, 0.5, player.getYaw(), player.getPitch());
        }
