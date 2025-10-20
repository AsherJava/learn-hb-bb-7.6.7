/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.base.common.utils.DataCenterUtil
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 *  com.jiuqi.gcreport.organization.impl.service.GcOrgDataService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.dc.adjustvchr.impl.utils;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.enums.AdjustVoucherColumnEnum;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.adjustvchr.impl.service.AdjustVoucherService;
import com.jiuqi.dc.base.common.utils.DataCenterUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;

public class AdjustVchrImportCheckUtil {
    public static String checkBaseData(int rowIndex, String value, List<Integer> noExistIndex, List<Integer> multiIndex, Set<String> codeSet, Set<String> repeatNameSet, Map<String, String> nameMap) {
        String colData = value;
        if (value.contains("|")) {
            colData = value.substring(0, value.indexOf("|"));
        }
        if (!codeSet.contains(colData) && ObjectUtils.isEmpty(nameMap.get(colData))) {
            noExistIndex.add(rowIndex);
            return "";
        }
        if (repeatNameSet.contains(colData)) {
            multiIndex.add(rowIndex);
            return "";
        }
        if (!ObjectUtils.isEmpty(nameMap.get(colData))) {
            colData = nameMap.get(colData);
        }
        return colData;
    }

    public static List<String> checkDimBaseData(BaseDataClient baseDataClient, String tableName, String colValue) {
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        baseDataCondi.setTableName(tableName);
        String searchValue = colValue;
        if (colValue.contains("|")) {
            searchValue = colValue.substring(0, colValue.indexOf("|"));
        }
        baseDataCondi.setSearchKey(searchValue);
        List searchResult = baseDataClient.list(baseDataCondi).getRows();
        ArrayList<String> result = new ArrayList<String>();
        for (BaseDataDO baseDataDO : searchResult) {
            if (!baseDataDO.getCode().equals(searchValue) && !baseDataDO.getName().equals(searchValue)) continue;
            result.add(baseDataDO.getCode());
        }
        return result;
    }

    public static void getSubjectMap(Map<String, String> subjectNameMap, Set<String> repeatSubjectNameSet, Set<String> subjectCodeSet, Map<String, SubjectDTO> subjectMap) {
        SubjectService subjectService = (SubjectService)SpringContextUtils.getBean(SubjectService.class);
        List subjectDTOList = subjectService.list();
        for (SubjectDTO subjectDTO : subjectDTOList) {
            if (ObjectUtils.isEmpty(subjectNameMap.get(subjectDTO.getName()))) {
                subjectNameMap.put(subjectDTO.getName(), subjectDTO.getCode());
            } else {
                repeatSubjectNameSet.add(subjectDTO.getName());
            }
            subjectCodeSet.add(subjectDTO.getCode());
            subjectMap.put(subjectDTO.getCode(), subjectDTO);
        }
    }

    public static void getOrgMap(String orgVerCode, String orgType, Set<String> unitCodeSet, Set<String> repeatUnitNameSet, Map<String, String> unitNameMap, Map<String, OrgDataDO> orgMap) {
        OrgDataParam orgDataParam = new OrgDataParam();
        orgDataParam.setOrgVerCode(orgVerCode);
        orgDataParam.setOrgType(orgType);
        GcOrgDataService gcOrgDataService = (GcOrgDataService)SpringContextUtils.getBean(GcOrgDataService.class);
        List orgDataDOList = gcOrgDataService.list(orgDataParam);
        for (OrgDataDO orgDataDO : orgDataDOList) {
            if (unitNameMap.get(orgDataDO.getName()) == null) {
                unitNameMap.put(orgDataDO.getName(), orgDataDO.getCode());
            } else {
                repeatUnitNameSet.add(orgDataDO.getName());
            }
            unitCodeSet.add(orgDataDO.getCode());
            orgMap.put(orgDataDO.getCode(), orgDataDO);
        }
    }

    public static void getBaseDataMap(String tableName, Set<String> codeSet, Set<String> repeatNameSet, Map<String, String> adjustTypeNameMap) {
        BaseDataClient baseDataClient = (BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class);
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        baseDataCondi.setTableName(tableName);
        List baseDataDOList = baseDataClient.list(baseDataCondi).getRows();
        for (BaseDataDO baseDataDO : baseDataDOList) {
            if (adjustTypeNameMap.get(baseDataDO.getName()) == null) {
                adjustTypeNameMap.put(baseDataDO.getName(), baseDataDO.getCode());
            } else {
                repeatNameSet.add(baseDataDO.getName());
            }
            codeSet.add(baseDataDO.getCode());
        }
    }

