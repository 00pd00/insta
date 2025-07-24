package com.example.insta.config;

import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SameSiteCookieConfig {

    @Bean
    public ServletContextInitializer sameSiteCookieInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
                sessionCookieConfig.setSecure(true); // Required for SameSite=None
                sessionCookieConfig.setHttpOnly(true);
                sessionCookieConfig.setName("JSESSIONID");
                // Manually set SameSite=None in the Set-Cookie header (Render workaround)
                servletContext.setInitParameter("org.eclipse.jetty.servlet.SameSiteCookies", "None");
            }
        };
    }
}
