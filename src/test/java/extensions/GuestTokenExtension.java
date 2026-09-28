package extensions;

import enums.UserRole;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import support.TokenManager;

public class GuestTokenExtension
        implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        TokenManager.setCurrentRole(UserRole.GUEST);
        TokenManager.getToken();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        TokenManager.clear();
    }
}
