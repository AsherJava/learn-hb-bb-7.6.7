/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.formulaschemeconfig.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.gcreport.formulaschemeconfig.service.BillFormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.BillFormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.org.OrgDO;
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
public class BillFormulaSchemeConfigExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private BillFormulaSchemeConfigService billFormulaSchemeConfigService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    private BillExtractSettingClient settingClient;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "BillFormulaSchemeConfigExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String billId = (String)params.get("billId");
        String orgCode = (String)params.get("orgCode");
        context.getProgressData().setProgressValueAndRefresh(0.1);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        List<String> orgIds = this.getOrgCode(orgCode, billId);
        context.getProgressData().setProgressValueAndRefresh(0.3);
        Map<String, List<BillFormulaSchemeConfigTableVO>> formulaSchemeConfigTableMap = this.billFormulaSchemeConfigService.queryTabSelectOrgIds(billId, orgIds, false);
        context.getProgressData().setProgressValueAndRefresh(0.5);
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        MetaInfoService metaInfoService = (MetaInfoService)SpringContextUtils.getBean(MetaInfoService.class);
        MetaInfoDTO infoDTO = metaInfoService.getMetaInfoByUniqueCode(billId);
        String billTitle = infoDTO.getTitle();
        if (StringUtils.isEmpty((String)billTitle)) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u5355\u636e\uff01");
        }
        exportExcelSheets.add(this.exportSheet("batchStrategy", cellStyleMap, context.isTemplateExportFlag(), billTitle, formulaSchemeConfigTableMap.get("batchStrategy")));
        exportExcelSheets.add(this.exportSheet("batchUnit", cellStyleMap, context.isTemplateExportFlag(), billTitle, formulaSchemeConfigTableMap.get("batchUnit")));
        context.getProgressData().setProgressValueAndRefresh(0.95);
        LogHelper.info((String)"\u5408\u5e76-\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848", (String)("\u5bfc\u51fa-\u4efb\u52a1" + billTitle + "\u516c\u5f0f\u65b9\u6848"), (String)("\u5355\u4f4d\u4fe1\u606f\uff1a" + orgCode));
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    public ExportExcelSheet exportSheet(String tabSelect, Map<String, CellStyle> cellStyleMap, boolean templateExportFlag, String billTitle, List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOList) {
        ExportExcelSheet exportExcelSheet = null;
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle contentString = cellStyleMap.get("contentString");
        List<String> titleNames = BillFormulaSchemeConfigUtils.listBillTitleName(tabSelect);
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
        for (FormulaSchemeConfigTableVO formulaSchemeConfigTableVO : formulaSchemeConfigTableVOList) {
            Object[] rowData = new Object[titleNames.size()];
            rowData[0] = billTitle;
            rowData[1] = formulaSchemeConfigTableVO.getOrgId() + "|" + formulaSchemeConfigTableVO.getOrgTitle();
            rowData[2] = formulaSchemeConfigTableVO.getFetchScheme();
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

    private List<String> getOrgCode(String orgCode, String billId) {
        String masterTableName = (String)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.settingClient.getMasterTableName(billId));
        DataModelColumn column = (DataModelColumn)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.settingClient.getDataModelColumn(masterTableName, "UNITCODE"));
        String orgType = BillExtractUtil.queryOrgTypeByColumn((DataModelColumn)column);
        List orgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgCode);
        if (orgDOList == null) {
            return new ArrayList<String>(16);
        }
        return orgDOList.stream().filter(org -> null != org).map(OrgDO::getCode).collect(Collectors.toList());
    }
}

