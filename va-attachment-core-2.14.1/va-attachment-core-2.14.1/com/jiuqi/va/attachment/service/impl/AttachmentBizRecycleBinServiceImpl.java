/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO
 *  com.jiuqi.va.attachment.domain.FileDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.dao.BizAttachmentRecycleBinDao;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDO;
import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO;
import com.jiuqi.va.attachment.domain.FileDTO;
import com.jiuqi.va.attachment.entity.BizDataDO;
import com.jiuqi.va.attachment.service.AttachmentBizRecycleBinService;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.util.LogUtil;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class AttachmentBizRecycleBinServiceImpl
implements AttachmentBizRecycleBinService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizRecycleBinServiceImpl.class);
    @Autowired
    private BizAttachmentRecycleBinDao bizAttachmentRecycleBinDao;
    @Autowired
    private AttachmentBizService attachmentBizService;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private VaAttachmentBizDao vaAttachmentBizDao;
    private static final ConcurrentHashMap<String, String> userNameMap = new ConcurrentHashMap();

    @Override
    public int insert(BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDO) {
        return this.bizAttachmentRecycleBinDao.insert(bizAttachmentRecycleBinDO.copyEntity());
    }

    @Override
    public void scheduleClear() {
        BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDO = new BizAttachmentRecycleBinDTO();
        LocalDate endLoacalDate = LocalDate.now().plusDays(-7L);
        Instant endInstant = endLoacalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        bizAttachmentRecycleBinDO.setDeletetime(Date.from(endInstant));
        List<BizAttachmentRecycleBinDO> bizAttachmentRecycleBinDOS = this.bizAttachmentRecycleBinDao.selectByDeleteTime((BizAttachmentRecycleBinDO)bizAttachmentRecycleBinDO);
        for (BizAttachmentRecycleBinDO attachmentRecycleBinDO : bizAttachmentRecycleBinDOS) {
            FileDTO fileDTO = this.deleteFile(attachmentRecycleBinDO);
            if (fileDTO == null) continue;
            LOGGER.error("\u9644\u4ef6\u5220\u9664\u5931\u8d25\uff1a{}:{}:{}", fileDTO.getFileId(), fileDTO.getFileName(), fileDTO.getMessage());
        }
    }

    @Override
    public List<BizAttachmentRecycleBinDTO> listErrorData(BizAttachmentRecycleBinDTO param) {
        List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOS = this.bizAttachmentRecycleBinDao.listErrorData(param);
        return this.setData(bizAttachmentRecycleBinDTOS);
    }

    @Override
    public List<BizAttachmentRecycleBinDTO> listNothingData(BizAttachmentRecycleBinDTO param) {
        List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOS = this.bizAttachmentRecycleBinDao.listNothingData(param);
        return this.setData(bizAttachmentRecycleBinDTOS);
    }

    private List<BizAttachmentRecycleBinDTO> setData(List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOS) {
        ArrayList<BizAttachmentRecycleBinDTO> result = new ArrayList<BizAttachmentRecycleBinDTO>();
        UserDTO userDTO = new UserDTO();
        for (BizAttachmentRecycleBinDO bizAttachmentRecycleBinDO : bizAttachmentRecycleBinDTOS) {
            BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDTO = bizAttachmentRecycleBinDO.copyEntity();
            if (bizAttachmentRecycleBinDO.getDeletetime().getTime() < System.currentTimeMillis() - 2592000000L) {
                bizAttachmentRecycleBinDTO.setRestore(Boolean.valueOf(true));
            }
            if (userNameMap.contains(String.valueOf(bizAttachmentRecycleBinDO.getOptuser()))) {
                bizAttachmentRecycleBinDTO.setUsername(userNameMap.get(String.valueOf(bizAttachmentRecycleBinDO.getOptuser())));
            } else {
                userDTO.setId(String.valueOf(bizAttachmentRecycleBinDO.getOptuser()));
                userDTO.setTenantName(ShiroUtil.getTenantName());
                UserDO userDO = this.authUserClient.get(userDTO);
                if (userDO != null) {
                    bizAttachmentRecycleBinDTO.setUsername(userDO.getName());
                    userNameMap.put(String.valueOf(bizAttachmentRecycleBinDO.getOptuser()), userDO.getName());
                }
            }
            result.add(bizAttachmentRecycleBinDTO);
        }
        return result;
    }

    @Override
    public BizAttachmentRecycleBinDTO restore(List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOs) {
        BizAttachmentRecycleBinDTO result = new BizAttachmentRecycleBinDTO();
        ArrayList<FileDTO> success = new ArrayList<FileDTO>();
        ArrayList<FileDTO> fail = new ArrayList<FileDTO>();
        ArrayList<FileDTO> noThing = new ArrayList<FileDTO>();
        for (BizAttachmentRecycleBinDTO attachmentRecycleBinDTO : bizAttachmentRecycleBinDTOs) {
            String id;
            String sourceinfo = attachmentRecycleBinDTO.getSourceinfo();
            if (!StringUtils.hasText(sourceinfo)) {
                this.updateAndDel(success, fail, attachmentRecycleBinDTO);
                continue;
            }
            Map info = JSONUtil.parseMap((String)sourceinfo);
            String tableName = (String)info.get("tablename");
            int i = this.vaAttachmentBizDao.bizDataCount(new BizDataDO(tableName, id = (String)info.get("id")));
            if (i == 0) {
                noThing.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u5355\u636e\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u4e0d\u5141\u8bb8\u8fd8\u539f"));
                continue;
            }
            String quotecode = (String)info.get("quotecode");
            if (!StringUtils.hasText(quotecode)) {
                fail.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u62d3\u5c55\u4fe1\u606f\u4e2d\u5f15\u7528\u7801\u4e0d\u5b58\u5728"));
                continue;
            }
            List<Map<String, Object>> maps = this.vaAttachmentBizDao.quoteCodeQuery(new BizDataDO(tableName, id, quotecode));
            if (CollectionUtils.isEmpty(maps)) {
                noThing.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u5355\u636e\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u4e0d\u5141\u8bb8\u8fd8\u539f"));
                continue;
            }
            Map<String, Object> stringObjectMap = maps.get(0);
            if (stringObjectMap == null) {
                noThing.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u5355\u636e\u6570\u636e\u4e2d\u5f15\u7528\u7801\u503c\u4e3a\u7a7a"));
                continue;
            }
            Object o = stringObjectMap.get(quotecode);
            if (ObjectUtils.isEmpty(o)) {
                noThing.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u5355\u636e\u6570\u636e\u4e2d\u5f15\u7528\u7801\u503c\u4e3a\u7a7a"));
                continue;
            }
            if (!o.equals(attachmentRecycleBinDTO.getQuotecode())) {
                noThing.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u5355\u636e\u6570\u636e\u4e2d\u5f15\u7528\u7801\u4e0e\u9644\u4ef6\u4e2d\u4e0d\u4e00\u81f4"));
                continue;
            }
            this.updateAndDel(success, fail, attachmentRecycleBinDTO);
        }
        result.setSuccessDataList(success);
        result.setErrorDataList(fail);
        result.setNoThingDataList(noThing);
        return result;
    }

    private void updateAndDel(List<FileDTO> success, List<FileDTO> fail, BizAttachmentRecycleBinDTO attachmentRecycleBinDTO) {
        boolean flag = true;
        String quotecode = attachmentRecycleBinDTO.getQuotecode();
        UUID quoteid = attachmentRecycleBinDTO.getQuoteid();
        AttachmentBizDO attachmentBizDO = new AttachmentBizDO();
        attachmentBizDO.setQuotecode(quotecode);
        attachmentBizDO.setSuffix(quotecode.substring(0, 6));
        attachmentBizDO.setId(quoteid);
        attachmentBizDO.setStatus(Integer.valueOf(1));
        int update = this.attachmentBizService.update(attachmentBizDO);
        if (update > 0) {
            this.bizAttachmentRecycleBinDao.deleteById((BizAttachmentRecycleBinDO)attachmentRecycleBinDTO);
        } else {
            flag = false;
        }
        if (flag) {
            LogUtil.add((String)"\u9644\u4ef6\u56de\u6536\u7ad9", (String)"\u8fd8\u539f-\u6210\u529f", (String)attachmentRecycleBinDTO.getQuotecode(), (String)String.valueOf(attachmentRecycleBinDTO.getQuoteid()), null);
            success.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), null));
        } else {
            LogUtil.add((String)"\u9644\u4ef6\u56de\u6536\u7ad9", (String)"\u8fd8\u539f-\u5931\u8d25", (String)attachmentRecycleBinDTO.getQuotecode(), (String)String.valueOf(attachmentRecycleBinDTO.getQuoteid()), null);
            fail.add(new FileDTO(attachmentRecycleBinDTO.getFilename(), attachmentRecycleBinDTO.getQuoteid(), "\u8fd8\u539f\u5931\u8d25"));
        }
    }

    @Override
    public BizAttachmentRecycleBinDTO deleteRecord(List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOs) {
        BizAttachmentRecycleBinDTO result = new BizAttachmentRecycleBinDTO();
        ArrayList<FileDTO> success = new ArrayList<FileDTO>();
        ArrayList<FileDTO> fail = new ArrayList<FileDTO>();
        for (BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDTO : bizAttachmentRecycleBinDTOs) {
            FileDTO fileDTO = this.deleteFile((BizAttachmentRecycleBinDO)bizAttachmentRecycleBinDTO);
            if (fileDTO == null) {
                LogUtil.add((String)"\u9644\u4ef6\u56de\u6536\u7ad9", (String)"\u5220\u9664-\u6210\u529f", (String)bizAttachmentRecycleBinDTO.getQuotecode(), (String)String.valueOf(bizAttachmentRecycleBinDTO.getQuoteid()), null);
                success.add(new FileDTO(bizAttachmentRecycleBinDTO.getFilename(), bizAttachmentRecycleBinDTO.getQuoteid(), null));
                continue;
            }
            LogUtil.add((String)"\u9644\u4ef6\u56de\u6536\u7ad9", (String)"\u5220\u9664-\u5931\u8d25", (String)bizAttachmentRecycleBinDTO.getQuotecode(), (String)String.valueOf(bizAttachmentRecycleBinDTO.getQuoteid()), null);
            fail.add(fileDTO);
        }
        result.setErrorDataList(fail);
        result.setSuccessDataList(success);
        return result;
    }

    @Override
    public boolean markAsNotHandle(List<BizAttachmentRecycleBinDTO> bizAttachmentRecycleBinDTOs) {
        for (BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDTO : bizAttachmentRecycleBinDTOs) {
            BizAttachmentRecycleBinDTO update = new BizAttachmentRecycleBinDTO();
            update.setId(bizAttachmentRecycleBinDTO.getId());
            update.setDeletetype(AttachmentConst.DELETE_STATUS_NOTHING);
            this.bizAttachmentRecycleBinDao.updateDeleteTypeByPrimaryKey(update);
        }
        return true;
    }

    private FileDTO deleteFile(BizAttachmentRecycleBinDO bizAttachmentRecycleBinDO) {
        try {
            String quotecode = bizAttachmentRecycleBinDO.getQuotecode();
            if (!StringUtils.hasText(quotecode)) {
                return null;
            }
            UUID quoteid = bizAttachmentRecycleBinDO.getQuoteid();
            if (quoteid == null) {
                return null;
            }
            AttachmentBizDTO attachmentBizDTO = new AttachmentBizDTO();
            attachmentBizDTO.setQuotecode(quotecode);
            attachmentBizDTO.setId(quoteid);
            attachmentBizDTO.setSuffix(quotecode.substring(0, 6));
            attachmentBizDTO.setStatus(Integer.valueOf(-1));
            R r = this.attachmentBizService.deleteFile(attachmentBizDTO);
            if (r.getCode() != 0) {
                LOGGER.error("\u56de\u6536\u7ad9\u9644\u4ef6\u5220\u9664\u5931\u8d25{}, id:{}", (Object)quotecode, (Object)quoteid);
                return new FileDTO(bizAttachmentRecycleBinDO.getFilename(), bizAttachmentRecycleBinDO.getQuoteid(), "\u7269\u7406\u6587\u4ef6\u5220\u9664\u5931\u8d25");
            }
            int i = this.bizAttachmentRecycleBinDao.deleteById(bizAttachmentRecycleBinDO);
            if (i > 0) {
                return null;
            }
            return new FileDTO(bizAttachmentRecycleBinDO.getFilename(), bizAttachmentRecycleBinDO.getQuoteid(), "\u7269\u7406\u6587\u4ef6\u5220\u9664\u5931\u8d25");
        }
        catch (Exception e) {
            LOGGER.error("\u56de\u6536\u7ad9\u9644\u4ef6\u5220\u9664\u5931\u8d25", e);
            return new FileDTO(bizAttachmentRecycleBinDO.getFilename(), bizAttachmentRecycleBinDO.getQuoteid(), e.getMessage());
        }
    }

    @Override
    public int getErrorTotal() {
        return this.bizAttachmentRecycleBinDao.selectErrorCount(new BizAttachmentRecycleBinDO());
    }

    @Override
    public int getNoThingTotal() {
        return this.bizAttachmentRecycleBinDao.selectNoThingCount(new BizAttachmentRecycleBinDO());
    }
}

