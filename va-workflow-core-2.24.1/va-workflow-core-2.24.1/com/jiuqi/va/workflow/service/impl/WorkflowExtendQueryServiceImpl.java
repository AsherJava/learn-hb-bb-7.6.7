/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.relparam.domain.relparamcache.VaRelParamCacheDO
 *  com.jiuqi.va.relparam.domain.relparamcache.VaRelParamCacheVO
 *  com.jiuqi.va.relparam.service.VaRelParamCacheHelper
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.relparam.domain.relparamcache.VaRelParamCacheDO;
import com.jiuqi.va.relparam.domain.relparamcache.VaRelParamCacheVO;
import com.jiuqi.va.relparam.service.VaRelParamCacheHelper;
import com.jiuqi.va.workflow.dao.WorkflowExtendQueryDao;
import com.jiuqi.va.workflow.domain.WorkflowExtendQueryDTO;
import com.jiuqi.va.workflow.service.WorkflowExtendQueryService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowExtendQueryServiceImpl
implements WorkflowExtendQueryService {
    public static final String STAFF_TABLE_NAME = "MD_STAFF";
    public static final String SHOW_TYPE = "CODE&NAME";
    public static final String SHARE_UNITCODES = "shareUnitcodes";
    @Autowired
    private WorkflowExtendQueryDao workflowExtendQueryDao;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    @Autowired
    private VaRelParamCacheHelper vaRelParamCacheHelper;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private BaseDataClient baseDataClient;
    public static final int MAX_UNIT_SIZE = 1000;

    @Override
    public PageVO<UserDO> queryUser(WorkflowExtendQueryDTO workflowExtendQueryDTO) {
        List<Object> userDOList;
        int count;
        List<OrgDO> orgDOList;
        String unitCode = workflowExtendQueryDTO.getUnitcode();
        if (!StringUtils.hasText(unitCode)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        if ("-".equals(unitCode)) {
            orgDOList = this.queryAuthOrg();
        } else if (workflowExtendQueryDTO.isQuerySubordinateAndSelf()) {
            orgDOList = this.querySubordinateAndSelf(unitCode);
        } else {
            return this.currUnitUsers(workflowExtendQueryDTO);
        }
        if (orgDOList.size() > 1000) {
            ArrayList<VaRelParamCacheDO> relParamCacheDOS = new ArrayList<VaRelParamCacheDO>();
            for (OrgDO orgDO : orgDOList) {
                VaRelParamCacheDO cacheDO = new VaRelParamCacheDO();
                cacheDO.setC(orgDO.getCode());
                cacheDO.setI(UUID.randomUUID().toString());
                relParamCacheDOS.add(cacheDO);
            }
            VaRelParamCacheVO cacheTableCode = this.vaRelParamCacheHelper.getRelParamCacheTableCode(relParamCacheDOS);
            workflowExtendQueryDTO.setMappingTable(cacheTableCode.getMappingTable());
            workflowExtendQueryDTO.setHashKey(cacheTableCode.getHashKey());
            workflowExtendQueryDTO.setPagination(false);
            count = this.workflowExtendQueryDao.countByRelParam(workflowExtendQueryDTO);
            if (count == 0) {
                userDOList = Collections.emptyList();
            } else {
                workflowExtendQueryDTO.setPagination(true);
                userDOList = this.workflowExtendQueryDao.listByRelParam(workflowExtendQueryDTO);
            }
        } else {
            workflowExtendQueryDTO.setPagination(false);
            List<String> unitCodeList = orgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
            workflowExtendQueryDTO.setUnitCodeList(unitCodeList);
            count = this.workflowExtendQueryDao.countUser(workflowExtendQueryDTO);
            if (count == 0) {
                userDOList = Collections.emptyList();
            } else {
                workflowExtendQueryDTO.setPagination(true);
                userDOList = this.workflowExtendQueryDao.queryUser(workflowExtendQueryDTO);
            }
        }
        for (UserDO userDO : userDOList) {
            OrgDO orgDO;
            orgDO = orgDOList.stream().filter(item -> item.getCode().equals(userDO.getUnitcode())).findFirst().orElse(null);
            userDO.addExtInfo("orgInfo", (Object)orgDO);
        }
        return new PageVO(userDOList, count);
    }

    private PageVO<UserDO> currUnitUsers(WorkflowExtendQueryDTO workflowExtendQueryDTO) {
        workflowExtendQueryDTO.setPagination(false);
        int count = this.workflowExtendQueryDao.countUser(workflowExtendQueryDTO);
        if (count == 0) {
            return new PageVO(new ArrayList(), 0);
        }
        workflowExtendQueryDTO.setPagination(true);
        List<UserDO> userDOList = this.workflowExtendQueryDao.queryUser(workflowExtendQueryDTO);
        return new PageVO(userDOList, count);
    }

    private List<OrgDO> querySubordinateAndSelf(String unitCode) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(unitCode);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(this.getAuthType());
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        PageVO orgDOPageVO = this.orgDataClient.list(orgDTO);
        R r = orgDOPageVO.getRs();
        if (r.getCode() != 0) {
            throw new WorkflowException(r.getMsg());
        }
        List rows = orgDOPageVO.getRows();
        if (CollectionUtils.isEmpty(rows)) {
            return new ArrayList<OrgDO>();
        }
        return rows;
    }

    private List<OrgDO> queryAuthOrg() {
        OrgDTO param = new OrgDTO();
        param.setAuthType(this.getAuthType());
        param.setCategoryname("MD_ORG");
        param.setVersionDate(new Date());
        PageVO orgDOPageVO = this.orgDataClient.list(param);
        R r = orgDOPageVO.getRs();
        if (r.getCode() != 0) {
            throw new WorkflowException(r.getMsg());
        }
        List rows = orgDOPageVO.getRows();
        if (CollectionUtils.isEmpty(rows)) {
            return new ArrayList<OrgDO>();
        }
        return rows;
    }

    private OrgDataOption.AuthType getAuthType() {
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setName("WF1020");
        List optionItemVOs = this.workflowOptionService.list(optionItemDTO);
        String wf1020 = ((OptionItemVO)optionItemVOs.get(0)).getVal();
        return "1".equals(wf1020) ? OrgDataOption.AuthType.ACCESS : OrgDataOption.AuthType.NONE;
    }

    @Override
    public PageVO<BaseDataDO> queryStaff(WorkflowExtendQueryDTO queryDTO) {
        List<OrgDO> orgDOList;
        String unitCode = queryDTO.getUnitcode();
        if (!StringUtils.hasText(unitCode)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        if ("-".equals(unitCode)) {
            orgDOList = this.queryAuthOrg();
        } else if (queryDTO.isQuerySubordinateAndSelf()) {
            orgDOList = this.querySubordinateAndSelf(unitCode);
        } else {
            orgDOList = new ArrayList<OrgDO>();
            OrgDO orgDO = new OrgDO();
            orgDO.setCode(unitCode);
            orgDOList.add(orgDO);
        }
        BaseDataDTO baseDataDTO = queryDTO.getBaseDataDTO();
        List unitCodeList = orgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
        baseDataDTO.put(SHARE_UNITCODES, unitCodeList);
        return this.baseDataClient.list(baseDataDTO);
    }
}

