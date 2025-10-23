/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import java.util.List;
import java.util.Map;

public class LinkMappingDataDTO {
    List<LinkMappingDTO> linkMapping;
    Map<String, List<String>> relations;

    public List<LinkMappingDTO> getLinkMapping() {
        return this.linkMapping;
    }

    public void setLinkMapping(List<LinkMappingDTO> linkMapping) {
        this.linkMapping = linkMapping;
    }

    public Map<String, List<String>> getRelations() {
        return this.relations;
    }

    public void setRelations(Map<String, List<String>> relations) {
        this.relations = relations;
    }
}

