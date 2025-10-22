/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.monitor.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
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
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.monitor.client.feign.MonitorConfigFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface MonitorConfigClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/monitor/config/";

    @GetMapping(value={"/api/gcreport/v1/monitor/config/useorgtypes"})
    public BusinessResponseEntity<List<ValueAndLabelVO>> getUseOrgTypes();

    @GetMapping(value={"/api/gcreport/v1/monitor/config/monitornodes", "/api/gcreport/v1/monitor/config/monitornodes/{monitorId}"})
    public BusinessResponseEntity<List<MonitorSceneNodeVO>> getMonitorNodes(@PathVariable(name="monitorId", required=false) String var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/monitornodesnotree"})
    public BusinessResponseEntity<List<MonitorSceneNodeInfoVO>> getMonitorNodesNoTree();

    @GetMapping(value={"/api/gcreport/v1/monitor/config/nodechilds/{name}"})
    public BusinessResponseEntity<List<MonitorSceneNodeInfoVO>> getNodeChilds(@PathVariable(name="name") String var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/group"})
    public BusinessResponseEntity<String> addGroup(@RequestBody MonitorGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/update/group"})
    public BusinessResponseEntity<String> modifyGroup(@RequestBody MonitorGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/delete/group/{recid}"})
    public BusinessResponseEntity<Object> delMonitorGroup(@PathVariable(name="recid") String var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/group/{recid}"})
    public BusinessResponseEntity<MonitorGroupVO> getMonitorGroup(@PathVariable(name="recid") String var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/groups"})
    public BusinessResponseEntity<List<ValueAndLabelVO>> getMonitorGroups();

    @GetMapping(value={"/api/gcreport/v1/monitor/config/groupTree"})
    public BusinessResponseEntity<MonitorGroupVO> getMonitorGroupTrees();

    @GetMapping(value={"/api/gcreport/v1/monitor/config/boundTaskAndScheme"})
    public BusinessResponseEntity<List<NrSchemesVO>> getBoundFormSchemes();

    @GetMapping(value={"/api/gcreport/v1/monitor/config/monitorScenes/{formSchemeId}"})
    public BusinessResponseEntity<List<MonitorSceneNodeInfoVO>> getMonitorSceneNodes(@PathVariable(value="formSchemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/scheme"})
    public BusinessResponseEntity<String> addScheme(@RequestBody MonitorVO var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/delete/scheme/{recid}"})
    public BusinessResponseEntity<String> delMonitorScheme(@PathVariable(name="recid") String var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/scheme/{recid}"})
    public BusinessResponseEntity<MonitorVO> getScheme(@PathVariable(name="recid") String var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/schemeinfo"})
    public BusinessResponseEntity<String> addSchemeInfo(@RequestBody MonitorSaveInfoVO var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/schemeinfo/{recid}"})
    public BusinessResponseEntity<MonitorSaveInfoVO> getMonitorScheme(@PathVariable(name="recid") String var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/nrscheme"})
    public BusinessResponseEntity<Object> addMonitorNrScheme(@RequestBody List<MonitorNrSchemeVO> var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/nrscheme/{monitorId}"})
    public BusinessResponseEntity<List<MonitorNrSchemeVO>> getMonitorNrSchemes(@PathVariable(name="monitorId") String var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/config/delete/nrscheme/{recid}"})
    public BusinessResponseEntity<MonitorNrSchemeVO> deleteMonitorNrScheme(@PathVariable(name="recid") String var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/nrscheme/{monitorId}/{nodeCode}"})
    public BusinessResponseEntity<MonitorConfigDetailVO> getMonitorNrSchemes(@PathVariable(name="monitorId") String var1, @PathVariable(name="nodeCode") String var2);

    @GetMapping(value={"/api/gcreport/v1/monitor/config/nrschemes"})
    public BusinessResponseEntity<List<NrSchemesVO>> getNrSchemes() throws Exception;

    @GetMapping(value={"/api/gcreport/v1/monitor/config/unitfilter"})
    public BusinessResponseEntity<List<MonitorShowDataVO>> unitFilter();

    @PostMapping(value={"/api/gcreport/v1/monitor/config/schemecopy"})
    public BusinessResponseEntity<String> schemeCopy(@RequestBody MonitorSchemeCopyVO var1);
}

