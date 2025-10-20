/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metaupdate.MetaDataUpdateDTO;
import com.jiuqi.va.bizmeta.domain.metaupdate.MetaDataUpdateVO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaVersionManageDTO;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/version"})
public class MetaVersionController {
    private static final Logger logger = LoggerFactory.getLogger(MetaVersionController.class);
    @Autowired
    private IMetaVersionService metaVersionService;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    IMetaInfoService metaInfoService;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;

    @PostMapping(value={"/listMetaInfoHis"})
    public R listMetaInfoHis(MetaInfoDTO metaInfoDTO) {
        try {
            R r = R.ok();
            MetaInfoHistoryDO historyDO = new MetaInfoHistoryDO();
            historyDO.setId(metaInfoDTO.getId());
            historyDO.setMetaType(metaInfoDTO.getMetaType());
            historyDO.setUniqueCode(metaInfoDTO.getUniqueCode());
            historyDO.setVersionNO(metaInfoDTO.getVersionNO());
            historyDO.setRowVersion(metaInfoDTO.getRowVersion());
            historyDO.setModuleName(metaInfoDTO.getModuleName());
            historyDO.setName(metaInfoDTO.getName());
            List<MetaInfoHistoryDO> metaInfoHisList = this.metaInfoService.listMetaInfoHis(historyDO);
            r.put("data", metaInfoHisList);
            return r;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/published/update/{dataId}"})
    public R updatePublished(@PathVariable UUID dataId) {
        R r = R.ok();
        try {
            String paramValue = this.getParamValue();
            MetaDataDTO dataDTO = new MetaDataDTO();
            dataDTO.setDesignData(paramValue);
            this.metaVersionService.updatePublished(dataId, dataDTO);
            this.metaSyncCacheService.pushSyncMsg(dataId, MetaState.MODIFIED.getValue());
        }
        catch (Exception e) {
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    private String getParamValue() throws IOException {
        String line = null;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(RequestContextUtil.getInputStream(), StandardCharsets.UTF_8));){
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u53c2\u6570\u5185\u5bb9\u5931\u8d25\uff1a ", e);
        }
        return sb.toString();
    }

    @PostMapping(value={"/version"})
    public R gatherMetaInfoVersionNos(@RequestBody TenantDO param) {
        R r = R.ok();
        try {
            r = this.metaVersionService.gatherMetaInfoVersionNos(param);
        }
        catch (Exception e) {
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/getDesignData"})
    public String getDesignDataById(@RequestBody TenantDO param) {
        MetaDataDTO metaDataDTO = this.metaVersionService.getDesignDataById(param);
        return metaDataDTO.getDesignData();
    }

    @PostMapping(value={"/update"})
    public MetaDataUpdateVO updateMetaDataOrInfo(@RequestBody TenantDO param) {
        MetaDataUpdateVO updateVO = new MetaDataUpdateVO();
        try {
            MetaDataUpdateDTO deployDTO = this.metaVersionService.updateMetaHistoryBatch(param);
            updateVO.setSuccessData(deployDTO.getSuccessData());
            updateVO.setFailedData(deployDTO.getFailedData());
            updateVO.setFlag(true);
        }
        catch (Exception e) {
            updateVO.setFlag(false);
            updateVO.setMessage(e.getMessage());
        }
        return updateVO;
    }

    @PostMapping(value={"/{uniquecode}/list"})
    public R getMetaVersionInfo(@PathVariable(value="uniquecode") String uniquecode, @RequestBody MetaVersionManageDTO param) {
        try {
            List<MetaVersionManageDTO> infos = this.metaVersionService.getMetaVersionInfos(uniquecode);
            R r = R.ok();
            if (infos == null || infos.isEmpty()) {
                r.put("data", null);
                return r;
            }
            Date beginDate = param.getBeginDate();
            Date endDate = param.getEndDate();
            String username = param.getUsername();
            String info = param.getInfo();
            HashMap userMap = new HashMap();
            List collect = infos.stream().filter(o -> {
                if (beginDate != null && o.getCreatetime() != null && o.getCreatetime().getTime() < beginDate.getTime()) {
                    return false;
                }
                if (endDate != null && o.getCreatetime() != null && o.getCreatetime().getTime() > this.getNextDate(endDate).getTime()) {
                    return false;
                }
                if (StringUtils.hasText(info)) {
                    if (!StringUtils.hasText(o.getInfo())) {
                        return false;
                    }
                    if (!o.getInfo().contains(info)) {
                        return false;
                    }
                }
                userMap.computeIfAbsent(o.getUsername(), val -> this.getUsernameByID(ShiroUtil.getTenantName(), o.getUsername()));
                String name = (String)userMap.get(o.getUsername());
                if (StringUtils.hasText(name)) {
                    o.setUsername(name);
                }
                if (StringUtils.hasText(username)) {
                    if (!StringUtils.hasText(name)) {
                        return false;
                    }
                    return name.contains(username);
                }
                return true;
            }).collect(Collectors.toList());
            r.put("data", collect);
            return r;
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
    }

    private Date getNextDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        ((Calendar)calendar).add(5, 1);
        return calendar.getTime();
    }

    private String getUsernameByID(String tenantName, String userid) {
        UserDTO userDTO = new UserDTO();
        userDTO.tenantName = tenantName;
        userDTO.setId(userid);
        UserDO userDO = this.authUserClient.get(userDTO);
        return userDO == null ? "" : userDO.getName();
    }
}

