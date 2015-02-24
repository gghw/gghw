package com.ff.gghw.configs;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

import com.ff.gghw.configs.ITestAppConfig;
import com.ff.gghw.apps.CommandLineApp;

public class ITestAppConfigTest {
    @Test
    public void testCanInstantiateContextAndGetCliApp() {
        ApplicationContext appCtx = new AnnotationConfigApplicationContext(ITestAppConfig.class);
        CommandLineApp cliApp = appCtx.getBean("cliApp", CommandLineApp.class);
        assertNotNull(cliApp);
    }
}

