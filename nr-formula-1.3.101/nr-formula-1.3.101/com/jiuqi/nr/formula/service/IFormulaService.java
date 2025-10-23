/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.FormulaAuditTypeDTO;
import com.jiuqi.nr.formula.dto.FormulaCycleTreeDTO;
import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import com.jiuqi.nr.formula.dto.FormulaIoDTO;
import com.jiuqi.nr.formula.dto.FormulaSearchParam;
import com.jiuqi.nr.formula.dto.ImportResult;
import com.jiuqi.nr.formula.service.IFormulaDataService;
import com.jiuqi.nr.formula.web.param.ExpressionCheckParam;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFormulaService
extends IFormulaDataService {
    public FormulaDTO getFormula(String var1);

    public List<FormulaDTO> listFormulaByScheme(String var1);

    public String getFormCodeByForm(String var1);

    public <T extends FormulaExtDTO> FormulaCheckResult formulaCheckByFormScheme(String var1, boolean var2, List<T> var3, List<String> var4);

    public void expressionCheck(ExpressionCheckParam var1);

    public <T extends FormulaExtDTO> List<T> generateFormulaDescription(String var1, List<T> var2);

    public FormulaSearchParam searchFormula(FormulaSearchParam var1);

    public void exportFormula(FormulaIoDTO var1);

    public void importFormula(InputStream var1, FormulaIoDTO var2);

    public void deleteFormula(String var1);

    public void insertFormula(FormulaDTO var1);

    public void updateFormula(FormulaDTO var1);

    public void publish(String var1, String var2);

    public String getFormulaCode(Set<String> var1, String var2);

    public void deleteUploadFile(String var1);

    public FileInfoDTO uploadFormulaExcel(MultipartFile var1);

    public <T extends FormulaExtDTO> Collection<T> conditionStyleCheck(String var1, List<T> var2);

    public List<FormulaAuditTypeDTO> listAllAuditType();

    public List<FormulaCycleTreeDTO> checkFormulaCycle(String var1);

    public void exportCycle(HttpServletResponse var1, String var2);

    public void exportErrorExcel(ImportResult var1, HttpServletResponse var2) throws IOException;
}

