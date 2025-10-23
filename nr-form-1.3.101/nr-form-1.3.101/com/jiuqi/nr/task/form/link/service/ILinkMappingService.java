/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.link.service;

import com.jiuqi.nr.task.form.controller.dto.EntityModeDTO;
import com.jiuqi.nr.task.form.controller.dto.LinkMappingDataDTO;
import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import java.util.List;

public interface ILinkMappingService {
    public List<EntityModeDTO> queryEntityLevel(List<String> var1);

    public void saveLinkMapping(String var1, List<LinkMappingDTO> var2);

    public LinkMappingDataDTO queryLinkMappingData(String var1, List<LinkMappingDTO> var2);
}

