/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.AnalyticExpression
 *  net.sf.jsqlparser.expression.AnyComparisonExpression
 *  net.sf.jsqlparser.expression.ArrayConstructor
 *  net.sf.jsqlparser.expression.ArrayExpression
 *  net.sf.jsqlparser.expression.BinaryExpression
 *  net.sf.jsqlparser.expression.CaseExpression
 *  net.sf.jsqlparser.expression.CastExpression
 *  net.sf.jsqlparser.expression.CollateExpression
 *  net.sf.jsqlparser.expression.ConnectByRootOperator
 *  net.sf.jsqlparser.expression.DateTimeLiteralExpression
 *  net.sf.jsqlparser.expression.DateValue
 *  net.sf.jsqlparser.expression.DoubleValue
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.ExpressionVisitor
 *  net.sf.jsqlparser.expression.ExtractExpression
 *  net.sf.jsqlparser.expression.Function
 *  net.sf.jsqlparser.expression.HexValue
 *  net.sf.jsqlparser.expression.IntervalExpression
 *  net.sf.jsqlparser.expression.JdbcNamedParameter
 *  net.sf.jsqlparser.expression.JdbcParameter
 *  net.sf.jsqlparser.expression.JsonAggregateFunction
 *  net.sf.jsqlparser.expression.JsonExpression
 *  net.sf.jsqlparser.expression.JsonFunction
 *  net.sf.jsqlparser.expression.JsonFunctionExpression
 *  net.sf.jsqlparser.expression.KeepExpression
 *  net.sf.jsqlparser.expression.LongValue
 *  net.sf.jsqlparser.expression.MySQLGroupConcat
 *  net.sf.jsqlparser.expression.NextValExpression
 *  net.sf.jsqlparser.expression.NotExpression
 *  net.sf.jsqlparser.expression.NullValue
 *  net.sf.jsqlparser.expression.NumericBind
 *  net.sf.jsqlparser.expression.OracleHierarchicalExpression
 *  net.sf.jsqlparser.expression.OracleHint
 *  net.sf.jsqlparser.expression.OracleNamedFunctionParameter
 *  net.sf.jsqlparser.expression.Parenthesis
 *  net.sf.jsqlparser.expression.RowConstructor
 *  net.sf.jsqlparser.expression.RowGetExpression
 *  net.sf.jsqlparser.expression.SignedExpression
 *  net.sf.jsqlparser.expression.StringValue
 *  net.sf.jsqlparser.expression.TimeKeyExpression
 *  net.sf.jsqlparser.expression.TimeValue
 *  net.sf.jsqlparser.expression.TimestampValue
 *  net.sf.jsqlparser.expression.TimezoneExpression
 *  net.sf.jsqlparser.expression.UserVariable
 *  net.sf.jsqlparser.expression.ValueListExpression
 *  net.sf.jsqlparser.expression.VariableAssignment
 *  net.sf.jsqlparser.expression.WhenClause
 *  net.sf.jsqlparser.expression.XMLSerializeExpr
 *  net.sf.jsqlparser.expression.operators.arithmetic.Addition
 *  net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd
 *  net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift
 *  net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr
 *  net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift
 *  net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor
 *  net.sf.jsqlparser.expression.operators.arithmetic.Concat
 *  net.sf.jsqlparser.expression.operators.arithmetic.Division
 *  net.sf.jsqlparser.expression.operators.arithmetic.IntegerDivision
 *  net.sf.jsqlparser.expression.operators.arithmetic.Modulo
 *  net.sf.jsqlparser.expression.operators.arithmetic.Multiplication
 *  net.sf.jsqlparser.expression.operators.arithmetic.Subtraction
 *  net.sf.jsqlparser.expression.operators.conditional.AndExpression
 *  net.sf.jsqlparser.expression.operators.conditional.OrExpression
 *  net.sf.jsqlparser.expression.operators.conditional.XorExpression
 *  net.sf.jsqlparser.expression.operators.relational.Between
 *  net.sf.jsqlparser.expression.operators.relational.EqualsTo
 *  net.sf.jsqlparser.expression.operators.relational.ExistsExpression
 *  net.sf.jsqlparser.expression.operators.relational.ExpressionList
 *  net.sf.jsqlparser.expression.operators.relational.FullTextSearch
 *  net.sf.jsqlparser.expression.operators.relational.GreaterThan
 *  net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals
 *  net.sf.jsqlparser.expression.operators.relational.InExpression
 *  net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression
 *  net.sf.jsqlparser.expression.operators.relational.IsNullExpression
 *  net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor
 *  net.sf.jsqlparser.expression.operators.relational.JsonOperator
 *  net.sf.jsqlparser.expression.operators.relational.LikeExpression
 *  net.sf.jsqlparser.expression.operators.relational.Matches
 *  net.sf.jsqlparser.expression.operators.relational.MinorThan
 *  net.sf.jsqlparser.expression.operators.relational.MinorThanEquals
 *  net.sf.jsqlparser.expression.operators.relational.MultiExpressionList
 *  net.sf.jsqlparser.expression.operators.relational.NamedExpressionList
 *  net.sf.jsqlparser.expression.operators.relational.NotEqualsTo
 *  net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator
 *  net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator
 *  net.sf.jsqlparser.expression.operators.relational.SimilarToExpression
 *  net.sf.jsqlparser.schema.Column
 *  net.sf.jsqlparser.schema.Table
 *  net.sf.jsqlparser.statement.Block
 *  net.sf.jsqlparser.statement.Commit
 *  net.sf.jsqlparser.statement.CreateFunctionalStatement
 *  net.sf.jsqlparser.statement.DeclareStatement
 *  net.sf.jsqlparser.statement.DescribeStatement
 *  net.sf.jsqlparser.statement.ExplainStatement
 *  net.sf.jsqlparser.statement.IfElseStatement
 *  net.sf.jsqlparser.statement.PurgeObjectType
 *  net.sf.jsqlparser.statement.PurgeStatement
 *  net.sf.jsqlparser.statement.ResetStatement
 *  net.sf.jsqlparser.statement.RollbackStatement
 *  net.sf.jsqlparser.statement.SavepointStatement
 *  net.sf.jsqlparser.statement.SetStatement
 *  net.sf.jsqlparser.statement.ShowColumnsStatement
 *  net.sf.jsqlparser.statement.ShowStatement
 *  net.sf.jsqlparser.statement.Statement
 *  net.sf.jsqlparser.statement.StatementVisitor
 *  net.sf.jsqlparser.statement.Statements
 *  net.sf.jsqlparser.statement.UseStatement
 *  net.sf.jsqlparser.statement.alter.Alter
 *  net.sf.jsqlparser.statement.alter.AlterSession
 *  net.sf.jsqlparser.statement.alter.AlterSystemStatement
 *  net.sf.jsqlparser.statement.alter.RenameTableStatement
 *  net.sf.jsqlparser.statement.alter.sequence.AlterSequence
 *  net.sf.jsqlparser.statement.comment.Comment
 *  net.sf.jsqlparser.statement.create.index.CreateIndex
 *  net.sf.jsqlparser.statement.create.schema.CreateSchema
 *  net.sf.jsqlparser.statement.create.sequence.CreateSequence
 *  net.sf.jsqlparser.statement.create.synonym.CreateSynonym
 *  net.sf.jsqlparser.statement.create.table.CreateTable
 *  net.sf.jsqlparser.statement.create.view.AlterView
 *  net.sf.jsqlparser.statement.create.view.CreateView
 *  net.sf.jsqlparser.statement.delete.Delete
 *  net.sf.jsqlparser.statement.drop.Drop
 *  net.sf.jsqlparser.statement.execute.Execute
 *  net.sf.jsqlparser.statement.grant.Grant
 *  net.sf.jsqlparser.statement.insert.Insert
 *  net.sf.jsqlparser.statement.merge.Merge
 *  net.sf.jsqlparser.statement.replace.Replace
 *  net.sf.jsqlparser.statement.select.AllColumns
 *  net.sf.jsqlparser.statement.select.AllTableColumns
 *  net.sf.jsqlparser.statement.select.FromItemVisitor
 *  net.sf.jsqlparser.statement.select.Join
 *  net.sf.jsqlparser.statement.select.LateralSubSelect
 *  net.sf.jsqlparser.statement.select.ParenthesisFromItem
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectBody
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 *  net.sf.jsqlparser.statement.select.SelectItem
 *  net.sf.jsqlparser.statement.select.SelectItemVisitor
 *  net.sf.jsqlparser.statement.select.SelectVisitor
 *  net.sf.jsqlparser.statement.select.SetOperationList
 *  net.sf.jsqlparser.statement.select.SubJoin
 *  net.sf.jsqlparser.statement.select.SubSelect
 *  net.sf.jsqlparser.statement.select.TableFunction
 *  net.sf.jsqlparser.statement.select.ValuesList
 *  net.sf.jsqlparser.statement.select.WithItem
 *  net.sf.jsqlparser.statement.show.ShowTablesStatement
 *  net.sf.jsqlparser.statement.truncate.Truncate
 *  net.sf.jsqlparser.statement.update.Update
 *  net.sf.jsqlparser.statement.upsert.Upsert
 *  net.sf.jsqlparser.statement.values.ValuesStatement
 */
