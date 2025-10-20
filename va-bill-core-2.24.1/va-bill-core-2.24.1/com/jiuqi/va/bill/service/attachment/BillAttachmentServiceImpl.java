/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.bill.feign.util.domain.VaBillFeignUtil
 *  com.jiuqi.va.domain.attachement.AttachmentComponentInfo
 *  com.jiuqi.va.domain.attachement.AttachmentConfigInfo
 *  com.jiuqi.va.domain.attachement.AttachmentFieldInfo
 *  com.jiuqi.va.domain.attachement.AttachmentTableInfo
 *  com.jiuqi.va.domain.attachement.BillAttachmentInfo
 *  com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.client.BillClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.bill.service.attachment;

import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.bill.domain.attachmen.AttachmentInfo;
import com.jiuqi.va.bill.feign.util.domain.VaBillFeignUtil;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.plugin.AttachmentPluginDefineImpl;
import com.jiuqi.va.bill.service.attachment.BillAttachmentService;
import com.jiuqi.va.domain.attachement.AttachmentComponentInfo;
import com.jiuqi.va.domain.attachement.AttachmentConfigInfo;
import com.jiuqi.va.domain.attachement.AttachmentFieldInfo;
import com.jiuqi.va.domain.attachement.AttachmentTableInfo;
import com.jiuqi.va.domain.attachement.BillAttachmentInfo;
import com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.client.BillClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BillAttachmentServiceImpl
implements BillAttachmentService {
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private VaAttachmentFeignClient vaAttachmentFeignClient;

    @Override
    public List<AttachmentConfigInfo> listAttachmentTableFieldConfig(QueryAttachmentInfoParam param) {
        List billDefineCodeList = param.getBillDefineCodeList();
        Assert.notEmpty(billDefineCodeList, "Check interface param!");
        ArrayList<AttachmentConfigInfo> configInfos = new ArrayList<AttachmentConfigInfo>();
        for (String defineCode : billDefineCodeList) {
            AttachmentConfigInfo attachmentConfigInfo = this.getAttachmentConfigInfo(defineCode);
            if (!Objects.nonNull(attachmentConfigInfo)) continue;
            configInfos.add(attachmentConfigInfo);
        }
        return configInfos;
    }

    @Override
    public List<AttachmentComponentInfo> queryAttachmentComponentInfo(String defineCode) {
        if (!StringUtils.hasText(defineCode)) {
            return null;
        }
        BillDefine billDefine = this.billDefineService.getDefine(defineCode);
        AttachmentPluginDefineImpl attachmentPlugin = (AttachmentPluginDefineImpl)((Object)billDefine.getPlugins().find(AttachmentPluginDefineImpl.class));
        if (Objects.isNull((Object)attachmentPlugin)) {
            return null;
        }
        return attachmentPlugin.getAttachmentComponentInfos();
    }

    @Override
    public List<AttachmentInfo> uploadAttachment(List<MultipartFile> files, String defineCode) {
        return this.uploadAttachment(files, defineCode, null);
    }

    @Override
    public List<AttachmentInfo> uploadAttachment(List<MultipartFile> files, String defineCode, String quoteCode) {
        BillClient billClient = VaBillFeignUtil.getBillClientByDefineCode((String)defineCode, (String)ShiroUtil.getTenantName());
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("defineCode", (Object)defineCode);
        List attachmentComponentInfos = billClient.queryAttachmentComponentInfo(tenantDO);
        if (CollectionUtils.isEmpty(attachmentComponentInfos)) {
            throw new RuntimeException("\u5355\u636e\u672a\u7ed1\u5b9a\u9644\u4ef6\u7ec4\u4ef6");
        }
        AttachmentComponentInfo attachmentComponentInfo = (AttachmentComponentInfo)attachmentComponentInfos.get(0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM-");
        String dateString = formatter.format(new Date());
        if (!StringUtils.hasText(quoteCode)) {
            quoteCode = dateString + UUID.randomUUID();
        }
        ArrayList<AttachmentInfo> attachmentInfos = new ArrayList<AttachmentInfo>();
        for (MultipartFile file : files) {
            AttachmentBizDTO attachmentBizDTO = new AttachmentBizDTO();
            attachmentBizDTO.setAtttype(attachmentComponentInfo.getAttType());
            attachmentBizDTO.setQuotecode(quoteCode);
            R upload = this.vaAttachmentFeignClient.upload(file, quoteCode, attachmentBizDTO);
            AttachmentInfo attachmentInfo = new AttachmentInfo();
            attachmentInfo.setFilename(file.getOriginalFilename());
            attachmentInfo.setQuotecode(quoteCode);
            attachmentInfo.setCodeTable(attachmentComponentInfo.getCodeTable());
            attachmentInfo.setCodeField(attachmentComponentInfo.getCodeField());
            attachmentInfo.setNumTable(attachmentComponentInfo.getNumTable());
            attachmentInfo.setNumFiled(attachmentComponentInfo.getNumFiled());
            if (upload.getCode() == 1) {
                attachmentInfo.setSuccess(false);
                attachmentInfo.setMessage(upload.getMsg());
            } else {
                attachmentInfo.setSuccess(true);
            }
            attachmentInfos.add(attachmentInfo);
        }
        return attachmentInfos;
    }

    private AttachmentConfigInfo getAttachmentConfigInfo(String defineCode) {
        if (!StringUtils.hasText(defineCode)) {
            return null;
        }
        BillDefine billDefine = this.billDefineService.getDefine(defineCode);
        if (Objects.isNull(billDefine)) {
            return null;
        }
        AttachmentPluginDefineImpl attachmentPlugin = (AttachmentPluginDefineImpl)((Object)billDefine.getPlugins().find(AttachmentPluginDefineImpl.class));
        if (Objects.isNull((Object)attachmentPlugin)) {
            return null;
        }
        ArrayList<BillAttachmentInfo> attachmentInfos = new ArrayList<BillAttachmentInfo>();
        Map quoteCodes = Optional.ofNullable(attachmentPlugin.getQuoteCodes()).orElse(Collections.emptyMap());
        for (Map.Entry entry : quoteCodes.entrySet()) {
            String schemeCode = (String)entry.getKey();
            Map<String, Set<String>> tableFiledMap = Optional.ofNullable(entry.getValue()).orElse(Collections.emptyMap());
            BillAttachmentInfo schemeAttachmentInfo = new BillAttachmentInfo();
            schemeAttachmentInfo.setSchemeCode(schemeCode);
            List<AttachmentTableInfo> tableInfos = BillAttachmentServiceImpl.getAttachmentTableInfos(tableFiledMap);
            schemeAttachmentInfo.setAttachmentTableInfoList(tableInfos);
            if (CollectionUtils.isEmpty(tableInfos)) continue;
            attachmentInfos.add(schemeAttachmentInfo);
        }
        if (CollectionUtils.isEmpty(attachmentInfos)) {
            return null;
        }
        AttachmentConfigInfo attachmentConfigInfo = new AttachmentConfigInfo();
        attachmentConfigInfo.setBillDefineCode(defineCode);
        attachmentConfigInfo.setAttachmentInfoList(attachmentInfos);
        return attachmentConfigInfo;
    }

    private static List<AttachmentTableInfo> getAttachmentTableInfos(Map<String, Set<String>> value) {
        ArrayList<AttachmentTableInfo> tableInfos = new ArrayList<AttachmentTableInfo>();
        if (CollectionUtils.isEmpty(value)) {
            return tableInfos;
        }
        for (Map.Entry<String, Set<String>> entry : value.entrySet()) {
            String tableName = entry.getKey();
            AttachmentTableInfo attachmentTableInfo = new AttachmentTableInfo();
            attachmentTableInfo.setTableName(tableName);
            ArrayList<AttachmentFieldInfo> fieldInfoList = new ArrayList<AttachmentFieldInfo>();
            Set filedSet = Optional.ofNullable(entry.getValue()).orElse(Collections.emptySet());
            for (String columnName : filedSet) {
                AttachmentFieldInfo attachmentFieldInfo = new AttachmentFieldInfo();
                attachmentFieldInfo.setColumnName(columnName);
                fieldInfoList.add(attachmentFieldInfo);
            }
            if (CollectionUtils.isEmpty(fieldInfoList)) continue;
            attachmentTableInfo.setFieldInfoList(fieldInfoList);
            tableInfos.add(attachmentTableInfo);
        }
        return tableInfos;
    }
}

