package com.ff.gghw.configs;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.PrintStream;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.ff.gghw.configs.ATestAppConfig;
import com.ff.gghw.apps.CommandLineApp;

public class ATestAppConfigTest {
    @Test
    public void testCanInstantiateContextAndGetCliApp() {
        BufferedReader in = mock(BufferedReader.class);
        PrintStream out = mock(PrintStream.class);

        ConfigurableApplicationContext appCtx = new AnnotationConfigApplicationContext(ATestAppConfig.class);
        appCtx.getBeanFactory().registerSingleton("ccin", in);
        appCtx.getBeanFactory().registerSingleton("ccout", out);

        CommandLineApp cliApp = appCtx.getBean("cliApp", CommandLineApp.class);
        assertNotNull(cliApp);
        assertSame(in, appCtx.getBean("in"));
        assertSame(out, appCtx.getBean("out"));
        verifyNoMoreInteractions(in, out);
    }
}

