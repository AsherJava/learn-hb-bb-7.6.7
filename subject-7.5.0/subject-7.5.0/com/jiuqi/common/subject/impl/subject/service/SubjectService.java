/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.common.subject.impl.subject.service;

import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.dto.TreeDTO;
import com.jiuqi.common.subject.impl.subject.vo.SubjectInitVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface SubjectService {
    public SubjectInitVO getSubjectInitInfo();

    public Boolean syncCache();

    public BaseDataDefineDO findDefineByName(BaseDataDefineDTO var1);

    public List<SubjectDTO> list();

    public List<SubjectDTO> list(BaseDataDTO var1);

    public PageVO<SubjectDTO> pagination(BaseDataDTO var1);

    public List<TreeDTO> tree(BaseDataDTO var1);

    public List<TreeDTO> buildTree(List<SubjectDTO> var1);

    public List<TreeDTO> buildTreeBySubjectCodes(BaseDataDTO var1);

    public List<TreeDTO> buildTree(List<SubjectDTO> var1, TreeDTO var2);

    public BaseDataDO getById(@NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") UUID var1);

    public SubjectDTO findById(@NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") UUID var1);

    public SubjectDTO getByCode(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public SubjectDTO findByCode(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public Boolean create(SubjectDTO var1);

    public Boolean modify(SubjectDTO var1);

    public Boolean delete(SubjectDTO var1);

    public Boolean stop(SubjectDTO var1);

    public Boolean start(SubjectDTO var1);

    public List<DimensionVO> getAllPublishedDim();

    public Boolean batchCreate(List<SubjectDTO> var1);

    public Boolean batchModify(List<SubjectDTO> var1);
}

