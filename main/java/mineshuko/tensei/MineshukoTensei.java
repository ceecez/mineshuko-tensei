package mineshuko.tensei;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.structure.StructureSetRegistry;
import net.minecraft.world.level.LevelStem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import java.util.OptionalLong;

public class MineshukoTensei implements ModInitializer {
    public static final String MOD_ID = "mineshuko-tensei";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier CUTSCENE_TYPE_ID = new Identifier(MOD_ID, "opening-cutscene-dimension-type");
    public static final RegistryKey<DimensionType> CUTSCENE_TYPE_KEY = RegistryKey.of(Registries.DIMENSION_TYPE, CUTSCENE_TYPE_ID);
    public static final Identifier CUTSCENE_DIM_ID = new Identifier(MOD_ID, "opening-cutscene-dimension");
    public static final RegistryKey<World> CUTSCENE_DIM_KEY = RegistryKey.of(RegistryKeys.WORLD, CUTSCENE_DIM_ID);

    @Override
    public void onInitialize() {
        System.out.println("Mineshuko Tensei mod initialized!");
        // Register DimensionType
        DimensionType cutsceneDimensionType = DimensionType.create(
                OptionalLong.of(6000),
                true,
                false,
                false,
                true,
                1.0,
                false,
                false,
                false,
                false,
                new Identifier("minecraft:infiniburn_overworld"),
                DimensionType.OVERWORLD_EFFECTS,
                256,
                new DimensionType.MonsterSettings(false, false, Optional.empty())
        );
        Registry.register(Registries.DIMENSION_TYPE, CUTSCENE_TYPE_ID, cutsceneDimensionType);

        // Register LevelStem
        RegistryKey<LevelStem> CUTSCENE_LEVEL_STEM_KEY = RegistryKey.of(RegistryKeys.LEVEL_STEM, CUTSCENE_DIM_ID);
        Registry.register(
                Registries.LEVEL_STEM,
                CUTSCENE_DIM_ID,
                new LevelStem(
                        CUTSCENE_TYPE_KEY,
                        new FlatChunkGenerator(
                                new StructureSetRegistry(), // You may need to get this from the server context
                                new FlatChunkGeneratorConfig()
                        )
                )
        );

        // Player data sync event
        ServerPlayConnectionEvents.JOIN.register((oldPlayer, newPlayer, alive) -> {
            ((ServerPlayerDataAccess) newPlayer).setSeenCutscene(
                    ((ServerPlayerDataAccess) oldPlayer).getSeenCutscene()
            );
        });
    }
}