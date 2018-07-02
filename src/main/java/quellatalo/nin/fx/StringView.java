package quellatalo.nin.fx;

public class StringView<T> {
    private final int index;
    private final T value;

    public StringView(int index, T value) {
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
