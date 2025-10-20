/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryColumnEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dao.FinancialCheckQueryDao;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dto.FinancialCheckQueryDTO;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper.FcQueryHelper;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryColumnEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class FcQueryOffsetVchrModeHelper
extends FcQueryHelper {
    private FinancialCheckSchemeService financialCheckSchemeService = (FinancialCheckSchemeService)SpringContextUtils.getBean(FinancialCheckSchemeService.class);
    private FinancialCheckQueryDao financialCheckQueryDao = (FinancialCheckQueryDao)SpringContextUtils.getBean(FinancialCheckQueryDao.class);

    @Override
    public List<FinancialCheckQueryColumnVO> queryColumns(FinancialCheckQueryVO financialCheckQueryVO) {
        String checkSchemeId = financialCheckQueryVO.getSchemeId();
        String checkAttribute = financialCheckQueryVO.getCheckAttribute();
        Map<String, Map<Integer, List<GcBaseData>>> aubjectsMap = this.financialCheckSchemeService.queryOneLevelSubjectsBySchemeAndCHeckAttribute(checkSchemeId, Arrays.asList(checkAttribute));
        Map<Integer, List<GcBaseData>> oneLevelSubjectsMap = aubjectsMap.get(checkAttribute);
        ArrayList<FinancialCheckQueryColumnVO> financialCheckQueryColumnVOS = new ArrayList<FinancialCheckQueryColumnVO>();
        Integer businessRole = financialCheckQueryVO.getBusinessRole();
        if (Objects.isNull(businessRole)) {
            throw new BusinessRuntimeException("\u4e1a\u52a1\u89d2\u8272\u4e3a\u5fc5\u9009\u9879\u3002");
        }
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.CHECK_STATE.getValue(), CheckQueryColumnEnum.CHECK_STATE.getTitle(), "100"));
        GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", checkAttribute);
        if (Objects.isNull(gcBaseData)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u8d26\u5c5e\u6027\u57fa\u7840\u6570\u636e\u3002");
        }
        String assetPrefix = gcBaseData.getFieldVal("BUSINESSROLE1").toString();
        String debtPrefix = gcBaseData.getFieldVal("BUSINESSROLE2").toString();
        if (BusinessRoleEnum.DEBT.getCode().equals(businessRole)) {
            financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.DEBT_CHECKUNIT.getValue(), debtPrefix + "\u5bf9\u8d26\u5355\u4f4d", "250"));
            financialCheckQueryColumnVOS.add(this.setQueryColumn("DEBTEVEL2ORG", debtPrefix + "\u4e8c\u7ea7\u5355\u4f4d", "200"));
            financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.ASSET_CHECKUNIT.getValue(), assetPrefix + "\u5bf9\u8d26\u5355\u4f4d", "250"));
            financialCheckQueryColumnVOS.add(this.setQueryColumn("ASSETLEVEL2ORG", assetPrefix + "\u4e8c\u7ea7\u5355\u4f4d", "200"));
        } else {
            financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.ASSET_CHECKUNIT.getValue(), assetPrefix + "\u5bf9\u8d26\u5355\u4f4d", "250"));
            financialCheckQueryColumnVOS.add(this.setQueryColumn("ASSETLEVEL2ORG", assetPrefix + "\u4e8c\u7ea7\u5355\u4f4d", "200"));
            financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.DEBT_CHECKUNIT.getValue(), debtPrefix + "\u5bf9\u8d26\u5355\u4f4d", "250"));
            financialCheckQueryColumnVOS.add(this.setQueryColumn("DEBTEVEL2ORG", debtPrefix + "\u4e8c\u7ea7\u5355\u4f4d", "200"));
        }
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.ORIGINALCURR.getValue(), CheckQueryColumnEnum.ORIGINALCURR.getTitle(), "100"));
        if (BusinessRoleEnum.DEBT.getCode().equals(businessRole)) {
            List<GcBaseData> assetOneLevelSubjects;
            List<GcBaseData> debtOneLevelSubjects = oneLevelSubjectsMap.get(BusinessRoleEnum.DEBT.getCode());
            if (!CollectionUtils.isEmpty(debtOneLevelSubjects)) {
                financialCheckQueryColumnVOS.add(this.buildDebtProjectColumn(debtOneLevelSubjects, debtPrefix));
            }
            if (!CollectionUtils.isEmpty(assetOneLevelSubjects = oneLevelSubjectsMap.get(BusinessRoleEnum.ASSET.getCode()))) {
                financialCheckQueryColumnVOS.add(this.buildAssetProjectColumn(assetOneLevelSubjects, assetPrefix));
            }
        } else {
            List<GcBaseData> debtOneLevelSubjects;
            List<GcBaseData> assetOneLevelSubjects = oneLevelSubjectsMap.get(BusinessRoleEnum.ASSET.getCode());
            if (!CollectionUtils.isEmpty(assetOneLevelSubjects)) {
                financialCheckQueryColumnVOS.add(this.buildAssetProjectColumn(assetOneLevelSubjects, assetPrefix));
            }
            if (!CollectionUtils.isEmpty(debtOneLevelSubjects = oneLevelSubjectsMap.get(BusinessRoleEnum.DEBT.getCode()))) {
                financialCheckQueryColumnVOS.add(this.buildDebtProjectColumn(debtOneLevelSubjects, debtPrefix));
            }
        }
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.DIFF_AMOUNT.getValue(), CheckQueryColumnEnum.DIFF_AMOUNT.getTitle(), "200"));
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.UNCHECKED_COUNT.getValue(), CheckQueryColumnEnum.UNCHECKED_COUNT.getTitle(), "100"));
        return financialCheckQueryColumnVOS;
    }

    @Override
    public PageInfo<FinancialCheckQueryDataVO> queryCheckQueryData(FinancialCheckQueryVO financialCheckQueryVO) {
        String checkOrgType = financialCheckQueryVO.getOrgType();
        String checkAttribute = financialCheckQueryVO.getCheckAttribute();
        Map<String, Map<Integer, List<GcBaseData>>> subjectsMap = this.financialCheckSchemeService.queryOneLevelSubjectsBySchemeAndCHeckAttribute(financialCheckQueryVO.getSchemeId(), Arrays.asList(checkAttribute));
        Map<Integer, List<GcBaseData>> oneLevelSubjectsMap = subjectsMap.get(checkAttribute);
        ArrayList<String> oneLevelSubjectCodes = new ArrayList<String>();
        oneLevelSubjectsMap.values().forEach(v -> oneLevelSubjectCodes.addAll(v.stream().map(GcBaseData::getCode).collect(Collectors.toList())));
        FinancialCheckQueryDTO financialCheckQueryDTO = new FinancialCheckQueryDTO();
        BeanUtils.copyProperties(financialCheckQueryVO, financialCheckQueryDTO);
        BusinessRoleEnum businessRoleEnum = null;
        businessRoleEnum = BusinessRoleEnum.getEnumByCode((Integer)financialCheckQueryVO.getBusinessRole());
        if (Objects.isNull(businessRoleEnum)) {
            throw new BusinessRuntimeException("\u53cc\u8fb9\u5bf9\u8d26\u4e1a\u52a1\u89d2\u8272\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        PageInfo<Map<String, Object>> data = this.financialCheckQueryDao.queryOffsetVoucherModeCheck(financialCheckQueryDTO, oneLevelSubjectCodes, checkOrgType);
        List list = data.getList();
        BusinessRoleEnum finalBusinessRoleEnum = businessRoleEnum;
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(financialCheckQueryVO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryVO.getAcctPeriod())));
        List financialCheckQueryDataVOS = list.stream().map(item -> {
            FinancialCheckQueryDataVO financialCheckQueryDataVO = new FinancialCheckQueryDataVO();
            financialCheckQueryDataVO.putAll(item);
            String currencyId = item.get(CheckQueryColumnEnum.ORIGINALCURR.getValue()).toString();
            GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", currencyId);
            if (Objects.nonNull(baseData)) {
                financialCheckQueryDataVO.put((Object)CheckQueryColumnEnum.ORIGINALCURR.getValue(), (Object)baseData.getTitle());
                HashMap<String, String> currencyMap = new HashMap<String, String>();
                currencyMap.put("code", baseData.getCode());
                currencyMap.put("title", baseData.getTitle());
                financialCheckQueryDataVO.put((Object)"CURRENCYMAP", currencyMap);
            }
            String unitId = item.get(CheckQueryColumnEnum.ASSET_CHECKUNIT.getValue()).toString();
            String oppUnitId = item.get(CheckQueryColumnEnum.DEBT_CHECKUNIT.getValue()).toString();
            GcOrgCacheVO unit = orgTool.getOrgByCode(unitId);
            GcOrgCacheVO oppUnit = orgTool.getOrgByCode(oppUnitId);
            if (Objects.nonNull(unit) && unit.getParents().length > 1) {
                GcOrgCacheVO assetLevel2Org = orgTool.getOrgByCode(unit.getParents()[1]);
                financialCheckQueryDataVO.put((Object)"ASSETLEVEL2ORG", (Object)(Objects.nonNull(assetLevel2Org) ? assetLevel2Org.getTitle() : null));
            }
            if (Objects.nonNull(oppUnit) && oppUnit.getParents().length > 1) {
                GcOrgCacheVO debtLevel2Org = orgTool.getOrgByCode(oppUnit.getParents()[1]);
                financialCheckQueryDataVO.put((Object)"DEBTEVEL2ORG", (Object)(Objects.nonNull(debtLevel2Org) ? debtLevel2Org.getTitle() : null));
            }
            financialCheckQueryDataVO.put((Object)CheckQueryColumnEnum.ASSET_CHECKUNIT.getValue(), (Object)unit.getTitle());
            financialCheckQueryDataVO.put((Object)CheckQueryColumnEnum.DEBT_CHECKUNIT.getValue(), (Object)oppUnit.getTitle());
            if (!BusinessRoleEnum.DEBT.equals((Object)finalBusinessRoleEnum)) {
                financialCheckQueryDataVO.put((Object)"UNITMAP", this.buildUnitMap(unit));
                financialCheckQueryDataVO.put((Object)"OPPUNITMAP", this.buildUnitMap(oppUnit));
            } else {
                financialCheckQueryDataVO.put((Object)"UNITMAP", this.buildUnitMap(oppUnit));
                financialCheckQueryDataVO.put((Object)"OPPUNITMAP", this.buildUnitMap(unit));
            }
            return financialCheckQueryDataVO;
        }).collect(Collectors.toList());
        return PageInfo.of(financialCheckQueryDataVOS, (int)data.getPageNum(), (int)data.getPageSize(), (int)data.getSize());
    }

    private FinancialCheckQueryColumnVO buildAssetProjectColumn(List<GcBaseData> onLevelSubjects, String prefix) {
        FinancialCheckQueryColumnVO assetProjectColumnVO = this.setQueryColumn(CheckQueryColumnEnum.ASSET_CHECK_PROJECT.getValue(), prefix, "400");
        ArrayList<FinancialCheckQueryColumnVO> assetChildrenColumnVO = new ArrayList<FinancialCheckQueryColumnVO>();
        onLevelSubjects.forEach(subject -> assetChildrenColumnVO.add(this.setQueryColumn("ONELEVELSUBJECT" + subject.getCode(), subject.getTitle(), "200")));
        assetChildrenColumnVO.add(this.setQueryColumn(CheckQueryColumnEnum.ASSET_TOTAL.getValue(), prefix + "\u5408\u8ba1", "200"));
        assetProjectColumnVO.setChildren(assetChildrenColumnVO);
        return assetProjectColumnVO;
    }

    private FinancialCheckQueryColumnVO buildDebtProjectColumn(List<GcBaseData> projectCodes, String prefix) {
        FinancialCheckQueryColumnVO debtProjectColumnVO = this.setQueryColumn(CheckQueryColumnEnum.DEBT_CHECK_PROJECT.getValue(), prefix, "400");
        ArrayList<FinancialCheckQueryColumnVO> debtChildrenColumnVO = new ArrayList<FinancialCheckQueryColumnVO>();
        projectCodes.forEach(subject -> debtChildrenColumnVO.add(this.setQueryColumn("ONELEVELSUBJECT" + subject.getCode(), subject.getTitle(), "200")));
        debtChildrenColumnVO.add(this.setQueryColumn(CheckQueryColumnEnum.DEBT_TOTAL.getValue(), prefix + "\u5408\u8ba1", "200"));
        debtProjectColumnVO.setChildren(debtChildrenColumnVO);
        return debtProjectColumnVO;
    }
}

