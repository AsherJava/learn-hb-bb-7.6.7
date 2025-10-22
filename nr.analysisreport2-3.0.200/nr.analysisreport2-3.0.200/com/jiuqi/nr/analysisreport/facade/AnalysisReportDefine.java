/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.nr.analysisreport.facade.Analysis;
import java.util.Date;

public interface AnalysisReportDefine
extends Analysis {
    public String getKey();

    public void setKey(String var1);

    public String getGroupKey();

    public void setGroupKey(String var1);

    public String getTitle();

    public void setTitle(String var1);

    @Override
    public String getDescription();

    public void setDescription(String var1);

    public String getData();

    public void setData(String var1);

    public String getCreateuser();

    public void setCreateuser(String var1);

    public String getModifyuser();

    public void setModifyuser(String var1);

    public String getOrder();

    public void setOrder(String var1);

    public String getPrintData();

    public void setPrintData(String var1);

    public String getDimension();

    public void setDimension(String var1);

    public String getSecurityLevel();

    public void setSecurityLevel(String var1);

    public int getPeriodOffset();

    public void setPeriodOffset(int var1);

    public String getAtCatalog();

    public void setAtCatalog(String var1);

    public Date getAtCatalogUpdatetime();

    public void setAtCatalogUpdatetime(Date var1);
}