    public static Map<String, List<AdjustVchrItemImportVO>> getSaveData(List<AdjustVchrItemImportVO> importData) {
        HashMap<String, List<AdjustVchrItemImportVO>> saveVoucher = new HashMap<String, List<AdjustVchrItemImportVO>>();
        for (AdjustVchrItemImportVO rowData : importData) {
            String voucherKey = rowData.getUnit() + "|" + rowData.getVchrNum();
            saveVoucher.computeIfAbsent(voucherKey, k -> new ArrayList());
            ((List)saveVoucher.get(voucherKey)).add(rowData);
        }
        return saveVoucher;
    }

    public static String saveAdjustVchrData(AdjustVchrSysOptionVO optionVO, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, LinkedHashMap<String, String> convertCurrencyMap) {
        StringBuilder saveMsg = new StringBuilder();
        INvwaSystemOptionService sysOptionService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        Map<String, List<AdjustVchrItemImportVO>> saveVoucher = AdjustVchrImportCheckUtil.getSaveData(importData);
        DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        List dimensionVOList = dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
        HashMap<String, String> dimNameMap = new HashMap<String, String>();
        HashMap<String, String> dimReferFieldMap = new HashMap<String, String>();
        for (DimensionVO dimensionVO : dimensionVOList) {
            dimNameMap.put(dimensionVO.getTitle(), dimensionVO.getCode());
            if (StringUtils.isNull((String)dimensionVO.getReferField())) continue;
            dimReferFieldMap.put(dimensionVO.getTitle(), dimensionVO.getReferField());
        }
        SubjectService subjectService = (SubjectService)SpringContextUtils.getBean(SubjectService.class);
        Map<String, SubjectDTO> subjectDTOMap = subjectService.list().parallelStream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
        BaseDataClient baseDataClient = (BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class);
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        ArrayList<AdjustVoucherVO> resultList = new ArrayList<AdjustVoucherVO>();
        for (Map.Entry<String, List<AdjustVchrItemImportVO>> voucherItems : saveVoucher.entrySet()) {
            List<AdjustVchrItemImportVO> items = voucherItems.getValue();
            AdjustVoucherVO voucherVO = new AdjustVoucherVO();
            voucherVO.setUnitCode(items.get(0).getUnit());
            voucherVO.setAcctYear(importParam.getAcctYear());
            voucherVO.setVchrNum(items.get(0).getVchrNum());
            voucherVO.setAdjustTypeCode(items.get(0).getAdjustType());
            voucherVO.setAffectPeriodStart(AdjustVchrImportCheckUtil.getPeriod(items.get(0).getStartPeriod(), importParam.getPeriodType()));
            voucherVO.setAffectPeriodEnd(AdjustVchrImportCheckUtil.getPeriod(items.get(0).getEndPeriod(), importParam.getPeriodType()));
            voucherVO.setPeriodType(importParam.getPeriodType());
            voucherVO.setCurrentPeriod(importParam.getAffectPeriodStart());
            BigDecimal dsum = BigDecimal.ZERO;
            BigDecimal csum = BigDecimal.ZERO;
            ArrayList<AdjustVchrItemVO> itemVOList = new ArrayList<AdjustVchrItemVO>();
            HashSet repCurrSet = new HashSet(DataCenterUtil.getRepCurrCode((String)voucherVO.getUnitCode()));
            for (AdjustVchrItemImportVO item2 : items) {
                AdjustVchrItemVO itemVO = new AdjustVchrItemVO();
                itemVO.setUnitCode(item2.getUnit());
                itemVO.setVchrNum(item2.getVchrNum());
                itemVO.setAcctYear(importParam.getAcctYear());
                itemVO.setItemOrder(Integer.valueOf(Integer.parseInt(item2.getItemOrder())));
                String digest = item2.getDigest();
                itemVO.setDigest(StringUtils.isNull((String)digest) ? "" : digest);
                String remark = item2.getRemark();
                itemVO.setRemark(StringUtils.isNull((String)remark) ? "" : remark);
                itemVO.setSubjectCode(item2.getSubject());
                itemVO.setSubjectName(subjectDTOMap.get(itemVO.getSubjectCode()).getName());
                if (optionVO.isEnableOrgnCurr()) {
                    itemVO.setCurrencyCode(StringUtils.isNull((String)item2.getCurrency()) ? DataCenterUtil.getFinCurrCode((String)voucherVO.getUnitCode()) : item2.getCurrency());
                    itemVO.setExchrate(StringUtils.isNull((String)item2.getExchangeRate()) ? BigDecimal.ONE : new BigDecimal(item2.getExchangeRate()));
                    itemVO.setOrgnD(StringUtils.isNull((String)item2.getOrgND()) ? BigDecimal.ZERO : new BigDecimal(item2.getOrgND()));
                    itemVO.setOrgnC(StringUtils.isNull((String)item2.getOrgNC()) ? BigDecimal.ZERO : new BigDecimal(item2.getOrgNC()));
                } else {
                    itemVO.setCurrencyCode(DataCenterUtil.getFinCurrCode((String)voucherVO.getUnitCode()));
                    itemVO.setExchrate(BigDecimal.ONE);
                    itemVO.setOrgnD(StringUtils.isNull((String)item2.getDebit()) ? BigDecimal.ZERO : new BigDecimal(item2.getDebit()));
                    itemVO.setOrgnC(StringUtils.isNull((String)item2.getCredit()) ? BigDecimal.ZERO : new BigDecimal(item2.getCredit()));
                }
                BigDecimal debit = StringUtils.isNull((String)item2.getDebit()) ? BigDecimal.ZERO : new BigDecimal(item2.getDebit());
                itemVO.setDebit(debit);
                BigDecimal credit = StringUtils.isNull((String)item2.getCredit()) ? BigDecimal.ZERO : new BigDecimal(item2.getCredit());
                itemVO.setCredit(credit);
                dsum = dsum.add(debit);
                csum = csum.add(credit);
                HashSet extraFieldSet = new HashSet(optionVO.getExtraFields());
                if (extraFieldSet.contains("BIZDATE") && !ObjectUtils.isEmpty(item2.getBizDate())) {
                    item2.setBizDate(item2.getBizDate().replace("/", "-"));
                    itemVO.setBizDate(StringUtils.isNull((String)item2.getBizDate()) ? null : DateUtils.parse((String)item2.getBizDate()));
                }
                if (extraFieldSet.contains("EXPIREDATE") && !ObjectUtils.isEmpty(item2.getExpireDate())) {
                    item2.setExpireDate(item2.getExpireDate().replace("/", "-"));
                    itemVO.setExpireDate(StringUtils.isNull((String)item2.getExpireDate()) ? null : DateUtils.parse((String)item2.getExpireDate()));
                }
                if (extraFieldSet.contains("CFITEMCODE") && !StringUtils.isNull((String)item2.getCfitemCode())) {
                    itemVO.setCfItemCode(StringUtils.isNull((String)item2.getCfitemCode()) ? "" : String.valueOf(item2.getCfitemCode()));
                }
                HashMap<String, Object> assistDatas = new HashMap<String, Object>();
                for (Map.Entry<String, String> colMap : item2.getAssistMap().entrySet()) {
                    if (StringUtils.isNull((String)String.valueOf(colMap.getValue()))) continue;
                    Map assTypeMap = subjectDTOMap.get(itemVO.getSubjectCode()).getAssTypeMap();
                    String dimCode = (String)dimNameMap.get(colMap.getKey());
                    String tableName = (String)dimReferFieldMap.get(colMap.getKey());
                    if (ObjectUtils.isEmpty(assTypeMap.get(dimCode))) continue;
                    if (!StringUtils.isNull((String)tableName)) {
                        baseDataCondi.setTableName(tableName);
                        baseDataCondi.setCode(String.valueOf(colMap.getValue()));
                        BaseDataDO result = (BaseDataDO)baseDataClient.list(baseDataCondi).getRows().get(0);
                        assistDatas.put(dimCode, result);
                        continue;
                    }
                    assistDatas.put(dimCode, String.valueOf(colMap.getValue()));
                }
                itemVO.setAssistDatas(assistDatas);
                itemVOList.add(itemVO);
            }
            if (!NumberUtils.compareDouble((double)NumberUtils.parseDouble((Object)csum), (double)NumberUtils.parseDouble((Object)dsum))) {
                saveMsg.append("\u7ec4\u7ec7\u673a\u6784\u3010").append(voucherVO.getUnitCode()).append("\u3011\u51ed\u8bc1\u53f7\u3010").append(voucherVO.getVchrNum()).append("\u3011\u501f\u8d37\u65b9\u4e0d\u5e73\uff0c\u8bf7\u786e\u8ba4");
            }
            voucherVO.setItems(itemVOList);
            resultList.add(voucherVO);
        }
        if (saveMsg.length() > 0) {
            return saveMsg.toString();
        }
        AdjustVoucherService adjustVoucherService = (AdjustVoucherService)SpringContextUtils.getBean(AdjustVoucherService.class);
        AdjustVoucherSaveDTO saveDTO = new AdjustVoucherSaveDTO();
        saveDTO.setVouchers(resultList);
        adjustVoucherService.save(saveDTO);
        return String.format("\u5bfc\u5165\u6210\u529f\uff0c\u5171\u5bfc\u5165%1$s\u7ec4\u8c03\u6574\u51ed\u8bc1\u3002", saveVoucher.size());
    }

