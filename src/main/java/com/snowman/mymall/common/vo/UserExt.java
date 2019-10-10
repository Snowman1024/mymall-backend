package com.snowman.mymall.common.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 13:30
 * @Version 1.0
 **/
@Data
public class UserExt extends User {
    private static final long serialVersionUID = -3230925662951768398L;

    public UserExt(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    private String userId;
}
