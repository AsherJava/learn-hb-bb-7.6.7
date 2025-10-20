/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeEO;
import com.jiuqi.gcreport.clbr.executor.model.ClbrSchemeExcelModel;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO;
import java.util.List;

public interface ClbrSchemeService {
    public ClbrSchemeVO save(ClbrSchemeVO var1);

    public boolean edit(ClbrSchemeVO var1);

    public PageInfo<ClbrSchemeVO> list(ClbrSchemeCondition var1);

    public List<ClbrSchemeEO> listByCondition(ClbrSchemeCondition var1);

    public List<ClbrSchemeDTO> listByConditionToDTO(ClbrSchemeCondition var1);

    public void delete(List<String> var1);

    public StringBuilder clbrSchemeImport(List<ClbrSchemeExcelModel> var1);

    public List<ClbrSchemeExcelModel> clbrSchemeExport(ClbrSchemeCondition var1);

    public ClbrSchemeDTO getFirstClbrScheme(String var1, String var2, String var3, String var4);

    public List<ClbrSchemeTreeVO> listTree();
}

