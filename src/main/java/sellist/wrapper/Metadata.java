package sellist.wrapper;

/**
 * Simple metadata container for API responses.
 */
public class Metadata {
    private String requestId;
    private long timestamp; // epoch millis
    private String version;

    public Metadata() {}

    public Metadata(String requestId, long timestamp, String version) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.version = version;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