package com.jiuqi.va.query.sql.parser.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.ArrayConstructor;
import net.sf.jsqlparser.expression.ArrayExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.CollateExpression;
import net.sf.jsqlparser.expression.ConnectByRootOperator;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonAggregateFunction;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.JsonFunction;
import net.sf.jsqlparser.expression.JsonFunctionExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NextValExpression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.OracleNamedFunctionParameter;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.RowGetExpression;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.TimezoneExpression;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.VariableAssignment;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.XMLSerializeExpr;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.IntegerDivision;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.FullTextSearch;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.expression.operators.relational.SimilarToExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Block;
import net.sf.jsqlparser.statement.Commit;
import net.sf.jsqlparser.statement.CreateFunctionalStatement;
import net.sf.jsqlparser.statement.DeclareStatement;
import net.sf.jsqlparser.statement.DescribeStatement;
import net.sf.jsqlparser.statement.ExplainStatement;
import net.sf.jsqlparser.statement.IfElseStatement;
import net.sf.jsqlparser.statement.PurgeObjectType;
import net.sf.jsqlparser.statement.PurgeStatement;
import net.sf.jsqlparser.statement.ResetStatement;
import net.sf.jsqlparser.statement.RollbackStatement;
import net.sf.jsqlparser.statement.SavepointStatement;
import net.sf.jsqlparser.statement.SetStatement;
import net.sf.jsqlparser.statement.ShowColumnsStatement;
import net.sf.jsqlparser.statement.ShowStatement;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.UseStatement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;

