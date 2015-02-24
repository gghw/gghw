package com.ff.gghw.configs;

import java.io.BufferedReader;
import java.io.PrintStream;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import com.ff.gghw.apps.CommandLineApp;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.services.AppServices;
import com.ff.gghw.etc.Time;

@Configuration
public abstract class AppConfigBase {
    @Bean
    @Autowired
    public CommandLineApp cliApp(AppServices appServices, Time time, BufferedReader in, PrintStream out) {
        return new CommandLineApp(appServices, time, in, out);
    }
    
    @Bean
    @Autowired
    public AppServices appServices(ApplicationDao applicationDao, ExtensionDao extensionDao, LoanDao loanDao) {
        return new AppServices(applicationDao, extensionDao, loanDao);
    }
    
    @Bean
    @Autowired
    public ApplicationDao applicationDao(SessionFactory sessionFactory) {
        return new ApplicationDao(sessionFactory);
    }
    
    @Bean
    @Autowired
    public ExtensionDao extensionDao(SessionFactory sessionFactory) {
        return new ExtensionDao(sessionFactory);
    }
    
    @Bean
    @Autowired
    public LoanDao loanDao(SessionFactory sessionFactory) {
        return new LoanDao(sessionFactory);
    }
    
    @Bean
    public Time time() {
        return new Time();
    }
}

