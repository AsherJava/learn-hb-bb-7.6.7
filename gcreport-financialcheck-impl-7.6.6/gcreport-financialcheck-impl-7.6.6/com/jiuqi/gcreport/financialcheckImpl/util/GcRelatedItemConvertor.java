/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckapi.common.vo.DimBaseDataVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.financialcheckImpl.util;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.common.vo.DimBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class GcRelatedItemConvertor {
    @Autowired
    DimensionService dimensionService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    private FinancialCheckSchemeService schemeService;

    public List<GcRelatedItemVO> converE2V(List<GcRelatedItemEO> items) {
        ArrayList<GcRelatedItemVO> itemVos = new ArrayList<GcRelatedItemVO>();
        if (CollectionUtils.isEmpty(items)) {
            return itemVos;
        }
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        HashMap cacheMap = new HashMap();
        items.forEach(entity -> {
            GcOrgCenterService orgCenter;
            String orgV = String.format("%04d", entity.getAcctYear()) + String.format("%02d", entity.getAcctPeriod() == 0 ? 1 : entity.getAcctPeriod()) + "15";
            if (!cacheMap.containsKey(orgV)) {
                GcOrgCenterService tool;
                YearPeriodObject yp = new YearPeriodObject(null, orgV);
                orgCenter = tool = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                cacheMap.put(orgV, orgCenter);
            } else {
                orgCenter = (GcOrgCenterService)cacheMap.get(orgV);
            }
            GcRelatedItemVO item = this.converE2V((GcRelatedItemEO)entity, orgCenter, cacheMap);
            itemVos.add(item);
        });
        return itemVos;
    }

    public GcRelatedItemVO converE2V(GcRelatedItemEO voucherItemE, GcOrgCenterService orgCenter, Map<String, Object> cacheMap) {
        String vchrSourceTypeCode;
        String checkRuleId;
        List dimensionEOs;
        GcBaseData origCurr;
        GcBaseData subject;
        GcRelatedItemVO voucherItemV = new GcRelatedItemVO();
        BeanUtils.copyProperties(voucherItemE, voucherItemV);
        GcOrgCacheVO unit = orgCenter.getOrgByCode(voucherItemV.getUnitId());
        voucherItemV.setUnitVo(unit);
        voucherItemV.setUnitName(unit == null ? "" : unit.getTitle());
        GcOrgCacheVO oppUnit = orgCenter.getOrgByCode(voucherItemV.getOppUnitId());
        voucherItemV.setOppUnitVo(oppUnit);
        voucherItemV.setOppUnitName(oppUnit == null ? "" : oppUnit.getTitle());
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        if (!cacheMap.containsKey(voucherItemV.getSubjectCode())) {
            subject = baseDataCenterTool.queryBasedataByCode("MD_ACCTSUBJECT", voucherItemV.getSubjectCode());
            cacheMap.put(voucherItemE.getSubjectCode(), subject);
        } else {
            subject = (GcBaseData)cacheMap.get(voucherItemV.getSubjectCode());
        }
        voucherItemV.setSubjectVo(baseDataCenterTool.convertGcBaseDataVO(subject));
        if (subject != null) {
            voucherItemV.setSubjectName(subject.getTitle());
        }
        if (!cacheMap.containsKey(voucherItemE.getOriginalCurr())) {
            origCurr = baseDataCenterTool.queryBasedataByCode("MD_CURRENCY", voucherItemE.getOriginalCurr());
            cacheMap.put(voucherItemE.getOriginalCurr(), origCurr);
        } else {
            origCurr = (GcBaseData)cacheMap.get(voucherItemE.getOriginalCurr());
        }
        if (origCurr != null) {
            voucherItemV.setOriginalCurrName(origCurr.getTitle());
        }
        if (Objects.nonNull(voucherItemE.getChkCurr())) {
            GcBaseData checkCurr;
            if (!cacheMap.containsKey(voucherItemE.getChkCurr())) {
                checkCurr = baseDataCenterTool.queryBasedataByCode("MD_CURRENCY", voucherItemE.getChkCurr());
                cacheMap.put(voucherItemE.getChkCurr(), checkCurr);
            } else {
                checkCurr = (GcBaseData)cacheMap.get(voucherItemE.getChkCurr());
            }
            if (checkCurr != null) {
                voucherItemV.setChkCurrName(checkCurr.getTitle());
            }
        }
        if (Objects.nonNull(voucherItemE.getCurrency())) {
            GcBaseData functionalCurr;
            String currency = voucherItemE.getCurrency();
            if (!cacheMap.containsKey(currency)) {
                functionalCurr = baseDataCenterTool.queryBasedataByCode("MD_CURRENCY", currency);
                cacheMap.put(currency, functionalCurr);
            } else {
                functionalCurr = (GcBaseData)cacheMap.get(currency);
            }
            if (functionalCurr != null) {
                voucherItemV.setCurrencyName(functionalCurr.getTitle());
            }
        }
        if (voucherItemV.getCreateDate() != null) {
            voucherItemV.setCreateDateStr(DateUtils.format((Date)voucherItemV.getCreateDate()));
        }
        if (!CollectionUtils.isEmpty(dimensionEOs = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM"))) {
            Object DimBaseDataMap = new HashMap();
            HashMap dims = new HashMap();
            dimensionEOs.forEach(dimensionEO -> {
                String dimValueCode;
                String string = dimValueCode = voucherItemE.getFieldValue(dimensionEO.getCode()) == null ? "" : voucherItemE.getFieldValue(dimensionEO.getCode()).toString();
                if (!StringUtils.isEmpty(dimValueCode)) {
                    GcBaseData dimBaseData = null;
                    if (!cacheMap.containsKey(dimValueCode)) {
                        try {
                            if (!StringUtils.isEmpty(dimensionEO.getReferTable())) {
                                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(dimensionEO.getReferTable());
                                dimBaseData = baseDataCenterTool.queryBasedataByCode(tableModelDefine.getName(), dimValueCode);
                                cacheMap.put(dimValueCode, dimBaseData);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        dimBaseData = (GcBaseData)cacheMap.get(dimValueCode);
                    }
                    if (FieldType.FIELD_TYPE_DATE.getValue() == dimensionEO.getFieldType().intValue()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        dimValueCode = sdf.format(voucherItemE.getFieldValue(dimensionEO.getCode()));
                    }
                    DimBaseDataVO dimBaseDataVO = new DimBaseDataVO();
                    dimBaseDataVO.setGcBaseDataVO(baseDataCenterTool.convertGcBaseDataVO(dimBaseData));
                    dimBaseDataVO.setTitle(dimBaseData == null ? dimValueCode : dimBaseData.getTitle());
                    dimBaseDataVO.setCode(dimValueCode);
                    dims.put(dimensionEO.getCode(), dimBaseData == null ? dimValueCode : dimBaseData.getTitle());
                    DimBaseDataMap.put(dimensionEO.getCode(), dimBaseDataVO);
                }
            });
            voucherItemV.setDimensionCode(dims);
            voucherItemV.setDimBaseData(DimBaseDataMap);
        }
        voucherItemV.setChkAmt(voucherItemE.getChkAmtD() == null || voucherItemE.getChkAmtD() == 0.0 ? voucherItemE.getChkAmtC() : voucherItemE.getChkAmtD());
        if (voucherItemV.getChkAmt() == null) {
            voucherItemV.setChkAmt(Double.valueOf(0.0));
        }
        if (StringUtils.hasText(voucherItemV.getVchrSourceType())) {
            voucherItemV.setVchrSourceTypeCode(voucherItemV.getVchrSourceType());
            switch (voucherItemV.getVchrSourceType()) {
                case "0": {
                    voucherItemV.setVchrSourceType("\u666e\u901a\u51ed\u8bc1");
                    break;
                }
                case "1": {
                    voucherItemV.setVchrSourceType("\u603b\u8d26\u51ed\u8bc1");
                    break;
                }
                case "2": {
                    voucherItemV.setVchrSourceType("\u603b\u8d26\u51b2\u9500\u51ed\u8bc1");
                    break;
                }
                case "3": {
                    voucherItemV.setVchrSourceType("\u6682\u4f30\u51ed\u8bc1");
                    break;
                }
                default: {
                    voucherItemV.setVchrSourceType("");
                }
            }
        }
        if (StringUtils.hasText(checkRuleId = voucherItemV.getCheckRuleId())) {
            FinancialCheckSchemeEO financialCheckSchemeEO = this.schemeService.queryById(checkRuleId);
            voucherItemV.setCheckRuleName(Objects.isNull(financialCheckSchemeEO) ? "" : financialCheckSchemeEO.getSchemeName());
        }
        if (voucherItemE.getFields().containsKey("UNCHECKREASONTYPECODE")) {
            Object unCheckReason = voucherItemE.getFieldValue("UNCHECKREASON");
            voucherItemV.setUnCheckReason(Objects.isNull(unCheckReason) ? "" : String.valueOf(unCheckReason));
            Object unCheckReasonType = voucherItemE.getFieldValue("UNCHECKREASONTYPE");
            voucherItemV.setUnCheckReasonType(Objects.isNull(unCheckReasonType) ? "" : String.valueOf(unCheckReasonType));
            Object unCheckReasonTypeCode = voucherItemE.getFieldValue("UNCHECKREASONTYPECODE");
            voucherItemV.setUnCheckReasonTypeCode(Objects.isNull(unCheckReasonTypeCode) ? (Objects.isNull(unCheckReasonType) ? "" : String.valueOf(unCheckReasonType)) : String.valueOf(unCheckReasonTypeCode));
        }
        if ("SystemDefault".equals(voucherItemE.getGcNumber())) {
            voucherItemV.setGcNumber("");
        }
        if (StringUtils.hasText(vchrSourceTypeCode = voucherItemV.getVchrSourceTypeCode())) {
            GcBaseData vchrSourceType = baseDataCenterTool.queryBasedataByCode("MD_VCHRTYPE", vchrSourceTypeCode);
            voucherItemV.setVchrSourceType(Objects.isNull(vchrSourceType) ? "" : vchrSourceType.getTitle());
        }
        return voucherItemV;
    }

    public GcRelatedItemEO convertVO2EO(GcRelatedItemVO voucherItemVO) {
        GcRelatedItemEO voucherItemPO = new GcRelatedItemEO();
        BeanUtils.copyProperties(voucherItemVO, voucherItemPO);
        if (!CollectionUtils.isEmpty(voucherItemVO.getDimensionCode())) {
            voucherItemPO.resetFields(voucherItemVO.getDimensionCode());
        }
        voucherItemPO.setChkState(voucherItemVO.getChkState() == null ? CheckStateEnum.UNCHECKED.name() : voucherItemVO.getChkState());
        voucherItemPO.setSubjectCode(Objects.nonNull(voucherItemVO.getSubjectVo()) ? voucherItemVO.getSubjectVo().getCode() : (Objects.nonNull(voucherItemVO.getSubjectCode()) ? voucherItemVO.getSubjectCode() : null));
        voucherItemPO.setUnitId(Objects.nonNull(voucherItemVO.getUnitVo()) ? voucherItemVO.getUnitVo().getCode() : (Objects.nonNull(voucherItemVO.getUnitId()) ? voucherItemVO.getUnitId() : null));
        voucherItemPO.setOppUnitId(Objects.nonNull(voucherItemVO.getOppUnitVo()) ? voucherItemVO.getOppUnitVo().getCode() : (Objects.nonNull(voucherItemVO.getOppUnitId()) ? voucherItemVO.getOppUnitId() : null));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        voucherItemPO.setUpdateTime(sdf.format(new Date()));
        return voucherItemPO;
    }
}

