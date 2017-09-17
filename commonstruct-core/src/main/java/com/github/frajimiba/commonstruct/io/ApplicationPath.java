package com.github.frajimiba.commonstruct.io;

import java.io.File;

/**
 * The application path.
 * 
 * @author Francisco José Jiménez
 *
 */
public interface ApplicationPath {
  /**
   * The root path of the application.
   * 
   * @return the root path
   */
  File getRootPath();
  /**
   * The temporal file path of the application.
   * 
   * @return the temporal file path
   */
  File getTempFilePath();
  /**
   * The file path of the application.
   * 
   * @return the file path
   */
  File getFilePath();
  /**
   * The log path of the application.
   * 
   * @return the log path
   */
  File getLogPath();
  /**
   * The configuration path of the application.
   * 
   * @return the configuration path
   */
  File getConfigPath();
}