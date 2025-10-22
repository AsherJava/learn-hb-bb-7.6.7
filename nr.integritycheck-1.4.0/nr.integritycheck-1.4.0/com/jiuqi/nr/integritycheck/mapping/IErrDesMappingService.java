/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.mapping;

import com.jiuqi.nr.integritycheck.message.FormMappingMessage;
import com.jiuqi.nr.integritycheck.message.FormSchemeMappingMessage;
import com.jiuqi.nr.integritycheck.message.TaskMappingMessage;
import java.util.List;
import java.util.Map;

public interface IErrDesMappingService {
    public TaskMappingMessage getTaskMapping(TaskMappingMessage var1);

    public FormSchemeMappingMessage getFormSchemeMapping(TaskMappingMessage var1, FormSchemeMappingMessage var2);

    public Map<String, FormMappingMessage> getFormMapping(TaskMappingMessage var1, FormSchemeMappingMessage var2, List<FormMappingMessage> var3);
}

