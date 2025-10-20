/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.offsetitem.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.offsetitem.dto.OffsetItemExportParam;
import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.executor.tab.task.OffsetTabExportTask;
import com.jiuqi.gcreport.offsetitem.executor.tab.task.SumTabExportTask;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdjustingEntryExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private OffsetTabExportTask offsetTabExportTask;
    @Autowired
    private SumTabExportTask sumTabExportTask;
    @Autowired
    private GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;
    @Autowired(required=false)
    private GcInputDataOffsetItemService inputDataOffsetCoreService;
    private static final Logger logger = LoggerFactory.getLogger(AdjustingEntryExportExecutorImpl.class);

    public String getName() {
        return "AdjustingEntryExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        try {
            GcOffsetExecutorVO gcOffsetExecutorVO;
            HashMap<String, Object> paramName2Value;
            OffsetItemExportParam queryParamsVO = (OffsetItemExportParam)((Object)JsonUtils.readValue((String)context.getParam(), OffsetItemExportParam.class));
            queryParamsVO.setPageNum(-1);
            queryParamsVO.setPageSize(-1);
            List<String> exportTabs = queryParamsVO.getExportTabs();
            Map<String, String> selectFilterMethodByTab = queryParamsVO.getSelectFilterMethodByTab();
            List otherShowColumns = queryParamsVO.getOtherShowColumns();
            List otherShowColumnTitles = queryParamsVO.getOtherShowColumnTitles();
            List notOffsetOtherColumns = queryParamsVO.getNotOffsetOtherColumns();
            List notOffsetOtherTitles = queryParamsVO.getNotOffsetOtherTitles();
            List<String> notOffsetParentOtherColumns = queryParamsVO.getNotOffsetParentOtherColumns();
            List<String> notOffsetParentOtherTitles = queryParamsVO.getNotOffsetParentOtherTitles();
            int sheetNo = -1;
            if (exportTabs.contains(TabSelectEnum.ALL_PAGE.getCode())) {
                queryParamsVO.setFilterCondition(queryParamsVO.getSumSubFilterCondition());
                queryParamsVO.setOtherShowColumns(new ArrayList());
                queryParamsVO.setOtherShowColumnTitles(new ArrayList());
                ExportExcelSheet sumTabSheet = this.sumTabExportTask.createSheet(queryParamsVO, context.isTemplateExportFlag(), ++sheetNo);
                exportExcelSheets.add(sumTabSheet);
            }
            if (exportTabs.contains(TabSelectEnum.OFFSET_PAGE.getCode())) {
                queryParamsVO.setOtherShowColumns(otherShowColumns);
                queryParamsVO.setOtherShowColumnTitles(otherShowColumnTitles);
                queryParamsVO.setFilterCondition(queryParamsVO.getOffsetFilterCondition());
                ExportExcelSheet offsetSheet = this.offsetTabExportTask.createSheet(workbook, queryParamsVO, context.isTemplateExportFlag(), ++sheetNo);
                exportExcelSheets.add(offsetSheet);
            }
            if (exportTabs.contains(TabSelectEnum.NOT_OFFSET_PAGE.getCode())) {
                ++sheetNo;
                OffsetItemExportParam queryNotOffsetPageParamsVO = (OffsetItemExportParam)((Object)JsonUtils.readValue((String)context.getParam(), OffsetItemExportParam.class));
                queryParamsVO.setNotOffsetFilterCondition(queryNotOffsetPageParamsVO.getNotOffsetFilterCondition());
                queryParamsVO.setOtherShowColumns(notOffsetOtherColumns);
                queryParamsVO.setOtherShowColumnTitles(notOffsetOtherTitles);
                queryParamsVO.setFilterCondition(queryParamsVO.getNotOffsetFilterCondition());
                queryParamsVO.setFilterMethod(selectFilterMethodByTab.get(TabSelectEnum.NOT_OFFSET_PAGE.getCode()));
                paramName2Value = new HashMap<String, Object>();
                paramName2Value.put("queryParamsVO", (Object)queryParamsVO);
                paramName2Value.put("templateExportFlag", context.isTemplateExportFlag());
                paramName2Value.put("sheetNo", sheetNo);
                queryParamsVO.setPageCode(TabSelectEnum.NOT_OFFSET_PAGE.getCode());
                gcOffsetExecutorVO = new GcOffsetExecutorVO(queryParamsVO.getActionCode(), queryParamsVO.getPageCode(), queryParamsVO.getDataSourceCode(), queryParamsVO.getFilterMethod(), paramName2Value);
                this.inputDataOffsetCoreService.getUnOffsetTabExportExcelSheet(exportExcelSheets, gcOffsetExecutorVO);
            }
            if (exportTabs.contains(TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getCode())) {
                ++sheetNo;
                OffsetItemExportParam queryNotOffsetParentPageParamsVO = (OffsetItemExportParam)((Object)JsonUtils.readValue((String)context.getParam(), OffsetItemExportParam.class));
                queryParamsVO.setNotOffsetFilterCondition(queryNotOffsetParentPageParamsVO.getNotOffsetFilterCondition());
                queryParamsVO.setOtherShowColumns(notOffsetParentOtherColumns);
                queryParamsVO.setOtherShowColumnTitles(notOffsetParentOtherTitles);
                queryParamsVO.setFilterCondition(queryParamsVO.getNotOffsetParentFilterCondition());
                queryParamsVO.setFilterMethod(selectFilterMethodByTab.get(TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getCode()));
                paramName2Value = new HashMap();
                paramName2Value.put("queryParamsVO", (Object)queryParamsVO);
                paramName2Value.put("templateExportFlag", context.isTemplateExportFlag());
                paramName2Value.put("sheetNo", sheetNo);
                queryParamsVO.setPageCode(TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getCode());
                gcOffsetExecutorVO = new GcOffsetExecutorVO(queryParamsVO.getActionCode(), queryParamsVO.getPageCode(), queryParamsVO.getDataSourceCode(), queryParamsVO.getFilterMethod(), paramName2Value);
                this.inputDataOffsetCoreService.getUnOffsetParentTabExportExcelSheet(exportExcelSheets, gcOffsetExecutorVO);
            }
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u5bfc\u51fa-\u4efb\u52a1" + taskDefine.getTitle() + "-\u65f6\u671f-" + queryParamsVO.getAcctYear() + "\u5e74" + queryParamsVO.getAcctPeriod() + "\u6708"));
        }
        catch (Exception e) {
            logger.error("\u8c03\u6574\u62b5\u9500\u5206\u5f55\u5bfc\u51fa\u4efb\u52a1\u5931\u8d25\uff1a", e);
            throw new BusinessRuntimeException("\u8c03\u6574\u62b5\u9500\u5206\u5f55\u5bfc\u51fa\u4efb\u52a1\u5931\u8d25\uff1a" + e.getMessage());
        }
        return exportExcelSheets;
    }
}

