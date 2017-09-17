package com.github.frajimiba.commonstruct.jee5.data.validation.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

/**
 * Simple support for bean validation api in jpa 1.0 environments.
 * 
 * @author Francisco José Jiménez
 *
 */
public class ValidationEventListener {

	/**
	 * Validates an entity in prePersist and preUpdate phases.
	 *  
	 * @param entity Entity to validate.
	 */
	@PrePersist
    @PreUpdate
    public void validate(Object entity) {
		TraversableResolver tr = new SimpleTraversableResolver();
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		ValidatorContext vc = vf.usingContext();
        Validator validator = vc.traversableResolver(tr).getValidator();
        
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
        	
            Set<ConstraintViolation<?>> propagatedViolations = 
            		new HashSet<ConstraintViolation<?>>();
            
            Set<String> classNames = new HashSet<String>();
            for (ConstraintViolation<?> violation : constraintViolations) {
                propagatedViolations.add(violation);
                classNames.add(violation.getLeafBean().getClass().getName());
            }
            
            StringBuilder builder = new StringBuilder();
            builder.append("validation failed for classes ");
            builder.append(classNames);
            throw new ConstraintViolationException(builder.toString(), propagatedViolations);
        }
    }
	
}