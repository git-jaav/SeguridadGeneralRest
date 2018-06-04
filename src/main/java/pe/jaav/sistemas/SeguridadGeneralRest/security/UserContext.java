package pe.jaav.sistemas.SeguridadGeneralRest.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author araucoj
 *
 */
public class UserContext {
    private Long userId;
    private String userCode;
    
    private final List<GrantedAuthority> authorities;

    private UserContext(Long userId, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    private UserContext(String userCode, List<GrantedAuthority> authorities) {
        this.userCode = userCode;
        this.authorities = authorities;
    }
    
    public static UserContext create(Long userId,  List<GrantedAuthority> authorities) {
        if (userId == null) throw new IllegalArgumentException("Usuario es requerido");
        return new UserContext(userId,  authorities);
    }

    public static UserContext create(String userCode,  List<GrantedAuthority> authorities) {
        if (userCode == null) throw new IllegalArgumentException("Usuario es requerido");
        return new UserContext(userCode,  authorities);
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getUserId() {
        return userId;
    }

	public String getUserCode() {
		return userCode;
	}


}
