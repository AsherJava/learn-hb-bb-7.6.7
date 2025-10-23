/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  javax.validation.groups.Default
 */
package com.jiuqi.nr.zb.scheme.internal.excel.validation;

import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.DateExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.DefaultExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.DoubleExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelCheck;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.IntegerExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.StringExcelReaderValidator;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ExcelZbReaderValidatorImpl
implements ExcelReaderValidator {
    private static final Map<ZbDataType, ExcelReaderValidator> validators = new EnumMap<ZbDataType, ExcelReaderValidator>(ZbDataType.class);
    private static final ExcelReaderValidator defaultValidator = new DefaultExcelReaderValidator();
    private final Validator validator;

    public ExcelZbReaderValidatorImpl() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean.getValidator();
    }

    @Override
    public <T> void validate(T data, IExcelRowWrapper rowWrapper) {
        ZbDataType dataType;
        ZbInfoDTO zbInfo = (ZbInfoDTO)data;
        Set validate = this.validator.validate(data, new Class[]{ExcelCheck.class, Default.class});
        if (!CollectionUtils.isEmpty(validate)) {
            ArrayList<String> errors = new ArrayList<String>(validate.size());
            for (ConstraintViolation constraintViolation : validate) {
                errors.add(constraintViolation.getMessage());
            }
            rowWrapper.addErrors(errors);
        }
        if (validators.containsKey((Object)(dataType = zbInfo.getDataType()))) {
            ExcelReaderValidator readerValidator = validators.get((Object)dataType);
            readerValidator.validate(zbInfo, rowWrapper);
            readerValidator.afterValidate(zbInfo);
        } else {
            defaultValidator.validate(zbInfo, rowWrapper);
            defaultValidator.afterValidate(zbInfo);
        }
    }

    static {
        validators.put(ZbDataType.STRING, new StringExcelReaderValidator());
        validators.put(ZbDataType.INTEGER, new IntegerExcelReaderValidator());
        validators.put(ZbDataType.BIGDECIMAL, new DoubleExcelReaderValidator());
        validators.put(ZbDataType.DATE, new DateExcelReaderValidator());
    }
}

