package com.xir.NHUtilities.mixinPlugin;

import static com.xir.NHUtilities.config.Config.enableAccelerateGregTechMachine;
import static com.xir.NHUtilities.config.Config.enableEnhancedTeleporterMKII;
import static com.xir.NHUtilities.config.Config.enableModifyEnderIoCapBankIO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

public enum MixinsPackage {

    EnhanceTeleporterMKII(enableEnhancedTeleporterMKII, "DraconicEvolution", Mixins.DE_TeleporterMKII_Mixin,
        Mixins.DE_GUITeleporter_Mixin, Mixins.DE_TeleporterPacket_Mixin),
    Enable_MTEAcclerator(enableAccelerateGregTechMachine, "GregTech", Mixins.GT_MTEAcclerator_Mixin),
    EnderIO_Modify(enableModifyEnderIoCapBankIO, "EnderIO", Mixins.Modify_CapBankMaxIO_Mixin);

    private final Boolean isEnabledModule;
    private final Set<String> targetMods = new HashSet<>();
    private final List<String> mixinsList = new ArrayList<>();

    MixinsPackage(Boolean isEnabledModule, String modulePath, Mixins @NotNull... mixins) {
        this.isEnabledModule = isEnabledModule;
        for (Mixins mixin : mixins) {
            mixinsList.add(modulePath + '.' + mixin.getMixinClass());
            targetMods.addAll(mixin.getTargetMod());
        }
    }

    public static @NotNull List<String> getLateMixins(Set<String> loadedMods) {
        ArrayList<String> mixins = new ArrayList<>();
        for (MixinsPackage module : MixinsPackage.values()) {
            if (module.isEnabledModule && loadedMods.containsAll(module.targetMods)) {
                mixins.addAll(module.mixinsList);
            }
        }
        return mixins;
    }
}
