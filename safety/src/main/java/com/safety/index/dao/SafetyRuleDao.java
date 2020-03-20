package com.safety.index.dao;

import com.safety.common.dao.IRepository;
import com.safety.index.model.BaseSafetyRule;
import org.springframework.stereotype.Repository;

@Repository
public interface SafetyRuleDao extends IRepository<BaseSafetyRule, String> {
}
