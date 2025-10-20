/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationOperation
 */
package com.jiuqi.gcreport.financialcheckImpl.automation;

import com.jiuqi.nvwa.core.automation.annotation.AutomationOperation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Inherited
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@AutomationOperation(operation="financialCheck", desc="\u81ea\u52a8\u5bf9\u8d26")
@interface FinancialCheckOperation {
}

