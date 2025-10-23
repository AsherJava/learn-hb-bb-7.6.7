/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.web.vo.ResultVO;
import java.util.List;
import java.util.Map;

public interface IMCSchemeService {
    public void addScheme(MultcheckScheme var1);

    public void modifyScheme(MultcheckScheme var1);

    public void deleteSchemeByKey(String var1);

    public void deleteSchemeByFormScheme(String var1);

    public MultcheckScheme getSchemeByKey(String var1);

    public List<MultcheckScheme> getSchemeByKeys(List<String> var1);

    public List<MultcheckScheme> getSchemeByForm(String var1);

    public List<MultcheckScheme> getSchemeByFSAndOrg(String var1, String var2);

    public List<MultcheckScheme> getByFSAndCode(String var1, String var2);

    public Map<String, List<MultcheckScheme>> getSchemeByTask(String var1);

    public void moveScheme(MultcheckScheme var1, MultcheckScheme var2) throws JQException;

    public List<String> getAllTask();

    public void batchModifyOrg(List<MultcheckScheme> var1);

    public List<MultcheckScheme> getAllSchemes();

    public void deleteOrgByScheme(String var1);

    public void batchAddOrg(List<MultcheckSchemeOrg> var1);

    public List<MultcheckSchemeOrg> getOrgListByScheme(String var1);

    public int getOrgCountByScheme(String var1);

    public List<MultcheckScheme> fuzzySearchScheme(String var1);

    public List<MultcheckItem> getItemInfoList(String var1);

    public List<MultcheckItem> getItemList(String var1);

    public ResultVO addItem(MultcheckItem var1);

    public void batchAddItem(List<MultcheckItem> var1, String var2);

    public String getItemConfig(String var1);

    public void deleteItem(String var1);

    public void deleteItemByScheme(String var1);

    public ResultVO modifyItem(MultcheckItem var1);

    public void moveItem(String var1, String var2);

    public void addItemsByScheme(MultcheckScheme var1);

    public void saveReportDim(String var1, String var2);

    public String getReportDim(String var1);
}

