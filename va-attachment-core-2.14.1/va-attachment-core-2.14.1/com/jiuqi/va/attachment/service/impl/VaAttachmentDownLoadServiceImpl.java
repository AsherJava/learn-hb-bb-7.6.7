/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.attachment.domain.exception.VaAttachmentException
 *  com.jiuqi.va.attachment.domain.folderdownload.AttachmentFolder
 *  com.jiuqi.va.attachment.domain.folderdownload.AttachmentQuoteCodeInfo
 *  com.jiuqi.va.attachment.domain.folderdownload.AttachmentZipInfo
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.core.ValueOperations
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.domain.exception.VaAttachmentException;
import com.jiuqi.va.attachment.domain.folderdownload.AttachmentFolder;
import com.jiuqi.va.attachment.domain.folderdownload.AttachmentQuoteCodeInfo;
import com.jiuqi.va.attachment.domain.folderdownload.AttachmentZipInfo;
import com.jiuqi.va.attachment.entity.zipdownload.ZipDownLoadParam;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.attachment.service.VaAttachmentDownLoadService;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.attachment.utils.VaAttachmentZipUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class VaAttachmentDownLoadServiceImpl
implements VaAttachmentDownLoadService {
    private static final Logger logger = LoggerFactory.getLogger(VaAttachmentDownLoadServiceImpl.class);
    @Autowired(required=false)
    @Qualifier(value="vaAttachmentByteRedisTemplate")
    private RedisTemplate<String, byte[]> redisTemplate;
    @Autowired
    private AttachmentBizService attachmentBizService;
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Autowired
    private List<AttachmentHandleIntf> attachmentHandleIntfList;

    @Override
    public R downloadZipCheck() {
        UserLoginDTO user = ShiroUtil.getUser();
        String id = user.getId();
        String useDownloadTaskKey = "VA_ATTACHMENT_FOLD_DOWNLOAD:" + id;
        if (this.redisTemplate.hasKey((Object)useDownloadTaskKey).booleanValue()) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public void downloadZipByFolder(String key) {
        Assert.hasText(key, "Check interface param!");
        UserLoginDTO user = ShiroUtil.getUser();
        try {
            byte[] bytes = this.getZipFolderBytes(key);
            if (this.checkUseDownLoadTask(user.getId(), key)) {
                logger.error("\u5f53\u524d\u7528\u6237\u6709\u672a\u4e0b\u8f7d\u5b8c\u7684\u6587\u4ef6\uff0c\u8bf7\u7a0d\u540e\u4e0b\u8f7d\uff01");
                return;
            }
            AttachmentZipInfo attachmentZipInfo = (AttachmentZipInfo)JSONUtil.parseObject((byte[])VaAttachmentIOUtils.decompress(bytes), AttachmentZipInfo.class);
            Assert.notNull((Object)attachmentZipInfo, "\u6ca1\u6709\u8981\u4e0b\u8f7d\u7684\u9644\u4ef6\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u6216\u8005\u7a0b\u5e8f\uff01");
            List folderList = attachmentZipInfo.getFolderList();
            Assert.notEmpty(folderList, "\u6ca1\u6709\u8981\u4e0b\u8f7d\u7684\u9644\u4ef6\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u6216\u8005\u7a0b\u5e8f\uff01");
            Map<String, List<AttachmentBizDTO>> attachmentBizMap = this.getAttachmentBizDTOMap(attachmentZipInfo);
            Assert.notEmpty(attachmentBizMap, "\u6ca1\u6709\u8981\u4e0b\u8f7d\u7684\u9644\u4ef6\u8bf7\u68c0\u67e5\u53c2\u6570\u6216\u8005\u7a0b\u5e8f\uff01");
            this.downloadZipByFolderExecute(attachmentZipInfo, attachmentBizMap);
            LogUtil.add((String)"\u9644\u4ef6", (String)"\u6279\u91cf\u4e0b\u8f7d\u5355\u636e\u9644\u4ef6", null, (String)user.getName(), null);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VaAttachmentException(e.getMessage(), (Throwable)e);
        }
        finally {
            this.releaseUseDownLoadTask(user.getId());
        }
    }

    private byte[] getZipFolderBytes(String key) {
        String keyPrefix = "VA_BILL_LIST_ATTACHMENT_ZIP:" + key;
        ValueOperations operations = this.redisTemplate.opsForValue();
        byte[] bytes = (byte[])operations.get((Object)keyPrefix);
        if (bytes == null || bytes.length == 0) {
            throw new VaAttachmentException("\u6ca1\u6709\u8981\u4e0b\u8f7d\u7684\u9644\u4ef6\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u6216\u8005\u7a0b\u5e8f\uff01");
        }
        return bytes;
    }

    private void releaseUseDownLoadTask(String userId) {
        String useDownloadTaskKey = "VA_ATTACHMENT_FOLD_DOWNLOAD:" + userId;
        if (this.redisTemplate.hasKey((Object)useDownloadTaskKey).booleanValue()) {
            this.redisTemplate.delete((Object)useDownloadTaskKey);
        }
    }

    private boolean checkUseDownLoadTask(String id, String key) {
        String useDownloadTaskKey = "VA_ATTACHMENT_FOLD_DOWNLOAD:" + id;
        if (this.redisTemplate.hasKey((Object)useDownloadTaskKey).booleanValue()) {
            return true;
        }
        this.redisTemplate.opsForValue().set((Object)useDownloadTaskKey, (Object)key.getBytes(StandardCharsets.UTF_8), 5L, TimeUnit.MINUTES);
        return false;
    }

    private Map<String, List<AttachmentBizDTO>> getAttachmentBizDTOMap(AttachmentZipInfo attachmentZipInfo) {
        ArrayList<String> quoteCodeList = new ArrayList<String>();
        for (AttachmentFolder attachmentFolder : attachmentZipInfo.getFolderList()) {
            for (AttachmentQuoteCodeInfo attachmentQuoteCodeInfo : attachmentFolder.getAttachmentQuoteCodeInfoList()) {
                String quoteCode = attachmentQuoteCodeInfo.getQuoteCode();
                quoteCodeList.add(quoteCode);
            }
        }
        Map<String, List<AttachmentBizDTO>> map = this.attachmentBizService.listAttAndSchemeTitle(quoteCodeList, false);
        Assert.notEmpty(map, "\u6ca1\u6709\u8981\u4e0b\u8f7d\u7684\u9644\u4ef6\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u6216\u8005\u7a0b\u5e8f\uff01");
        return map.entrySet().stream().filter(entry -> {
            List list = (List)entry.getValue();
            return list != null && !list.isEmpty();
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void downloadZipByFolderExecute(AttachmentZipInfo attachmentZipInfo, Map<String, List<AttachmentBizDTO>> attachmentBizMap) {
        String zipName = attachmentZipInfo.getZipName();
        if (!StringUtils.hasText(zipName)) {
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
            zipName = "\u9644\u4ef6\u5305" + yyyyMMddHHmmss.format(new Date());
            attachmentZipInfo.setZipName(zipName);
        }
        ZipOutputStream zipOutputStream = null;
        OutputStream outputStream = null;
        try {
            RequestContextUtil.setResponseContentType((String)"application/zip");
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(zipName + ".zip", "UTF-8")));
            outputStream = RequestContextUtil.getOutputStream();
            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
            this.folderZipDownload(zipOutputStream, attachmentBizMap, attachmentZipInfo);
            zipOutputStream.flush();
            outputStream.flush();
        }
        catch (Exception e) {
            VaAttachmentZipUtils.writeErrorInToZip(zipOutputStream, zipName, null);
            logger.error(e.getMessage(), e);
        }
        finally {
            try {
                if (Objects.nonNull(zipOutputStream)) {
                    zipOutputStream.finish();
                    zipOutputStream.close();
                }
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void folderZipDownload(ZipOutputStream zipOutputStream, Map<String, List<AttachmentBizDTO>> attachmentBizMap, AttachmentZipInfo attachmentZipInfo) throws IOException {
        String rootDir = attachmentZipInfo.getZipName();
        HashMap<String, SchemeEntity> schemeEntityMap = new HashMap<String, SchemeEntity>();
        ArrayList<String> downLoadInfoList = new ArrayList<String>();
        List folderList = attachmentZipInfo.getFolderList();
        folderList = folderList.stream().filter(x -> !CollectionUtils.isEmpty(x.getAttachmentQuoteCodeInfoList())).collect(Collectors.toList());
        for (AttachmentFolder attachmentFolder : folderList) {
            String folderName = attachmentFolder.getFolderName();
            String pathName = rootDir + "/" + folderName;
            ZipDownLoadParam zipDownLoadParam = new ZipDownLoadParam();
            zipDownLoadParam.setPathName(pathName);
            zipDownLoadParam.setSchemeEntityMap(schemeEntityMap);
            zipDownLoadParam.setDownLoadInfoList(downLoadInfoList);
            zipDownLoadParam.setFolderName(folderName);
            List quoteCodeList = attachmentFolder.getAttachmentQuoteCodeInfoList();
            for (AttachmentQuoteCodeInfo quoteCodeInfo : quoteCodeList) {
                this.folderZipDownloadExecute(zipOutputStream, quoteCodeInfo, attachmentBizMap, zipDownLoadParam);
            }
            zipOutputStream.flush();
        }
        String zipName = attachmentZipInfo.getZipName();
        if (downLoadInfoList.isEmpty()) {
            VaAttachmentZipUtils.writeSuccessInToZip(zipOutputStream, zipName);
        } else {
            VaAttachmentZipUtils.writeErrorFolderInToZip(zipOutputStream, zipName, downLoadInfoList);
        }
    }

    private void folderZipDownloadExecute(ZipOutputStream zipOutputStream, AttachmentQuoteCodeInfo attachmentQuoteCodeInfo, Map<String, List<AttachmentBizDTO>> attachmentBizMap, ZipDownLoadParam zipDownLoadParam) {
        String quoteCode = attachmentQuoteCodeInfo.getQuoteCode();
        List<AttachmentBizDTO> attachments = attachmentBizMap.get(quoteCode);
        if (CollectionUtils.isEmpty(attachments)) {
            return;
        }
        Map<String, SchemeEntity> schemeEntityMap = zipDownLoadParam.getSchemeEntityMap();
        String pathName = zipDownLoadParam.getPathName();
        String folderName = zipDownLoadParam.getFolderName();
        List<String> downLoadInfoList = zipDownLoadParam.getDownLoadInfoList();
        for (AttachmentBizDTO attachment : attachments) {
            String name = attachment.getName();
            String zipPath = pathName + "/" + name;
            try {
                this.zipDownLoadExecute(attachment, schemeEntityMap, zipOutputStream, zipPath);
            }
            catch (Exception e) {
                String msg = String.format("\u6587\u4ef6\u5939%s\u4e0b\u8f7d\u51fa\u9519\uff0c\u9644\u4ef6\u5f15\u7528\u7801%s\uff0c\u6587\u4ef6\u3010%s\u3011\u4e0b\u8f7d\u51fa\u9519\uff1a%s", folderName, quoteCode, name, e.getMessage());
                downLoadInfoList.add(msg);
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void zipDownLoadExecute(AttachmentBizDTO attachmentBizDO, Map<String, SchemeEntity> schemeEntityMap, ZipOutputStream zipOutputStream, String zipPath) throws Exception {
        String schemeName = attachmentBizDO.getSchemename();
        if (StringUtils.hasText(schemeName)) {
            SchemeEntity schemeEntity = schemeEntityMap.get(schemeName);
            if (schemeEntity == null) {
                schemeEntity = this.bizHelpService.getMessageFromScheme(schemeName);
                schemeEntityMap.put(schemeName, schemeEntity);
            }
            Integer storeMode = schemeEntity.getStoremode();
            attachmentBizDO.addExtInfo("schemeEntity", (Object)schemeEntity);
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != storeMode.intValue()) continue;
                attachment.zipDownLoad(attachmentBizDO, zipOutputStream, zipPath);
            }
        } else {
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != 3) continue;
                attachment.zipDownLoad(attachmentBizDO, zipOutputStream, zipPath);
            }
        }
    }
}

