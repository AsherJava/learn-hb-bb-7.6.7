/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.FetchSchemeApi
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FormulaSchemeConfigCond
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.web;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.FetchSchemeApi;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FormulaSchemeConfigCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.AdjustPeriodSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSchemeLogHelperUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchSchemeController
implements FetchSchemeApi {
    private static final Logger log = LoggerFactory.getLogger(FetchSchemeController.class);
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private IEFDCConfigService efdcConfigServiceImpl;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private RequestCertifyService certifyService;
    @Resource
    private IJtableParamService jtableParamService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private AdjustPeriodSettingService adjustPeriodSettingService;

    public BusinessResponseEntity<List<FetchSchemeVO>> listFetchScheme(String formSchemeId) {
        return BusinessResponseEntity.ok(this.fetchSchemeService.listFetchScheme(formSchemeId));
    }

    public BusinessResponseEntity<FetchSchemeVO> queryFetchSchemeById(String fetchSchemeId) {
        return BusinessResponseEntity.ok((Object)this.fetchSchemeService.getFetchScheme(fetchSchemeId));
    }

    public BusinessResponseEntity<List<FetchSchemeVO>> listFetchSchemeByBizType(String bizType) {
        return BusinessResponseEntity.ok(this.fetchSchemeService.listFetchSchemeByBizType(bizType));
    }

    public BusinessResponseEntity<Object> saveFetchScheme(FetchSchemeVO fetchSchemeVO) {
        if (StringUtils.isEmpty((String)fetchSchemeVO.getId())) {
            fetchSchemeVO.setId(UUIDUtils.newHalfGUIDStr());
        }
        log.debug("\u53d6\u6570\u8bbe\u7f6e-\u4fdd\u5b58{}", (Object)JsonUtils.writeValueAsString((Object)fetchSchemeVO));
        this.fetchSchemeService.saveFetchScheme(fetchSchemeVO);
        this.authorityProvider.grantAllPrivilegesToFormulaScheme(fetchSchemeVO.getId());
        FetchSchemeLogHelperUtil.saveFetchSchemeLogInfo(fetchSchemeVO);
        return BusinessResponseEntity.ok((Object)"\u65b0\u5efa\u6210\u529f");
    }

    public BusinessResponseEntity<Object> copyFetchScheme(FetchSchemeVO fetchSchemeVO) {
        Assert.isNotEmpty((String)fetchSchemeVO.getId(), (String)"\u590d\u5236\u7684\u6e90\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSchemeVO.getName(), (String)"\u590d\u5236\u540e\u7684\u65b9\u6848\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        log.debug("\u590d\u5236BDE\u53d6\u6570\u65b9\u6848{}", (Object)JsonUtils.writeValueAsString((Object)fetchSchemeVO));
        String newFetchSchemeId = this.fetchSchemeService.copyFetchScheme(fetchSchemeVO.getId(), fetchSchemeVO.getName());
        this.authorityProvider.grantAllPrivilegesToFormulaScheme(newFetchSchemeId);
        return BusinessResponseEntity.ok((Object)"\u590d\u5236\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<Object> updateFetchScheme(FetchSchemeVO fetchSchemeVO) {
        int updateCount = this.fetchSchemeService.updateFetchScheme(fetchSchemeVO);
        if (updateCount != 1) {
            return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u5931\u8d25\u3002");
        }
        return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<Object> deleteFetchScheme(FetchSchemeVO fetchSchemeVO) {
        Assert.isNotEmpty((String)fetchSchemeVO.getId(), (String)"\u8981\u5220\u9664\u7684\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        this.fetchSchemeService.deleteFetchScheme(fetchSchemeVO);
        FetchSchemeLogHelperUtil.deleteFetchSchemeLogInfo(fetchSchemeVO);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f\u3002");
    }

    @NRContextBuild
    public BusinessResponseEntity<Object> getSchemeConfigByOrgAndAssistDim(FormulaSchemeConfigCond formulaSchemeConfigCond) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formulaSchemeConfigCond.getSchemeId());
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formulaSchemeConfigCond.getSchemeId(), (Throwable)e);
        }
        if (formSchemeDefine == null) {
            return null;
        }
        formulaSchemeConfigCond.setAssistDim(formulaSchemeConfigCond.getAssistDim() == null ? new HashMap() : formulaSchemeConfigCond.getAssistDim());
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(formSchemeDefine.getTaskKey(), formulaSchemeConfigCond.getSchemeId(), formulaSchemeConfigCond.getOrgId());
        String entityId = FetchTaskUtil.getEntityIdByTaskAndCtx(formSchemeDefine.getTaskKey());
        List dimEntityList = this.jtableParamService.getDimEntityList(formulaSchemeConfigCond.getSchemeId());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            String dimensionValue = (String)formulaSchemeConfigCond.getAssistDim().get(entityInfo.getDimensionName());
            if (dimensionValue == null) {
                dimensionValue = "";
            }
            dimMap.put(entityInfo.getTableName(), dimensionValue);
        }
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(formSchemeDefine.getDateTime());
        dimMap.put(periodEntity.getDimensionName(), (String)formulaSchemeConfigCond.getAssistDim().get(periodEntity.getDimensionName()));
        FormulaSchemeDefine formulaSchemeDefine = this.efdcConfigServiceImpl.getSoluctionByDimensions(queryObjectImpl, dimMap, entityId);
        if (formulaSchemeDefine == null || StringUtils.isEmpty((String)formulaSchemeDefine.getKey())) {
            return BusinessResponseEntity.ok();
        }
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(formulaSchemeDefine.getKey());
        if (null == fetchScheme) {
            return BusinessResponseEntity.ok();
        }
        String[] times = FetchTaskUtil.parseDataTime(formulaSchemeConfigCond.getPeriodStr());
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("id", fetchScheme.getId());
        result.put("beginDate", times[0]);
        result.put("endDate", times[1]);
        String address = "";
        if (!BdeCommonUtil.isStandaloneServer() && StringUtils.isEmpty((String)(address = this.certifyService.getNvwaUrl()))) {
            throw new BdeRuntimeException("\u8bf7\u914d\u7f6eBDE\u548c\u62a5\u8868\u5730\u5740\u3002");
        }
        result.put("bdeUrl", address);
        return BusinessResponseEntity.ok(result);
    }

    public BusinessResponseEntity<Boolean> canEditFetchScheme(String taskId) {
        return BusinessResponseEntity.ok((Object)this.fetchSchemeService.canEditFetchScheme(taskId));
    }

    public BusinessResponseEntity<String> saveAdjustPeriodSetting(List<AdjustPeriodSettingVO> adjustPeriodSettingVOS, String fetchSchemeId) {
        Assert.isNotEmpty((String)fetchSchemeId, (String)"\u53d6\u6570\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        this.adjustPeriodSettingService.save(adjustPeriodSettingVOS, fetchSchemeId);
        FetchSchemeLogHelperUtil.saveAdjustPeriodSettingLogInfo(adjustPeriodSettingVOS, fetchSchemeId);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    @NRContextBuild
    public BusinessResponseEntity<Boolean> isAdjustFetch(AdjustPeriodFetchDTO adjustPeriodFetchDTO) {
        return BusinessResponseEntity.ok((Object)this.adjustPeriodSettingService.isAdjustFetch(adjustPeriodFetchDTO));
    }

    public BusinessResponseEntity<Object> exchangeOrdinal(@RequestParam(value="srcId") String srcId, @RequestParam(value="targetId") String targetId) {
        return BusinessResponseEntity.ok((Object)this.fetchSchemeService.exchangeOrdinal(srcId, targetId));
    }

    public BusinessResponseEntity<String> updateIncludeAdjustVchrBySchemeId(String fetchSchemeId, Integer includeAdjustVchr) {
        Assert.isNotEmpty((String)fetchSchemeId, (String)"\u53d6\u6570\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        this.fetchSchemeService.updateIncludeAdjustVoucherByFetchSchemeId(fetchSchemeId, includeAdjustVchr);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }
}

