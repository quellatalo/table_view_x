package io.github.quellatalo.fx.tvx;

public class PrimRow<T> {
    private final int index;
    private final T value;

    public PrimRow(int index, T value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
