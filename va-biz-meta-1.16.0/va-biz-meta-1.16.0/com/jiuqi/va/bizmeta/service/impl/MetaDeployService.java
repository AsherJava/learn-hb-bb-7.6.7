/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.domain.metadeploy.MetaDataDeployDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.exception.MetaCheckException;
import com.jiuqi.va.bizmeta.service.IMetaDeployService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.MetaDeployHandle;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataGroupService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.bizmeta.util.MetaDataDeployUtils;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class MetaDeployService
implements IMetaDeployService {
    private static final Logger log = LoggerFactory.getLogger(MetaDeployService.class);
    @Autowired
    private IMetaDataInfoUserDao metaInfoEditionDao;
    @Autowired
    private IMetaDataGroupDao metaGroupDao;
    @Autowired
    private IMetaDataGroupUserDao metaGroupUserDao;
    @Autowired
    private MetaDataInfoService metaDataInfoService;
    private IMetaInfoService metaInfoService;
    private IMetaGroupService metaGroupSerice;
    @Autowired
    private MetaDataGroupService metaDataGroupService;
    @Autowired
    private IMetaVersionService metaVersionService;
    @Autowired
    private MetaDeployHandle metaDeployHandle;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;

    public IMetaInfoService getMetaInfoService() {
        if (this.metaInfoService == null) {
            this.metaInfoService = (IMetaInfoService)ApplicationContextRegister.getBean(IMetaInfoService.class);
        }
        return this.metaInfoService;
    }

    public IMetaGroupService getMetaGroupSerice() {
        if (this.metaGroupSerice == null) {
            this.metaGroupSerice = (IMetaGroupService)ApplicationContextRegister.getBean(IMetaGroupService.class);
        }
        return this.metaGroupSerice;
    }

    @Override
    public MetaDataDeployDTO getDeployDatas(String userName, MetaModelDTO infoDTO) {
        ArrayList<MetaDataDeployDim> deployDatas = new ArrayList<MetaDataDeployDim>();
        List<MetaGroupEditionDO> groupEditionDoList = this.metaDataGroupService.getGroupEditionList(infoDTO.getModuleName(), infoDTO.getMetaType());
        List<MetaGroupDTO> metaGroupDtoList = this.metaDataGroupService.getGroupList(infoDTO.getModuleName(), infoDTO.getMetaType(), OperateType.DESIGN);
        for (MetaGroupEditionDO metaGroupEditionDO : groupEditionDoList) {
            MetaDataDeployDim dataDeployDim = new MetaDataDeployDim();
            dataDeployDim.setId(metaGroupEditionDO.getId());
            dataDeployDim.setPath(MetaUtils.getGroupPath(metaGroupDtoList, metaGroupEditionDO));
            dataDeployDim.setState(metaGroupEditionDO.getMetaState().intValue());
            dataDeployDim.setVersion(metaGroupEditionDO.getVersionNo().longValue());
            dataDeployDim.setType("group");
            dataDeployDim.setModuleName(metaGroupEditionDO.getModuleName());
            deployDatas.add(dataDeployDim);
        }
        List<MetaInfoEditionDO> infoEditionDoList = this.metaDataInfoService.getMetaEditionList(infoDTO);
        for (MetaInfoEditionDO metaInfoEditionDO : infoEditionDoList) {
            MetaDataDeployDim dataDeployDim = new MetaDataDeployDim();
            dataDeployDim.setId(metaInfoEditionDO.getId());
            dataDeployDim.setPath(MetaUtils.getGroupPath(metaGroupDtoList, metaInfoEditionDO));
            dataDeployDim.setState(metaInfoEditionDO.getMetaState().intValue());
            if (metaInfoEditionDO.getVersionNO() != null) {
                dataDeployDim.setVersion(metaInfoEditionDO.getVersionNO().longValue());
            }
            dataDeployDim.setType("metaData");
            dataDeployDim.setModuleName(metaInfoEditionDO.getModuleName());
            deployDatas.add(dataDeployDim);
        }
        deployDatas.sort(Comparator.comparing(MetaDataDeployDim::getPath));
        MetaDataDeployDTO metaDataDeployDTO = new MetaDataDeployDTO();
        metaDataDeployDTO.setDeployDatas(deployDatas);
        return metaDataDeployDTO;
    }

    @Override
    public MetaDataDeployDTO publishMetaData(String userName, MetaDataDeployDTO deployDTO) {
        String uuid = "000000000000";
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            uuid = (String)RequestContextUtil.getAttribute((String)"PUB_LOG_UUID");
        }
        MetaDataDeployDTO dataDeployDTO = new MetaDataDeployDTO();
        List<MetaDataDeployDim> dataDeployDims = deployDTO.getDeployDatas();
        if (dataDeployDims == null) {
            log.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u53d1\u5e03\u6570\u636e\u4e3a\u7a7a", (Object)uuid);
            return dataDeployDTO;
        }
        int metaDataSize = dataDeployDims.size();
        ArrayList<String> sussessData = new ArrayList<String>();
        ArrayList<Map<String, Object>> failedData = new ArrayList<Map<String, Object>>();
        List<MetaGroupEditionDO> groupEditionDOs = this.metaDataGroupService.getGroupEditionList();
        MetaUtils.gatherUnPublishGroup(groupEditionDOs, dataDeployDims);
        MetaUtils.sortPublishDatas(dataDeployDims);
        long newVersion = VersionManage.getInstance().newVersion(this.metaVersionService);
        log.info("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u6536\u96c6\u672a\u53d1\u5e03\u5206\u7ec4\u3001\u6392\u5e8f\u5f85\u53d1\u5e03\u5143\u7d20({}\u6761\u6570\u636e)\u3002\u51c6\u5907\u9010\u4e2a\u53d1\u5e03...versionNo: {}", uuid, metaDataSize, newVersion);
        int index = 0;
        for (MetaDataDeployDim metaDataDeployDim : dataDeployDims) {
            String orderStr;
            ++index;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            boolean isMetaData = "metaData".equals(metaDataDeployDim.getType());
            String metaStr = MetaDataDeployUtils.readMetaStr(metaDataDeployDim.getPath());
            if (isMetaData) {
                orderStr = String.format("(%d/%d)\u3010%s\u3011", index, metaDataSize, metaStr);
                if (servletRequestAttributes != null) {
                    RequestContextUtil.setAttribute((String)"PUB_LOG_ITEM_INDEX", (Object)orderStr);
                }
            } else {
                orderStr = "\u3010\u5206\u7ec4\u3011\u3010" + metaStr + "\u3011";
            }
            try {
                if (isMetaData) {
                    log.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u53d1\u5e03\u524d", (Object)uuid, (Object)orderStr);
                }
                this.metaDeployHandle.doPublish(metaDataDeployDim, userName, newVersion, this.getMetaInfoService(), this.getMetaGroupSerice());
                if (isMetaData) {
                    log.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u53d1\u5e03\u540e", (Object)uuid, (Object)orderStr);
                }
                sussessData.add(metaDataDeployDim.getPath());
                if (!isMetaData) continue;
                log.info("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u51c6\u5907\u5e7f\u64ad\u5904\u7406\u96c6\u7fa4\u7f13\u5b58...", (Object)uuid, (Object)orderStr);
                this.metaSyncCacheService.pushSyncMsg(metaDataDeployDim.getId(), metaDataDeployDim.getState());
                log.info("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u5e7f\u64ad\u5904\u7406\u96c6\u7fa4\u7f13\u5b58\u6210\u529f", (Object)uuid, (Object)orderStr);
            }
            catch (MetaCheckException e) {
                List<PluginCheckResultVO> pluginCheckResultVOS = e.getPluginCheckResultVOS();
                hashMap.put("title", metaDataDeployDim.getPath());
                hashMap.put("msg", e.getMessage());
                hashMap.put("checkResult", pluginCheckResultVOS);
                failedData.add(hashMap);
                log.error("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u51fa\u73b0MetaCheck\u5f02\u5e38\uff1a{}", uuid, orderStr, e.getMessage(), e);
            }
            catch (Exception e) {
                hashMap.put("title", metaDataDeployDim.getPath());
                hashMap.put("msg", e.getMessage());
                failedData.add(hashMap);
                log.error("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u51fa\u73b0\u5f02\u5e38\uff1a{}", uuid, orderStr, e.getMessage(), e);
            }
        }
        log.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u5168\u90e8\u6570\u636e\u5df2\u53d1\u5e03\u5b8c\u6210\uff0c\u51c6\u5907\u63d2\u5165\u7248\u672c\u8868\u6570\u636e", (Object)uuid);
        MetaDataVersionDTO dataVersionDO = new MetaDataVersionDTO();
        dataVersionDO.setCreateTime(new Date());
        dataVersionDO.setInfo(deployDTO.getInfo());
        dataVersionDO.setUserName(userName);
        dataVersionDO.setVersionNo(newVersion);
        this.metaVersionService.addMetaVersionInfo(dataVersionDO);
        log.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011\u5df2\u63d2\u5165\u7248\u672c\u8868\u6570\u636e\uff0cversionNo: {}", (Object)uuid, (Object)newVersion);
        dataDeployDTO.setSuccessData(sussessData);
        dataDeployDTO.setFailedData(failedData);
        return dataDeployDTO;
    }

    @Override
    public MetaDataDeployDTO getDeployDataByUniqueCode(String uniqueCode) {
        MetaDataDeployDTO result = new MetaDataDeployDTO();
        MetaInfoEditionDO param = new MetaInfoEditionDO();
        param.setUniqueCode(uniqueCode);
        MetaInfoEditionDO currEditionObj = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(param);
        if (currEditionObj == null) {
            return result;
        }
        String path = this.generateDeployPath((MetaInfoDO)currEditionObj);
        ArrayList<MetaDataDeployDim> data = new ArrayList<MetaDataDeployDim>();
        result.setDeployDatas(data);
        MetaDataDeployDim dataDeployDim = new MetaDataDeployDim();
        dataDeployDim.setId(currEditionObj.getId());
        dataDeployDim.setPath(path);
        dataDeployDim.setState(currEditionObj.getMetaState().intValue());
        if (currEditionObj.getVersionNO() != null) {
            dataDeployDim.setVersion(currEditionObj.getVersionNO().longValue());
        }
        dataDeployDim.setType("metaData");
        dataDeployDim.setModuleName(currEditionObj.getModuleName());
        data.add(dataDeployDim);
        return result;
    }

    private String generateDeployPath(MetaInfoDO metaInfo) {
        String moduleName = metaInfo.getModuleName();
        String metaType = metaInfo.getMetaType();
        String moduleTitle = MetaUtils.getModuleTitleByName(moduleName);
        String metaTypeTitle = Objects.requireNonNull(MetaUtils.getMetaTypeByName(metaType)).getTitle();
        String metaTypeCode = Objects.requireNonNull(MetaUtils.getMetaTypeByName(metaType)).name();
        String groupName = metaInfo.getGroupName();
        MetaGroupDO metaGroupData = this.getMetaGroupData(groupName, metaType, moduleName);
        return moduleTitle + "(" + moduleName + ")/" + metaTypeTitle + "(" + metaTypeCode + ")/" + metaGroupData.getTitle() + "(" + metaGroupData.getName() + ")/" + metaInfo.getTitle() + "(" + metaInfo.getName() + ")";
    }

    private MetaGroupDO getMetaGroupData(String groupName, String metaType, String moduleName) {
        if (groupName == null) {
            return null;
        }
        MetaGroupEditionDO groupParam = new MetaGroupEditionDO();
        groupParam.setMetaType(metaType);
        groupParam.setModuleName(moduleName);
        groupParam.setName(groupName);
        MetaGroupEditionDO groupEdition = (MetaGroupEditionDO)((Object)this.metaGroupUserDao.selectOne((Object)groupParam));
        if (groupEdition != null) {
            return groupEdition;
        }
        return (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)groupParam));
    }
}

