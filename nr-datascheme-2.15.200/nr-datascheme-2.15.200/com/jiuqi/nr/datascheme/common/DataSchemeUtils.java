/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datascheme.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeBeanUtils;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DataSchemeUtils {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeUtils.class);

    private DataSchemeUtils() {
    }

    public static List<String> getValidationsStr(DataField field) {
        if (null == field) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(field.getValidationRules())) {
            return Collections.emptyList();
        }
        ArrayList<String> result = new ArrayList<String>();
        for (ValidationRule rule : field.getValidationRules()) {
            result.add(rule.getVerification());
        }
        return result;
    }

    public static List<String> getValidationRulesStr(DataField field) {
        if (null == field) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(field.getValidationRules())) {
            return Collections.emptyList();
        }
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> result = new ArrayList<String>();
        for (ValidationRule rule : field.getValidationRules()) {
            ValidationRuleObj validationRuleObj = new ValidationRuleObj();
            validationRuleObj.setMessage(rule.getMessage());
            validationRuleObj.setVerification(rule.getVerification());
            try {
                String ruleStr = mapper.writeValueAsString((Object)validationRuleObj);
                result.add(ruleStr);
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    public static ValidationRule getValidationRule(String validationRuleStr) {
        ValidationRuleDTO validationRuleDTO = new ValidationRuleDTO();
        if (StringUtils.hasText(validationRuleStr)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ValidationRuleObj validationRuleObj = (ValidationRuleObj)mapper.readValue(validationRuleStr, ValidationRuleObj.class);
                validationRuleDTO.setMessage(validationRuleObj.getMessage());
                validationRuleDTO.setVerification(validationRuleObj.getVerification());
                return validationRuleDTO;
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static boolean isSet(String level) {
        if (level == null || "0".equals(level)) {
            return false;
        }
        return StringUtils.hasLength(level);
    }

    public static boolean isSingleSelect(DataDimension dim) {
        if (DimensionType.DIMENSION != dim.getDimensionType()) {
            return false;
        }
        DataDimension unit = DataSchemeBeanUtils.getRuntimeDataSchemeService().getDataSchemeDimension(dim.getDataSchemeKey(), DimensionType.UNIT).stream().findAny().orElse(null);
        if (null == unit) {
            return false;
        }
        return DataSchemeUtils.isSingleSelect(unit, dim);
    }

    public static boolean isSingleSelect(DataDimension unit, DataDimension dim) {
        IEntityAttribute attribute;
        IEntityMetaService entityMetaService = DataSchemeBeanUtils.getEntityMetaService();
        IEntityModel entityModel = entityMetaService.getEntityModel(unit.getDimKey());
        String refField = dim.getDimAttribute();
        if (!StringUtils.hasText(refField)) {
            List entityRefer = entityMetaService.getEntityRefer(unit.getDimKey());
            if (CollectionUtils.isEmpty(entityRefer)) {
                return false;
            }
            for (IEntityRefer iEntityRefer : entityRefer) {
                if (!dim.getDimKey().equals(iEntityRefer.getReferEntityId())) continue;
                refField = iEntityRefer.getOwnField();
                break;
            }
        }
        return null != (attribute = entityModel.getAttribute(refField)) && !attribute.isMultival();
    }

    public static <T extends Serializable> String serializeToString(T obj) {
        String result = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);){
            oos.writeObject(obj);
            result = Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static <T extends Serializable> T deserializeFromString(String data, final Class<T> t) {
        if (!StringUtils.hasText(data)) {
            return null;
        }
        Serializable result = null;
        byte[] byteArray = Base64.getDecoder().decode(data);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bais){

            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                return t != null && desc.getName().equals(t.getName()) ? t : super.resolveClass(desc);
            }
        };){
            result = (Serializable)ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return (T)result;
    }

    public static String getUUID(String str) {
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new InternalError("MD5 not supported", e);
        }
        byte[] data = md.digest(str.getBytes());
        long msb = 0L;
        long lsb = 0L;
        assert (data.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb).toString();
    }

    public static String getEntityBizKeys(String entityId) {
        PeriodEngineService periodEngineService = DataSchemeBeanUtils.getPeriodEngineService();
        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
        if (periodAdapter.isPeriodEntity(entityId)) {
            TableModelDefine tableModel = periodAdapter.getPeriodEntityTableModel(entityId);
            return tableModel.getBizKeys();
        }
        IEntityMetaService entityMetaService = DataSchemeBeanUtils.getEntityMetaService();
        TableModelDefine tableModel = entityMetaService.getTableModel(entityId);
        if (null != tableModel) {
            return tableModel.getBizKeys();
        }
        return null;
    }

    static class ValidationRuleObj {
        private String message;
        private String verification;

        ValidationRuleObj() {
        }

        public String getVerification() {
            return this.verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

