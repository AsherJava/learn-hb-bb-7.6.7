/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO;
import com.jiuqi.va.attachment.service.AttachmentBizRecycleBinService;
import com.jiuqi.va.attachment.service.impl.AttachmentBizRecycleBinServiceImpl;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaAttachmentBizRecycleBinController")
@RequestMapping(value={"/bizAttachment/recycle/bin"})
public class AttachmentBizRecycleBinController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizRecycleBinServiceImpl.class);
    @Autowired
    private AttachmentBizRecycleBinService recycleBinService;

    @PostMapping(value={"/listErrorData"})
    R listErrorData(@RequestBody BizAttachmentRecycleBinDTO param) {
        List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOS = this.recycleBinService.listErrorData(param);
        PageVO pageVO = new PageVO();
        pageVO.setRows(bizAttachmentRecycleBinDTOS);
        pageVO.setTotal(this.recycleBinService.getErrorTotal());
        return R.ok().put("data", (Object)pageVO);
    }

    @PostMapping(value={"/listNothingData"})
    R listNothingData(@RequestBody BizAttachmentRecycleBinDTO param) {
        List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOS = this.recycleBinService.listNothingData(param);
        PageVO pageVO = new PageVO();
        pageVO.setRows(bizAttachmentRecycleBinDTOS);
        pageVO.setTotal(this.recycleBinService.getNoThingTotal());
        return R.ok().put("data", (Object)pageVO);
    }

    @PostMapping(value={"/deletes"})
    R deletes(@RequestBody List<BizAttachmentRecycleBinDTO> param) {
        if (this.checkAdmin()) {
            return R.error((String)"\u975e\u7ba1\u7406\u5458\u7981\u6b62\u64cd\u4f5c");
        }
        try {
            if (CollectionUtils.isEmpty(param)) {
                return R.ok();
            }
            return R.ok().put("data", (Object)this.recycleBinService.deleteRecord(param));
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/restore"})
    R restore(@RequestBody List<BizAttachmentRecycleBinDTO> param) {
        if (this.checkAdmin()) {
            return R.error((String)"\u975e\u7ba1\u7406\u5458\u7981\u6b62\u64cd\u4f5c");
        }
        try {
            if (CollectionUtils.isEmpty(param)) {
                return R.ok();
            }
            return R.ok().put("data", (Object)this.recycleBinService.restore(param));
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/markAsNotHandle"})
    R markAsNotHandle(@RequestBody List<BizAttachmentRecycleBinDTO> param) {
        if (this.checkAdmin()) {
            return R.error((String)"\u975e\u7ba1\u7406\u5458\u7981\u6b62\u64cd\u4f5c");
        }
        try {
            return R.ok().put("data", (Object)this.recycleBinService.markAsNotHandle(param));
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    private boolean checkAdmin() {
        UserLoginDTO user = ShiroUtil.getUser();
        return user == null || !"super".equalsIgnoreCase(user.getMgrFlag());
    }
}

