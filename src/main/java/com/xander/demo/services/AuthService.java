// package com.xander.demo.services;

// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class AuthService {
// private final AuthzClient authzClient;

// public LoginResponseMessage login(String email, String pass) {
// log.info("START login for user {}", email);
// try {
// val response = authzClient.authorization(email, pass)
// .authorize();
// val result = new LoginResponseMessage()
// .tokenType(response.getTokenType())
// .token(response.getToken());
// log.info("FINISH login for user {} successfully", email)
// return result;
// } catch (AuthorizationDeniedException | HttpResponseException ex) {
// log.debug("Exception when login {}", email, ex);
// log.info("FINISH login for user {} is bad", email);
// throw new BadAuthorizeException();
// } catch (Exception ex) {
// log.error("Some error occurred during login");
// throw new BadAuthorizeException();
// }
// }
// }
