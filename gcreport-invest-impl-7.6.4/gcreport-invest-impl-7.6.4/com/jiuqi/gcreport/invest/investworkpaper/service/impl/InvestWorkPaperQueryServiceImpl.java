/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.billcore.service.CommonBillService
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.investworkpaper.vo.Column
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo$SettingData
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.billcore.service.CommonBillService;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.invest.investworkpaper.enums.DataSourceEnum;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperQueryService;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperSettingService;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.InvestWorkPaperFormDataQueryTask;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.InvestWorkPaperOffsetDataQueryTask;
import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestWorkPaperQueryServiceImpl
implements InvestWorkPaperQueryService {
    private static final Logger logger = LoggerFactory.getLogger(InvestWorkPaperQueryServiceImpl.class);
    @Autowired
    private InvestBillService investBillService;
    @Autowired
    private InvestWorkPaperSettingService investWorkPaperSettingService;
    @Autowired
    private InvestWorkPaperFormDataQueryTask investWorkPaperFomDataQueryTask;
    @Autowired
    private InvestWorkPaperOffsetDataQueryTask investWorkPaperOffsetDataQueryTask;
    @Autowired
    private CommonBillService commonBillService;

    @Override
    public InvestWorkPaperQueryResultVo getInvestWorkPaperColumnsAndDatas(InvestWorkPaperQueryCondition condition) {
        InvestWorkPaperQueryResultVo investWorkPaperQueryResultVo = new InvestWorkPaperQueryResultVo();
        List<Map<String, Object>> investBills = this.listInvestBills(condition);
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List orgCacheVOs = orgTool.listAllOrgByParentIdContainsSelf(condition.getMergeUnitId());
        Map<String, String> orgCode2TitleMap = orgCacheVOs.stream().collect(Collectors.toMap(orgCacheVO -> orgCacheVO.getCode(), orgCacheVO -> orgCacheVO.getTitle()));
        investWorkPaperQueryResultVo.setTableColumns(this.buildTableColumnList(investBills, orgCode2TitleMap));
        ArrayList<InvestWorkPaperRowData> tableRowDatas = new ArrayList<InvestWorkPaperRowData>();
        InvestWorkPaperSettingVo.SettingData investWorkPaperSettingData = this.getSettingData(condition);
        tableRowDatas.addAll(this.assembleInvestData(investBills, investWorkPaperSettingData));
        tableRowDatas.addAll(this.investWorkPaperFomDataQueryTask.assembleFomData(investBills, investWorkPaperSettingData, condition, orgCacheVOs));
        tableRowDatas.addAll(this.investWorkPaperOffsetDataQueryTask.assembleOffsetData(investBills, investWorkPaperSettingData, condition));
        investWorkPaperQueryResultVo.setTableRowDatas(tableRowDatas);
        return investWorkPaperQueryResultVo;
    }

    private InvestWorkPaperSettingVo.SettingData getSettingData(InvestWorkPaperQueryCondition condition) {
        InvestWorkPaperSettingVo investWorkPaperSettingVo = this.investWorkPaperSettingService.getSettingData(condition.getTaskId(), condition.getSystemId(), condition.getOrgType());
        InvestWorkPaperSettingVo.SettingData settingData = investWorkPaperSettingVo.getSettingData();
        return settingData;
    }

    private List<InvestWorkPaperRowData> assembleInvestData(List<Map<String, Object>> investBills, InvestWorkPaperSettingVo.SettingData settingData) {
        Map<String, Map> investedAndInvestUnitCode2InvestMap = investBills.stream().collect(Collectors.toMap(investBill -> investBill.get("INVESTEDUNIT_CODE") + "_" + investBill.get("UNITCODE_CODE"), investBill -> investBill));
        List investFieldList = settingData.getTzSetting();
        ArrayList<String> investFieldCodes = new ArrayList<String>(Arrays.asList("UNITCODE", "INVESTEDUNIT", "ENDEQUITYRATIO", "COMPREEQUITYRATIO"));
        if (!CollectionUtils.isEmpty((Collection)investFieldList)) {
            investFieldCodes.addAll(investFieldList.stream().map(investfield -> investfield.getFieldCode()).collect(Collectors.toList()));
        }
        HashMap<String, String> columnKey2TitleMap = new HashMap<String, String>();
        List investNotSystemColumns = this.commonBillService.listNotSystemFileds("GC_INVESTBILL");
        columnKey2TitleMap.putAll(investNotSystemColumns.stream().collect(Collectors.toMap(item -> item.getColumnName(), item -> item.getColumnTitle())));
        columnKey2TitleMap.put("ENDEQUITYRATIO", "\u671f\u672b\u80a1\u6743\u6bd4\u4f8b %");
        columnKey2TitleMap.put("COMPREEQUITYRATIO", "\u5171\u540c\u4e0a\u7ea7\u5bf9\u6295\u8d44\u5355\u4f4d\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b %");
        ArrayList<InvestWorkPaperRowData> tableRowDatas = new ArrayList<InvestWorkPaperRowData>();
        investFieldCodes.forEach(fieldCode -> {
            InvestWorkPaperRowData rowData = new InvestWorkPaperRowData();
            rowData.setDataSourceTitle(DataSourceEnum.INVESTDATA.getTitle());
            rowData.setDataSource(DataSourceEnum.INVESTDATA.getCode());
            rowData.setZbTitle((String)columnKey2TitleMap.get(fieldCode));
            investedAndInvestUnitCode2InvestMap.forEach((investedAndInvestUnitCode, investBill) -> rowData.addDynamicField(investedAndInvestUnitCode, investBill.get(fieldCode)));
            tableRowDatas.add(rowData);
        });
        return tableRowDatas;
    }

    public List<Column> buildTableColumnList(List<Map<String, Object>> investBills, Map<String, String> orgCode2TitleMap) {
        ArrayList<Column> tableColumnList = new ArrayList<Column>(32);
        tableColumnList.add(new Column("dataSourceTitle", "\u6570\u636e\u6765\u6e90"));
        tableColumnList.add(new Column("orientTitle", "\u65b9\u5411"));
        tableColumnList.add(new Column("zbTitle", "\u6307\u6807"));
        investBills.forEach(investBill -> {
            String investedUnit = (String)investBill.get("INVESTEDUNIT_CODE");
            String unitCode = (String)investBill.get("UNITCODE_CODE");
            String columnKey = investedUnit + "_" + unitCode;
            String columnTitle = investBill.get("INVESTEDUNIT") + "_" + investBill.get("UNITCODE");
            Column column = new Column(columnKey, columnTitle);
            column.setBillCode((String)investBill.get("BILLCODE"));
            tableColumnList.add(column);
        });
        tableColumnList.add(new Column("notCurrentLevelInvestOffset", "\u5f53\u524d\u5c42\u7ea7\u5f52\u5c5e\u6295\u8d44\u53f0\u8d26\u4ee5\u5916\u7684\u62b5\u9500\u5206\u5f55"));
        return tableColumnList;
    }

    private List<Map<String, Object>> listInvestBills(InvestWorkPaperQueryCondition condition) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        YearPeriodDO yearPeriodDO = yp.formatYP();
        params.put("acctYear", String.valueOf(yearPeriodDO.getYear()));
        params.put("acctPeriod", String.valueOf(yearPeriodDO.getPeriod()));
        params.put("periodStr", condition.getPeriodStr());
        params.put("mergeUnit", condition.getMergeUnitId());
        HashMap<String, String> mergeRange = new HashMap<String, String>();
        mergeRange.put("mergeRange", "mergeLevel");
        params.put("filterParam", mergeRange);
        List<Map<String, Object>> investBills = this.investBillService.listInvests(params);
        investBills = this.sortedInvestBills(investBills);
        List investedUnitIds = condition.getInvestedUnitIds();
        if (CollectionUtils.isEmpty((Collection)investedUnitIds)) {
            return investBills;
        }
        return investBills.stream().filter(investBill -> investedUnitIds.contains(investBill.get("INVESTEDUNIT_ID"))).collect(Collectors.toList());
    }

    private List<Map<String, Object>> sortedInvestBills(List<Map<String, Object>> investBills) {
        Comparator investBillComparator = (record1, record2) -> {
            boolean isDirect2;
            String mergeType1 = (String)record1.get("MERGETYPE");
            String mergeType2 = (String)record2.get("MERGETYPE");
            boolean isDirect1 = InvestInfoEnum.DIRECT.getTitle().equals(mergeType1);
            if (isDirect1 != (isDirect2 = InvestInfoEnum.DIRECT.getTitle().equals(mergeType2))) {
                return isDirect1 ? -1 : 1;
            }
            return MapUtils.compareStr((Map)record1, (Map)record2, (Object)"CREATETIME");
        };
        investBills.sort(investBillComparator);
        Map groupedByInvestedUnit = investBills.stream().collect(Collectors.groupingBy(bill -> (String)bill.get("INVESTEDUNIT_CODE"), Collectors.toList()));
        groupedByInvestedUnit.forEach((code, bills) -> bills.sort(investBillComparator));
        ArrayList<Map<String, Object>> sortedInvestBills = new ArrayList<Map<String, Object>>();
        HashSet<String> processed = new HashSet<String>();
        for (Map<String, Object> bill2 : investBills) {
            String code2 = (String)bill2.get("INVESTEDUNIT_CODE");
            if (processed.contains(code2)) continue;
            sortedInvestBills.addAll(groupedByInvestedUnit.get(code2));
            processed.add(code2);
        }
        return sortedInvestBills;
    }
}

