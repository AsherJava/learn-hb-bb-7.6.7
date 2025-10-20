/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrReceiveSettingDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrReceiveSettingDTO;
import com.jiuqi.gcreport.clbr.executor.model.ClbrReceiveSettingExcelModel;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ClbrReceiveSettingService {
    public List<ClbrReceiveSettingDTO> listByUserOrRole(String var1, String var2);

    public boolean save(ClbrReceiveSettingVO var1);

    public PageInfo<ClbrReceiveSettingVO> query(ClbrReceiveSettingCondition var1);

    public void delete(List<String> var1);

    public boolean edit(ClbrReceiveSettingVO var1);

    public StringBuilder settingImport(List<ClbrReceiveSettingExcelModel> var1);

    public List<ClbrReceiveSettingExcelModel> settingExport();

    public List<ClbrReceiveSettingVO> queryForUser(ClbrReceiveSettingCondition var1);

    public Map<String, Set<String>> getReceiveClbrType2Relations(String var1, String var2);

    public boolean exist();
}

