package com.github.frajimiba.commonstruct.spring.data;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.frajimiba.commonstruct.data.Repository;
import com.github.frajimiba.commonstruct.domain.Entity;

public interface SpringRepository<E extends Entity<PK>, PK extends Serializable>
    extends Repository<E, PK>, JpaRepository<E, PK> {

}