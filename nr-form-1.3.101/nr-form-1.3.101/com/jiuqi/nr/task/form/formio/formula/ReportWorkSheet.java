/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 */
package com.jiuqi.nr.task.form.formio.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nr.task.form.formio.dto.ImportReverseResultDTO;
import com.jiuqi.nr.task.form.formio.formula.ReportCellNode;
import com.jiuqi.nr.task.form.formio.formula.ReportContext;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class ReportWorkSheet
implements IWorksheet {
    private static final Logger log = LoggerFactory.getLogger(ReportWorkSheet.class);
    private final String key;
    private final ImportReverseResultDTO reverseResultDTO;

    public ReportWorkSheet(String key, ImportReverseResultDTO reverseResultDTO) {
        this.key = key;
        this.reverseResultDTO = reverseResultDTO;
    }

    public String name() {
        return this.key;
    }

    public IASTNode findCell(IContext context, Token token, Position pos, int cellOptions) throws CellExcpetion {
        if (context instanceof ReportContext) {
            ReportContext reportContext = (ReportContext)context;
            log.debug("\u67e5\u8be2Cell: {}", (Object)pos);
            List<DesignFormulaDefine> formulaPath = reportContext.getFormulaPath();
            if (!CollectionUtils.isEmpty(formulaPath)) {
                StringBuilder builder = new StringBuilder();
                formulaPath.forEach(x -> builder.append(x.getExpression()).append("->"));
                log.debug("\u516c\u5f0f\u89e3\u6790\u5668: {}", (Object)builder);
            }
            String key = pos.row() + "," + pos.col();
            Map<String, DataFieldSettingDTO> fieldPosMap = this.reverseResultDTO.getFieldPosMap();
            DataFieldDTO dataFieldDTO = fieldPosMap.get(key);
            if (dataFieldDTO != null) {
                log.info("\u3010\u89e3\u6790\u516c\u5f0f\u3011\u67e5\u8be2\u5230\u6307\u6807: {}-{}", (Object)pos, (Object)dataFieldDTO);
                Map<String, DesignFormulaDefine> formulaPosMap = this.reverseResultDTO.getFormulaPosMap();
                if (formulaPosMap.containsKey(key)) {
                    int type;
                    DesignFormulaDefine formulaDefine = formulaPosMap.get(key);
                    boolean dep = formulaPath.stream().anyMatch(x -> x.getKey().equals(formulaDefine.getKey()));
                    if (dep) {
                        StringBuilder builder = new StringBuilder();
                        formulaPath.forEach(x -> builder.append(x.getExpression()).append("->"));
                        log.debug("\u89e3\u6790\u516c\u5f0f\u6b7b\u5faa\u73af\uff1a{}", (Object)builder);
                        ReportCellNode.ReportCellData reportCellData = new ReportCellNode.ReportCellData(this.reverseResultDTO.getFormDefine().getFormCode(), key, 10);
                        return new ReportCellNode(token, this, pos, cellOptions, reportCellData);
                    }
                    formulaPath.add(formulaDefine);
                    IExpression expression = reportContext.getExpressionMap().get(formulaDefine.getKey());
                    try {
                        if (expression == null) {
                            log.debug("\u5355\u5143\u683c\u3010{}\u3011\u516c\u5f0f\u3010{}\u3011\u89e3\u6790", (Object)pos, (Object)formulaDefine.getExpression());
                            expression = reportContext.getParser().parseEval(formulaDefine.getExpression(), (IContext)reportContext);
                            type = expression.getType((IContext)reportContext);
                            reportContext.getExpressionMap().put(formulaDefine.getKey(), expression);
                        } else {
                            type = expression.getType((IContext)reportContext);
                        }
                    }
                    catch (SyntaxException e) {
                        log.error("\u516c\u5f0f\u94fe\u89e3\u6790\u9519\u8bef: {}", (Object)e.getMessage());
                        type = 10;
                    }
                    ReportCellNode.ReportCellData reportCellData = new ReportCellNode.ReportCellData(this.reverseResultDTO.getFormDefine().getFormCode(), key, type);
                    return new ReportCellNode(token, this, pos, cellOptions, reportCellData);
                }
                ImportCellType cellType = this.reverseResultDTO.getCellTypeMap().get(key);
                ReportCellNode.ReportCellData reportCellData = new ReportCellNode.ReportCellData(this.reverseResultDTO.getFormDefine().getFormCode(), key, cellType.getValue());
                return new ReportCellNode(token, this, pos, cellOptions, reportCellData);
            }
            log.debug("\u3010\u89e3\u6790\u516c\u5f0f\u3011\u672a\u627e\u5230\u6307\u6807: {}", (Object)pos);
        }
        return null;
    }

    public IASTNode findCell(IContext context, Token token, CompatiblePosition pos, int cellOptions, List<IASTNode> restrictions) throws CellExcpetion {
        return this.findCell(context, token, pos.getPosition(), cellOptions);
    }

    public IASTNode findRegion(IContext context, Token token, Region region, int cellOptions) throws CellExcpetion {
        log.debug("\u4e0d\u652f\u6301\u533a\u57df\u6a21\u5f0f\u3002");
        return null;
    }
}

