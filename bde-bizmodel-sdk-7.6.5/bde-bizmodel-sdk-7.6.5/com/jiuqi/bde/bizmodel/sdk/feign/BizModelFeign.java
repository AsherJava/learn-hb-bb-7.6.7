/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.util.TreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.sdk.feign;

import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.util.TreeNode;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizModelFeign
implements BizModelClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<List<? extends BizModelDTO>> listByCategory(String category) {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listByCategory(category);
    }

    public BusinessResponseEntity<List<BizModelDTO>> list() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).list();
    }

    public BusinessResponseEntity<List<BizModelAllPropsDTO>> listBizModelAllProps() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listBizModelAllProps();
    }

    public BusinessResponseEntity<BizModelDTO> get(String bizModelCode) {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).get(bizModelCode);
    }

    public BusinessResponseEntity<BizModelColumnDefineVO> getColumnDefines(String bizModelCode) {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).getColumnDefines(bizModelCode);
    }

    public BusinessResponseEntity<Map<String, String>> getBaseDataInputConfig() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).getBaseDataInputConfig();
    }

    public BusinessResponseEntity<CustomFetchFormVO> getCustomFetchFormData() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).getCustomFetchFormData();
    }

    public BusinessResponseEntity<List<TreeNode>> queryTreeInitByFetchSourceCode(String bizModelCode) {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).queryTreeInitByFetchSourceCode(bizModelCode);
    }

    public BusinessResponseEntity<Map<String, List<ExtInfoResultVO>>> queryExtInfo(ExtInfoParamVO extInfoParamVO) {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).queryExtInfo(extInfoParamVO);
    }

    public BusinessResponseEntity<List<BizModelDTO>> listByTfv() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listByTfv();
    }

    public BusinessResponseEntity<List<FinBizModelDTO>> listByFin() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listByFin();
    }

    public BusinessResponseEntity<List<BaseDataBizModelDTO>> listByBaseData() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listByBaseData();
    }

    public BusinessResponseEntity<List<CustomBizModelDTO>> listByCustom() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listByCustom();
    }

    @Deprecated
    public BusinessResponseEntity<List<BizModelDTO>> listFetchSource() {
        return ((BizModelClient)this.requestCertifyService.getFeignClient(BizModelClient.class)).listFetchSource();
    }
}

