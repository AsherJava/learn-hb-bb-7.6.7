/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.io.Serializable;
import java.time.Instant;
import nr.single.para.compare.definition.common.CompareStatusType;

public interface ICompareInfo
extends Serializable {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getFileFlag();

    public String getTaskYear();

    public String getTaskTitle();

    public String getTaskKey();

    public String getFormSchemeKey();

    public String getDataSchemeKey();

    public CompareStatusType getStatus();

    public String getJioFile();

    public String getJioFileKey();

    public String getLogFile();

    public String getLogFileKey();

    public String getJioData();

    public String getMapData();

    public String getOptionData();

    public String getMessage();

    public Instant getUpdateTime();
}

