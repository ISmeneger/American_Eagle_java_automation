package extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.opentest4j.TestAbortedException;

import java.lang.reflect.Method;

public class KnownDefectExtension implements InvocationInterceptor {

    @Override
    public void interceptTestMethod(
            Invocation<Void> invocation,
            ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {

        KnownDefect knownDefect =
                extensionContext
                        .getRequiredTestMethod()
                        .getAnnotation(KnownDefect.class);

        try {
            invocation.proceed();

        } catch (Throwable throwable) {

            throw new TestAbortedException(
                    "Known defect reproduced: "
                            + knownDefect.value(),
                    throwable
            );
        }

        throw new AssertionError(
                "Known defect test unexpectedly passed. " +
                        "Check whether the defect has been fixed: "
                        + knownDefect.value()
        );
    }
}