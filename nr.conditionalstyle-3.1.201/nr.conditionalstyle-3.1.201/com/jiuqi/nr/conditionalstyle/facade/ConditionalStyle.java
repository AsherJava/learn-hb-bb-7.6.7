/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.conditionalstyle.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import java.io.Serializable;
import java.util.Date;

public interface ConditionalStyle
extends Serializable,
IMetaItem {
    public String getKey();

    public String getFormKey();

    public int getPosX();

    public int getPosY();

    public String getStyleExpression();

    public String getFontColor();

    public String getForeGroundColor();

    public Boolean getBold();

    public Boolean getItalic();

    public Boolean getReadOnly();

    public String getOrder();

    public Date getUpdateTime();

    public String getLinkKey();

    public Boolean getHorizontalBar();

    public Boolean getStrikeThrough();
}

