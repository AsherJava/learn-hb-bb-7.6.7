/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.AssistExtendDimClient
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.sdk.feign;

import com.jiuqi.bde.bizmodel.client.AssistExtendDimClient;
import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistExtendDimFeign
implements AssistExtendDimClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<AssistExtendDimVO> getAssistExtendDimByCode(String code) {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.getAssistExtendDimByCode(code);
    }

    public BusinessResponseEntity<String> save(AssistExtendDimVO assistExtendDimVO) {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.save(assistExtendDimVO);
    }

    public BusinessResponseEntity<String> update(AssistExtendDimVO assistExtendDimVO) {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.update(assistExtendDimVO);
    }

    public BusinessResponseEntity<List<AssistExtendDimVO>> getAllAssistExtendDim() {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.getAllAssistExtendDim();
    }

    public BusinessResponseEntity<List<AssistExtendDimVO>> getAllStartAssistExtendDim() {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.getAllStartAssistExtendDim();
    }

    public BusinessResponseEntity<String> stopAssistExtendDimById(String id) {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.stopAssistExtendDimById(id);
    }

    public BusinessResponseEntity<String> startAssistExtendDimById(String id) {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.startAssistExtendDimById(id);
    }

    public BusinessResponseEntity<List<DataModelColumn>> getBaseDataColumns(String name) {
        AssistExtendDimClient assistExtendDimClient = (AssistExtendDimClient)this.requestCertifyService.getFeignClient(AssistExtendDimClient.class);
        return assistExtendDimClient.getBaseDataColumns(name);
    }
}

