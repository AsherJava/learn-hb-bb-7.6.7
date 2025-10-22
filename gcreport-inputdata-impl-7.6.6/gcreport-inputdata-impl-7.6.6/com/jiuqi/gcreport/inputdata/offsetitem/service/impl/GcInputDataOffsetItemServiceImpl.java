/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.carryover.utils.CarryOverUtil
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.carryover.utils.CarryOverUtil;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleMappingImplUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetParentTabExportExcelSheet;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetTabExportExcelSheet;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.inputdata.util.I18nTableUtils;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GcInputDataOffsetItemServiceImpl
implements GcInputDataOffsetItemService {
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private InputDataDao inputdataDao;
    @Autowired
    private GcUnOffsetSelectOptionService gcUnOffsetSelectOptionService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;

    public void cancelInputOffsetByOffsetGroupId(Collection<String> srcOffsetGroupIds, String taskId) {
        this.gcOffsetAppInputDataService.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, taskId);
    }

    public void mappingRule(String taskId, String systemId, String ruleId, String periodStr, String selectAdjustCode) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        InputRuleFilterCondition inputRuleFilterCondition = new InputRuleFilterCondition();
        inputRuleFilterCondition.setRuleId(ruleId);
        inputRuleFilterCondition.setNrPeriod(periodStr);
        inputRuleFilterCondition.setTaskId(taskId);
        inputRuleFilterCondition.setSelectAdjustCode(selectAdjustCode);
        List<InputDataEO> inputDataItems = this.inputdataDao.queryByRuleCondition(inputRuleFilterCondition);
        if (!CollectionUtils.isEmpty(inputDataItems)) {
            RuleMappingImplUtils.mappingRule(inputDataItems, systemId);
            this.inputdataDao.updateRuleAndDcById(inputDataItems, tableName);
        }
    }

    public void updateMemoById(String id, String memo, String taskId) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        this.inputdataDao.updateMemoById(id, memo, tableName);
    }

    public Pagination<Map<String, Object>> queryUnOffsetRecords(QueryParamsVO queryParamsVO, Boolean isQueryParent) {
        return this.inputdataDao.queryUnOffsetRecords(queryParamsVO, isQueryParent);
    }

    public List<DesignFieldDefineVO> listUnOffsetColumnSelects(String systemId, String dataSource) {
        if (DataSourceEnum.INPUT_DATA.getCode().equals(dataSource)) {
            ConsolidatedSystemEO consolidatedSystemEO = this.consolidatedSystemService.getConsolidatedSystemEO(systemId);
            String inputDataName = this.inputDataNameProvider.getTableNameByDataSchemeKey(consolidatedSystemEO.getDataSchemeKey());
            return this.inputdataDao.queryUnOffsetColumnSelect(inputDataName);
        }
        String[] updateColumnSelectPartStr = new String[]{"UNITID", "OPPUNITID", "SUBJECTCODE"};
        List<String> updateColumnSelectPartList = Arrays.asList(updateColumnSelectPartStr);
        String[] notColumnSelectPartStr = new String[]{"OFFSETGROUPID", "RELATEDITEMID", "SYSTEMID", "DATATIME", "RTOFFSETCANDEL"};
        List<String> notColumnSelectPartList = Arrays.asList(notColumnSelectPartStr);
        I18nTableUtils i18nTableUtils = (I18nTableUtils)SpringContextUtils.getBean(I18nTableUtils.class);
        List<DesignFieldDefineVO> designFieldDefineVOS = i18nTableUtils.getAllFieldsByTableName("GC_OFFSETRELATEDITEM", new HashSet<String>());
        ArrayList<DesignFieldDefineVO> designFieldDefineVOList = new ArrayList<DesignFieldDefineVO>();
        designFieldDefineVOS.forEach(designFieldDefineVO -> {
            if (updateColumnSelectPartList.contains(designFieldDefineVO.getKey())) {
                designFieldDefineVO.setLabel("\u5173\u8054\u4ea4\u6613-" + designFieldDefineVO.getLabel());
            }
            if (!notColumnSelectPartList.contains(designFieldDefineVO.getKey())) {
                designFieldDefineVOList.add((DesignFieldDefineVO)designFieldDefineVO);
            }
        });
        return designFieldDefineVOList;
    }

    public Map<String, String> initFieldCode2DictTableMap(List<String> otherShowColumns, String taskId) {
        HashMap<String, String> fieldCode2DictTableMap = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            try {
                DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByName("GC_OFFSETVCHRITEM");
                List defines = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
                TableModelDefine inputDataTabelDefine = dataModelService.getTableModelDefineByName(this.inputDataNameProvider.getTableNameByTaskId(taskId));
                List inputDataDefines = dataModelService.getColumnModelDefinesByTable(inputDataTabelDefine.getID());
                defines.addAll(inputDataDefines);
                for (ColumnModelDefine designFieldDefine : defines) {
                    TableModelDefine dictField;
                    if (!StringUtils.hasText(designFieldDefine.getReferTableID()) || (dictField = dataModelService.getTableModelDefineById(designFieldDefine.getReferTableID())) == null || !StringUtils.hasText(designFieldDefine.getCode())) continue;
                    fieldCode2DictTableMap.put(designFieldDefine.getCode(), dictField.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new BusinessRuntimeException("\u521d\u59cb\u5316otherShowColumns\u7f13\u5b58Map\u5931\u8d25", (Throwable)e);
            }
        }
        return fieldCode2DictTableMap;
    }

    public List<Map<String, Object>> queryUnOffset(QueryParamsVO queryParamsVO, boolean isBizCodeOrUnionRuleNull) {
        return this.inputdataDao.queryUnOffset(queryParamsVO, isBizCodeOrUnionRuleNull);
    }

    public Set<String> queryInputDataOffsetGroupIds(QueryParamsVO queryParamsVO) {
        String[] selectColumnNamesInDB = new String[]{"OFFSETGROUPID"};
        String[] whereColumnNamesInDB = new String[]{"taskId", "DATATIME"};
        String periodStr = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        Object[] whereValues = new Object[]{queryParamsVO.getTaskId(), periodStr};
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        List<InputDataEO> inputDataEOs = this.inputdataDao.queryUnOffsetRecordsByWhere(selectColumnNamesInDB, whereColumnNamesInDB, whereValues, tableName);
        HashSet<String> results = new HashSet<String>(128);
        for (InputDataEO inputDataEO : inputDataEOs) {
            results.add(inputDataEO.getOffsetGroupId());
        }
        results.remove(null);
        return results;
    }

    public void getUnOffsetTabExportExcelSheet(List<ExportExcelSheet> exportExcelSheets, GcOffsetExecutorVO gcOffsetExecutorVO) {
        GcOffSetItemAction offSetItemAction = null;
        try {
            offSetItemAction = this.gcOffSetItemAdjustExecutor.getActionForCode(gcOffsetExecutorVO);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (null != offSetItemAction) {
            UnOffsetTabExportExcelSheet unOffsetsheet = (UnOffsetTabExportExcelSheet)((Object)offSetItemAction.execute(gcOffsetExecutorVO));
            exportExcelSheets.add((ExportExcelSheet)unOffsetsheet);
        }
    }

    public void getUnOffsetParentTabExportExcelSheet(List<ExportExcelSheet> exportExcelSheets, GcOffsetExecutorVO gcOffsetExecutorVO) {
        GcOffSetItemAction offSetItemAction = null;
        try {
            offSetItemAction = this.gcOffSetItemAdjustExecutor.getActionForCode(gcOffsetExecutorVO);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (null != offSetItemAction) {
            UnOffsetParentTabExportExcelSheet unOffsetsheet = (UnOffsetParentTabExportExcelSheet)((Object)offSetItemAction.execute(gcOffsetExecutorVO));
            exportExcelSheets.add((ExportExcelSheet)unOffsetsheet);
        }
    }

    public List<DesignFieldDefineVO> getAllFieldsByTableName(String tableName, Set<String> notColumnSelectPart) {
        I18nTableUtils i18nTableUtils = (I18nTableUtils)SpringContextUtils.getBean(I18nTableUtils.class);
        return i18nTableUtils.getAllFieldsByTableName("GC_OFFSETVCHRITEM", notColumnSelectPart);
    }

    public void setRuleTitle(Map<String, Object> record, Map<String, AbstractUnionRule> ruleId2TitleCache) {
        this.gcOffsetAppInputDataService.setRuleTitle(record, ruleId2TitleCache);
    }

    public void setOtherShowColumnDictTitle(Map<String, Object> record, List<String> otherShowColumns, Map<String, String> fieldCode2DictTableMap, boolean showDictCode) {
        this.gcOffsetAppInputDataService.setOtherShowColumnDictTitle(record, otherShowColumns, fieldCode2DictTableMap, showDictCode);
    }
}

