/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.MappingGroup
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.MappingGroup;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import java.util.List;

public interface MappingTransferService {
    public List<String> getSchemeByForm(String var1);

    public List<MappingScheme> getMappingSchemeByFormScheme(String var1);

    public List<MappingScheme> getMappingSchemeByFormScheme(String var1, String var2);

    public List<MappingScheme> getMappingSchemeByTask(String var1);

    public List<MappingScheme> getMappingSchemeByTask(String var1, String var2);

    public MappingScheme createScheme(NrMappingSchemeDTO var1) throws JQException;

    public String createGroup(MappingGroup var1) throws JQException;

    public void batchAddOrgMapping(String var1, List<OrgMapping> var2);

    public void addBaseDataMapping(String var1, List<BaseDataMapping> var2);

    public void addBaseDataItemMapping(String var1, List<BaseDataItemMapping> var2);

    public void saveBaseDataItemMappings(String var1, String var2, List<BaseDataItemMapping> var3);
}

