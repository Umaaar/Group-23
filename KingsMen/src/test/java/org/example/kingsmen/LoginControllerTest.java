//package org.example.kingsmen;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.example.controller.LoginController;
//import org.example.model.CustomUserDetail;
//import org.example.service.CategoryService;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//
//import java.io.IOException;
//import java.util.Collections;
//
//public class LoginControllerTest {
//    @InjectMocks
//    private LoginController controller;
//
//    @Mock
//    private CategoryService categoryService;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testLogin() {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpSession session = mock(HttpSession.class);
//        Model model = mock(Model.class);
//        when(request.getSession()).thenReturn(session);
//
//        // Call the method under test
//        String viewName = controller.login(model);
//
//        // Verify the results
//        assertEquals("/frontend-views/login", viewName);
//    }
//
//    @Test
//    public void testGetLoginInfoAsAdmin() throws IOException {
//        CustomUserDetail userDetail = mock(CustomUserDetail.class);
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getPrincipal()).thenReturn(userDetail);
//        when(userDetail.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//
//        HttpServletResponse response = mock(HttpServletResponse.class);
//
//        // Instantiate the LoginController and set the CategoryService dependency
//        LoginController controller = new LoginController();
//        controller.setCategoryService(mock(CategoryService.class));
//
//        controller.getLoginInfo((CustomUserDetail) authentication, response);
//
//        verify(response).sendRedirect("/admin");
//    }
//}
//
//
//
//
////    @Test
////    public void testGetLoginInfoAsCustomer() {
////        CustomUserDetail userDetail = mock(CustomUserDetail.class);
////        Authentication authentication = mock(Authentication.class);
////        when(authentication.getPrincipal()).thenReturn(userDetail);
////        when(userDetail.getAuthorities()).thenReturn(
////                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
////
////        HttpServletResponse response = mock(HttpServletResponse.class);
////
////        controller.getLoginInfo(authentication, response);
////
////        verify(response).sendRedirect("/customer-dashboard");
////    }
////}
package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void testLogin() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("/frontend-views/login"));

        verify(categoryService, times(1)).getAllCategory();
    }

    @Test
    void testRegister() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("/frontend-views/register"));

        verify(categoryService, times(1)).getAllCategory();
    }

    @Test
    void testRegisterPost() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("test123");
        Role role = new Role();
        role.setId(2);

        when(roleRepository.findById(2)).thenReturn(Optional.of(role));
        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/register")
                        .param("email", "test@example.com")
                        .param("password", "test123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        verify(userRepository, times(1)).save(any());
    }
}

