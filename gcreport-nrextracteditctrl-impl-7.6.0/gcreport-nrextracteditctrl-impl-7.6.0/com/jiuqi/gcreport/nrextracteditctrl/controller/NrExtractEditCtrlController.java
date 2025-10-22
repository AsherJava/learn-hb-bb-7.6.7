/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.nrextracteditctrl.client.NrExtractEditCtrlClient
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.nrextracteditctrl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.nrextracteditctrl.client.NrExtractEditCtrlClient;
import com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO;
import com.jiuqi.gcreport.nrextracteditctrl.service.NrExtractEditCtrlService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class NrExtractEditCtrlController
implements NrExtractEditCtrlClient {
    @Autowired
    private NrExtractEditCtrlService nrExtractEditCtrlService;

    public BusinessResponseEntity<Boolean> save(NrExtractEditCtrlSaveDTO nrExtractEditCtrlSaveDTO) {
        this.nrExtractEditCtrlService.save(nrExtractEditCtrlSaveDTO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> update(NrExtractEditCtrlSaveDTO nrExtractEditCtrlSaveDTO) {
        this.nrExtractEditCtrlService.update(nrExtractEditCtrlSaveDTO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> delete(String id) {
        this.nrExtractEditCtrlService.delete(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> batchDelete(List<String> ids) {
        this.nrExtractEditCtrlService.batchDelete(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> stop(String id) {
        this.nrExtractEditCtrlService.stop(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> batchStop(List<String> ids) {
        this.nrExtractEditCtrlService.batchStop(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> start(String id) {
        this.nrExtractEditCtrlService.start(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> batchStart(List<String> ids) {
        this.nrExtractEditCtrlService.batchStart(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<NrExtractEditCtrlDTO>> queryAll() {
        return BusinessResponseEntity.ok(this.nrExtractEditCtrlService.queryAll());
    }

    public BusinessResponseEntity<List<FormSchemeParamDTO>> queryAllFormSchemeByTaskId(String taskKey) {
        return BusinessResponseEntity.ok(this.nrExtractEditCtrlService.queryAllFormSchemeByTaskId(taskKey));
    }

    public BusinessResponseEntity<PageInfo<NrExtractEditCtrlDTO>> queryPage(Integer page, Integer size) {
        return BusinessResponseEntity.ok(this.nrExtractEditCtrlService.queryPage(page, size));
    }

    public BusinessResponseEntity<Object> queryFormTreeByFormSchemeKey(String formSchemeKey) {
        return BusinessResponseEntity.ok((Object)this.nrExtractEditCtrlService.queryFormTreeByFormSchemeKey(formSchemeKey));
    }

    public BusinessResponseEntity<List<String>> queryEditableLinkIdListInForm(NrExtractEditCtrlCondi nrExtractEditCtrlCondi, List<String> linkIdList) {
        return BusinessResponseEntity.ok(this.nrExtractEditCtrlService.queryEditableLinkIdListInForm(nrExtractEditCtrlCondi, linkIdList));
    }
}

