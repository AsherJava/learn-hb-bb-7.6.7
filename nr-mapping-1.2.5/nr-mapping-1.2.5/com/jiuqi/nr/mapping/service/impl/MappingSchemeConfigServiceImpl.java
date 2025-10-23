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
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.mapping.service.impl;

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
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping.bean.MappingConfig;
import com.jiuqi.nr.mapping.common.MappingErrorEnum;
import com.jiuqi.nr.mapping.dao.MappingSchemeDao;
import com.jiuqi.nr.mapping.service.MappingSchemeConfigService;
import com.jiuqi.nr.mapping.web.vo.EntityVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MappingSchemeConfigServiceImpl
implements MappingSchemeConfigService {
    @Autowired
    private MappingSchemeDao mappingSchemeDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormulaRunTimeController runTimeFormula;
    @Autowired
    private RunTimeAuthViewController runTime;

    @Override
    public MappingConfig query(String msKey) throws JQException {
        MappingConfig config = null;
        String data = this.mappingSchemeDao.getConfig(msKey);
        config = this.getMappingConfig(data);
        return config;
    }

    @Override
    public void update(String msKey, MappingConfig config) throws JQException {
        this.mappingSchemeDao.updateConfig(msKey, this.getMappingConfigByte(config));
    }

    @Override
    public List<EntityVO> queryEntityAttribute(String formSchemeKey) throws Exception {
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        if (entityId == null) {
            return Collections.emptyList();
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        Iterator attributes = entityModel.getAttributes();
        ArrayList<EntityVO> entityAttributes = new ArrayList<EntityVO>();
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

    private String getMappingConfigByte(MappingConfig config) throws JQException {
        if (null == config) {
            return "";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString((Object)config);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_001);
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
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_002);
        }
    }

    private String getEntityIdByFormSchemeKey(String formSchemeKey) throws Exception {
        FormSchemeDefine formSchemeDefine = this.runTime.getFormScheme(formSchemeKey);
        String dw = formSchemeDefine.getDw();
        if (!StringUtils.hasText(dw)) {
            TaskDefine designTaskDefine = this.runTime.queryTaskDefine(formSchemeDefine.getTaskKey());
            dw = designTaskDefine.getDw();
        }
        return dw;
    }
}

