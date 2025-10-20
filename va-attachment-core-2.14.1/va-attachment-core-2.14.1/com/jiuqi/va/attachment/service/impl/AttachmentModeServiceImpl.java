/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizTemplateFileDO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.dao.VaAttachmentBizTemplateFileDao;
import com.jiuqi.va.attachment.dao.VaAttachmentModeDao;
import com.jiuqi.va.attachment.domain.AttachmentBizTemplateFileDO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.service.AttachmentModeService;
import com.jiuqi.va.attachment.service.AttachmentSchemeService;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service(value="AttachmentModeServiceImpl")
public class AttachmentModeServiceImpl
implements AttachmentModeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentModeServiceImpl.class);
    @Autowired
    private VaAttachmentModeDao attachmentModeDao;
    @Autowired
    private VaAttachmentBizTemplateFileDao attachmentBizTemplateFileDao;
    @Autowired
    private AttachmentSchemeService attachmentSchemeService;
    private List<AttachmentHandleIntf> attachmentHandleIntfList;

    private List<AttachmentHandleIntf> getAttachmentHandleIntfList() {
        if (this.attachmentHandleIntfList == null) {
            this.attachmentHandleIntfList = new ArrayList<AttachmentHandleIntf>();
            Map<String, AttachmentHandleIntf> intfMap = ApplicationContextRegister.getApplicationContext().getBeansOfType(AttachmentHandleIntf.class);
            if (!intfMap.isEmpty()) {
                this.attachmentHandleIntfList.addAll(intfMap.values());
            }
        }
        return this.attachmentHandleIntfList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R add(MultipartFile file, AttachmentModeDO attachmentModeDO) {
        R checkR = this.checkModeConfig(attachmentModeDO);
        if (checkR.getCode() != 0) {
            return checkR;
        }
        int num = 0;
        UUID masterId = UUID.randomUUID();
        attachmentModeDO.setId(masterId);
        attachmentModeDO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        attachmentModeDO.setCreatetime(new Date());
        attachmentModeDO.setCreateuser(ShiroUtil.getUser().getId());
        AttachmentModeDO mode = new AttachmentModeDO();
        mode.setName(attachmentModeDO.getName());
        num = this.attachmentModeDao.selectCount(mode);
        if (num > 0) {
            return R.error();
        }
        this.attachmentModeDao.insert(attachmentModeDO);
        if (file != null) {
            byte[] bytes = new byte[]{};
            try {
                bytes = file.getBytes();
            }
            catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            AttachmentBizTemplateFileDO attachmentBizTemplateFileDO = new AttachmentBizTemplateFileDO();
            attachmentBizTemplateFileDO.setId(UUID.randomUUID());
            attachmentBizTemplateFileDO.setMasterId(masterId);
            attachmentBizTemplateFileDO.setName(file.getOriginalFilename());
            attachmentBizTemplateFileDO.setTemplateFile(bytes);
            int result = this.attachmentBizTemplateFileDao.insert(attachmentBizTemplateFileDO);
            if (result == 0) {
                return R.error();
            }
        }
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R update(MultipartFile file, AttachmentModeDO attachmentModeDO) {
        if (attachmentModeDO.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        R checkR = this.checkModeConfig(attachmentModeDO);
        if (checkR.getCode() != 0) {
            return checkR;
        }
        if (attachmentModeDO.getStartflag() == 0 && attachmentModeDO.getDefaultflag() != null) {
            attachmentModeDO.setDefaultflag(null);
        } else if (attachmentModeDO.getDefaultflag() != null) {
            AttachmentModeDO query = new AttachmentModeDO();
            query.setDefaultflag(Integer.valueOf(1));
            List select = this.attachmentModeDao.select(query);
            if (!CollectionUtils.isEmpty(select)) {
                ((AttachmentModeDO)select.get(0)).setDefaultflag(null);
                this.attachmentModeDao.updateByPrimaryKey(select.get(0));
            }
        }
        attachmentModeDO.setModifytime(new Date());
        attachmentModeDO.setModifyuser(ShiroUtil.getUser().getId());
        this.attachmentModeDao.updateByPrimaryKey(attachmentModeDO);
        if (file != null) {
            byte[] bytes = new byte[]{};
            try {
                bytes = file.getBytes();
            }
            catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            AttachmentBizTemplateFileDO attachmentBizTemplateFileDO = new AttachmentBizTemplateFileDO();
            attachmentBizTemplateFileDO.setMasterId(attachmentModeDO.getId());
            AttachmentBizTemplateFileDO attachmentBizTemplate = (AttachmentBizTemplateFileDO)this.attachmentBizTemplateFileDao.selectOne(attachmentBizTemplateFileDO);
            attachmentBizTemplateFileDO.setName(file.getOriginalFilename());
            attachmentBizTemplateFileDO.setTemplateFile(bytes);
            int result = 0;
            if (attachmentBizTemplate == null) {
                attachmentBizTemplateFileDO.setId(UUID.randomUUID());
                result = this.attachmentBizTemplateFileDao.insert(attachmentBizTemplateFileDO);
            } else {
                attachmentBizTemplateFileDO.setId(attachmentBizTemplate.getId());
                result = this.attachmentBizTemplateFileDao.updateByPrimaryKeySelective(attachmentBizTemplateFileDO);
            }
            if (result == 0) {
                return R.error();
            }
        } else {
            AttachmentBizTemplateFileDO attachmentBizTemplateFileDO = new AttachmentBizTemplateFileDO();
            attachmentBizTemplateFileDO.setMasterId(attachmentModeDO.getId());
            this.attachmentBizTemplateFileDao.delete(attachmentBizTemplateFileDO);
        }
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R delete(AttachmentModeDO attachmentModeDO) {
        if (attachmentModeDO.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        AttachmentModeDO mode = new AttachmentModeDO();
        mode.setId(attachmentModeDO.getId());
        this.attachmentModeDao.delete(mode);
        AttachmentBizTemplateFileDO attachmentBizTemplateFileDO = new AttachmentBizTemplateFileDO();
        attachmentBizTemplateFileDO.setMasterId(attachmentModeDO.getId());
        this.attachmentBizTemplateFileDao.delete(attachmentBizTemplateFileDO);
        return R.ok();
    }

    @Override
    public PageVO<AttachmentModeDTO> list(AttachmentModeDO attachmentModeDO) {
        PageVO page = new PageVO();
        ArrayList<AttachmentModeDTO> list = new ArrayList<AttachmentModeDTO>();
        AttachmentModeDTO de = new AttachmentModeDTO();
        attachmentModeDO.setStartflag(Integer.valueOf(1));
        List<AttachmentModeDTO> modeList = this.attachmentModeDao.getAttList(attachmentModeDO);
        for (AttachmentModeDTO mode : modeList) {
            if (!"DEFAULTSCHEME".equals(mode.getSchemename())) {
                if (mode.getDefaultflag() == null) {
                    mode.setTitle(mode.getSchemetitle() + "-" + mode.getTitle());
                } else {
                    mode.setTitle(mode.getSchemetitle() + "-" + mode.getTitle() + "(\u9ed8\u8ba4)");
                }
                list.add(mode);
                continue;
            }
            de.setName("DEFAULT");
            if (mode.getDefaultflag() != null) {
                de.setTitle("\u670d\u52a1\u5668\u5b58\u50a8\u65b9\u6848-DEFAULT(\u9ed8\u8ba4)");
            } else {
                de.setTitle("\u670d\u52a1\u5668\u5b58\u50a8\u65b9\u6848-DEFAULT");
            }
            de.setSchemename("DEFAULTSCHEME");
            de.setStoremode(Integer.valueOf(3));
            de.setTemplatename(mode.getTemplatename());
            de.setId(mode.getId());
        }
        list.add(de);
        page.setRows(list);
        page.setRs(R.ok());
        return page;
    }

    @Override
    public AttachmentModeDTO get(AttachmentModeDO attachmentModeDO) {
        AttachmentModeDO mode = new AttachmentModeDO();
        mode.setName(attachmentModeDO.getName());
        return this.attachmentModeDao.getAttMode(mode);
    }

    @Override
    public R checkMode(AttachmentModeDO attachmentModeDO) {
        int num = 0;
        AttachmentModeDO mode = new AttachmentModeDO();
        mode.setName(attachmentModeDO.getName());
        num = this.attachmentModeDao.selectCount(mode);
        if (num > 0) {
            return R.error();
        }
        return R.ok();
    }

    private R checkModeConfig(AttachmentModeDO attachmentModeDO) {
        if (attachmentModeDO == null || !StringUtils.hasText(attachmentModeDO.getSchemename())) {
            return R.ok();
        }
        AttachmentSchemeDO param = new AttachmentSchemeDO();
        param.setName(attachmentModeDO.getSchemename());
        AttachmentSchemeDO attachmentScheme = this.attachmentSchemeService.get(param);
        if (attachmentScheme == null) {
            return R.ok();
        }
        for (AttachmentHandleIntf item : this.getAttachmentHandleIntfList()) {
            if (item.getStoremode() != attachmentScheme.getStoremode().intValue()) continue;
            return item.checkModeConfig(attachmentModeDO);
        }
        return R.ok();
    }
}

