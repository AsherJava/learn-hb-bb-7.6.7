/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils
 *  com.jiuqi.gcreport.common.util.LamdbaUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.monitor.api.common.MonitorOrgTypeEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorState
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailItemVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorGroupVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorNrSchemeVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSaveInfoVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeInfoVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSchemeCopyVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.NrSchemesVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.MonitorShowDataVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.tool.GcOrgTypeVerCacheTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.bpm.upload.utils.ActionAlias
 *  com.jiuqi.nr.bpm.upload.utils.ActionStateEnum
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.monitor.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.gcreport.common.util.LamdbaUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.monitor.api.common.MonitorOrgTypeEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import com.jiuqi.gcreport.monitor.api.inf.MonitorState;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailItemVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorGroupVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorNrSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSaveInfoVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeInfoVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSchemeCopyVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorVO;
import com.jiuqi.gcreport.monitor.api.vo.config.NrSchemesVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.MonitorShowDataVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO;
import com.jiuqi.gcreport.monitor.impl.config.MonitorSceneCollectUtils;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorGroupConfigDao;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorNrSchemeDao;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDao;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDetailDao;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDetailItemDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailItemEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorGroupEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorNrSchemeEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorSchemeEO;
import com.jiuqi.gcreport.monitor.impl.service.MonitorConfigService;
import com.jiuqi.gcreport.monitor.impl.util.MonitorUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.tool.GcOrgTypeVerCacheTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bpm.upload.utils.ActionAlias;
import com.jiuqi.nr.bpm.upload.utils.ActionStateEnum;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonitorConfigServiceImpl
implements MonitorConfigService {
    @Autowired
    private MonitorGroupConfigDao groupDao;
    @Autowired
    private MonitorSchemeConfigDao configDao;
    @Autowired
    private MonitorNrSchemeDao configSchemeDao;
    @Autowired
    private MonitorSchemeConfigDetailDao configNodeDao;
    @Autowired
    private MonitorSchemeConfigDetailItemDao configNodeItemDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignTimeController;
    @Autowired
    private ActionAlias actionAlias;
    private char PERIOD_SPLIT_CHAR = (char)89;
    public static final String MAX_DATE = "9999-12-30";

    @Override
    public List<ValueAndLabelVO> getMonitorOrgTypes() {
        MonitorOrgTypeEnum[] types;
        ArrayList<ValueAndLabelVO> resultList = new ArrayList<ValueAndLabelVO>();
        for (MonitorOrgTypeEnum tempType : types = MonitorOrgTypeEnum.values()) {
            ValueAndLabelVO valueLable = new ValueAndLabelVO();
            valueLable.setValue(String.valueOf(tempType.getCode()));
            valueLable.setLabel(tempType.getTitle());
            resultList.add(valueLable);
        }
        return resultList;
    }

    @Override
    public List<MonitorSceneNodeVO> getTreeNodes() {
        return this.getAllTreeNodes(null);
    }

    @Override
    public List<MonitorSceneNodeVO> getTreeNodes(String monitorId) {
        if (monitorId == null) {
            return this.getTreeNodes();
        }
        List<MonitorConfigDetailEO> configDetailList = this.configNodeDao.findConfigDetailByMonitorId(monitorId);
        if (configDetailList != null && configDetailList.size() > 0) {
            ArrayList<String> checkNodes = new ArrayList<String>();
            for (MonitorConfigDetailEO tempEO : configDetailList) {
                checkNodes.add(tempEO.getNodeCode());
            }
            return this.getAllTreeNodes(checkNodes);
        }
        return this.getTreeNodes();
    }

    private List<MonitorSceneNodeVO> getAllTreeNodes(List<String> checkNodes) {
        ArrayList<MonitorSceneNodeVO> resultList = new ArrayList<MonitorSceneNodeVO>();
        List<MonitorSceneGroupEnum> groups = MonitorSceneCollectUtils.getSceneGroups();
        for (MonitorSceneGroupEnum tempGroup : groups) {
            List<MonitorSceneEnum> tempScenes;
            MonitorSceneNodeVO nodeVo = new MonitorSceneNodeVO();
            nodeVo.setCode(tempGroup.getCode());
            nodeVo.setTitle(tempGroup.getName());
            if (checkNodes != null && checkNodes.contains(tempGroup.getCode())) {
                nodeVo.setChecked(Boolean.valueOf(true));
            }
            if ((tempScenes = MonitorSceneCollectUtils.getScenes(tempGroup)) != null && tempScenes.size() > 0) {
                ArrayList<MonitorSceneNodeVO> children = new ArrayList<MonitorSceneNodeVO>();
                for (MonitorSceneEnum tempScene : tempScenes) {
                    MonitorSceneNodeVO tempNodeVo = new MonitorSceneNodeVO();
                    tempNodeVo.setCode(tempScene.getCode());
                    tempNodeVo.setTitle(tempScene.getName());
                    tempNodeVo.setGroupCode(tempGroup.getCode());
                    tempNodeVo.setGroupTitle(tempGroup.getName());
                    if (checkNodes != null && checkNodes.contains(tempScene.getCode())) {
                        tempNodeVo.setChecked(Boolean.valueOf(true));
                    }
                    tempNodeVo.setStates(this.getStates(tempScene.getMonitorStates()));
                    tempNodeVo.setConfigDataModel(this.getConfigDataModel(tempScene));
                    tempNodeVo.setChildren(new ArrayList());
                    children.add(tempNodeVo);
                }
                nodeVo.setChildren(children);
            }
            nodeVo.setStates(this.getStates(tempGroup.getMonitorStates()));
            nodeVo.setConfigDataModel(this.getConfigDataModel(tempGroup));
            resultList.add(nodeVo);
        }
        return resultList;
    }

    private MonitorConfigDetailVO getConfigDataModel(MonitorSceneEnum tempScene) {
        return this.getConfigDataModel(tempScene.getCode(), tempScene.getName(), null);
    }

    private MonitorConfigDetailVO getConfigDataModelWithNodeOrgType(MonitorSceneEnum tempScene, String nodeTitle, Map<String, String> stateNameMap) {
        MonitorConfigDetailVO configDataModel = this.getConfigDataModel(tempScene.getCode(), nodeTitle == null ? tempScene.getName() : nodeTitle, stateNameMap);
        configDataModel.setNodeOrgType(Integer.valueOf(tempScene.getNodeOrgType().getCode()));
        return configDataModel;
    }

    private MonitorConfigDetailVO getConfigDataModel(MonitorSceneGroupEnum tempScene) {
        return this.getConfigDataModel(tempScene.getCode(), tempScene.getName(), null);
    }

    private MonitorConfigDetailVO getConfigDataModel(String nodeCode, String nodeName, Map<String, String> stateNameMap) {
        MonitorConfigDetailVO vo = new MonitorConfigDetailVO();
        vo.setNodeCode(nodeCode);
        vo.setChecked(Integer.valueOf(0));
        vo.setcLeafNodeFlag(Integer.valueOf(0));
        vo.setReNodeTitle(nodeName);
        vo.setShowType("0");
        vo.setTableData(this.getAllNodeItems(null, nodeCode, null, stateNameMap));
        return vo;
    }

    private List<MonitorState> getStates(MonitorStateEnum[] monitorStates) {
        ArrayList<MonitorState> states = new ArrayList<MonitorState>();
        for (MonitorStateEnum tempState : monitorStates) {
            MonitorState monitorState = new MonitorState(tempState.getCode().intValue(), tempState.getName());
            states.add(monitorState);
        }
        return states;
    }

    @Override
    public MonitorGroupVO saveMonitorGroup(MonitorGroupVO vo) {
        this.checkVO(vo);
        return this.convertEO2VO(this.doSaveMonitorGroup(this.convertVO2EO(vo)));
    }

    @Override
    public MonitorGroupVO modifyMonitorGroup(MonitorGroupVO vo) {
        this.checkVO(vo);
        return this.convertEO2VO(this.doModifyMonitorGroup(this.convertVO2EO(vo)));
    }

    @Override
    public MonitorGroupVO getMonitorGroup(String recid) {
        return this.convertEO2VO((MonitorGroupEO)this.groupDao.get((Serializable)((Object)recid)));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String delMonitorGroup(String recid) {
        List<MonitorSchemeEO> schemeEoList = this.configDao.findByGroupId(recid);
        schemeEoList.forEach(tempScheme -> {
            this.configSchemeDao.deleteNrSchemeByMonitorId(tempScheme.getId());
            List<MonitorConfigDetailEO> configNodeList = this.configNodeDao.findConfigDetailByMonitorId(tempScheme.getId());
            configNodeList.forEach(tempConfigNode -> this.configNodeItemDao.deleteConfigDetailItemByConfigId(tempConfigNode.getId()));
            this.configNodeDao.deleteBySchemeId(tempScheme.getId());
        });
        this.configDao.deleteByGroupId(recid);
        MonitorGroupEO group = new MonitorGroupEO();
        group.setId(recid);
        this.groupDao.delete((BaseEntity)group);
        return recid;
    }

    @Override
    public MonitorGroupVO getMonitorGroupSchemes() {
        List<MonitorGroupEO> groups = this.groupDao.loadAllBySortOrder();
        List<MonitorSchemeEO> schemes = this.configDao.loadAllBySortOrder();
        ArrayList groupChildren = new ArrayList();
        groups.stream().map(this::convertEO2VO).forEach(group -> {
            group.setNodeType("group");
            ArrayList schemeChildren = new ArrayList();
            schemes.stream().filter(tempEo -> tempEo.getGroupId() != null && tempEo.getGroupId().equals(group.getRecid())).forEach(tempEo -> {
                MonitorGroupVO leaf = new MonitorGroupVO();
                leaf.setCode(tempEo.getSchemeCode());
                leaf.setLabel(tempEo.getSchemeName());
                leaf.setTitle(tempEo.getSchemeName());
                leaf.setKey(tempEo.getId().toString());
                leaf.setGroupId(tempEo.getGroupId());
                leaf.setRecid(tempEo.getId());
                leaf.setShowNode(tempEo.getShowNode());
                leaf.setNodeType("leaf");
                schemeChildren.add(leaf);
            });
            group.setChildren(schemeChildren);
            groupChildren.add(group);
        });
        MonitorGroupVO root = new MonitorGroupVO();
        root.setRecid(UUIDUtils.emptyUUIDStr());
        root.setLabel("\u5168\u90e8");
        root.setTitle("\u5168\u90e8");
        root.setKey("all");
        root.setNodeType("all");
        root.setChildren(groupChildren);
        return root;
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorGroupEO doSaveMonitorGroup(MonitorGroupEO eo) {
        this.beforeSave(eo);
        this.groupDao.save(eo);
        return eo;
    }

    private void beforeSave(MonitorGroupEO eo) {
        eo.setId(UUID.randomUUID().toString());
        eo.setCreateTime(new Date());
        eo.setRecver(0L);
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setCreatorId(loginUser.getId());
        }
        eo.setSortOrder(MonitorUtil.newOrder());
    }

    private MonitorGroupVO convertEO2VO(MonitorGroupEO eo) {
        MonitorGroupVO vo = new MonitorGroupVO();
        vo.setRecid(eo.getId());
        vo.setGroupId(eo.getGroupId());
        vo.setCode(eo.getGroupCode());
        vo.setLabel(eo.getGroupName());
        vo.setTitle(eo.getGroupName());
        return vo;
    }

    private MonitorGroupEO convertVO2EO(MonitorGroupVO vo) {
        MonitorGroupEO eo = new MonitorGroupEO();
        eo.setId(vo.getRecid());
        eo.setGroupId(vo.getGroupId());
        eo.setGroupCode(vo.getCode());
        eo.setGroupName(vo.getLabel());
        return eo;
    }

    private void checkVO(MonitorGroupVO vo) {
        if (StringUtils.isEmpty((CharSequence)vo.getCode())) {
            throw new BusinessRuntimeException("\u5206\u7ec4\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (StringUtils.isEmpty((CharSequence)vo.getLabel())) {
            throw new BusinessRuntimeException("\u5206\u7ec4\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (vo.getRecid() == null && !this.groupDao.checkMonitorGroupCode(vo.getCode()).booleanValue()) {
            throw new BusinessRuntimeException("\u6807\u8bc6\u91cd\u590d\uff0c\u8bf7\u4fee\u6539\u6807\u8bc6");
        }
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorGroupEO doModifyMonitorGroup(MonitorGroupEO eo) {
        this.beforeModify(eo);
        this.groupDao.update((BaseEntity)eo);
        return eo;
    }

    private void beforeModify(MonitorGroupEO eo) {
        MonitorGroupEO oldEO = (MonitorGroupEO)this.groupDao.get((Serializable)((Object)eo.getId()));
        oldEO.setModifyTime(new Date());
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            oldEO.setModifierId(loginUser.getId());
        }
        oldEO.setGroupName(eo.getGroupName());
        eo = oldEO;
    }

    @Override
    public List<ValueAndLabelVO> getMonitorGroups() {
        List<MonitorGroupEO> groups = this.groupDao.loadAllBySortOrder();
        return groups.stream().map(monitorGroupEO -> {
            ValueAndLabelVO result = new ValueAndLabelVO();
            result.setLabel(monitorGroupEO.getGroupName());
            result.setValue(monitorGroupEO.getId().toString());
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public MonitorVO addScheme(MonitorSaveInfoVO monitorSaveVo) {
        MonitorNrSchemeEO byFormSchemeId;
        List nrSchemeList = monitorSaveVo.getMonitorNrData();
        if (!CollectionUtils.isEmpty((Collection)nrSchemeList) && null != (byFormSchemeId = this.configSchemeDao.findByFormSchemeId(((MonitorNrSchemeVO)nrSchemeList.get(0)).getNrId())) && !byFormSchemeId.getMonitorId().equals(monitorSaveVo.getRecid())) {
            throw new BusinessRuntimeException("\u8be5\u62a5\u8868\u65b9\u6848\u5df2\u7ecf\u88ab\u5173\u8054\uff01");
        }
        MonitorVO monitorVo = null;
        monitorVo = StringUtils.isEmpty((CharSequence)monitorSaveVo.getRecid()) ? this.saveScheme(this.getMonitorSchemeVO(monitorSaveVo)) : this.updateScheme(this.getMonitorSchemeVO(monitorSaveVo));
        String monitorID = monitorVo.getRecid();
        this.processMonitorNrScheme(monitorSaveVo, monitorID);
        this.processSaveNodeConfig(monitorSaveVo, monitorID);
        return monitorVo;
    }

    private void processSaveNodeConfig(MonitorSaveInfoVO monitorSaveVo, String monitorID) {
        Map detailVoMap = monitorSaveVo.getNodeDataMap();
        List checkNodeCodeList = monitorSaveVo.getNodeList();
        ArrayList detailVoList = new ArrayList();
        detailVoList.addAll(detailVoMap.values());
        if (!CollectionUtils.isEmpty(detailVoList)) {
            List<MonitorConfigDetailVO> configDetailVOS = detailVoList.stream().map(monitorConfigDetailVO -> {
                if (checkNodeCodeList != null) {
                    if (checkNodeCodeList.contains(monitorConfigDetailVO.getNodeCode())) {
                        monitorConfigDetailVO.setChecked(Integer.valueOf(1));
                    } else {
                        monitorConfigDetailVO.setChecked(Integer.valueOf(0));
                    }
                }
                monitorConfigDetailVO.setMonitorId(monitorID);
                return monitorConfigDetailVO;
            }).collect(Collectors.toList());
            this.saveMonitorNodeScheme(configDetailVOS);
            detailVoList.forEach(tempNodeScheme -> {
                List nodeDatailItemList = tempNodeScheme.getTableData();
                if (!CollectionUtils.isEmpty((Collection)nodeDatailItemList)) {
                    nodeDatailItemList.forEach(tempItem -> {
                        tempItem.setMonitorId(monitorID);
                        tempItem.setConfigId(tempNodeScheme.getRecid());
                    });
                    this.saveNodeDatailItemList(nodeDatailItemList.stream().filter(LamdbaUtils.distinctByKey(MonitorConfigDetailItemVO::getNodeStateCode)).collect(Collectors.toList()), tempNodeScheme.getRecid());
                }
            });
        }
    }

    private void processMonitorNrScheme(MonitorSaveInfoVO monitorSaveVo, String monitorID) {
        List nrSchemeList = monitorSaveVo.getMonitorNrData();
        if (!CollectionUtils.isEmpty((Collection)nrSchemeList)) {
            List<MonitorNrSchemeVO> nrSchemeVOS = nrSchemeList.stream().map(monitorNrSchemeVO -> {
                if (monitorNrSchemeVO.getMonitorId() == null) {
                    monitorNrSchemeVO.setMonitorId(monitorID);
                }
                return monitorNrSchemeVO;
            }).collect(Collectors.toList());
            this.saveMonitorNrScheme(nrSchemeVOS);
        }
    }

    @Override
    public MonitorSaveInfoVO getMonitorSchemeById(String recid) {
        MonitorSaveInfoVO resultVo = new MonitorSaveInfoVO();
        this.setMonitorSchemeVO(this.convertEO2VO((MonitorSchemeEO)this.configDao.get((Serializable)((Object)recid))), resultVo);
        resultVo.setMonitorNrData(this.getMonitorNrSchemeByMonitorId(recid));
        List<MonitorConfigDetailVO> configDetailVoList = this.getMonitorConfigDetailsByMonitorId(recid);
        ArrayList nodeList = new ArrayList();
        LinkedHashMap nodeDataMap = new LinkedHashMap();
        configDetailVoList.stream().sorted(Comparator.comparingInt(config -> {
            String nodeCode = config.getNodeCode();
            return MonitorSceneCollectUtils.getMonitorScene(nodeCode).getOrder();
        })).forEach(tempConfigDetail -> {
            tempConfigDetail.setTableData(this.getConfigItems(tempConfigDetail.getRecid()));
            nodeDataMap.put(tempConfigDetail.getNodeCode(), tempConfigDetail);
            if (tempConfigDetail.getChecked() == 1) {
                nodeList.add(tempConfigDetail.getNodeCode());
            }
        });
        resultVo.setNodeDataMap(nodeDataMap);
        resultVo.setAllNodes(this.getTreeNodes(recid));
        resultVo.setNodeList(nodeList);
        return resultVo;
    }

    @Override
    public void saveNodeDatailItemList(List<MonitorConfigDetailItemVO> voList, String configId) {
        this.checkNodeDetailItemVO(voList);
        voList.forEach(item -> {
            MonitorConfigDetailItemVO tempVO = this.saveNodeDatailItem((MonitorConfigDetailItemVO)item);
            item.setRecid(tempVO.getRecid());
        });
        this.configNodeItemDao.deleteConfigDetailItemByConfigId(configId);
        List detailItemEOS = voList.stream().map(item -> {
            MonitorConfigDetailItemEO eo = this.convertVO2EO((MonitorConfigDetailItemVO)item);
            eo.setId(UUID.randomUUID().toString());
            ContextUser loginUser = NpContextHolder.getContext().getUser();
            if (loginUser != null) {
                eo.setCreatorId(loginUser.getId());
            }
            eo.setCreateTime(new Date());
            return eo;
        }).collect(Collectors.toList());
        this.configNodeItemDao.saveAll(detailItemEOS);
    }

    @Override
    public MonitorConfigDetailItemVO saveNodeDatailItem(MonitorConfigDetailItemVO vo) {
        this.checkVO(vo);
        if (vo.getRecid() != null) {
            return this.convertEO2VO(this.doUpdateNodeDatailItem(this.convertVO2EO(vo)));
        }
        return this.convertEO2VO(this.doSaveNodeDatailItem(this.convertVO2EO(vo)));
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorConfigDetailItemEO doSaveNodeDatailItem(MonitorConfigDetailItemEO eo) {
        this.beforeSave(eo);
        this.configNodeItemDao.save(eo);
        return eo;
    }

    private void beforeSave(MonitorConfigDetailItemEO eo) {
        eo.setId(UUID.randomUUID().toString());
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setCreatorId(loginUser.getId());
        }
        eo.setCreateTime(new Date());
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorConfigDetailItemEO doUpdateNodeDatailItem(MonitorConfigDetailItemEO eo) {
        this.beforeUpdate(eo);
        this.configNodeItemDao.update((BaseEntity)eo);
        return eo;
    }

    private void beforeUpdate(MonitorConfigDetailItemEO eo) {
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setModifierId(loginUser.getId());
        }
        eo.setModifyTime(new Date());
    }

    private MonitorConfigDetailItemEO convertVO2EO(MonitorConfigDetailItemVO vo) {
        MonitorConfigDetailItemEO eo = new MonitorConfigDetailItemEO();
        eo.setId(vo.getRecid());
        eo.setConfigId(vo.getConfigId());
        eo.setNodeStateCode(vo.getNodeStateCode());
        eo.setcNodeNewName(vo.getcNodeNewName());
        eo.setcNodeColor(vo.getcNodeColor());
        eo.setFlag(vo.getFlag());
        if (vo.getgRelationNode() != null && vo.getgRelationNode().size() == 2) {
            eo.setgRelationNode((String)vo.getgRelationNode().get(0));
            eo.setgRelationNodeState((String)vo.getgRelationNode().get(1));
        }
        return eo;
    }

    private MonitorConfigDetailItemVO convertEO2VO(MonitorConfigDetailItemEO eo) {
        MonitorConfigDetailItemVO vo = new MonitorConfigDetailItemVO();
        vo.setRecid(eo.getId());
        vo.setConfigId(eo.getConfigId());
        vo.setNodeStateCode(eo.getNodeStateCode());
        vo.setcNodeNewName(eo.getcNodeNewName());
        vo.setcNodeColor(eo.getcNodeColor());
        vo.setFlag(eo.getFlag());
        ArrayList<String> relationNodeInfo = new ArrayList<String>();
        relationNodeInfo.add(eo.getgRelationNode());
        relationNodeInfo.add(eo.getgRelationNodeState());
        vo.setgRelationNode(relationNodeInfo);
        return vo;
    }

    private void checkNodeDetailItemVO(List<MonitorConfigDetailItemVO> voList) {
        if (voList != null && voList.size() > 0) {
            voList.forEach(this::checkVO);
        }
    }

    private void checkVO(MonitorConfigDetailItemVO item) {
    }

    @Override
    public void saveMonitorNodeScheme(List<MonitorConfigDetailVO> voList) {
        this.checkMonitorNodeVO(voList);
        voList.forEach(item -> {
            MonitorConfigDetailVO tempVO = this.saveMonitorNodeScheme((MonitorConfigDetailVO)item);
            item.setRecid(tempVO.getRecid());
        });
    }

    @Override
    public MonitorConfigDetailVO saveMonitorNodeScheme(MonitorConfigDetailVO vo) {
        this.checkVO(vo);
        if (vo.getRecid() != null) {
            return this.convertEO2VO(this.doUpdateMonitorNodeScheme(this.convertVO2EO(vo)));
        }
        MonitorConfigDetailEO eo = this.configNodeDao.findConfigDetailByNodecode(vo.getMonitorId(), vo.getNodeCode());
        if (eo != null) {
            vo.setRecid(eo.getId());
            return this.convertEO2VO(this.doUpdateMonitorNodeScheme(this.convertVO2EO(vo)));
        }
        return this.convertEO2VO(this.doSaveMonitorNodeScheme(this.convertVO2EO(vo)));
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorConfigDetailEO doSaveMonitorNodeScheme(MonitorConfigDetailEO eo) {
        this.beforeSave(eo);
        this.configNodeDao.save(eo);
        return eo;
    }

    private void beforeSave(MonitorConfigDetailEO eo) {
        eo.setId(UUID.randomUUID().toString());
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setCreatorId(loginUser.getId());
        }
        eo.setCreateTime(new Date());
        eo.setSortOrder(MonitorUtil.newOrder());
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorConfigDetailEO doUpdateMonitorNodeScheme(MonitorConfigDetailEO eo) {
        this.beforeUpdate(eo);
        this.configNodeDao.update((BaseEntity)eo);
        return eo;
    }

    private void beforeUpdate(MonitorConfigDetailEO eo) {
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setModifierId(loginUser.getId());
        }
        eo.setModifyTime(new Date());
    }

    private void checkVO(MonitorConfigDetailVO vo) {
    }

    private MonitorConfigDetailVO convertEO2VO(MonitorConfigDetailEO eo) {
        MonitorConfigDetailVO vo = new MonitorConfigDetailVO();
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setShowType(String.valueOf(eo.getShowType()));
        vo.setRecid(eo.getId());
        String unitids = eo.getUnitIds();
        if (unitids != null) {
            String[] split = unitids.split(";");
            List<String> asList = Arrays.asList(split);
            vo.setUnitIds(asList);
        } else {
            vo.setUnitIds(new ArrayList());
        }
        return vo;
    }

    private MonitorConfigDetailEO convertVO2EO(MonitorConfigDetailVO vo) {
        MonitorConfigDetailEO eo = new MonitorConfigDetailEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        if (StringUtils.isNotEmpty((CharSequence)vo.getShowType())) {
            eo.setShowType(Integer.valueOf(vo.getShowType()));
        }
        eo.setId(vo.getRecid());
        if (vo.getUnitIds() != null && vo.getUnitIds().size() > 0) {
            String[] o = new String[vo.getUnitIds().size()];
            String node = String.join((CharSequence)";", vo.getUnitIds().toArray(o));
            eo.setUnitIds(node);
        }
        return eo;
    }

    private void checkMonitorNodeVO(List<MonitorConfigDetailVO> voList) {
        if (voList != null && voList.size() > 0) {
            voList.forEach(this::checkVO);
        }
    }

    @Override
    public MonitorNrSchemeVO saveMonitorNrScheme(MonitorNrSchemeVO vo) {
        List<MonitorNrSchemeEO> nrSchemeEOS = this.configSchemeDao.getNrSchemeByMonitorId(vo.getMonitorId());
        if (CollectionUtils.isEmpty(nrSchemeEOS)) {
            return this.convertEO2VO(this.doSaveNrScheme(this.convertVO2EO(vo)));
        }
        vo.setRecid(nrSchemeEOS.get(0).getId());
        vo.setMonitorId(vo.getMonitorId());
        return this.convertEO2VO(this.doUpdateNrScheme(this.convertVO2EO(vo)));
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorNrSchemeEO doUpdateNrScheme(MonitorNrSchemeEO eo) {
        this.beforeUpdate(eo);
        this.configSchemeDao.update((BaseEntity)eo);
        return eo;
    }

    private void beforeUpdate(MonitorNrSchemeEO eo) {
        MonitorNrSchemeEO oldEO = (MonitorNrSchemeEO)this.configSchemeDao.get((Serializable)((Object)eo.getId()));
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setModifierId(loginUser.getId());
        }
        eo.setModifyTime(new Date());
        eo.setStartDate(oldEO.getStartDate());
        eo.setEndDate(oldEO.getEndDate());
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorNrSchemeEO doSaveNrScheme(MonitorNrSchemeEO eo) {
        this.beforeSave(eo);
        this.configSchemeDao.save(eo);
        return eo;
    }

    private void beforeSave(MonitorNrSchemeEO eo) {
        eo.setId(UUID.randomUUID().toString());
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setCreatorId(loginUser.getId());
        }
        eo.setCreateTime(new Date());
        eo.setSortOrder(MonitorUtil.newOrder());
    }

    private MonitorNrSchemeEO convertVO2EO(MonitorNrSchemeVO vo) {
        MonitorNrSchemeEO eo = new MonitorNrSchemeEO();
        eo.setId(vo.getRecid());
        eo.setMonitorId(vo.getMonitorId());
        eo.setNrId(vo.getNrId());
        PeriodType nrSchemeType = this.getNrFormSchemeType(vo.getNrId());
        this.PERIOD_SPLIT_CHAR = (char)nrSchemeType.code();
        eo.setStartDate(vo.getStartYear() + this.PERIOD_SPLIT_CHAR + this.changeMonthValue(vo.getStartCustom()));
        eo.setEndDate(vo.getEndYear() + this.PERIOD_SPLIT_CHAR + this.changeMonthValue(vo.getEndCustom()));
        return eo;
    }

    private String changeMonthValue(String monthValue) {
        if (monthValue == null || "".equals(monthValue)) {
            return "0000";
        }
        if (monthValue.length() == 1) {
            return "000" + monthValue;
        }
        if (monthValue.length() == 2) {
            return "00" + monthValue;
        }
        return monthValue;
    }

    private MonitorNrSchemeVO convertEO2VO(MonitorNrSchemeEO eo) {
        String[] periodArr;
        MonitorNrSchemeVO vo = new MonitorNrSchemeVO();
        if (eo == null) {
            return vo;
        }
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setRecid(eo.getId());
        FormSchemeDefine schemeDefine = this.getNrFormScheme(eo.getNrId());
        if (schemeDefine != null) {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(schemeDefine.getTaskKey());
            if (taskDefine != null) {
                vo.setNrTitle(taskDefine.getTitle() + "/" + schemeDefine.getTitle());
            } else {
                vo.setNrTitle(schemeDefine.getTitle());
            }
            vo.setType(Integer.valueOf(schemeDefine.getPeriodType().type()));
            this.PERIOD_SPLIT_CHAR = (char)schemeDefine.getPeriodType().code();
        }
        if (eo.getStartDate() != null && (periodArr = eo.getStartDate().split(String.valueOf(this.PERIOD_SPLIT_CHAR))).length > 1) {
            vo.setStartYear(periodArr[0]);
            vo.setStartCustom(periodArr[1]);
        }
        if (eo.getEndDate() != null && (periodArr = eo.getEndDate().split(String.valueOf(this.PERIOD_SPLIT_CHAR))).length > 1) {
            vo.setEndYear(periodArr[0]);
            vo.setEndCustom(periodArr[1]);
        }
        return vo;
    }

    private PeriodType getNrFormSchemeType(String nrSchemeId) {
        FormSchemeDefine queryFormSchemeDefine = this.getNrFormScheme(nrSchemeId);
        if (queryFormSchemeDefine != null) {
            return queryFormSchemeDefine.getPeriodType();
        }
        return null;
    }

    private FormSchemeDefine getNrFormScheme(String nrSchemeId) {
        return this.iRunTimeViewController.getFormScheme(nrSchemeId);
    }

    private void checkVO(MonitorNrSchemeVO vo) {
        if (vo.getMonitorId() == null) {
            throw new BusinessRuntimeException("\u76d1\u63a7\u7b56\u7565\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (vo.getNrId() == null) {
            throw new BusinessRuntimeException("\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveMonitorNrScheme(List<MonitorNrSchemeVO> nrSchemeList) {
        if (nrSchemeList != null && nrSchemeList.size() > 0) {
            nrSchemeList.forEach(item -> this.saveMonitorNrScheme((MonitorNrSchemeVO)item));
        }
    }

    private void checkVO(List<MonitorNrSchemeVO> nrSchemeList) {
        if (nrSchemeList != null && nrSchemeList.size() > 0) {
            nrSchemeList.forEach(item -> this.checkVO((MonitorNrSchemeVO)item));
        }
    }

    private MonitorVO getMonitorSchemeVO(MonitorSaveInfoVO monitorSaveVo) {
        MonitorVO vo = new MonitorVO();
        vo.setRecid(monitorSaveVo.getRecid());
        vo.setGroupId(monitorSaveVo.getGroupId());
        vo.setCode(monitorSaveVo.getCode());
        if (monitorSaveVo.getTitle() == null || monitorSaveVo.getTitle().trim().length() == 0) {
            vo.setLabel(monitorSaveVo.getLabel());
        } else {
            vo.setLabel(monitorSaveVo.getTitle());
        }
        vo.setShowNode(monitorSaveVo.getShowNode());
        return vo;
    }

    private void setMonitorSchemeVO(MonitorVO vo, MonitorSaveInfoVO monitorSaveVo) {
        monitorSaveVo.setRecid(vo.getRecid());
        monitorSaveVo.setGroupId(vo.getGroupId());
        monitorSaveVo.setTitle(vo.getLabel());
        monitorSaveVo.setCode(vo.getCode());
        monitorSaveVo.setShowNode(vo.getShowNode());
    }

    @Override
    public MonitorVO saveScheme(MonitorVO vo) {
        this.checkVO(vo);
        if (!StringUtils.isEmpty((CharSequence)vo.getRecid())) {
            return this.convertEO2VO(this.doUpdateScheme(this.convertVO2EO(vo)));
        }
        return this.convertEO2VO(this.doSaveScheme(this.convertVO2EO(vo)));
    }

    private MonitorVO convertEO2VO(MonitorSchemeEO eo) {
        MonitorVO vo = new MonitorVO();
        vo.setRecid(eo.getId());
        vo.setGroupId(eo.getGroupId());
        vo.setCode(eo.getSchemeCode());
        vo.setKey(eo.getId().toString());
        vo.setLabel(eo.getSchemeName());
        vo.setShowNode(eo.getShowNode());
        vo.setCreatorId(eo.getCreatorId());
        vo.setCreateTime(eo.getCreateTime());
        vo.setModifierId(eo.getModifierId());
        vo.setModifyTime(eo.getModifyTime());
        return vo;
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorSchemeEO doSaveScheme(MonitorSchemeEO eo) {
        this.beforeSave(eo);
        this.configDao.save(eo);
        return eo;
    }

    private void beforeSave(MonitorSchemeEO eo) {
        eo.setId(UUID.randomUUID().toString());
        eo.setCreateTime(new Date());
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            eo.setCreatorId(loginUser.getId());
        }
        eo.setSortOrder(MonitorUtil.newOrder());
    }

    private MonitorSchemeEO convertVO2EO(MonitorVO vo) {
        MonitorSchemeEO eo = new MonitorSchemeEO();
        eo.setId(vo.getRecid());
        eo.setGroupId(vo.getGroupId());
        eo.setSchemeCode(vo.getCode());
        eo.setSchemeName(vo.getLabel());
        eo.setShowNode(vo.getShowNode());
        return eo;
    }

    private void checkVO(MonitorVO vo) {
        if (vo.getCode() == null || vo.getCode().trim().length() == 0) {
            throw new BusinessRuntimeException("\u7b56\u7565\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (vo.getLabel() == null || vo.getLabel().trim().length() == 0) {
            throw new BusinessRuntimeException("\u7b56\u7565\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (vo.getRecid() == null && !this.configDao.checkMonitorSchemeCode(vo.getCode()).booleanValue()) {
            throw new BusinessRuntimeException("\u6807\u8bc6\u91cd\u590d\uff0c\u8bf7\u4fee\u6539\u6807\u8bc6");
        }
    }

    @Override
    public MonitorVO updateScheme(MonitorVO vo) {
        this.checkVO(vo);
        return this.convertEO2VO(this.doUpdateScheme(this.convertVO2EO(vo)));
    }

    @Transactional(rollbackFor={Exception.class})
    MonitorSchemeEO doUpdateScheme(MonitorSchemeEO eo) {
        this.beforeUpdate(eo);
        this.configDao.update((BaseEntity)eo);
        return eo;
    }

    private void beforeUpdate(MonitorSchemeEO eo) {
        MonitorSchemeEO oldEO = (MonitorSchemeEO)this.configDao.get((Serializable)((Object)eo.getId()));
        oldEO.setModifyTime(new Date());
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (loginUser != null) {
            oldEO.setModifierId(loginUser.getId());
        }
        oldEO.setSchemeName(eo.getSchemeName());
        oldEO.setShowNode(eo.getShowNode());
        eo = oldEO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public MonitorVO deleteScheme(String id) {
        MonitorSchemeEO monitorSchemeEO = (MonitorSchemeEO)this.configDao.get((Serializable)((Object)id));
        MonitorVO vo = this.convertEO2VO(monitorSchemeEO);
        this.configSchemeDao.deleteNrSchemeByMonitorId(id);
        List<MonitorConfigDetailEO> configNodeList = this.configNodeDao.findConfigDetailByMonitorId(id);
        if (configNodeList != null && configNodeList.size() > 0) {
            for (MonitorConfigDetailEO tempConfigNode : configNodeList) {
                this.configNodeItemDao.deleteConfigDetailItemByConfigId(tempConfigNode.getId());
            }
            this.configNodeDao.deleteBySchemeId(id);
        }
        this.configDao.delete((BaseEntity)monitorSchemeEO);
        return vo;
    }

    @Override
    public MonitorVO getScheme(String id) {
        return this.convertEO2VO((MonitorSchemeEO)this.configDao.get((Serializable)((Object)id)));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public MonitorNrSchemeVO deleteMonitorNrScheme(String id) {
        MonitorNrSchemeEO eo = (MonitorNrSchemeEO)this.configSchemeDao.get((Serializable)((Object)id));
        MonitorNrSchemeVO vo = this.convertEO2VO(eo);
        this.configSchemeDao.delete((BaseEntity)eo);
        return vo;
    }

    @Override
    public List<MonitorNrSchemeVO> getMonitorNrSchemeByMonitorId(String monitotId) {
        List<MonitorNrSchemeEO> eoList = this.configSchemeDao.getNrSchemeByMonitorId(monitotId);
        return eoList.stream().map(this::convertEO2VO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorConfigDetailVO> getMonitorConfigDetailsByMonitorId(String monitotId) {
        List<MonitorConfigDetailEO> eoList = this.configNodeDao.findConfigDetailByMonitorId(monitotId);
        return eoList.stream().map(this::convertEO2VO).collect(Collectors.toList());
    }

    @Override
    public MonitorConfigDetailVO getMonitorConfigDetail(String monitorId, String nodeCode) {
        MonitorConfigDetailEO eo = this.configNodeDao.findConfigDetailByNodecode(monitorId, nodeCode);
        return this.changeEO2VO(eo, monitorId, nodeCode);
    }

    private MonitorConfigDetailVO changeEO2VO(MonitorConfigDetailEO eo, String monitorId, String nodeCode) {
        if (eo != null) {
            MonitorConfigDetailVO vo = this.convertEO2VO(eo);
            vo.setTableData(this.getConfigItems(eo.getId()));
            return vo;
        }
        MonitorSceneEnum sceneNode = MonitorSceneEnum.getInstance((String)nodeCode);
        if (sceneNode != null) {
            MonitorConfigDetailVO vo = new MonitorConfigDetailVO();
            vo.setMonitorId(monitorId);
            vo.setNodeCode(nodeCode);
            vo.setChecked(Integer.valueOf(0));
            vo.setcLeafNodeFlag(Integer.valueOf(0));
            vo.setReNodeTitle(sceneNode.getName());
            vo.setTableData(this.getAllNodeItems(monitorId, nodeCode, null));
        }
        return null;
    }

    @Override
    public List<MonitorConfigDetailItemVO> getConfigItems(String configId) {
        ArrayList<MonitorConfigDetailItemVO> resultList = new ArrayList<MonitorConfigDetailItemVO>();
        List<MonitorConfigDetailItemEO> eoList = this.configNodeItemDao.getConfigDetailItemByConfigId(configId);
        if (eoList != null && eoList.size() > 0) {
            for (MonitorConfigDetailItemEO tmpEO : eoList) {
                resultList.add(this.convertEO2VO(tmpEO));
            }
        } else {
            return this.getAllNodeItems(configId);
        }
        return resultList;
    }

    @Override
    public List<MonitorConfigDetailItemVO> getConfigItems(String monitorId, String nodeCode) {
        MonitorConfigDetailEO eo = this.configNodeDao.findConfigDetailByNodecode(monitorId, nodeCode);
        if (eo != null) {
            return this.getConfigItems(eo.getId());
        }
        return this.getAllNodeItems(monitorId, nodeCode, null);
    }

    private List<MonitorConfigDetailItemVO> getAllNodeItems(String configId) {
        MonitorConfigDetailEO cpmfigDetailEO = (MonitorConfigDetailEO)this.configNodeDao.get((Serializable)((Object)configId));
        if (cpmfigDetailEO != null) {
            return this.getAllNodeItems(cpmfigDetailEO.getMonitorId(), cpmfigDetailEO.getNodeCode(), configId);
        }
        return null;
    }

    private List<MonitorConfigDetailItemVO> getAllNodeItems(String monitorId, String nodeCode, String configId) {
        return this.getAllNodeItems(monitorId, nodeCode, configId, null);
    }

    private List<MonitorConfigDetailItemVO> getAllNodeItems(String monitorId, String nodeCode, String configId, Map<String, String> stateNameMap) {
        ArrayList<MonitorConfigDetailItemVO> resultList = new ArrayList<MonitorConfigDetailItemVO>();
        MonitorSceneEnum sceneNode = MonitorSceneEnum.getInstance((String)nodeCode);
        MonitorStateEnum[] monitorStates = null;
        if (sceneNode == null) {
            MonitorSceneGroupEnum sceneGroupNode = MonitorSceneGroupEnum.getInstance((String)nodeCode);
            if (sceneGroupNode != null) {
                monitorStates = sceneGroupNode.getMonitorStates();
            }
        } else {
            monitorStates = sceneNode.getMonitorStates();
        }
        if (monitorStates != null && monitorStates.length > 0) {
            for (MonitorStateEnum tempState : monitorStates) {
                MonitorConfigDetailItemVO vo = new MonitorConfigDetailItemVO();
                vo.setConfigId(configId);
                vo.setMonitorId(monitorId);
                String nickName = tempState.getName();
                if (!MapUtils.isEmpty(stateNameMap)) {
                    if (tempState == MonitorStateEnum.SUBMITTED_NOT && !StringUtils.isEmpty((CharSequence)stateNameMap.get(ActionStateEnum.ORIGINAL_SUBMIT.getStateCode()))) {
                        nickName = stateNameMap.get(ActionStateEnum.ORIGINAL_SUBMIT.getStateCode());
                    }
                    if (tempState == MonitorStateEnum.SUBMITTED_IS && !StringUtils.isEmpty((CharSequence)stateNameMap.get(ActionStateEnum.SUBMITED.getStateCode()))) {
                        nickName = stateNameMap.get(ActionStateEnum.SUBMITED.getStateCode());
                    }
                    if (tempState == MonitorStateEnum.RETURNED_NOT_IS && !StringUtils.isEmpty((CharSequence)stateNameMap.get(ActionStateEnum.RETURNED.getStateCode()))) {
                        nickName = stateNameMap.get(ActionStateEnum.RETURNED.getStateCode());
                    }
                    if (tempState == MonitorStateEnum.UPLOAD_NOT && !StringUtils.isEmpty((CharSequence)stateNameMap.get(ActionStateEnum.ORIGINAL_UPLOAD.getStateCode()))) {
                        nickName = stateNameMap.get(ActionStateEnum.ORIGINAL_UPLOAD.getStateCode());
                    }
                    if (tempState == MonitorStateEnum.UPLOAD_IS && !StringUtils.isEmpty((CharSequence)stateNameMap.get(ActionStateEnum.UPLOADED.getStateCode()))) {
                        nickName = stateNameMap.get(ActionStateEnum.UPLOADED.getStateCode());
                    }
                    if (tempState == MonitorStateEnum.REJECT_NOT_IS && !StringUtils.isEmpty((CharSequence)stateNameMap.get(ActionStateEnum.REJECTED.getStateCode()))) {
                        nickName = stateNameMap.get(ActionStateEnum.REJECTED.getStateCode());
                    }
                }
                vo.setNodeStateCode(tempState.getCode() + "/" + nickName);
                vo.setFlag(tempState.getCode());
                vo.setcNodeNewName(nickName);
                resultList.add(vo);
            }
        }
        return resultList;
    }

    @Override
    public List<MonitorSceneNodeInfoVO> getNodeChilds(String nodeCode) {
        ArrayList<MonitorSceneNodeInfoVO> resultList = new ArrayList<MonitorSceneNodeInfoVO>();
        List<MonitorScene> monitorSceneList = MonitorSceneCollectUtils.getAllMonitorScene();
        for (MonitorScene tempScene : monitorSceneList) {
            if (!tempScene.getMonitorScene().getSceneGroup().getCode().equals(nodeCode)) continue;
            MonitorSceneNodeInfoVO vo = new MonitorSceneNodeInfoVO();
            vo.setName(tempScene.getMonitorScene().getCode());
            vo.setGroupTitle(tempScene.getMonitorScene().getSceneGroup().getName());
            vo.setValue(tempScene.getMonitorScene().getCode());
            vo.setLabel(tempScene.getMonitorScene().getName());
            MonitorStateEnum[] stateList = tempScene.getMonitorScene().getMonitorStates();
            ArrayList<MonitorState> states = new ArrayList<MonitorState>();
            ArrayList<MonitorSceneNodeInfoVO> childs = new ArrayList<MonitorSceneNodeInfoVO>();
            for (MonitorStateEnum tempState : stateList) {
                MonitorState monitorState = new MonitorState(tempState.getCode().intValue(), tempState.getName());
                states.add(monitorState);
                MonitorSceneNodeInfoVO vop = new MonitorSceneNodeInfoVO();
                vop.setValue(monitorState.getValue() + "");
                vop.setLabel(monitorState.getTitle());
                vop.setChildren(new ArrayList());
                childs.add(vop);
            }
            vo.setStates(states);
            vo.setChildren(childs);
            resultList.add(vo);
        }
        return resultList;
    }

    @Override
    public MonitorSceneNodeInfoVO getNodeInfo(String nodeCode) {
        MonitorSceneGroupEnum tempSceneGroup = MonitorSceneGroupEnum.getInstance((String)nodeCode);
        if (tempSceneGroup != null) {
            tempSceneGroup = MonitorSceneCollectUtils.getSceneGroup(nodeCode);
            if (tempSceneGroup != null) {
                MonitorSceneNodeInfoVO vo = new MonitorSceneNodeInfoVO();
                vo.setName(tempSceneGroup.getCode());
                vo.setValue(tempSceneGroup.getCode());
                vo.setLabel(tempSceneGroup.getName());
                vo.setStates(this.getMonitorState(tempSceneGroup.getMonitorStates()));
                vo.setChildren(this.getNodeChilds(nodeCode));
                return vo;
            }
        } else {
            MonitorScene monitorScene;
            MonitorSceneEnum tempScene = MonitorSceneEnum.getInstance((String)nodeCode);
            if (tempScene != null && (monitorScene = MonitorSceneCollectUtils.getMonitorScene(nodeCode)) != null) {
                MonitorSceneNodeInfoVO vo = new MonitorSceneNodeInfoVO();
                vo.setName(monitorScene.getMonitorScene().getCode());
                vo.setGroupTitle(monitorScene.getMonitorScene().getSceneGroup().getName());
                vo.setValue(monitorScene.getMonitorScene().getCode());
                vo.setLabel(monitorScene.getMonitorScene().getName());
                vo.setStates(this.getMonitorState(monitorScene.getMonitorScene().getMonitorStates()));
                vo.setChildren(new ArrayList());
                return vo;
            }
        }
        return null;
    }

    private List<MonitorState> getMonitorState(MonitorStateEnum[] stateList) {
        return this.getMonitorState(stateList, null);
    }

    private List<MonitorState> getMonitorState(MonitorStateEnum[] stateList, Map<String, String> nickNameMap) {
        ArrayList<MonitorState> states = new ArrayList<MonitorState>();
        for (MonitorStateEnum tempState : stateList) {
            String nickName = tempState.getName();
            if (!MapUtils.isEmpty(nickNameMap)) {
                if (tempState == MonitorStateEnum.SUBMITTED_NOT && !StringUtils.isEmpty((CharSequence)nickNameMap.get(ActionStateEnum.ORIGINAL_SUBMIT.getStateCode()))) {
                    nickName = nickNameMap.get(ActionStateEnum.ORIGINAL_SUBMIT.getStateCode());
                }
                if (tempState == MonitorStateEnum.SUBMITTED_IS && !StringUtils.isEmpty((CharSequence)nickNameMap.get(ActionStateEnum.SUBMITED.getStateCode()))) {
                    nickName = nickNameMap.get(ActionStateEnum.SUBMITED.getStateCode());
                }
                if (tempState == MonitorStateEnum.RETURNED_NOT_IS && !StringUtils.isEmpty((CharSequence)nickNameMap.get(ActionStateEnum.RETURNED.getStateCode()))) {
                    nickName = nickNameMap.get(ActionStateEnum.RETURNED.getStateCode());
                }
                if (tempState == MonitorStateEnum.UPLOAD_NOT && !StringUtils.isEmpty((CharSequence)nickNameMap.get(ActionStateEnum.ORIGINAL_UPLOAD.getStateCode()))) {
                    nickName = nickNameMap.get(ActionStateEnum.ORIGINAL_UPLOAD.getStateCode());
                }
                if (tempState == MonitorStateEnum.UPLOAD_IS && !StringUtils.isEmpty((CharSequence)nickNameMap.get(ActionStateEnum.UPLOADED.getStateCode()))) {
                    nickName = nickNameMap.get(ActionStateEnum.UPLOADED.getStateCode());
                }
                if (tempState == MonitorStateEnum.REJECT_NOT_IS && !StringUtils.isEmpty((CharSequence)nickNameMap.get(ActionStateEnum.REJECTED.getStateCode()))) {
                    nickName = nickNameMap.get(ActionStateEnum.REJECTED.getStateCode());
                }
            }
            MonitorState monitorState = new MonitorState(tempState.getCode().intValue(), nickName);
            states.add(monitorState);
        }
        return states;
    }

    @Override
    public List<MonitorSceneNodeInfoVO> getMonitorNodesNoTree() {
        ArrayList<MonitorSceneNodeInfoVO> resutlList = new ArrayList<MonitorSceneNodeInfoVO>();
        List<MonitorSceneGroupEnum> sceneGroups = MonitorSceneCollectUtils.getSceneGroups();
        for (MonitorSceneGroupEnum tempGroup : sceneGroups) {
            MonitorSceneNodeInfoVO vo = new MonitorSceneNodeInfoVO();
            vo.setName(tempGroup.getCode());
            vo.setExpand(Boolean.valueOf(false));
            vo.setStates(this.getMonitorState(tempGroup.getMonitorStates()));
            vo.setLabel(tempGroup.getName());
            resutlList.add(vo);
            List<MonitorSceneEnum> scenes = MonitorSceneCollectUtils.getScenes(tempGroup);
            for (MonitorSceneEnum tempScene : scenes) {
                MonitorSceneNodeInfoVO tempVO = new MonitorSceneNodeInfoVO();
                tempVO.setName(tempScene.getCode());
                tempVO.setExpand(Boolean.valueOf(false));
                tempVO.setStates(this.getMonitorState(tempScene.getMonitorStates()));
                tempVO.setLabel(tempScene.getName());
                resutlList.add(tempVO);
            }
        }
        return resutlList;
    }

    @Override
    public List<NrSchemesVO> getBoundFormSchemes() {
        ArrayList<NrSchemesVO> list = new ArrayList<NrSchemesVO>();
        Map<String, MonitorNrSchemeEO> schemeEOMap = this.configSchemeDao.loadAll().stream().filter(monitorNrSchemeEO -> StringUtils.isNotEmpty((CharSequence)monitorNrSchemeEO.getNrId())).collect(Collectors.toMap(MonitorNrSchemeEO::getNrId, o -> o));
        List<NrSchemesVO> nrSchemes = null;
        try {
            nrSchemes = this.getNrSchemes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (NrSchemesVO nrScheme : nrSchemes) {
            List children = nrScheme.getChildren();
            Optional<NrSchemesVO> any = children.stream().filter(nrSchemesVO -> schemeEOMap.containsKey(nrSchemesVO.getValue())).findAny();
            if (!any.isPresent()) continue;
            List collect = children.stream().filter(nrSchemesVO -> schemeEOMap.containsKey(nrSchemesVO.getValue())).collect(Collectors.toList());
            nrScheme.setChildren(collect);
            list.add(nrScheme);
        }
        return list;
    }

    @Override
    public List<NrSchemesVO> getNrSchemes() throws Exception {
        ArrayList<NrSchemesVO> list = new ArrayList<NrSchemesVO>();
        List allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        for (int i = 0; i < allTaskDefines.size(); ++i) {
            NrSchemesVO voi = new NrSchemesVO();
            TaskDefine taskDefine = (TaskDefine)allTaskDefines.get(i);
            voi.setValue(taskDefine.getKey());
            voi.setLabel(taskDefine.getTitle());
            ArrayList<NrSchemesVO> arrayList = new ArrayList<NrSchemesVO>();
            voi.setChildren(arrayList);
            List formSchemeDefines = this.runTimeAuthViewController.queryFormSchemeByTask(taskDefine.getKey());
            for (int j = 0; j < formSchemeDefines.size(); ++j) {
                FormSchemeDefine formSchemeDefine = (FormSchemeDefine)formSchemeDefines.get(j);
                try {
                    String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)formSchemeDefine.getKey());
                    NrSchemesVO voj = new NrSchemesVO();
                    voj.setValue(formSchemeDefine.getKey());
                    voj.setLabel(formSchemeDefine.getTitle());
                    voj.setType(formSchemeDefine.getPeriodType().type());
                    voj.setStartDate(fromToPeriodByFormSchemeKey[0]);
                    voj.setEndDate(fromToPeriodByFormSchemeKey[1]);
                    voj.setChildren(new ArrayList());
                    voj.setOffset(formSchemeDefine.getPeriodOffset());
                    arrayList.add(voj);
                    continue;
                }
                catch (Exception e) {
                    LogHelper.error((String)"\u5408\u5e76\u76d1\u63a7", (String)("\u83b7\u53d6\u4efb\u52a1\uff1a" + taskDefine.getTitle() + ",\u4e0b\u4efb\u52a1\uff1a" + formSchemeDefine.getTitle() + ",\u5f02\u5e38"), (String)e.getMessage());
                }
            }
            list.add(voi);
        }
        return list;
    }

    @Override
    public List<MonitorShowDataVO> unitFilter() {
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance();
        GcOrgTypeVerCacheTool typeVerService = orgService.getTypeVerInstance();
        List orgTypes = typeVerService.listOrgType();
        ArrayList<MonitorShowDataVO> result = new ArrayList<MonitorShowDataVO>();
        if (orgTypes != null && orgTypes.size() > 0) {
            for (int i = 0; i < orgTypes.size(); ++i) {
                OrgTypeVO orgType = (OrgTypeVO)orgTypes.get(i);
                MonitorShowDataVO root = new MonitorShowDataVO();
                root.setOrgId(orgType.getId().toString());
                root.setLabel(orgType.getName());
                ArrayList<MonitorShowDataVO> orgs = new ArrayList<MonitorShowDataVO>();
                root.setChildren(orgs);
                result.add(root);
                YearPeriodObject yp = new YearPeriodObject(null, MAX_DATE);
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType.getName(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
                List<GcOrgCacheVO> orgTree = this.getOrgTree(tool);
                if (orgTree == null || orgTree.size() <= 0) continue;
                for (int j = 0; j < orgTree.size(); ++j) {
                    GcOrgCacheVO org = orgTree.get(j);
                    orgs.add(new MonitorShowDataVO(org));
                }
            }
        }
        return result;
    }

    private List<GcOrgCacheVO> getOrgTree(GcOrgCenterService orgService) {
        List tree = orgService.getOrgTree();
        return tree;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveCopyScheme(MonitorSchemeCopyVO copyVo) {
        this.checkVO(copyVo);
        MonitorSchemeEO eo = (MonitorSchemeEO)this.configDao.get((Serializable)((Object)copyVo.getRecid()));
        eo.setSchemeCode(copyVo.getCode());
        eo.setSchemeName(copyVo.getLabel());
        eo.setGroupId(copyVo.getGroup());
        eo.setModifierId(null);
        eo.setId(null);
        eo = this.doSaveScheme(eo);
        List<MonitorConfigDetailEO> configList = this.configNodeDao.findConfigDetailByMonitorId(copyVo.getRecid());
        for (MonitorConfigDetailEO configEo : configList) {
            String oldConfigId = configEo.getId();
            configEo.setMonitorId(eo.getId());
            configEo.setId(null);
            configEo = this.doSaveMonitorNodeScheme(configEo);
            List<MonitorConfigDetailItemEO> configDetail = this.configNodeItemDao.getConfigDetailItemByConfigId(oldConfigId);
            for (MonitorConfigDetailItemEO itemEo : configDetail) {
                itemEo.setConfigId(configEo.getId());
                itemEo.setId(null);
                this.configNodeItemDao.save(itemEo);
            }
        }
        return eo.getId();
    }

    @Override
    public List<MonitorSceneNodeInfoVO> getMonitorSceneNodes(String formSchemeId) {
        ArrayList<MonitorSceneNodeInfoVO> ret = new ArrayList();
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeId);
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        boolean submit = flowsSetting.isUnitSubmitForCensorship();
        List<Object> sceneNodes = MonitorSceneCollectUtils.getAllMonitorScene();
        Map renameMap = this.actionAlias.nodeAndNodeName(formSchemeId);
        Map stateNameMap = this.actionAlias.actionStateCodeAndStateName(formSchemeId);
        if (!submit) {
            sceneNodes = sceneNodes.stream().filter(monitorScene -> !monitorScene.getMonitorScene().equals((Object)MonitorSceneEnum.NODE_SUBMITTED)).sorted(Comparator.comparingInt(MonitorScene::getOrder)).collect(Collectors.toList());
        }
        ret = sceneNodes.stream().filter(monitorScene -> !monitorScene.getMonitorScene().equals((Object)MonitorSceneEnum.NODE_DATA_PICK)).sorted(Comparator.comparingInt(MonitorScene::getOrder)).map(monitorScene -> {
            MonitorSceneNodeInfoVO tempVO = new MonitorSceneNodeInfoVO();
            MonitorSceneEnum sceneEnum = monitorScene.getMonitorScene();
            String label = sceneEnum.getName();
            if (renameMap != null) {
                if (sceneEnum == MonitorSceneEnum.NODE_UPLOAD && renameMap.get("tsk_upload") != null) {
                    label = (String)renameMap.get("tsk_upload");
                }
                if (sceneEnum == MonitorSceneEnum.NODE_SUBMITTED && renameMap.get("tsk_submit") != null) {
                    label = (String)renameMap.get("tsk_submit");
                }
            }
            tempVO.setName(sceneEnum.getCode());
            tempVO.setValue(sceneEnum.getCode());
            tempVO.setStates(this.getMonitorState(sceneEnum.getMonitorStates(), stateNameMap));
            tempVO.setLabel(label);
            tempVO.setConfigDataModel(this.getConfigDataModelWithNodeOrgType(sceneEnum, label, stateNameMap));
            return tempVO;
        }).collect(Collectors.toList());
        return ret;
    }

    private void checkVO(MonitorSchemeCopyVO vo) {
        if (vo.getCode() == null || vo.getCode().trim().length() == 0) {
            throw new BusinessRuntimeException("\u65b0\u7b56\u7565\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (vo.getLabel() == null || vo.getLabel().trim().length() == 0) {
            throw new BusinessRuntimeException("\u65b0\u7b56\u7565\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (!this.configDao.checkMonitorSchemeCode(vo.getCode()).booleanValue()) {
            throw new BusinessRuntimeException("\u6807\u8bc6\u91cd\u590d\uff0c\u8bf7\u4fee\u6539\u6807\u8bc6");
        }
    }
}

