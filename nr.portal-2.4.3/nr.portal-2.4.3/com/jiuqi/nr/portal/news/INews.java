/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news;

import java.util.Date;

public interface INews {
    public String getID();

    public String getMId();

    public String getTitle();

    public String getLink();

    public String getPoster();

    public String getAbstact();

    public String getContent();

    public boolean showLatest();

    public boolean stick();

    public boolean push();

    public boolean hide();

    public int getNewsOrder();

    public Date getCreateTime();

    public String getPortalId();
}

