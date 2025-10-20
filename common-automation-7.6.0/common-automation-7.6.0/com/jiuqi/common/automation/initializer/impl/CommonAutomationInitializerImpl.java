/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 */
package com.jiuqi.common.automation.initializer.impl;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.automation.initializer.IAutomationInitializer;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommonAutomationInitializerImpl
implements IAutomationInitializer {
    @Autowired
    private ApplicationContext applicationContext;
    private final Logger logger = LoggerFactory.getLogger(CommonAutomationInitializerImpl.class);

    @Override
    public void init(Map<String, String> typeCategoryMap, Map<String, Map<String, AutomationFolder>> automationMap, Map<String, List<AutomationInstance>> automationInstancesMap) {
        Map<String, Object> commonAutomationMap = this.applicationContext.getBeansWithAnnotation(CommonAutomation.class);
        for (Map.Entry<String, Object> entry : commonAutomationMap.entrySet()) {
            AutomationType automationType = entry.getValue().getClass().getAnnotation(AutomationType.class);
            if (automationType == null) {
                this.logger.error("automationType is null. \t" + entry.getKey());
                continue;
            }
            this.automationInit(automationType, typeCategoryMap);
            CommonAutomation commonAutomation = this.applicationContext.findAnnotationOnBean(entry.getKey(), CommonAutomation.class);
            if (commonAutomation == null) {
                this.logger.error("CommonAutomation is null. \t" + entry.getKey());
                continue;
            }
            this.automationInit(commonAutomation, automationType, typeCategoryMap, automationMap, automationInstancesMap);
        }
    }

    private void automationInit(AutomationType automationType, Map<String, String> typeCategoryMap) {
        typeCategoryMap.put(automationType.id(), automationType.category());
    }

    private void automationInit(CommonAutomation automationExample, AutomationType automationType, Map<String, String> typeCategoryMap, Map<String, Map<String, AutomationFolder>> automationMap, Map<String, List<AutomationInstance>> automationInstancesMap) {
        String category = typeCategoryMap.get(automationType.id());
        Map folderMap = automationMap.computeIfAbsent(category, k -> new HashMap());
        String folderId = category;
        StringJoiner folderFullNameJoiner = new StringJoiner("/");
        if (!StringUtils.isEmpty((String)automationExample.path())) {
            String[] pathSplit;
            if (!automationExample.path().startsWith("/")) {
                this.logger.error("CommonAutomation\u6ce8\u89e3path\u5c5e\u6027\u5fc5\u987b\u4ee5\"/\"\u5f00\u59cb\uff01\uff01");
                return;
            }
            for (String pathName : pathSplit = automationExample.path().split("/")) {
                if (StringUtils.isEmpty((String)pathName)) continue;
                folderFullNameJoiner.add(pathName);
                String finalFolderId = folderId;
                folderId = folderMap.computeIfAbsent(folderFullNameJoiner.toString(), k -> this.createFolder(pathName, folderFullNameJoiner.toString(), finalFolderId)).getGuid();
            }
        }
        List automationInstances = automationInstancesMap.computeIfAbsent(category, k -> new ArrayList());
        AutomationInstance automationInstance = new AutomationInstance();
        automationInstance.setName(StringUtils.isEmpty((String)automationExample.name()) ? automationType.id() : automationExample.name());
        automationInstance.setTitle(StringUtils.isEmpty((String)automationExample.title()) ? automationType.title() : automationExample.title());
        automationInstance.setType(automationType.id());
        automationInstance.setPid(folderId);
        automationInstance.setGuid(UUIDUtils.nameUUIDFromBytes((byte[])(folderId + automationInstance.getName()).getBytes(StandardCharsets.UTF_8)));
        automationInstances.add(automationInstance);
    }

    private AutomationFolder createFolder(String pathName, String fullName, String pid) {
        AutomationFolder folder = new AutomationFolder();
        folder.setGuid(UUIDUtils.nameUUIDFromBytes((byte[])fullName.getBytes(StandardCharsets.UTF_8)));
        folder.setTitle(pathName);
        folder.setPid(pid);
        return folder;
    }
}

