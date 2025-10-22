/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.SQLHelper
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.gcreport.asset.assetbill.dao.CombinedAssetBillDao;
import com.jiuqi.gcreport.asset.assetbill.service.CombinedAssetBillService;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.SQLHelper;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CombinedAssetBillServiceImpl
implements CombinedAssetBillService {
    @Autowired
    private CombinedAssetBillDao assetBillDao;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public String getAllBillListJson(String type) {
        List<Map<String, Object>> allBillList = this.assetBillDao.listAllBillList(type);
        StringBuilder result = new StringBuilder(32);
        result.append("[");
        for (Map<String, Object> oneBill : allBillList) {
            result.append("{\"title\": \"").append(oneBill.get("TITLE")).append("\", \"code\": \"").append(oneBill.get("UNIQUECODE")).append("\"},");
        }
        if (result.length() > 1) {
            result.setLength(result.length() - 1);
        }
        result.append("]");
        return result.toString();
    }

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
            InvestBillTool.formatBillContent(records, params, (String)"GC_COMBINEDASSETBILL");
            this.grossProfitRateHandle(params, records);
            PageInfo pageInfo = PageInfo.of(records, (int)(offset / pageSize + 1), (int)pageSize, (int)totalCount);
            return pageInfo;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public void batchDelete(List<String> ids) {
        if (org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.assetBillDao.batchDeleteByIdList(ids);
    }

    @Override
    public void batchUnDisposal(List<String> ids) {
        if (org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.assetBillDao.batchUnDisposalByIdList(ids);
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
    public List<ExportExcelSheet> getExcelSheets(ExportContext context, Map<String, Object> params) {
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        ArrayList<String> itemColumnCodes = new ArrayList<String>();
        HashSet<String> mastAssetTypeOrTitele = new HashSet<String>();
        String defineCode = (String)params.get("defineCode");
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo((String)defineCode);
        LinkedHashSet masterColumnCodes = billInfoVo.getMasterColumnCodes();
        List<ExportExcelSheet> exportExcelSheets = this.exportHead(masterColumnCodes, context, itemColumnCodes, mastAssetTypeOrTitele);
        ExportExcelSheet mastSheet = exportExcelSheets.get(0);
        ExportExcelSheet itemSheet = exportExcelSheets.get(1);
        if (context.isTemplateExportFlag()) {
            return exportExcelSheets;
        }
        HashSet<String> mastBillCodes = new HashSet<String>();
        List<Map<String, Object>> mastRecords = this.allListAssetBills(params, mastBillCodes);
        if (mastRecords == null || mastRecords.size() == 0) {
            return exportExcelSheets;
        }
        for (Map<String, Object> record : mastRecords) {
            Object[] values = new Object[masterColumnCodes.size()];
            int columnIndex = 0;
            for (String code : masterColumnCodes) {
                values[columnIndex++] = record.get(code);
            }
            mastSheet.getRowDatas().add(values);
        }
        List<Map<String, Object>> itemRecords = this.assetBillDao.listItemAssetBillsByBillCodes(mastBillCodes, itemColumnCodes, mastAssetTypeOrTitele);
        InvestBillTool.formatBillContent(itemRecords, params, (String)"GC_COMBINEDASSETBILL");
        if (itemRecords == null || itemRecords.size() == 0) {
            return exportExcelSheets;
        }
        for (Map<String, Object> record : itemRecords) {
            Object[] values = new Object[itemColumnCodes.size()];
            for (int i = 0; i < itemColumnCodes.size(); ++i) {
                values[i] = record.get(itemColumnCodes.get(i));
            }
            itemSheet.getRowDatas().add(values);
        }
        return exportExcelSheets;
    }

    @Override
    public String commonAssetBillImport(Map<String, Object> params, List<ImportExcelSheet> excelSheets) {
        StringBuilder log = new StringBuilder(128);
        ImportExcelSheet mastImportExcelSheet = excelSheets.get(0);
        ImportExcelSheet itemImportExcelSheet = excelSheets.get(1);
        String defineCode = (String)params.get("defineCode");
        YearPeriodObject yp = new YearPeriodObject(null, "");
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService noAuthOrgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Map<String, String> allLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(noAuthOrgTool, null);
        ColumnModelDefine[] mastFieldDefines = this.parseExcelHeaderColumnCodes(mastImportExcelSheet.getExcelSheetDatas());
        boolean mastHasAssetType = false;
        boolean mastHasAssetTitle = false;
        for (ColumnModelDefine fieldDefine : mastFieldDefines) {
            if ("ASSETTITLE".equals(fieldDefine.getCode())) {
                mastHasAssetTitle = true;
            }
            if (!"ASSETTYPE".equals(fieldDefine.getCode())) continue;
            mastHasAssetType = true;
        }
        SQLHelper mastSqlHelper = new SQLHelper("GC_COMBINEDASSETBILL");
        HashMap<String, Map<String, String>> tableName2MapDictTitle2CodeCache = new HashMap<String, Map<String, String>>(16);
        String userId = ShiroUtil.getUser().getId();
        HashMap<String, List<Map<String, Object>>> mastUnionKeyRecordMap = new HashMap<String, List<Map<String, Object>>>();
        for (int i = 1; i < mastImportExcelSheet.getExcelSheetDatas().size(); ++i) {
            Object[] oneRowData = (Object[])mastImportExcelSheet.getExcelSheetDatas().get(i);
            try {
                Map<String, Object> record = this.parseExcelContentRow(oneRowData, mastFieldDefines, allLeafUnitTitle2OrgCodeMap, tableName2MapDictTitle2CodeCache);
                String unitCode = (String)record.get("UNITCODE");
                Assert.isNotEmpty((String)unitCode, (String)"\u672a\u89e3\u6790\u5230\u91c7\u8d2d\u5355\u4f4d", (Object[])new Object[0]);
                record.put("CREATETIME", new Date());
                record.put("VER", System.currentTimeMillis());
                record.put("CREATEUSER", userId);
                record.put("DEFINECODE", defineCode);
                record.put("BILLCODE", this.getBillCode(defineCode, unitCode));
                record.put("BILLDATE", new Date());
                record.put("ID", UUIDUtils.newUUIDStr());
                Object assetTypeObj = record.get("ASSETTYPE");
                String assetType = assetTypeObj == null ? "" : (String)assetTypeObj;
                Object assetTitleObj = record.get("ASSETTITLE");
                String assetTitle = assetTitleObj == null ? "" : (String)assetTitleObj;
                this.mapAddList(mastUnionKeyRecordMap, this.getUnionKey(unitCode, assetType, assetTitle, mastHasAssetTitle, mastHasAssetType), record);
                mastSqlHelper.saveData(record);
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u4e3b\u8868\uff0c\u7b2c%1d\u884c\uff1a%2s \n", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u4e3b\u8868\uff0c\u7b2c%1d\u884c\uff1a%2s \n", i, e.getMessage()));
                e.printStackTrace();
            }
        }
        ColumnModelDefine[] itemFieldDefines = this.parseExcelHeaderColumnCodes(itemImportExcelSheet.getExcelSheetDatas());
        SQLHelper itemSqlHelper = new SQLHelper("GC_COMBINEDASSETBILLITEM");
        for (int i = 1; i < itemImportExcelSheet.getExcelSheetDatas().size(); ++i) {
            Object[] oneRowData = (Object[])itemImportExcelSheet.getExcelSheetDatas().get(i);
            try {
                Map<String, Object> record = this.parseExcelContentRow(oneRowData, itemFieldDefines, allLeafUnitTitle2OrgCodeMap, tableName2MapDictTitle2CodeCache);
                String unitCode = (String)record.get("UNITCODE");
                String oppUnitCode = (String)record.get("OPPUNITCODE");
                Assert.isNotEmpty((String)unitCode, (String)"\u672a\u89e3\u6790\u5230\u91c7\u8d2d\u5355\u4f4d", (Object[])new Object[0]);
                Assert.isNotEmpty((String)oppUnitCode, (String)"\u672a\u89e3\u6790\u5230\u9500\u552e\u5355\u4f4d", (Object[])new Object[0]);
                Object assetTypeObj = record.get("ASSETTYPE");
                String assetType = assetTypeObj == null ? "" : (String)assetTypeObj;
                Object assetTitleObj = record.get("ASSETTITLE");
                String assetTitle = assetTitleObj == null ? "" : (String)assetTitleObj;
                String unionKey = this.getUnionKey(unitCode, assetType, assetTitle, mastHasAssetTitle, mastHasAssetType);
                List mastRecordList = (List)mastUnionKeyRecordMap.get(unionKey);
                if (org.springframework.util.CollectionUtils.isEmpty(mastRecordList)) continue;
                record.put("VER", System.currentTimeMillis());
                record.put("MASTERID", ((Map)mastRecordList.get(0)).get("ID"));
                record.put("BILLCODE", ((Map)mastRecordList.get(0)).get("BILLCODE"));
                itemSqlHelper.saveData(record);
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u5b50\u8868\uff0c\u7b2c%1d\u884c\uff1a%2s \n", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u5b50\u8868\uff0c\u7b2c%1d\u884c\uff1a%2s \n", i, e.getMessage()));
                e.printStackTrace();
            }
        }
        return log.toString();
    }

    private String getUnionKey(String unitCode, String assetType, String assetTitle, boolean mastHasAssetTitle, boolean mastHasAssetType) {
        StringBuffer unionKey = new StringBuffer(unitCode);
        if (mastHasAssetType) {
            unionKey.append("|").append(assetType);
        }
        if (mastHasAssetTitle) {
            unionKey.append("|").append(assetTitle);
        }
        return unionKey.toString();
    }

    private void mapAddList(Map<String, List<Map<String, Object>>> mastUnionKeyRecordMap, String key, Map<String, Object> record) {
        List<Map<String, Object>> recordList = mastUnionKeyRecordMap.get(key);
        if (recordList == null) {
            recordList = new ArrayList<Map<String, Object>>();
        }
        recordList.add(record);
        mastUnionKeyRecordMap.put(key, recordList);
    }

    private Object getBillCode(String uniqueCode, String unitCode) {
        return InvestBillTool.getBillCode((String)uniqueCode, (String)unitCode);
    }

    private ColumnModelDefine[] parseExcelHeaderColumnCodes(List<Object[]> excelSheetDatas) {
        if (CollectionUtils.isEmpty(excelSheetDatas)) {
            return new ColumnModelDefine[0];
        }
        Object[] titles = excelSheetDatas.get(0);
        ColumnModelDefine[] codes = new ColumnModelDefine[titles.length];
        Map columnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_COMBINEDASSETBILL").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        for (int i = 0; i < titles.length; ++i) {
            codes[i] = (ColumnModelDefine)columnTitle2CodeMap.get(titles[i]);
        }
        return codes;
    }

    private Map<String, Object> parseExcelContentRow(Object[] oneRowData, ColumnModelDefine[] fieldDefines, Map<String, String> allLeafUnitTitle2OrgCodeMap, Map<String, Map<String, String>> tableName2MapDictTitle2CodeCache) {
        HashMap<String, Object> record = new HashMap<String, Object>(16);
        int rowLength = oneRowData.length < fieldDefines.length ? oneRowData.length : fieldDefines.length;
        for (int j = 0; j < rowLength; ++j) {
            String unitCode;
            Object cellValue = oneRowData[j];
            ColumnModelDefine fieldDefine = fieldDefines[j];
            if (null == fieldDefine) continue;
            if ("UNITCODE".equals(fieldDefine.getCode())) {
                unitCode = allLeafUnitTitle2OrgCodeMap.get(cellValue);
                Assert.isNotEmpty((String)unitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + ":" + cellValue), (Object[])new Object[0]);
                record.put(fieldDefine.getCode(), unitCode);
                continue;
            }
            if ("OPPUNITCODE".equals(fieldDefine.getCode())) {
                unitCode = allLeafUnitTitle2OrgCodeMap.get(cellValue);
                Assert.isNotEmpty((String)unitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + ":" + cellValue), (Object[])new Object[0]);
                record.put(fieldDefine.getCode(), unitCode);
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

    private Map<String, String> leafUnitTitle2OrgCodeMap(GcOrgCenterService orgTool, String parentId) {
        List orgCacheVOs = orgTool.listAllOrgByParentIdContainsSelf(parentId);
        if (orgCacheVOs == null) {
            return new HashMap<String, String>(16);
        }
        return orgCacheVOs.stream().filter(org -> null != org && org.isLeaf()).collect(Collectors.toMap(GcOrgCacheVO::getTitle, GcOrgCacheVO::getId, (v1, v2) -> v2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Map<String, Object>> allListAssetBills(Map<String, Object> params, Set<String> mastBillCodes) {
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
            mastBillCodes.addAll(billCodes);
            List<Map<String, Object>> records = this.assetBillDao.listAssetBillsByBillCodes(billCodes);
            InvestBillTool.formatBillContent(records, params, (String)"GC_COMBINEDASSETBILL");
            this.grossProfitRateHandle(params, records);
            List<Map<String, Object>> list = records;
            return list;
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
            String oppUnitObj;
            Object grossProfitRate = record.get("GROSSPROFITRATE");
            if (grossProfitRate == null || StringUtils.isEmpty((String)grossProfitRate.toString()) || (oppUnitObj = record.get("OPPUNITCODE") + "_ID") == null || StringUtils.isEmpty((String)oppUnitObj.toString()) || (orgByCode = orgCenterTool.getOrgByCode(oppUnitObj.toString())) != null) continue;
            record.put("GROSSPROFITRATE", "***");
        }
    }

    private List<ExportExcelSheet> exportHead(LinkedHashSet<String> columnCodes, ExportContext context, List<String> itemColumnCodes, Set<String> mastAssetTypeOrTitele) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(0), "\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u4e3b\u8868");
        List fieldDefines = NrTool.queryAllColumnsInTable((String)"GC_COMBINEDASSETBILL");
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = fieldDefines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (k1, k2) -> k1));
        String[] titles = new String[columnCodes.size()];
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
        int headIndex = 0;
        for (String columnCode : columnCodes) {
            ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCode);
            titles[headIndex] = define.getTitle();
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.INTEGER || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                mastSheet.getContentCellStyleCache().put(headIndex, contentAmt);
                mastSheet.getHeadCellStyleCache().put(headIndex, headAmt);
            }
            ++headIndex;
        }
        mastSheet.getRowDatas().add(titles);
        ColumnModelDefine assetType = null;
        ColumnModelDefine assetTitle = null;
        for (ColumnModelDefine fieldDefine : fieldDefines) {
            if ("ASSETTITLE".equals(fieldDefine.getCode())) {
                assetTitle = fieldDefine;
                mastAssetTypeOrTitele.add(fieldDefine.getCode());
                continue;
            }
            if (!"ASSETTYPE".equals(fieldDefine.getCode())) continue;
            assetType = fieldDefine;
            mastAssetTypeOrTitele.add(fieldDefine.getCode());
        }
        ExportExcelSheet itemSheet = new ExportExcelSheet(Integer.valueOf(1), "\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u5b50\u8868");
        List itemFields = NrTool.queryAllColumnsInTable((String)"GC_COMBINEDASSETBILLITEM");
        Set itemFieldCodes = itemFields.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        if (assetType != null && !itemFieldCodes.contains("ASSETTYPE")) {
            itemFields.add(assetType);
        }
        if (assetTitle != null && !itemFieldCodes.contains("ASSETTITLE")) {
            itemFields.add(assetTitle);
        }
        ArrayList<String> itemTitleList = new ArrayList<String>();
        HashSet<String> billSystemField = new HashSet<String>();
        billSystemField.add("ID");
        billSystemField.add("VER");
        billSystemField.add("MASTERID");
        billSystemField.add("BILLCODE");
        billSystemField.add("ORDINAL");
        int i = 0;
        for (ColumnModelDefine define : itemFields) {
            if (billSystemField.contains(define.getCode())) continue;
            itemTitleList.add(define.getTitle());
            itemColumnCodes.add(define.getCode());
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.INTEGER || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                itemSheet.getContentCellStyleCache().put(i, contentAmt);
                itemSheet.getHeadCellStyleCache().put(i, headAmt);
            }
            ++i;
        }
        itemSheet.getRowDatas().add(itemTitleList.toArray());
        exportExcelSheets.add(mastSheet);
        exportExcelSheets.add(itemSheet);
        return exportExcelSheets;
    }

    private TempTableCondition getTempTableCondition(Map<String, Object> params) {
        String mergeUnit = (String)params.remove("mergeUnit");
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(mergeUnit);
        if (null == orgCacheVO) {
            return null;
        }
        List orgIds = orgCenterTool.listAllOrgByParentIdContainsSelf(mergeUnit).stream().map(GcOrgCacheVO::getId).collect(Collectors.toList());
        return SqlUtils.getConditionOfIds(orgIds, (String)" ");
    }
}

