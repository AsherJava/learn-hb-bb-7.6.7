/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.validator.DefaultDataSchemeValidator
 */
package com.jiuqi.nr.query.datascheme.service.impl;

import com.jiuqi.nr.datascheme.internal.validator.DefaultDataSchemeValidator;
import com.jiuqi.nr.query.datascheme.service.IQueryDataSchemeValidator;
import org.springframework.stereotype.Service;

@Service(value="QueryDataSchemeValidator")
public class QueryDataSchemeValidatorImpl
extends DefaultDataSchemeValidator
implements IQueryDataSchemeValidator {
}

