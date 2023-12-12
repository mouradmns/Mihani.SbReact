package com.mihani.security.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mihani.repositories.BricoleurRepo;
import com.mihani.security.config.JwtService;
import com.mihani.security.token.Token;
import com.mihani.security.token.TokenRepository;
import com.mihani.security.token.TokenType;
import com.mihani.security.user.Role;
import com.mihani.security.user.User;
import com.mihani.security.user.UserRepository;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletMapping;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private BricoleurRepo bricoleurRepo;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test:
     * {@link AuthenticationService#authenticate(AuthenticationRequest)}
     */
    @Test
    void testAuthenticate() throws AuthenticationException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setIdSecurity(1);
        user.setLastname("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.USER);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setIdSecurity(1);
        user2.setLastname("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.USER);
        user2.setTokens(new ArrayList<>());

        Token token = new Token();
        token.setExpired(true);
        token.setId(1);
        token.setRevoked(true);
        token.setToken("ABC123");
        token.setTokenType(TokenType.BEARER);
        token.setUser(user2);
        when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
        when(tokenRepository.findAllValidTokenByUser(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        AuthenticationResponse actualAuthenticateResult = authenticationService
                .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
        verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(tokenRepository).findAllValidTokenByUser(Mockito.<Integer>any());
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(tokenRepository).save(Mockito.<Token>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
        assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
        assertEquals("Doe", actualAuthenticateResult.getLastName());
        assertEquals("Jane", actualAuthenticateResult.getFirstName());
        assertEquals("USER", actualAuthenticateResult.getRole());
        assertEquals(1, actualAuthenticateResult.getUserId().intValue());
    }

    /**
     * Method under test:
     * {@link AuthenticationService#authenticate(AuthenticationRequest)}
     */
    @Test
    void testAuthenticate2() throws AuthenticationException {
        User user = mock(User.class);
        when(user.getRole()).thenReturn(Role.USER);
        when(user.getSecurityId()).thenReturn(1);
        when(user.getIdSecurity()).thenReturn(1);
        when(user.getFirstname()).thenReturn("Jane");
        when(user.getLastname()).thenReturn("Doe");
        Mockito.<Collection<? extends GrantedAuthority>>when(user.getAuthorities()).thenReturn(new ArrayList<>());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstname(Mockito.<String>any());
        doNothing().when(user).setIdSecurity(Mockito.<Integer>any());
        doNothing().when(user).setLastname(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
        doNothing().when(user).setTokens(Mockito.<List<Token>>any());
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setIdSecurity(1);
        user.setLastname("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.USER);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setIdSecurity(1);
        user2.setLastname("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.USER);
        user2.setTokens(new ArrayList<>());

        Token token = new Token();
        token.setExpired(true);
        token.setId(1);
        token.setRevoked(true);
        token.setToken("ABC123");
        token.setTokenType(TokenType.BEARER);
        token.setUser(user2);
        when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
        when(tokenRepository.findAllValidTokenByUser(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        AuthenticationResponse actualAuthenticateResult = authenticationService
                .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
        verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(tokenRepository).findAllValidTokenByUser(Mockito.<Integer>any());
        verify(user).getAuthorities();
        verify(user).getFirstname();
        verify(user).getIdSecurity();
        verify(user).getLastname();
        verify(user).getRole();
        verify(user).getSecurityId();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFirstname(Mockito.<String>any());
        verify(user).setIdSecurity(Mockito.<Integer>any());
        verify(user).setLastname(Mockito.<String>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRole(Mockito.<Role>any());
        verify(user).setTokens(Mockito.<List<Token>>any());
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(tokenRepository).save(Mockito.<Token>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
        assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
        assertEquals("Doe", actualAuthenticateResult.getLastName());
        assertEquals("Jane", actualAuthenticateResult.getFirstName());
        assertEquals("USER", actualAuthenticateResult.getRole());
        assertEquals(1, actualAuthenticateResult.getUserId().intValue());
    }

    /**
     * Method under test:
     * {@link AuthenticationService#authenticate(AuthenticationRequest)}
     */
    @Test
    void testAuthenticate3() throws AuthenticationException {
        User user = mock(User.class);
        when(user.getRole()).thenReturn(Role.USER);
        when(user.getSecurityId()).thenReturn(1);
        when(user.getIdSecurity()).thenReturn(1);
        when(user.getFirstname()).thenReturn("Jane");
        when(user.getLastname()).thenReturn("Doe");
        Mockito.<Collection<? extends GrantedAuthority>>when(user.getAuthorities()).thenReturn(new ArrayList<>());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstname(Mockito.<String>any());
        doNothing().when(user).setIdSecurity(Mockito.<Integer>any());
        doNothing().when(user).setLastname(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
        doNothing().when(user).setTokens(Mockito.<List<Token>>any());
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setIdSecurity(1);
        user.setLastname("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.USER);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setIdSecurity(1);
        user2.setLastname("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.USER);
        user2.setTokens(new ArrayList<>());

        Token token = new Token();
        token.setExpired(true);
        token.setId(1);
        token.setRevoked(true);
        token.setToken("ABC123");
        token.setTokenType(TokenType.BEARER);
        token.setUser(user2);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstname("Jane");
        user3.setIdSecurity(1);
        user3.setLastname("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.USER);
        user3.setTokens(new ArrayList<>());

        Token token2 = new Token();
        token2.setExpired(true);
        token2.setId(1);
        token2.setRevoked(true);
        token2.setToken("ABC123");
        token2.setTokenType(TokenType.BEARER);
        token2.setUser(user3);

        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token2);
        when(tokenRepository.saveAll(Mockito.<Iterable<Token>>any())).thenReturn(new ArrayList<>());
        when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
        when(tokenRepository.findAllValidTokenByUser(Mockito.<Integer>any())).thenReturn(tokenList);
        when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        AuthenticationResponse actualAuthenticateResult = authenticationService
                .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
        verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(tokenRepository).findAllValidTokenByUser(Mockito.<Integer>any());
        verify(user).getAuthorities();
        verify(user).getFirstname();
        verify(user).getIdSecurity();
        verify(user).getLastname();
        verify(user).getRole();
        verify(user).getSecurityId();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFirstname(Mockito.<String>any());
        verify(user).setIdSecurity(Mockito.<Integer>any());
        verify(user).setLastname(Mockito.<String>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRole(Mockito.<Role>any());
        verify(user).setTokens(Mockito.<List<Token>>any());
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(tokenRepository).save(Mockito.<Token>any());
        verify(tokenRepository).saveAll(Mockito.<Iterable<Token>>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
        assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
        assertEquals("Doe", actualAuthenticateResult.getLastName());
        assertEquals("Jane", actualAuthenticateResult.getFirstName());
        assertEquals("USER", actualAuthenticateResult.getRole());
        assertEquals(1, actualAuthenticateResult.getUserId().intValue());
    }

    /**
     * Method under test:
     * {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testRefreshToken() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        authenticationService.refreshToken(request, response);
        HttpServletResponse response2 = response.getResponse();
        assertTrue(response2 instanceof ResponseFacade);
        assertTrue(request.getInputStream() instanceof DelegatingServletInputStream);
        assertTrue(request.getHttpServletMapping() instanceof MockHttpServletMapping);
        assertTrue(request.getSession() instanceof MockHttpSession);
        assertEquals("", request.getContextPath());
        assertEquals("", request.getMethod());
        assertEquals("", request.getRequestURI());
        assertEquals("", request.getServletPath());
        assertEquals("HTTP/1.1", request.getProtocol());
        assertEquals("http", request.getScheme());
        assertEquals("localhost", request.getLocalName());
        assertEquals("localhost", request.getRemoteHost());
        assertEquals("localhost", request.getServerName());
        assertEquals(80, request.getLocalPort());
        assertEquals(80, request.getRemotePort());
        assertEquals(80, request.getServerPort());
        assertEquals(DispatcherType.REQUEST, request.getDispatcherType());
        assertFalse(request.isAsyncStarted());
        assertFalse(request.isAsyncSupported());
        assertFalse(request.isRequestedSessionIdFromURL());
        assertTrue(request.isActive());
        assertTrue(request.isRequestedSessionIdFromCookie());
        assertTrue(request.isRequestedSessionIdValid());
        assertSame(response.getOutputStream(), response2.getOutputStream());
    }

    /**
     * Method under test:
     * {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testRefreshToken2() throws IOException {
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        authenticationService.refreshToken(request, new Response());
        verify(request).getHeader(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testRefreshToken3() throws IOException {
        User user = mock(User.class);
        when(user.getIdSecurity()).thenReturn(1);
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstname(Mockito.<String>any());
        doNothing().when(user).setIdSecurity(Mockito.<Integer>any());
        doNothing().when(user).setLastname(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
        doNothing().when(user).setTokens(Mockito.<List<Token>>any());
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setIdSecurity(1);
        user.setLastname("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.USER);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setIdSecurity(1);
        user2.setLastname("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.USER);
        user2.setTokens(new ArrayList<>());

        Token token = new Token();
        token.setExpired(true);
        token.setId(1);
        token.setRevoked(true);
        token.setToken("ABC123");
        token.setTokenType(TokenType.BEARER);
        token.setUser(user2);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstname("Jane");
        user3.setIdSecurity(1);
        user3.setLastname("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.USER);
        user3.setTokens(new ArrayList<>());
        Token token2 = mock(Token.class);
        doNothing().when(token2).setExpired(anyBoolean());
        doNothing().when(token2).setId(Mockito.<Integer>any());
        doNothing().when(token2).setRevoked(anyBoolean());
        doNothing().when(token2).setToken(Mockito.<String>any());
        doNothing().when(token2).setTokenType(Mockito.<TokenType>any());
        doNothing().when(token2).setUser(Mockito.<User>any());
        token2.setExpired(true);
        token2.setId(1);
        token2.setRevoked(true);
        token2.setToken("ABC123");
        token2.setTokenType(TokenType.BEARER);
        token2.setUser(user3);

        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token2);
        when(tokenRepository.saveAll(Mockito.<Iterable<Token>>any())).thenReturn(new ArrayList<>());
        when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
        when(tokenRepository.findAllValidTokenByUser(Mockito.<Integer>any())).thenReturn(tokenList);
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");
        authenticationService.refreshToken(request, new MockHttpServletResponse());
        verify(jwtService).extractUsername(Mockito.<String>any());
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(jwtService).isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any());
        verify(token2, atLeast(1)).setExpired(anyBoolean());
        verify(token2).setId(Mockito.<Integer>any());
        verify(token2, atLeast(1)).setRevoked(anyBoolean());
        verify(token2).setToken(Mockito.<String>any());
        verify(token2).setTokenType(Mockito.<TokenType>any());
        verify(token2).setUser(Mockito.<User>any());
        verify(tokenRepository).findAllValidTokenByUser(Mockito.<Integer>any());
        verify(user).getIdSecurity();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFirstname(Mockito.<String>any());
        verify(user).setIdSecurity(Mockito.<Integer>any());
        verify(user).setLastname(Mockito.<String>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRole(Mockito.<Role>any());
        verify(user).setTokens(Mockito.<List<Token>>any());
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(request).getHeader(Mockito.<String>any());
        verify(tokenRepository).save(Mockito.<Token>any());
        verify(tokenRepository).saveAll(Mockito.<Iterable<Token>>any());
    }
}
