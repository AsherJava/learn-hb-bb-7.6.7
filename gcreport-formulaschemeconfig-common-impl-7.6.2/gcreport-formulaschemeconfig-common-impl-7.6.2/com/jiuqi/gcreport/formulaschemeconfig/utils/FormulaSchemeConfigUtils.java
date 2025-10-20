/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.va.organization.service.OrgVersionService
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.gcreport.formulaschemeconfig.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class FormulaSchemeConfigUtils {
    private static FormulaSchemeConfigUtils fetchUtil;
    private static Logger logger;
    @Autowired
    private OrgVersionService versionService;
    @Autowired
    OrgDataService orgDataService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        FormulaSchemeConfigUtils.fetchUtil.versionService = this.versionService;
        FormulaSchemeConfigUtils.fetchUtil.orgDataService = this.orgDataService;
    }

    public static boolean enableTaskMultiOrg(String taskKey) {
        List taskOrgList = ((IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class)).listTaskOrgLinkStreamByTask(taskKey).getList();
        return !CollectionUtils.isEmpty((Collection)taskOrgList) && taskOrgList.size() > 1;
    }

    public static String getEntityIdByTaskKeyAndCtx(String taskKey) {
        Assert.isNotEmpty((String)taskKey, (String)"\u4efb\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        TaskDefine taskDefine = ((com.jiuqi.nr.definition.controller.IRunTimeViewController)ApplicationContextRegister.getBean(com.jiuqi.nr.definition.controller.IRunTimeViewController.class)).queryTaskDefine(taskKey);
        Assert.isNotNull((Object)taskDefine, (String)String.format("\u6839\u636e\u4efb\u52a1\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", taskKey), (Object[])new Object[0]);
        if (FormulaSchemeConfigUtils.enableTaskMultiOrg(taskKey)) {
            DsContext dsContext = DsContextHolder.getDsContext();
            if (dsContext == null || StringUtils.isEmpty((String)dsContext.getContextEntityId())) {
                throw new BusinessRuntimeException(String.format("\u5f53\u524d\u62a5\u8868\u4efb\u52a1\u3010%1$s\u3011\u5305\u542b\u591a\u53e3\u5f84\u914d\u7f6e\uff0c\u4e0a\u4e0b\u6587\u53e3\u5f84\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", taskDefine.getTitle()));
            }
            return dsContext.getContextEntityId();
        }
        return taskDefine.getDw();
    }

    public static <T> T parseResponse(BusinessResponseEntity<T> response) {
        if (response == null) {
            throw new BusinessRuntimeException("\u8bf7\u6c42\u7ed3\u679c\u4e3a\u7a7a");
        }
        if (!response.isSuccess()) {
            throw new BusinessRuntimeException(FormulaSchemeConfigUtils.getString(response.getErrorMessage() + FormulaSchemeConfigUtils.getString(response.getErrorDetail())));
        }
        return (T)response.getData();
    }

    private static String getString(String val) {
        if (val == null) {
            return "";
        }
        return val;
    }

    public static List<OrgDO> queryOrgDO(String orgType, String orgCode) {
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        Date orgVer = FormulaSchemeConfigUtils.getOrgVersion(orgType);
        orgVersionDTO.setCategoryname(orgType);
        orgVersionDTO.setValidtime(orgVer);
        OrgVersionDO orgVersion = FormulaSchemeConfigUtils.fetchUtil.versionService.get(orgVersionDTO);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCategoryname(orgType);
        orgDTO.setCode(orgCode);
        orgDTO.setVersionDate(orgVersion.getValidtime());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        orgDTO.setLeafFlag(Boolean.valueOf(true));
        if (FormulaSchemeConfigUtils.fetchUtil.orgDataService.list(orgDTO).getRows().isEmpty()) {
            logger.error("\u6839\u636e\u7ec4\u7ec7\u673a\u6784\u53c2\u6570\uff1a\u7c7b\u578b\u3010{}\u3011\u65f6\u671f\u3010{}\u3011\u4ee3\u7801\u3010{}\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u7ec4\u7ec7\u673a\u6784\uff0c\u8fd4\u56de\u9ed8\u8ba4\u503c", orgType, DateUtils.format((Date)FormulaSchemeConfigUtils.getOrgVersion(orgType), (String)"yyyyMMdd"), orgCode);
            return CollectionUtils.newArrayList();
        }
        return FormulaSchemeConfigUtils.fetchUtil.orgDataService.list(orgDTO).getRows();
    }

    public static Date getOrgVersion(String orgType) {
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(orgType);
        List orgVersionList = FormulaSchemeConfigUtils.fetchUtil.versionService.list(orgVersionDTO).getRows();
        ArrayList<Date> invalidTime = new ArrayList<Date>();
        Date orgVer = null;
        if (!CollectionUtils.isEmpty((Collection)orgVersionList)) {
            for (int i = 0; i < orgVersionList.size(); ++i) {
                invalidTime.add(((OrgVersionDO)orgVersionList.get(i)).getInvalidtime());
            }
        }
        Date invalidTimeMax = (Date)Collections.max(invalidTime);
        for (int j = 0; j < orgVersionList.size(); ++j) {
            if (!((OrgVersionDO)orgVersionList.get(j)).getInvalidtime().equals(invalidTimeMax)) continue;
            orgVer = ((OrgVersionDO)orgVersionList.get(j)).getValidtime();
        }
        return orgVer;
    }

    static {
        logger = LoggerFactory.getLogger(FormulaSchemeConfigUtils.class);
    }
}

