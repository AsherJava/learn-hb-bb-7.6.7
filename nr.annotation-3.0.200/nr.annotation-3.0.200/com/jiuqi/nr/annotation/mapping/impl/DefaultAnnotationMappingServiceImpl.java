/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.annotation.mapping.impl;

import com.jiuqi.nr.annotation.mapping.IAnnotationMappingService;
import com.jiuqi.nr.annotation.message.FormMappingMessage;
import com.jiuqi.nr.annotation.message.FormSchemeMappingMessage;
import com.jiuqi.nr.annotation.message.LinkMappingMessage;
import com.jiuqi.nr.annotation.message.RegionMappingMessage;
import com.jiuqi.nr.annotation.message.TaskMappingMessage;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAnnotationMappingServiceImpl
implements IAnnotationMappingService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAnnotationMappingServiceImpl.class);
    private IRunTimeViewController runTimeViewController;
    private Map<String, TaskMappingMessage> taskCodeMappingCatch;
    private Map<String, FormSchemeMappingMessage> formSchemeMappingCatch;
    private Map<String, FormMappingMessage> formMappingCatch;
    private Map<String, RegionMappingMessage> regionMappingCatch;
    private Map<String, LinkMappingMessage> linkMappingCatch;

    public DefaultAnnotationMappingServiceImpl(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
        this.taskCodeMappingCatch = new HashMap<String, TaskMappingMessage>();
        this.formSchemeMappingCatch = new HashMap<String, FormSchemeMappingMessage>();
        this.formMappingCatch = new HashMap<String, FormMappingMessage>();
        this.regionMappingCatch = new HashMap<String, RegionMappingMessage>();
        this.linkMappingCatch = new HashMap<String, LinkMappingMessage>();
    }

    @Override
    public TaskMappingMessage getTaskMapping(TaskMappingMessage param) {
        if (this.taskCodeMappingCatch.containsKey(param.getTaskCode())) {
            return this.taskCodeMappingCatch.get(param.getTaskCode());
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefineByCode(param.getTaskCode());
        if (null == taskDefine) {
            throw new RuntimeException("\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728");
        }
        TaskMappingMessage taskMappingMessage = new TaskMappingMessage(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle());
        this.taskCodeMappingCatch.put(param.getTaskCode(), taskMappingMessage);
        return taskMappingMessage;
    }

    @Override
    public FormSchemeMappingMessage getFormSchemeMapping(TaskMappingMessage taskParam, FormSchemeMappingMessage formSchemeParam) {
        if (this.formSchemeMappingCatch.containsKey(formSchemeParam.getFormSchemeCode())) {
            return this.formSchemeMappingCatch.get(formSchemeParam.getFormSchemeCode());
        }
        TaskMappingMessage taskMapping = this.getTaskMapping(taskParam);
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskMapping.getTaskKey());
            if (null == formSchemeDefines || formSchemeDefines.isEmpty()) {
                throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\u4e0d\u5b58\u5728");
            }
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                if (!formSchemeDefine.getTitle().equals(formSchemeParam.getFormSchemeTitle())) continue;
                FormSchemeMappingMessage formSchemeMappingMessage = new FormSchemeMappingMessage(formSchemeDefine.getKey(), formSchemeDefine.getFormSchemeCode(), formSchemeDefine.getTitle());
                this.formSchemeMappingCatch.put(formSchemeParam.getFormSchemeCode(), formSchemeMappingMessage);
                return formSchemeMappingMessage;
            }
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\u4e0d\u5b58\u5728");
        }
        catch (Exception e) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\u4e0d\u5b58\u5728", e);
        }
    }

    @Override
    public Map<String, FormMappingMessage> getFormMapping(TaskMappingMessage taskParam, FormSchemeMappingMessage formSchemeParam, List<FormMappingMessage> formParams) {
        HashMap<String, FormMappingMessage> result = new HashMap<String, FormMappingMessage>();
        FormSchemeMappingMessage formSchemeMapping = this.getFormSchemeMapping(taskParam, formSchemeParam);
        try {
            for (FormMappingMessage formParam : formParams) {
                if (this.formMappingCatch.containsKey(formParam.getFormCode())) {
                    result.put(formParam.getFormKey(), this.formMappingCatch.get(formParam.getFormCode()));
                    continue;
                }
                FormDefine formDefine = this.runTimeViewController.queryFormByCodeInScheme(formSchemeMapping.getFormSchemeKey(), formParam.getFormCode());
                if (null == formDefine) {
                    throw new RuntimeException("\u8868\u5355\u5b9a\u4e49\u4e0d\u5b58\u5728");
                }
                FormMappingMessage formMappingMessage = new FormMappingMessage(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
                this.formMappingCatch.put(formParam.getFormCode(), formMappingMessage);
                result.put(formParam.getFormKey(), formMappingMessage);
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException("\u8868\u5355\u5b9a\u4e49\u4e0d\u5b58\u5728", e);
        }
    }

    @Override
    public Map<String, RegionMappingMessage> getRegionMapping(TaskMappingMessage taskParam, FormSchemeMappingMessage formSchemeParam, FormMappingMessage formParam, List<RegionMappingMessage> regionParams) {
        HashMap<String, RegionMappingMessage> result = new HashMap<String, RegionMappingMessage>();
        Map<String, FormMappingMessage> formMapping = this.getFormMapping(taskParam, formSchemeParam, Arrays.asList(formParam));
        try {
            FormMappingMessage formMappingMessage = formMapping.values().iterator().next();
            List mappingDataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formMappingMessage.getFormKey());
            if (null == mappingDataRegionDefines || mappingDataRegionDefines.isEmpty()) {
                throw new RuntimeException("\u533a\u57df\u5b9a\u4e49\u4e0d\u5b58\u5728");
            }
            ArrayList<String> noFindRegionKey = new ArrayList<String>();
            for (RegionMappingMessage regionParam : regionParams) {
                boolean haveData = false;
                for (DataRegionDefine mappingDataRegionDefine : mappingDataRegionDefines) {
                    if (regionParam.getRegionLeft() != mappingDataRegionDefine.getRegionLeft() || regionParam.getRegionRight() != mappingDataRegionDefine.getRegionRight() || regionParam.getRegionTop() != mappingDataRegionDefine.getRegionTop() || regionParam.getRegionBottom() != mappingDataRegionDefine.getRegionBottom() || regionParam.getType() != mappingDataRegionDefine.getRegionKind().getValue()) continue;
                    RegionMappingMessage mappingRegionMessage = new RegionMappingMessage(mappingDataRegionDefine.getKey(), mappingDataRegionDefine.getRegionLeft(), mappingDataRegionDefine.getRegionRight(), mappingDataRegionDefine.getRegionTop(), mappingDataRegionDefine.getRegionBottom(), mappingDataRegionDefine.getRegionKind().getValue());
                    result.put(regionParam.getRegionKey(), mappingRegionMessage);
                    haveData = true;
                    break;
                }
                if (haveData) continue;
                result.put(regionParam.getRegionKey(), regionParam);
                noFindRegionKey.add(regionParam.getRegionKey());
            }
            if (!noFindRegionKey.isEmpty()) {
                logger.info("\u672a\u627e\u5230\u6620\u5c04\u540e\u533a\u57df\uff0c\u4f7f\u7528\u6e90\u533a\u57df\uff1a" + String.join((CharSequence)";", noFindRegionKey));
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException("\u533a\u57df\u5b9a\u4e49\u4e0d\u5b58\u5728", e);
        }
    }

    @Override
    public Map<String, LinkMappingMessage> getLinkMapping(TaskMappingMessage taskParam, FormSchemeMappingMessage formSchemeParam, FormMappingMessage formParam, RegionMappingMessage regionParam, List<LinkMappingMessage> linkParams) {
        HashMap<String, LinkMappingMessage> result = new HashMap<String, LinkMappingMessage>();
        Map<String, RegionMappingMessage> regionMapping = this.getRegionMapping(taskParam, formSchemeParam, formParam, Arrays.asList(regionParam));
        try {
            RegionMappingMessage regionMappingMessage = regionMapping.values().iterator().next();
            List dataLinkDefines = this.runTimeViewController.getAllLinksInRegion(regionMappingMessage.getRegionKey());
            if (null == dataLinkDefines || dataLinkDefines.isEmpty()) {
                return null;
            }
            ArrayList<String> noFindLinkKey = new ArrayList<String>();
            for (LinkMappingMessage linkParam : linkParams) {
                boolean haveData = false;
                for (DataLinkDefine mappingLinkDefine : dataLinkDefines) {
                    if (linkParam.getPosX() != mappingLinkDefine.getPosX() || linkParam.getPosY() != mappingLinkDefine.getPosY()) continue;
                    LinkMappingMessage mappingLinkMessage = new LinkMappingMessage(mappingLinkDefine.getKey(), mappingLinkDefine.getPosX(), mappingLinkDefine.getPosY());
                    result.put(linkParam.getLinkKey(), mappingLinkMessage);
                    haveData = true;
                    break;
                }
                if (haveData) continue;
                result.put(linkParam.getLinkKey(), linkParam);
                noFindLinkKey.add(linkParam.getLinkKey());
            }
            if (!noFindLinkKey.isEmpty()) {
                logger.info("\u672a\u627e\u5230\u6620\u5c04\u540e\u94fe\u63a5\uff0c\u4f7f\u7528\u6e90\u94fe\u63a5\uff1a" + String.join((CharSequence)";", noFindLinkKey));
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException("\u94fe\u63a5\u5b9a\u4e49\u4e0d\u5b58\u5728", e);
        }
    }
}

