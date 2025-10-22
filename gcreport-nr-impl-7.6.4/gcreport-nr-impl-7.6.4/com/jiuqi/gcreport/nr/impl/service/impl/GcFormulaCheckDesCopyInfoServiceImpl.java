/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.copydes.CheckDesCopyParam
 *  com.jiuqi.nr.dataentry.copydes.CopyDesResult
 *  com.jiuqi.nr.dataentry.copydes.ICopyDesService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.nr.impl.dto.GcFormulaCheckDesCopyInfoDTO;
import com.jiuqi.gcreport.nr.impl.service.GcFormulaCheckDesCopyInfoService;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.copydes.CheckDesCopyParam;
import com.jiuqi.nr.dataentry.copydes.CopyDesResult;
import com.jiuqi.nr.dataentry.copydes.ICopyDesService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFormulaCheckDesCopyInfoServiceImpl
implements GcFormulaCheckDesCopyInfoService {
    private Logger logger = LoggerFactory.getLogger(GcFormulaCheckDesCopyInfoServiceImpl.class);
    @Autowired
    private ICopyDesService iCopyDesService;

    @Override
    public void getFormulaCheckDesCopyInfoMassage(GcFormulaCheckDesCopyInfoDTO formulaCheckDesCopyInfoDTO) {
        GcFormulaCheckDesCopyInfoParam gcFormulaCheckDesCopyInfo = formulaCheckDesCopyInfoDTO.getFormulaCheckDesCopyInfoParam();
        AsyncTaskMonitor asyncTaskMonitor = formulaCheckDesCopyInfoDTO.getAsyncTaskMonitor();
        this.getFormulaCheckDesCopyInfoMassage(gcFormulaCheckDesCopyInfo, asyncTaskMonitor);
    }

    private JtableContext getJtableContext(GcFormulaCheckDesCopyInfoParam formulaCheckDesCopyInfo) {
        JtableContext jtableContext = formulaCheckDesCopyInfo.getJtableContext();
        Map dimensionSet = jtableContext.getDimensionSet();
        List orgIds = formulaCheckDesCopyInfo.getOrgIds();
        if (!CollectionUtils.isEmpty((Collection)orgIds)) {
            ((DimensionValue)dimensionSet.get("MD_ORG")).setValue(orgIds.stream().collect(Collectors.joining(";")));
        } else {
            ((DimensionValue)dimensionSet.get("MD_ORG")).setValue("");
        }
        if (dimensionSet.containsKey("MD_CURRENCY")) {
            List currncyCodes = formulaCheckDesCopyInfo.getCurrncyCodes();
            if (!CollectionUtils.isEmpty((Collection)currncyCodes)) {
                ((DimensionValue)dimensionSet.get("MD_CURRENCY")).setValue(currncyCodes.stream().collect(Collectors.joining(";")));
            } else {
                ((DimensionValue)dimensionSet.get("MD_CURRENCY")).setValue("");
            }
        }
        if (dimensionSet.containsKey("MD_GCORGTYPE")) {
            ((DimensionValue)dimensionSet.get("MD_GCORGTYPE")).setValue("");
        }
        return jtableContext;
    }

    private void getFormulaCheckDesCopyInfoMassage(GcFormulaCheckDesCopyInfoParam formulaCheckDesCopyInfoParam, AsyncTaskMonitor monitor) {
        try {
            JtableContext jtableContext = this.getJtableContext(formulaCheckDesCopyInfoParam);
            monitor.progressAndMessage(0.05, "\u6b63\u5728\u7ec4\u7ec7\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53c2\u6570...");
            CheckDesCopyParam checkDesCopyParam = new CheckDesCopyParam();
            checkDesCopyParam.setTargetDimensionSet(jtableContext.getDimensionSet());
            checkDesCopyParam.setTargetFormSchemeKey(jtableContext.getFormSchemeKey());
            checkDesCopyParam.setTargetFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
            monitor.progressAndMessage(0.4, "\u6b63\u5728\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e...");
            CopyDesResult returnInfo = this.iCopyDesService.copy(checkDesCopyParam);
            monitor.progressAndMessage(0.9, "\u6b63\u5728\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e...");
            monitor.finish("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u590d\u5236\u5b8c\u6210", (Object)returnInfo.getUsrLogText());
        }
        catch (Exception e) {
            monitor.error("\u5ba1\u6838\u9519\u8bef\u4fe1\u606f\u590d\u5236\u5904\u7406\u4efb\u52a1\u51fa\u9519:" + e.getMessage(), (Throwable)e);
            this.logger.error("\u5ba1\u6838\u9519\u8bef\u4fe1\u606f\u590d\u5236\u5904\u7406:" + e.getMessage());
            throw new BusinessRuntimeException("\u5ba1\u6838\u9519\u8bef\u4fe1\u606f\u590d\u5236\u5904\u7406:" + e.getMessage(), (Throwable)e);
        }
    }
}

