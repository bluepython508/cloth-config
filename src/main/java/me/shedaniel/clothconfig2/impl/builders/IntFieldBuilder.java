package me.shedaniel.clothconfig2.impl.builders;

import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class IntFieldBuilder extends FieldBuilder<Integer, IntegerListEntry> {
    
    private Consumer<Integer> saveConsumer = null;
    private Function<Integer, Optional<Text[]>> tooltipSupplier = i -> Optional.empty();
    private final int value;
    private Integer min = null, max = null;
    
    public IntFieldBuilder(Text resetButtonKey, Text fieldNameKey, int value) {
        super(resetButtonKey, fieldNameKey);
        this.value = value;
    }
    
    public IntFieldBuilder requireRestart() {
        requireRestart(true);
        return this;
    }
    
    public IntFieldBuilder setErrorSupplier(Function<Integer, Optional<Text>> errorSupplier) {
        this.errorSupplier = errorSupplier;
        return this;
    }
    
    public IntFieldBuilder setSaveConsumer(Consumer<Integer> saveConsumer) {
        this.saveConsumer = saveConsumer;
        return this;
    }
    
    public IntFieldBuilder setDefaultValue(Supplier<Integer> defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
    
    public IntFieldBuilder setDefaultValue(int defaultValue) {
        this.defaultValue = () -> defaultValue;
        return this;
    }
    
    public IntFieldBuilder setTooltipSupplier(Function<Integer, Optional<Text[]>> tooltipSupplier) {
        this.tooltipSupplier = tooltipSupplier;
        return this;
    }
    
    public IntFieldBuilder setTooltipSupplier(Supplier<Optional<Text[]>> tooltipSupplier) {
        this.tooltipSupplier = i -> tooltipSupplier.get();
        return this;
    }
    
    public IntFieldBuilder setTooltip(Optional<Text[]> tooltip) {
        this.tooltipSupplier = i -> tooltip;
        return this;
    }
    
    public IntFieldBuilder setTooltip(Text... tooltip) {
        this.tooltipSupplier = i -> Optional.ofNullable(tooltip);
        return this;
    }
    
    public IntFieldBuilder setMin(int min) {
        this.min = min;
        return this;
    }
    
    public IntFieldBuilder setMax(int max) {
        this.max = max;
        return this;
    }
    
    public IntFieldBuilder removeMin() {
        this.min = null;
        return this;
    }
    
    public IntFieldBuilder removeMax() {
        this.max = null;
        return this;
    }
    
    @NotNull
    @Override
    public IntegerListEntry build() {
        IntegerListEntry entry = new IntegerListEntry(getFieldNameKey(), value, getResetButtonKey(), defaultValue, saveConsumer, null, isRequireRestart());
        if (min != null)
            entry.setMinimum(min);
        if (max != null)
            entry.setMaximum(max);
        entry.setTooltipSupplier(() -> tooltipSupplier.apply(entry.getValue()));
        if (errorSupplier != null)
            entry.setErrorSupplier(() -> errorSupplier.apply(entry.getValue()));
        return entry;
    }
    
}
