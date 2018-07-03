package quellatalo.nin.fx;

class PrimView<T> {
    private final int index;
    private final T value;

    public PrimView(int index, T value) {
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
