package cn.devifish.cloud.user.server.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2AuthorizationServerConfiguration
 * OAuth2 身份认证服务配置
 *
 * @see org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration
 * @author Devifish
 * @date 2020/7/3 16:07
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    //private final AuthenticationManager authenticationManager;
    private final OAuth2ClientDetailsService oauth2ClientDetailsService;
    private final OAuth2UserDetailsService oauth2UserDetailsService;
    private final TokenStore tokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauth2ClientDetailsService);
    }

    /**
     * 配置授权、令牌的访问服务 用于账户密码授权
     *
     * @param endpoints spring security
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .userDetailsService(oauth2UserDetailsService)
                .tokenStore(tokenStore);
    }

}
