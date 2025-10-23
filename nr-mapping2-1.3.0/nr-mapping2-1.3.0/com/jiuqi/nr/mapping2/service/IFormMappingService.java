/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.nr.mapping2.dto.FormMappingDTO;
import com.jiuqi.nr.mapping2.web.vo.CommonTreeNode;
import java.util.List;

public interface IFormMappingService {
    public List<FormMappingDTO> list(String var1, String var2);

    public void create(String var1, List<FormMappingDTO> var2);

    public void update(String var1, List<FormMappingDTO> var2);

    public void clean(String var1, String var2);

    public List<CommonTreeNode> initTree(String var1);
}

