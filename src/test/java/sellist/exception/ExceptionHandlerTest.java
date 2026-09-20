package sellist.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExceptionHandlerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void illegalArgumentExceptionIsWrapped()
            throws Exception
        {
        mockMvc.perform(get("/test/illegal-arg"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.requestId").exists())
                .andExpect(jsonPath("$.meta.timestamp").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0.0"))
                .andExpect(jsonPath("$.data.error").value("Bad Request"))
                .andExpect(jsonPath("$.data.errorCode").value("ILLEGAL_ARGUMENT"))
                .andExpect(jsonPath("$.data.message").exists())
                .andExpect(jsonPath("$.data.timestamp").isNumber());
        }

    @Test
    void genericExceptionIsWrapped()
            throws Exception
        {
        mockMvc.perform(get("/test/runtime-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.meta.requestId").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0.0"))
                .andExpect(jsonPath("$.data.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.data.errorCode").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.data.message").value("An unexpected error occurred"));
        }

    @Test
    void requestIdHeaderIsPreserved()
            throws Exception
        {
        String customRequestId = "custom-req-123";
        mockMvc.perform(get("/test/illegal-arg").header(
                        "X-Request-Id",
                        customRequestId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.requestId").value(customRequestId));
        }
}
