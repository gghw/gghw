package com.ff.gghw.configs;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;
import static org.junit.Assert.*;

import com.ff.gghw.configs.AppConfigTempDB;
import com.ff.gghw.apps.CommandLineApp;

public class AppConfigTempDBTest {
    @Test
    public void testCanInstantiateContextAndGetCliApp() {
        ApplicationContext appCtx = new AnnotationConfigApplicationContext(AppConfigTempDB.class);
        CommandLineApp cliApp = appCtx.getBean("cliApp", CommandLineApp.class);
        assertNotNull(cliApp);
    }
}

