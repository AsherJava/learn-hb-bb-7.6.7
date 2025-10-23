/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.mapping.bean.MappingConfig;
import com.jiuqi.nr.mapping.web.vo.EntityVO;
import java.util.List;

public interface MappingSchemeConfigService {
    public MappingConfig query(String var1) throws JQException;

    public void update(String var1, MappingConfig var2) throws JQException;

    public List<EntityVO> queryEntityAttribute(String var1) throws Exception;

    public List<FormulaSchemeDefine> getFormulaSchemesByReport(String var1);

    public String queryEntityIdByFormSchemeKey(String var1) throws Exception;
}

