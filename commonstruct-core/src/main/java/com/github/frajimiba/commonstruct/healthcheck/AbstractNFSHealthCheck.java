package com.github.frajimiba.commonstruct.healthcheck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Common NFS HealthCheck.
 * 
 * @author Francisco José Jiménez
 *
 */
public abstract class AbstractNFSHealthCheck implements NFSHealthCheck {

  private static final Logger LOG = Logger.getLogger(AbstractNFSHealthCheck.class.getName());

  private HealthCheckStatus status;

  /**
   * {@inheritDoc}
   */
  @Override
  public void check() {
    this.status = HealthCheckStatus.KO;

    OutputStream outputStream = null;
    InputStream inputStream = null;

    try {

      File tempFile = new File(this.getApplicationPath().getTempFilePath(), UUID.randomUUID().toString());

      inputStream = this.getClass().getClassLoader().getResourceAsStream("com/github/frajimiba/commonstruct/healthcheck/NFSFile.tmp");
      outputStream = new FileOutputStream(tempFile);

      int c;
      while ((c = inputStream.read()) != -1) {
        outputStream.write(c);
      }

      closeStreams(outputStream, inputStream);

      if (tempFile.delete()) {
        this.status = HealthCheckStatus.OK;
      } else {
        LOG.log(Level.SEVERE, "ERROR AL CHECKEAR NFS");
      }
    } catch (IOException ex) {
      LOG.log(Level.SEVERE, "ERROR AL CHECKEAR NFS", ex);
    } finally {
      closeStreams(outputStream, inputStream);
    }
    
    // JAVA 7 - NIO2 - Nos perimitirá distinguir por sistema de archivos, importante para testing
    
    // FileSystem fileSystem = FileSystems.getDefault(); // Definir en el ApplicationPath
    // Path path = fileSystem.getPath(this.getApplicationPath().getTempFilePath().getAbsolutePath(), UUID.randomUUID().toString());
    
    // File tempFile = new File(this.getApplicationPath().getTempFilePath(), UUID.randomUUID().toString());
    // inputStream = this.getClass().getClassLoader().getResourceAsStream("com/github/frajimiba/commonstruct/healthcheck/NFSFile.tmp");
    // outputStream = Files.newOutputStream(path);

    // int c;
    // while ((c = inputStream.read()) != -1) {
    //  outputStream.write(c);
    // }

    // closeStreams(outputStream, inputStream);

    // Files.delete(path);
    // this.status = HealthCheckStatus.OK;

  }

  private void closeStreams(OutputStream outputStream, InputStream inputStream) {
    try {
      if (inputStream != null) {
        inputStream.close();
      }
      if (outputStream != null) {
        outputStream.close();
      }
    } catch (IOException closeEx) {
      LOG.log(Level.SEVERE, "ERROR AL CHECKEAR NFS", closeEx);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HealthCheckStatus getStatus() {
    return this.status;
  }
}