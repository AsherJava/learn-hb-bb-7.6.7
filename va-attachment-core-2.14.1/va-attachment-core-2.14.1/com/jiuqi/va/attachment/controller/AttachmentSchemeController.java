/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentConfigItemDO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.dao.VaAttachmentModeDao;
import com.jiuqi.va.attachment.domain.AttachmentConfigItemDO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.service.AttachmentModeService;
import com.jiuqi.va.attachment.service.AttachmentSchemeService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaAttachmentSchemeController")
@RequestMapping(value={"/bizAttachment/scheme"})
public class AttachmentSchemeController {
    @Autowired
    private AttachmentSchemeService attachmentSchemeService;
    @Autowired
    private AttachmentModeService attachmentModeService;
    @Autowired
    private VaAttachmentModeDao attachmentModeDao;
    @Autowired
    private List<AttachmentHandleIntf> attachmentHandleIntfList;
    private static List<Map<String, Object>> attachmentHandleMap = new ArrayList<Map<String, Object>>();

    @PostMapping(value={"/tree"})
    @RequiresPermissions(value={"vaContent:attachment:mgr"})
    PageVO<TreeVO<AttachmentConfigItemDO>> tree(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.tree(attachmentSchemeDO);
    }

    @PostMapping(value={"/get"})
    AttachmentSchemeDO get(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.get(attachmentSchemeDO);
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaContent:attachment:mgr"})
    R add(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        if (attachmentSchemeDO.getStoremode() == null) {
            return R.error((String)"\u5b58\u50a8\u65b9\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.attachmentHandleIntfList != null) {
            for (AttachmentHandleIntf handle : this.attachmentHandleIntfList) {
                if (handle.getStoremode() != attachmentSchemeDO.getStoremode().intValue()) continue;
                handle.processSchemeConfig(attachmentSchemeDO);
                break;
            }
        }
        return this.attachmentSchemeService.add(attachmentSchemeDO);
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaContent:attachment:mgr"})
    R delete(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.delete(attachmentSchemeDO);
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaContent:attachment:mgr"})
    R update(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        AttachmentModeDO attachmentModeDO = new AttachmentModeDO();
        attachmentModeDO.setSchemename(attachmentSchemeDO.getName());
        List modes = this.attachmentModeDao.select(attachmentModeDO);
        for (AttachmentModeDO mode : modes) {
            mode.setStartflag(attachmentSchemeDO.getStartflag());
            this.attachmentModeService.update(null, mode);
        }
        if (this.attachmentHandleIntfList != null) {
            for (AttachmentHandleIntf handle : this.attachmentHandleIntfList) {
                if (handle.getStoremode() != attachmentSchemeDO.getStoremode().intValue()) continue;
                handle.processSchemeConfig(attachmentSchemeDO);
                break;
            }
        }
        return this.attachmentSchemeService.update(attachmentSchemeDO);
    }

    @PostMapping(value={"/connect"})
    R connect(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.connect(attachmentSchemeDO);
    }

    @PostMapping(value={"/checkscheme"})
    R checkScheme(@RequestBody AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.checkScheme(attachmentSchemeDO);
    }

    @GetMapping(value={"/storemode/list"})
    List<Map<String, Object>> checkScheme() {
        if (attachmentHandleMap.isEmpty()) {
            Collections.sort(this.attachmentHandleIntfList, (o1, o2) -> o1.getStoremode() <= o2.getStoremode() ? -1 : 1);
            for (AttachmentHandleIntf handle : this.attachmentHandleIntfList) {
                if (handle.getStoreTitle() == null) continue;
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", handle.getStoremode());
                map.put("name", handle.getStoreTitle());
                map.put("schemeConfig", handle.getSchemeConfigItems());
                map.put("modelConfig", handle.getModelConfigItems());
                map.put("testConnectFlag", handle.testConnectFlag());
                attachmentHandleMap.add(map);
            }
        }
        return attachmentHandleMap;
    }
}

