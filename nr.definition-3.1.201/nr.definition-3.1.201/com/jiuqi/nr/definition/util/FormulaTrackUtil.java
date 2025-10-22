/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.common.Consts;
import com.jiuqi.nr.definition.formulatracking.facade.FormulaTrackDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FormulaTrackUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaTrackUtil.class);

    public static void parseExpression(IParsedExpression expression, Consumer<String> linkConsumer, BiConsumer<String, String> calLinkConsumer) {
        IExpression exp = expression.getRealExpression();
        DynamicDataNode assignNode = expression.getAssignNode();
        for (IASTNode child : exp) {
            try {
                DataModelLinkColumn modelLink;
                DataModelLinkColumn dataModelLink;
                if (!(child instanceof DynamicDataNode) || null == (dataModelLink = ((DynamicDataNode)child).getDataModelLink())) continue;
                String dataLinkCode = dataModelLink.getDataLinkCode();
                linkConsumer.accept(dataLinkCode);
                if (DataEngineConsts.FormulaType.CALCULATE != expression.getFormulaType() || null == assignNode || null == (modelLink = assignNode.getDataModelLink()) || !modelLink.getColumModel().getID().equals(dataModelLink.getColumModel().getID()) || !FormulaTrackUtil.trackIsReadOnly(expression, assignNode)) continue;
                calLinkConsumer.accept(modelLink.getReportInfo().getReportKey(), modelLink.getDataLinkCode());
            }
            catch (Exception e) {
                LOGGER.error("\u83b7\u53d6\u516c\u5f0f\u8ffd\u8e2a\u5b9a\u4e49\u5931\u8d25\uff1a[{}]\u6839\u636edataNode.getDataLink()\u83b7\u53d6\uff0cDataLinkColumn\u5bf9\u8c61\u4e3a\u7a7a, {}", (Object)expression.getSource().getId(), (Object)e);
            }
        }
    }

    public static List<FormulaTrackDefine> buildFormulaTrack(String formulaSchemeKey, List<IParsedExpression> iParsedExpressions, Function<String, String> fieldFinder) {
        ArrayList<FormulaTrackDefineImpl> allFormulaTrackDefines = new ArrayList<FormulaTrackDefineImpl>();
        for (IParsedExpression iParsedExpression : iParsedExpressions) {
            IExpression exp = iParsedExpression.getRealExpression();
            for (IASTNode child : exp) {
                String dataFieldKey;
                ColumnModelDefine columModel;
                DataModelLinkColumn dataLink = null;
                if (!(child instanceof DynamicDataNode) || null == (dataLink = ((DynamicDataNode)child).getDataModelLink()) || null == (columModel = dataLink.getColumModel()) || !StringUtils.hasText(dataFieldKey = fieldFinder.apply(columModel.getID()))) continue;
                DynamicDataNode assignNode = iParsedExpression.getAssignNode();
                FormulaTrackDefineImpl formulaTrackDefine = new FormulaTrackDefineImpl(UUIDUtils.getKey(), formulaSchemeKey, iParsedExpression);
                try {
                    formulaTrackDefine.setDataLinkCode(dataLink.getDataLinkCode());
                    formulaTrackDefine.setDataLinkFormKey(dataLink.getReportInfo().getReportKey());
                    formulaTrackDefine.setFormulaFieldKey(dataFieldKey);
                    formulaTrackDefine.setReadOnly(FormulaTrackUtil.trackIsReadOnly(iParsedExpression, assignNode));
                    formulaTrackDefine.setFormulaDataDirection(assignNode != null && assignNode.getDataModelLink().getColumModel().getID().equalsIgnoreCase(columModel.getID()));
                    allFormulaTrackDefines.add(formulaTrackDefine);
                }
                catch (Exception e) {
                    LOGGER.error("\u83b7\u53d6\u516c\u5f0f\u8ffd\u8e2a\u5b9a\u4e49\u5931\u8d25\uff1a[{}]\u6839\u636edataNode.getDataLink()\u83b7\u53d6\uff0cDataLinkColumn\u5bf9\u8c61\u4e3a\u7a7a, {}", (Object)iParsedExpression.getSource().getId(), (Object)e);
                }
            }
        }
        return allFormulaTrackDefines.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<FormulaTrackDefine>(Comparator.comparing(formula -> formula.getDataLinkCode() + formula.getExpressionKey()))), ArrayList::new));
    }

    private static boolean trackIsReadOnly(IParsedExpression expression, DynamicDataNode assignNode) {
        if (assignNode == null || null == assignNode.getDataModelLink()) {
            return true;
        }
        IASTNode rootNode = expression.getRealExpression().getChild(0);
        if (rootNode instanceof IfThenElse) {
            if (rootNode.childrenSize() < 3) {
                return false;
            }
            IASTNode elseNode = rootNode.getChild(2);
            DynamicDataNode elseAssignNode = ExpressionUtils.getAssignNode((IASTNode)elseNode);
            if (elseAssignNode == null || !elseAssignNode.equals((Object)assignNode)) {
                return false;
            }
        }
        return assignNode.getQueryField().getDimensionRestriction() == null && assignNode.getQueryField().getPeriodModifier() == null;
    }

    public static class FormulaTrackDefineImpl
    implements FormulaTrackDefine {
        private static final long serialVersionUID = -2742753004025848775L;
        private final String id;
        private final String formulaSchemeKey;
        private final IParsedExpression expression;
        private String dataLinkFormKey;
        private String dataLinkCode;
        private String formulaFieldKey;
        private boolean formulaDataDirection;
        private boolean readOnly = true;

        public FormulaTrackDefineImpl(String id, String formulaSchemeKey, IParsedExpression expression) {
            this.id = id;
            this.formulaSchemeKey = formulaSchemeKey;
            this.expression = expression;
        }

        @Deprecated
        public Date getUpdateTime() {
            return null;
        }

        @Deprecated
        public String getKey() {
            return this.getId();
        }

        @Deprecated
        public String getTitle() {
            return null;
        }

        @Deprecated
        public String getOrder() {
            return null;
        }

        @Deprecated
        public String getVersion() {
            return null;
        }

        @Deprecated
        public String getOwnerLevelAndId() {
            return null;
        }

        @Override
        @Deprecated
        public String getId() {
            return this.id;
        }

        @Override
        @Deprecated
        public String getFormulaSchemeKey() {
            return this.formulaSchemeKey;
        }

        @Override
        public String getFormulaKey() {
            return this.expression.getSource().getId();
        }

        @Override
        public String getFormKey() {
            return this.expression.getSource().getFormKey();
        }

        @Override
        public Integer getFormulaType() {
            return this.expression.getFormulaType().getValue();
        }

        @Override
        public String getExpressionKey() {
            return this.expression.getKey();
        }

        @Override
        public String getDataLinkFormKey() {
            return this.dataLinkFormKey;
        }

        public void setDataLinkFormKey(String dataLinkFormKey) {
            this.dataLinkFormKey = dataLinkFormKey;
        }

        @Override
        public String getDataLinkCode() {
            return this.dataLinkCode;
        }

        public void setDataLinkCode(String dataLinkCode) {
            this.dataLinkCode = dataLinkCode;
        }

        @Override
        public String getFormulaFieldKey() {
            return this.formulaFieldKey;
        }

        public void setFormulaFieldKey(String formulaFieldKey) {
            this.formulaFieldKey = formulaFieldKey;
        }

        @Override
        public Integer getFormulaDataDirection() {
            return this.formulaDataDirection ? Consts.WRITE_ENUM : Consts.READ_ENUM;
        }

        public void setFormulaDataDirection(boolean write) {
            this.formulaDataDirection = write;
        }

        public void setReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
        }

        @Override
        public Boolean getReadOnly() {
            return this.readOnly;
        }
    }
}

