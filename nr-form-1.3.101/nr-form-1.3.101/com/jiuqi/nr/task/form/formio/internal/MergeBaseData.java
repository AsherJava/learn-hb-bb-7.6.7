/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 */
package com.jiuqi.nr.task.form.formio.internal;

import com.jiuqi.nr.task.form.formio.dto.ImportBaseDataDTO;
import com.jiuqi.nr.task.form.util.DefaultIdentifierGenerator;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MergeBaseData {
    private final Set<List<BaseDataDTO>> existBaseData = new HashSet<List<BaseDataDTO>>();
    private final Map<List<BaseDataDTO>, ImportBaseDataDTO> baseDataDefineDTOMap = new HashMap<List<BaseDataDTO>, ImportBaseDataDTO>();
    private final Set<BaseDataDefineDTO> existBaseDataDefine = new HashSet<BaseDataDefineDTO>();
    private final DefaultIdentifierGenerator titleIdentifierGenerator = new DefaultIdentifierGenerator();
    private final DefaultIdentifierGenerator codeIdentifierGenerator = new DefaultIdentifierGenerator();
    private final List<ImportBaseDataDTO> result = new ArrayList<ImportBaseDataDTO>();

    public List<ImportBaseDataDTO> getResult() {
        return this.result;
    }

    public ImportBaseDataDTO mergeBaseData(ImportBaseDataDTO importBaseDataDTO) {
        BaseDataDefineDTO baseDataDefine = importBaseDataDTO.getBaseDataDefine();
        List<BaseDataDTO> baseData = importBaseDataDTO.getBaseData();
        if (this.existBaseData.contains(baseData)) {
            return this.baseDataDefineDTOMap.get(baseData);
        }
        this.existBaseData.add(baseData);
        this.baseDataDefineDTOMap.put(baseData, importBaseDataDTO);
        this.result.add(importBaseDataDTO);
        if (this.existBaseDataDefine.contains(baseDataDefine)) {
            String name = baseDataDefine.getName();
            String name2 = this.codeIdentifierGenerator.generateUniqueId(name);
            baseDataDefine.setName(name2);
            String title = baseDataDefine.getTitle();
            String title2 = this.titleIdentifierGenerator.generateUniqueId(title);
            baseDataDefine.setTitle(title2);
        } else {
            this.existBaseDataDefine.add(baseDataDefine);
        }
        return importBaseDataDTO;
    }
}

