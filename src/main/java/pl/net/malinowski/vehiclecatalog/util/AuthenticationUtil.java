package pl.net.malinowski.vehiclecatalog.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthenticationUtil {

    public static String getAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("PRIVILEGE_ADMIN_READ"));
    }


}
