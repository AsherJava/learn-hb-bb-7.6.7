/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.util.SerializeUtils
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping2.bean.JIOConfig;
import com.jiuqi.nr.mapping2.bean.MappingConfig;
import com.jiuqi.nr.mapping2.common.MappingErrorEnum;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dao.JIOConfigDao;
import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.dto.JIOStateDTO;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.web.vo.EntityVO;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class JIOConfigServiceImpl
implements JIOConfigService {
    @Autowired
    private JIOConfigDao jioDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormulaRunTimeController runTimeFormula;
    @Autowired
    private RunTimeAuthViewController runTime;
    @Autowired
    IOrgMappingService orgMappingService;
    @Autowired
    IMappingSchemeService nvwaMappingScheme;

    @Override
    public void saveJIOFile(String file, String msKey) {
        if (StringUtils.hasText(file)) {
            Assert.notNull((Object)msKey, "JIO\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.jioDao.isExist(msKey)) {
            this.jioDao.addFile(file, msKey, UUID.randomUUID().toString());
        } else {
            this.jioDao.updateFile(file, msKey);
        }
    }

    @Override
    public String getJIOFileByMs(String msKey) {
        return this.jioDao.findFileByMS(msKey);
    }

    @Override
    public void saveJIOConfig(byte[] config, String msKey) {
        if (config == null || config.length == 0) {
            Assert.notNull((Object)msKey, "JIO\u6587\u4ef6\u89e3\u6790\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.jioDao.isExist(msKey)) {
            this.jioDao.addConfig(config, msKey, UUID.randomUUID().toString());
        } else {
            this.jioDao.updateConfig(new String(config, StandardCharsets.UTF_8), msKey);
        }
    }

    @Override
    public byte[] getJIOConfigByMs(String msKey) {
        String jio = this.jioDao.findConfigByMS(msKey);
        if (StringUtils.hasText(jio)) {
            return jio.getBytes(StandardCharsets.UTF_8);
        }
        return null;
    }

    @Override
    public void saveJIOContent(JIOContent content, String msKey) {
        if (content == null) {
            Assert.notNull((Object)msKey, "JIO\u6587\u4ef6\u914d\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try {
            byte[] contentByte = SerializeUtils.jsonSerializeToByte((Object)content);
            if (this.jioDao.isExist(msKey)) {
                this.jioDao.addContent(contentByte, msKey, UUID.randomUUID().toString());
            } else {
                this.jioDao.updateContent(contentByte, msKey);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JIOContent getJIOContentByMs(String msKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] contentByte = this.jioDao.findContentByMS(msKey);
            if (contentByte != null) {
                return (JIOContent)objectMapper.readValue(contentByte, JIOContent.class);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<JIOContent> batchGetJIOContentByMs(List<String> msKeys) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<byte[]> contentByte = this.jioDao.batchFindContentByMS(msKeys);
        if (contentByte != null) {
            return contentByte.stream().map(content -> {
                try {
                    return (JIOContent)objectMapper.readValue(content, JIOContent.class);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveJIO(String msKey, String file, byte[] config, JIOContent content) {
        JIOConfig jioConfig = new JIOConfig();
        if (this.jioDao.isExist(msKey)) {
            jioConfig.setFile(file);
            jioConfig.setConfig(config);
            try {
                byte[] contentByte = SerializeUtils.jsonSerializeToByte((Object)content);
                jioConfig.setContent(contentByte);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            this.jioDao.updateByMS(msKey, jioConfig);
        } else {
            jioConfig.setKey(UUID.randomUUID().toString());
            jioConfig.setMsKey(msKey);
            jioConfig.setFile(file);
            jioConfig.setConfig(config);
            try {
                byte[] contentByte = SerializeUtils.jsonSerializeToByte((Object)content);
                jioConfig.setContent(contentByte);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            this.jioDao.add(jioConfig);
        }
    }

    @Override
    public boolean isJIOScheme(MappingScheme scheme) {
        if (scheme != null) {
            return this.isJIOScheme(NrMappingUtil.getNrMappingParam(scheme));
        }
        return false;
    }

    @Override
    public boolean isJIOScheme(NrMappingParam nrMappingParam) {
        if (nrMappingParam != null) {
            return "JIO".equals(nrMappingParam.getType());
        }
        return false;
    }

    @Override
    public JIOStateDTO isJIOSchemeWithFile(MappingScheme scheme) {
        JIOStateDTO dto = new JIOStateDTO();
        dto.setJioScheme(this.isJIOScheme(scheme));
        if (dto.isJioScheme()) {
            dto.setJioFile(this.jioDao.isExist(scheme.getKey()));
        }
        return dto;
    }

    @Override
    public JIOStateDTO isJIOSchemeWithFile(String msKey, NrMappingParam nrMappingParam) {
        JIOStateDTO dto = new JIOStateDTO();
        dto.setJioScheme(this.isJIOScheme(nrMappingParam));
        if (dto.isJioScheme()) {
            dto.setJioFile(this.jioDao.isExist(msKey));
        }
        return dto;
    }

    @Override
    public boolean isJIO(String msKey) {
        return this.isJIO(this.nvwaMappingScheme.getSchemeByKey(msKey));
    }

    @Override
    public boolean isJIO(MappingScheme scheme) {
        JIOStateDTO dto = this.isJIOSchemeWithFile(scheme);
        return dto.isJioScheme() && dto.isJioFile();
    }

    @Override
    public void deleteByMS(String msKey) {
        this.jioDao.deleteByMS(msKey);
    }

    @Override
    public MappingConfig queryMappingConfig(String msKey) throws JQException {
        String data = this.jioDao.getMappingConfig(msKey);
        return this.getMappingConfig(data);
    }

    @Override
    public void updateMappingConfig(String msKey, MappingConfig config) throws JQException {
        if (this.jioDao.isExist(msKey)) {
            String oldConfig;
            MappingConfig oldMappingConfig;
            if (!config.isConfigParentNode() && (oldMappingConfig = this.getMappingConfig(oldConfig = this.jioDao.getMappingConfig(msKey))) != null && oldMappingConfig.isConfigParentNode()) {
                this.orgMappingService.clearParentMappingByMS(msKey);
            }
            this.jioDao.updateMappingConfig(msKey, this.getMappingConfigByte(config));
        } else {
            this.jioDao.addMappingConfig(UUID.randomUUID().toString(), msKey, "00000000-0000-0000-0000-000000000000", this.getMappingConfigByte(config));
        }
    }

    @Override
    public List<EntityVO> queryEntityAttribute(String formSchemeKey) throws Exception {
        ArrayList<EntityVO> entityAttributes = new ArrayList<EntityVO>();
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        if (entityId == null) {
            return Collections.emptyList();
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            return entityAttributes;
        }
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            EntityVO vo = new EntityVO();
            vo.setKey(attribute.getID());
            vo.setCode(attribute.getCode());
            vo.setTitle(attribute.getTitle());
            entityAttributes.add(vo);
        }
        return entityAttributes;
    }

    @Override
    public List<FormulaSchemeDefine> getFormulaSchemesByReport(String reportKey) {
        List allFormula = this.runTimeFormula.getAllFormulaSchemeDefinesByFormScheme(reportKey);
        List emptyOrder = allFormula.stream().filter(f -> f.getOrder() == null).collect(Collectors.toList());
        List<FormulaSchemeDefine> sorted = allFormula.stream().filter(f -> f.getOrder() != null).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        sorted.addAll(emptyOrder);
        return sorted;
    }

    @Override
    public String queryEntityIdByFormSchemeKey(String formSchemeKey) throws Exception {
        return this.getEntityIdByFormSchemeKey(formSchemeKey);
    }

    private String getEntityIdByFormSchemeKey(String formSchemeKey) throws Exception {
        FormSchemeDefine formSchemeDefine = this.runTime.getFormScheme(formSchemeKey);
        if (formSchemeDefine == null) {
            return "";
        }
        String dw = formSchemeDefine.getDw();
        if (!StringUtils.hasText(dw)) {
            TaskDefine designTaskDefine = this.runTime.queryTaskDefine(formSchemeDefine.getTaskKey());
            dw = designTaskDefine.getDw();
        }
        return dw;
    }

    private String getMappingConfigByte(MappingConfig config) throws JQException {
        if (null == config) {
            return "";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString((Object)config);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_101);
        }
    }

    private MappingConfig getMappingConfig(String data) throws JQException {
        if (!StringUtils.hasText(data)) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return (MappingConfig)objectMapper.readValue(data, MappingConfig.class);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_102);
        }
    }
}

