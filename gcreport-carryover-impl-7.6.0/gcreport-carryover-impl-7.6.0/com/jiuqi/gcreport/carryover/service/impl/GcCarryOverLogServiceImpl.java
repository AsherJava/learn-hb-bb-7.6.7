/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.carryover.vo.CarryOverOrgInfo
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.carryover.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogDao;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogExtendDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogExtendEO;
import com.jiuqi.gcreport.carryover.service.GcCarryOverLogService;
import com.jiuqi.gcreport.carryover.vo.CarryOverOrgInfo;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcCarryOverLogServiceImpl
implements GcCarryOverLogService {
    @Autowired
    private CarryOverLogDao carryOverLogDao;
    @Autowired
    private CarryOverLogExtendDao carryOverLogExtendDao;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveLog(TaskLog log, QueryParamsVO queryParamsVO, Date beginTime) {
        CarryOverLogEO logEO = this.initLogEO(beginTime, queryParamsVO);
        List messages = log.getMessages();
        StringBuilder sb = new StringBuilder();
        messages.stream().forEach(message -> sb.append(message.getMessage()).append("\n"));
        logEO.setInfo(sb.toString());
        this.carryOverLogDao.add((BaseEntity)logEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveLogExtend(String taskLogId, Map<String, String> extendInfo) {
        ArrayList<CarryOverLogExtendEO> extendEOS = new ArrayList<CarryOverLogExtendEO>(extendInfo.size());
        for (String key : extendInfo.keySet()) {
            String value = extendInfo.get(key);
            CarryOverLogExtendEO eo = this.initLogExtendEO(taskLogId);
            eo.setExtendName(key);
            eo.setExtendValue(value);
            eo.setId(UUIDOrderUtils.newUUIDStr());
            extendEOS.add(eo);
        }
        this.carryOverLogExtendDao.addBatch(extendEOS);
    }

    @Override
    public Pagination<Map<String, Object>> listLogInfo(QueryParamsVO queryParamsVO) {
        HashMap<String, Object> otherCondition = new HashMap<String, Object>();
        String userName = NpContextHolder.getContext().getUserName();
        List allUsers = this.systemUserService.getAllUsers();
        if (!((SystemUser)allUsers.get(0)).getName().equals(userName)) {
            YearPeriodDO period = YearPeriodUtil.transform(null, (String)queryParamsVO.getPeriodStr());
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodDO)period);
            List orgTree = orgTool.getOrgTree();
            ArrayList<CarryOverOrgInfo> orgInfos = new ArrayList<CarryOverOrgInfo>();
            for (GcOrgCacheVO node : orgTree) {
                this.traverseNodes(node, orgInfos);
            }
            ArrayList<String> orgInfoStrs = new ArrayList<String>();
            for (CarryOverOrgInfo orgInfo : orgInfos) {
                orgInfoStrs.add(JsonUtils.writeValueAsString(Collections.singletonList(orgInfo)));
            }
            otherCondition.put("authOrg", orgInfoStrs);
        }
        return this.carryOverLogDao.listLogInfo(queryParamsVO, otherCondition);
    }

    @Override
    public Pagination<Map<String, Object>> listLogInfoBySchemeId(String schemeId, int page, int pageSize) {
        Pagination pagination = new Pagination(null, Integer.valueOf(0), Integer.valueOf(page), Integer.valueOf(pageSize));
        List<Map<String, Object>> maps = this.carryOverLogDao.listLogInfoBySchemeId(schemeId);
        if (CollectionUtils.isEmpty(maps)) {
            return pagination;
        }
        pagination.setContent(maps);
        pagination.setTotalElements(Integer.valueOf(maps.size()));
        return pagination;
    }

    @Override
    public Map<String, Map<String, Object>> listLogExtendInfoByIds(List<String> ids) {
        return this.carryOverLogExtendDao.listLogExtendInfoByIds(ids);
    }

    @Override
    public CarryOverLogEO getCarryOverLogById(String id) {
        CarryOverLogEO eo = new CarryOverLogEO();
        eo.setId(id);
        return (CarryOverLogEO)this.carryOverLogDao.selectByEntity((BaseEntity)eo);
    }

    private CarryOverLogEO initLogEO(Date beginTime, QueryParamsVO queryParamsVO) {
        CarryOverLogEO eo = new CarryOverLogEO();
        BeanUtils.copyProperties(queryParamsVO, (Object)eo);
        eo.setAcctYear(queryParamsVO.getAcctYear() - 1);
        eo.setId(StringUtils.isEmpty((String)queryParamsVO.getTaskLogId()) ? UUIDUtils.newUUIDStr() : queryParamsVO.getTaskLogId());
        eo.setCreator(NpContextHolder.getContext().getUserName());
        eo.setTargetSystemId(queryParamsVO.getConsSystemId());
        eo.setStartTime(beginTime);
        eo.setEndTime(new Date());
        List<CarryOverOrgInfo> orgInfos = this.getCarryOverOrgInfo(queryParamsVO.getOrgList());
        eo.setUnitInfo(JsonUtils.writeValueAsString(orgInfos));
        return eo;
    }

    private CarryOverLogExtendEO initLogExtendEO(String taskLogId) {
        CarryOverLogExtendEO eo = new CarryOverLogExtendEO();
        eo.setLogId(taskLogId);
        return eo;
    }

    private List<CarryOverOrgInfo> getCarryOverOrgInfo(List<GcOrgCacheVO> orgCacheVOList) {
        ArrayList<CarryOverOrgInfo> orgInfos = new ArrayList<CarryOverOrgInfo>();
        orgCacheVOList.stream().forEach(org -> {
            CarryOverOrgInfo info = new CarryOverOrgInfo();
            info.setCode(org.getCode());
            info.setTitle(org.getTitle());
            orgInfos.add(info);
        });
        return orgInfos;
    }

    private void traverseNodes(GcOrgCacheVO node, List<CarryOverOrgInfo> orgInfos) {
        if (node == null) {
            return;
        }
        CarryOverOrgInfo orgInfo = new CarryOverOrgInfo();
        orgInfo.setCode(node.getCode());
        orgInfo.setTitle(node.getTitle());
        orgInfos.add(orgInfo);
        List childrens = node.getChildren();
        if (childrens != null) {
            for (GcOrgCacheVO child : childrens) {
                this.traverseNodes(child, orgInfos);
            }
        }
    }
}

