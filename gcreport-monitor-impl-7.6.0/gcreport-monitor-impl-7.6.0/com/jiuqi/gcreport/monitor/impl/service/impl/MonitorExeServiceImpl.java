/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum
 *  com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.monitor.impl.service.impl;

import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum;
import com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO;
import com.jiuqi.gcreport.monitor.impl.config.MonitorSceneCollectUtils;
import com.jiuqi.gcreport.monitor.impl.dao.execute.MonitorExeDao;
import com.jiuqi.gcreport.monitor.impl.dao.execute.MonitorExeUserConfigDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorExeSchemeEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorExeUserConfigEO;
import com.jiuqi.gcreport.monitor.impl.service.MonitorExeService;
import com.jiuqi.gcreport.monitor.impl.util.MonitorUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonitorExeServiceImpl
implements MonitorExeService {
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private MonitorExeDao monitorExeDao;
    @Autowired
    private MonitorExeUserConfigDao monitorExeUserConfigDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignTimeController;
    private String COMMON_PLACEHOLDER = "--";
    private String COMMON_COLOR = "##";

    @Override
    public List<ValueAndLabelVO> getNrSchemes() throws JQException {
        ArrayList<ValueAndLabelVO> list = new ArrayList<ValueAndLabelVO>();
        HashMap<String, ValueAndLabelVO> tmplMap = new HashMap<String, ValueAndLabelVO>(16);
        List allTaskDefines = this.iDesignTimeViewController.getAllTaskDefines();
        for (DesignTaskDefine designTaskDefine : allTaskDefines) {
            List queryFormScheme = this.iDesignTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
            for (DesignFormSchemeDefine designFormSchemeDefine : queryFormScheme) {
                String schemeRecid = designFormSchemeDefine.getKey();
                String schemeTitle = designTaskDefine.getTitle() + "/" + designFormSchemeDefine.getTitle() + "(" + designFormSchemeDefine.getFormSchemeCode() + ")";
                String periodTypeName = designFormSchemeDefine.getPeriodType().type() + "";
                String periodTypeTitle = designFormSchemeDefine.getPeriodType().title();
                ValueAndLabelVO vo = (ValueAndLabelVO)tmplMap.get(periodTypeName);
                if (vo == null) {
                    ValueAndLabelVO parent = new ValueAndLabelVO();
                    parent.setTaskId(designTaskDefine.getKey());
                    parent.setValue(periodTypeName);
                    parent.setLabel(periodTypeTitle);
                    char period = (char)designFormSchemeDefine.getPeriodType().code();
                    parent.setPeriodChar(String.valueOf(period));
                    tmplMap.put(periodTypeName, parent);
                    ArrayList<ValueAndLabelVO> childList = new ArrayList<ValueAndLabelVO>();
                    ValueAndLabelVO child = new ValueAndLabelVO();
                    child.setTaskId(designTaskDefine.getKey());
                    child.setValue(schemeRecid);
                    child.setLabel(schemeTitle);
                    child.setPeriodChar(String.valueOf(period));
                    child.setChildren(new ArrayList());
                    childList.add(child);
                    parent.setChildren(childList);
                    continue;
                }
                List children = vo.getChildren();
                ValueAndLabelVO child = new ValueAndLabelVO();
                children.add(child);
                child.setTaskId(designTaskDefine.getKey());
                child.setValue(schemeRecid);
                child.setLabel(schemeTitle);
                child.setChildren(new ArrayList());
                char period = (char)designFormSchemeDefine.getPeriodType().code();
                child.setPeriodChar(String.valueOf(period));
            }
        }
        for (String key : tmplMap.keySet()) {
            ValueAndLabelVO valueAndLabelVo = (ValueAndLabelVO)tmplMap.get(key);
            list.add(valueAndLabelVo);
        }
        return list;
    }

    @Override
    public List<ValueAndLabelVO> getNrUnitType() {
        List orgTypes = GcOrgPublicTool.getInstance().getTypeVerInstance().listOrgType();
        ArrayList<ValueAndLabelVO> list = new ArrayList<ValueAndLabelVO>();
        if (orgTypes != null && orgTypes.size() > 0) {
            for (int i = 0; i < orgTypes.size(); ++i) {
                OrgTypeVO orgType = (OrgTypeVO)orgTypes.get(i);
                ValueAndLabelVO vo = new ValueAndLabelVO();
                vo.setValue(orgType.getName());
                vo.setLabel(orgType.getTitle());
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public List<ValueAndLabelVO> getMonitorNodes() {
        List<MonitorSceneGroupEnum> allMonitorSceneGroups = MonitorSceneCollectUtils.getSceneGroups();
        ArrayList<ValueAndLabelVO> resultList = new ArrayList<ValueAndLabelVO>();
        allMonitorSceneGroups.forEach(tempSceneGroup -> {
            ValueAndLabelVO vo = new ValueAndLabelVO();
            vo.setValue(tempSceneGroup.getCode());
            vo.setLabel(tempSceneGroup.getName());
            resultList.add(vo);
            List<MonitorSceneEnum> allMonitorScene = MonitorSceneCollectUtils.getScenes(tempSceneGroup);
            allMonitorScene.forEach(tempScene -> {
                ValueAndLabelVO tempVo = new ValueAndLabelVO();
                tempVo.setValue(tempScene.getCode());
                tempVo.setLabel(tempScene.getName());
                resultList.add(tempVo);
            });
        });
        return resultList;
    }

    @Override
    public List<MonitorExeSchemeVO> getMonitorExeSchemes() {
        List<MonitorExeSchemeEO> all = this.monitorExeDao.loadAllBySortOrder();
        ArrayList<MonitorExeSchemeVO> result = new ArrayList<MonitorExeSchemeVO>();
        if (all != null && all.size() > 0) {
            for (int i = 0; i < all.size(); ++i) {
                MonitorExeSchemeEO eo = all.get(i);
                MonitorExeSchemeVO vo = this.convertExeSchemeEo2Vo(eo);
                result.add(vo);
            }
        }
        return result;
    }

    public MonitorExeSchemeVO convertExeSchemeEo2Vo(MonitorExeSchemeEO eo) {
        MonitorExeSchemeVO vo = new MonitorExeSchemeVO();
        vo.setRecid(eo.getId());
        vo.setCode(eo.getCode());
        vo.setLabel(eo.getTitle());
        vo.setTitle(eo.getTitle());
        String nrId = eo.getNrId();
        ArrayList<String> nrScheme = new ArrayList<String>();
        if (nrId != null) {
            DesignFormSchemeDefine queryFormSchemeDefine = this.iDesignTimeViewController.queryFormSchemeDefine(nrId);
            String name = queryFormSchemeDefine.getPeriodType().type() + "";
            nrScheme.add(name);
            nrScheme.add(nrId.toString());
        }
        vo.setNrScheme(nrScheme);
        String showNode = eo.getShowNode();
        List<Object> showNodeList = new ArrayList();
        if (showNode != null && !"".equals(showNode)) {
            String[] showNodeSplit = showNode.split(";");
            showNodeList = Arrays.asList(showNodeSplit);
        }
        vo.setShowNode(showNodeList);
        String monitorNode = eo.getMonitorNode();
        if (monitorNode != null && !"".equals(monitorNode)) {
            List<String> list = Arrays.asList(monitorNode.split(";"));
            vo.setMonitor(list);
        }
        vo.setUnitType(eo.getUnitType());
        return vo;
    }

    public MonitorExeSchemeEO convertExeSchemeVo2Eo(MonitorExeSchemeEO eo, MonitorExeSchemeVO vo, boolean isAdd) {
        List showNode;
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        if (isAdd) {
            eo.setCreateTime(new Date());
            eo.setCreatorId(loginUser.getId());
            eo.setSortOrder(MonitorUtil.newOrder());
            eo.setCode(vo.getCode());
        } else {
            eo.setModifierId(loginUser.getId());
            eo.setModifyTime(new Date());
        }
        eo.setTitle(vo.getLabel());
        List monitor = vo.getMonitor();
        if (monitor != null && monitor.size() > 0) {
            StringBuilder monitorStr = new StringBuilder();
            for (String nodeName : monitor) {
                monitorStr.append(nodeName).append(";");
            }
            eo.setMonitorNode(monitorStr.toString());
        } else {
            eo.setMonitorNode("");
        }
        List nrScheme = vo.getNrScheme();
        if (nrScheme != null && nrScheme.size() == 2) {
            String nrId = (String)nrScheme.get(1);
            eo.setNrId(nrId);
        }
        if ((showNode = vo.getShowNode()) != null && showNode.size() > 0) {
            StringBuilder showNodeStr = new StringBuilder();
            for (String nodeName : showNode) {
                showNodeStr.append(nodeName).append(";");
            }
            eo.setShowNode(showNodeStr.toString());
        } else {
            eo.setShowNode("");
        }
        eo.setUnitType(vo.getUnitType());
        return eo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String addScheme(MonitorExeSchemeVO vo) {
        String recid = vo.getRecid();
        if (recid == null) {
            MonitorExeSchemeEO eo = new MonitorExeSchemeEO();
            this.convertExeSchemeVo2Eo(eo, vo, true);
            Serializable save = this.monitorExeDao.save(eo);
            recid = (String)((Object)save);
        } else {
            MonitorExeSchemeEO eo = (MonitorExeSchemeEO)this.monitorExeDao.get((Serializable)((Object)recid));
            this.convertExeSchemeVo2Eo(eo, vo, false);
            this.monitorExeDao.update((BaseEntity)eo);
        }
        return recid;
    }

    @Override
    public Boolean checkCode(String code) {
        return this.monitorExeDao.checkCode(code);
    }

    @Override
    public MonitorExeSchemeVO getExeScheme(String recid) {
        MonitorExeSchemeEO eo = (MonitorExeSchemeEO)this.monitorExeDao.get((Serializable)((Object)recid));
        MonitorExeSchemeVO vo = this.convertExeSchemeEo2Vo(eo);
        return vo;
    }

    @Override
    public List<String> getUserConfig() {
        String nodes;
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        List<MonitorExeUserConfigEO> findNodesByUserId = this.monitorExeUserConfigDao.findNodesByUserId(loginUser.getId());
        if (findNodesByUserId != null && findNodesByUserId.size() > 0 && (nodes = findNodesByUserId.get(0).getNodes()) != null && !"".equals(nodes)) {
            List<String> asList = Arrays.asList(nodes.split(";"));
            return asList;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveOrModifyUserConfig(List<String> nodes) {
        String result = null;
        String[] o = new String[nodes.size()];
        String node = String.join((CharSequence)";", nodes.toArray(o));
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String id = loginUser.getId();
        List<MonitorExeUserConfigEO> findNodesByUserId = this.monitorExeUserConfigDao.findNodesByUserId(id);
        if (findNodesByUserId != null && findNodesByUserId.size() > 0) {
            MonitorExeUserConfigEO monitorExeUserConfigEo = findNodesByUserId.get(0);
            monitorExeUserConfigEo.setNodes(node);
            this.monitorExeUserConfigDao.update((BaseEntity)monitorExeUserConfigEo);
            result = monitorExeUserConfigEo.getId();
        } else {
            MonitorExeUserConfigEO entity = new MonitorExeUserConfigEO();
            entity.setUserId(id);
            entity.setNodes(node);
            entity.setSortOrder(MonitorUtil.newOrder());
            Serializable save = this.monitorExeUserConfigDao.save(entity);
            result = (String)((Object)save);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delScheme(String recid) {
        MonitorExeSchemeEO eo = new MonitorExeSchemeEO();
        eo.setId(recid);
        this.monitorExeDao.delete((BaseEntity)eo);
    }
}

