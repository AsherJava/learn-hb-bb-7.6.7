/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.AssistExtendDimClient
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.impl.dimension.controller;

import com.jiuqi.bde.bizmodel.client.AssistExtendDimClient;
import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistExtendDimController
implements AssistExtendDimClient {
    @Autowired
    private AssistExtendDimService assistExtendDimService;

    public BusinessResponseEntity<AssistExtendDimVO> getAssistExtendDimByCode(String code) {
        return BusinessResponseEntity.ok((Object)this.assistExtendDimService.getAssistExtendDimByCode(code));
    }

    public BusinessResponseEntity<String> save(AssistExtendDimVO assistExtendDimVO) {
        this.assistExtendDimService.save(assistExtendDimVO);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<String> update(AssistExtendDimVO assistExtendDimVO) {
        this.assistExtendDimService.update(assistExtendDimVO);
        return BusinessResponseEntity.ok((Object)"\u66f4\u65b0\u6210\u529f");
    }

    public BusinessResponseEntity<List<AssistExtendDimVO>> getAllAssistExtendDim() {
        return BusinessResponseEntity.ok(this.assistExtendDimService.getAllAssistExtendDim());
    }

    public BusinessResponseEntity<List<AssistExtendDimVO>> getAllStartAssistExtendDim() {
        return BusinessResponseEntity.ok(this.assistExtendDimService.getAllStartAssistExtendDim());
    }

    public BusinessResponseEntity<String> stopAssistExtendDimById(String id) {
        this.assistExtendDimService.stopAssistExtendDimById(id);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }

    public BusinessResponseEntity<String> startAssistExtendDimById(String id) {
        this.assistExtendDimService.startAssistExtendDimById(id);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }

    public BusinessResponseEntity<List<DataModelColumn>> getBaseDataColumns(String name) {
        return BusinessResponseEntity.ok(this.assistExtendDimService.getBaseDataColumns(name));
    }
}

