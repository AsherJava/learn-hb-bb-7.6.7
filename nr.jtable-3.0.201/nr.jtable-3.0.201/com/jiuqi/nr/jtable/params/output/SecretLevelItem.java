/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

@ApiModel(value="SecretLevelItem", description="\u5bc6\u7ea7\u9879")
public class SecretLevelItem {
    public static String NOSEE_NAME = "NOSEE";
    public static String NOSEE_TITLE = "\u8d85\u51fa\u5bc6\u7ea7";
    public static SecretLevelItem NOSEE = new SecretLevelItem(NOSEE_NAME, NOSEE_TITLE);
    @ApiModelProperty(value="\u5bc6\u7ea7name", name="name")
    private String name;
    @ApiModelProperty(value="\u5bc6\u7ea7\u6807\u9898", name="title")
    private String title;

    public SecretLevelItem() {
    }

    public SecretLevelItem(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SecretLevelItem that = (SecretLevelItem)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.title, that.title);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.title);
    }
}

