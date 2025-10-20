/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;
import java.util.UUID;

public class PasswordRuleDTO
implements Serializable {
    private static final long serialVersionUID = 3991043532287019633L;
    public static final String DEFAULT_NAME = "default";
    public static final String DEFAULT_TITLE = "\u9ed8\u8ba4\u5bc6\u7801\u89c4\u5219";
    protected String ruleKey;
    protected String ruleName;
    protected String ruleTitle;
    protected boolean ruleActive;
    protected int lengthMin;
    protected int lengthMax;
    protected int charAllowed;
    protected int charNecessary;
    protected int charForbidden;
    protected int differentTimes;

    public static PasswordRuleDTO getDefaultRule() {
        return DefaultRule.DEFAULT;
    }

    public static void changeDefaultRuleActive(boolean isActive) {
        DefaultRule.cancleDefaultRuleActive(isActive);
    }

    public PasswordRuleDTO() {
    }

    public PasswordRuleDTO(String ruleKey, String ruleName, String ruleTitle, boolean ruleActive, int lengthMin, int lengthMax, int charAllowed, int charNecessary, int charForbidden, int differentTimes) {
        this.ruleKey = ruleKey;
        this.ruleName = ruleName;
        this.ruleTitle = ruleTitle;
        this.ruleActive = ruleActive;
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
        this.charAllowed = charAllowed;
        this.charNecessary = charNecessary;
        this.charForbidden = charForbidden;
        this.differentTimes = differentTimes;
    }

    public String getRuleKey() {
        return this.ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public boolean isRuleActive() {
        return this.ruleActive;
    }

    public void setRuleActive(boolean ruleActive) {
        this.ruleActive = ruleActive;
    }

    public int getLengthMin() {
        return this.lengthMin;
    }

    public void setLengthMin(int lengthMin) {
        this.lengthMin = lengthMin;
    }

    public int getLengthMax() {
        return this.lengthMax;
    }

    public void setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
    }

    public int getCharAllowed() {
        return this.charAllowed;
    }

    public void setCharAllowed(int charAllowed) {
        this.charAllowed = charAllowed;
    }

    public int getCharNecessary() {
        return this.charNecessary;
    }

    public void setCharNecessary(int charNecessary) {
        this.charNecessary = charNecessary;
    }

    public int getCharForbidden() {
        return this.charForbidden;
    }

    public void setCharForbidden(int charForbidden) {
        this.charForbidden = charForbidden;
    }

    public int getDifferentTimes() {
        return this.differentTimes;
    }

    public void setDifferentTimes(int differentTimes) {
        this.differentTimes = differentTimes;
    }

    private static class DefaultRule {
        private static PasswordRuleDTO DEFAULT = new PasswordRuleDTO(UUID.randomUUID().toString(), "default", "\u9ed8\u8ba4\u5bc6\u7801\u89c4\u5219", true, 1, Integer.MAX_VALUE, 0, 0, 0, 0);

        private DefaultRule() {
        }

        public static void cancleDefaultRuleActive(boolean isActive) {
            DEFAULT.setRuleActive(isActive);
        }
    }
}

