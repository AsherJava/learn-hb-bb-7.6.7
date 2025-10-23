/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.mapping2.bean.MappingConfig;
import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.dto.JIOStateDTO;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.web.vo.EntityVO;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import java.util.List;

public interface JIOConfigService {
    public void saveJIOFile(String var1, String var2);

    public String getJIOFileByMs(String var1);

    public void saveJIOConfig(byte[] var1, String var2);

    public byte[] getJIOConfigByMs(String var1);

    public void saveJIOContent(JIOContent var1, String var2);

    public JIOContent getJIOContentByMs(String var1);

    public List<JIOContent> batchGetJIOContentByMs(List<String> var1);

    public void saveJIO(String var1, String var2, byte[] var3, JIOContent var4);

    public boolean isJIOScheme(MappingScheme var1);

    public boolean isJIOScheme(NrMappingParam var1);

    public JIOStateDTO isJIOSchemeWithFile(MappingScheme var1);

    public JIOStateDTO isJIOSchemeWithFile(String var1, NrMappingParam var2);

    public boolean isJIO(String var1);

    public boolean isJIO(MappingScheme var1);

    public void deleteByMS(String var1);

    public MappingConfig queryMappingConfig(String var1) throws JQException;

    public void updateMappingConfig(String var1, MappingConfig var2) throws JQException;

    public List<EntityVO> queryEntityAttribute(String var1) throws Exception;

    public List<FormulaSchemeDefine> getFormulaSchemesByReport(String var1);

    public String queryEntityIdByFormSchemeKey(String var1) throws Exception;
}

