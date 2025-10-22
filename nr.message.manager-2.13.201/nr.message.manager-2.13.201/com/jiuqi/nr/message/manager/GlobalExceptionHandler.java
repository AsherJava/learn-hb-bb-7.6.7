/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ValidationException
 */
package com.jiuqi.nr.message.manager;

import com.jiuqi.nr.message.manager.BusinessResponseEntity;
import javax.validation.ValidationException;

@Deprecated
public class GlobalExceptionHandler {
    public BusinessResponseEntity handle(ValidationException exception) {
        return BusinessResponseEntity.error(exception.getMessage());
    }
}

