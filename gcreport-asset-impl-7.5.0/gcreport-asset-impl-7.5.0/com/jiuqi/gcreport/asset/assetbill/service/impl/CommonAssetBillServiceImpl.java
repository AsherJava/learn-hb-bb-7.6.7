/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.SQLHelper
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils$Relation
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.billlist.controller.BillImportTemplateController
 *  com.jiuqi.va.billlist.dao.BillImportTemplateDao
 *  com.jiuqi.va.billlist.domain.dto.QueryTemplateDTO
 *  com.jiuqi.va.billlist.domain.dto.TableHeaderDTO
 *  com.jiuqi.va.billlist.domain.entity.BillImportTemplateDO
 *  com.jiuqi.va.billlist.service.BillImportTemplateService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.asset.assetbill.dao.CommonAssetBillDao;
import com.jiuqi.gcreport.asset.assetbill.service.CommonAssetBillService;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.SQLHelper;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.billlist.controller.BillImportTemplateController;
import com.jiuqi.va.billlist.dao.BillImportTemplateDao;
import com.jiuqi.va.billlist.domain.dto.QueryTemplateDTO;
import com.jiuqi.va.billlist.domain.dto.TableHeaderDTO;
import com.jiuqi.va.billlist.domain.entity.BillImportTemplateDO;
import com.jiuqi.va.billlist.service.BillImportTemplateService;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonAssetBillServiceImpl
implements CommonAssetBillService {
    private static Logger LOGGER = LoggerFactory.getLogger(CommonAssetBillServiceImpl.class);
    @Autowired
    private CommonAssetBillDao assetBillDao;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private BillImportTemplateController billImportTemplateController;
    @Autowired
    private BillImportTemplateDao billImportTemplateDao;
    @Autowired
    private BillImportTemplateService billImportTemplateService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<Map<String, Object>> listAssetBills(Map<String, Object> params) {
        int pageSize = (Integer)params.get("pageSize");
        int pageNum = (Integer)params.get("pageNum");
        int offset = (pageNum - 1) * pageSize;
        TempTableCondition tempTableCondition = this.getTempTableCondition(params);
        if (null == tempTableCondition) {
            return PageInfo.of(new ArrayList(), (int)(offset / pageSize + 1), (int)pageSize, (int)0);
        }
        try {
            int totalCount = this.assetBillDao.countAssetBills(tempTableCondition, params);
            if (totalCount <= 0) {
                PageInfo pageInfo = PageInfo.of(new ArrayList(), (int)(offset / pageSize + 1), (int)pageSize, (int)0);
                return pageInfo;
            }
            Set<String> billCodes = this.assetBillDao.listAssetBillCodesByPaging(tempTableCondition, params, offset, offset + pageSize);
            if (org.springframework.util.CollectionUtils.isEmpty(billCodes)) {
                PageInfo pageInfo = PageInfo.of(new ArrayList(), (int)(offset / pageSize + 1), (int)pageSize, (int)0);
                return pageInfo;
            }
            List<Map<String, Object>> records = this.assetBillDao.listAssetBillsByBillCodes(billCodes);
            InvestBillTool.formatBillContent(records, params, (String)"GC_COMMONASSETBILL");
            this.grossProfitRateHandle(params, records);
            PageInfo pageInfo = PageInfo.of(records, (int)(offset / pageSize + 1), (int)pageSize, (int)totalCount);
            return pageInfo;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    private void grossProfitRateHandle(Map<String, Object> params, List<Map<String, Object>> records) {
        Object acctYearObj = params.get("acctYear");
        int acctYear = null == acctYearObj ? Calendar.getInstance().get(1) : Integer.parseInt(acctYearObj.toString());
        YearPeriodObject yp = new YearPeriodObject(null, acctYear, 4, 12);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        for (Map<String, Object> record : records) {
            GcOrgCacheVO orgByCode;
            Object oppUnitObj;
            Object grossProfitRate = record.get("GROSSPROFITRATE");
            if (grossProfitRate == null || StringUtils.isEmpty((String)grossProfitRate.toString()) || (oppUnitObj = record.get("OPPUNITCODE_ID")) == null || StringUtils.isEmpty((String)oppUnitObj.toString()) || (orgByCode = orgCenterTool.getOrgByCode(oppUnitObj.toString())) != null) continue;
            record.put("GROSSPROFITRATE", "***");
        }
    }

    @Override
    public void batchDelete(List<String> ids) {
        if (org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<Map<String, Object>> AssetBills = this.assetBillDao.listAssetBillsByIds(ids);
        AssetBills.forEach(assetItem -> {
            String unitCode = (String)assetItem.get("UNITCODE");
            String oppunitCode = (String)assetItem.get("OPPUNITCODE");
            String zcbh = (String)assetItem.get("ZZBH");
            String operateTypeTitle = String.format(StringUtils.isEmpty((String)zcbh) ? "\u5220\u9664-\u91c7\u8d2d\u5355\u4f4d%1s-\u9500\u552e\u5355\u4f4d%2s" : "\u5220\u9664-\u91c7\u8d2d\u5355\u4f4d%1s-\u9500\u552e\u5355\u4f4d%2s-\u8d44\u4ea7\u7f16\u53f7%3s", unitCode, oppunitCode, zcbh);
            LogHelper.info((String)"\u5408\u5e76-\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
        });
        this.assetBillDao.batchDeleteByIdList(ids);
    }

    @Override
    public void batchUnDisposal(List<String> ids) {
        if (org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.assetBillDao.batchUnDisposalByIdList(ids);
        List<Map<String, Object>> AssetBills = this.assetBillDao.listAssetBillsByIds(ids);
        AssetBills.forEach(assetItem -> {
            String unitCode = (String)assetItem.get("UNITCODE");
            String oppunitCode = (String)assetItem.get("OPPUNITCODE");
            String zcbh = (String)assetItem.get("ZZBH");
            String operateTypeTitle = String.format(StringUtils.isEmpty((String)zcbh) ? "\u5904\u7f6e-\u91c7\u8d2d\u5355\u4f4d%1s-\u9500\u552e\u5355\u4f4d%2s" : "\u5904\u7f6e-\u91c7\u8d2d\u5355\u4f4d%1s-\u9500\u552e\u5355\u4f4d%2s-\u8d44\u4ea7\u7f16\u53f7%3s", unitCode, oppunitCode, zcbh);
            LogHelper.info((String)"\u5408\u5e76-\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
        });
    }

    @Override
    public String getIdsByBillCode(String tableName, String billCode) {
        List<String> ids = this.assetBillDao.listIdsByBillCode(tableName, billCode);
        return null == ids ? "" : String.join((CharSequence)",", ids);
    }

    @Override
    public void transfer2FixedAsset(String billCode) {
        this.assetBillDao.transfer2FixedAsset(billCode);
    }

    @Override
    public ExportExcelSheet getExcelSheet(ExportContext context, Map<String, Object> params) {
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        LinkedHashSet<String> masterColumnCodes = new LinkedHashSet<String>((List)params.get("columnCodes"));
        ExportExcelSheet sheet = this.exportHead(masterColumnCodes, context);
        if (context.isTemplateExportFlag()) {
            return sheet;
        }
        List<Map<String, Object>> records = this.allListAssetBills(params);
        if (records == null || records.size() == 0) {
            return sheet;
        }
        for (Map<String, Object> record : records) {
            Object[] values = new Object[masterColumnCodes.size()];
            int columnIndex = 0;
            for (String code : masterColumnCodes) {
                values[columnIndex++] = record.get(code);
            }
            sheet.getRowDatas().add(values);
        }
        return sheet;
    }

    @Override
    public StringBuilder commonAssetBillImport(Map<String, Object> params, List<Object[]> excelDatas) {
        StringBuilder log = new StringBuilder(128);
        String defineCode = (String)params.get("defineCode");
        YearPeriodObject yp = new YearPeriodObject(null, "");
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService noAuthOrgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Map<String, String> allLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(noAuthOrgTool, null);
        ColumnModelDefine[] fieldDefines = this.parseExcelHeaderColumnCodes(excelDatas);
        SQLHelper sqlHelper = new SQLHelper("GC_COMMONASSETBILL");
        HashMap<String, Map<String, String>> tableName2MapDictTitle2CodeCache = new HashMap<String, Map<String, String>>(16);
        String userId = ShiroUtil.getUser().getId();
        for (int i = 1; i < excelDatas.size(); ++i) {
            Object[] oneRowData = excelDatas.get(i);
            try {
                Map<String, Object> record = this.parseExcelContentRow(oneRowData, fieldDefines, allLeafUnitTitle2OrgCodeMap, tableName2MapDictTitle2CodeCache, noAuthOrgTool);
                String unitCode = (String)record.get("UNITCODE");
                String oppUnitCode = (String)record.get("OPPUNITCODE");
                Assert.isNotEmpty((String)unitCode, (String)"\u672a\u89e3\u6790\u5230\u91c7\u8d2d\u5355\u4f4d", (Object[])new Object[0]);
                Assert.isNotEmpty((String)oppUnitCode, (String)"\u672a\u89e3\u6790\u5230\u9500\u552e\u5355\u4f4d", (Object[])new Object[0]);
                record.put("CREATETIME", new Date());
                record.put("VER", System.currentTimeMillis());
                record.put("CREATEUSER", userId);
                record.put("DEFINECODE", defineCode);
                record.put("BILLCODE", this.getBillCode(defineCode, unitCode));
                record.put("BILLDATE", new Date());
                sqlHelper.saveData(record);
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u7b2c%1d\u884c\uff1a%2s<br>", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u7b2c%1d\u884c\uff1a%2s<br>", i, e.getMessage()));
                e.printStackTrace();
            }
        }
        return log;
    }

    private Object getBillCode(String uniqueCode, String unitCode) {
        return InvestBillTool.getBillCode((String)uniqueCode, (String)unitCode);
    }

    private Map<String, String> leafUnitTitle2OrgCodeMap(GcOrgCenterService orgTool, String parentId) {
        List orgCacheVOs = orgTool.listAllOrgByParentIdContainsSelf(parentId);
        if (orgCacheVOs == null) {
            return new HashMap<String, String>(16);
        }
        return orgCacheVOs.stream().filter(org -> null != org && org.isLeaf()).collect(Collectors.toMap(GcOrgCacheVO::getTitle, GcOrgCacheVO::getId, (v1, v2) -> v2));
    }

    private Map<String, Object> parseExcelContentRow(Object[] oneRowData, ColumnModelDefine[] fieldDefines, Map<String, String> allLeafUnitTitle2OrgCodeMap, Map<String, Map<String, String>> tableName2MapDictTitle2CodeCache, GcOrgCenterService noAuthOrgTool) {
        HashMap<String, Object> record = new HashMap<String, Object>(16);
        int rowLength = oneRowData.length < fieldDefines.length ? oneRowData.length : fieldDefines.length;
        String unitTitle = "";
        for (int j = 0; j < rowLength; ++j) {
            Object cellValue = oneRowData[j];
            ColumnModelDefine fieldDefine = fieldDefines[j];
            if (null == fieldDefine) continue;
            if ("UNITCODE".equals(fieldDefine.getCode())) {
                unitTitle = (String)cellValue;
                String unitCode = allLeafUnitTitle2OrgCodeMap.get(cellValue);
                Assert.isNotEmpty((String)unitCode, (String)String.format("\u91c7\u8d2d\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u5408\u5e76\u5355\u4f4d: \u91c7\u8d2d\u5355\u4f4d'%1s', \u6216\u8005\u586b\u5199\u5355\u4f4d\u6709\u8bef\uff0c\u627e\u4e0d\u89c1%2s: %1$s ", cellValue, fieldDefines[j].getTitle()), (Object[])new Object[0]);
                GcOrgCacheVO UnitVO = noAuthOrgTool.getOrgByCode(unitCode);
                if (UnitVO != null && GcOrgKindEnum.DIFFERENCE.equals((Object)UnitVO.getOrgKind())) {
                    throw new BusinessRuntimeException("\u91c7\u8d2d\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u5dee\u989d\u5355\u4f4d\uff1a\u91c7\u8d2d\u5355\u4f4d'" + unitTitle);
                }
                record.put(fieldDefine.getCode(), unitCode);
                continue;
            }
            if ("OPPUNITCODE".equals(fieldDefine.getCode())) {
                String oppUnitCode = allLeafUnitTitle2OrgCodeMap.get(cellValue);
                Assert.isNotEmpty((String)oppUnitCode, (String)String.format("\u9500\u552e\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u5408\u5e76\u5355\u4f4d: \u91c7\u8d2d\u5355\u4f4d'%1s', \u9500\u552e\u5355\u4f4d'%2s', \u6216\u8005\u586b\u5199\u5355\u4f4d\u6709\u8bef\uff0c\u627e\u4e0d\u89c1%3s: %2$s ", unitTitle, cellValue, fieldDefines[j].getTitle()), (Object[])new Object[0]);
                GcOrgCacheVO oppUnitVO = noAuthOrgTool.getOrgByCode(oppUnitCode);
                if (oppUnitVO != null && GcOrgKindEnum.DIFFERENCE.equals((Object)oppUnitVO.getOrgKind())) {
                    throw new BusinessRuntimeException("\u9500\u552e\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u5dee\u989d\u5355\u4f4d\uff1a\u91c7\u8d2d\u5355\u4f4d'" + unitTitle + "', \u9500\u552e\u5355\u4f4d'" + cellValue + "'");
                }
                record.put(fieldDefine.getCode(), oppUnitCode);
                continue;
            }
            if (!StringUtils.isEmpty((String)fieldDefine.getReferTableID())) {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(fieldDefine.getReferTableID());
                if (tableModelDefine == null) continue;
                String dictTableName = tableModelDefine.getName();
                record.put(fieldDefine.getCode(), NrTool.getDictCodeByTitle((String)dictTableName, (String)((String)cellValue), tableName2MapDictTitle2CodeCache));
                continue;
            }
            if (fieldDefine.getColumnType() == ColumnModelType.DOUBLE || fieldDefine.getColumnType() == ColumnModelType.BIGDECIMAL || fieldDefine.getColumnType() == ColumnModelType.INTEGER) {
                if (!StringUtils.isEmpty((String)((String)cellValue))) {
                    cellValue = String.valueOf(cellValue).replace(",", "");
                }
                record.put(fieldDefine.getCode(), cellValue);
                continue;
            }
            if (fieldDefine.getColumnType() == ColumnModelType.DATETIME) {
                if (StringUtils.isEmpty((String)((String)cellValue))) {
                    cellValue = null;
                }
                record.put(fieldDefine.getCode(), cellValue);
                continue;
            }
            record.put(fieldDefine.getCode(), cellValue);
        }
        return record;
    }

    private ColumnModelDefine[] parseExcelHeaderColumnCodes(List<Object[]> excelSheetDatas) {
        if (CollectionUtils.isEmpty(excelSheetDatas)) {
            return new ColumnModelDefine[0];
        }
        Object[] titles = excelSheetDatas.get(0);
        ColumnModelDefine[] codes = new ColumnModelDefine[titles.length];
        Map columnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_COMMONASSETBILL").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        for (int i = 0; i < titles.length; ++i) {
            codes[i] = (ColumnModelDefine)columnTitle2CodeMap.get(titles[i]);
        }
        return codes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Map<String, Object>> allListAssetBills(Map<String, Object> params) {
        TempTableCondition tempTableCondition = this.getTempTableCondition(params);
        if (null == tempTableCondition) {
            return new ArrayList<Map<String, Object>>();
        }
        try {
            Set<String> billCodes = this.assetBillDao.listAssetBillCodes(tempTableCondition, params);
            if (org.springframework.util.CollectionUtils.isEmpty(billCodes)) {
                ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
                return arrayList;
            }
            List<Map<String, Object>> records = this.assetBillDao.listAssetBillsByBillCodes(billCodes);
            InvestBillTool.formatBillContent(records, params, (String)"GC_COMMONASSETBILL");
            this.grossProfitRateHandle(params, records);
            List<Map<String, Object>> list = records;
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    private ExportExcelSheet exportHead(LinkedHashSet<String> columnCodes, ExportContext context) {
        TableHeaderDTO tableHeaderDTO;
        QueryTemplateDTO params = new QueryTemplateDTO();
        params.setBillDefineCode("GCBILL_B_GC_COMMONASSETBILL");
        List tempArray = (List)this.billImportTemplateController.getTemplateListByBillDefineCode(params).get((Object)"templist");
        String templateId = (String)((Map)tempArray.get(0)).get("id");
        BillImportTemplateDO billImportTemplateDoParam = new BillImportTemplateDO();
        billImportTemplateDoParam.setId(templateId);
        BillImportTemplateDO billImportTemplateDO = this.billImportTemplateDao.byId(billImportTemplateDoParam);
        if (billImportTemplateDO == null) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u67e5\u8be2\u5230\u5355\u636e\u5bfc\u5165\u6a21\u677f\u8bf7\u68c0\u67e5\u5355\u636e\u5bfc\u5165\u6a21\u677f\u914d\u7f6e");
        }
        String sheetName = null;
        String defaultName = null;
        List tableHeaders = this.billImportTemplateService.getTableHeaders(billImportTemplateDO);
        for (int i = 0; i < tableHeaders.size() && StringUtils.isEmpty((String)(sheetName = (tableHeaderDTO = (TableHeaderDTO)tableHeaders.get(i)).getSheetName())); ++i) {
            defaultName = tableHeaderDTO.getTitle() + "(" + tableHeaderDTO.getTableName() + ")";
        }
        if (StringUtils.isEmpty(sheetName)) {
            sheetName = defaultName;
        }
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(0), sheetName);
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)"GC_COMMONASSETBILL").stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (v1, v2) -> v1));
        String[] titles = new String[columnCodes.size()];
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
        int headIndex = 0;
        for (String code : columnCodes) {
            ColumnModelDefine define = columnCode2FieldDefineMap.get(code);
            titles[headIndex] = define.getTitle();
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.INTEGER || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                sheet.getContentCellStyleCache().put(headIndex, contentAmt);
                sheet.getContentCellTypeCache().put(headIndex, CellType.NUMERIC);
                sheet.getHeadCellStyleCache().put(headIndex, headAmt);
            }
            ++headIndex;
        }
        sheet.getRowDatas().add(titles);
        return sheet;
    }

    private TempTableCondition getTempTableCondition(Map<String, Object> params) {
        String mergeUnit = (String)params.get("mergeUnit");
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(mergeUnit);
        if (null == orgCacheVO) {
            return null;
        }
        List orgIds = orgCenterTool.listAllOrgByParentIdContainsSelf(mergeUnit).stream().map(GcOrgCacheVO::getId).collect(Collectors.toList());
        return SqlUtils.getConditionOfIds(orgIds, (String)" ", (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
    }
}

