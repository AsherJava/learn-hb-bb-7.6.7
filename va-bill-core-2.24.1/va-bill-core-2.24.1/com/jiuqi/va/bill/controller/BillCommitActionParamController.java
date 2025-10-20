/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.action.param.ActionParamItem;
import com.jiuqi.va.bill.action.param.CommitActionParam;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/action/commit"})
public class BillCommitActionParamController {
    @Autowired
    private ModelManager modelManager;
    private static final String MODEL_TYPE_KEY = "modelType";
    @Autowired(required=false)
    private List<CommitActionParam> commitActionParam;

    @PostMapping(value={"/params"})
    public R agree(@RequestBody TenantDO tenantDO) {
        if (CollectionUtils.isEmpty(this.commitActionParam)) {
            return R.ok();
        }
        Object modelType = tenantDO.getExtInfo().get(MODEL_TYPE_KEY);
        if (ObjectUtils.isEmpty(modelType)) {
            return R.error();
        }
        ModelType model = (ModelType)this.modelManager.find((String)modelType);
        if (model == null) {
            return R.error((String)"\u6a21\u578b\u4e0d\u5b58\u5728");
        }
        Class modelClass = model.getModelClass();
        ArrayList<ActionParamItem> items = new ArrayList<ActionParamItem>();
        for (CommitActionParam actionParam : this.commitActionParam) {
            if (!actionParam.getDependModel().isAssignableFrom(modelClass)) continue;
            items.addAll(actionParam.getParams());
        }
        return R.ok().put("data", items);
    }
}

