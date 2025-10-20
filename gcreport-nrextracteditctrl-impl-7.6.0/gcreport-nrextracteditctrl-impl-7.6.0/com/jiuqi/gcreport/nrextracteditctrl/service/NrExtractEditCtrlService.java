/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO
 *  com.jiuqi.nr.dataentry.tree.FormTree
 */
package com.jiuqi.gcreport.nrextracteditctrl.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlEO;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;

public interface NrExtractEditCtrlService {
    public void save(NrExtractEditCtrlSaveDTO var1);

    public void update(NrExtractEditCtrlSaveDTO var1);

    public void delete(String var1);

    public void batchDelete(List<String> var1);

    public void stop(String var1);

    public void batchStop(List<String> var1);

    public void start(String var1);

    public void batchStart(List<String> var1);

    public List<NrExtractEditCtrlDTO> queryAll();

    public PageInfo<NrExtractEditCtrlDTO> queryPage(Integer var1, Integer var2);

    public List<NrExtractEditCtrlEO> queryByTaskIdAndSchemeKey(String var1, String var2);

    public void nrExtractEditCtrlCacheClear();

    public void nrExtractEditCtrlCacheEvict(String var1);

    public List<FormSchemeParamDTO> queryAllFormSchemeByTaskId(String var1);

    public FormTree queryFormTreeByFormSchemeKey(String var1);

    public List<String> queryEditableLinkIdListInForm(NrExtractEditCtrlCondi var1, List<String> var2);
}

