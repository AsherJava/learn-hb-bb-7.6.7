/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO;
import java.util.List;
import java.util.Map;

public interface WorkflowBusinessSchemeService {
    public List<WorkflowBusinessSchemeDTO> list(WorkflowBusinessSchemeDTO var1);

    public List<Map<String, Object>> get(WorkflowBusinessSchemeDTO var1);

    public void update(WorkflowBusinessSchemeDTO var1);

    public void delete(WorkflowBusinessSchemeDTO var1);

    public void add(WorkflowBusinessSchemeDTO var1);
}

