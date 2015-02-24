package com.ff.gghw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

import com.ff.gghw.configs.ITestAppConfig;

public abstract class TestBase {
    protected ApplicationContext newCtx() {
        return new AnnotationConfigApplicationContext(ITestAppConfig.class);
    }
}

