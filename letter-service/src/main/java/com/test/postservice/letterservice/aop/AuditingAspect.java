package com.test.postservice.letterservice.aop;

import com.test.postservice.letterservice.entity.Audit;
import com.test.postservice.letterservice.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Aspect
public class AuditingAspect {

    private final AuditService auditService;

    public AuditingAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @AfterReturning(value = "@annotation(auditable)", returning = "result")
    @Transactional
    public void logAuditActivity(JoinPoint jp, Auditable auditable, boolean result) {
        Date date = new Date();
        if (result) {
            auditService.save(
                    new Audit(
                            auditable.entityType().getDescription(),
                            auditable.actionType().getDescription(),
                            jp.getArgs()[0].toString(),
                            date));
        }
        }
}
