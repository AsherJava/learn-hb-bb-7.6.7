/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import com.jiuqi.budget.domain.BudCalChartNodeData;
import java.math.BigDecimal;

public class BudCalChartNodeItem<E extends BudCalChartNodeData> {
    private String id;
    private E data;
    private boolean flated;
    private String text;
    private boolean disableDefaultClickEffect;
    private Integer width;
    private Integer height;
    private String imageId;
    private boolean expanded = true;
    private boolean fixed = false;
    private BigDecimal x;
    private BigDecimal y;

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public boolean isDisableDefaultClickEffect() {
        return this.disableDefaultClickEffect;
    }

    public void setDisableDefaultClickEffect(boolean disableDefaultClickEffect) {
        this.disableDefaultClickEffect = disableDefaultClickEffect;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean isFlated() {
        return this.flated;
    }

    public void setFlated(boolean flated) {
        this.flated = flated;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public BigDecimal getX() {
        return this.x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return this.y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public String toString() {
        return "BudCalChartNodeItem [id=" + this.id + ", data=" + this.data + "]";
    }
}

