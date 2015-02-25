package com.ff.gghw.configs;

import java.io.BufferedReader;
import java.io.PrintStream;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ff.gghw.configs.ATestAppConfig;
import com.ff.gghw.apps.CommandLineApp;

public class ATestAppConfigTest {
    @Test
    public void testCanInstantiateContextAndGetCliApp() {
        BufferedReader in = mock(BufferedReader.class);
        PrintStream out = mock(PrintStream.class);
        
        ConfigurableApplicationContext appCtx = new AnnotationConfigApplicationContext(ATestAppConfig.class);
        appCtx.getBeanFactory().registerSingleton("test_in", in);
        appCtx.getBeanFactory().registerSingleton("test_out", out);
        
        CommandLineApp cliApp = appCtx.getBean("cliApp", CommandLineApp.class);
        assertNotNull(cliApp);
        assertSame(in, appCtx.getBean("in"));
        assertSame(out, appCtx.getBean("out"));
        verifyNoMoreInteractions(in, out);
    }
}

