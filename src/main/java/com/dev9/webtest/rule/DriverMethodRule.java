package com.dev9.webtest.rule;

import com.google.common.annotations.VisibleForTesting;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static java.lang.String.format;

/**
 * User: yurodivuie
 * Date: 5/23/13
 * Time: 9:15 AM
 */
public class DriverMethodRule extends TestWatcher {

    @VisibleForTesting
    protected static final String STARTED_MESSAGE = "STARTED";

    @VisibleForTesting
    protected static final String FAILED_MESSAGE = "FAILED: ";

    @VisibleForTesting
    protected static final String PASSED_MESSAGE = "PASSED";

    private final DriverClassRule driverClassRule;

    public DriverMethodRule(DriverClassRule driverClassRule) {
        this.driverClassRule = driverClassRule;
    }

    @Override
    protected void succeeded(Description description) {
        logForTest(description, PASSED_MESSAGE);
        super.succeeded(description);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void failed(Throwable e, Description description) {
        driverClassRule.reportFailure();
        logForTest(description, FAILED_MESSAGE + e.getMessage());
        super.failed(e, description);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        super.skipped(e, description);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void starting(Description description) {
        logForTest(description, STARTED_MESSAGE);
        super.starting(description);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void logForTest(Description description, String message) {
        driverClassRule.logInContext(format("%s: [%s]", getTestName(description), message));
    }

    private String getTestName(Description description) {
        return description.getTestClass().getSimpleName() + "." + description.getMethodName();
    }
}
