/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.va.bill.bd.core.controller;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapConfigItemDTO;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapGroupDO;
import com.jiuqi.va.bill.bd.core.service.ApplyRegMapGroupService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/bill/applyReg/group"})
public class AppRegGroupController {
    @Autowired
    ApplyRegMapGroupService groupService;

    @RequestMapping(value={"/deleteGroup"})
    @ResponseBody
    public R deleteGroup(@RequestBody ApplyRegMapGroupDO applyRegMapGroupDO) {
        return this.groupService.delete(applyRegMapGroupDO);
    }

    @RequestMapping(value={"/updateGroup"})
    @ResponseBody
    public R updateGroup(@RequestBody ApplyRegMapGroupDO applyRegMapGroupDO) {
        return this.groupService.update(applyRegMapGroupDO);
    }

    @RequestMapping(value={"tree"})
    @ResponseBody
    public PageVO<TreeVO<ApplyRegMapConfigItemDTO>> tree(@RequestBody ApplyRegMapGroupDO applyRegMapGroupDO) {
        return this.groupService.tree(applyRegMapGroupDO);
    }

    @RequestMapping(value={"/createGroup"})
    @ResponseBody
    public R createMapGroup(@RequestBody ApplyRegMapGroupDO applyRegMapGroupDO) {
        return this.groupService.add(applyRegMapGroupDO);
    }
}

