/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryColumnEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dao.FinancialCheckQueryDao;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dto.FinancialCheckQueryDTO;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper.FcQueryHelper;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryColumnEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class FcQueryUnilateralModeHelper
extends FcQueryHelper {
    private FinancialCheckSchemeService financialCheckSchemeService = (FinancialCheckSchemeService)SpringContextUtils.getBean(FinancialCheckSchemeService.class);
    private FinancialCheckProjectDao financialCheckProjectDao = (FinancialCheckProjectDao)SpringContextUtils.getBean(FinancialCheckProjectDao.class);
    private FinancialCheckQueryDao financialCheckQueryDao = (FinancialCheckQueryDao)SpringContextUtils.getBean(FinancialCheckQueryDao.class);

    @Override
    public List<FinancialCheckQueryColumnVO> queryColumns(FinancialCheckQueryVO financialCheckQueryVO) {
        String checkSchemeId = financialCheckQueryVO.getSchemeId();
        List financialCheckProjectEOS = this.financialCheckProjectDao.selectBySchemeId(checkSchemeId);
        Map role2ProjectsMap = financialCheckProjectEOS.stream().collect(Collectors.groupingBy(FinancialCheckProjectEO::getBusinessRole, Collectors.mapping(FinancialCheckProjectEO::getCheckProject, Collectors.toList())));
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CHECK_PROJECT");
        Map<String, String> projectCode2Title = baseData.stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle, (k1, k2) -> k1));
        ArrayList<FinancialCheckQueryColumnVO> financialCheckQueryColumnVOS = new ArrayList<FinancialCheckQueryColumnVO>();
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.CHECK_STATE.getValue(), CheckQueryColumnEnum.CHECK_STATE.getTitle(), "100"));
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.UNIT.getValue(), CheckQueryColumnEnum.UNIT.getTitle(), "150"));
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.OPPUNIT.getValue(), CheckQueryColumnEnum.OPPUNIT.getTitle(), "150"));
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.ORIGINALCURR.getValue(), CheckQueryColumnEnum.ORIGINALCURR.getTitle(), "100"));
        List<String> projectCodes = role2ProjectsMap.get(BusinessRoleEnum.ASSET.getCode());
        if (!CollectionUtils.isEmpty(projectCodes)) {
            FinancialCheckQueryColumnVO singleProjectColumnVO = this.setQueryColumn(CheckQueryColumnEnum.CHECK_PROJECT.getValue(), CheckQueryColumnEnum.CHECK_PROJECT.getTitle(), "100");
            ArrayList<FinancialCheckQueryColumnVO> childrenColumnVO = new ArrayList<FinancialCheckQueryColumnVO>();
            projectCodes.forEach(projectCode -> childrenColumnVO.add(this.setQueryColumn((String)projectCode, (String)projectCode2Title.get(projectCode), "100")));
            childrenColumnVO.add(this.setQueryColumn(CheckQueryColumnEnum.TOTAL.getValue(), CheckQueryColumnEnum.TOTAL.getTitle(), "100"));
            singleProjectColumnVO.setChildren(childrenColumnVO);
            financialCheckQueryColumnVOS.add(singleProjectColumnVO);
        }
        financialCheckQueryColumnVOS.add(this.setQueryColumn(CheckQueryColumnEnum.COUNT.getValue(), CheckQueryColumnEnum.COUNT.getTitle(), "100"));
        return financialCheckQueryColumnVOS;
    }

    @Override
    public PageInfo<FinancialCheckQueryDataVO> queryCheckQueryData(FinancialCheckQueryVO financialCheckQueryVO) {
        ReconciliationModeEnum checkWay = FinancialCheckConfigUtils.getCheckWay();
        String checkOrgType = financialCheckQueryVO.getOrgType();
        List financialCheckProjectEOS = this.financialCheckProjectDao.selectBySchemeId(financialCheckQueryVO.getSchemeId());
        if (CollectionUtils.isEmpty((Collection)financialCheckProjectEOS)) {
            return PageInfo.empty();
        }
        List<String> checkProjectCodes = financialCheckProjectEOS.stream().map(FinancialCheckProjectEO::getCheckProject).collect(Collectors.toList());
        FinancialCheckQueryDTO financialCheckQueryDTO = new FinancialCheckQueryDTO();
        BeanUtils.copyProperties(financialCheckQueryVO, financialCheckQueryDTO);
        PageInfo<Map<String, Object>> data = this.financialCheckQueryDao.querySingleCheck(financialCheckQueryDTO, checkProjectCodes, checkWay, checkOrgType);
        List list = data.getList();
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
            String unitId = item.get(CheckQueryColumnEnum.UNIT.getValue()).toString();
            String oppUnitId = item.get(CheckQueryColumnEnum.OPPUNIT.getValue()).toString();
            GcOrgCacheVO unit = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(financialCheckQueryVO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryVO.getAcctPeriod()))).getOrgByCode(unitId);
            GcOrgCacheVO oppUnit = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(financialCheckQueryVO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryVO.getAcctPeriod()))).getOrgByCode(oppUnitId);
            financialCheckQueryDataVO.put((Object)CheckQueryColumnEnum.UNIT.getValue(), (Object)unit.getTitle());
            financialCheckQueryDataVO.put((Object)CheckQueryColumnEnum.OPPUNIT.getValue(), (Object)oppUnit.getTitle());
            financialCheckQueryDataVO.put((Object)"UNITMAP", this.buildUnitMap(unit));
            financialCheckQueryDataVO.put((Object)"OPPUNITMAP", this.buildUnitMap(oppUnit));
            return financialCheckQueryDataVO;
        }).collect(Collectors.toList());
        return PageInfo.of(financialCheckQueryDataVOS, (int)data.getPageNum(), (int)data.getPageSize(), (int)data.getSize());
    }
}

