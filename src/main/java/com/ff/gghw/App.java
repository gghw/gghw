package com.ff.gghw;

import java.util.logging.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

import com.ff.gghw.apps.CommandLineApp;
import com.ff.gghw.configs.AppConfigTempDB;

public class App {
    public static void main(String[] args) {
        // This is probably a really bad way of getting rid of unwanted logs, but I'll use it for this toy problem.
        LogManager.getLogManager().reset();
        try {
            ApplicationContext appCtx = new AnnotationConfigApplicationContext(AppConfigTempDB.class);
            CommandLineApp cliApp = appCtx.getBean("cliApp", CommandLineApp.class);
            cliApp.run();
        }
        catch ( Exception e ) {
            System.out.println("Crashed! Probably should log exception info somewhere, but I won't.");
        }
    }
}

