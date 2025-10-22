/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryTableDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryColumnEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao.GcRelTxCheckQueryDao;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao.GcRelTxSubjectMappingDao;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.domain.RelTxCheckQueryLevel4ParamDO;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.domain.RelTxCheckQueryTableDataDO;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.entity.GCRelTxSubjectMappingEO;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.service.RelTxCheckQueryService;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryTableDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryColumnEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelTxCheckQueryServiceImpl
implements RelTxCheckQueryService {
    @Autowired
    private FinancialCheckSchemeService financialCheckSchemeService;
    @Autowired
    private GcRelTxSubjectMappingDao gcRelTxSubjectMappingDao;
    @Autowired
    private GcRelTxCheckQueryDao relTxCheckQueryDao;

    @Override
    @OuterTransaction
    public RelTxCheckQueryDataVO queryRelTxCheckData(RelTxCheckQueryParamVO param) {
        RelTxCheckQueryDataVO relTxCheckQueryDataVO = new RelTxCheckQueryDataVO();
        if (Objects.isNull(param.getBusinessRole())) {
            throw new BusinessRuntimeException("\u4e1a\u52a1\u89d2\u8272\u4e3a\u5fc5\u9009\u9879\u3002");
        }
        if (CollectionUtils.isEmpty((Collection)param.getCheckAttribute())) {
            throw new BusinessRuntimeException("\u5bf9\u8d26\u4e1a\u52a1\u4e3a\u5fc5\u9009\u9879");
        }
        relTxCheckQueryDataVO.setColumns(this.getQueryColumn(param));
        relTxCheckQueryDataVO.setData(this.getQueryTableData(param));
        return relTxCheckQueryDataVO;
    }

    @Override
    public RelTxCheckQueryLevel4DataV0 queryLevel4Data(RelTxCheckQueryLevel4Param param) {
        GcBaseData checkAttribute;
        RelTxCheckQueryTableDataVO level3Data = param.getLevel3Data();
        RelTxCheckQueryParamVO queryParam = param.getParam();
        RelTxCheckQueryLevel4ParamDO relTxQueryParam = new RelTxCheckQueryLevel4ParamDO();
        relTxQueryParam.setOriginalCurr((String)level3Data.getOriginalCurrMap().get("code"));
        String assetUnit = (String)level3Data.getAssetUnitMap().get("code");
        String debtUnit = (String)level3Data.getDebtUnitMap().get("code");
        if (BusinessRoleEnum.ASSET.getCode().equals(level3Data.getBusinessRole())) {
            relTxQueryParam.setUnit(assetUnit);
            relTxQueryParam.setOppUnit(debtUnit);
        } else {
            relTxQueryParam.setUnit(debtUnit);
            relTxQueryParam.setOppUnit(assetUnit);
        }
        relTxQueryParam.setCheckProject((String)level3Data.getCheckAttributeMap().get("code"));
        relTxQueryParam.setAcctYear(queryParam.getAcctYear());
        relTxQueryParam.setAcctPeriod(queryParam.getAcctPeriod());
        relTxQueryParam.setOrgType(queryParam.getOrgType());
        RelTxCheckQueryLevel4DataV0 level4Data = this.relTxCheckQueryDao.queryLevel4Data(relTxQueryParam);
        if (Objects.isNull(level4Data.getUnCheckAmt())) {
            level4Data.setUnCheckAmt(Double.valueOf(0.0));
        }
        if (Objects.isNull(level4Data.getCheckAmt())) {
            level4Data.setCheckAmt(Double.valueOf(0.0));
        }
        if (Objects.isNull(checkAttribute = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", (String)queryParam.getCheckAttribute().get(0)))) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u8d26\u5c5e\u6027\u57fa\u7840\u6570\u636e\u3002");
        }
        String assetPrefix = checkAttribute.getFieldVal("BUSINESSROLE1").toString();
        String debtPrefix = checkAttribute.getFieldVal("BUSINESSROLE2").toString();
        level4Data.setBusinessRole(BusinessRoleEnum.ASSET.getCode().equals(level3Data.getBusinessRole()) ? assetPrefix : debtPrefix);
        String assetAmt = level3Data.getAssetAmt().replace(",", "");
        String debtAmt = level3Data.getDebtAmt().replace(",", "");
        double unClbrAmt = NumberUtils.sub((double)NumberUtils.sum((double)NumberUtils.parseDouble((Object)assetAmt), (double)NumberUtils.parseDouble((Object)debtAmt)), (double)NumberUtils.sum((Double)level4Data.getCheckAmt(), (Double)level4Data.getUnCheckAmt()));
        level4Data.setUnClbrAmt(Double.valueOf(unClbrAmt));
        level4Data.setCheckAmtStr(NumberUtils.doubleToString((Double)level4Data.getCheckAmt()));
        level4Data.setUnCheckAmtStr(NumberUtils.doubleToString((Double)level4Data.getUnCheckAmt()));
        level4Data.setUnClbrAmtStr(NumberUtils.doubleToString((Double)level4Data.getUnClbrAmt()));
        return level4Data;
    }

    private PageInfo<RelTxCheckQueryTableDataVO> getQueryTableData(RelTxCheckQueryParamVO param) {
        FinancialCheckSchemeVO checkScheme = this.financialCheckSchemeService.queryCheckScheme(param.getSchemeId());
        List checkAttributes = param.getCheckAttribute();
        GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", (String)checkAttributes.get(0));
        if (Objects.isNull(gcBaseData)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u8d26\u5c5e\u6027\u57fa\u7840\u6570\u636e\u3002");
        }
        if (!CheckModeEnum.OFFSETVCHR.getCode().equals(checkScheme.getCheckMode())) {
            throw new BusinessRuntimeException("\u6682\u4e0d\u652f\u6301\u6309\u975e\u534f\u540c\u5bf9\u8d26\u6a21\u5f0f\u7684\u65b9\u6848\u6c47\u603b\u67e5\u8be2");
        }
        List bilateralSubSettings = checkScheme.getBilateralSubSettings();
        if (bilateralSubSettings == null) {
            return PageInfo.empty();
        }
        ArrayList<GCRelTxSubjectMappingEO> relTxSubjectMappingS = new ArrayList<GCRelTxSubjectMappingEO>();
        for (FinancialCheckBilateralSubSettingDTO setting : bilateralSubSettings) {
            if (!checkAttributes.contains(setting.getGroup())) continue;
            if (!CollectionUtils.isEmpty((Collection)setting.getSubjects())) {
                this.processSubjects(setting.getSubjects(), setting.getGroup(), BusinessRoleEnum.ASSET.getCode(), relTxSubjectMappingS);
            }
            if (CollectionUtils.isEmpty((Collection)setting.getDebtSubjects())) continue;
            this.processSubjects(setting.getDebtSubjects(), setting.getGroup(), BusinessRoleEnum.DEBT.getCode(), relTxSubjectMappingS);
        }
        if (CollectionUtils.isEmpty(relTxSubjectMappingS)) {
            return PageInfo.empty();
        }
        this.gcRelTxSubjectMappingDao.addBatch(relTxSubjectMappingS);
        boolean isCF = "03".equals(gcBaseData.getFieldVal("BUSINESSTYPE").toString());
        List<RelTxCheckQueryTableDataDO> data = this.relTxCheckQueryDao.queryData(param, isCF);
        if (CollectionUtils.isEmpty(data)) {
            return PageInfo.empty();
        }
        data.forEach(item -> {
            if (BusinessRoleEnum.ASSET.getCode().equals(item.getBusinessRole())) {
                item.setAssetUnit(item.getUnitCode());
                item.setDebtUnit(item.getOppUnitCode());
                item.setOrgCom(item.getUnitCode() + ";" + item.getOppUnitCode());
                item.setAssetAmt(item.getAmt());
                item.setDebtAmt(0.0);
            } else {
                item.setAssetUnit(item.getOppUnitCode());
                item.setDebtUnit(item.getUnitCode());
                item.setOrgCom(item.getOppUnitCode() + ";" + item.getUnitCode());
                item.setDebtAmt(item.getAmt());
                item.setAssetAmt(0.0);
            }
        });
        YearPeriodObject yp = new YearPeriodObject(param.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)param.getAcctPeriod()));
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)param.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        if (!BusinessRoleEnum.ALL.getCode().equals(param.getBusinessRole())) {
            List allLocalOrg = tool.listAllOrgByParentIdContainsSelf(param.getLocalUnit());
            List allLocalOrgCodes = allLocalOrg.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
            data = data.stream().filter(item -> {
                if (BusinessRoleEnum.ASSET.getCode().equals(param.getBusinessRole())) {
                    return allLocalOrgCodes.contains(item.getAssetUnit());
                }
                return allLocalOrgCodes.contains(item.getDebtUnit());
            }).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(data)) {
            return PageInfo.empty();
        }
        Map<String, List<RelTxCheckQueryTableDataDO>> groupedData = data.stream().collect(Collectors.groupingBy(item -> item.getAssetUnit() + ";" + item.getDebtUnit() + ";" + item.getOrgnCurrency() + ";" + item.getCheckAttribute()));
        groupedData = groupedData.entrySet().stream().filter(entry -> {
            BigDecimal assetSum = BigDecimal.ZERO;
            BigDecimal debtSum = BigDecimal.ZERO;
            for (RelTxCheckQueryTableDataDO item : (List)entry.getValue()) {
                assetSum = NumberUtils.sum((BigDecimal)assetSum, (double)item.getAssetAmt());
                debtSum = NumberUtils.sum((BigDecimal)debtSum, (double)item.getDebtAmt());
            }
            return BigDecimal.ZERO.compareTo(assetSum) != 0 || BigDecimal.ZERO.compareTo(debtSum) != 0;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        int totalNumber = groupedData.size();
        List<Map.Entry> sortedAndPagedData = groupedData.entrySet().stream().sorted(Map.Entry.comparingByKey()).skip((long)(param.getPageNum() - 1) * (long)param.getPageSize().intValue()).limit(param.getPageSize().intValue()).collect(Collectors.toList());
        Map<String, Map<Integer, List<GcBaseData>>> subjectsMap = this.financialCheckSchemeService.queryOneLevelSubjectsBySchemeAndCHeckAttribute(param.getSchemeId(), checkAttributes);
        ArrayList tableData = new ArrayList();
        sortedAndPagedData.forEach(entry -> tableData.add(this.disposeTableData(param.getBusinessRole(), (List)entry.getValue(), tool, subjectsMap)));
        return PageInfo.of(tableData, (int)totalNumber);
    }

    private RelTxCheckQueryTableDataVO disposeTableData(Integer businessRoleParam, List<RelTxCheckQueryTableDataDO> mxData, GcOrgCenterService tool, Map<String, Map<Integer, List<GcBaseData>>> subjectsMap) {
        Double diff;
        String checkState;
        RelTxCheckQueryTableDataDO firstData = mxData.get(0);
        String checkAttributeCode = firstData.getCheckAttribute();
        GcBaseData checkAttribute = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", checkAttributeCode);
        Map<Integer, List<GcBaseData>> oneLevelSubjectMap = subjectsMap.get(checkAttributeCode);
        if (Objects.isNull(checkAttribute)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u8d26\u5c5e\u6027\u57fa\u7840\u6570\u636e\u3002");
        }
        String assetPrefix = checkAttribute.getFieldVal("BUSINESSROLE1").toString();
        String debtPrefix = checkAttribute.getFieldVal("BUSINESSROLE2").toString();
        GcOrgCacheVO assetUnit = tool.getOrgByCode(firstData.getAssetUnit());
        GcOrgCacheVO debtUnit = tool.getOrgByCode(firstData.getDebtUnit());
        GcOrgCacheVO assetLevel2Org = Objects.nonNull(assetUnit) && assetUnit.getParents().length > 1 ? tool.getOrgByCode(assetUnit.getParents()[1]) : null;
        GcOrgCacheVO debtLevel2Org = Objects.nonNull(debtUnit) && debtUnit.getParents().length > 1 ? tool.getOrgByCode(debtUnit.getParents()[1]) : null;
        GcBaseData currency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", firstData.getOrgnCurrency());
        BigDecimal assetSum = BigDecimal.ZERO;
        BigDecimal debtSum = BigDecimal.ZERO;
        for (RelTxCheckQueryTableDataDO item : mxData) {
            assetSum = NumberUtils.sum((BigDecimal)assetSum, (double)item.getAssetAmt());
            debtSum = NumberUtils.sum((BigDecimal)debtSum, (double)item.getDebtAmt());
        }
        if (assetSum.equals(debtSum)) {
            checkState = "\u5bf9\u8d26\u4e00\u81f4";
            diff = 0.0;
        } else {
            checkState = "\u5bf9\u8d26\u4e0d\u4e00\u81f4";
            diff = assetSum.subtract(debtSum).doubleValue();
        }
        BigDecimal finalAssetSum = assetSum;
        BigDecimal finalDebtSum = debtSum;
        ArrayList<RelTxCheckQueryTableDataVO> level2DataS = new ArrayList<RelTxCheckQueryTableDataVO>();
        Map<Integer, List<RelTxCheckQueryTableDataDO>> tableDataSByOrient = mxData.stream().collect(Collectors.groupingBy(RelTxCheckQueryTableDataDO::getBusinessRole));
        tableDataSByOrient.putIfAbsent(BusinessRoleEnum.ASSET.getCode(), new ArrayList());
        tableDataSByOrient.putIfAbsent(BusinessRoleEnum.DEBT.getCode(), new ArrayList());
        tableDataSByOrient.forEach((businessRole, items) -> {
            RelTxCheckQueryTableDataVO level2Data = new RelTxCheckQueryTableDataVO();
            ArrayList level3Datas = new ArrayList();
            List gcBaseData = (List)oneLevelSubjectMap.get(businessRole);
            Map<String, RelTxCheckQueryTableDataDO> checkProjectMap = items.stream().collect(Collectors.toMap(item -> item.getCheckProject(), item -> item));
            gcBaseData.forEach(project -> {
                String checkProjectCode = project.getCode();
                boolean checkProjectExist = checkProjectMap.containsKey(checkProjectCode);
                RelTxCheckQueryTableDataVO level3Data = new RelTxCheckQueryTableDataVO();
                level3Data.setLevel(Integer.valueOf(3));
                level3Data.setBusinessRole(businessRole);
                level3Data.setCheckState(checkState);
                if (Objects.nonNull(assetUnit)) {
                    level3Data.setAssetUnit(assetUnit.getTitle());
                    level3Data.setAssetUnitMap(this.buildUnitMap(assetUnit));
                    level3Data.setAssetLevel2Unit(Objects.nonNull(assetLevel2Org) ? assetLevel2Org.getTitle() : null);
                }
                if (Objects.nonNull(debtUnit)) {
                    level3Data.setDebtUnit(debtUnit.getTitle());
                    level3Data.setDebtUnitMap(this.buildUnitMap(debtUnit));
                    level3Data.setDebtLevel2Unit(Objects.nonNull(debtLevel2Org) ? debtLevel2Org.getTitle() : null);
                }
                if (Objects.nonNull(currency)) {
                    level3Data.setOriginalCurr(currency.getTitle());
                    HashMap<String, String> currencyMap = new HashMap<String, String>();
                    currencyMap.put("code", currency.getCode());
                    currencyMap.put("title", currency.getTitle());
                    level3Data.setOriginalCurrMap(currencyMap);
                }
                if (checkProjectExist) {
                    RelTxCheckQueryTableDataDO item = (RelTxCheckQueryTableDataDO)checkProjectMap.get(checkProjectCode);
                    level3Data.setAssetAmt(NumberUtils.doubleToString((Double)item.getAssetAmt()));
                    level3Data.setDebtAmt(NumberUtils.doubleToString((Double)item.getDebtAmt()));
                    level3Data.setCheckAttribute(project.getTitle());
                    HashMap<String, String> checkAttributeMap = new HashMap<String, String>();
                    checkAttributeMap.put("code", checkProjectCode);
                    checkAttributeMap.put("title", project.getTitle());
                    level3Data.setCheckAttributeMap(checkAttributeMap);
                    level3Data.setHasChild(true);
                } else {
                    level3Data.setAssetAmt(NumberUtils.doubleToString((double)0.0));
                    level3Data.setDebtAmt(NumberUtils.doubleToString((double)0.0));
                    level3Data.setCheckAttribute(project.getTitle());
                    HashMap<String, String> checkAttributeMap = new HashMap<String, String>();
                    checkAttributeMap.put("code", checkProjectCode);
                    checkAttributeMap.put("title", project.getTitle());
                    level3Data.setCheckAttributeMap(checkAttributeMap);
                    level3Data.setHasChild(false);
                }
                level3Datas.add(level3Data);
            });
            level2Data.setLevel(Integer.valueOf(2));
            level2Data.setChildren(level3Datas);
            level2Data.setOriginalCurr(currency.getTitle());
            level2Data.setCheckState(checkState);
            level2Data.setAssetLevel2Unit(Objects.nonNull(assetLevel2Org) ? assetLevel2Org.getTitle() : null);
            level2Data.setDebtLevel2Unit(Objects.nonNull(debtLevel2Org) ? debtLevel2Org.getTitle() : null);
            level2Data.setAssetUnit(Objects.nonNull(assetUnit) ? assetUnit.getTitle() : null);
            level2Data.setDebtUnit(Objects.nonNull(debtUnit) ? debtUnit.getTitle() : null);
            level2Data.setBusinessRole(businessRole);
            if (BusinessRoleEnum.ASSET.getCode().equals(businessRole)) {
                level2Data.setCheckAttribute(assetPrefix + "\u671f\u672b\u4f59\u989d");
                level2Data.setAssetAmt(NumberUtils.doubleToString((double)finalAssetSum.doubleValue()));
                level2Data.setDebtAmt(null);
            } else {
                level2Data.setCheckAttribute(debtPrefix + "\u671f\u672b\u4f59\u989d");
                level2Data.setAssetAmt(null);
                level2Data.setDebtAmt(NumberUtils.doubleToString((double)finalDebtSum.doubleValue()));
            }
            level2DataS.add(level2Data);
        });
        if (BusinessRoleEnum.DEBT.getCode().equals(businessRoleParam)) {
            level2DataS.sort(Comparator.comparing(RelTxCheckQueryTableDataVO::getBusinessRole));
        } else {
            level2DataS.sort(Comparator.comparing(RelTxCheckQueryTableDataVO::getBusinessRole).reversed());
        }
        RelTxCheckQueryTableDataVO level1Data = new RelTxCheckQueryTableDataVO();
        level1Data.setChildren(level2DataS);
        level1Data.setLevel(Integer.valueOf(1));
        level1Data.setCheckAttribute(checkAttribute.getTitle());
        level1Data.setCheckState(checkState);
        level1Data.setDiffAmt(NumberUtils.doubleToString((Double)diff));
        level1Data.setAssetAmt(NumberUtils.doubleToString((double)finalAssetSum.doubleValue()));
        level1Data.setDebtAmt(NumberUtils.doubleToString((double)finalDebtSum.doubleValue()));
        level1Data.setAssetLevel2Unit(Objects.nonNull(assetLevel2Org) ? assetLevel2Org.getTitle() : null);
        level1Data.setDebtLevel2Unit(Objects.nonNull(debtLevel2Org) ? debtLevel2Org.getTitle() : null);
        level1Data.setAssetUnit(Objects.nonNull(assetUnit) ? assetUnit.getTitle() : null);
        level1Data.setDebtUnit(Objects.nonNull(debtUnit) ? debtUnit.getTitle() : null);
        level1Data.setOriginalCurr(currency.getTitle());
        return level1Data;
    }

    private Map<String, String> buildUnitMap(GcOrgCacheVO unit) {
        HashMap<String, String> unitMap = new HashMap<String, String>();
        unitMap.put("code", unit.getCode());
        unitMap.put("title", unit.getTitle());
        return unitMap;
    }

    private void processSubjects(List<String> subjectCodes, String group, Integer businessRole, List<GCRelTxSubjectMappingEO> result) {
        for (String onLevelSubjectCode : subjectCodes) {
            List childrenSubjects;
            GcBaseData onLevelSubject = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", onLevelSubjectCode);
            if (onLevelSubject == null || CollectionUtils.isEmpty((Collection)(childrenSubjects = BaseDataUtils.getAllChildrenContainSelf((String)"MD_ACCTSUBJECT", (String)onLevelSubject.getCode())))) continue;
            Object orientObj = onLevelSubject.getFieldVal("ORIENT");
            Integer orient = Integer.valueOf(orientObj.toString());
            for (String childrenSubjectCode : childrenSubjects) {
                GCRelTxSubjectMappingEO gcRelTxSubjectMapping = new GCRelTxSubjectMappingEO();
                gcRelTxSubjectMapping.setId(UUIDUtils.newUUIDStr());
                gcRelTxSubjectMapping.setSubjectCode(childrenSubjectCode);
                gcRelTxSubjectMapping.setCheckAttribute(group);
                gcRelTxSubjectMapping.setCheckProject(onLevelSubjectCode);
                gcRelTxSubjectMapping.setCheckProjectDirection(orient);
                gcRelTxSubjectMapping.setBusinessRole(businessRole);
                result.add(gcRelTxSubjectMapping);
            }
        }
    }

    private List<Map<String, Object>> getQueryColumn(RelTxCheckQueryParamVO param) {
        Integer businessRole = param.getBusinessRole();
        GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", (String)param.getCheckAttribute().get(0));
        if (Objects.isNull(gcBaseData)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u8d26\u5c5e\u6027\u57fa\u7840\u6570\u636e\u3002");
        }
        String assetPrefix = gcBaseData.getFieldVal("BUSINESSROLE1").toString();
        String debtPrefix = gcBaseData.getFieldVal("BUSINESSROLE2").toString();
        ArrayList<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
        columns.add(this.newColumn("checkState", CheckQueryColumnEnum.CHECK_STATE.getTitle(), 100));
        if (BusinessRoleEnum.DEBT.getCode().equals(businessRole)) {
            columns.add(this.newColumn("debtUnit", debtPrefix + "\u5355\u4f4d", 250));
            columns.add(this.newColumn("debtLevel2Unit", debtPrefix + "\u6240\u5c5e\u4e8c\u7ea7", 200));
            columns.add(this.newColumn("assetUnit", assetPrefix + "\u5355\u4f4d", 250));
            columns.add(this.newColumn("assetLevel2Unit", assetPrefix + "\u6240\u5c5e\u4e8c\u7ea7", 200));
        } else {
            columns.add(this.newColumn("assetUnit", assetPrefix + "\u5355\u4f4d", 250));
            columns.add(this.newColumn("assetLevel2Unit", assetPrefix + "\u6240\u5c5e\u4e8c\u7ea7", 200));
            columns.add(this.newColumn("debtUnit", debtPrefix + "\u5355\u4f4d", 250));
            columns.add(this.newColumn("debtLevel2Unit", debtPrefix + "\u6240\u5c5e\u4e8c\u7ea7", 200));
        }
        columns.add(this.newColumn("originalCurr", "\u4ea4\u6613\u5e01\u79cd", 100));
        columns.add(this.newColumn("checkAttribute", "\u5bf9\u8d26\u4e1a\u52a1", 200));
        if (BusinessRoleEnum.DEBT.getCode().equals(businessRole)) {
            columns.add(this.newColumn("debtAmt", "\u503a\u52a1\u65b9\u91d1\u989d", 150));
            columns.add(this.newColumn("assetAmt", "\u503a\u6743\u65b9\u91d1\u989d", 150));
        } else {
            columns.add(this.newColumn("assetAmt", "\u503a\u6743\u65b9", 150));
            columns.add(this.newColumn("debtAmt", "\u503a\u52a1\u65b9", 150));
        }
        columns.add(this.newColumn("diffAmt", "\u5dee\u5f02\u91d1\u989d", 150));
        return columns;
    }

    public Map<String, Object> newColumn(String key, String title, Integer width) {
        HashMap<String, Object> column = new HashMap<String, Object>();
        column.put("key", key);
        column.put("title", title);
        column.put("minWidth", width);
        return column;
    }
}

