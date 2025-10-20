/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.support.ExcelTypeEnum
 *  com.alibaba.excel.write.builder.ExcelWriterBuilder
 *  com.alibaba.excel.write.builder.ExcelWriterSheetBuilder
 *  com.alibaba.excel.write.handler.WriteHandler
 *  com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrQueryResultDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.adjustvchr.impl.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrQueryResultDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.enums.AdjustVoucherColumnEnum;
import com.jiuqi.dc.adjustvchr.impl.impexp.AdjustVoucherExpExcelCellStyleStrategy;
import com.jiuqi.dc.adjustvchr.impl.impexp.ExcelFillCellMergeStrategy;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.adjustvchr.impl.service.AdjustVoucherImpExpService;
import com.jiuqi.dc.adjustvchr.impl.utils.AdjustVchrImportCheckUtil;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdjustVoucherImpExpServiceImpl
implements AdjustVoucherImpExpService {
    @Autowired
    private AdjustVoucherClientService adjustVoucherService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private List<AdjustVchrImportCheckService> checkServiceList;
    private static final LinkedHashMap<String, String> convertCurrencyMap = new LinkedHashMap();
    private final Logger logger = LoggerFactory.getLogger(AdjustVoucherImpExpServiceImpl.class);
    private static final String EXAMPLELINEFLAG = "\u5220\u9664\u6b64\u884c";

    @Override
    public void exportAdjustVoucher(HttpServletResponse response, AdjustVoucherQueryDTO adjustVoucherQueryDTO, boolean templateExportFlag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "";
        try {
            fileName = URLEncoder.encode(String.format("\u8c03\u6574\u51ed\u8bc1%1$s", sdf.format(new Date())), StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            this.logger.error("\u5bfc\u51fa\u6587\u4ef6\u540d\u79f0\u5904\u7406\u5f02\u5e38", e);
        }
        try {
            AdjustVchrSysOptionVO optionVO = this.adjustVoucherService.getAdjustVchrSysOptions();
            List dimensionVOList = this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
            List<List<String>> headerColumns = this.getHeaderColumns(optionVO, dimensionVOList, templateExportFlag);
            List<Object> expDataList = new ArrayList();
            AdjustVchrQueryResultDTO adjustVchrQueryResultDTO = new AdjustVchrQueryResultDTO();
            if (templateExportFlag) {
                expDataList = this.getExportRemarkRow(optionVO, dimensionVOList);
            } else {
                adjustVoucherQueryDTO.setPagination(Boolean.valueOf(false));
                adjustVchrQueryResultDTO = this.adjustVoucherService.list(adjustVoucherQueryDTO);
                if (!CollectionUtils.isEmpty((Collection)adjustVchrQueryResultDTO.getVouchers())) {
                    expDataList = this.getExpData(adjustVchrQueryResultDTO.getVouchers(), dimensionVOList, optionVO);
                }
            }
            int[] mergeColumnIndex = new int[]{0, 1, 2, 3, 4, 5};
            int mergeRowIndex = 0;
            ArrayList<Integer> mergeRowNode = new ArrayList<Integer>();
            mergeRowNode.add(1);
            int count = 1;
            for (AdjustVoucherVO voucherVO : adjustVchrQueryResultDTO.getVouchers()) {
                mergeRowNode.add(count += voucherVO.getItems().size());
            }
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new AdjustVoucherExpExcelCellStyleStrategy(templateExportFlag))).registerWriteHandler((WriteHandler)new ExcelFillCellMergeStrategy(mergeRowIndex, mergeColumnIndex, mergeRowNode))).registerWriteHandler((WriteHandler)new SimpleColumnWidthStyleStrategy(Integer.valueOf(25)))).sheet("\u8c03\u6574\u51ed\u8bc1").head(headerColumns)).doWrite(expDataList);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bfc\u51fa\u5931\u8d25\uff01", e);
        }
    }

    @Override
    public Object importAdjustVoucher(List<Object[]> excelDatas, AdjustVoucherQueryDTO importParam) {
        AdjustVchrSysOptionVO optionVO = this.adjustVoucherService.getAdjustVchrSysOptions();
        ArrayList<String> importColumns = new ArrayList<String>();
        for (Object column : excelDatas.get(0)) {
            if (StringUtils.isEmpty((String)String.valueOf(column))) continue;
            importColumns.add(String.valueOf(column));
        }
        List<Object[]> importData = excelDatas.stream().filter(excelData -> !String.valueOf(excelData[((Object[])excelData).length - 1]).contains(EXAMPLELINEFLAG)).collect(Collectors.toList());
        if (importData.size() == 1) {
            return "Excel\u4e2d\u7684\u6570\u636e\u5e94\u5f53\u5305\u542b\u9664\u4e86\u8868\u5934\u548c\u793a\u4f8b\u884c\u4e4b\u5916\u7684\u8c03\u6574\u51ed\u8bc1\u6570\u636e\uff0c\u5bfc\u5165\u5931\u8d25";
        }
        importData = importData.subList(1, importData.size());
        List<AdjustVchrItemImportVO> adjustVchrList = AdjustVchrImportCheckUtil.getImportData(importColumns, importData);
        this.checkServiceList.sort(Comparator.comparing(AdjustVchrImportCheckService::checkOrder));
        boolean convertEnable = false;
        int preIndex = 1;
        preIndex += excelDatas.size() - importData.size();
        StringBuilder errorMessage = new StringBuilder();
        for (AdjustVchrImportCheckService checkService : this.checkServiceList) {
            errorMessage.append(checkService.checkImportData(preIndex, adjustVchrList, importParam, optionVO, convertEnable));
            if (StringUtils.isNull((String)errorMessage.toString())) continue;
            return errorMessage;
        }
        return AdjustVchrImportCheckUtil.saveAdjustVchrData(optionVO, adjustVchrList, importParam, convertCurrencyMap);
    }

    private List<List<String>> getHeaderColumns(AdjustVchrSysOptionVO optionVO, List<DimensionVO> dimensionVOList, boolean templateExportFlag) {
        ArrayList<List<String>> headerColumns = new ArrayList<List<String>>();
        List extraFields = optionVO.getExtraFields();
        boolean enableOrgnCurr = optionVO.isEnableOrgnCurr();
        boolean remarkEnable = optionVO.isRemarkEnable();
        for (AdjustVoucherColumnEnum columnEnum : AdjustVoucherColumnEnum.values()) {
            if (templateExportFlag && AdjustVoucherColumnEnum.VCHRNUM.getCode().equals(columnEnum.getCode())) {
                Iterator<DimensionVO> vchrNum = new ArrayList<String>();
                vchrNum.add((DimensionVO)AdjustVoucherColumnEnum.VCHRNUM.getName());
                headerColumns.add((List<String>)((Object)vchrNum));
                continue;
            }
            if (!enableOrgnCurr && (AdjustVoucherColumnEnum.CURRENCYCODE.getCode().equals(columnEnum.getCode()) || AdjustVoucherColumnEnum.EXCHANGERATE.getCode().equals(columnEnum.getCode()) || AdjustVoucherColumnEnum.ORGND.getCode().equals(columnEnum.getCode()) || AdjustVoucherColumnEnum.ORGNC.getCode().equals(columnEnum.getCode()))) continue;
            if (AdjustVoucherColumnEnum.EXTRAFIELDS.getCode().equals(columnEnum.getCode())) {
                if (CollectionUtils.isEmpty((Collection)extraFields)) continue;
                for (String extraField : extraFields) {
                    ArrayList<String> extraFieldName = new ArrayList<String>();
                    extraFieldName.add("BIZDATE".equals(extraField) ? "\u4e1a\u52a1\u65e5\u671f" : ("CFITEMCODE".equals(extraField) ? "\u73b0\u91d1\u6d41\u91cf\u5c5e\u6027" : "\u5230\u671f\u65e5"));
                    headerColumns.add(extraFieldName);
                }
                continue;
            }
            if (AdjustVoucherColumnEnum.ASSISTSTR.getCode().equals(columnEnum.getCode())) {
                for (DimensionVO dimensionVO : dimensionVOList) {
                    ArrayList<String> assistName = new ArrayList<String>();
                    assistName.add(dimensionVO.getTitle());
                    headerColumns.add(assistName);
                }
                continue;
            }
            if (!remarkEnable && AdjustVoucherColumnEnum.REMARK.getCode().equals(columnEnum.getCode())) continue;
            ArrayList<String> column = new ArrayList<String>();
            column.add(columnEnum.getName());
            headerColumns.add(column);
        }
        if (templateExportFlag) {
            ArrayList<String> remark = new ArrayList<String>();
            remark.add("");
            headerColumns.add(remark);
        }
        return headerColumns;
    }

    private List<List<Object>> getExportRemarkRow(AdjustVchrSysOptionVO optionVO, List<DimensionVO> dimensionVOList) {
        ArrayList<List<Object>> remarkContentList = new ArrayList<List<Object>>();
        List extraFields = optionVO.getExtraFields();
        boolean enableOrgnCurr = optionVO.isEnableOrgnCurr();
        boolean remarkEnable = optionVO.isRemarkEnable();
        ArrayList<String> remarkContent = new ArrayList<String>();
        ArrayList<String> exampleContent1 = new ArrayList<String>();
        ArrayList<String> exampleContent2 = new ArrayList<String>();
        for (AdjustVoucherColumnEnum columnEnum : AdjustVoucherColumnEnum.values()) {
            if (AdjustVoucherColumnEnum.ASSISTSTR.getCode().equals(columnEnum.getCode())) {
                for (DimensionVO dimensionVO : dimensionVOList) {
                    remarkContent.add(columnEnum.getRemark());
                    exampleContent1.add("");
                    exampleContent2.add("");
                }
                continue;
            }
            if (!remarkEnable && AdjustVoucherColumnEnum.REMARK.getCode().equals(columnEnum.getCode()) || !enableOrgnCurr && (AdjustVoucherColumnEnum.CURRENCYCODE.getCode().equals(columnEnum.getCode()) || AdjustVoucherColumnEnum.EXCHANGERATE.getCode().equals(columnEnum.getCode()) || AdjustVoucherColumnEnum.ORGND.getCode().equals(columnEnum.getCode()) || AdjustVoucherColumnEnum.ORGNC.getCode().equals(columnEnum.getCode()))) continue;
            if (!AdjustVoucherColumnEnum.EXTRAFIELDS.getCode().equals(columnEnum.getCode())) {
                remarkContent.add(columnEnum.getRemark());
                exampleContent1.add(columnEnum.getExample1());
                exampleContent2.add(columnEnum.getExample2());
                continue;
            }
            if (extraFields.size() == 0) continue;
            for (String extraField : extraFields) {
                if ("BIZDATE".equals(extraField)) {
                    remarkContent.add("\u975e\u5fc5\u586b\uff0c\u9700\u8981\u8ba1\u7b97\u8d26\u9f84\u7684\u79d1\u76ee\u8ba1\u7b97\u8d26\u9f84\u65f6\u4f7f\u7528\u7684\u5f00\u59cb\u65f6\u95f4\uff0c\u65e5\u671f\u578b\u683c\u5f0f\uff0c\u4f8b\u59822023-1-1");
                    exampleContent1.add("2023-1-1");
                    exampleContent2.add("2023-1-1");
                    continue;
                }
                if ("CFITEMCODE".equals(extraField)) {
                    remarkContent.add("\u8c03\u6574\u51ed\u8bc1\u4e2d\u79d1\u76ee\u7684\u73b0\u91d1\u6d41\u91cf\u8f85\u52a9\u6838\u7b97\u4fe1\u606f\uff0c\u6839\u636e\u79d1\u76ee\u8bbe\u7f6e\u7684\u73b0\u91d1\u6d41\u91cf\u8f85\u52a9\u6838\u7b97\u586b\u5199\u3002\r\n\u6ce8\u610f\uff1a\u79d1\u76ee\u7c7b\u578b\u4e3a\u73b0\u91d1\u6d41\u91cf\u7c7b\u672c\u5217\u4e0d\u53ef\u586b");
                    exampleContent1.add("");
                    exampleContent2.add("");
                    continue;
                }
                remarkContent.add("\u975e\u5fc5\u586b\uff0c\u9700\u8981\u8fdb\u884c\u5230\u671f\u65e5\u91cd\u5206\u7c7b\u7684\u79d1\u76ee\u8fdb\u884c\u91cd\u5206\u7c7b\u65f6\u4f7f\u7528\u7684\u65f6\u95f4\uff0c\u65e5\u671f\u578b\u683c\u5f0f\uff0c\u4f8b\u59822023-1-1");
                exampleContent1.add("2023-1-1");
                exampleContent2.add("2023-1-1");
            }
        }
        remarkContent.add("\u89c4\u5219\u8bf4\u660e\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        exampleContent1.add("\u793a\u4f8b\u884c\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        exampleContent2.add("\u793a\u4f8b\u884c\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        remarkContentList.add(remarkContent);
        remarkContentList.add(exampleContent1);
        remarkContentList.add(exampleContent2);
        return remarkContentList;
    }

    private List<List<Object>> getExpData(List<AdjustVoucherVO> vouchers, List<DimensionVO> dimensionVOList, AdjustVchrSysOptionVO optionVO) {
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        baseDataCondi.setStopflag(Integer.valueOf(-1));
        baseDataCondi.setTableName("MD_CURRENCY");
        Map<String, String> currMap = this.baseDataClient.list(baseDataCondi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        baseDataCondi.setTableName("MD_ADJUSTTYPE");
        Map<String, String> typeMap = this.baseDataClient.list(baseDataCondi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        baseDataCondi.setTableName("MD_ADJUSTPERIOD");
        Map<String, String> periodMap = this.baseDataClient.list(baseDataCondi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        baseDataCondi.setTableName("MD_CFITEM");
        Map<String, String> cfitemMap = this.baseDataClient.list(baseDataCondi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        baseDataCondi.setTableName("MD_ACCTSUBJECT");
        Map<String, String> subjectMap = this.baseDataClient.list(baseDataCondi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        ArrayList<List<Object>> expDatas = new ArrayList<List<Object>>();
        List extraFields = optionVO.getExtraFields();
        boolean enableOrgnCurr = optionVO.isEnableOrgnCurr();
        boolean remarkEnable = optionVO.isRemarkEnable();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int voucherCount = 1;
        for (AdjustVoucherVO adjustVoucherVO : vouchers) {
            int itemCount = 1;
            for (AdjustVchrItemVO itemVO : adjustVoucherVO.getItems()) {
                ArrayList<Object> lineData = new ArrayList<Object>();
                lineData.add(voucherCount);
                lineData.add(itemVO.getUnitCode() + "|" + itemVO.getUnitName());
                lineData.add(itemVO.getVchrNum());
                lineData.add(adjustVoucherVO.getAdjustTypeCode() + "|" + typeMap.get(adjustVoucherVO.getAdjustTypeCode()));
                lineData.add(periodMap.get(adjustVoucherVO.getAffectPeriodStart()));
                lineData.add(periodMap.get(adjustVoucherVO.getAffectPeriodEnd()));
                lineData.add(itemCount);
                ++itemCount;
                lineData.add(itemVO.getSubjectCode() + "|" + subjectMap.get(itemVO.getSubjectCode()));
                lineData.add(itemVO.getDigest());
                if (remarkEnable) {
                    lineData.add(itemVO.getRemark());
                }
                if (enableOrgnCurr) {
                    lineData.add(itemVO.getCurrencyCode() + "|" + currMap.get(itemVO.getCurrencyCode()));
                    lineData.add(itemVO.getExchrate() != null ? itemVO.getExchrate() : BigDecimal.ZERO);
                    lineData.add(itemVO.getOrgnD() != null ? itemVO.getOrgnD() : BigDecimal.ZERO);
                    lineData.add(itemVO.getOrgnC() != null ? itemVO.getOrgnC() : BigDecimal.ZERO);
                }
                lineData.add(itemVO.getDebit() != null ? itemVO.getDebit() : BigDecimal.ZERO);
                lineData.add(itemVO.getCredit() != null ? itemVO.getCredit() : BigDecimal.ZERO);
                if (!CollectionUtils.isEmpty((Collection)extraFields)) {
                    extraFields.forEach(item -> {
                        if ("BIZDATE".equals(item)) {
                            lineData.add(itemVO.getBizDate() != null ? dateFormat.format(itemVO.getBizDate()) : itemVO.getBizDate());
                        }
                        if ("CFITEMCODE".equals(item)) {
                            lineData.add(StringUtils.isNull((String)itemVO.getCfItemCode()) ? "" : itemVO.getCfItemCode() + "|" + (String)cfitemMap.get(itemVO.getCfItemCode()));
                        }
                        if ("EXPIREDATE".equals(item)) {
                            lineData.add(itemVO.getExpireDate() != null ? dateFormat.format(itemVO.getExpireDate()) : itemVO.getExpireDate());
                        }
                    });
                }
                lineData.addAll(this.getAssistData(itemVO.getAssistDatas(), dimensionVOList));
                expDatas.add(lineData);
            }
            ++voucherCount;
        }
        return expDatas;
    }

    private List<Object> getAssistData(Map<String, Object> assistDatas, List<DimensionVO> dimensionVOList) {
        ArrayList<Object> lineData = new ArrayList<Object>();
        for (DimensionVO dimensionVO : dimensionVOList) {
            if (assistDatas.get(dimensionVO.getCode()) == null) {
                lineData.add("");
                continue;
            }
            if (dimensionVO.getFieldType() == 2 && !StringUtils.isEmpty((String)dimensionVO.getReferField())) {
                ObjectMapper objectMapper = new ObjectMapper();
                SelectOptionVO data = (SelectOptionVO)objectMapper.convertValue(assistDatas.get(dimensionVO.getCode()), SelectOptionVO.class);
                lineData.add(data.getCode() + "|" + data.getName());
                continue;
            }
            lineData.add(assistDatas.get(dimensionVO.getCode()));
        }
        return lineData;
    }

    private List<Object> getConvertData(Map<String, BigDecimal> convertMap) {
        ArrayList<Object> lineData = new ArrayList<Object>();
        for (Map.Entry<String, String> entry : convertCurrencyMap.entrySet()) {
            lineData.add(convertMap.get(entry.getKey() + "DEBIT") == null ? BigDecimal.ZERO : convertMap.get(entry.getKey() + "DEBIT"));
            lineData.add(convertMap.get(entry.getKey() + "CREDIT") == null ? BigDecimal.ZERO : convertMap.get(entry.getKey() + "CREDIT"));
        }
        return lineData;
    }

    static {
        convertCurrencyMap.put("CNY", "\u4eba\u6c11\u5e01");
        convertCurrencyMap.put("HKD", "\u6e2f\u5e01");
        convertCurrencyMap.put("USD", "\u7f8e\u5143");
    }
}

