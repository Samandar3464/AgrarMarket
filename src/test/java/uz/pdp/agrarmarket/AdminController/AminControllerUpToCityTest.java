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
import uz.pdp.agrarmarket.model.address.CityRegisterDto;
import uz.pdp.agrarmarket.model.address.ProvinceRegisterDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AminControllerUpToCityTest extends BaseTestConfiguration {

    private static final String BASE_URL="/api/v1";
    @AfterEach
    void tearDown() {
        cityRepository.deleteAll();
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Can add City")
    void addCity() throws Exception {
        addCityDistrict().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Check add City throw exception if city exist ")
    void addCityThrow() throws Exception {
        addProvince();
        addCityDistrict();
        addCityDistrict().andExpect(status().isAlreadyReported());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCityList() throws Exception {
        addCityDistrict();
        getList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteCity() throws Exception {
        addCityDistrict();
        callDelete(6).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteCityThrow() throws Exception {
        callDelete(999).andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCityById() throws Exception {
        addCityDistrict();
        getById(4).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCityByIdThrow() throws Exception {
        getById(999).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void updateCity() throws Exception {
        addCityDistrict();
        update(3).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Throw exception if city not fount")
    void updateCityThrow() throws Exception {
        update(100).andExpect(status().isNotFound());
    }
    private void addProvince() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post(BASE_URL+"/addProvince")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        mockMvc.perform(requestBuilder);
    }
    private ResultActions addCityDistrict() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post(BASE_URL+"/addCity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CityRegisterDto("Tashkent sh",8)));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get(BASE_URL+"/getCityList");
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete(BASE_URL+"/deleteCity/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get(BASE_URL+"/getCity/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions update(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put(BASE_URL+"/updateCity/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CityRegisterDto("Qarshi sh",8)));
        return mockMvc.perform(requestBuilder);
    }
}