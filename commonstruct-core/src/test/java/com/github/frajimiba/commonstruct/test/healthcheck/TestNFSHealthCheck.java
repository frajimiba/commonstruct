package com.github.frajimiba.commonstruct.test.healthcheck;

import java.io.File;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.github.frajimiba.commonstruct.healthcheck.AbstractNFSHealthCheck;
import com.github.frajimiba.commonstruct.io.ApplicationPath;

public class TestNFSHealthCheck extends AbstractNFSHealthCheck {
  
  @Rule
  public TemporaryFolder temp = new TemporaryFolder();
   
  @Override
  public ApplicationPath getApplicationPath() {
    return new ApplicationPath(){
    
      @Override
      public File getRootPath() {
        return checkFloder("/test");
      }

      @Override
      public File getTempFilePath() {
        return checkFloder(getRootPath().getAbsolutePath() + "/temp");
      }

      @Override
      public File getFilePath() {
        return checkFloder(getRootPath().getAbsolutePath() + "/file");
      }

      @Override
      public File getLogPath() {
        return checkFloder(getRootPath().getAbsolutePath() + "/log");
      }
      
      @Override
      public File getConfigPath() {
        return checkFloder(getRootPath().getAbsolutePath() + "/config");
      }
      
      private File checkFloder(String folder) {
        File dir = new File(folder);
        if (!dir.exists()) {
          dir.mkdir();
        }
        return dir;
      }
    };
  }
    
  @Override
  public String getName() {
    return "TestNFSHealthCheck";
  }

}
