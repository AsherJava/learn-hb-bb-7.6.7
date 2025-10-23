/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.link.service;

import com.jiuqi.nr.task.form.ext.dto.LinkQuery;
import com.jiuqi.nr.task.form.link.dto.DataLinkDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import java.util.List;

public interface IDataLinkService {
    public DataLinkSettingDTO getSetting(LinkQuery var1);

    public void saveLinks(String var1, List<DataLinkSettingDTO> var2);

    public List<DataLinkDTO> listDataLink(String var1);

    public List<DataLinkSettingDTO> listDataLinkSettingByForm(String var1);

    public DataLinkSettingDTO getLinkSetting(String var1);

    public List<DataLinkDTO> listLinksByFieldKeys(List<String> var1);
}

