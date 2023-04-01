package uz.pdp.agrarmarket.AdminController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.agrarmarket.BaseTestConfiguration;
import uz.pdp.agrarmarket.model.request.PostCategoryRegisterDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class AdminControllerUpToPostCategoryTest extends BaseTestConfiguration {
    private static final String BASE_URL="/api/v1/admin/category";
    @AfterEach
    void tearDown() {
        postCategoryRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void addCategory() throws Exception {
        add().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void addCategoryThrowException() throws Exception {
        add();
        add().andExpect(status().isAlreadyReported());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCategoryList() throws Exception {
        add();
        getList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCategoryById() throws Exception {
        add();
        getById(5).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCategoryByIdThrowException() throws Exception {
        getById(5).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteCategoryById() throws Exception {
        add();
        callDelete(2).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteCategoryByIdThrow() throws Exception {
        callDelete(999).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void updateCategory() throws Exception {
        add();
        update(4).andExpect(status().isOk());
    }

    private ResultActions add() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post(BASE_URL+"/addCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new PostCategoryRegisterDto("Animal")));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get(BASE_URL+"/getCategoryList");
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete(BASE_URL+"/deleteById/" + id);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get(BASE_URL+"/getCategoryById/" + id);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions update(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put(BASE_URL+"/updateCategory/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new PostCategoryRegisterDto("Plants")));
        return mockMvc.perform(requestBuilder);
    }
}