    private static String getPeriod(String colValue, String periodType) {
        String affectPeriod = "";
        affectPeriod = !colValue.contains("\u534a\u5e74") ? colValue.replaceAll("[\\u4e00-\\u9fa5]", "") : (colValue.equals("\u4e0a\u534a\u5e74") ? "1" : "2");
        return affectPeriod + periodType;
    }

    public static List<AdjustVchrItemImportVO> getImportData(List<String> importColumns, List<Object[]> importData) {
        ArrayList<AdjustVchrItemImportVO> adjustVchrList = new ArrayList<AdjustVchrItemImportVO>();
        for (Object[] importDatum : importData) {
            AdjustVchrItemImportVO adjustVchrItem = new AdjustVchrItemImportVO();
            HashMap<String, String> assistMap = new HashMap<String, String>();
            block28: for (int j = 0; j < importColumns.size(); ++j) {
                if (importColumns.get(j).equals("\u5e8f\u53f7")) continue;
                String columnName = importColumns.get(j);
                String columnValue = columnName.contains("\u91d1\u989d") ? String.valueOf(importDatum[j]).replace(",", "") : String.valueOf(importDatum[j]);
                String amountValue = columnName.contains("\u91d1\u989d") && !StringUtils.isNull((String)columnValue) && BigDecimal.ZERO.compareTo(new BigDecimal(columnValue)) != 0 ? columnValue : "";
                switch (AdjustVoucherColumnEnum.getColumnByName(columnName)) {
                    case UNITCODE: {
                        adjustVchrItem.setUnit(columnValue);
                        continue block28;
                    }
                    case VCHRNUM: {
                        adjustVchrItem.setVchrNum(columnValue);
                        continue block28;
                    }
                    case ADJUSTTYPE: {
                        adjustVchrItem.setAdjustType(columnValue);
                        continue block28;
                    }
                    case STARTPERIOD: {
                        adjustVchrItem.setStartPeriod(columnValue);
                        continue block28;
                    }
                    case ENDPERIOD: {
                        adjustVchrItem.setEndPeriod(columnValue);
                        continue block28;
                    }
                    case ITEMORDER: {
                        adjustVchrItem.setItemOrder(columnValue);
                        continue block28;
                    }
                    case SUBJECT: {
                        adjustVchrItem.setSubject(columnValue);
                        continue block28;
                    }
                    case DIGEST: {
                        adjustVchrItem.setDigest(columnValue);
                        continue block28;
                    }
                    case REMARK: {
                        adjustVchrItem.setRemark(columnValue);
                        continue block28;
                    }
                    case CURRENCYCODE: {
                        adjustVchrItem.setCurrency(columnValue);
                        continue block28;
                    }
                    case EXCHANGERATE: {
                        adjustVchrItem.setExchangeRate(amountValue);
                        continue block28;
                    }
                    case ORGND: {
                        adjustVchrItem.setOrgND(amountValue);
                        continue block28;
                    }
                    case ORGNC: {
                        adjustVchrItem.setOrgNC(amountValue);
                        continue block28;
                    }
                    case DEBIT: {
                        adjustVchrItem.setDebit(amountValue);
                        continue block28;
                    }
                    case CREDIT: {
                        adjustVchrItem.setCredit(amountValue);
                        continue block28;
                    }
                    default: {
                        switch (columnName) {
                            case "\u4e1a\u52a1\u65e5\u671f": {
                                adjustVchrItem.setBizDate(columnValue);
                                continue block28;
                            }
                            case "\u5230\u671f\u65e5": {
                                adjustVchrItem.setExpireDate(columnValue);
                                continue block28;
                            }
                            case "\u73b0\u91d1\u6d41\u91cf\u5c5e\u6027": {
                                adjustVchrItem.setCfitemCode(columnValue);
                                continue block28;
                            }
                        }
                        assistMap.put(columnName, columnValue);
                    }
                }
            }
            adjustVchrItem.setAssistMap(assistMap);
            adjustVchrList.add(adjustVchrItem);
        }
        return adjustVchrList;
    }
}

