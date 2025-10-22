/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.GcFinancialCheckDataEntryService;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.xlib.utils.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInputImportExecutor
extends AbstractImportExcelOneSheetExecutor {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private GcFinancialCheckDataEntryService dataEntryService;
    @Autowired
    DimensionService dimensionService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    DataModelService dataModelService;

    protected Object importExcelSheet(ImportContext context, List<Object[]> excelDatas) {
        List<GcRelatedItemVO> items;
        try {
            items = this.convertByExcel(excelDatas);
        }
        catch (ParseException e) {
            this.logger.error("\u6570\u636e\u83b7\u53d6\u5bfc\u5165\u65f6\u89e3\u6790\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u6570\u636e\u83b7\u53d6\u5bfc\u5165\u65f6\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage());
        }
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            items.stream().collect(Collectors.groupingBy(GcRelatedItemVO::getAcctPeriod)).entrySet().forEach(entry -> this.dataEntryService.saveRelatedItems((List)entry.getValue()));
            return "\u5bfc\u5165\u6210\u529f";
        }
        this.dataEntryService.saveRelatedItems(items);
        return "\u5bfc\u5165\u6210\u529f";
    }

    private List<GcRelatedItemVO> convertByExcel(List<Object[]> excelDatas) throws ParseException {
        List dims = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<GcRelatedItemVO> voucherItemS = new ArrayList<GcRelatedItemVO>();
        Object[] columns = excelDatas.get(0);
        if (columns.length - 26 != dims.size()) {
            throw new BusinessRuntimeException("\u6a21\u677f\u5df2\u53d1\u751f\u53d8\u5316,\u8bf7\u91cd\u65b0\u5bfc\u51fa\u6a21\u677f");
        }
        for (int i = 1; i < excelDatas.size(); ++i) {
            Object[] data = excelDatas.get(i);
            GcRelatedItemVO item = new GcRelatedItemVO();
            item.setSubjectCode(Objects.isNull(data[0]) ? "" : String.valueOf(data[0]));
            item.setUnitId(Objects.isNull(data[2]) ? "" : String.valueOf(data[2]));
            item.setOppUnitId(Objects.isNull(data[4]) ? "" : String.valueOf(data[4]));
            item.setAcctYear(Objects.isNull(data[6]) ? null : Integer.valueOf((String)data[6]));
            item.setAcctPeriod(Objects.isNull(data[7]) ? null : Integer.valueOf((String)data[7]));
            item.setOriginalCurr(Objects.isNull(data[8]) ? "" : String.valueOf(data[8]));
            item.setDebitOrig(Objects.isNull(data[10]) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)data[10])));
            item.setCreditOrig(Objects.isNull(data[11]) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)data[11])));
            item.setCurrency(Objects.isNull(data[12]) ? "" : String.valueOf(data[12]));
            item.setDebit(Objects.isNull(data[14]) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)data[14])));
            item.setCredit(Objects.isNull(data[15]) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)data[15])));
            item.setGcNumber(Objects.isNull(data[16]) ? "" : String.valueOf(data[16]));
            item.setCreateDate(Objects.isNull(data[17]) || StringUtil.isEmpty((String)((String)data[17])) ? null : sdf.parse((String)data[17]));
            item.setMemo(Objects.isNull(data[18]) ? "" : String.valueOf(data[18]));
            item.setItemOrder(Objects.isNull(data[19]) ? "" : String.valueOf(data[19]));
            item.setVchrType(Objects.isNull(data[20]) ? "" : String.valueOf(data[20]));
            item.setVchrNum(Objects.isNull(data[21]) ? "" : String.valueOf(data[21]));
            item.setDigest(Objects.isNull(data[22]) ? "" : String.valueOf(data[22]));
            item.setVchrSourceType(Objects.isNull(data[24]) ? "" : String.valueOf(data[24]));
            item.setBillCode(Objects.isNull(data[25]) ? "" : String.valueOf(data[25]));
            HashMap<String, Object> dimCodes = new HashMap<String, Object>();
            for (int j = 0; j < dims.size(); ++j) {
                String title = String.valueOf(columns[26 + j]);
                DimensionEO dim = (DimensionEO)dims.get(j);
                if (!dim.getTitle().equals(title)) continue;
                dimCodes.put(dim.getCode(), this.getBaseDataCodeByTitle(dim, data[26 + j]));
            }
            item.setDimensionCode(dimCodes);
            voucherItemS.add(item);
        }
        return voucherItemS;
    }

    private Object getBaseDataCodeByTitle(DimensionEO dim, Object data) {
        if (Objects.isNull(data)) {
            return data;
        }
        if (StringUtil.isEmpty((String)dim.getReferField())) {
            return data;
        }
        GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode(dim.getReferField(), ConverterUtils.getAsString((Object)data));
        if (Objects.nonNull(gcBaseData)) {
            return data;
        }
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(dim.getReferField());
        baseDataDTO.setName(ConverterUtils.getAsString((Object)data));
        baseDataDTO.setLeafFlag(Boolean.valueOf(true));
        PageVO list = this.baseDataClient.list(baseDataDTO);
        if (list == null || list.getRows().size() < 1) {
            throw new RuntimeException("\u901a\u8fc7" + data + "\u5728\u57fa\u7840\u6570\u636e" + dim.getReferField() + "\u627e\u4e0d\u5230\u679a\u4e3e\u9879");
        }
        return ((BaseDataDO)list.getRows().get(0)).get((Object)"code");
    }

    public String getName() {
        return "DataInputImportExecutor";
    }
}

