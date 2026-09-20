package sellist.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;
import java.util.UUID;

@ControllerAdvice
public class WrapperResponseBodyAdvice implements ResponseBodyAdvice<Object>
{

    private final ObjectMapper objectMapper;
    private final String apiVersion;

    public WrapperResponseBodyAdvice(
            ObjectMapper objectMapper,
            @Value("${api.version:1.0.0}") String apiVersion)
        {
        this.objectMapper = objectMapper;
        this.apiVersion = apiVersion;
        }

    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType)
        {
        return true;
        }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response)
        {
        if (body instanceof ResponseWrapper) {
            return body;
        }

        Metadata meta = new Metadata(
                extractRequestId(request),
                Instant.now().toEpochMilli(),
                apiVersion);
        ResponseWrapper<Object> wrapped = ResponseWrapper.of(
                body,
                meta);

        if (StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            try {
                return objectMapper.writeValueAsString(wrapped);
            } catch (JsonProcessingException e) {
                return body;
            }
        }

        return wrapped;
        }

    private String extractRequestId(ServerHttpRequest request)
        {
        String rid = request.getHeaders().getFirst("X-Request-Id");
        if (rid != null && !rid.isEmpty()) {
            return rid;
        }
        return UUID.randomUUID().toString();
        }
}
