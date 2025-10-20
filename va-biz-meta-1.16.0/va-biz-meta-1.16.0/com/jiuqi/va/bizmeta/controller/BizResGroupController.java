/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDTO;
import com.jiuqi.va.bizmeta.service.impl.BizResGroupServiceImpl;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/res"})
public class BizResGroupController {
    @Autowired
    private BizResGroupServiceImpl bizResGroupService;

    @GetMapping(value={"/group/list"})
    public R list() {
        return this.bizResGroupService.list();
    }

    @PostMapping(value={"/group/add"})
    public R add(@RequestBody BizResGroupDTO bizResGroupDTO) {
        return this.bizResGroupService.add(bizResGroupDTO);
    }

    @PostMapping(value={"/group/delete"})
    public R delete(@RequestBody BizResGroupDTO bizResGroupDTO) {
        return this.bizResGroupService.delete(bizResGroupDTO);
    }

    @PostMapping(value={"/group/update"})
    public R update(@RequestBody BizResGroupDTO bizResGroupDTO) {
        return this.bizResGroupService.update(bizResGroupDTO);
    }

    @GetMapping(value={"/group/checkName/{name}"})
    public R checkName(@PathVariable String name) {
        return this.bizResGroupService.checkName(name);
    }
}

