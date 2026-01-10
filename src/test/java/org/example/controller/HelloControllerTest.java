package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.HelloController.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HelloController 单元测试
 */
@Slf4j
@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isString());
    }

    @Test
    void testShowTools() throws Exception {
        mockMvc.perform(get("/api/hello/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.currentTime").exists())
                .andExpect(jsonPath("$.data.md5Hash").exists())
                .andExpect(jsonPath("$.data.randomCode").exists())
                .andExpect(jsonPath("$.data.jsonExample").exists())
                .andExpect(jsonPath("$.data.sensitiveDataMasked").exists())
                .andExpect(jsonPath("$.data.jwtToken").exists())
                .andExpect(jsonPath("$.data.tokenValid").value(true));
    }

    @Test
    void testValidateParamsSuccess() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername("张三");
        request.setPhone("13812345678");

        mockMvc.perform(post("/api/hello/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    void testValidateParamsFailure() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername(""); // 空用户名
        request.setPhone("invalid-phone"); // 无效手机号

        mockMvc.perform(post("/api/hello/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40000)); // 参数错误码
    }

    @Test
    void testBusinessErrorNoException() throws Exception {
        mockMvc.perform(get("/api/hello/error/business")
                        .param("throwError", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("没有抛出异常"));
    }

    @Test
    void testBusinessErrorWithException() throws Exception {
        mockMvc.perform(get("/api/hello/error/business")
                        .param("throwError", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40000)); // 参数错误码
    }

    @Test
    void testRuntimeErrorNoException() throws Exception {
        mockMvc.perform(get("/api/hello/error/runtime")
                        .param("throwError", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("没有抛出异常"));
    }

    @Test
    void testRuntimeErrorWithException() throws Exception {
        mockMvc.perform(get("/api/hello/error/runtime")
                        .param("throwError", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(50000)); // 服务器错误码
    }

    @Test
    void testValidationErrorSuccess() throws Exception {
        mockMvc.perform(get("/api/hello/error/validation")
                        .param("username", "张三")
                        .param("phone", "13812345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("参数校验通过"));
    }

    @Test
    void testValidationErrorFailure() throws Exception {
        mockMvc.perform(get("/api/hello/error/validation")
                        .param("username", "") // 空用户名
                        .param("phone", "invalid")) // 无效手机号
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40000)); // 参数错误码
    }

    @Test
    void testShowTrace() throws Exception {
        mockMvc.perform(get("/api/hello/trace"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.traceId").exists())
                .andExpect(jsonPath("$.data.message").exists());
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/hello/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.status").value("UP"))
                .andExpect(jsonPath("$.data.timestamp").exists())
                .andExpect(jsonPath("$.data.version").value("1.0.0"))
                .andExpect(jsonPath("$.data.traceId").exists());
    }
}