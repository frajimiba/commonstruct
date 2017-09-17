package com.github.frajimiba.commonstruct.test.healthcheck;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.frajimiba.commonstruct.healthcheck.HealthCheckStatus;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import junit.framework.Assert;

public class HeathCheckTest {
  
  private static GreenMail mailServer;
    
  @BeforeClass
  public static void setUpClass() {
    mailServer = new GreenMail(
        new ServerSetup(OkMailSessionHealthCheck.PORT, "localhost", ServerSetup.PROTOCOL_SMTP)
        ).withConfiguration(GreenMailConfiguration.aConfig().
            withUser("user@test.com","user", "password"));
    mailServer.start();
  }
    
  @Test
  public void checkOK(){
    TestHealthCheck healthCheck = new TestHealthCheck();
    healthCheck.addHealthCheck(new AlwaysOkHealthCheck());
    healthCheck.addHealthCheck(new AlwaysOkHealthCheck());
    healthCheck.addHealthCheck(new AlwaysOkHealthCheck());
    healthCheck.check();
    Assert.assertEquals(healthCheck.getStatus(), HealthCheckStatus.OK);
  }
    
  @Test
  public void checkOkMail(){   
    OkMailSessionHealthCheck mailSessionHealthCheck = new OkMailSessionHealthCheck();
    mailSessionHealthCheck.check();
    Assert.assertEquals(mailSessionHealthCheck.getStatus(), HealthCheckStatus.OK);
  }
  
  @Test
  public void checkKoMail(){
    KoMailSessionHealthCheck mailSessionHealthCheck = new KoMailSessionHealthCheck();
    mailSessionHealthCheck.check();
    Assert.assertEquals(mailSessionHealthCheck.getStatus(), HealthCheckStatus.KO);

  }
  
  //@Test
  public void checkNFS(){
    TestNFSHealthCheck nfsHealthCheck = new TestNFSHealthCheck();
    nfsHealthCheck.check();
    Assert.assertEquals(nfsHealthCheck.getStatus(), HealthCheckStatus.OK);
  }
  
  @Test
  public void checkOkDataSource(){
    OkDataSourceHealthCheck dataSourceHealthCheck = new OkDataSourceHealthCheck();
    dataSourceHealthCheck.check();
    Assert.assertEquals(dataSourceHealthCheck.getStatus(), HealthCheckStatus.OK);
  }
  
  @Test
  public void checkKoDataSource(){
    KoDataSourceHealthCheck dataSourceHealthCheck = new KoDataSourceHealthCheck();
    dataSourceHealthCheck.check();
    Assert.assertEquals(dataSourceHealthCheck.getStatus(), HealthCheckStatus.KO);
  }
  
  @Test
  public void checkKO(){
    
    TestHealthCheck healthCheck = new TestHealthCheck();
    healthCheck.addHealthCheck(new AlwaysOkHealthCheck());
    healthCheck.addHealthCheck(new AlwaysKoHealthCheck());
    healthCheck.addHealthCheck(new AlwaysOkHealthCheck());
    healthCheck.check();
    Assert.assertEquals(healthCheck.getStatus(), HealthCheckStatus.KO);
  }

  @AfterClass
  public static void tearDownClass() {
    mailServer.stop();
  }
  
}