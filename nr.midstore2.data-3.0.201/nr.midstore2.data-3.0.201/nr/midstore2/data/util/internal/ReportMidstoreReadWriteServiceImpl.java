/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.util.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.util.IReportMidstoreReadWriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreReadWriteServiceImpl
implements IReportMidstoreReadWriteService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreReadWriteServiceImpl.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IFormulaRunTimeController formulaService;
    @Autowired
    private IReportMidstoreDimensionService dimensionService;
    @Autowired
    private IDataentryFlowService flowService;
    @Autowired
    private TreeState flowerTreeStateService;
    @Autowired
    private IEntityAuthorityService iEntityAuthorityService;
    @Autowired
    private SystemIdentityService systemdentityService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;

    @Override
    public List<String> getUnitcodeByUnitState(ReportMidstoreContext context, List<String> queryEntitys) throws MidstoreException {
        FormulaSchemeDefine formulaScheme;
        ArrayList<String> dataGetUnits = new ArrayList<String>();
        String formSchemeKey = null;
        String formulaSchemeKey = null;
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        String periodCode = dimSetMap.get("DATATIME").getValue();
        try {
            SchemePeriodLinkDefine periodLinkDefine = this.viewController.querySchemePeriodLinkByPeriodAndTask(periodCode, context.getTaskDefine().getKey());
            if (periodLinkDefine != null) {
                formSchemeKey = periodLinkDefine.getSchemeKey();
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw new MidstoreException((Throwable)e1);
        }
        if (StringUtils.isNotEmpty((String)formSchemeKey) && (formulaScheme = this.formulaService.getDefaultFormulaSchemeInFormScheme(formSchemeKey)) != null) {
            formulaSchemeKey = formulaScheme.getKey();
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimSetMap);
        DimensionValue periodDimensionValue = dimSetMap.get("DATATIME");
        String periodValue = periodDimensionValue.getValue();
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(context.getTaskDefine().getDateTime());
        Date[] dates = null;
        try {
            dates = periodProvider.getPeriodDateRegion(periodValue);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException("\u83b7\u53d6\u65f6\u671f\u7684\u65f6\u95f4\u8303\u56f4\u51fa\u9519", (Throwable)e);
        }
        Date queryVersionStartDate = null;
        Date queryVersionEndDate = null;
        queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (dates != null && dates.length >= 2) {
            queryVersionStartDate = dates[0];
            queryVersionEndDate = dates[1];
        }
        Set canWriteEntityKeys = new HashSet();
        ArrayList<String> queryEntitys1 = new ArrayList<String>();
        boolean isAdmin = this.systemdentityService.isAdmin();
        if (StringUtils.isNotEmpty((String)context.getExcuteUserName()) && !isAdmin) {
            try {
                canWriteEntityKeys = this.iEntityAuthorityService.getCanWriteEntityKeys(context.getTaskDefine().getDw(), queryVersionStartDate, queryVersionEndDate);
            }
            catch (UnauthorizedEntityException e) {
                throw new RuntimeException(e);
            }
            for (String string : queryEntitys) {
                if (canWriteEntityKeys == null || canWriteEntityKeys != null && !canWriteEntityKeys.contains(string)) {
                    String string2 = "\u5355\u4f4d\u6ca1\u6709\u7f16\u8f91\u6743\u9650";
                    String unitTile = "";
                    if (context.getUnitCache().containsKey(string)) {
                        unitTile = ((MistoreWorkUnitInfo)context.getUnitCache().get(string)).getUnitTitle();
                    }
                    this.addUnitErrorInfo(context.getWorkResult(), string2, string, unitTile);
                    continue;
                }
                queryEntitys1.add(string);
            }
        } else {
            queryEntitys1.addAll(queryEntitys);
        }
        ArrayList<String> unitList = new ArrayList<String>();
        for (String string : queryEntitys1) {
            unitList.add(string);
        }
        HashSet<String> hashSet = new HashSet<String>();
        WorkFlowType workFlowType = this.flowService.queryStartType(formSchemeKey);
        String errorMessage = "";
        if (workFlowType == WorkFlowType.ENTITY && unitList.size() > 0) {
            dimensionValueSet.setValue(context.getEntityTypeName(), unitList);
            Map actionStateMaps = this.flowerTreeStateService.getWorkflowUploadState(dimensionValueSet, null, formSchemeKey);
            for (DimensionValueSet dim : actionStateMaps.keySet()) {
                String netEntityKey = (String)dim.getValue(context.getEntityTypeName());
                ActionStateBean actionState = (ActionStateBean)actionStateMaps.get(dim);
                if (actionState == null || !StringUtils.isNotEmpty((String)actionState.getCode()) || !actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode()) && !actionState.getCode().equals(UploadStateEnum.UPLOADED.getCode()) && !actionState.getCode().equals(UploadStateEnum.CONFIRMED.getCode())) continue;
                errorMessage = "\u5355\u4f4d\u5df2\u7ecf\u4e0a\u62a5";
                String unitTile = "";
                if (context.getUnitCache().containsKey(netEntityKey)) {
                    unitTile = ((MistoreWorkUnitInfo)context.getUnitCache().get(netEntityKey)).getUnitTitle();
                }
                this.addUnitErrorInfo(context.getWorkResult(), errorMessage, netEntityKey, unitTile);
                hashSet.add(netEntityKey);
            }
            for (String unitCode : unitList) {
                if (hashSet.contains(unitCode)) continue;
                dataGetUnits.add(unitCode);
            }
        } else {
            dataGetUnits.addAll(queryEntitys);
        }
        return dataGetUnits;
    }

    @Override
    public Set<String> getUnitcodeByOnlyUnitState(ReportMidstoreContext context, List<String> queryEntitys) throws MidstoreException {
        HashSet<String> dataGetUnits = new HashSet<String>();
        HashSet<String> errorUnits = new HashSet<String>();
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        String periodCode = dimSetMap.get("DATATIME").getValue();
        String formSchemeKey = context.getFormSchemeKey();
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            try {
                SchemePeriodLinkDefine periodLinkDefine = this.viewController.querySchemePeriodLinkByPeriodAndTask(periodCode, context.getTaskDefine().getKey());
                if (periodLinkDefine == null) {
                    throw new MidstoreException("\u65f6\u671f\u6ca1\u6709\u5173\u8054\u62a5\u8868\u65b9\u6848\uff0c" + periodCode);
                }
                formSchemeKey = periodLinkDefine.getSchemeKey();
                context.setFormSchemeKey(formSchemeKey);
            }
            catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
                throw new MidstoreException((Throwable)e1);
            }
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimSetMap);
        WorkFlowType wflowType = this.flowService.queryStartType(formSchemeKey);
        String errorMessage = "";
        if (wflowType == WorkFlowType.ENTITY && queryEntitys.size() > 0) {
            dimensionValueSet.setValue(context.getEntityTypeName(), queryEntitys);
            Map actionStateMaps = this.flowerTreeStateService.getWorkflowUploadState(dimensionValueSet, null, formSchemeKey);
            for (DimensionValueSet dim : actionStateMaps.keySet()) {
                String netEntityKey = (String)dim.getValue(context.getEntityTypeName());
                ActionStateBean actionState = (ActionStateBean)actionStateMaps.get(dim);
                if (actionState == null || !StringUtils.isNotEmpty((String)actionState.getCode()) || !actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode()) && !actionState.getCode().equals(UploadStateEnum.UPLOADED.getCode()) && !actionState.getCode().equals(UploadStateEnum.CONFIRMED.getCode())) continue;
                errorMessage = "\u5355\u4f4d\u5df2\u7ecf\u4e0a\u62a5";
                errorUnits.add(netEntityKey);
            }
            for (String unitCode : queryEntitys) {
                if (errorUnits.contains(unitCode)) continue;
                dataGetUnits.add(unitCode);
            }
        } else {
            dataGetUnits.addAll(queryEntitys);
        }
        return dataGetUnits;
    }

    @Override
    public boolean isAdmin() {
        return this.systemdentityService.isAdmin();
    }

    @Override
    public Set<String> getCanWriteUnitList(ReportMidstoreContext context, String periodValue) throws MidstoreException {
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(context.getTaskDefine().getDateTime());
        Date[] dates = null;
        try {
            dates = periodProvider.getPeriodDateRegion(periodValue);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException("\u83b7\u53d6\u65f6\u671f\u7684\u65f6\u95f4\u8303\u56f4\u51fa\u9519", (Throwable)e);
        }
        Date queryVersionStartDate = null;
        Date queryVersionEndDate = null;
        queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (dates != null && dates.length >= 2) {
            queryVersionStartDate = dates[0];
            queryVersionEndDate = dates[1];
        }
        Set<String> canWriteEntityKeys = new HashSet<String>();
        boolean isAdmin = this.systemdentityService.isAdmin();
        if (!isAdmin) {
            try {
                canWriteEntityKeys = this.iEntityAuthorityService.getCanWriteEntityKeys(context.getTaskDefine().getDw(), queryVersionStartDate, queryVersionEndDate);
            }
            catch (UnauthorizedEntityException e) {
                throw new RuntimeException(e);
            }
        }
        return canWriteEntityKeys;
    }

    @Override
    public Set<String> getCanReadUnitList(ReportMidstoreContext context, String periodValue) throws MidstoreException {
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(context.getTaskDefine().getDateTime());
        Date[] dates = null;
        try {
            dates = periodProvider.getPeriodDateRegion(periodValue);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException("\u83b7\u53d6\u65f6\u671f\u7684\u65f6\u95f4\u8303\u56f4\u51fa\u9519", (Throwable)e);
        }
        Date queryVersionStartDate = null;
        Date queryVersionEndDate = null;
        queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (dates != null && dates.length >= 2) {
            queryVersionStartDate = dates[0];
            queryVersionEndDate = dates[1];
        }
        Set<String> canReadEntityKeys = new HashSet<String>();
        boolean isAdmin = this.systemdentityService.isAdmin();
        if (!isAdmin) {
            try {
                canReadEntityKeys = this.iEntityAuthorityService.getCanReadEntityKeys(context.getTaskDefine().getDw(), queryVersionStartDate, queryVersionEndDate);
            }
            catch (UnauthorizedEntityException e) {
                throw new RuntimeException(e);
            }
        }
        return canReadEntityKeys;
    }

    private void addUnitErrorInfo(MistoreWorkResultObject workResult, String message, String unitCode, String unitTitle) {
        MistoreWorkFailInfo failInfo = null;
        if (workResult.getFailInfos().containsKey(message)) {
            failInfo = (MistoreWorkFailInfo)workResult.getFailInfos().get(message);
        } else {
            failInfo = new MistoreWorkFailInfo();
            failInfo.setMessage(message);
            workResult.addFailInfo(failInfo);
        }
        MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo();
        unitInfo.setUnitCode(message);
        unitInfo.setUnitTitle(unitTitle);
        failInfo.getUnitInfos().put(unitCode, unitInfo);
    }
}

