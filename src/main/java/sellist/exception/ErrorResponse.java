package sellist.exception;

import lombok.Data;

@Data
public class ErrorResponse
{
private String error;
private String message;
private String errorCode;
private long timestamp;

public ErrorResponse(String error, String message, String errorCode, long timestamp)
    {
    this.error = error;
    this.message = message;
    this.errorCode = errorCode;
    this.timestamp = timestamp;
    }
}
