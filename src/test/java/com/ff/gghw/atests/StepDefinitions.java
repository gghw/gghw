package com.ff.gghw.atests;

import static org.junit.Assert.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import static org.hamcrest.CoreMatchers.containsString;

import com.ff.gghw.apps.CommandLineApp;
import com.ff.gghw.configs.ATestAppConfig;

public class StepDefinitions {
    private String input = "";
    
    @Given("^(.*) has applied for a (\\d+).(\\d+) EUR loan with a (\\d+) day term$")
    public void has_applied_for_a_loan(String client, int euros, int cents, int days) throws Throwable {
        input += "apply\n" + client + "\n" + (100*euros+cents) + "\n" + days + "\n1.2.3.4\n";
    }
    
    @When("^(.*) applies for a (\\d+).(\\d+) EUR loan with a (\\d+) day term$")
    public void applies_for_a_loan(String client, int euros, int cents, int days) throws Throwable {
        input += "apply\n" + client + "\n" + (100*euros+cents) + "\n" + days + "\n1.2.3.4\n";
    }
    
    @When("^(.*) extends loan (\\d+)$")
    public void extends_loan(String client, int loan_id) throws Throwable {
        input += "extend\n" + loan_id + "\n";
    }
    
    @Then("^(.*) should have a loan with (\\d+).(\\d+) EUR sum and (\\d+).(\\d+) EUR interest$")
    public void should_have_loan_with_interest(String client, int sum_euros, int sum_cents
            , int interest_euros, int interest_cents) throws Throwable {
        String lastCommandOutput = lastCommandOutput(input+"list\n"+client+"\n");
        assertThat(lastCommandOutput, containsString("client=" + client
            + ", sum=" + (100*sum_euros+sum_cents)
            + ", interest=" + (100*interest_euros+interest_cents)
            + ", dueDate="));
    }
    
    private String lastCommandOutput(String input) {
        String terminatedInput = input + "exit\n";
        
        BufferedReader in = new BufferedReader(new StringReader(terminatedInput));
        ByteArrayOutputStream outBytes = new java.io.ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBytes);
        
        ConfigurableApplicationContext appCtx = new AnnotationConfigApplicationContext(ATestAppConfig.class);
        appCtx.getBeanFactory().registerSingleton("ccin", in);
        appCtx.getBeanFactory().registerSingleton("ccout", out);
        
        CommandLineApp cliApp = appCtx.getBean("cliApp", CommandLineApp.class);
        try { cliApp.run(); } catch ( Exception e ) { assertTrue(false); }
        
        String output = outBytes.toString();
        
        int last = output.lastIndexOf("Available commands:");
        if ( last != -1 ) {
            output = output.substring(0, last);
            last = output.lastIndexOf("Available commands:");
            if ( last != -1 ) {
                output = output.substring(last);
            }
        }
        
        return output;
    }
}
