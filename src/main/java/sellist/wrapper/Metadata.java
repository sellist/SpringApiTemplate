package sellist.wrapper;

import lombok.Data;

@Data
public class Metadata {
    private String requestId;
    private long timestamp; // epoch millis
    private String version;

    public Metadata(String requestId, long timestamp, String version) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.version = version;
    }

}
