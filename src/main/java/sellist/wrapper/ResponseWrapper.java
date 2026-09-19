package sellist.wrapper;

/**
 * Generic response wrapper that encloses data with metadata.
 */
public class ResponseWrapper<T> {
    private Metadata meta;
    private T data;

    public ResponseWrapper() {}

    public ResponseWrapper(Metadata meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    public static <T> ResponseWrapper<T> of(T data, Metadata meta) {
        return new ResponseWrapper<>(meta, data);
    }

    public Metadata getMeta() {
        return meta;
    }

    public void setMeta(Metadata meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
