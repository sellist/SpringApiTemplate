package sellist.wrapper;

import lombok.Data;

@Data
public class ResponseWrapper<T>
{
    private Metadata meta;
    private T data;

    public ResponseWrapper(
            Metadata meta,
            T data)
        {
        this.meta = meta;
        this.data = data;
        }

    public static <T> ResponseWrapper<T> of(
            T data,
            Metadata meta)
        {
        return new ResponseWrapper<>(
                meta,
                data);
        }

}
