/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.configurations.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.deserializer.RuleMapDeserializer;

@JsonDeserialize(using=RuleMapDeserializer.class)
public class RuleMap
implements Serializable {
    private static final long serialVersionUID = 1983924000402638863L;
    public static final String RULE_CODE = "rule";
    public static final String SINGLE_CODE = "singleCode";
    public static final String NET_CODE = "netCode";
    private RuleKind rule;
    private String singleCode;
    private String netCode;

    public RuleMap() {
    }

    public RuleMap(RuleKind rule, String singleCode, String netCode) {
        this.rule = rule;
        this.singleCode = singleCode;
        this.netCode = netCode;
    }

    public RuleKind getRule() {
        return this.rule;
    }

    public void setRule(RuleKind rule) {
        this.rule = rule;
    }

    public String getSingleCode() {
        return this.singleCode;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public String getNetCode() {
        return this.netCode;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }
}

