/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.monitor.api.MonitorExeClient
 *  com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO
 *  com.jiuqi.np.common.exception.JQException
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.monitor.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.monitor.api.MonitorExeClient;
import com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO;
import com.jiuqi.gcreport.monitor.impl.service.MonitorExeService;
import com.jiuqi.np.common.exception.JQException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class MonitorExeControl
implements MonitorExeClient {
    @Autowired
    private MonitorExeService monitorExeService;

    public BusinessResponseEntity<List<MonitorExeSchemeVO>> getMonitorExeSchemes() {
        return BusinessResponseEntity.ok(this.monitorExeService.getMonitorExeSchemes());
    }

    public BusinessResponseEntity<List<ValueAndLabelVO>> getNrSchemes() throws JQException {
        return BusinessResponseEntity.ok(this.monitorExeService.getNrSchemes());
    }

    public BusinessResponseEntity<List<ValueAndLabelVO>> getNrUnitType() {
        return BusinessResponseEntity.ok(this.monitorExeService.getNrUnitType());
    }

    public BusinessResponseEntity<List<ValueAndLabelVO>> getMonitorNodes() {
        return BusinessResponseEntity.ok(this.monitorExeService.getMonitorNodes());
    }

    public BusinessResponseEntity<String> addScheme(@RequestBody MonitorExeSchemeVO vo) {
        Boolean codeFlag;
        if (vo.getRecid() == null && !(codeFlag = this.monitorExeService.checkCode(vo.getCode())).booleanValue()) {
            return BusinessResponseEntity.error((String)"\u6807\u8bc6\u91cd\u590d\uff0c\u8bf7\u4fee\u6539\u6807\u8bc6");
        }
        return BusinessResponseEntity.ok((Object)this.monitorExeService.addScheme(vo));
    }

    public BusinessResponseEntity<MonitorExeSchemeVO> getExeScheme(@PathVariable(name="recid") String recid) {
        return BusinessResponseEntity.ok((Object)this.monitorExeService.getExeScheme(recid));
    }

    public BusinessResponseEntity<String> delScheme(@PathVariable(name="recid") String recid) {
        this.monitorExeService.delScheme(recid);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<String>> getUserConfig() {
        return BusinessResponseEntity.ok(this.monitorExeService.getUserConfig());
    }

    public BusinessResponseEntity<Object> addUserConfig(@RequestBody List<String> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return BusinessResponseEntity.ok();
        }
        return BusinessResponseEntity.ok((Object)this.monitorExeService.saveOrModifyUserConfig(nodes));
    }
}

