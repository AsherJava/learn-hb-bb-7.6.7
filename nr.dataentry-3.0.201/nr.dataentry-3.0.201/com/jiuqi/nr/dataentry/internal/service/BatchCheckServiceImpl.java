/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.helper.CheckHelper
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.helper.CheckHelper;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.dataentry.monitor.LogicProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchCheckServiceImpl
implements IBatchCheckService {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckServiceImpl.class);
    @Autowired
    AsyncTaskManager asyncTaskManager;
    @Autowired
    private ICheckService checkService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private ICheckResultService checkResultService;
    @Autowired
    private CheckHelper checkHelper;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;

    @Override
    public void batchCheckForm(BatchCheckInfo batchCheckInfo) {
        this.batchCheckForm(batchCheckInfo, null);
    }

    @Override
    public void batchCheckForm(BatchCheckInfo batchCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        if (asyncTaskMonitor != null && batchCheckInfo.isChangeMonitorState()) {
            String getParam = "get_param";
            asyncTaskMonitor.progressAndMessage(0.07, getParam);
        }
        JtableContext jtableContext = DimensionValueSetUtil.fillDw((JtableContext)batchCheckInfo.getContext(), (String)batchCheckInfo.getDwScope());
        CheckParam checkParam = new CheckParam();
        Map<String, List<String>> formulas = batchCheckInfo.getFormulas();
        if (formulas.isEmpty()) {
            checkParam.setMode(Mode.FORM);
            checkParam.setRangeKeys(new ArrayList());
        } else {
            Iterator<Map.Entry<String, List<String>>> iterator = formulas.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                if (entry.getValue().isEmpty()) {
                    checkParam.setMode(Mode.FORM);
                    checkParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                } else {
                    checkParam.setMode(Mode.FORMULA);
                    ArrayList<String> formulaKeys = new ArrayList<String>();
                    for (List<String> value : formulas.values()) {
                        formulaKeys.addAll(value);
                    }
                    checkParam.setRangeKeys(formulaKeys);
                }
            }
        }
        checkParam.setVariableMap(batchCheckInfo.getContext().getVariableMap());
        DimensionCollection dimensionCollection = this.dimensionBuildUtil.getDimensionCollection(DimensionUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), jtableContext.getFormSchemeKey(), (SpecificDimBuilder)DWLeafNodeBuilder.getInstance());
        checkParam.setDimensionCollection(dimensionCollection);
        String actionId = asyncTaskMonitor == null ? UUID.randomUUID().toString() : asyncTaskMonitor.getTaskId();
        checkParam.setActionId(actionId);
        List formulaSchemes = FormulaUtil.getFormulaSchemeList((String)jtableContext.getFormSchemeKey(), (String)batchCheckInfo.getFormulaSchemeKeys());
        LogicProgressMonitor progressMonitor = null;
        double coefficient = 0.9199999999999999 / (double)formulaSchemes.size();
        if (asyncTaskMonitor != null && batchCheckInfo.isChangeMonitorState()) {
            String checking = "batch_check_ing";
            progressMonitor = new LogicProgressMonitor(asyncTaskMonitor, 0.07, checking, coefficient);
        }
        ArrayList<String> formulaSchemeKeys = new ArrayList<String>();
        for (int i = 0; i < formulaSchemes.size(); ++i) {
            if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"");
                LogHelper.info((String)"\u6279\u91cf\u5ba1\u6838", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
            if (progressMonitor != null) {
                progressMonitor.setProgressStart(0.07 + (double)i * coefficient);
            }
            String formulaSchemeKey = ((FormulaSchemeDefine)formulaSchemes.get(i)).getKey();
            checkParam.setFormulaSchemeKey(formulaSchemeKey);
            this.checkService.batchCheck(checkParam, (IFmlMonitor)progressMonitor);
            formulaSchemeKeys.add(formulaSchemeKey);
        }
        if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
            asyncTaskMonitor.canceled("stop_execute", (Object)"");
            LogHelper.info((String)"\u6279\u91cf\u5ba1\u6838", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        if (asyncTaskMonitor != null && batchCheckInfo.isChangeMonitorState()) {
            BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
            batchReturnInfo.setStatus(0);
            CheckResultQueryParam checkQuery = this.checkHelper.getCheckQuery(checkParam);
            checkQuery.setFormulaSchemeKeys(formulaSchemeKeys);
            checkQuery.setBatchId(actionId);
            boolean existError = this.checkResultService.existError(checkQuery);
            if (existError) {
                batchReturnInfo.setErrcnt(1);
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                String checkWarnInfo = "check_warn_info";
                asyncTaskMonitor.error(checkWarnInfo, null, objectToJson);
            } else {
                batchReturnInfo.setErrcnt(0);
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                String checkSuccessInfo = "check_success_info";
                if (!asyncTaskMonitor.isFinish()) {
                    asyncTaskMonitor.finish(checkSuccessInfo, (Object)objectToJson);
                }
            }
        }
    }
}

