/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelManageClient
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO
 *  com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.impl.model.controller;

import com.jiuqi.bde.bizmodel.client.BizModelManageClient;
import com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO;
import com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.impl.model.gather.IBizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelCategoryService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizModelManageController
implements BizModelManageClient {
    @Autowired
    private BizModelCategoryService bizModelCategoryService;
    @Autowired
    private IBizModelServiceGather bizModelServiceGather;

    @GetMapping(value={"/api/bde/v1/bizModelManage/listCategory"})
    public BusinessResponseEntity<List<BizModelCategoryDTO>> listCategory() {
        return BusinessResponseEntity.ok(this.bizModelCategoryService.listCategory());
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/listAllCategoryAppInfo"})
    public BusinessResponseEntity<List<BizModelCategoryDTO>> listAllCategoryAppInfo() {
        return BusinessResponseEntity.ok(this.bizModelCategoryService.listAllCategoryAppInfo());
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/getBizModelTree"})
    public BusinessResponseEntity<List<BizModelTreeNode>> getBizModelTree(@RequestParam String category) {
        return BusinessResponseEntity.ok(this.bizModelServiceGather.getByCode(category).getBizModelTree());
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/list"})
    public BusinessResponseEntity<String> list(@RequestParam String category) {
        return BusinessResponseEntity.ok((Object)this.bizModelServiceGather.getByCode(category).list());
    }

    @PostMapping(value={"/api/bde/v1/bizModelManage/save/{type}"})
    public BusinessResponseEntity<Object> save(@PathVariable(value="type") String type, @RequestParam String category, @RequestBody String bizModelDtoStr) {
        if ("create".equals(type)) {
            this.bizModelServiceGather.getByCode(category).save(bizModelDtoStr);
            return BusinessResponseEntity.ok((Object)"\u65b0\u589e\u6210\u529f\u3002");
        }
        if ("update".equals(type)) {
            this.bizModelServiceGather.getByCode(category).update(bizModelDtoStr);
            return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f\u3002");
        }
        return BusinessResponseEntity.error((String)"\u64cd\u4f5c\u4e0d\u6b63\u786e\u3002");
    }

    @PostMapping(value={"/api/bde/v1/bizModelManage/start/{id}"})
    public BusinessResponseEntity<Object> start(@PathVariable(value="id") String id, @RequestParam String category) {
        int startCount = this.bizModelServiceGather.getByCode(category).start(id);
        if (startCount != 1) {
            return BusinessResponseEntity.error((String)"\u542f\u7528\u5931\u8d25\u3002");
        }
        return BusinessResponseEntity.ok((Object)"\u542f\u7528\u6210\u529f\u3002");
    }

    @PostMapping(value={"/api/bde/v1/bizModelManage/stop/{id}"})
    public BusinessResponseEntity<Object> stop(@PathVariable(value="id") String id, @RequestParam String category) {
        int stopCount = this.bizModelServiceGather.getByCode(category).stop(id);
        if (stopCount != 1) {
            return BusinessResponseEntity.error((String)"\u505c\u7528\u5931\u8d25\u3002");
        }
        return BusinessResponseEntity.ok((Object)"\u505c\u7528\u6210\u529f\u3002");
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/exchangeOrdinal"})
    public BusinessResponseEntity<Object> exchangeOrdinal(@RequestParam String category, @RequestParam String srcId, @RequestParam String targetId) {
        this.bizModelServiceGather.getByCode(category).exchangeOrdinal(srcId, targetId);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f\u3002");
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/getFetchTypeByBizModelCode/{bizModelCode}"})
    public BusinessResponseEntity<String> getFetchTypeByBizModelCode(@PathVariable(value="bizModelCode") String bizModelCode, @RequestParam String category) {
        return BusinessResponseEntity.ok((Object)this.bizModelServiceGather.getByCode(category).getFetchTypeByBizModelCode(bizModelCode));
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/getDimensionByBizModelCode/{bizModelCode}"})
    public BusinessResponseEntity<String> getDimensionByBizModelCode(@PathVariable(value="bizModelCode") String bizModelCode, @RequestParam String category) {
        return BusinessResponseEntity.ok((Object)this.bizModelServiceGather.getByCode(category).getDimensionByBizModelCode(bizModelCode));
    }

    @GetMapping(value={"/api/bde/v1/bizModelManage/customFetchComboBoxData"})
    public BusinessResponseEntity<CustomFetchFormVO> getCustomFetchFormData(@RequestParam String category) {
        return BusinessResponseEntity.ok((Object)this.bizModelServiceGather.getByCode(category).getCustomFetchFormData());
    }
}

