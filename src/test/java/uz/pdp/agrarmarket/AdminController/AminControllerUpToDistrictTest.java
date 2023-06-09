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

    private static final String BASE_URL="/api/v1/admin/district";
    @AfterEach
    void tearDown() {
        districtRepository.deleteAll();
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void addDistrict() throws Exception {
        addDistrictForCheck().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
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
    void updateDistrictThrow() throws Exception {
        update(100).andExpect(status().isNotFound());
    }

    private void addProvince() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/admin/province/addProvince")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        mockMvc.perform(requestBuilder);
    }
    private ResultActions addDistrictForCheck() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post(BASE_URL+"/addDistrict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new DistrictRegisterDto("Tashkent t",1)));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get(BASE_URL+"/getDistrictList");
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete(BASE_URL+"/deleteDistrict/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get(BASE_URL+"/getDistrict/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions update(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put(BASE_URL+"/updateDistrict/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new DistrictRegisterDto("Qarshi Tuman",1)));
        return mockMvc.perform(requestBuilder);
    }
}