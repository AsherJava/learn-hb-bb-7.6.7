/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.antlr.runtime.BitSet
 *  org.antlr.runtime.IntStream
 *  org.antlr.runtime.MismatchedSetException
 *  org.antlr.runtime.NoViableAltException
 *  org.antlr.runtime.ParserRuleReturnScope
 *  org.antlr.runtime.RecognitionException
 *  org.antlr.runtime.RecognizerSharedState
 *  org.antlr.runtime.Token
 *  org.antlr.runtime.TokenStream
 *  org.antlr.runtime.tree.CommonTreeAdaptor
 *  org.antlr.runtime.tree.TreeAdaptor
 */
package com.jiuqi.bi.database.sql.parser.antlr;

import com.jiuqi.bi.database.sql.parser.antlr.DDLParser;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

public class ANTLRParser
extends DDLParser {
    public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADD", "ALTER", "BLOB", "CHAR", "CHAR_ID", "CLASS", "CLOB", "COLUMN", "COMMA", "COMMENT", "CONSTRAINT", "CREATE", "DATE", "DATETIME", "DEFAULT", "DEFINE", "DIGIT", "DROP", "EQ", "ESC_SEQ", "EXISTS", "FLOAT", "IF", "INDEX", "INT", "KEY", "LPAREN", "MAX", "MINUS", "MODIFY", "NOT", "NULL", "NUMBER", "NVARCHAR", "ON", "PLUS", "PRIMARY", "RAW", "RENAME", "RPAREN", "SL_COMMENT", "STRING", "TABLE", "TIMESTAMP", "TO", "UNIQUE", "VARCHAR", "WS"};
    public static final int EOF = -1;
    public static final int ADD = 4;
    public static final int ALTER = 5;
    public static final int BLOB = 6;
    public static final int CHAR = 7;
    public static final int CHAR_ID = 8;
    public static final int CLASS = 9;
    public static final int CLOB = 10;
    public static final int NCLOB = 8;
    public static final int COLUMN = 11;
    public static final int COMMA = 12;
    public static final int COMMENT = 13;
    public static final int CONSTRAINT = 14;
    public static final int CREATE = 15;
    public static final int DATE = 16;
    public static final int DATETIME = 17;
    public static final int DEFAULT = 18;
    public static final int DEFINE = 19;
    public static final int DIGIT = 20;
    public static final int DROP = 21;
    public static final int EQ = 22;
    public static final int ESC_SEQ = 23;
    public static final int EXISTS = 24;
    public static final int FLOAT = 25;
    public static final int IF = 26;
    public static final int INDEX = 27;
    public static final int INT = 28;
    public static final int KEY = 29;
    public static final int LPAREN = 30;
    public static final int MAX = 31;
    public static final int MINUS = 32;
    public static final int MODIFY = 33;
    public static final int NOT = 34;
    public static final int NULL = 35;
    public static final int NUMBER = 36;
    public static final int NVARCHAR = 37;
    public static final int ON = 38;
    public static final int PLUS = 39;
    public static final int PRIMARY = 40;
    public static final int RAW = 41;
    public static final int RENAME = 42;
    public static final int RPAREN = 43;
    public static final int SL_COMMENT = 44;
    public static final int STRING = 45;
    public static final int TABLE = 46;
    public static final int TIMESTAMP = 47;
    public static final int TO = 48;
    public static final int UNIQUE = 49;
    public static final int VARCHAR = 50;
    public static final int WS = 51;
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();
    private Token currToken;
    private boolean isUniqueIdx;
    private boolean isJudgeExists = false;
    private String propKey;
    public static final BitSet FOLLOW_expression_in_evaluate54 = new BitSet(new long[]{0L});
    public static final BitSet FOLLOW_EOF_in_evaluate56 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_create_table_in_expression65 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_create_index_in_expression69 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_alter_table_in_expression73 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_drop_table_in_expression77 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_drop_index_in_expression81 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_rename_table_in_expression85 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_rename_index_in_expression89 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ALTER_in_rename_table101 = new BitSet(new long[]{0x400000000000L});
    public static final BitSet FOLLOW_TABLE_in_rename_table103 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_table105 = new BitSet(new long[]{0x40000000000L});
    public static final BitSet FOLLOW_RENAME_in_rename_table111 = new BitSet(new long[]{0x1000000004000L});
    public static final BitSet FOLLOW_CONSTRAINT_in_rename_table114 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_table120 = new BitSet(new long[]{0x1000000000000L});
    public static final BitSet FOLLOW_TO_in_rename_table126 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_table128 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_DROP_in_drop_index141 = new BitSet(new long[]{0x8000000L});
    public static final BitSet FOLLOW_INDEX_in_drop_index143 = new BitSet(new long[]{0x4000100L});
    public static final BitSet FOLLOW_IF_in_drop_index146 = new BitSet(new long[]{0x1000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_index148 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_drop_index154 = new BitSet(new long[]{0x4000000000L});
    public static final BitSet FOLLOW_ON_in_drop_index158 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_drop_index160 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ALTER_in_rename_index174 = new BitSet(new long[]{0x8000000L});
    public static final BitSet FOLLOW_INDEX_in_rename_index176 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_index178 = new BitSet(new long[]{0x40000000000L});
    public static final BitSet FOLLOW_RENAME_in_rename_index182 = new BitSet(new long[]{0x1000000000000L});
    public static final BitSet FOLLOW_TO_in_rename_index184 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_index186 = new BitSet(new long[]{0x4000000000L});
    public static final BitSet FOLLOW_ON_in_rename_index192 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_index194 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_DROP_in_drop_table208 = new BitSet(new long[]{0x400000000000L});
    public static final BitSet FOLLOW_TABLE_in_drop_table210 = new BitSet(new long[]{0x4000100L});
    public static final BitSet FOLLOW_IF_in_drop_table213 = new BitSet(new long[]{0x1000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_table215 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_drop_table221 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ALTER_in_alter_table235 = new BitSet(new long[]{0x400000000000L});
    public static final BitSet FOLLOW_TABLE_in_alter_table237 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_alter_table239 = new BitSet(new long[]{4406638542864L});
    public static final BitSet FOLLOW_addmodify_column_in_alter_table243 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_drop_column_in_alter_table247 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_rename_column_in_alter_table251 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_add_constraint_in_alter_table255 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_drop_constraint_in_alter_table259 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ADD_in_addmodify_column274 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_MODIFY_in_addmodify_column280 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_column_define_in_addmodify_column286 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_DROP_in_drop_column304 = new BitSet(new long[]{2048L});
    public static final BitSet FOLLOW_COLUMN_in_drop_column306 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_drop_column308 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RENAME_in_rename_column328 = new BitSet(new long[]{2048L});
    public static final BitSet FOLLOW_COLUMN_in_rename_column330 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_rename_column332 = new BitSet(new long[]{0x1000000000000L});
    public static final BitSet FOLLOW_TO_in_rename_column338 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_column_define_in_rename_column340 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ADD_in_add_constraint358 = new BitSet(new long[]{16384L});
    public static final BitSet FOLLOW_CONSTRAINT_in_add_constraint360 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_add_constraint362 = new BitSet(new long[]{0x10000000000L});
    public static final BitSet FOLLOW_PRIMARY_in_add_constraint368 = new BitSet(new long[]{0x20000000L});
    public static final BitSet FOLLOW_KEY_in_add_constraint370 = new BitSet(new long[]{0x40000000L});
    public static final BitSet FOLLOW_LPAREN_in_add_constraint372 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_add_constraint374 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_add_constraint379 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_add_constraint381 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_RPAREN_in_add_constraint387 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_DROP_in_drop_constraint402 = new BitSet(new long[]{16384L});
    public static final BitSet FOLLOW_CONSTRAINT_in_drop_constraint404 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_drop_constraint406 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_CREATE_in_create_index422 = new BitSet(new long[]{0x2000008000000L});
    public static final BitSet FOLLOW_UNIQUE_in_create_index425 = new BitSet(new long[]{0x8000000L});
    public static final BitSet FOLLOW_INDEX_in_create_index431 = new BitSet(new long[]{0x4000100L});
    public static final BitSet FOLLOW_IF_in_create_index434 = new BitSet(new long[]{0x400000000L});
    public static final BitSet FOLLOW_NOT_in_create_index436 = new BitSet(new long[]{0x1000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_index438 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_index443 = new BitSet(new long[]{0x4000000000L});
    public static final BitSet FOLLOW_ON_in_create_index448 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_index450 = new BitSet(new long[]{0x40000000L});
    public static final BitSet FOLLOW_LPAREN_in_create_index454 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_index456 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_create_index463 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_index465 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_RPAREN_in_create_index471 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_CREATE_in_create_table486 = new BitSet(new long[]{0x400000000000L});
    public static final BitSet FOLLOW_TABLE_in_create_table488 = new BitSet(new long[]{0x4000100L});
    public static final BitSet FOLLOW_IF_in_create_table491 = new BitSet(new long[]{0x400000000L});
    public static final BitSet FOLLOW_NOT_in_create_table493 = new BitSet(new long[]{0x1000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_table495 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_table500 = new BitSet(new long[]{0x40000000L});
    public static final BitSet FOLLOW_LPAREN_in_create_table505 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_column_define_in_create_table507 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_create_table513 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_column_define_in_create_table515 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_create_table523 = new BitSet(new long[]{0x10000004000L});
    public static final BitSet FOLLOW_CONSTRAINT_in_create_table526 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_table528 = new BitSet(new long[]{0x10000000000L});
    public static final BitSet FOLLOW_PRIMARY_in_create_table534 = new BitSet(new long[]{0x20000000L});
    public static final BitSet FOLLOW_KEY_in_create_table536 = new BitSet(new long[]{0x40000000L});
    public static final BitSet FOLLOW_LPAREN_in_create_table538 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_table540 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_create_table546 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_char_id_in_create_table548 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_RPAREN_in_create_table555 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_create_table569 = new BitSet(new long[]{8192L});
    public static final BitSet FOLLOW_COMMENT_in_create_table571 = new BitSet(new long[]{0x200000000000L});
    public static final BitSet FOLLOW_STRING_in_create_table576 = new BitSet(new long[]{0x80000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_table583 = new BitSet(new long[]{0x200012000102L});
    public static final BitSet FOLLOW_table_property_define_in_create_table586 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_default_val_in_table_property_define601 = new BitSet(new long[]{0x400000L});
    public static final BitSet FOLLOW_EQ_in_table_property_define604 = new BitSet(new long[]{0x200012000100L});
    public static final BitSet FOLLOW_default_val_in_table_property_define606 = new BitSet(new long[]{4098L});
    public static final BitSet FOLLOW_COMMA_in_table_property_define610 = new BitSet(new long[]{0x200012000100L});
    public static final BitSet FOLLOW_default_val_in_table_property_define612 = new BitSet(new long[]{0x400000L});
    public static final BitSet FOLLOW_EQ_in_table_property_define615 = new BitSet(new long[]{0x200012000100L});
    public static final BitSet FOLLOW_default_val_in_table_property_define617 = new BitSet(new long[]{4098L});
    public static final BitSet FOLLOW_char_id_in_column_define630 = new BitSet(new long[]{1269042577081408L});
    public static final BitSet FOLLOW_column_type_in_column_define636 = new BitSet(new long[]{52613619714L});
    public static final BitSet FOLLOW_LPAREN_in_column_define643 = new BitSet(new long[]{0x90000000L});
    public static final BitSet FOLLOW_set_in_column_define648 = new BitSet(new long[]{0x80000001000L});
    public static final BitSet FOLLOW_COMMA_in_column_define657 = new BitSet(new long[]{0x10000000L});
    public static final BitSet FOLLOW_INT_in_column_define661 = new BitSet(new long[]{0x80000000000L});
    public static final BitSet FOLLOW_RPAREN_in_column_define666 = new BitSet(new long[]{51539877890L});
    public static final BitSet FOLLOW_DEFAULT_in_column_define672 = new BitSet(new long[]{0x200012000100L});
    public static final BitSet FOLLOW_default_val_in_column_define674 = new BitSet(new long[]{0xC00002002L});
    public static final BitSet FOLLOW_NULL_in_column_define683 = new BitSet(new long[]{8194L});
    public static final BitSet FOLLOW_NOT_in_column_define691 = new BitSet(new long[]{0x800000000L});
    public static final BitSet FOLLOW_NULL_in_column_define693 = new BitSet(new long[]{8194L});
    public static final BitSet FOLLOW_COMMENT_in_column_define709 = new BitSet(new long[]{0x200000000000L});
    public static final BitSet FOLLOW_STRING_in_column_define714 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_set_in_column_type734 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_CHAR_ID_in_char_id786 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_set_in_default_val801 = new BitSet(new long[]{2L});

    public DDLParser[] getDelegates() {
        return new DDLParser[0];
    }

    public ANTLRParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }

    public ANTLRParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    public String[] getTokenNames() {
        return tokenNames;
    }

    public String getGrammarFileName() {
        return "D:\\antlr4\\antlr(\ufffd\ufffd)\\SQL\\ANTLR.g";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final evaluate_return evaluate() throws RecognitionException {
        evaluate_return retval = new evaluate_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token EOF2 = null;
        expression_return expression1 = null;
        Object EOF2_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.pushFollow(FOLLOW_expression_in_evaluate54);
            expression1 = this.expression();
            --this.state._fsp;
            this.adaptor.addChild(root_0, expression1.getTree());
            EOF2 = (Token)this.match((IntStream)this.input, -1, FOLLOW_EOF_in_evaluate56);
            EOF2_tree = this.adaptor.create(EOF2);
            this.adaptor.addChild(root_0, EOF2_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final expression_return expression() throws RecognitionException {
        expression_return retval = new expression_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        create_table_return create_table3 = null;
        create_index_return create_index4 = null;
        alter_table_return alter_table5 = null;
        drop_table_return drop_table6 = null;
        drop_index_return drop_index7 = null;
        rename_table_return rename_table8 = null;
        rename_index_return rename_index9 = null;
        try {
            int alt1 = 7;
            switch (this.input.LA(1)) {
                case 15: {
                    int LA1_1 = this.input.LA(2);
                    if (LA1_1 == 46) {
                        alt1 = 1;
                        break;
                    }
                    if (LA1_1 == 27 || LA1_1 == 49) {
                        alt1 = 2;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 1, 1, (IntStream)this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 5: {
                    int LA1_2 = this.input.LA(2);
                    if (LA1_2 == 46) {
                        int LA1_6 = this.input.LA(3);
                        if (LA1_6 == 8) {
                            int LA1_10 = this.input.LA(4);
                            if (LA1_10 == 4 || LA1_10 == 21 || LA1_10 == 33) {
                                alt1 = 3;
                                break;
                            }
                            if (LA1_10 == 42) {
                                int LA1_12 = this.input.LA(5);
                                if (LA1_12 == 11) {
                                    alt1 = 3;
                                    break;
                                }
                                if (LA1_12 == 14 || LA1_12 == 48) {
                                    alt1 = 6;
                                    break;
                                }
                                int nvaeMark = this.input.mark();
                                try {
                                    for (int nvaeConsume = 0; nvaeConsume < 4; ++nvaeConsume) {
                                        this.input.consume();
                                    }
                                    NoViableAltException nvae = new NoViableAltException("", 1, 12, (IntStream)this.input);
                                    throw nvae;
                                }
                                catch (Throwable throwable) {
                                    this.input.rewind(nvaeMark);
                                    throw throwable;
                                }
                            }
                            int nvaeMark = this.input.mark();
                            try {
                                for (int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                }
                                NoViableAltException nvae = new NoViableAltException("", 1, 10, (IntStream)this.input);
                                throw nvae;
                            }
                            catch (Throwable throwable) {
                                this.input.rewind(nvaeMark);
                                throw throwable;
                            }
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            for (int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                this.input.consume();
                            }
                            NoViableAltException nvae = new NoViableAltException("", 1, 6, (IntStream)this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                    if (LA1_2 == 27) {
                        alt1 = 7;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 1, 2, (IntStream)this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 21: {
                    int LA1_3 = this.input.LA(2);
                    if (LA1_3 == 46) {
                        alt1 = 4;
                        break;
                    }
                    if (LA1_3 == 27) {
                        alt1 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 1, 3, (IntStream)this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 1, 0, (IntStream)this.input);
                    throw nvae;
                }
            }
            switch (alt1) {
                case 1: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_create_table_in_expression65);
                    create_table3 = this.create_table();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, create_table3.getTree());
                    break;
                }
                case 2: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_create_index_in_expression69);
                    create_index4 = this.create_index();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, create_index4.getTree());
                    break;
                }
                case 3: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_alter_table_in_expression73);
                    alter_table5 = this.alter_table();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, alter_table5.getTree());
                    break;
                }
                case 4: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_drop_table_in_expression77);
                    drop_table6 = this.drop_table();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, drop_table6.getTree());
                    break;
                }
                case 5: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_drop_index_in_expression81);
                    drop_index7 = this.drop_index();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, drop_index7.getTree());
                    break;
                }
                case 6: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_rename_table_in_expression85);
                    rename_table8 = this.rename_table();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, rename_table8.getTree());
                    break;
                }
                case 7: {
                    root_0 = this.adaptor.nil();
                    this.pushFollow(FOLLOW_rename_index_in_expression89);
                    rename_index9 = this.rename_index();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, rename_index9.getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rename_table_return rename_table() throws RecognitionException {
        rename_table_return retval = new rename_table_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token ALTER10 = null;
        Token TABLE11 = null;
        Token RENAME13 = null;
        Token CONSTRAINT14 = null;
        Token TO16 = null;
        char_id_return char_id12 = null;
        char_id_return char_id15 = null;
        char_id_return char_id17 = null;
        Object ALTER10_tree = null;
        Object TABLE11_tree = null;
        Object RENAME13_tree = null;
        Object CONSTRAINT14_tree = null;
        Object TO16_tree = null;
        try {
            root_0 = this.adaptor.nil();
            ALTER10 = (Token)this.match((IntStream)this.input, 5, FOLLOW_ALTER_in_rename_table101);
            ALTER10_tree = this.adaptor.create(ALTER10);
            this.adaptor.addChild(root_0, ALTER10_tree);
            TABLE11 = (Token)this.match((IntStream)this.input, 46, FOLLOW_TABLE_in_rename_table103);
            TABLE11_tree = this.adaptor.create(TABLE11);
            this.adaptor.addChild(root_0, TABLE11_tree);
            this.pushFollow(FOLLOW_char_id_in_rename_table105);
            char_id12 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id12.getTree());
            this.newRenameTableStatement(this.currToken.getText());
            RENAME13 = (Token)this.match((IntStream)this.input, 42, FOLLOW_RENAME_in_rename_table111);
            RENAME13_tree = this.adaptor.create(RENAME13);
            this.adaptor.addChild(root_0, RENAME13_tree);
            int alt2 = 2;
            int LA2_0 = this.input.LA(1);
            if (LA2_0 == 14) {
                alt2 = 1;
            }
            switch (alt2) {
                case 1: {
                    CONSTRAINT14 = (Token)this.match((IntStream)this.input, 14, FOLLOW_CONSTRAINT_in_rename_table114);
                    CONSTRAINT14_tree = this.adaptor.create(CONSTRAINT14);
                    this.adaptor.addChild(root_0, CONSTRAINT14_tree);
                    this.newRenameConstraintStatement(this.currToken.getText());
                    this.pushFollow(FOLLOW_char_id_in_rename_table120);
                    char_id15 = this.char_id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, char_id15.getTree());
                    this.setConstraintNameForRenameConstraint(this.currToken.getText());
                }
            }
            TO16 = (Token)this.match((IntStream)this.input, 48, FOLLOW_TO_in_rename_table126);
            TO16_tree = this.adaptor.create(TO16);
            this.adaptor.addChild(root_0, TO16_tree);
            this.pushFollow(FOLLOW_char_id_in_rename_table128);
            char_id17 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id17.getTree());
            this.setRenameForTable(this.currToken.getText());
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final drop_index_return drop_index() throws RecognitionException {
        drop_index_return retval = new drop_index_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token DROP18 = null;
        Token INDEX19 = null;
        Token IF20 = null;
        Token EXISTS21 = null;
        Token ON23 = null;
        char_id_return char_id22 = null;
        char_id_return char_id24 = null;
        Object DROP18_tree = null;
        Object INDEX19_tree = null;
        Object IF20_tree = null;
        Object EXISTS21_tree = null;
        Object ON23_tree = null;
        try {
            root_0 = this.adaptor.nil();
            DROP18 = (Token)this.match((IntStream)this.input, 21, FOLLOW_DROP_in_drop_index141);
            DROP18_tree = this.adaptor.create(DROP18);
            this.adaptor.addChild(root_0, DROP18_tree);
            INDEX19 = (Token)this.match((IntStream)this.input, 27, FOLLOW_INDEX_in_drop_index143);
            INDEX19_tree = this.adaptor.create(INDEX19);
            this.adaptor.addChild(root_0, INDEX19_tree);
            int alt3 = 2;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 26) {
                alt3 = 1;
            }
            switch (alt3) {
                case 1: {
                    IF20 = (Token)this.match((IntStream)this.input, 26, FOLLOW_IF_in_drop_index146);
                    IF20_tree = this.adaptor.create(IF20);
                    this.adaptor.addChild(root_0, IF20_tree);
                    EXISTS21 = (Token)this.match((IntStream)this.input, 24, FOLLOW_EXISTS_in_drop_index148);
                    EXISTS21_tree = this.adaptor.create(EXISTS21);
                    this.adaptor.addChild(root_0, EXISTS21_tree);
                    this.isJudgeExists = true;
                }
            }
            this.pushFollow(FOLLOW_char_id_in_drop_index154);
            char_id22 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id22.getTree());
            this.newDropIndexStatement(this.currToken.getText(), this.isJudgeExists);
            this.isJudgeExists = false;
            ON23 = (Token)this.match((IntStream)this.input, 38, FOLLOW_ON_in_drop_index158);
            ON23_tree = this.adaptor.create(ON23);
            this.adaptor.addChild(root_0, ON23_tree);
            this.pushFollow(FOLLOW_char_id_in_drop_index160);
            char_id24 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id24.getTree());
            this.setTableNameForIndex(this.currToken.getText());
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rename_index_return rename_index() throws RecognitionException {
        rename_index_return retval = new rename_index_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token ALTER25 = null;
        Token INDEX26 = null;
        Token RENAME28 = null;
        Token TO29 = null;
        Token ON31 = null;
        char_id_return char_id27 = null;
        char_id_return char_id30 = null;
        char_id_return char_id32 = null;
        Object ALTER25_tree = null;
        Object INDEX26_tree = null;
        Object RENAME28_tree = null;
        Object TO29_tree = null;
        Object ON31_tree = null;
        try {
            root_0 = this.adaptor.nil();
            ALTER25 = (Token)this.match((IntStream)this.input, 5, FOLLOW_ALTER_in_rename_index174);
            ALTER25_tree = this.adaptor.create(ALTER25);
            this.adaptor.addChild(root_0, ALTER25_tree);
            INDEX26 = (Token)this.match((IntStream)this.input, 27, FOLLOW_INDEX_in_rename_index176);
            INDEX26_tree = this.adaptor.create(INDEX26);
            this.adaptor.addChild(root_0, INDEX26_tree);
            this.pushFollow(FOLLOW_char_id_in_rename_index178);
            char_id27 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id27.getTree());
            this.newRenameIndexStatement(this.currToken.getText());
            RENAME28 = (Token)this.match((IntStream)this.input, 42, FOLLOW_RENAME_in_rename_index182);
            RENAME28_tree = this.adaptor.create(RENAME28);
            this.adaptor.addChild(root_0, RENAME28_tree);
            TO29 = (Token)this.match((IntStream)this.input, 48, FOLLOW_TO_in_rename_index184);
            TO29_tree = this.adaptor.create(TO29);
            this.adaptor.addChild(root_0, TO29_tree);
            this.pushFollow(FOLLOW_char_id_in_rename_index186);
            char_id30 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id30.getTree());
            this.setRenameForIndex(this.currToken.getText());
            ON31 = (Token)this.match((IntStream)this.input, 38, FOLLOW_ON_in_rename_index192);
            ON31_tree = this.adaptor.create(ON31);
            this.adaptor.addChild(root_0, ON31_tree);
            this.pushFollow(FOLLOW_char_id_in_rename_index194);
            char_id32 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id32.getTree());
            this.setTableNameForIndex(this.currToken.getText());
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final drop_table_return drop_table() throws RecognitionException {
        drop_table_return retval = new drop_table_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token DROP33 = null;
        Token TABLE34 = null;
        Token IF35 = null;
        Token EXISTS36 = null;
        char_id_return char_id37 = null;
        Object DROP33_tree = null;
        Object TABLE34_tree = null;
        Object IF35_tree = null;
        Object EXISTS36_tree = null;
        try {
            root_0 = this.adaptor.nil();
            DROP33 = (Token)this.match((IntStream)this.input, 21, FOLLOW_DROP_in_drop_table208);
            DROP33_tree = this.adaptor.create(DROP33);
            this.adaptor.addChild(root_0, DROP33_tree);
            TABLE34 = (Token)this.match((IntStream)this.input, 46, FOLLOW_TABLE_in_drop_table210);
            TABLE34_tree = this.adaptor.create(TABLE34);
            this.adaptor.addChild(root_0, TABLE34_tree);
            int alt4 = 2;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 26) {
                alt4 = 1;
            }
            switch (alt4) {
                case 1: {
                    IF35 = (Token)this.match((IntStream)this.input, 26, FOLLOW_IF_in_drop_table213);
                    IF35_tree = this.adaptor.create(IF35);
                    this.adaptor.addChild(root_0, IF35_tree);
                    EXISTS36 = (Token)this.match((IntStream)this.input, 24, FOLLOW_EXISTS_in_drop_table215);
                    EXISTS36_tree = this.adaptor.create(EXISTS36);
                    this.adaptor.addChild(root_0, EXISTS36_tree);
                    this.isJudgeExists = true;
                }
            }
            this.pushFollow(FOLLOW_char_id_in_drop_table221);
            char_id37 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id37.getTree());
            this.newDropTableStatement(this.currToken.getText(), this.isJudgeExists);
            this.isJudgeExists = false;
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final alter_table_return alter_table() throws RecognitionException {
        alter_table_return retval = new alter_table_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token ALTER38 = null;
        Token TABLE39 = null;
        char_id_return char_id40 = null;
        addmodify_column_return addmodify_column41 = null;
        drop_column_return drop_column42 = null;
        rename_column_return rename_column43 = null;
        add_constraint_return add_constraint44 = null;
        drop_constraint_return drop_constraint45 = null;
        Object ALTER38_tree = null;
        Object TABLE39_tree = null;
        try {
            root_0 = this.adaptor.nil();
            ALTER38 = (Token)this.match((IntStream)this.input, 5, FOLLOW_ALTER_in_alter_table235);
            ALTER38_tree = this.adaptor.create(ALTER38);
            this.adaptor.addChild(root_0, ALTER38_tree);
            TABLE39 = (Token)this.match((IntStream)this.input, 46, FOLLOW_TABLE_in_alter_table237);
            TABLE39_tree = this.adaptor.create(TABLE39);
            this.adaptor.addChild(root_0, TABLE39_tree);
            this.pushFollow(FOLLOW_char_id_in_alter_table239);
            char_id40 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id40.getTree());
            int alt5 = 5;
            switch (this.input.LA(1)) {
                case 4: {
                    int LA5_1 = this.input.LA(2);
                    if (LA5_1 == 14) {
                        alt5 = 4;
                        break;
                    }
                    if (LA5_1 == 8) {
                        alt5 = 1;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 5, 1, (IntStream)this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 33: {
                    alt5 = 1;
                    break;
                }
                case 21: {
                    int LA5_3 = this.input.LA(2);
                    if (LA5_3 == 11) {
                        alt5 = 2;
                        break;
                    }
                    if (LA5_3 == 14) {
                        alt5 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 5, 3, (IntStream)this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 42: {
                    alt5 = 3;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 5, 0, (IntStream)this.input);
                    throw nvae;
                }
            }
            switch (alt5) {
                case 1: {
                    this.pushFollow(FOLLOW_addmodify_column_in_alter_table243);
                    addmodify_column41 = this.addmodify_column();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, addmodify_column41.getTree());
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_drop_column_in_alter_table247);
                    drop_column42 = this.drop_column();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, drop_column42.getTree());
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_rename_column_in_alter_table251);
                    rename_column43 = this.rename_column();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, rename_column43.getTree());
                    break;
                }
                case 4: {
                    this.pushFollow(FOLLOW_add_constraint_in_alter_table255);
                    add_constraint44 = this.add_constraint();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, add_constraint44.getTree());
                    break;
                }
                case 5: {
                    this.pushFollow(FOLLOW_drop_constraint_in_alter_table259);
                    drop_constraint45 = this.drop_constraint();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, drop_constraint45.getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final addmodify_column_return addmodify_column() throws RecognitionException {
        addmodify_column_return retval = new addmodify_column_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token ADD46 = null;
        Token MODIFY47 = null;
        column_define_return column_define48 = null;
        Object ADD46_tree = null;
        Object MODIFY47_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.newAlterColumnStatement(this.currToken.getText());
            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 == 4) {
                alt6 = 1;
            } else if (LA6_0 == 33) {
                alt6 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 6, 0, (IntStream)this.input);
                throw nvae;
            }
            switch (alt6) {
                case 1: {
                    ADD46 = (Token)this.match((IntStream)this.input, 4, FOLLOW_ADD_in_addmodify_column274);
                    ADD46_tree = this.adaptor.create(ADD46);
                    this.adaptor.addChild(root_0, ADD46_tree);
                    this.recordColumnInfo("ALTERTYPE", "add");
                    break;
                }
                case 2: {
                    MODIFY47 = (Token)this.match((IntStream)this.input, 33, FOLLOW_MODIFY_in_addmodify_column280);
                    MODIFY47_tree = this.adaptor.create(MODIFY47);
                    this.adaptor.addChild(root_0, MODIFY47_tree);
                    this.recordColumnInfo("ALTERTYPE", "modify");
                }
            }
            this.pushFollow(FOLLOW_column_define_in_addmodify_column286);
            column_define48 = this.column_define();
            --this.state._fsp;
            this.adaptor.addChild(root_0, column_define48.getTree());
            this.addAlterColumnInfo();
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final drop_column_return drop_column() throws RecognitionException {
        drop_column_return retval = new drop_column_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token DROP49 = null;
        Token COLUMN50 = null;
        char_id_return char_id51 = null;
        Object DROP49_tree = null;
        Object COLUMN50_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.newAlterColumnStatement(this.currToken.getText());
            DROP49 = (Token)this.match((IntStream)this.input, 21, FOLLOW_DROP_in_drop_column304);
            DROP49_tree = this.adaptor.create(DROP49);
            this.adaptor.addChild(root_0, DROP49_tree);
            COLUMN50 = (Token)this.match((IntStream)this.input, 11, FOLLOW_COLUMN_in_drop_column306);
            COLUMN50_tree = this.adaptor.create(COLUMN50);
            this.adaptor.addChild(root_0, COLUMN50_tree);
            this.pushFollow(FOLLOW_char_id_in_drop_column308);
            char_id51 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id51.getTree());
            this.recordColumnInfo("ALTERTYPE", "drop");
            this.recordColumnInfo("COLNAME", this.currToken.getText());
            this.addAlterColumnInfo();
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rename_column_return rename_column() throws RecognitionException {
        rename_column_return retval = new rename_column_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token RENAME52 = null;
        Token COLUMN53 = null;
        Token TO55 = null;
        char_id_return char_id54 = null;
        column_define_return column_define56 = null;
        Object RENAME52_tree = null;
        Object COLUMN53_tree = null;
        Object TO55_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.newAlterColumnStatement(this.currToken.getText());
            RENAME52 = (Token)this.match((IntStream)this.input, 42, FOLLOW_RENAME_in_rename_column328);
            RENAME52_tree = this.adaptor.create(RENAME52);
            this.adaptor.addChild(root_0, RENAME52_tree);
            COLUMN53 = (Token)this.match((IntStream)this.input, 11, FOLLOW_COLUMN_in_rename_column330);
            COLUMN53_tree = this.adaptor.create(COLUMN53);
            this.adaptor.addChild(root_0, COLUMN53_tree);
            this.pushFollow(FOLLOW_char_id_in_rename_column332);
            char_id54 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id54.getTree());
            this.recordColumnInfo("ALTERTYPE", "rename");
            this.recordColumnInfo("ORG_COL_NAME", this.currToken.getText());
            TO55 = (Token)this.match((IntStream)this.input, 48, FOLLOW_TO_in_rename_column338);
            TO55_tree = this.adaptor.create(TO55);
            this.adaptor.addChild(root_0, TO55_tree);
            this.pushFollow(FOLLOW_column_define_in_rename_column340);
            column_define56 = this.column_define();
            --this.state._fsp;
            this.adaptor.addChild(root_0, column_define56.getTree());
            this.addAlterColumnInfo();
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final add_constraint_return add_constraint() throws RecognitionException {
        add_constraint_return retval = new add_constraint_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token ADD57 = null;
        Token CONSTRAINT58 = null;
        Token PRIMARY60 = null;
        Token KEY61 = null;
        Token LPAREN62 = null;
        Token COMMA64 = null;
        Token RPAREN66 = null;
        char_id_return char_id59 = null;
        char_id_return char_id63 = null;
        char_id_return char_id65 = null;
        Object ADD57_tree = null;
        Object CONSTRAINT58_tree = null;
        Object PRIMARY60_tree = null;
        Object KEY61_tree = null;
        Object LPAREN62_tree = null;
        Object COMMA64_tree = null;
        Object RPAREN66_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.newAlterPrimarykeyStatement(this.currToken.getText(), "add");
            ADD57 = (Token)this.match((IntStream)this.input, 4, FOLLOW_ADD_in_add_constraint358);
            ADD57_tree = this.adaptor.create(ADD57);
            this.adaptor.addChild(root_0, ADD57_tree);
            CONSTRAINT58 = (Token)this.match((IntStream)this.input, 14, FOLLOW_CONSTRAINT_in_add_constraint360);
            CONSTRAINT58_tree = this.adaptor.create(CONSTRAINT58);
            this.adaptor.addChild(root_0, CONSTRAINT58_tree);
            this.pushFollow(FOLLOW_char_id_in_add_constraint362);
            char_id59 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id59.getTree());
            this.setPrimaryKeyName(this.currToken.getText());
            PRIMARY60 = (Token)this.match((IntStream)this.input, 40, FOLLOW_PRIMARY_in_add_constraint368);
            PRIMARY60_tree = this.adaptor.create(PRIMARY60);
            this.adaptor.addChild(root_0, PRIMARY60_tree);
            KEY61 = (Token)this.match((IntStream)this.input, 29, FOLLOW_KEY_in_add_constraint370);
            KEY61_tree = this.adaptor.create(KEY61);
            this.adaptor.addChild(root_0, KEY61_tree);
            LPAREN62 = (Token)this.match((IntStream)this.input, 30, FOLLOW_LPAREN_in_add_constraint372);
            LPAREN62_tree = this.adaptor.create(LPAREN62);
            this.adaptor.addChild(root_0, LPAREN62_tree);
            this.pushFollow(FOLLOW_char_id_in_add_constraint374);
            char_id63 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id63.getTree());
            this.addPrimaryColumn(this.currToken.getText());
            block7: while (true) {
                int alt7 = 2;
                int LA7_0 = this.input.LA(1);
                if (LA7_0 == 12) {
                    alt7 = 1;
                }
                switch (alt7) {
                    case 1: {
                        COMMA64 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_add_constraint379);
                        COMMA64_tree = this.adaptor.create(COMMA64);
                        this.adaptor.addChild(root_0, COMMA64_tree);
                        this.pushFollow(FOLLOW_char_id_in_add_constraint381);
                        char_id65 = this.char_id();
                        --this.state._fsp;
                        this.adaptor.addChild(root_0, char_id65.getTree());
                        this.addPrimaryColumn(this.currToken.getText());
                        continue block7;
                    }
                }
                break;
            }
            RPAREN66 = (Token)this.match((IntStream)this.input, 43, FOLLOW_RPAREN_in_add_constraint387);
            RPAREN66_tree = this.adaptor.create(RPAREN66);
            this.adaptor.addChild(root_0, RPAREN66_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final drop_constraint_return drop_constraint() throws RecognitionException {
        drop_constraint_return retval = new drop_constraint_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token DROP67 = null;
        Token CONSTRAINT68 = null;
        char_id_return char_id69 = null;
        Object DROP67_tree = null;
        Object CONSTRAINT68_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.newAlterPrimarykeyStatement(this.currToken.getText(), "drop");
            DROP67 = (Token)this.match((IntStream)this.input, 21, FOLLOW_DROP_in_drop_constraint402);
            DROP67_tree = this.adaptor.create(DROP67);
            this.adaptor.addChild(root_0, DROP67_tree);
            CONSTRAINT68 = (Token)this.match((IntStream)this.input, 14, FOLLOW_CONSTRAINT_in_drop_constraint404);
            CONSTRAINT68_tree = this.adaptor.create(CONSTRAINT68);
            this.adaptor.addChild(root_0, CONSTRAINT68_tree);
            this.pushFollow(FOLLOW_char_id_in_drop_constraint406);
            char_id69 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id69.getTree());
            this.setPrimaryKeyName(this.currToken.getText());
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final create_index_return create_index() throws RecognitionException {
        create_index_return retval = new create_index_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token CREATE70 = null;
        Token UNIQUE71 = null;
        Token INDEX72 = null;
        Token IF73 = null;
        Token NOT74 = null;
        Token EXISTS75 = null;
        Token ON77 = null;
        Token LPAREN79 = null;
        Token COMMA81 = null;
        Token RPAREN83 = null;
        char_id_return char_id76 = null;
        char_id_return char_id78 = null;
        char_id_return char_id80 = null;
        char_id_return char_id82 = null;
        Object CREATE70_tree = null;
        Object UNIQUE71_tree = null;
        Object INDEX72_tree = null;
        Object IF73_tree = null;
        Object NOT74_tree = null;
        Object EXISTS75_tree = null;
        Object ON77_tree = null;
        Object LPAREN79_tree = null;
        Object COMMA81_tree = null;
        Object RPAREN83_tree = null;
        try {
            root_0 = this.adaptor.nil();
            CREATE70 = (Token)this.match((IntStream)this.input, 15, FOLLOW_CREATE_in_create_index422);
            CREATE70_tree = this.adaptor.create(CREATE70);
            this.adaptor.addChild(root_0, CREATE70_tree);
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 == 49) {
                alt8 = 1;
            }
            switch (alt8) {
                case 1: {
                    UNIQUE71 = (Token)this.match((IntStream)this.input, 49, FOLLOW_UNIQUE_in_create_index425);
                    UNIQUE71_tree = this.adaptor.create(UNIQUE71);
                    this.adaptor.addChild(root_0, UNIQUE71_tree);
                    this.isUniqueIdx = true;
                }
            }
            INDEX72 = (Token)this.match((IntStream)this.input, 27, FOLLOW_INDEX_in_create_index431);
            INDEX72_tree = this.adaptor.create(INDEX72);
            this.adaptor.addChild(root_0, INDEX72_tree);
            int alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 26) {
                alt9 = 1;
            }
            switch (alt9) {
                case 1: {
                    IF73 = (Token)this.match((IntStream)this.input, 26, FOLLOW_IF_in_create_index434);
                    IF73_tree = this.adaptor.create(IF73);
                    this.adaptor.addChild(root_0, IF73_tree);
                    NOT74 = (Token)this.match((IntStream)this.input, 34, FOLLOW_NOT_in_create_index436);
                    NOT74_tree = this.adaptor.create(NOT74);
                    this.adaptor.addChild(root_0, NOT74_tree);
                    EXISTS75 = (Token)this.match((IntStream)this.input, 24, FOLLOW_EXISTS_in_create_index438);
                    EXISTS75_tree = this.adaptor.create(EXISTS75);
                    this.adaptor.addChild(root_0, EXISTS75_tree);
                    this.isJudgeExists = true;
                }
            }
            this.pushFollow(FOLLOW_char_id_in_create_index443);
            char_id76 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id76.getTree());
            this.newCreateIndexStatement(this.currToken.getText(), this.isUniqueIdx, this.isJudgeExists);
            this.isUniqueIdx = false;
            ON77 = (Token)this.match((IntStream)this.input, 38, FOLLOW_ON_in_create_index448);
            ON77_tree = this.adaptor.create(ON77);
            this.adaptor.addChild(root_0, ON77_tree);
            this.pushFollow(FOLLOW_char_id_in_create_index450);
            char_id78 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id78.getTree());
            this.setTableNameForIndex(this.currToken.getText());
            LPAREN79 = (Token)this.match((IntStream)this.input, 30, FOLLOW_LPAREN_in_create_index454);
            LPAREN79_tree = this.adaptor.create(LPAREN79);
            this.adaptor.addChild(root_0, LPAREN79_tree);
            this.pushFollow(FOLLOW_char_id_in_create_index456);
            char_id80 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id80.getTree());
            this.addIndexColumn(this.currToken.getText());
            block13: while (true) {
                int alt10 = 2;
                int LA10_0 = this.input.LA(1);
                if (LA10_0 == 12) {
                    alt10 = 1;
                }
                switch (alt10) {
                    case 1: {
                        COMMA81 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_create_index463);
                        COMMA81_tree = this.adaptor.create(COMMA81);
                        this.adaptor.addChild(root_0, COMMA81_tree);
                        this.pushFollow(FOLLOW_char_id_in_create_index465);
                        char_id82 = this.char_id();
                        --this.state._fsp;
                        this.adaptor.addChild(root_0, char_id82.getTree());
                        this.addIndexColumn(this.currToken.getText());
                        continue block13;
                    }
                }
                break;
            }
            RPAREN83 = (Token)this.match((IntStream)this.input, 43, FOLLOW_RPAREN_in_create_index471);
            RPAREN83_tree = this.adaptor.create(RPAREN83);
            this.adaptor.addChild(root_0, RPAREN83_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final create_table_return create_table() throws RecognitionException {
        create_table_return retval = new create_table_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token r = null;
        Token CREATE84 = null;
        Token TABLE85 = null;
        Token IF86 = null;
        Token NOT87 = null;
        Token EXISTS88 = null;
        Token LPAREN90 = null;
        Token COMMA92 = null;
        Token COMMA94 = null;
        Token CONSTRAINT95 = null;
        Token PRIMARY97 = null;
        Token KEY98 = null;
        Token LPAREN99 = null;
        Token COMMA101 = null;
        Token RPAREN103 = null;
        Token COMMA104 = null;
        Token COMMENT105 = null;
        Token RPAREN106 = null;
        char_id_return char_id89 = null;
        column_define_return column_define91 = null;
        column_define_return column_define93 = null;
        char_id_return char_id96 = null;
        char_id_return char_id100 = null;
        char_id_return char_id102 = null;
        table_property_define_return table_property_define107 = null;
        Object r_tree = null;
        Object CREATE84_tree = null;
        Object TABLE85_tree = null;
        Object IF86_tree = null;
        Object NOT87_tree = null;
        Object EXISTS88_tree = null;
        Object LPAREN90_tree = null;
        Object COMMA92_tree = null;
        Object COMMA94_tree = null;
        Object CONSTRAINT95_tree = null;
        Object PRIMARY97_tree = null;
        Object KEY98_tree = null;
        Object LPAREN99_tree = null;
        Object COMMA101_tree = null;
        Object RPAREN103_tree = null;
        Object COMMA104_tree = null;
        Object COMMENT105_tree = null;
        Object RPAREN106_tree = null;
        try {
            int LA15_1;
            root_0 = this.adaptor.nil();
            CREATE84 = (Token)this.match((IntStream)this.input, 15, FOLLOW_CREATE_in_create_table486);
            CREATE84_tree = this.adaptor.create(CREATE84);
            this.adaptor.addChild(root_0, CREATE84_tree);
            TABLE85 = (Token)this.match((IntStream)this.input, 46, FOLLOW_TABLE_in_create_table488);
            TABLE85_tree = this.adaptor.create(TABLE85);
            this.adaptor.addChild(root_0, TABLE85_tree);
            int alt11 = 2;
            int LA11_0 = this.input.LA(1);
            if (LA11_0 == 26) {
                alt11 = 1;
            }
            switch (alt11) {
                case 1: {
                    IF86 = (Token)this.match((IntStream)this.input, 26, FOLLOW_IF_in_create_table491);
                    IF86_tree = this.adaptor.create(IF86);
                    this.adaptor.addChild(root_0, IF86_tree);
                    NOT87 = (Token)this.match((IntStream)this.input, 34, FOLLOW_NOT_in_create_table493);
                    NOT87_tree = this.adaptor.create(NOT87);
                    this.adaptor.addChild(root_0, NOT87_tree);
                    EXISTS88 = (Token)this.match((IntStream)this.input, 24, FOLLOW_EXISTS_in_create_table495);
                    EXISTS88_tree = this.adaptor.create(EXISTS88);
                    this.adaptor.addChild(root_0, EXISTS88_tree);
                    this.isJudgeExists = true;
                }
            }
            this.pushFollow(FOLLOW_char_id_in_create_table500);
            char_id89 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id89.getTree());
            this.newCreateTableStatement(this.currToken.getText(), this.isJudgeExists);
            this.isJudgeExists = false;
            LPAREN90 = (Token)this.match((IntStream)this.input, 30, FOLLOW_LPAREN_in_create_table505);
            LPAREN90_tree = this.adaptor.create(LPAREN90);
            this.adaptor.addChild(root_0, LPAREN90_tree);
            this.pushFollow(FOLLOW_column_define_in_create_table507);
            column_define91 = this.column_define();
            --this.state._fsp;
            this.adaptor.addChild(root_0, column_define91.getTree());
            this.addColumnForCreateTable();
            block25: while (true) {
                int LA12_1;
                int alt12 = 2;
                int LA12_0 = this.input.LA(1);
                if (LA12_0 == 12 && (LA12_1 = this.input.LA(2)) == 8) {
                    alt12 = 1;
                }
                switch (alt12) {
                    case 1: {
                        COMMA92 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_create_table513);
                        COMMA92_tree = this.adaptor.create(COMMA92);
                        this.adaptor.addChild(root_0, COMMA92_tree);
                        this.pushFollow(FOLLOW_column_define_in_create_table515);
                        column_define93 = this.column_define();
                        --this.state._fsp;
                        this.adaptor.addChild(root_0, column_define93.getTree());
                        this.addColumnForCreateTable();
                        continue block25;
                    }
                }
                break;
            }
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 12 && ((LA15_1 = this.input.LA(2)) == 14 || LA15_1 == 40)) {
                alt15 = 1;
            }
            switch (alt15) {
                case 1: {
                    COMMA94 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_create_table523);
                    COMMA94_tree = this.adaptor.create(COMMA94);
                    this.adaptor.addChild(root_0, COMMA94_tree);
                    int alt13 = 2;
                    int LA13_0 = this.input.LA(1);
                    if (LA13_0 == 14) {
                        alt13 = 1;
                    }
                    switch (alt13) {
                        case 1: {
                            CONSTRAINT95 = (Token)this.match((IntStream)this.input, 14, FOLLOW_CONSTRAINT_in_create_table526);
                            CONSTRAINT95_tree = this.adaptor.create(CONSTRAINT95);
                            this.adaptor.addChild(root_0, CONSTRAINT95_tree);
                            this.pushFollow(FOLLOW_char_id_in_create_table528);
                            char_id96 = this.char_id();
                            --this.state._fsp;
                            this.adaptor.addChild(root_0, char_id96.getTree());
                            this.addPkNameForCreateTable(this.currToken.getText());
                        }
                    }
                    PRIMARY97 = (Token)this.match((IntStream)this.input, 40, FOLLOW_PRIMARY_in_create_table534);
                    PRIMARY97_tree = this.adaptor.create(PRIMARY97);
                    this.adaptor.addChild(root_0, PRIMARY97_tree);
                    KEY98 = (Token)this.match((IntStream)this.input, 29, FOLLOW_KEY_in_create_table536);
                    KEY98_tree = this.adaptor.create(KEY98);
                    this.adaptor.addChild(root_0, KEY98_tree);
                    LPAREN99 = (Token)this.match((IntStream)this.input, 30, FOLLOW_LPAREN_in_create_table538);
                    LPAREN99_tree = this.adaptor.create(LPAREN99);
                    this.adaptor.addChild(root_0, LPAREN99_tree);
                    this.pushFollow(FOLLOW_char_id_in_create_table540);
                    char_id100 = this.char_id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, char_id100.getTree());
                    this.addPrimaryKeyForCreateTable(this.currToken.getText());
                    block26: while (true) {
                        int alt14 = 2;
                        int LA14_0 = this.input.LA(1);
                        if (LA14_0 == 12) {
                            alt14 = 1;
                        }
                        switch (alt14) {
                            case 1: {
                                COMMA101 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_create_table546);
                                COMMA101_tree = this.adaptor.create(COMMA101);
                                this.adaptor.addChild(root_0, COMMA101_tree);
                                this.pushFollow(FOLLOW_char_id_in_create_table548);
                                char_id102 = this.char_id();
                                --this.state._fsp;
                                this.adaptor.addChild(root_0, char_id102.getTree());
                                this.addPrimaryKeyForCreateTable(this.currToken.getText());
                                continue block26;
                            }
                        }
                        break;
                    }
                    RPAREN103 = (Token)this.match((IntStream)this.input, 43, FOLLOW_RPAREN_in_create_table555);
                    RPAREN103_tree = this.adaptor.create(RPAREN103);
                    this.adaptor.addChild(root_0, RPAREN103_tree);
                }
            }
            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 == 12) {
                alt16 = 1;
            }
            switch (alt16) {
                case 1: {
                    COMMA104 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_create_table569);
                    COMMA104_tree = this.adaptor.create(COMMA104);
                    this.adaptor.addChild(root_0, COMMA104_tree);
                    COMMENT105 = (Token)this.match((IntStream)this.input, 13, FOLLOW_COMMENT_in_create_table571);
                    COMMENT105_tree = this.adaptor.create(COMMENT105);
                    this.adaptor.addChild(root_0, COMMENT105_tree);
                    r = (Token)this.match((IntStream)this.input, 45, FOLLOW_STRING_in_create_table576);
                    r_tree = this.adaptor.create(r);
                    this.adaptor.addChild(root_0, r_tree);
                    this.addCommentForCreateTable(r.getText());
                }
            }
            RPAREN106 = (Token)this.match((IntStream)this.input, 43, FOLLOW_RPAREN_in_create_table583);
            RPAREN106_tree = this.adaptor.create(RPAREN106);
            this.adaptor.addChild(root_0, RPAREN106_tree);
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 8 || LA17_0 == 25 || LA17_0 == 28 || LA17_0 == 45) {
                alt17 = 1;
            }
            switch (alt17) {
                case 1: {
                    this.pushFollow(FOLLOW_table_property_define_in_create_table586);
                    table_property_define107 = this.table_property_define();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, table_property_define107.getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final table_property_define_return table_property_define() throws RecognitionException {
        table_property_define_return retval = new table_property_define_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token EQ109 = null;
        Token COMMA111 = null;
        Token EQ113 = null;
        default_val_return default_val108 = null;
        default_val_return default_val110 = null;
        default_val_return default_val112 = null;
        default_val_return default_val114 = null;
        Object EQ109_tree = null;
        Object COMMA111_tree = null;
        Object EQ113_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.pushFollow(FOLLOW_default_val_in_table_property_define601);
            default_val108 = this.default_val();
            --this.state._fsp;
            this.adaptor.addChild(root_0, default_val108.getTree());
            this.propKey = this.currToken.getText();
            EQ109 = (Token)this.match((IntStream)this.input, 22, FOLLOW_EQ_in_table_property_define604);
            EQ109_tree = this.adaptor.create(EQ109);
            this.adaptor.addChild(root_0, EQ109_tree);
            this.pushFollow(FOLLOW_default_val_in_table_property_define606);
            default_val110 = this.default_val();
            --this.state._fsp;
            this.adaptor.addChild(root_0, default_val110.getTree());
            this.addTableProperty(this.propKey, this.currToken.getText());
            block7: while (true) {
                int alt18 = 2;
                int LA18_0 = this.input.LA(1);
                if (LA18_0 == 12) {
                    alt18 = 1;
                }
                switch (alt18) {
                    case 1: {
                        COMMA111 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_table_property_define610);
                        COMMA111_tree = this.adaptor.create(COMMA111);
                        this.adaptor.addChild(root_0, COMMA111_tree);
                        this.pushFollow(FOLLOW_default_val_in_table_property_define612);
                        default_val112 = this.default_val();
                        --this.state._fsp;
                        this.adaptor.addChild(root_0, default_val112.getTree());
                        this.propKey = this.currToken.getText();
                        EQ113 = (Token)this.match((IntStream)this.input, 22, FOLLOW_EQ_in_table_property_define615);
                        EQ113_tree = this.adaptor.create(EQ113);
                        this.adaptor.addChild(root_0, EQ113_tree);
                        this.pushFollow(FOLLOW_default_val_in_table_property_define617);
                        default_val114 = this.default_val();
                        --this.state._fsp;
                        this.adaptor.addChild(root_0, default_val114.getTree());
                        this.addTableProperty(this.propKey, this.currToken.getText());
                        continue block7;
                    }
                }
                break;
            }
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final column_define_return column_define() throws RecognitionException {
        column_define_return retval = new column_define_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token r = null;
        Token LPAREN117 = null;
        Token COMMA118 = null;
        Token RPAREN119 = null;
        Token DEFAULT120 = null;
        Token NULL122 = null;
        Token NOT123 = null;
        Token NULL124 = null;
        Token COMMENT125 = null;
        char_id_return char_id115 = null;
        column_type_return column_type116 = null;
        default_val_return default_val121 = null;
        Object r_tree = null;
        Object LPAREN117_tree = null;
        Object COMMA118_tree = null;
        Object RPAREN119_tree = null;
        Object DEFAULT120_tree = null;
        Object NULL122_tree = null;
        Object NOT123_tree = null;
        Object NULL124_tree = null;
        Object COMMENT125_tree = null;
        try {
            root_0 = this.adaptor.nil();
            this.pushFollow(FOLLOW_char_id_in_column_define630);
            char_id115 = this.char_id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, char_id115.getTree());
            this.recordColumnInfo("COLNAME", this.currToken.getText());
            this.pushFollow(FOLLOW_column_type_in_column_define636);
            column_type116 = this.column_type();
            --this.state._fsp;
            this.adaptor.addChild(root_0, column_type116.getTree());
            this.recordColumnInfo("COLTYPE", this.currToken.getText());
            int alt20 = 2;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 30) {
                alt20 = 1;
            }
            switch (alt20) {
                case 1: {
                    LPAREN117 = (Token)this.match((IntStream)this.input, 30, FOLLOW_LPAREN_in_column_define643);
                    LPAREN117_tree = this.adaptor.create(LPAREN117);
                    this.adaptor.addChild(root_0, LPAREN117_tree);
                    r = this.input.LT(1);
                    if (this.input.LA(1) != 28 && this.input.LA(1) != 31) {
                        MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                        throw mse;
                    }
                    this.input.consume();
                    this.adaptor.addChild(root_0, this.adaptor.create(r));
                    this.state.errorRecovery = false;
                    this.recordColumnInfo("COLLEN", r.getText());
                    int alt19 = 2;
                    int LA19_0 = this.input.LA(1);
                    if (LA19_0 == 12) {
                        alt19 = 1;
                    }
                    switch (alt19) {
                        case 1: {
                            COMMA118 = (Token)this.match((IntStream)this.input, 12, FOLLOW_COMMA_in_column_define657);
                            COMMA118_tree = this.adaptor.create(COMMA118);
                            this.adaptor.addChild(root_0, COMMA118_tree);
                            r = (Token)this.match((IntStream)this.input, 28, FOLLOW_INT_in_column_define661);
                            r_tree = this.adaptor.create(r);
                            this.adaptor.addChild(root_0, r_tree);
                            this.recordColumnInfo("COLDECIMAL", r.getText());
                        }
                    }
                    RPAREN119 = (Token)this.match((IntStream)this.input, 43, FOLLOW_RPAREN_in_column_define666);
                    RPAREN119_tree = this.adaptor.create(RPAREN119);
                    this.adaptor.addChild(root_0, RPAREN119_tree);
                }
            }
            int alt21 = 2;
            int LA21_0 = this.input.LA(1);
            if (LA21_0 == 18) {
                alt21 = 1;
            }
            switch (alt21) {
                case 1: {
                    DEFAULT120 = (Token)this.match((IntStream)this.input, 18, FOLLOW_DEFAULT_in_column_define672);
                    DEFAULT120_tree = this.adaptor.create(DEFAULT120);
                    this.adaptor.addChild(root_0, DEFAULT120_tree);
                    this.pushFollow(FOLLOW_default_val_in_column_define674);
                    default_val121 = this.default_val();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, default_val121.getTree());
                    this.recordColumnInfo("COLDEFAULT", this.currToken.getText());
                }
            }
            int alt22 = 3;
            int LA22_0 = this.input.LA(1);
            if (LA22_0 == 35) {
                alt22 = 1;
            } else if (LA22_0 == 34) {
                alt22 = 2;
            }
            switch (alt22) {
                case 1: {
                    NULL122 = (Token)this.match((IntStream)this.input, 35, FOLLOW_NULL_in_column_define683);
                    NULL122_tree = this.adaptor.create(NULL122);
                    this.adaptor.addChild(root_0, NULL122_tree);
                    this.recordColumnInfo("COLNULL", "true");
                    break;
                }
                case 2: {
                    NOT123 = (Token)this.match((IntStream)this.input, 34, FOLLOW_NOT_in_column_define691);
                    NOT123_tree = this.adaptor.create(NOT123);
                    this.adaptor.addChild(root_0, NOT123_tree);
                    NULL124 = (Token)this.match((IntStream)this.input, 35, FOLLOW_NULL_in_column_define693);
                    NULL124_tree = this.adaptor.create(NULL124);
                    this.adaptor.addChild(root_0, NULL124_tree);
                    this.recordColumnInfo("COLNULL", "false");
                }
            }
            int alt23 = 2;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 == 13) {
                alt23 = 1;
            }
            switch (alt23) {
                case 1: {
                    COMMENT125 = (Token)this.match((IntStream)this.input, 13, FOLLOW_COMMENT_in_column_define709);
                    COMMENT125_tree = this.adaptor.create(COMMENT125);
                    this.adaptor.addChild(root_0, COMMENT125_tree);
                    r = (Token)this.match((IntStream)this.input, 45, FOLLOW_STRING_in_column_define714);
                    r_tree = this.adaptor.create(r);
                    this.adaptor.addChild(root_0, r_tree);
                    this.recordColumnInfo("comment", r.getText());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final column_type_return column_type() throws RecognitionException {
        column_type_return retval = new column_type_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token r = null;
        Object r_tree = null;
        try {
            root_0 = this.adaptor.nil();
            r = this.input.LT(1);
            if (!(this.input.LA(1) == 6 || this.input.LA(1) == 10 || this.input.LA(1) == 8 || this.input.LA(1) >= 16 && this.input.LA(1) <= 17 || this.input.LA(1) >= 36 && this.input.LA(1) <= 37 || this.input.LA(1) == 41 || this.input.LA(1) == 47 || this.input.LA(1) == 50)) {
                MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                throw mse;
            }
            this.input.consume();
            this.adaptor.addChild(root_0, this.adaptor.create(r));
            this.state.errorRecovery = false;
            this.currToken = r;
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final char_id_return char_id() throws RecognitionException {
        char_id_return retval = new char_id_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token r = null;
        Object r_tree = null;
        try {
            root_0 = this.adaptor.nil();
            r = (Token)this.match((IntStream)this.input, 8, FOLLOW_CHAR_ID_in_char_id786);
            r_tree = this.adaptor.create(r);
            this.adaptor.addChild(root_0, r_tree);
            this.currToken = r;
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final default_val_return default_val() throws RecognitionException {
        default_val_return retval = new default_val_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Token r = null;
        Object r_tree = null;
        try {
            root_0 = this.adaptor.nil();
            r = this.input.LT(1);
            if (this.input.LA(1) != 8 && this.input.LA(1) != 25 && this.input.LA(1) != 28 && this.input.LA(1) != 45) {
                MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                throw mse;
            }
            this.input.consume();
            this.adaptor.addChild(root_0, this.adaptor.create(r));
            this.state.errorRecovery = false;
            this.currToken = r;
            retval.stop = this.input.LT(-1);
            retval.tree = this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover((IntStream)this.input, re);
            retval.tree = this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    public static class default_val_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class char_id_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class column_type_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class column_define_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class table_property_define_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class create_table_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class create_index_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class drop_constraint_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class add_constraint_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class rename_column_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class drop_column_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class addmodify_column_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class alter_table_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class drop_table_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class rename_index_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class drop_index_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class rename_table_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class expression_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }

    public static class evaluate_return
    extends ParserRuleReturnScope {
        Object tree;

        public Object getTree() {
            return this.tree;
        }
    }
}

