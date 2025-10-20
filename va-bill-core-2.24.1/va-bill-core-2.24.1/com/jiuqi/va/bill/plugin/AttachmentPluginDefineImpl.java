/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.PluginDefineImpl
 *  com.jiuqi.va.domain.attachement.AttachmentComponentInfo
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.domain.attachement.AttachmentComponentInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.util.StringUtils;

public class AttachmentPluginDefineImpl
extends PluginDefineImpl {
    private Map<String, Map<String, Set<String>>> quoteCodes;
    private Map<String, Map<String, Set<String>>> attachNums;
    private List<AttachmentComponentInfo> attachmentComponentInfos;

    public List<AttachmentComponentInfo> getAttachmentComponentInfos() {
        return this.attachmentComponentInfos;
    }

    public void setAttachmentComponentInfos(List<AttachmentComponentInfo> attachmentComponentInfos) {
        this.attachmentComponentInfos = attachmentComponentInfos;
    }

    public Map<String, Map<String, Set<String>>> getQuoteCodes() {
        return this.quoteCodes;
    }

    public void setQuoteCodes(Map<String, Map<String, Set<String>>> quoteCodes) {
        this.quoteCodes = quoteCodes;
    }

    public Map<String, Map<String, Set<String>>> getAttachNums() {
        return this.attachNums;
    }

    public void setAttachNums(Map<String, Map<String, Set<String>>> attachNums) {
        this.attachNums = attachNums;
    }

    public void addAttachmentComponentInfo(AttachmentComponentInfo attachmentComponentInfo) {
        if (this.attachmentComponentInfos == null) {
            this.attachmentComponentInfos = new ArrayList<AttachmentComponentInfo>();
        }
        if (!this.attachmentComponentInfos.isEmpty()) {
            Optional<AttachmentComponentInfo> optional = this.attachmentComponentInfos.stream().filter(info -> info.getAttType().equals(attachmentComponentInfo.getAttType()) && info.getCodeField().equals(attachmentComponentInfo.getCodeField()) && info.getCodeTable().equals(attachmentComponentInfo.getCodeTable())).findFirst();
            if (optional.isPresent()) {
                AttachmentComponentInfo info2 = optional.get();
                if (StringUtils.hasText(attachmentComponentInfo.getAttTitle())) {
                    info2.setAttTitle(attachmentComponentInfo.getAttTitle());
                }
                if (StringUtils.hasText(attachmentComponentInfo.getNumTable())) {
                    info2.setNumTable(attachmentComponentInfo.getNumTable());
                    info2.setNumFiled(attachmentComponentInfo.getNumFiled());
                }
            } else {
                this.attachmentComponentInfos.add(attachmentComponentInfo);
            }
        } else {
            this.attachmentComponentInfos.add(attachmentComponentInfo);
        }
    }
}

