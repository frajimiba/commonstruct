package com.github.frajimiba.commonstruct.io;

import java.io.File;
import javax.annotation.Resource;

/**
 * Common application path.
 * 
 * @author Francisco José Jiménez
 *
 */
public class BaseApplicationPath implements ApplicationPath {

  @Resource(name = "PATH_REPO")
  private String rootPath;
  @Resource(name = "RELATIVE_PATH_TEMPS")
  private String tempsPath;
  @Resource(name = "RELATIVE_PATH_FILES")
  private String filesPath;
  @Resource(name = "RELATIVE_PATH_CONFIG")
  private String configPath;
  @Resource(name = "RELATIVE_PATH_LOG")
  private String logPath;
  /**
   * {@inheritDoc}
   */
  @Override
  public File getRootPath() {
    return checkFloder(rootPath);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public File getFilePath() {
    return checkFloder(this.getRootPath().getAbsolutePath() + filesPath);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public File getTempFilePath() {
    return checkFloder(this.getRootPath().getAbsolutePath() + tempsPath);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public File getConfigPath() {
    return checkFloder(this.getRootPath().getAbsolutePath() + configPath);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public File getLogPath() {
    return checkFloder(this.getRootPath().getAbsolutePath() + logPath);
  }

  private File checkFloder(String folder) {
    File dir = new File(folder);
    if (!dir.exists()) {
      dir.mkdir();
    }
    return dir;
  }
}