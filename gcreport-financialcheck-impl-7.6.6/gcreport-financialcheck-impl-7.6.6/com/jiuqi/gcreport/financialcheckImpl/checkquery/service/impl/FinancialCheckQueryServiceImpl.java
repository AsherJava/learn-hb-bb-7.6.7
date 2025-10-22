/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.FinancialCheckQueryService;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper.FcQueryHelper;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialCheckQueryServiceImpl
implements FinancialCheckQueryService {
    @Autowired
    private FinancialCheckSchemeService financialCheckSchemeService;

    @Override
    public FinancialCheckQueryInitDataVO initData() {
        Date now = new Date();
        FinancialCheckQueryInitDataVO iniData = new FinancialCheckQueryInitDataVO();
        int acctYear = DateUtils.getDateFieldValue((Date)now, (int)1);
        int acctPeriod = DateUtils.getDateFieldValue((Date)now, (int)2);
        iniData.setAcctYear(Integer.valueOf(acctYear));
        iniData.setAcctPeriod(Integer.valueOf(acctPeriod));
        iniData.setOrgVer(DateUtils.format((Date)now, (String)"yyyyMMdd"));
        iniData.setOrgType(FinancialCheckConfigUtils.getCheckOrgType());
        this.initDefaultNode(iniData, acctYear, acctPeriod);
        return iniData;
    }

    @Override
    public FinancialCheckQueryInitDataVO queryDefaultNode(FinancialCheckQueryVO financialCheckQueryVO) {
        FinancialCheckQueryInitDataVO iniData = new FinancialCheckQueryInitDataVO();
        this.initDefaultNode(iniData, financialCheckQueryVO.getAcctYear(), financialCheckQueryVO.getAcctPeriod());
        return iniData;
    }

    private void initDefaultNode(FinancialCheckQueryInitDataVO iniData, int year, int period) {
        List orgTree = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(year, PeriodUtils.standardPeriod((int)period))).getOrgTree();
        if (!CollectionUtils.isEmpty((Collection)orgTree)) {
            GcOrgCacheVO gcOrgCacheVO = (GcOrgCacheVO)orgTree.get(0);
            HashMap<String, String> mergeNode = new HashMap<String, String>();
            mergeNode.put("code", gcOrgCacheVO.getCode());
            mergeNode.put("title", gcOrgCacheVO.getTitle());
            iniData.setMergeNode(mergeNode);
        }
        FinancialCheckGroupVO financialCheckGroupVO = new FinancialCheckGroupVO();
        financialCheckGroupVO.setAcctYear(Integer.valueOf(year));
        List<FinancialCheckSchemeBaseDataVO> financialCheckSchemeBaseDataVOS = this.financialCheckSchemeService.treeEnableScheme(financialCheckGroupVO);
        if (!CollectionUtils.isEmpty(financialCheckSchemeBaseDataVOS)) {
            block0: for (FinancialCheckSchemeBaseDataVO financialCheckSchemeBaseDataVO : financialCheckSchemeBaseDataVOS) {
                List children = financialCheckSchemeBaseDataVO.getChildren();
                if (CollectionUtils.isEmpty((Collection)children)) continue;
                for (FinancialCheckSchemeBaseDataVO child : children) {
                    FinancialCheckSchemeVO schemeVO;
                    List bilateralSubSettings;
                    Object checkMode = child.getData().get("checkMode");
                    if (!Objects.nonNull(checkMode)) continue;
                    HashMap<String, String> schemeNode = new HashMap<String, String>();
                    schemeNode.put("code", ((FinancialCheckSchemeBaseDataVO)children.get(0)).getCode());
                    schemeNode.put("title", ((FinancialCheckSchemeBaseDataVO)children.get(0)).getTitle());
                    schemeNode.put("checkMode", checkMode.toString());
                    if (CheckModeEnum.OFFSETVCHR.getCode().equals(checkMode) && !CollectionUtils.isEmpty((Collection)(bilateralSubSettings = (schemeVO = this.financialCheckSchemeService.queryCheckScheme(child.getKey())).getBilateralSubSettings()))) {
                        iniData.setGroupItem(((FinancialCheckBilateralSubSettingDTO)bilateralSubSettings.get(0)).getGroupItem());
                    }
                    iniData.setSchemeNode(schemeNode);
                    break block0;
                }
            }
        }
    }

    @Override
    public List<FinancialCheckQueryColumnVO> queryColumns(FinancialCheckQueryVO financialCheckQueryVO) {
        String checkSchemeId = financialCheckQueryVO.getSchemeId();
        if (StringUtils.isEmpty((String)checkSchemeId)) {
            throw new BusinessRuntimeException("\u672a\u9009\u62e9\u5bf9\u8d26\u65b9\u6848\u3002");
        }
        FinancialCheckSchemeEO financialCheckSchemeEO = this.financialCheckSchemeService.queryById(checkSchemeId);
        if (Objects.isNull(financialCheckSchemeEO)) {
            throw new BusinessRuntimeException("\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u518d\u8bd5\u3002");
        }
        String checkMode = financialCheckSchemeEO.getCheckMode();
        if (StringUtils.isEmpty((String)checkMode)) {
            throw new BusinessRuntimeException("\u65b9\u6848\u672a\u8bbe\u7f6e\u5bf9\u8d26\u6a21\u5f0f\u3002");
        }
        FcQueryHelper fcQueryHelper = FcQueryHelper.newInstance(checkMode);
        return fcQueryHelper.queryColumns(financialCheckQueryVO);
    }

    @Override
    public PageInfo<FinancialCheckQueryDataVO> queryData(FinancialCheckQueryVO financialCheckQueryVO) {
        if (StringUtils.isEmpty((String)financialCheckQueryVO.getLocalUnit())) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u672c\u65b9\u5355\u4f4d");
        }
        String checkSchemeId = financialCheckQueryVO.getSchemeId();
        if (StringUtils.isEmpty((String)checkSchemeId)) {
            throw new BusinessRuntimeException("\u672a\u9009\u62e9\u5bf9\u8d26\u65b9\u6848\u3002");
        }
        FinancialCheckSchemeEO financialCheckSchemeEO = this.financialCheckSchemeService.queryById(checkSchemeId);
        if (Objects.isNull(financialCheckSchemeEO)) {
            throw new BusinessRuntimeException("\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u518d\u8bd5\u3002");
        }
        if (StringUtils.isEmpty((String)financialCheckSchemeEO.getCheckMode())) {
            throw new BusinessRuntimeException("\u65b9\u6848\u672a\u8bbe\u7f6e\u5bf9\u8d26\u6a21\u5f0f\u3002");
        }
        FcQueryHelper fcQueryHelper = FcQueryHelper.newInstance(financialCheckSchemeEO.getCheckMode());
        return fcQueryHelper.queryCheckQueryData(financialCheckQueryVO);
    }
}

