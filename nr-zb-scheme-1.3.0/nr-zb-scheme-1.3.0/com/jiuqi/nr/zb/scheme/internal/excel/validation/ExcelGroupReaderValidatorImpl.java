/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  javax.validation.groups.Default
 */
package com.jiuqi.nr.zb.scheme.internal.excel.validation;

import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelCheck;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.utils.ExcelUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ExcelGroupReaderValidatorImpl
implements ExcelReaderValidator {
    private final Validator validator;
    private final Map<String, Set<String>> titleMap;
    private final Set<String> codes;

    public ExcelGroupReaderValidatorImpl() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean.getValidator();
        this.codes = new HashSet<String>(32);
        this.titleMap = new HashMap<String, Set<String>>(32);
    }

    @Override
    public <T> void validate(T data, IExcelRowWrapper rowWrapper) {
        Set validate;
        Set<Object> set;
        ZbGroupDTO groupDTO = (ZbGroupDTO)data;
        String parentKey = groupDTO.getParentKey();
        if (!StringUtils.hasLength(parentKey)) {
            parentKey = "GROUP_00";
            groupDTO.setParentKey(parentKey);
        }
        if (this.codes.contains(groupDTO.getKey())) {
            rowWrapper.addError("\u5206\u7ec4\u4ee3\u7801\u91cd\u590d" + groupDTO.getKey());
        } else {
            this.codes.add(groupDTO.getKey());
        }
        if (this.titleMap.containsKey(parentKey)) {
            String value;
            set = this.titleMap.get(parentKey);
            if (set.contains(value = groupDTO.getTitle())) {
                rowWrapper.addError("\u5206\u7ec4\u6807\u9898\u91cd\u590d");
            } else {
                set.add(value);
            }
        } else {
            set = new HashSet<String>();
            set.add(groupDTO.getTitle());
            this.titleMap.put(parentKey, set);
        }
        if (!this.isChild(groupDTO.getParentKey(), groupDTO.getKey())) {
            rowWrapper.addError("\u5206\u7ec4\u4ee3\u7801\u548c\u4e0a\u7ea7\u4ee3\u7801\u4e0d\u6b63\u786e");
        }
        if (!CollectionUtils.isEmpty(validate = this.validator.validate(data, new Class[]{ExcelCheck.class, Default.class}))) {
            ArrayList<String> errors = new ArrayList<String>(validate.size());
            for (ConstraintViolation constraintViolation : validate) {
                errors.add(constraintViolation.getMessage());
            }
            rowWrapper.addErrors(errors);
        }
    }

    private boolean isChild(String parentKey, String key) {
        if (parentKey == null || key == null) {
            return false;
        }
        if ("GROUP_00".equals(parentKey)) {
            return key.length() == "GROUP_00".length() && key.compareTo("GROUP_00") > 0;
        }
        if (!key.startsWith(parentKey)) {
            return false;
        }
        String next = ExcelUtils.next(ExcelUtils.child(parentKey));
        return next.length() == key.length() && key.compareTo(next) >= 0;
    }
}

