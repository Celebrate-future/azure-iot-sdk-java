package tests.integration.com.microsoft.azure.sdk.iot.helpers.rules;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import tests.integration.com.microsoft.azure.sdk.iot.helpers.IntegrationTest;
import tests.integration.com.microsoft.azure.sdk.iot.helpers.annotations.ErrInjTest;
import tests.integration.com.microsoft.azure.sdk.iot.helpers.annotations.IotHubTest;

public class ErrInjTestRule implements TestRule
{
    @Override
    public Statement apply(Statement base, Description description) {
        return new IgnorableStatement(base, description);
    }

    private static class IgnorableStatement extends Statement {

        private final Statement base;
        private final Description description;

        public IgnorableStatement(Statement base, Description description) {
            this.base = base;
            this.description = description;
        }

        @Override
        public void evaluate() throws Throwable {
            ErrInjTest annotation = description.getAnnotation(ErrInjTest.class);
            ErrInjTest classAnnotation = description.getTestClass().getAnnotation(ErrInjTest.class);
            if (annotation != null || classAnnotation != null)
            {
                Assume.assumeTrue("Test is ignored because it is an error injection test and the RUN_ERRINJ_TESTS environment variable is set to false", IntegrationTest.runErrInjTests);
            }

            base.evaluate();
        }
    }
}
