/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 */
package com.jiuqi.gcreport.org.impl.fieldManager.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.gcreport.org.impl.fieldManager.entity.GcOrgFieldEO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface GcFieldManagerService {
    public GcOrgFieldVO convertFieldEOToVO(GcOrgFieldEO var1);

    public GcOrgFieldEO convertFieldVOToEO(GcOrgFieldVO var1);

    public List<GcOrgFieldVO> queryAllFieldsByTableName(String var1);

    public void batchUpdateFieldVOS(String var1, List<GcOrgFieldVO> var2);

    public List<OrgFiledComponentVO> getFieldComponent(String var1, boolean var2);

    public List<OrgFiledComponentVO> getFieldComponent(String var1);

    public List<Map<String, Object>> exportExcel(ExportConditionVO var1);

    public PageInfo<Map<String, Object>> listOrgValuesByPage(Boolean var1, String var2, Integer var3, Integer var4, boolean var5);

    public Map<String, Object> getOrgFormData(GcOrgApiParam var1);
}

