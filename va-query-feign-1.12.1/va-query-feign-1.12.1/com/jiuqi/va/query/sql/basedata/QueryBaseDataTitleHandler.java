/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.basedata;

import com.jiuqi.va.query.basedata.dto.DCBaseDataDTO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;
import java.util.Map;

public interface QueryBaseDataTitleHandler {
    public List<Map<String, Object>> codeToTitle(List<Map<String, Object>> var1, List<TemplateFieldSettingVO> var2);

    public List<Map<String, Object>> codeToTitle(List<Map<String, Object>> var1, List<TemplateFieldSettingVO> var2, Map<String, Map<String, DCBaseDataDTO>> var3);

    public List<String> listObjectCodeByTableNameAndAuth(String var1);

    public DCBaseDataDTO getBaseDataByCode(String var1, String var2, TemplateFieldSettingVO var3);
}

