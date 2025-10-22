/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formtype.org.extend.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public class FormTypeOrgExtendException
extends RuntimeException {
    private static final long serialVersionUID = 4941042601074666376L;

    public FormTypeOrgExtendException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
    }
}

