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
import uz.pdp.agrarmarket.model.address.DistrictRegisterDto;
import uz.pdp.agrarmarket.model.address.ProvinceRegisterDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AminControllerUpToDistrictTest extends BaseTestConfiguration {

    @AfterEach
    void tearDown() {
        districtRepository.deleteAll();
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Can add District  ")
    void addDistrict() throws Exception {
        addDistrictForCheck().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Check add District throw exception if District exist ")
    void addDistrictThrow() throws Exception {
        addDistrictForCheck();
        addDistrictForCheck().andExpect(status().isAlreadyReported());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getDistrictList() throws Exception {
        addDistrictForCheck();
        getList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteDistrict() throws Exception {
        addProvince();
        addDistrictForCheck();
        callDelete(1).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteDistrictThrow() throws Exception {
        callDelete(999).andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getDistrictById() throws Exception {
        addDistrictForCheck();
        getById(4).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getDistrictByIdThrow() throws Exception {
        getById(999).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void updateDistrict() throws Exception {
        addDistrictForCheck();
        update(6).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Update Throw exception if city not fount")
    void updateDistrictThrow() throws Exception {
        update(100).andExpect(status().isNotFound());
    }

    private ResultActions addProvince() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addProvince")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions addDistrictForCheck() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addDistrict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new DistrictRegisterDto("Tashkent t",1)));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/getDistrictList");
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete("/api/v1/deleteDistrict/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/getDistrict/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions update(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put("/api/v1/updateDistrict/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new DistrictRegisterDto("Qarshi Tuman",1)));
        return mockMvc.perform(requestBuilder);
    }
}