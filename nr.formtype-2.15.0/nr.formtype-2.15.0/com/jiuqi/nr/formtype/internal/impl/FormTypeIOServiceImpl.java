/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.common.FormTypeExceptionEnum;
import com.jiuqi.nr.formtype.common.io.JsonDataConverter;
import com.jiuqi.nr.formtype.common.io.ZipUtils;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDefineImpl;
import com.jiuqi.nr.formtype.internal.bean.FormTypeGroupDefineImpl;
import com.jiuqi.nr.formtype.internal.dao.FormTypeDao;
import com.jiuqi.nr.formtype.internal.dao.FormTypeGroupDao;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IFormTypeIOService;
import com.jiuqi.va.domain.org.ZB;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class FormTypeIOServiceImpl
implements IFormTypeIOService {
    @Autowired
    private FormTypeGroupDao formTypeGroupDao;
    @Autowired
    private FormTypeDao formTypeDao;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static final Logger logger = LoggerFactory.getLogger(FormTypeIOServiceImpl.class);
    private static final String SUBFILE_DATA_VERSION = "1";
    private static final String SUBFILE_PATH_DIR = "\u62a5\u8868\u7c7b\u578b";
    private static final String SUBFILE_PATH_FORMTYPE_GROUP = "formtypegroup.json";
    private static final String SUBFILE_PATH_FORMTYPE = "formtype.json";

    @Override
    public boolean exportFormTypeByEntity(OutputStream outputStream, String ... entityIds) throws JQException {
        if (ObjectUtils.isEmpty(entityIds)) {
            return true;
        }
        HashSet<String> formTypeCodes = new HashSet<String>();
        for (String entityId : entityIds) {
            ZB orgFormTypeZb;
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            if (null == entityDefine || null == (orgFormTypeZb = this.iFormTypeApplyService.getFormTypeZb(entityDefine.getCode()))) continue;
            formTypeCodes.add(orgFormTypeZb.getReltablename());
        }
        if (CollectionUtils.isEmpty(formTypeCodes)) {
            return true;
        }
        this.exportFormType(outputStream, formTypeCodes.toArray(new String[0]));
        return false;
    }

    @Override
    public void exportFormType(OutputStream outputStream, String ... formTypeCodes) throws JQException {
        if (ObjectUtils.isEmpty(formTypeCodes)) {
            return;
        }
        List<FormTypeDefine> formTypeDefines = this.getFormTypeDefines(formTypeCodes);
        List<FormTypeGroupDefine> formTypeGroups = this.getFormTypeGroups(formTypeDefines);
        ArrayList<ZipUtils.ZipSubFile> zipSubFiles = new ArrayList<ZipUtils.ZipSubFile>();
        ZipUtils.ZipSubFile zipSubFile = null;
        try {
            zipSubFile = new ZipUtils.ZipSubFile(SUBFILE_PATH_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_FORMTYPE_GROUP, new JsonDataConverter<FormTypeGroupDefine[]>(formTypeGroups.toArray(new FormTypeGroupDefine[0]), SUBFILE_DATA_VERSION), null);
            zipSubFiles.add(zipSubFile);
            zipSubFile = new ZipUtils.ZipSubFile(SUBFILE_PATH_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_FORMTYPE, new JsonDataConverter<FormTypeDefine[]>(formTypeDefines.toArray(new FormTypeDefine[0]), SUBFILE_DATA_VERSION), null);
            zipSubFiles.add(zipSubFile);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.IMPORT_ERROR, (Throwable)e);
        }
        ZipUtils.zip(outputStream, zipSubFiles);
    }

    private List<FormTypeDefine> getFormTypeDefines(String ... formTypeCodes) {
        return this.formTypeDao.getByCodes(formTypeCodes);
    }

    private List<FormTypeGroupDefine> getFormTypeGroups(List<FormTypeDefine> formTypeDefines) {
        List<FormTypeGroupDefine> all = this.formTypeGroupDao.getAll();
        if (CollectionUtils.isEmpty(all)) {
            return Collections.emptyList();
        }
        Map<String, FormTypeGroupDefine> groupMap = all.stream().collect(Collectors.toMap(FormTypeGroupDefine::getId, v -> v));
        ArrayList<FormTypeGroupDefine> result = new ArrayList<FormTypeGroupDefine>();
        for (FormTypeDefine formDefine : formTypeDefines) {
            String id = formDefine.getGroupId();
            while (StringUtils.hasText(id) && !id.equals("--")) {
                FormTypeGroupDefine define = groupMap.get(id);
                id = define.getGroupId();
                result.add(define);
            }
        }
        return result;
    }

    @Override
    public void importFormType(InputStream inputStream) throws JQException {
        Map<String, ZipUtils.ZipSubFile> zipSubFileMap = ZipUtils.unZip(inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFileName, f -> f));
        if (CollectionUtils.isEmpty(zipSubFileMap)) {
            return;
        }
        SimpleModule simpleModule = this.getSimpleModule();
        FormTypeDefine[] formTypeDefines = null;
        FormTypeGroupDefine[] formTypeGroups = null;
        try {
            formTypeDefines = zipSubFileMap.get(SUBFILE_PATH_FORMTYPE).getSubFileJsonDataConverter(FormTypeDefine[].class, objectMapper -> objectMapper.registerModule((Module)simpleModule)).getData();
            formTypeGroups = zipSubFileMap.get(SUBFILE_PATH_FORMTYPE_GROUP).getSubFileJsonDataConverter(FormTypeGroupDefine[].class, objectMapper -> objectMapper.registerModule((Module)simpleModule)).getData();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.EXPORT_ERROR, (Throwable)e);
        }
        try {
            this.importFormType(formTypeGroups, formTypeDefines);
        }
        catch (BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.EXPORT_ERROR, e);
        }
    }

    private SimpleModule getSimpleModule() {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(FormTypeGroupDefine.class, FormTypeGroupDefineImpl.class);
        module.addAbstractTypeMapping(FormTypeDefine.class, FormTypeDefineImpl.class);
        return module;
    }

    private void importFormType(FormTypeGroupDefine[] formTypeGroups, FormTypeDefine[] formTypeDefines) throws BeanParaException, DBParaException {
        Map<String, String> groupIdMap = this.importFormTypeGroup(formTypeGroups);
        this.importFormType(groupIdMap, formTypeDefines);
    }

    private void importFormType(Map<String, String> groupIdMap, FormTypeDefine[] formTypeDefines) throws DBParaException {
        if (ObjectUtils.isEmpty(formTypeDefines)) {
            return;
        }
        Map<String, FormTypeDefine> formTypeMap = Arrays.stream(formTypeDefines).collect(Collectors.toMap(FormTypeDefine::getCode, v -> v));
        List<FormTypeDefine> byCodes = this.formTypeDao.getByCodes(formTypeMap.keySet().toArray(new String[0]));
        ArrayList<FormTypeDefine> updateFormTypes = new ArrayList<FormTypeDefine>();
        ArrayList<FormTypeDefine> addFormTypes = new ArrayList<FormTypeDefine>();
        for (FormTypeDefine formType : byCodes) {
            if (groupIdMap.containsKey(formType.getGroupId())) {
                formType.setGroupId(groupIdMap.get(formType.getGroupId()));
            }
            if (formTypeMap.containsKey(formType.getCode())) {
                FormTypeDefine updateFormType = formTypeMap.get(formType.getCode());
                updateFormType.setId(formType.getId());
                updateFormTypes.add(updateFormType);
                continue;
            }
            addFormTypes.add(formType);
        }
        if (!CollectionUtils.isEmpty(updateFormTypes)) {
            this.formTypeDao.update(updateFormTypes.toArray());
        }
        if (!CollectionUtils.isEmpty(addFormTypes)) {
            this.formTypeDao.insert(addFormTypes.toArray());
        }
    }

    private Map<String, String> importFormTypeGroup(FormTypeGroupDefine[] formTypeGroups) throws DBParaException {
        if (ObjectUtils.isEmpty(formTypeGroups)) {
            return Collections.emptyMap();
        }
        HashMap<String, Map<String, FormTypeGroupDefine>> dbGorupTree = new HashMap<String, Map<String, FormTypeGroupDefine>>();
        for (FormTypeGroupDefine group : this.formTypeGroupDao.getAll()) {
            if (!dbGorupTree.containsKey(group.getGroupId())) {
                HashMap map = new HashMap();
                dbGorupTree.put(group.getGroupId(), map);
            }
            ((Map)dbGorupTree.get(group.getGroupId())).put(group.getTitle(), group);
        }
        Map<String, List<FormTypeGroupDefine>> importGorupTree = Arrays.stream(formTypeGroups).collect(Collectors.groupingBy(FormTypeGroupDefine::getGroupId));
        HashMap<String, String> groupIdMap = new HashMap<String, String>();
        ArrayList<FormTypeGroupDefine> addGroups = new ArrayList<FormTypeGroupDefine>();
        String parentGroupId = "--";
        this.importFormTypeGroup(dbGorupTree, importGorupTree, parentGroupId, parentGroupId, groupIdMap, addGroups);
        if (CollectionUtils.isEmpty(addGroups)) {
            this.formTypeGroupDao.insert(addGroups.toArray());
        }
        return groupIdMap;
    }

    private void importFormTypeGroup(Map<String, Map<String, FormTypeGroupDefine>> dbGorupTree, Map<String, List<FormTypeGroupDefine>> importGorupTree, String dbParentGroupId, String importParentGroupId, Map<String, String> groupIdMap, List<FormTypeGroupDefine> addGroups) {
        List<FormTypeGroupDefine> importGroupList = importGorupTree.get(importParentGroupId);
        for (FormTypeGroupDefine group : importGroupList) {
            if (dbGorupTree.get(dbParentGroupId).containsKey(group.getTitle())) {
                FormTypeGroupDefine dbGroup = dbGorupTree.get(dbParentGroupId).get(group.getTitle());
                groupIdMap.put(group.getId(), dbGroup.getId());
                if (!importGorupTree.containsKey(group.getId())) continue;
                this.importFormTypeGroup(dbGorupTree, importGorupTree, dbGroup.getId(), group.getId(), groupIdMap, addGroups);
                continue;
            }
            if (groupIdMap.containsKey(group.getGroupId())) {
                group.setGroupId(groupIdMap.get(group.getGroupId()));
            }
            addGroups.add(group);
            if (!importGorupTree.containsKey(group.getId())) continue;
            addGroups.addAll((Collection<FormTypeGroupDefine>)importGorupTree.get(group.getId()));
        }
    }
}

