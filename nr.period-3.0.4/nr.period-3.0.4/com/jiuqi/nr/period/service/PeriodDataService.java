/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.period.service;

import com.jiuqi.nr.period.common.rest.ErrorPosInfo;
import com.jiuqi.nr.period.common.rest.PeriodDataObject;
import com.jiuqi.nr.period.common.rest.PeriodObject;
import com.jiuqi.nr.period.common.rest.SimpleTitleObj;
import com.jiuqi.nr.period.common.rest.VailFormObject;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PeriodDataService {
    public List<IPeriodRow> queryPeriodDataByPeriodCode(String var1) throws Exception;

    public IPeriodRow queryPeriodDataByPeriodCodeAndDataCode(String var1, String var2) throws Exception;

    public void insertCustomPeriod(IPeriodRow var1, String var2) throws Exception;

    public void updateCustomPeriod(IPeriodRow var1, String var2) throws Exception;

    public void deleteCustomPeriodData(IPeriodRow var1, String var2) throws Exception;

    public void deleteCustomPeriodDatas(List<String> var1, String var2) throws Exception;

    public PeriodObject autoCreateCustomDataCode(String var1) throws Exception;

    public void handleValidForm(VailFormObject var1) throws Exception;

    public void handleValidDate(PeriodDataObject var1) throws Exception;

    public void exportData(String var1, HttpServletResponse var2) throws Exception;

    public List<ErrorPosInfo> importData(String var1, MultipartFile var2) throws Exception;

    public void checkTitle(IPeriodEntity var1, PeriodDataDefineImpl var2, String var3) throws Exception;

    public List<IPeriodRow> queryPeriodDataByPeriodCodeLanguage(String var1, String var2) throws Exception;

    public IPeriodRow queryPeriodDataByPeriodCodeAndDataCodeLanguage(String var1, String var2, String var3) throws Exception;

    public void insertCustomPeriodLanguage(IPeriodRow var1, String var2, String var3) throws Exception;

    public void updateCustomPeriodLanguage(IPeriodRow var1, String var2, String var3) throws Exception;

    public void updatePeriod13DataLanguage(IPeriodRow var1, String var2, String var3) throws Exception;

    public void updateSimpleTitle(SimpleTitleObj var1) throws Exception;
}

