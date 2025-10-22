/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.mapping;

import com.jiuqi.nr.annotation.message.FormMappingMessage;
import com.jiuqi.nr.annotation.message.FormSchemeMappingMessage;
import com.jiuqi.nr.annotation.message.LinkMappingMessage;
import com.jiuqi.nr.annotation.message.RegionMappingMessage;
import com.jiuqi.nr.annotation.message.TaskMappingMessage;
import java.util.List;
import java.util.Map;

public interface IAnnotationMappingService {
    public TaskMappingMessage getTaskMapping(TaskMappingMessage var1);

    public FormSchemeMappingMessage getFormSchemeMapping(TaskMappingMessage var1, FormSchemeMappingMessage var2);

    public Map<String, FormMappingMessage> getFormMapping(TaskMappingMessage var1, FormSchemeMappingMessage var2, List<FormMappingMessage> var3);

    public Map<String, RegionMappingMessage> getRegionMapping(TaskMappingMessage var1, FormSchemeMappingMessage var2, FormMappingMessage var3, List<RegionMappingMessage> var4);

    public Map<String, LinkMappingMessage> getLinkMapping(TaskMappingMessage var1, FormSchemeMappingMessage var2, FormMappingMessage var3, RegionMappingMessage var4, List<LinkMappingMessage> var5);
}

