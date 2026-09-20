package sellist.controller;

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
class WrapperIntegrationTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloIsWrapped()
            throws Exception
        {
        mockMvc.perform(get("/api/example/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.requestId").exists())
                .andExpect(jsonPath("$.meta.timestamp").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0.0"))
                .andExpect(jsonPath("$.data.message").value("Hello, world!"));
        }

    @Test
    void itemsAreWrapped()
            throws Exception
        {
        mockMvc.perform(get("/api/example/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.requestId").exists())
                .andExpect(jsonPath("$.meta.timestamp").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0.0"))
                .andExpect(jsonPath("$.data[0]").value("alpha"))
                .andExpect(jsonPath("$.data[1]").value("beta"))
                .andExpect(jsonPath("$.data[2]").value("gamma"));
        }

    @Test
    void textIsWrapped()
            throws Exception
        {
        mockMvc.perform(get("/api/example/text"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.requestId").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0.0"))
                .andExpect(jsonPath("$.data").value("plain text"));
        }

    @Test
    void notFoundReturnsWrappedError()
            throws Exception
        {
        mockMvc.perform(get("/api/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.meta.requestId").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0.0"));
        }
}
