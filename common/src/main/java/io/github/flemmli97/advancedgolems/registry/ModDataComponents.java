package io.github.flemmli97.advancedgolems.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.tenshilib.platform.PlatformUtils;
import io.github.flemmli97.tenshilib.platform.registry.PlatformRegistry;
import io.github.flemmli97.tenshilib.platform.registry.RegistryEntrySupplier;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final PlatformRegistry<DataComponentType<?>> DATA_COMPONENTS = PlatformUtils.INSTANCE.of(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), AdvancedGolems.MODID);

    public static final RegistryEntrySupplier<DataComponentType<?>, DataComponentType<Boolean>> SHUTDOWN = DATA_COMPONENTS.register("golem_shutdown", () -> ModDataComponents.build(b -> b.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)));
    public static final RegistryEntrySupplier<DataComponentType<?>, DataComponentType<GolemController.Mode>> CONTROLLER_MODE = DATA_COMPONENTS.register("controller_mode", () -> ModDataComponents.build(b -> b.persistent(enumCodec(GolemController.Mode.class, null)).networkSynchronized(enumStreamCodec(GolemController.Mode.class, null))));
    public static final RegistryEntrySupplier<DataComponentType<?>, DataComponentType<UUID>> CONTROLLER_ENTITY = DATA_COMPONENTS.register("selected_golem", () -> ModDataComponents.build(b -> b.persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC)));

    private static <T> DataComponentType<T> build(UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return unaryOperator.apply(DataComponentType.builder()).build();
    }

    public static <T extends Enum<T>> Codec<T> enumCodec(Class<T> clss, T fallback) {
        return Codec.INT.flatXmap(i -> {
            try {
                return DataResult.success(clss.getEnumConstants()[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                if (fallback != null)
                    return DataResult.error(() -> "No such enum ordinal " + i + " for class " + clss, fallback);
                return DataResult.error(() -> "No such enum ordinal " + i + " for class " + clss);
            }
        }, e -> DataResult.success(e.ordinal()));
    }

    public static <T extends Enum<T>> StreamCodec<ByteBuf, T> enumStreamCodec(Class<T> clss, T fallback) {
        return new StreamCodec<>() {

            @Override
            public void encode(ByteBuf buf, T val) {
                buf.writeInt(val.ordinal());
            }

            @Override
            public T decode(ByteBuf buf) {
                int i = buf.readInt();
                try {
                    return clss.getEnumConstants()[i];
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (fallback != null) {
                        AdvancedGolems.LOGGER.warn("No such enum ordinal {} for class {}. Returning default {}", i, clss, fallback);
                        return fallback;
                    }
                    throw new ArrayIndexOutOfBoundsException("No such enum ordinal " + i + " for class " + clss);
                }
            }
        };
    }
}
