package com.english.config;

import com.english.security.LogoutSuccessHandlerImpl;
import com.english.security.filter.AuthenticationFilter;
import com.english.security.handler.UnauthenticatedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthenticatedHandler unauthenticatedHandler;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors().and()
            .csrf().disable()
            // 禁用HTTP响应标头缓存
            .headers()
            // 禁止iframe嵌套所有URL X-Frame-Options:DENY
            .frameOptions().and()
            .cacheControl().disable().and()
            // 认证失败处理器
            .exceptionHandling().authenticationEntryPoint(unauthenticatedHandler).and()
            // 基于token非session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // 过滤请求
            .authorizeRequests()
            // 允许匿名访问的URL
            .antMatchers("/login", "/captcha", "/open/**").permitAll()
            // 允许匿名访问的静态资源URL
            .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
            // 其他所有请求全部需要鉴权认证
            .anyRequest().authenticated();
        /* 访问/logout触发退出登录. logoutSuccessHandler()设置退出处理器. */
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        /* 在UsernamePasswordAuthenticationFilter前, 添加过滤器authenticationFilter. 前者负责从登录请求中提取用户名和密码. 后者负责从request中提取token  */
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        /* AuthenticationFilter.class 独立返回. 所以需要添加跨域设置 */
        httpSecurity.addFilterBefore(corsFilter, AuthenticationFilter.class);
        /* LogoutFilter.class 独立返回. 所以需要添加跨域设置 */
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }
}
