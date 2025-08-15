package mineshuko.tensei;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineshukoTensei implements ModInitializer {
	public static final String MOD_ID = "mineshuko-tensei";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier CUTSCENE_TYPE_ID = new Identifier("mineshuko-tensei", "opening-cutscene-type");
    public static final RegistryKey<DimensionType> CUTSCENE_TYPE_KEY = RegistryKey.of(Registries.DIMENSION_TYPE, CUTSCENE_TYPE_ID);
    public static final Identifier CUTSCENE_DIM_ID = new Identifier("mineshuko-tensei", "opening-cutscene-dimension");
    public static final RegistryKey<World> CUTSCENE_DIM_KEY = RegistryKey.of(RegistryKeys.WORLD, CUTSCENE_DIM_ID);

	@Override
	public void onInitialize() {   // Teleport to cutscene dimension on first join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            // Replace with your dimension ID
            Identifier CUTSCENE_DIM = new Identifier("mineshuko-tensei", "opening-cutscene-dimension");
            RegistryKey<World> cutsceneKey = RegistryKey.of(RegistryKeys.WORLD, CUTSCENE_DIM);

            // Only run if in overworld and player hasn't seen cutscene
            if (player.getWorld().getRegistryKey() == World.OVERWORLD && !((ServerPlayerEntityMixin) player).mineshuko$hasSeenCutscene()) {
                // Teleport player to the cutscene dimension
                ServerWorld cutsceneWorld = server.getWorld(cutsceneKey);
                if (cutsceneWorld != null) {
                    player.teleport(cutsceneWorld, 0.5, 100, 0.5, player.getYaw(), player.getPitch());
                    player.((ServerPlayerEntityMixin) player).mineshuko$hasSeenCutscene()).putBoolean("cutscene_seen", true);
                }
            }
        });
    }}
