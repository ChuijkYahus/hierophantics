package robotgiggle.hierophantics.forge;

import com.google.common.collect.ImmutableSet;

import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.block.BedBlock;
import net.minecraft.block.enums.BedPart;
import robotgiggle.hierophantics.Hierophantics;
import robotgiggle.hierophantics.HierophanticsVillagers

import net.minecraftforge.registries.RegisterEvent;

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier

object ForgeHierophanticsVillagers {
    val POI_TYPES: DeferredRegister<PointOfInterestType> = DeferredRegister.create(Hierophantics.MOD_ID, RegistryKeys.POINT_OF_INTEREST_TYPE)
    val PROFESSIONS: DeferredRegister<VillagerProfession> = DeferredRegister.create(Hierophantics.MOD_ID, RegistryKeys.VILLAGER_PROFESSION)

    val FLAY_BED_POI = POI_TYPES.register("flay_bed", {-> PointOfInterestType(Hierophantics.FLAY_BED_BLOCK.get()
        .getStateManager().getStates().stream()
        .filter({blockState -> 
            blockState.get(BedBlock.PART) == BedPart.HEAD;
        })
        .collect(ImmutableSet.toImmutableSet()),
    1, 1)})

    val QUILTMIND_POI_KEY = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Hierophantics.id("quiltmind"));
    val QUILTMIND_POI = POI_TYPES.register("quiltmind", {-> PointOfInterestType(Hierophantics.EDIFIED_WORKSTATION_BLOCK.get().getStateManager().getStates().toSet(), 1, 1)})
    val QUILTMIND = PROFESSIONS.register("quiltmind", {->VillagerProfession(
        "quiltmind", 
        {e -> e.matchesKey(QUILTMIND_POI_KEY)}, 
        {e -> e.matchesKey(QUILTMIND_POI_KEY)}, 
        ImmutableSet.of(), 
        ImmutableSet.of(), 
        SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE
    )})

    var registered = false
    
    fun init(event: RegisterEvent) {
        if (!registered) {
            POI_TYPES.register()
            PROFESSIONS.register()
            HierophanticsVillagers.QUILTMIND = QUILTMIND
            registered = true
        }
    }
}
