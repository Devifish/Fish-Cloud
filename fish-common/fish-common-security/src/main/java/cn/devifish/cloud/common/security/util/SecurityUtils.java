package cn.devifish.cloud.common.security.util;

import cn.devifish.cloud.common.security.BasicUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * SecurityUtils
 * 安全工具类
 *
 * @author Devifish
 * @date 2020/7/11 17:15
 */
public class SecurityUtils {

    private static final BasicUser EMPTY = new BasicUser(
        -1L, "EMPTY", "EMPTY", Collections.emptyList());

    /**
     * 获取当前用户鉴权信息
     *
     * @return authentication
     */
    public static Authentication getAuthentication() {
        var context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    /**
     * 获取当前用户的身份信息
     *
     * @return Principal
     */
    public static Optional<BasicUser> getPrincipalOpt() {
        var authentication = getAuthentication();
        if (authentication != null) {
            var principal = authentication.getPrincipal();
            if (principal instanceof BasicUser) {
                return Optional.of((BasicUser) principal);
            }
        }
        return Optional.empty();
    }

    /**
     * 获取当前用户的身份信息
     *
     * @return Principal
     */
    public static BasicUser getPrincipal() {
        return getPrincipalOpt().orElse(EMPTY);
    }

    /**
     * 获取当前用户的权限信息
     *
     * @return 权限集合
     */
    public static Collection<? extends GrantedAuthority> getAuthorities() {
        var authentication = getAuthentication();
        return authentication.getAuthorities();
    }

}