public class UserVariableFinder
implements SelectVisitor,
FromItemVisitor,
ExpressionVisitor,
ItemsListVisitor,
SelectItemVisitor,
StatementVisitor {
    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private List<String> tables = new ArrayList<String>();
    private List<String> userVariables = new ArrayList<String>();
    private boolean allowColumnProcessing = false;
    private List<String> otherItemNames = new ArrayList<String>();

    public List<String> getTableList(Statement statement) {
        this.init(false);
        statement.accept((StatementVisitor)this);
        return this.tables;
    }

    public List<String> getUserVariableList(Statement statement) {
        this.init(false);
        statement.accept((StatementVisitor)this);
        return this.userVariables;
    }

    public List<String> getUserVariableList(Expression expression) {
        this.init(false);
        expression.accept((ExpressionVisitor)this);
        return this.userVariables;
    }

    public void visit(Select select) {
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                withItem.accept((SelectVisitor)this);
            }
        }
        select.getSelectBody().accept((SelectVisitor)this);
    }

    public List<String> getTableList(Expression expr) {
        this.init(true);
        expr.accept((ExpressionVisitor)this);
        return this.tables;
    }

    public void visit(WithItem withItem) {
        this.otherItemNames.add(withItem.getName().toLowerCase());
        withItem.getSubSelect().accept((ItemsListVisitor)this);
    }

    public void visit(PlainSelect plainSelect) {
        if (plainSelect.getSelectItems() != null) {
            for (SelectItem item : plainSelect.getSelectItems()) {
                item.accept((SelectItemVisitor)this);
            }
        }
        if (plainSelect.getFromItem() != null) {
            plainSelect.getFromItem().accept((FromItemVisitor)this);
        }
        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                join.getRightItem().accept((FromItemVisitor)this);
            }
        }
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept((ExpressionVisitor)this);
        }
        if (plainSelect.getHaving() != null) {
            plainSelect.getHaving().accept((ExpressionVisitor)this);
        }
        if (plainSelect.getOracleHierarchical() != null) {
            plainSelect.getOracleHierarchical().accept((ExpressionVisitor)this);
        }
    }

    protected String extractTableName(Table table) {
        return table.getFullyQualifiedName();
    }

    public void visit(Table tableName) {
        String tableWholeName = this.extractTableName(tableName);
        if (!this.otherItemNames.contains(tableWholeName.toLowerCase()) && !this.tables.contains(tableWholeName)) {
            this.tables.add(tableWholeName);
        }
    }

    public void visit(SubSelect subSelect) {
        if (subSelect.getWithItemsList() != null) {
            for (WithItem withItem : subSelect.getWithItemsList()) {
                withItem.accept((SelectVisitor)this);
            }
        }
        subSelect.getSelectBody().accept((SelectVisitor)this);
    }

    public void visit(Addition addition) {
        this.visitBinaryExpression((BinaryExpression)addition);
    }

    public void visit(AndExpression andExpression) {
        this.visitBinaryExpression((BinaryExpression)andExpression);
    }

    public void visit(Between between) {
        between.getLeftExpression().accept((ExpressionVisitor)this);
        between.getBetweenExpressionStart().accept((ExpressionVisitor)this);
        between.getBetweenExpressionEnd().accept((ExpressionVisitor)this);
    }

    public void visit(Column tableColumn) {
        if (this.allowColumnProcessing && tableColumn.getTable() != null && tableColumn.getTable().getName() != null) {
            this.visit(tableColumn.getTable());
        }
    }

    public void visit(Division division) {
        this.visitBinaryExpression((BinaryExpression)division);
    }

    public void visit(IntegerDivision division) {
        this.visitBinaryExpression((BinaryExpression)division);
    }

    public void visit(DoubleValue doubleValue) {
    }

    public void visit(EqualsTo equalsTo) {
        this.visitBinaryExpression((BinaryExpression)equalsTo);
    }

    public void visit(Function function) {
        ExpressionList exprList = function.getParameters();
        if (exprList != null) {
            this.visit(exprList);
        }
    }

    public void visit(GreaterThan greaterThan) {
        this.visitBinaryExpression((BinaryExpression)greaterThan);
    }

    public void visit(GreaterThanEquals greaterThanEquals) {
        this.visitBinaryExpression((BinaryExpression)greaterThanEquals);
    }

    public void visit(InExpression inExpression) {
        if (inExpression.getLeftExpression() != null) {
            inExpression.getLeftExpression().accept((ExpressionVisitor)this);
        }
        if (inExpression.getRightExpression() != null) {
            inExpression.getRightExpression().accept((ExpressionVisitor)this);
        } else if (inExpression.getRightItemsList() != null) {
            inExpression.getRightItemsList().accept((ItemsListVisitor)this);
        } else {
            inExpression.getMultiExpressionList().accept((ItemsListVisitor)this);
        }
    }

    public void visit(FullTextSearch fullTextSearch) {
    }

    public void visit(SignedExpression signedExpression) {
        signedExpression.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(IsNullExpression isNullExpression) {
    }

    public void visit(IsBooleanExpression isBooleanExpression) {
    }

    public void visit(JdbcParameter jdbcParameter) {
    }

    public void visit(LikeExpression likeExpression) {
        this.visitBinaryExpression((BinaryExpression)likeExpression);
    }

    public void visit(ExistsExpression existsExpression) {
        existsExpression.getRightExpression().accept((ExpressionVisitor)this);
    }

    public void visit(LongValue longValue) {
    }

    public void visit(MinorThan minorThan) {
        this.visitBinaryExpression((BinaryExpression)minorThan);
    }

    public void visit(MinorThanEquals minorThanEquals) {
        this.visitBinaryExpression((BinaryExpression)minorThanEquals);
    }

    public void visit(Multiplication multiplication) {
        this.visitBinaryExpression((BinaryExpression)multiplication);
    }

    public void visit(NotEqualsTo notEqualsTo) {
        this.visitBinaryExpression((BinaryExpression)notEqualsTo);
    }

    public void visit(NullValue nullValue) {
    }

    public void visit(OrExpression orExpression) {
        this.visitBinaryExpression((BinaryExpression)orExpression);
    }

    public void visit(XorExpression xorExpression) {
        this.visitBinaryExpression((BinaryExpression)xorExpression);
    }

    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(StringValue stringValue) {
    }

    public void visit(Subtraction subtraction) {
        this.visitBinaryExpression((BinaryExpression)subtraction);
    }

    public void visit(NotExpression notExpr) {
        notExpr.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(BitwiseRightShift expr) {
        this.visitBinaryExpression((BinaryExpression)expr);
    }

    public void visit(BitwiseLeftShift expr) {
        this.visitBinaryExpression((BinaryExpression)expr);
    }

    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept((ExpressionVisitor)this);
        binaryExpression.getRightExpression().accept((ExpressionVisitor)this);
    }

    public void visit(ExpressionList expressionList) {
        for (Expression expression : expressionList.getExpressions()) {
            expression.accept((ExpressionVisitor)this);
        }
    }

    public void visit(NamedExpressionList namedExpressionList) {
        for (Expression expression : namedExpressionList.getExpressions()) {
            expression.accept((ExpressionVisitor)this);
        }
    }

    public void visit(DateValue dateValue) {
    }

    public void visit(TimestampValue timestampValue) {
    }

    public void visit(TimeValue timeValue) {
    }

    public void visit(CaseExpression caseExpression) {
        if (caseExpression.getSwitchExpression() != null) {
            caseExpression.getSwitchExpression().accept((ExpressionVisitor)this);
        }
        if (caseExpression.getWhenClauses() != null) {
            for (WhenClause when : caseExpression.getWhenClauses()) {
                when.accept((ExpressionVisitor)this);
            }
        }
        if (caseExpression.getElseExpression() != null) {
            caseExpression.getElseExpression().accept((ExpressionVisitor)this);
        }
    }

    public void visit(WhenClause whenClause) {
        if (whenClause.getWhenExpression() != null) {
            whenClause.getWhenExpression().accept((ExpressionVisitor)this);
        }
        if (whenClause.getThenExpression() != null) {
            whenClause.getThenExpression().accept((ExpressionVisitor)this);
        }
    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {
        anyComparisonExpression.getSubSelect().getSelectBody().accept((SelectVisitor)this);
    }

    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept((FromItemVisitor)this);
        for (Join join : subjoin.getJoinList()) {
            join.getRightItem().accept((FromItemVisitor)this);
        }
    }

    public void visit(Concat concat) {
        this.visitBinaryExpression((BinaryExpression)concat);
    }

    public void visit(Matches matches) {
        this.visitBinaryExpression((BinaryExpression)matches);
    }

    public void visit(BitwiseAnd bitwiseAnd) {
        this.visitBinaryExpression((BinaryExpression)bitwiseAnd);
    }

    public void visit(BitwiseOr bitwiseOr) {
        this.visitBinaryExpression((BinaryExpression)bitwiseOr);
    }

    public void visit(BitwiseXor bitwiseXor) {
        this.visitBinaryExpression((BinaryExpression)bitwiseXor);
    }

    public void visit(CastExpression cast) {
        cast.getLeftExpression().accept((ExpressionVisitor)this);
    }

    public void visit(Modulo modulo) {
        this.visitBinaryExpression((BinaryExpression)modulo);
    }

    public void visit(AnalyticExpression analytic) {
    }

    public void visit(SetOperationList list) {
        for (SelectBody plainSelect : list.getSelects()) {
            plainSelect.accept((SelectVisitor)this);
        }
    }

    public void visit(ExtractExpression eexpr) {
    }

    public void visit(LateralSubSelect lateralSubSelect) {
        lateralSubSelect.getSubSelect().getSelectBody().accept((SelectVisitor)this);
    }

    public void visit(MultiExpressionList multiExprList) {
        for (ExpressionList exprList : multiExprList.getExprList()) {
            exprList.accept((ItemsListVisitor)this);
        }
    }

    public void visit(ValuesList valuesList) {
    }

    protected void init(boolean allowColumnProcessing) {
        this.otherItemNames = new ArrayList<String>();
        this.tables = new ArrayList<String>();
        this.userVariables = new ArrayList<String>();
        this.allowColumnProcessing = allowColumnProcessing;
    }

    public void visit(IntervalExpression iexpr) {
    }

    public void visit(JdbcNamedParameter jdbcNamedParameter) {
    }

    public void visit(OracleHierarchicalExpression oexpr) {
        if (oexpr.getStartExpression() != null) {
            oexpr.getStartExpression().accept((ExpressionVisitor)this);
        }
        if (oexpr.getConnectExpression() != null) {
            oexpr.getConnectExpression().accept((ExpressionVisitor)this);
        }
    }

    public void visit(RegExpMatchOperator rexpr) {
        this.visitBinaryExpression((BinaryExpression)rexpr);
    }

    public void visit(RegExpMySQLOperator rexpr) {
        this.visitBinaryExpression((BinaryExpression)rexpr);
    }

    public void visit(JsonExpression jsonExpr) {
    }

    public void visit(JsonOperator jsonExpr) {
    }

    public void visit(AllColumns allColumns) {
    }

    public void visit(AllTableColumns allTableColumns) {
    }

    public void visit(SelectExpressionItem item) {
        item.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(UserVariable var) {
        if (var.toString().startsWith(String.valueOf('@'))) {
            this.userVariables.add(var.getName());
        }
    }

    public void visit(NumericBind bind) {
    }

    public void visit(KeepExpression aexpr) {
    }

    public void visit(MySQLGroupConcat groupConcat) {
    }

    public void visit(ValueListExpression valueList) {
        valueList.getExpressionList().accept((ItemsListVisitor)this);
    }

    public void visit(Delete delete) {
        this.visit(delete.getTable());
        if (delete.getUsingList() != null) {
            for (Table using : delete.getUsingList()) {
                this.visit(using);
            }
        }
        if (delete.getJoins() != null) {
            for (Join join : delete.getJoins()) {
                join.getRightItem().accept((FromItemVisitor)this);
            }
        }
        if (delete.getWhere() != null) {
            delete.getWhere().accept((ExpressionVisitor)this);
        }
    }

    public void visit(Update update) {
        this.visit(update.getTable());
        if (update.getStartJoins() != null) {
            for (Join join : update.getStartJoins()) {
                join.getRightItem().accept((FromItemVisitor)this);
            }
        }
        if (update.getExpressions() != null) {
            for (Expression expression : update.getExpressions()) {
                expression.accept((ExpressionVisitor)this);
            }
        }
        if (update.getFromItem() != null) {
            update.getFromItem().accept((FromItemVisitor)this);
        }
        if (update.getJoins() != null) {
            for (Join join : update.getJoins()) {
                join.getRightItem().accept((FromItemVisitor)this);
            }
        }
        if (update.getWhere() != null) {
            update.getWhere().accept((ExpressionVisitor)this);
        }
    }

    public void visit(Insert insert) {
        this.visit(insert.getTable());
        if (insert.getItemsList() != null) {
            insert.getItemsList().accept((ItemsListVisitor)this);
        }
        if (insert.getSelect() != null) {
            this.visit(insert.getSelect());
        }
    }

    public void visit(Replace replace) {
        this.visit(replace.getTable());
        if (replace.getExpressions() != null) {
            for (Expression expression : replace.getExpressions()) {
                expression.accept((ExpressionVisitor)this);
            }
        }
        if (replace.getItemsList() != null) {
            replace.getItemsList().accept((ItemsListVisitor)this);
        }
    }

    public void visit(Drop drop) {
        this.visit(drop.getName());
    }

    public void visit(Truncate truncate) {
        this.visit(truncate.getTable());
    }

    public void visit(CreateIndex createIndex) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(CreateSchema aThis) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(CreateTable create) {
        this.visit(create.getTable());
        if (create.getSelect() != null) {
            create.getSelect().accept((StatementVisitor)this);
        }
    }

    public void visit(CreateView createView) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(Alter alter) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(Statements stmts) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(Execute execute) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(SetStatement set) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(ResetStatement reset) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(ShowColumnsStatement set) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(RowConstructor rowConstructor) {
        for (Expression expr : rowConstructor.getExprList().getExpressions()) {
            expr.accept((ExpressionVisitor)this);
        }
    }

    public void visit(RowGetExpression rowGetExpression) {
        rowGetExpression.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(HexValue hexValue) {
    }

    public void visit(Merge merge) {
        this.visit(merge.getTable());
        if (merge.getUsingTable() != null) {
            merge.getUsingTable().accept((FromItemVisitor)this);
        } else if (merge.getUsingSelect() != null) {
            merge.getUsingSelect().accept((FromItemVisitor)this);
        }
    }

    public void visit(OracleHint hint) {
    }

    public void visit(TableFunction valuesList) {
    }

    public void visit(AlterView alterView) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void visit(TimeKeyExpression timeKeyExpression) {
    }

    public void visit(DateTimeLiteralExpression literal) {
    }

    public void visit(Commit commit) {
    }

    public void visit(Upsert upsert) {
        this.visit(upsert.getTable());
        if (upsert.getItemsList() != null) {
            upsert.getItemsList().accept((ItemsListVisitor)this);
        }
        if (upsert.getSelect() != null) {
            this.visit(upsert.getSelect());
        }
    }

    public void visit(UseStatement use) {
    }

    public void visit(ParenthesisFromItem parenthesis) {
        parenthesis.getFromItem().accept((FromItemVisitor)this);
    }

    public void visit(Block block) {
        if (block.getStatements() != null) {
            this.visit(block.getStatements());
        }
    }

    public void visit(Comment comment) {
        Table table;
        if (comment.getTable() != null) {
            this.visit(comment.getTable());
        }
        if (comment.getColumn() != null && (table = comment.getColumn().getTable()) != null) {
            this.visit(table);
        }
    }

    public void visit(ValuesStatement values) {
        values.getExpressions().accept((ItemsListVisitor)this);
    }

    public void visit(DescribeStatement describe) {
        describe.getTable().accept((FromItemVisitor)this);
    }

    public void visit(ExplainStatement explain) {
        explain.getStatement().accept((StatementVisitor)this);
    }

    public void visit(NextValExpression nextVal) {
    }

    public void visit(CollateExpression col) {
        col.getLeftExpression().accept((ExpressionVisitor)this);
    }

    public void visit(ShowStatement aThis) {
    }

    public void visit(SimilarToExpression expr) {
        this.visitBinaryExpression((BinaryExpression)expr);
    }

    public void visit(DeclareStatement aThis) {
    }

    public void visit(Grant grant) {
    }

    public void visit(ArrayExpression array) {
        array.getObjExpression().accept((ExpressionVisitor)this);
        if (array.getStartIndexExpression() != null) {
            array.getIndexExpression().accept((ExpressionVisitor)this);
        }
        if (array.getStartIndexExpression() != null) {
            array.getStartIndexExpression().accept((ExpressionVisitor)this);
        }
        if (array.getStopIndexExpression() != null) {
            array.getStopIndexExpression().accept((ExpressionVisitor)this);
        }
    }

    public void visit(ArrayConstructor array) {
        for (Expression expression : array.getExpressions()) {
            expression.accept((ExpressionVisitor)this);
        }
    }

    public void visit(CreateSequence createSequence) {
        throw new UnsupportedOperationException("Finding tables from CreateSequence is not supported");
    }

    public void visit(AlterSequence alterSequence) {
        throw new UnsupportedOperationException("Finding tables from AlterSequence is not supported");
    }

    public void visit(CreateFunctionalStatement createFunctionalStatement) {
        throw new UnsupportedOperationException("Finding tables from CreateFunctionalStatement is not supported");
    }

    public void visit(ShowTablesStatement showTables) {
        throw new UnsupportedOperationException("Finding tables from ShowTablesStatement is not supported");
    }

    public void visit(VariableAssignment var) {
        var.getVariable().accept((ExpressionVisitor)this);
        var.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(XMLSerializeExpr aThis) {
    }

    public void visit(CreateSynonym createSynonym) {
        UserVariableFinder.throwUnsupported(createSynonym);
    }

    private static <T> void throwUnsupported(T type) {
        throw new UnsupportedOperationException(String.format("Finding tables from %s is not supported", type.getClass().getSimpleName()));
    }

    public void visit(TimezoneExpression aThis) {
        aThis.getLeftExpression().accept((ExpressionVisitor)this);
    }

    public void visit(SavepointStatement savepointStatement) {
    }

    public void visit(RollbackStatement rollbackStatement) {
    }

    public void visit(AlterSession alterSession) {
    }

    public void visit(JsonAggregateFunction expression) {
        Expression expr = expression.getExpression();
        if (expr != null) {
            expr.accept((ExpressionVisitor)this);
        }
        if ((expr = expression.getFilterExpression()) != null) {
            expr.accept((ExpressionVisitor)this);
        }
    }

    public void visit(JsonFunction expression) {
        for (JsonFunctionExpression expr : expression.getExpressions()) {
            expr.getExpression().accept((ExpressionVisitor)this);
        }
    }

    public void visit(ConnectByRootOperator connectByRootOperator) {
        connectByRootOperator.getColumn().accept((ExpressionVisitor)this);
    }

    public void visit(IfElseStatement ifElseStatement) {
        ifElseStatement.getIfStatement().accept((StatementVisitor)this);
        if (ifElseStatement.getElseStatement() != null) {
            ifElseStatement.getElseStatement().accept((StatementVisitor)this);
        }
    }

    public void visit(OracleNamedFunctionParameter oracleNamedFunctionParameter) {
        oracleNamedFunctionParameter.getExpression().accept((ExpressionVisitor)this);
    }

    public void visit(RenameTableStatement renameTableStatement) {
        for (Map.Entry e : renameTableStatement.getTableNames()) {
            ((Table)e.getKey()).accept((FromItemVisitor)this);
            ((Table)e.getValue()).accept((FromItemVisitor)this);
        }
    }

    public void visit(PurgeStatement purgeStatement) {
        if (purgeStatement.getPurgeObjectType() == PurgeObjectType.TABLE) {
            ((Table)purgeStatement.getObject()).accept((FromItemVisitor)this);
        }
    }

    public void visit(AlterSystemStatement alterSystemStatement) {
    }
}

