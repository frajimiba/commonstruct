package com.github.frajimiba.commonstruct.spring.audit.data;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class AuditableRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
    extends JpaRepositoryFactoryBean<R, T, ID> {

  protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
    return new AuditableRepositoryFactory<T, ID>(entityManager);
  }

  private static class AuditableRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {

    private final EntityManager entityManager;

    public AuditableRepositoryFactory(EntityManager entityManager) {

      super(entityManager);
      this.entityManager = entityManager;
    }

    @SuppressWarnings({ "unchecked" })
    protected Object getTargetRepository(RepositoryMetadata metadata) {
      JpaEntityInformation<T, Serializable> entityInformation = (JpaEntityInformation<T, Serializable>) getEntityInformation(
          metadata.getDomainType());
      Class<?> repositoryInterface = metadata.getRepositoryInterface();
      return AuditableRepository.class.isAssignableFrom(repositoryInterface)
          ? new AuditableRepositoryImp<T, ID>(entityInformation, entityManager) : super.getTargetRepository(metadata);
    }

    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      return AuditableRepositoryImp.class;
    }
  }

}