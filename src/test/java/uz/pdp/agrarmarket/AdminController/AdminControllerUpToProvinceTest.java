package uz.pdp.agrarmarket.AdminController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.agrarmarket.BaseTestConfiguration;
import uz.pdp.agrarmarket.model.address.ProvinceRegisterDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerUpToProvinceTest extends BaseTestConfiguration {

    @AfterEach
    void tearDown() {
        provinceRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Can add Province ")
    void addProvince() throws Exception {
        add().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Check add Province throw exception if province exist ")
    void addProvinceThrow() throws Exception {
        add();
        add().andExpect(status().isAlreadyReported());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getProvinceList() throws Exception {
        add();
        getList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteProvince() throws Exception {
        add();
        callDelete(5).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void updateProvince() throws Exception {
        add();
        update(4).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteProvinceThrow() throws Exception {
        callDelete(999).andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getProvinceById() throws Exception {
        add();
        getById(6).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getProvinceByIdThrow() throws Exception {
        getById(999).andExpect(status().isNotFound());
    }



    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Throw exception if province not fount")
    void updateProvinceThrow() throws Exception {
        update(100).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Throw exception if province already exist")
    void updateProvinceThrowException() throws Exception {
        add();
        updateThrow(3).andExpect(status().isAlreadyReported());
    }

    private ResultActions add() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addProvince")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/getProvinceList");
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete("/api/v1/deleteProvince/" + id);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/getProvince/" + id);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions update(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put("/api/v1/updateProvince/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Olmaliq")));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions updateThrow(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put("/api/v1/updateProvince/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        return mockMvc.perform(requestBuilder);
    }
}