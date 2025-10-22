/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaUnitGroup
 *  com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.facade.extend.IFmlCheckListener;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckEvent;
import com.jiuqi.nr.data.logic.facade.listener.ICheckRecordListener;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckRecord;
import com.jiuqi.nr.data.logic.facade.param.output.CheckRecordData;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataSetHelper;
import com.jiuqi.nr.data.logic.internal.helper.CheckDataCollector;
import com.jiuqi.nr.data.logic.internal.obj.ParallelFormulaExecuteInfo;
import com.jiuqi.nr.data.logic.internal.service.impl.AllCheckResultSaver;
import com.jiuqi.nr.data.logic.internal.service.impl.BatchCheckResultSaver;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CheckHelper {
    private static final Logger logger = LoggerFactory.getLogger(CheckHelper.class);
    @Autowired(required=false)
    private List<ICheckRecordListener> checkRecordListeners;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private SplitCheckTableHelper splitCheckTableHelper;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired(required=false)
    private IFormulaUnitGroupGetter formulaUnitGroupGetter;
    @Autowired
    private MemoryDataSetHelper memoryDataSetHelper;
    @Autowired(required=false)
    private List<IFmlCheckListener> fmlCheckListeners;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private BatchCheckResultSaver batchCheckResultSaver;
    @Autowired
    private AllCheckResultSaver allCheckResultSaver;

    public boolean recordOpen() {
        if (CollectionUtils.isEmpty(this.checkRecordListeners)) {
            return false;
        }
        for (ICheckRecordListener checkRecordListener : this.checkRecordListeners) {
            if (!checkRecordListener.isEnabled()) continue;
            return true;
        }
        return false;
    }

    public List<ICheckRecordListener> getEnableRecordListeners() {
        if (CollectionUtils.isEmpty(this.checkRecordListeners)) {
            return Collections.emptyList();
        }
        return this.checkRecordListeners.stream().filter(ICheckRecordListener::isEnabled).collect(Collectors.toList());
    }

    public void checkRecord(List<ICheckRecordListener> checkRecordListeners, List<CheckRecordData> checkRecordData, ParallelFormulaExecuteInfo taskInfo) {
        if (!CollectionUtils.isEmpty(checkRecordListeners)) {
            CheckRecord checkRecord = new CheckRecord();
            checkRecord.setActionID(taskInfo.getActionId());
            checkRecord.setCheckRecordData(checkRecordData);
            checkRecord.setCheckTime(taskInfo.getActionTime());
            checkRecord.setActionName(taskInfo.getActionName());
            checkRecord.setFormSchemeKey(taskInfo.getFormSchemeKey());
            checkRecord.setFormulaSchemeKey(taskInfo.getFormulaSchemeKey());
            NpContext context = NpContextHolder.getContext();
            checkRecord.setUserKey(context.getUserId());
            checkRecord.setUserName(context.getUserName());
            for (ICheckRecordListener checkRecordListener : checkRecordListeners) {
                checkRecordListener.processCheckRecord(checkRecord);
            }
        }
    }

    public void cleanCKR(ActionEnum action, String formSchemeKey, String batchId, List<String> forms, Map<String, DimensionValue> dimensionValues) {
        if (ActionEnum.BATCH_CHECK == action) {
            DimensionValueSet dimensionValueSet = DimensionUtil.getDimensionValueSet(dimensionValues);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String ckrTableName = this.splitCheckTableHelper.getCKRTableName(formScheme, batchId);
            TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(ckrTableName);
            String tableId = tableModel.getID();
            List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableId);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            for (ColumnModelDefine columnModel : columnModels) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModel));
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
            queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(tableId, "CKR_BATCH_ID"), batchId);
            queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(tableId, "CKR_FORMKEY"), forms);
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                ColumnModelDefine column = dimensionChanger.getColumn(dimensionValueSet.getName(i));
                if (column == null) continue;
                queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(tableId, column.getCode()), dimensionValueSet.getValue(i));
            }
            DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
            try {
                INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
                dataUpdator.deleteAll();
                dataUpdator.commitChanges(dataAccessContext);
            }
            catch (Exception e) {
                logger.error("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868" + ckrTableName + "\u6570\u636e\u6e05\u9664\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }

    public void cleanCache(ActionEnum actionEnum, DimensionCollection dimensionCollection, String formulaSchemeKey) {
        if (ActionEnum.ALL_CHECK == actionEnum) {
            for (DimensionValueSet dimensionValueSet : dimensionCollection) {
                String cacheKey = CheckResultUtil.buildAllCheckCacheKey(formulaSchemeKey, dimensionValueSet);
                this.memoryDataSetHelper.clearData(cacheKey);
            }
        }
    }

    public void pushPriFmlIfNeed(CheckResultQueryParam checkResultQueryParam, String masterDimensionName) {
        Mode mode = checkResultQueryParam.getMode();
        if (Mode.FORMULA == mode && this.formulaUnitGroupGetter != null) {
            List<String> rangeKeys = checkResultQueryParam.getRangeKeys();
            List<String> formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
            HashSet<String> strings = new HashSet<String>(rangeKeys);
            List<String> units = this.dimensionCollectionUtil.getDimKeySet(checkResultQueryParam.getDimensionCollection(), masterDimensionName);
            List formulaUnitGroups = this.formulaUnitGroupGetter.get(units, formulaSchemeKeys.get(0), DataEngineConsts.FormulaType.CHECK);
            for (FormulaUnitGroup formulaUnitGroup : formulaUnitGroups) {
                List formulaList;
                String unit = formulaUnitGroup.getUnit();
                if (StringUtils.isEmpty((String)unit) || CollectionUtils.isEmpty(formulaList = formulaUnitGroup.getFormulaList())) continue;
                formulaList.forEach(o -> strings.add(o.getSource().getId()));
            }
            checkResultQueryParam.setRangeKeys(new ArrayList<String>(strings));
        }
    }

    public CheckResultQueryParam getCheckQuery(CheckParam checkParam) {
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setDimensionCollection(checkParam.getDimensionCollection());
        ArrayList<String> formulaSchemeKeys = new ArrayList<String>();
        formulaSchemeKeys.add(checkParam.getFormulaSchemeKey());
        checkResultQueryParam.setFormulaSchemeKeys(formulaSchemeKeys);
        checkResultQueryParam.setMode(checkParam.getMode());
        checkResultQueryParam.setRangeKeys(checkParam.getRangeKeys());
        checkResultQueryParam.setFilterCondition(checkParam.getFilterCondition());
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        for (Integer checkType : checkParam.getFormulaCheckType()) {
            checkTypes.put(checkType, null);
        }
        checkResultQueryParam.setCheckTypes(checkTypes);
        return checkResultQueryParam;
    }

    public void beforeCheck(CheckEvent checkEvent) {
        if (!CollectionUtils.isEmpty(this.fmlCheckListeners)) {
            for (IFmlCheckListener fmlCheckListener : this.fmlCheckListeners) {
                try {
                    fmlCheckListener.beforeCheck(checkEvent);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    public void afterCheck(CheckEvent checkEvent) {
        if (!CollectionUtils.isEmpty(this.fmlCheckListeners)) {
            for (IFmlCheckListener fmlCheckListener : this.fmlCheckListeners) {
                try {
                    fmlCheckListener.afterCheck(checkEvent);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    public void checkDes(CheckDesParam checkDesParam) {
        try {
            this.checkErrorDescriptionService.checkDes(checkDesParam);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void delCheckResult(ActionEnum actionEnum, String formSchemeKey, String batchId, List<String> cacheKeys, String executeId) {
        if (ActionEnum.CHECK == actionEnum) {
            CheckDataCollector.getInstance().remove(executeId);
        } else if (ActionEnum.ALL_CHECK == actionEnum) {
            this.allCheckResultSaver.delResult(formSchemeKey, cacheKeys, batchId);
        } else if (ActionEnum.BATCH_CHECK == actionEnum) {
            this.batchCheckResultSaver.delResult(formSchemeKey, batchId);
        }
    }
}

