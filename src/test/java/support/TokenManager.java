package support;

import api.controller.TokenClient;
import enums.UserRole;

import java.util.EnumMap;

public final class TokenManager {

    private static final ThreadLocal<UserRole> CURRENT_ROLE =
            new ThreadLocal<>();

    private static final ThreadLocal<EnumMap<UserRole, String>> THREAD_TOKENS =
            ThreadLocal.withInitial(
                    () -> new EnumMap<>(UserRole.class)
            );

    private TokenManager() {
    }

    public static void setCurrentRole(UserRole role) {
        CURRENT_ROLE.set(role);
    }

    public static String getToken() {
        UserRole role = CURRENT_ROLE.get();

        if (role == null) {
            throw new IllegalStateException(
                    "User role is not set. " +
                            "Set the role before requesting a token."
            );
        }

        return getToken(role);
    }

    public static String getToken(UserRole role) {
        return THREAD_TOKENS
                .get()
                .computeIfAbsent(
                        role,
                        TokenManager::fetchToken
                );
    }

    private static String fetchToken(UserRole role) {
        return switch (role) {
            case GUEST -> TokenClient.getGuestToken();
            case AUTHORIZED -> TokenClient.getAuthorizedToken();
        };
    }

    public static void clear() {
        CURRENT_ROLE.remove();
        THREAD_TOKENS.remove();
    }
}