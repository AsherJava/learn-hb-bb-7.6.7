/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.xlib.runtime.Assert
 */
package com.jiuqi.gcreport.formulaschemeconfig.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.utils.NrFormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.xlib.runtime.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeConfigExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "FormulaSchemeConfigExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String taskId = (String)params.get("taskId");
        String schemeId = (String)params.get("schemeId");
        String entityId = (String)params.get("entityId");
        entityId = NrFormulaSchemeConfigUtils.getEntityIdBySchemeIdAndEntityId(schemeId, entityId);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        Assert.isNotNull((Object)entityDefine, (String)String.format("\u53e3\u5f84\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", entityId));
        String orgCode = (String)params.get("orgCode");
        context.getProgressData().setProgressValueAndRefresh(0.1);
        Map<String, Object> formulaSchemeData = this.formulaSchemeConfigService.getFormulaSchemesBySchemeId(schemeId, entityId);
        String orgType = (String)formulaSchemeData.get("orgType");
        context.getProgressData().setProgressValueAndRefresh(0.2);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        List<String> orgIds = this.getOrgCode(orgType, orgCode);
        context.getProgressData().setProgressValueAndRefresh(0.3);
        Map<String, List<NrFormulaSchemeConfigTableVO>> formulaSchemeConfigTableMap = this.formulaSchemeConfigService.queryTabSelectOrgIds(schemeId, entityId, orgIds, false);
        context.getProgressData().setProgressValueAndRefresh(0.5);
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        exportExcelSheets.add(this.exportSheet("batchStrategy", cellStyleMap, context.isTemplateExportFlag(), taskDefine, formSchemeDefine.getTitle(), entityDefine.getTitle(), formulaSchemeConfigTableMap.get("batchStrategy")));
        exportExcelSheets.add(this.exportSheet("batchUnit", cellStyleMap, context.isTemplateExportFlag(), taskDefine, formSchemeDefine.getTitle(), entityDefine.getTitle(), formulaSchemeConfigTableMap.get("batchUnit")));
        context.getProgressData().setProgressValueAndRefresh(0.95);
        LogHelper.info((String)"\u5408\u5e76-\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848", (String)("\u5bfc\u51fa-\u4efb\u52a1" + taskDefine.getTitle() + "\u516c\u5f0f\u65b9\u6848"), (String)("\u5355\u4f4d\u4fe1\u606f\uff1a" + orgCode));
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    public ExportExcelSheet exportSheet(String tabSelect, Map<String, CellStyle> cellStyleMap, boolean templateExportFlag, TaskDefine taskDefine, String formSchemeTitle, String entityTitle, List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOList) {
        ExportExcelSheet exportExcelSheet = null;
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle contentString = cellStyleMap.get("contentString");
        List<String> titleNames = NrFormulaSchemeConfigUtils.listTitleName(taskDefine.getKey(), tabSelect);
        if ("batchStrategy".equals(tabSelect)) {
            exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u7b56\u7565");
        } else if ("batchUnit".equals(tabSelect)) {
            exportExcelSheet = new ExportExcelSheet(Integer.valueOf(1), "\u5355\u4f4d");
        }
        for (int i = 0; i < titleNames.size(); ++i) {
            exportExcelSheet.getHeadCellStyleCache().put(i, headString);
            exportExcelSheet.getContentCellStyleCache().put(i, contentString);
            exportExcelSheet.getContentCellTypeCache().put(i, CellType.STRING);
        }
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        rowDatas.add(titleNames.toArray());
        if (templateExportFlag) {
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            return exportExcelSheet;
        }
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskDefine.getKey());
        for (NrFormulaSchemeConfigTableVO schemeConfigTableVO : formulaSchemeConfigTableVOList) {
            Object[] rowData = new Object[titleNames.size()];
            rowData[0] = taskDefine.getTitle();
            rowData[1] = formSchemeTitle;
            rowData[2] = entityTitle;
            rowData[3] = schemeConfigTableVO.getOrgId() + "|" + schemeConfigTableVO.getOrgUnit().getName();
            if ("batchStrategy".equals(tabSelect)) {
                rowData[4] = schemeConfigTableVO.getBblx();
                if (isExistCurrency) {
                    rowData[5] = schemeConfigTableVO.getCurrency() != null ? schemeConfigTableVO.getCurrency().getTitle() : (!StringUtils.isEmpty((String)schemeConfigTableVO.getAssistDim()) && schemeConfigTableVO.getAssistDim().contains("MD_CURRENCY@ALL") ? "\u5168\u90e8" : "\u672c\u4f4d\u5e01");
                    this.setRowData(6, schemeConfigTableVO, rowData);
                } else {
                    this.setRowData(5, schemeConfigTableVO, rowData);
                }
            } else if (isExistCurrency) {
                rowData[4] = schemeConfigTableVO.getCurrency() != null ? schemeConfigTableVO.getCurrency().getTitle() : "";
                this.setRowData(5, schemeConfigTableVO, rowData);
            } else {
                this.setRowData(4, schemeConfigTableVO, rowData);
            }
            rowDatas.add(rowData);
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            contentStringStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            cellStyleMap.put("contentString", contentStringStyle);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    private List<String> getOrgCode(String orgType, String orgCode) {
        List orgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgCode);
        if (orgDOList == null) {
            return new ArrayList<String>(16);
        }
        return orgDOList.stream().filter(org -> null != org).map(OrgDO::getCode).collect(Collectors.toList());
    }

    private void setRowData(int starIndex, NrFormulaSchemeConfigTableVO schemeConfigTableVO, Object[] rowData) {
        rowData[starIndex] = schemeConfigTableVO.getFetchScheme();
        rowData[starIndex + 1] = schemeConfigTableVO.getFetchAfterScheme();
        rowData[starIndex + 2] = schemeConfigTableVO.getCompleteMerge();
        rowData[starIndex + 3] = schemeConfigTableVO.getConvertSystemScheme();
        rowData[starIndex + 4] = schemeConfigTableVO.getConvertAfterScheme();
        rowData[starIndex + 5] = schemeConfigTableVO.getUnSaCtDeExtLaYeNumSaPer();
        rowData[starIndex + 6] = schemeConfigTableVO.getSameCtrlExtAfterScheme();
        rowData[starIndex + 7] = schemeConfigTableVO.getPostingScheme();
    }
}

