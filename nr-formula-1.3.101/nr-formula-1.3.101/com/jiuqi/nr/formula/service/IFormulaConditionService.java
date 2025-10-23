/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.dto.IPageDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.FormulaConditionDTO;
import com.jiuqi.nr.formula.web.vo.ConditionImportResult;
import com.jiuqi.nr.formula.web.vo.UpdateResult;
import com.jiuqi.nr.task.api.dto.IPageDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import java.io.OutputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IFormulaConditionService {
    public IPageDTO<FormulaConditionDTO> queryConditionsByTask(String var1, Long var2, Long var3);

    public List<FormulaConditionDTO> list(List<String> var1);

    public UpdateResult updateFormulaCondition(FormulaConditionDTO var1) throws Exception;

    public void updateFormulaConditions(List<FormulaConditionDTO> var1);

    public void deleteFormulaCondition(String var1);

    public void deleteFormulaConditions(List<String> var1);

    public UpdateResult insertFormulaCondition(FormulaConditionDTO var1);

    public void exportConditions(OutputStream var1, String var2);

    public FileInfoDTO uploadFile(MultipartFile var1);

    public ConditionImportResult importAddConditions(String var1, String var2);

    public void deployFormulaConditions(String var1, String[] var2);

    public boolean isConditionExist(String var1);

    public String generatorCode(String var1);

    public FormulaConditionDTO getCondition(String var1, String var2);

    public List<FormulaConditionDTO> listConditionsByCode(String var1, List<String> var2);
}

