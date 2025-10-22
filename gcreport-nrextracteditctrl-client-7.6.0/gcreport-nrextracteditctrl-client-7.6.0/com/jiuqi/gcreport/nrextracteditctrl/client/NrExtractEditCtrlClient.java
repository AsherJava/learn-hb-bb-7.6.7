/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.nrextracteditctrl.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface NrExtractEditCtrlClient {
    @PostMapping(value={"/api/gcreport/v1/nrextracteditctrl/save"})
    public BusinessResponseEntity<Boolean> save(@RequestBody NrExtractEditCtrlSaveDTO var1);

    @PostMapping(value={"/api/gcreport/v1/nrextracteditctrl/update"})
    public BusinessResponseEntity<Boolean> update(@RequestBody NrExtractEditCtrlSaveDTO var1);

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/delete/{id}"})
    public BusinessResponseEntity<Boolean> delete(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/nrextracteditctrl/batch/delete"})
    public BusinessResponseEntity<Boolean> batchDelete(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/stop/{id}"})
    public BusinessResponseEntity<Boolean> stop(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/nrextracteditctrl/batch/stop"})
    public BusinessResponseEntity<Boolean> batchStop(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/start/{id}"})
    public BusinessResponseEntity<Boolean> start(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/nrextracteditctrl/batch/start"})
    public BusinessResponseEntity<Boolean> batchStart(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/queryAll"})
    public BusinessResponseEntity<List<NrExtractEditCtrlDTO>> queryAll();

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/queryAllFormSchemeByTaskKey"})
    public BusinessResponseEntity<List<FormSchemeParamDTO>> queryAllFormSchemeByTaskId(@RequestParam(value="taskKey") String var1);

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/queryPage"})
    public BusinessResponseEntity<PageInfo<NrExtractEditCtrlDTO>> queryPage(@RequestParam(value="page") Integer var1, @RequestParam(value="size") Integer var2);

    @GetMapping(value={"/api/gcreport/v1/nrextracteditctrl/queryFormTreeByFormSchemeKey/{formSchemeKey}"})
    public BusinessResponseEntity<Object> queryFormTreeByFormSchemeKey(@PathVariable(value="formSchemeKey") String var1);

    @PostMapping(value={"/api/gcreport/v1/nrextracteditctrl/queryEditableLinkIdListInForm"})
    public BusinessResponseEntity<List<String>> queryEditableLinkIdListInForm(NrExtractEditCtrlCondi var1, List<String> var2);
}

