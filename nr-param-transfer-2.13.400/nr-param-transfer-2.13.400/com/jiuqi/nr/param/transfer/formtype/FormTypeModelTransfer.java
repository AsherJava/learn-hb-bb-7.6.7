/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.formtype.facade.FormTypeDataDefine
 *  com.jiuqi.nr.formtype.facade.FormTypeDefine
 *  com.jiuqi.nr.formtype.facade.FormTypeGroupDefine
 *  com.jiuqi.nr.formtype.service.IFormTypeGroupService
 *  com.jiuqi.nr.formtype.service.IFormTypeService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.param.transfer.formtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeDTO;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeDataDTO;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeGroupDTO;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeTransferDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class FormTypeModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormTypeModelTransfer.class);
    private IFormTypeService formTypeService;
    private IFormTypeGroupService formTypeGroupService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void init() {
        this.formTypeService = (IFormTypeService)BeanUtil.getBean(IFormTypeService.class);
        this.formTypeGroupService = (IFormTypeGroupService)BeanUtil.getBean(IFormTypeGroupService.class);
    }

    @Transactional(rollbackFor={Exception.class})
    public void importModel(IImportContext iImportContext, byte[] bytes) throws TransferException {
        FormTypeTransferDTO formTypeTransferDTO;
        this.init();
        try {
            formTypeTransferDTO = (FormTypeTransferDTO)this.objectMapper.readValue(bytes, FormTypeTransferDTO.class);
        }
        catch (IOException e) {
            throw new TransferException("\u62a5\u8868\u7c7b\u578b\u8bfb\u53d6\u6570\u636e\u5f02\u5e38\uff01", (Throwable)e);
        }
        this.importGroup(formTypeTransferDTO);
        FormTypeDTO formTypeDTO = formTypeTransferDTO.getFormTypeDTO();
        this.importFormType(formTypeDTO);
        List<FormTypeDataDTO> formTypeDataDTO = formTypeTransferDTO.getFormTypeDataDTO();
        this.importFormTypeData(formTypeDTO.getCode(), formTypeDataDTO);
    }

    private void importFormTypeData(String formTypeCode, List<FormTypeDataDTO> formTypeDataDTOS) throws TransferException {
        StringBuilder log = new StringBuilder("\u62a5\u8868\u7c7b\u578bCODE=");
        ArrayList<FormTypeDataDefine> update = new ArrayList<FormTypeDataDefine>();
        ArrayList<FormTypeDataDefine> add = new ArrayList<FormTypeDataDefine>();
        try {
            log.append(formTypeCode);
            Set collect = this.formTypeService.queryFormTypeDatas(formTypeCode).stream().map(FormTypeDataDefine::getCode).collect(Collectors.toSet());
            this.importLinkMessage("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u6570\u636e\uff0c\u6570\u636e\u5e93\u5df2\u6709\u6570\u636e\u7684code", add.stream().map(FormTypeDataDefine::getCode).collect(Collectors.toList()));
            for (FormTypeDataDTO dataDTO : formTypeDataDTOS) {
                if (collect.contains(dataDTO.getCode())) {
                    update.add(dataDTO.convertDefine());
                    continue;
                }
                add.add(dataDTO.convertDefine());
            }
            this.importLinkMessage("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u6570\u636e\uff0c\u65b0\u589e\u7684code", add.stream().map(FormTypeDataDefine::getCode).collect(Collectors.toList()));
            this.importLinkMessage("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u6570\u636e\uff0c\u66f4\u65b0\u7684code", update.stream().map(FormTypeDataDefine::getCode).collect(Collectors.toList()));
            this.formTypeService.insertFormTypeData(add);
            this.formTypeService.updateFormTypeData(update);
        }
        catch (JQException e) {
            throw new TransferException("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u3010\u6570\u636e\u9879\u3011\u5931\u8d25" + log, (Throwable)e);
        }
    }

    private void importFormType(FormTypeDTO formTypeDTO) throws TransferException {
        StringBuilder log = new StringBuilder("CODE=");
        try {
            log.append(formTypeDTO.getCode());
            FormTypeDefine temp = this.formTypeService.queryFormType(formTypeDTO.getCode());
            this.oldResourceExistMessage(temp != null, "code\u67e5\u8be2\u62a5\u8868\u7c7b\u578b\u5b9a\u4e49" + (temp != null ? temp.getCode() : "null"), formTypeDTO.getTitle());
            if (temp != null) {
                formTypeDTO.setId(temp.getId());
                this.formTypeService.updateFormType(formTypeDTO.convertDefine());
            } else {
                this.formTypeService.insertFormTypeNoCheck(formTypeDTO.convertDefine(), false);
            }
        }
        catch (JQException e) {
            throw new TransferException("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u5931\u8d25\uff01" + log, (Throwable)e);
        }
    }

    private void importGroup(FormTypeTransferDTO formTypeTransferDTO) throws TransferException {
        StringBuilder log = new StringBuilder("ID=");
        try {
            Set groupIdSet = this.formTypeGroupService.queryAll().stream().map(FormTypeGroupDefine::getId).collect(Collectors.toSet());
            List<FormTypeGroupDTO> formTypeGroupDTOList = formTypeTransferDTO.getFormTypeGroupDTOList();
            for (FormTypeGroupDTO groupDefine : formTypeGroupDTOList) {
                log.append(groupDefine.getId());
                this.oldResourceExistMessage(groupIdSet.contains(groupDefine.getId()), "key\u67e5\u8be2\u62a5\u8868\u7c7b\u578b\u5206\u7ec4" + groupDefine.getId(), groupDefine.getTitle());
                if (groupIdSet.contains(groupDefine.getId())) {
                    this.formTypeGroupService.updateFormTypeGroup(groupDefine.convertDefine());
                    continue;
                }
                this.formTypeGroupService.insertFormTypeGroup(groupDefine.convertDefine());
            }
        }
        catch (JQException e) {
            throw new TransferException("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5931\u8d25\uff01" + log, (Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public MetaExportModel exportModel(IExportContext iExportContext, String formTypeCode) throws TransferException {
        FormTypeDefine formTypeDefine;
        this.init();
        FormTypeTransferDTO formTypeTransferDTO = new FormTypeTransferDTO();
        try {
            formTypeDefine = this.formTypeService.queryFormType(formTypeCode);
        }
        catch (JQException e) {
            throw new TransferException("\u5bfc\u51fa\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\uff0c\u83b7\u53d6\u6570\u636e\u5f02\u5e38", (Throwable)e);
        }
        this.exportGroup(formTypeDefine.getGroupId(), formTypeTransferDTO);
        formTypeTransferDTO.initFormTypeDTO(formTypeDefine);
        List formTypeDataDefines = this.formTypeService.queryFormTypeDatas(formTypeDefine.getCode());
        formTypeTransferDTO.initFormTypeDataDTO(formTypeDataDefines);
        MetaExportModel meta = new MetaExportModel();
        try {
            meta.setData(this.objectMapper.writeValueAsBytes((Object)formTypeTransferDTO));
        }
        catch (JsonProcessingException e) {
            throw new TransferException("\u62a5\u8868\u7c7b\u578b\u76f8\u5173\u6570\u636e\u5e8f\u5217\u5316\u5f02\u5e38\uff01", (Throwable)e);
        }
        return meta;
    }

    private void exportGroup(String groupId, FormTypeTransferDTO formTypeTransferDTO) {
        List<FormTypeGroupDTO> formTypeGroupDTOList = formTypeTransferDTO.getFormTypeGroupDTOList();
        Map<String, FormTypeGroupDefine> groupDefineMap = this.formTypeGroupService.queryAll().stream().collect(Collectors.toMap(FormTypeGroupDefine::getId, p -> p));
        if (groupDefineMap.isEmpty()) {
            return;
        }
        while (!"--".equals(groupId)) {
            FormTypeGroupDefine groupDefine = groupDefineMap.get(groupId);
            if (groupDefine == null) continue;
            formTypeGroupDTOList.add(new FormTypeGroupDTO(groupDefine));
            groupId = groupDefine.getGroupId();
        }
    }

    private void oldResourceExistMessage(boolean oldResourceNotEmpty, String resourceTypeName, String messageTitle) {
        if (logger.isDebugEnabled()) {
            if (oldResourceNotEmpty) {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684%s\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u66f4\u65b0\uff0c\u5176title\u662f\uff1a %s", resourceTypeName, messageTitle));
            } else {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684%s\u4e0d\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u65b0\u589e\uff01", resourceTypeName));
            }
        }
    }

    private void importLinkMessage(String resourceTypeName, Collection resource) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s\u6709\uff1a", resourceTypeName) + resource);
        }
    }
}

