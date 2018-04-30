package pl.net.malinowski.vehiclecatalog.util;

import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthenticationUtil {

    public static String getAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
