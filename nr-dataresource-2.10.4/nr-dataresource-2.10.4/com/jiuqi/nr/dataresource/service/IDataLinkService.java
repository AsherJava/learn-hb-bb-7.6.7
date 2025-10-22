/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataresource.service;

import com.jiuqi.nr.dataresource.DataResourceLink;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.dto.SearchDataFieldDTO;
import com.jiuqi.nr.dataresource.web.param.DataResourceQuery;
import com.jiuqi.nr.datascheme.api.DataField;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface IDataLinkService {
    public DataResourceLink init();

    public void insert(List<DataResourceLink> var1);

    public void delete(String var1, List<String> var2);

    public void delete(String var1);

    public void update(List<DataResourceLink> var1);

    @Transactional(readOnly=true)
    public List<DataField> getByGroupNoPeriod(String var1);

    public List<DataField> getByGroup(String var1);

    public List<DataResourceLink> getByDataFieldKey(String var1);

    public List<SearchDataFieldDTO> searchByPeriod(DataResourceQuery var1);

    public List<SearchDataFieldDTO> searchByDefineKey(String var1, String var2);

    public List<DimAttribute> getDimAttributeByGroup(String var1);

    @Deprecated
    public List<DimAttribute> getFmDimAttribute(String var1, String var2, String var3);

    public DimAttribute initDimAttribute();

    public void setAttribute(List<DimAttribute> var1);
}

