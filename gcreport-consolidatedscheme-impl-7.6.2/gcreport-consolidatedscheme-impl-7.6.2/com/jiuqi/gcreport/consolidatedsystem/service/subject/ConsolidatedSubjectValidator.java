/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.service.subject;

import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import java.util.List;

public interface ConsolidatedSubjectValidator {
    public ValidatorResult deleteValidator(List<ConsolidatedSubjectEO> var1);

    public static class ValidatorResult {
        private boolean success;
        private String message;

        private ValidatorResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public static ValidatorResult success() {
            return new ValidatorResult(true, null);
        }

        public static ValidatorResult error(String message) {
            return new ValidatorResult(false, message);
        }

        public boolean isSuccess() {
            return this.success;
        }

        public String getMessage() {
            return this.message;
        }
    }
}

