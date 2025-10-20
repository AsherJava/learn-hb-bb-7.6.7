/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.monitor.api.MonitorConfigClient
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
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.monitor.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.monitor.api.MonitorConfigClient;
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
import com.jiuqi.gcreport.monitor.impl.service.MonitorConfigService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class MonitorConfigController
implements MonitorConfigClient {
    @Autowired
    private MonitorConfigService monitorConfigService;

    public BusinessResponseEntity<List<ValueAndLabelVO>> getUseOrgTypes() {
        return BusinessResponseEntity.ok(this.monitorConfigService.getMonitorOrgTypes());
    }

    public BusinessResponseEntity<List<MonitorSceneNodeVO>> getMonitorNodes(@PathVariable(name="monitorId", required=false) String monitorId) {
        return BusinessResponseEntity.ok(this.monitorConfigService.getTreeNodes(monitorId));
    }

    public BusinessResponseEntity<List<MonitorSceneNodeInfoVO>> getMonitorNodesNoTree() {
        return BusinessResponseEntity.ok(this.monitorConfigService.getMonitorNodesNoTree());
    }

    public BusinessResponseEntity<List<MonitorSceneNodeInfoVO>> getNodeChilds(@PathVariable(name="name") String name) {
        return BusinessResponseEntity.ok(this.monitorConfigService.getNodeChilds(name));
    }

    public BusinessResponseEntity<String> addGroup(@RequestBody MonitorGroupVO monitorGroupVo) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.saveMonitorGroup(monitorGroupVo).getRecid());
    }

    public BusinessResponseEntity<String> modifyGroup(@RequestBody MonitorGroupVO monitorGroupVo) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.modifyMonitorGroup(monitorGroupVo).getRecid());
    }

    public BusinessResponseEntity<Object> delMonitorGroup(@PathVariable(name="recid") String recid) {
        this.monitorConfigService.delMonitorGroup(recid);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<MonitorGroupVO> getMonitorGroup(@PathVariable(name="recid") String recid) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.getMonitorGroup(recid));
    }

    public BusinessResponseEntity<List<ValueAndLabelVO>> getMonitorGroups() {
        return BusinessResponseEntity.ok(this.monitorConfigService.getMonitorGroups());
    }

    public BusinessResponseEntity<MonitorGroupVO> getMonitorGroupTrees() {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.getMonitorGroupSchemes());
    }

    public BusinessResponseEntity<List<NrSchemesVO>> getBoundFormSchemes() {
        return BusinessResponseEntity.ok(this.monitorConfigService.getBoundFormSchemes());
    }

    public BusinessResponseEntity<List<MonitorSceneNodeInfoVO>> getMonitorSceneNodes(String formSchemeId) {
        return BusinessResponseEntity.ok(this.monitorConfigService.getMonitorSceneNodes(formSchemeId));
    }

    public BusinessResponseEntity<String> addScheme(@RequestBody MonitorVO vo) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.saveScheme(vo).getRecid());
    }

    public BusinessResponseEntity<String> delMonitorScheme(@PathVariable(name="recid") String recid) {
        this.monitorConfigService.deleteScheme(recid);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<MonitorVO> getScheme(@PathVariable(name="recid") String recid) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.getScheme(recid));
    }

    public BusinessResponseEntity<String> addSchemeInfo(@RequestBody MonitorSaveInfoVO monitorSaveVo) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.addScheme(monitorSaveVo).getRecid());
    }

    public BusinessResponseEntity<MonitorSaveInfoVO> getMonitorScheme(@PathVariable(name="recid") String recid) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.getMonitorSchemeById(recid));
    }

    public BusinessResponseEntity<Object> addMonitorNrScheme(@RequestBody List<MonitorNrSchemeVO> nrSchemeList) {
        this.monitorConfigService.saveMonitorNrScheme(nrSchemeList);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<MonitorNrSchemeVO>> getMonitorNrSchemes(@PathVariable(name="monitorId") String monitorId) {
        return BusinessResponseEntity.ok(this.monitorConfigService.getMonitorNrSchemeByMonitorId(monitorId));
    }

    public BusinessResponseEntity<MonitorNrSchemeVO> deleteMonitorNrScheme(@PathVariable(name="recid") String recid) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.deleteMonitorNrScheme(recid));
    }

    public BusinessResponseEntity<MonitorConfigDetailVO> getMonitorNrSchemes(@PathVariable(name="monitorId") String monitorId, @PathVariable(name="nodeCode") String nodeCode) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.getMonitorConfigDetail(monitorId, nodeCode));
    }

    public BusinessResponseEntity<List<NrSchemesVO>> getNrSchemes() throws Exception {
        return BusinessResponseEntity.ok(this.monitorConfigService.getNrSchemes());
    }

    public BusinessResponseEntity<List<MonitorShowDataVO>> unitFilter() {
        return BusinessResponseEntity.ok(this.monitorConfigService.unitFilter());
    }

    public BusinessResponseEntity<String> schemeCopy(@RequestBody MonitorSchemeCopyVO copyVo) {
        return BusinessResponseEntity.ok((Object)this.monitorConfigService.saveCopyScheme(copyVo));
    }
}

