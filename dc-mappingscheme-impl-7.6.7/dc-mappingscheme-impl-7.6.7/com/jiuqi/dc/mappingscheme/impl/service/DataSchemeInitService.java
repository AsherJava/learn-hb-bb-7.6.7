/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo
 */
package com.jiuqi.dc.mappingscheme.impl.service;

import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo;
import com.jiuqi.dc.mappingscheme.impl.common.DataSchemeInit;
import java.util.Set;

public interface DataSchemeInitService {
    public SchemeDefaultDataVO getDefaultSchemeData(DataSchemeDTO var1);

    public SchemeDefaultVO getDefaultData(DataSchemeDTO var1);

    public Boolean initSchemeDataConfig(DataSchemeDTO var1, DataSchemeInit var2);

    public Set<SchemeDimVo> getDimList(String var1);
}

