package uz.pdp.agrarmarket.AdminController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.agrarmarket.BaseTestConfiguration;
import uz.pdp.agrarmarket.model.request.AddUserFromAdminDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uz.pdp.agrarmarket.entity.ENUM.Role.*;

class AdminControllerUpToUserTest extends BaseTestConfiguration {
    private static final String BASE_URL = "/api/v1/admin/user";

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void addUser() throws Exception {
        add().andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void addUserThrowIfUserExist() throws Exception {
        add();
        add().andExpect(status().isAlreadyReported());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getUsersList() throws Exception {
        add();
        getPersonList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getPersonById() throws Exception {
        add();
        getById(4).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getPersonByIdThrowIdUserNotFound() throws Exception {
        getById(999).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteUser() throws Exception {
        add();
        deleteById(5).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteUserThrowIfUserNotFound() throws Exception {
        deleteById(999).andExpect(status().isNotFound());
    }

    private ResultActions add() throws Exception {
        final MockHttpServletRequestBuilder request =
                post(BASE_URL + "/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new AddUserFromAdminDto("123456789", "Samandar", "Shodmonov", "123456", List.of(SUPER_ADMIN, ADMIN, USER))));
      return   mockMvc.perform(request);
    }

    private ResultActions getPersonList() throws Exception {
        final MockHttpServletRequestBuilder request =
                get(BASE_URL + "/userList");
        return mockMvc.perform(request);
    }

    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder request =
                get(BASE_URL + "/getUserById/" + id);
        return mockMvc.perform(request);
    }

    private ResultActions deleteById(int id) throws Exception {
        final MockHttpServletRequestBuilder request =
                delete(BASE_URL + "/deleteUser/" + id);
        return mockMvc.perform(request);
    }
}