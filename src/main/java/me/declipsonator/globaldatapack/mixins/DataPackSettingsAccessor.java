package me.declipsonator.globaldatapack.mixins;

import net.minecraft.resource.DataPackSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(DataPackSettings.class)
public interface DataPackSettingsAccessor {
    @Accessor("disabled")
    @Mutable
    void setDisabled(List<String> disabled);
}
