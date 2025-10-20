/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.antlr.runtime.BaseRecognizer
 *  org.antlr.runtime.CharStream
 *  org.antlr.runtime.DFA
 *  org.antlr.runtime.EarlyExitException
 *  org.antlr.runtime.IntStream
 *  org.antlr.runtime.Lexer
 *  org.antlr.runtime.MismatchedSetException
 *  org.antlr.runtime.NoViableAltException
 *  org.antlr.runtime.RecognitionException
 *  org.antlr.runtime.RecognizerSharedState
 */
package com.jiuqi.bi.database.sql.parser.antlr;

import com.jiuqi.bi.database.sql.parser.antlr.DDLLexer;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class ANTLRLexer
extends DDLLexer {
    public static final int EOF = -1;
    public static final int ADD = 4;
    public static final int ALTER = 5;
    public static final int BLOB = 6;
    public static final int CHAR = 7;
    public static final int CHAR_ID = 8;
    public static final int CLASS = 9;
    public static final int CLOB = 10;
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
    protected DFA45 dfa45 = new DFA45((BaseRecognizer)this);
    static final String DFA45_eotS = "\u0001\uffff\u001e\u001f\u0002\uffff\u0001a\u0001b\u0005\uffff\u0001d\u0001\uffff\u0007\u001f\u0001r\u0002\u001f\u0001r\u0002\u001f\u0001w\u0001\u001f\u0001w\u0010\u001f\u0002\u008b\u0014\u001f\u0003\uffff\u0001\u00a1\u0001\uffff\r\u001f\u0001\uffff\u0004\u001f\u0001\uffff\u0003\u001f\u0002\u00b6\u0001\u00b7\u0003\u001f\u0001\u00b7\t\u001f\u0001\uffff\u0001\u001f\u0001\u00c7\u0001\u001f\u0001\u00c7\u0001\u001f\u0001\u00ca\u0001\u001f\u0001\u00ca\u0001\u001f\u0001\u00cd\u0001\u001f\u0001\u00cd\b\u001f\u0001`\u0001\uffff\u0005\u001f\u0001\u00de\u0005\u001f\u0001\u00de\b\u001f\u0002\uffff\u0001\u00ec\u0002\u001f\u0001\u00ec\u0004\u001f\u0001\u00f3\u0001\u00f5\u0002\u001f\u0001\u00f3\u0001\u00f5\u0001\u001f\u0001\uffff\u0002\u001f\u0001\uffff\u0002\u001f\u0001\uffff\u0005\u001f\u0002\u0103\u0002\u001f\u0002\u00a1\u0004\u001f\u0001\u010e\u0001\uffff\u0004\u001f\u0001\u010e\u0001\u0113\u0001\u001f\u0001\u0113\u0001\u001f\u0002\u0116\u0002\u001f\u0001\uffff\u0006\u001f\u0001\uffff\u0001\u001f\u0001\uffff\u0003\u001f\u0002\u0123\b\u001f\u0001\uffff\u0002\u001f\u0001\u00a1\u0001`\u0001\u00a1\u0001`\u0001\u012f\u0002\u001f\u0001\u0132\u0001\uffff\u0001\u012f\u0002\u001f\u0001\u0132\u0001\uffff\u0002\u001f\u0001\uffff\u0002\u001f\u0001\u0139\u0001\u001f\u0001\u0139\u0002\u001f\u0001\u013d\u0002\u001f\u0001\u013d\u0001\u001f\u0001\uffff\u0002\u0141\u0002\u0142\u0002\u0143\u0002\u001f\u0002\u0146\u0001\u00a1\u0001\uffff\u0001\u001f\u0001\u0148\u0001\uffff\u0001\u001f\u0001\u0148\u0002\u001f\u0002\u014c\u0001\uffff\u0002\u001f\u0001\u014f\u0001\uffff\u0001\u001f\u0001\u014f\u0001\u001f\u0003\uffff\u0002\u0152\u0001\uffff\u0001\u001f\u0001\uffff\u0003\u001f\u0001\uffff\u0002\u0157\u0001\uffff\u0002\u0158\u0001\uffff\u0002\u001f\u0002\u015b\u0002\uffff\u0002\u015c\u0002\uffff";
    static final String DFA45_eofS = "\u015d\uffff";
    static final String DFA45_minS = "\u0001\t\u0001L\u0001l\u0001A\u0001a\u0001F\u0001f\u0001R\u0001r\u0001E\u0001e\u0001O\u0001o\u0001A\u0001a\u0001N\u0001n\u0001D\u0001d\u0001A\u0001a\u0001A\u0001a\u0001N\u0001n\u0001A\u0001a\u0001L\u0001l\u0001X\u0001x\u0002\uffff\u0001+\u0001.\u0005\uffff\u0001+\u0001\uffff\u0001E\u0001L\u0001A\u0001e\u0001l\u0001a\u0001B\u00010\u0001M\u0001b\u00010\u0001m\u0001D\u00010\u0001d\u00010\u0001I\u0001i\u0001Y\u0001y\u0001T\u0001L\u0001A\u0001t\u0001l\u0001a\u0001F\u0001O\u0001T\u0001f\u0001o\u0001t\u00020\u0001T\u0001D\u0001t\u0001d\u0001D\u0001X\u0001d\u0001x\u0001N\u0001W\u0001n\u0001w\u0001I\u0001i\u0001R\u0001r\u0001O\u0001o\u0001I\u0001i\u0003\uffff\u00010\u0001\uffff\u0001A\u0001S\u0001M\u0001U\u0001S\u0001B\u0001a\u0001s\u0001m\u0001u\u0001s\u0001b\u0001L\u0001\uffff\u0001E\u0001l\u0001e\u0001E\u0001\uffff\u0001e\u0001M\u0001m\u00030\u0001L\u0001B\u0001R\u00010\u0001l\u0001b\u0001r\u0001A\u0001P\u0001E\u0001a\u0001p\u0001e\u0001\uffff\u0001E\u00010\u0001e\u00010\u0001I\u00010\u0001i\u00010\u0001A\u00010\u0001a\u00010\u0001Q\u0001q\u0001C\u0001c\u0001B\u0001b\u0001S\u0001s\u0001\u0000\u0001\uffff\u0002T\u0001E\u0001M\u0001S\u00010\u0002t\u0001e\u0001m\u0001s\u00010\u0001E\u0001S\u0001e\u0001s\u0001X\u0001x\u0001A\u0001a\u0002\uffff\u00010\u0001E\u0001C\u00010\u0001e\u0001c\u0001U\u0001N\u00020\u0001u\u0001n\u00020\u0001R\u0001\uffff\u0001r\u0001F\u0001\uffff\u0001f\u0001M\u0001\uffff\u0001m\u0001U\u0001u\u0001H\u0001h\u00020\u0001T\u0001t\u0002+\u0001E\u0001R\u0002N\u00010\u0001\uffff\u0001e\u0001r\u0002n\u00020\u0001T\u00010\u0001t\u00020\u0001R\u0001r\u0001\uffff\u0001R\u0001H\u0001r\u0001h\u0001L\u0001E\u0001\uffff\u0001I\u0001\uffff\u0001l\u0001e\u0001i\u00020\u0001Y\u0001y\u0001E\u0001e\u0001E\u0001e\u0001A\u0001a\u0001\uffff\u0001S\u0001s\u00010\u0001\u0000\u0001+\u0001\u0000\u00010\u0001A\u0001T\u00010\u0001\uffff\u00010\u0001a\u0001t\u00010\u0001\uffff\u0001A\u0001a\u0001\uffff\u0001Y\u0001y\u00010\u0001A\u00010\u0001a\u0001T\u00010\u0001M\u0001t\u00010\u0001m\u0001\uffff\u00060\u0001R\u0001r\u00030\u0001\uffff\u0001I\u00010\u0001\uffff\u0001i\u00010\u0001M\u0001m\u00020\u0001\uffff\u0001R\u0001r\u00010\u0001\uffff\u0001E\u00010\u0001e\u0003\uffff\u00020\u0001\uffff\u0001N\u0001\uffff\u0001n\u0001P\u0001p\u0001\uffff\u00020\u0001\uffff\u00020\u0001\uffff\u0001T\u0001t\u00020\u0002\uffff\u00020\u0002\uffff";
    static final String DFA45_maxS = "\u0001z\u0001R\u0001r\u0001O\u0001o\u0001N\u0001n\u0001R\u0001r\u0001E\u0001e\u0001V\u0001v\u0001R\u0001r\u0001N\u0001n\u0001L\u0001l\u0001O\u0001o\u0001E\u0001e\u0001N\u0001n\u0001A\u0001a\u0001L\u0001l\u0001X\u0001x\u0002\uffff\u00019\u0001e\u0005\uffff\u00019\u0001\uffff\u0001E\u0001N\u0001O\u0001e\u0001n\u0001o\u0001B\u0001z\u0001M\u0001b\u0001z\u0001m\u0001D\u0001z\u0001d\u0001z\u0001I\u0001i\u0001Y\u0001y\u0001T\u0001M\u0001A\u0001t\u0001m\u0001a\u0001F\u0001O\u0001T\u0001f\u0001o\u0001t\u0002z\u0001T\u0001D\u0001t\u0001d\u0001D\u0001X\u0001d\u0001x\u0001N\u0001W\u0001n\u0001w\u0001I\u0001i\u0001R\u0001r\u0001O\u0001o\u0001I\u0001i\u0003\uffff\u00019\u0001\uffff\u0001A\u0001S\u0001M\u0001U\u0001S\u0001B\u0001a\u0001s\u0001m\u0001u\u0001s\u0001b\u0001L\u0001\uffff\u0001E\u0001l\u0001e\u0001E\u0001\uffff\u0001e\u0001M\u0001m\u0003z\u0001L\u0001B\u0001R\u0001z\u0001l\u0001b\u0001r\u0001I\u0001P\u0001E\u0001i\u0001p\u0001e\u0001\uffff\u0001E\u0001z\u0001e\u0001z\u0001I\u0001z\u0001i\u0001z\u0001A\u0001z\u0001a\u0001z\u0001Q\u0001q\u0001C\u0001c\u0001B\u0001b\u0001S\u0001s\u0001\uffff\u0001\uffff\u0002T\u0001E\u0001M\u0001S\u0001z\u0002t\u0001e\u0001m\u0001s\u0001z\u0001E\u0001S\u0001e\u0001s\u0001X\u0001x\u0001A\u0001a\u0002\uffff\u0001z\u0001E\u0001C\u0001z\u0001e\u0001c\u0001U\u0001N\u0002z\u0001u\u0001n\u0002z\u0001R\u0001\uffff\u0001r\u0001F\u0001\uffff\u0001f\u0001M\u0001\uffff\u0001m\u0001U\u0001u\u0001H\u0001h\u0002z\u0001T\u0001t\u00029\u0001E\u0001R\u0002N\u0001z\u0001\uffff\u0001e\u0001r\u0002n\u0002z\u0001T\u0001z\u0001t\u0002z\u0001R\u0001r\u0001\uffff\u0001R\u0001H\u0001r\u0001h\u0001L\u0001E\u0001\uffff\u0001I\u0001\uffff\u0001l\u0001e\u0001i\u0002z\u0001Y\u0001y\u0001E\u0001e\u0001E\u0001e\u0001A\u0001a\u0001\uffff\u0001S\u0001s\u00019\u0001\uffff\u00019\u0001\uffff\u0001z\u0001A\u0001T\u0001z\u0001\uffff\u0001z\u0001a\u0001t\u0001z\u0001\uffff\u0001A\u0001a\u0001\uffff\u0001Y\u0001y\u0001z\u0001A\u0001z\u0001a\u0001T\u0001z\u0001M\u0001t\u0001z\u0001m\u0001\uffff\u0006z\u0001R\u0001r\u0002z\u00019\u0001\uffff\u0001I\u0001z\u0001\uffff\u0001i\u0001z\u0001M\u0001m\u0002z\u0001\uffff\u0001R\u0001r\u0001z\u0001\uffff\u0001E\u0001z\u0001e\u0003\uffff\u0002z\u0001\uffff\u0001N\u0001\uffff\u0001n\u0001P\u0001p\u0001\uffff\u0002z\u0001\uffff\u0002z\u0001\uffff\u0001T\u0001t\u0002z\u0002\uffff\u0002z\u0002\uffff";
    static final String DFA45_acceptS = "\u001f\uffff\u0001\"\u0001#\u0002\uffff\u0001&\u0001'\u0001(\u0001)\u0001*\u0001\uffff\u0001-6\uffff\u0001%\u0001+\u0001$\u0001\uffff\u0001,\r\uffff\u0001\u0012\u0004\uffff\u0001 \u0013\uffff\u0001\u000b\u0015\uffff\u0001.\u0014\uffff\u0001\u0007\u0001\b\u000f\uffff\u0001\r\u0002\uffff\u0001\u001f\u0002\uffff\u0001\u001d\u0010\uffff\u0001\u001e\r\uffff\u0001\t\u0006\uffff\u0001\u000f\u0001\uffff\u0001\u0019\r\uffff\u0001\u001a\n\uffff\u0001\u0014\u0004\uffff\u0001\u0002\u0002\uffff\u0001\u0003\f\uffff\u0001\f\u000b\uffff\u0001\u0001\u0002\uffff\u0001\u0010\u0006\uffff\u0001\u0018\u0003\uffff\u0001\u0013\u0003\uffff\u0001\u000e\u0001\u0011\u0001\u0015\u0002\uffff\u0001!\u0001\uffff\u0001\u0006\u0003\uffff\u0001\u0005\u0002\uffff\u0001\n\u0002\uffff\u0001\u0016\u0004\uffff\u0001\u0017\u0001\u001b\u0002\uffff\u0001\u001c\u0001\u0004";
    static final String DFA45_specialS = "\u00a0\uffff\u0001\u0002f\uffff\u0001\u0001\u0001\uffff\u0001\u0000S\uffff}>";
    static final String[] DFA45_transitionS = new String[]{"\u0002$\u0002\uffff\u0001$\u0012\uffff\u0001$\u0006\uffff\u0001 \u0001%\u0001&\u0001\uffff\u0001!\u0001'\u0001(\u0002\uffff\n\"\u0003\uffff\u0001)\u0003\uffff\u0001\u0011\u0001\u001b\u0001\u0001\u0001\r\u0001\u001d\u0003\u001f\u0001\u0005\u0001\u001f\u0001\t\u0001\u001f\u0001\u0013\u0001\u000b\u0001\u000f\u0001\u0007\u0001\u001f\u0001\u0015\u0001\u001f\u0001\u0003\u0001\u0017\u0001\u0019\u0004\u001f\u0001\uffff\u0001#\u0002\uffff\u0001\u001f\u0001\uffff\u0001\u0012\u0001\u001c\u0001\u0002\u0001\u000e\u0001\u001e\u0003\u001f\u0001\u0006\u0001\u001f\u0001\n\u0001\u001f\u0001\u0014\u0001\f\u0001\u0010\u0001\b\u0001\u001f\u0001\u0016\u0001\u001f\u0001\u0004\u0001\u0018\u0001\u001a\u0004\u001f", "\u0001,\u0002\uffff\u0001+\u0002\uffff\u0001*", "\u0001/\u0002\uffff\u0001.\u0002\uffff\u0001-", "\u00010\u0007\uffff\u00012\u0005\uffff\u00011", "\u00013\u0007\uffff\u00015\u0005\uffff\u00014", "\u00017\u0007\uffff\u00016", "\u00019\u0007\uffff\u00018", "\u0001:", "\u0001;", "\u0001<", "\u0001=", "\u0001>\u0005\uffff\u0001?\u0001@", "\u0001A\u0005\uffff\u0001B\u0001C", "\u0001F\u0003\uffff\u0001D\f\uffff\u0001E", "\u0001I\u0003\uffff\u0001G\f\uffff\u0001H", "\u0001J", "\u0001K", "\u0001M\u0007\uffff\u0001L", "\u0001O\u0007\uffff\u0001N", "\u0001Q\r\uffff\u0001P", "\u0001S\r\uffff\u0001R", "\u0001U\u0003\uffff\u0001T", "\u0001W\u0003\uffff\u0001V", "\u0001X", "\u0001Y", "\u0001Z", "\u0001[", "\u0001\\", "\u0001]", "\u0001^", "\u0001_", "", "", "\u0001`\u0001\uffff\u0001`\u0002\uffff\n\"", "\u0001`\u0001\uffff\n\"\u000b\uffff\u0001`\u001f\uffff\u0001`", "", "", "", "", "", "\u0001`\u0001\uffff\u0001c\u0002\uffff\n\"", "", "\u0001e", "\u0001h\u0001g\u0001f", "\u0001i\r\uffff\u0001j", "\u0001k", "\u0001n\u0001m\u0001l", "\u0001o\r\uffff\u0001p", "\u0001q", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001s", "\u0001t", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001u", "\u0001v", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001x", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001y", "\u0001z", "\u0001{", "\u0001|", "\u0001}", "\u0001~\u0001\u007f", "\u0001\u0080", "\u0001\u0081", "\u0001\u0082\u0001\u0083", "\u0001\u0084", "\u0001\u0085", "\u0001\u0086", "\u0001\u0087", "\u0001\u0088", "\u0001\u0089", "\u0001\u008a", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u008c", "\u0001\u008d", "\u0001\u008e", "\u0001\u008f", "\u0001\u0090", "\u0001\u0091", "\u0001\u0092", "\u0001\u0093", "\u0001\u0094", "\u0001\u0095", "\u0001\u0096", "\u0001\u0097", "\u0001\u0098", "\u0001\u0099", "\u0001\u009a", "\u0001\u009b", "\u0001\u009c", "\u0001\u009d", "\u0001\u009e", "\u0001\u009f", "", "", "", "\n\u00a0", "", "\u0001\u00a2", "\u0001\u00a3", "\u0001\u00a4", "\u0001\u00a5", "\u0001\u00a6", "\u0001\u00a7", "\u0001\u00a8", "\u0001\u00a9", "\u0001\u00aa", "\u0001\u00ab", "\u0001\u00ac", "\u0001\u00ad", "\u0001\u00ae", "", "\u0001\u00af", "\u0001\u00b0", "\u0001\u00b1", "\u0001\u00b2", "", "\u0001\u00b3", "\u0001\u00b4", "\u0001\u00b5", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00b8", "\u0001\u00b9", "\u0001\u00ba", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00bb", "\u0001\u00bc", "\u0001\u00bd", "\u0001\u00be\u0007\uffff\u0001\u00bf", "\u0001\u00c0", "\u0001\u00c1", "\u0001\u00c2\u0007\uffff\u0001\u00c3", "\u0001\u00c4", "\u0001\u00c5", "", "\u0001\u00c6", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00c8", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00c9", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00cb", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00cc", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00ce", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00cf", "\u0001\u00d0", "\u0001\u00d1", "\u0001\u00d2", "\u0001\u00d3", "\u0001\u00d4", "\u0001\u00d5", "\u0001\u00d6", ".\u00a1\u0001\u00d7\u0001\u00a1\n\u00a0\u000b\u00a1\u0001\u00d8\u001f\u00a1\u0001\u00d8\uff9a\u00a1", "", "\u0001\u00d9", "\u0001\u00da", "\u0001\u00db", "\u0001\u00dc", "\u0001\u00dd", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00df", "\u0001\u00e0", "\u0001\u00e1", "\u0001\u00e2", "\u0001\u00e3", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00e4", "\u0001\u00e5", "\u0001\u00e6", "\u0001\u00e7", "\u0001\u00e8", "\u0001\u00e9", "\u0001\u00ea", "\u0001\u00eb", "", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00ed", "\u0001\u00ee", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00ef", "\u0001\u00f0", "\u0001\u00f1", "\u0001\u00f2", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u0013\u001f\u0001\u00f4\u0006\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u00f6", "\u0001\u00f7", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u0013\u001f\u0001\u00f8\u0006\u001f", "\u0001\u00f9", "", "\u0001\u00fa", "\u0001\u00fb", "", "\u0001\u00fc", "\u0001\u00fd", "", "\u0001\u00fe", "\u0001\u00ff", "\u0001\u0100", "\u0001\u0101", "\u0001\u0102", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0104", "\u0001\u0105", "\u0001\u0106\u0001\uffff\u0001\u0106\u0002\uffff\n\u0107", "\u0001\u0108\u0001\uffff\u0001\u0108\u0002\uffff\n\u0109", "\u0001\u010a", "\u0001\u010b", "\u0001\u010c", "\u0001\u010d", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u010f", "\u0001\u0110", "\u0001\u0111", "\u0001\u0112", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0114", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0115", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0117", "\u0001\u0118", "", "\u0001\u0119", "\u0001\u011a", "\u0001\u011b", "\u0001\u011c", "\u0001\u011d", "\u0001\u011e", "", "\u0001\u011f", "", "\u0001\u0120", "\u0001\u0121", "\u0001\u0122", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0124", "\u0001\u0125", "\u0001\u0126", "\u0001\u0127", "\u0001\u0128", "\u0001\u0129", "\u0001\u012a", "\u0001\u012b", "", "\u0001\u012c", "\u0001\u012d", "\n\u0107", "0\u00a1\n\u0107\u000b\u00a1\u0001\u00d8\u001f\u00a1\u0001\u00d8\uff9a\u00a1", "\u0001\u012e\u0001\uffff\u0001\u012e\u0002\uffff\n\u0109", "0\u00a1\n\u0109\uffc6\u00a1", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0130", "\u0001\u0131", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0133", "\u0001\u0134", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u0135", "\u0001\u0136", "", "\u0001\u0137", "\u0001\u0138", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u013a", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u013b", "\u0001\u013c", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u013e", "\u0001\u013f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0140", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0144", "\u0001\u0145", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u0109", "", "\u0001\u0147", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u0149", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u014a", "\u0001\u014b", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u014d", "\u0001\u014e", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u0150", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\u0001\u0151", "", "", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u0153", "", "\u0001\u0154", "\u0001\u0155", "\u0001\u0156", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "\u0001\u0159", "\u0001\u015a", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", "", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "\n\u001f\u0007\uffff\u001a\u001f\u0004\uffff\u0001\u001f\u0001\uffff\u001a\u001f", "", ""};
    static final short[] DFA45_eot = DFA.unpackEncodedString((String)"\u0001\uffff\u001e\u001f\u0002\uffff\u0001a\u0001b\u0005\uffff\u0001d\u0001\uffff\u0007\u001f\u0001r\u0002\u001f\u0001r\u0002\u001f\u0001w\u0001\u001f\u0001w\u0010\u001f\u0002\u008b\u0014\u001f\u0003\uffff\u0001\u00a1\u0001\uffff\r\u001f\u0001\uffff\u0004\u001f\u0001\uffff\u0003\u001f\u0002\u00b6\u0001\u00b7\u0003\u001f\u0001\u00b7\t\u001f\u0001\uffff\u0001\u001f\u0001\u00c7\u0001\u001f\u0001\u00c7\u0001\u001f\u0001\u00ca\u0001\u001f\u0001\u00ca\u0001\u001f\u0001\u00cd\u0001\u001f\u0001\u00cd\b\u001f\u0001`\u0001\uffff\u0005\u001f\u0001\u00de\u0005\u001f\u0001\u00de\b\u001f\u0002\uffff\u0001\u00ec\u0002\u001f\u0001\u00ec\u0004\u001f\u0001\u00f3\u0001\u00f5\u0002\u001f\u0001\u00f3\u0001\u00f5\u0001\u001f\u0001\uffff\u0002\u001f\u0001\uffff\u0002\u001f\u0001\uffff\u0005\u001f\u0002\u0103\u0002\u001f\u0002\u00a1\u0004\u001f\u0001\u010e\u0001\uffff\u0004\u001f\u0001\u010e\u0001\u0113\u0001\u001f\u0001\u0113\u0001\u001f\u0002\u0116\u0002\u001f\u0001\uffff\u0006\u001f\u0001\uffff\u0001\u001f\u0001\uffff\u0003\u001f\u0002\u0123\b\u001f\u0001\uffff\u0002\u001f\u0001\u00a1\u0001`\u0001\u00a1\u0001`\u0001\u012f\u0002\u001f\u0001\u0132\u0001\uffff\u0001\u012f\u0002\u001f\u0001\u0132\u0001\uffff\u0002\u001f\u0001\uffff\u0002\u001f\u0001\u0139\u0001\u001f\u0001\u0139\u0002\u001f\u0001\u013d\u0002\u001f\u0001\u013d\u0001\u001f\u0001\uffff\u0002\u0141\u0002\u0142\u0002\u0143\u0002\u001f\u0002\u0146\u0001\u00a1\u0001\uffff\u0001\u001f\u0001\u0148\u0001\uffff\u0001\u001f\u0001\u0148\u0002\u001f\u0002\u014c\u0001\uffff\u0002\u001f\u0001\u014f\u0001\uffff\u0001\u001f\u0001\u014f\u0001\u001f\u0003\uffff\u0002\u0152\u0001\uffff\u0001\u001f\u0001\uffff\u0003\u001f\u0001\uffff\u0002\u0157\u0001\uffff\u0002\u0158\u0001\uffff\u0002\u001f\u0002\u015b\u0002\uffff\u0002\u015c\u0002\uffff");
    static final short[] DFA45_eof = DFA.unpackEncodedString((String)"\u015d\uffff");
    static final char[] DFA45_min = DFA.unpackEncodedStringToUnsignedChars((String)"\u0001\t\u0001L\u0001l\u0001A\u0001a\u0001F\u0001f\u0001R\u0001r\u0001E\u0001e\u0001O\u0001o\u0001A\u0001a\u0001N\u0001n\u0001D\u0001d\u0001A\u0001a\u0001A\u0001a\u0001N\u0001n\u0001A\u0001a\u0001L\u0001l\u0001X\u0001x\u0002\uffff\u0001+\u0001.\u0005\uffff\u0001+\u0001\uffff\u0001E\u0001L\u0001A\u0001e\u0001l\u0001a\u0001B\u00010\u0001M\u0001b\u00010\u0001m\u0001D\u00010\u0001d\u00010\u0001I\u0001i\u0001Y\u0001y\u0001T\u0001L\u0001A\u0001t\u0001l\u0001a\u0001F\u0001O\u0001T\u0001f\u0001o\u0001t\u00020\u0001T\u0001D\u0001t\u0001d\u0001D\u0001X\u0001d\u0001x\u0001N\u0001W\u0001n\u0001w\u0001I\u0001i\u0001R\u0001r\u0001O\u0001o\u0001I\u0001i\u0003\uffff\u00010\u0001\uffff\u0001A\u0001S\u0001M\u0001U\u0001S\u0001B\u0001a\u0001s\u0001m\u0001u\u0001s\u0001b\u0001L\u0001\uffff\u0001E\u0001l\u0001e\u0001E\u0001\uffff\u0001e\u0001M\u0001m\u00030\u0001L\u0001B\u0001R\u00010\u0001l\u0001b\u0001r\u0001A\u0001P\u0001E\u0001a\u0001p\u0001e\u0001\uffff\u0001E\u00010\u0001e\u00010\u0001I\u00010\u0001i\u00010\u0001A\u00010\u0001a\u00010\u0001Q\u0001q\u0001C\u0001c\u0001B\u0001b\u0001S\u0001s\u0001\u0000\u0001\uffff\u0002T\u0001E\u0001M\u0001S\u00010\u0002t\u0001e\u0001m\u0001s\u00010\u0001E\u0001S\u0001e\u0001s\u0001X\u0001x\u0001A\u0001a\u0002\uffff\u00010\u0001E\u0001C\u00010\u0001e\u0001c\u0001U\u0001N\u00020\u0001u\u0001n\u00020\u0001R\u0001\uffff\u0001r\u0001F\u0001\uffff\u0001f\u0001M\u0001\uffff\u0001m\u0001U\u0001u\u0001H\u0001h\u00020\u0001T\u0001t\u0002+\u0001E\u0001R\u0002N\u00010\u0001\uffff\u0001e\u0001r\u0002n\u00020\u0001T\u00010\u0001t\u00020\u0001R\u0001r\u0001\uffff\u0001R\u0001H\u0001r\u0001h\u0001L\u0001E\u0001\uffff\u0001I\u0001\uffff\u0001l\u0001e\u0001i\u00020\u0001Y\u0001y\u0001E\u0001e\u0001E\u0001e\u0001A\u0001a\u0001\uffff\u0001S\u0001s\u00010\u0001\u0000\u0001+\u0001\u0000\u00010\u0001A\u0001T\u00010\u0001\uffff\u00010\u0001a\u0001t\u00010\u0001\uffff\u0001A\u0001a\u0001\uffff\u0001Y\u0001y\u00010\u0001A\u00010\u0001a\u0001T\u00010\u0001M\u0001t\u00010\u0001m\u0001\uffff\u00060\u0001R\u0001r\u00030\u0001\uffff\u0001I\u00010\u0001\uffff\u0001i\u00010\u0001M\u0001m\u00020\u0001\uffff\u0001R\u0001r\u00010\u0001\uffff\u0001E\u00010\u0001e\u0003\uffff\u00020\u0001\uffff\u0001N\u0001\uffff\u0001n\u0001P\u0001p\u0001\uffff\u00020\u0001\uffff\u00020\u0001\uffff\u0001T\u0001t\u00020\u0002\uffff\u00020\u0002\uffff");
    static final char[] DFA45_max = DFA.unpackEncodedStringToUnsignedChars((String)"\u0001z\u0001R\u0001r\u0001O\u0001o\u0001N\u0001n\u0001R\u0001r\u0001E\u0001e\u0001V\u0001v\u0001R\u0001r\u0001N\u0001n\u0001L\u0001l\u0001O\u0001o\u0001E\u0001e\u0001N\u0001n\u0001A\u0001a\u0001L\u0001l\u0001X\u0001x\u0002\uffff\u00019\u0001e\u0005\uffff\u00019\u0001\uffff\u0001E\u0001N\u0001O\u0001e\u0001n\u0001o\u0001B\u0001z\u0001M\u0001b\u0001z\u0001m\u0001D\u0001z\u0001d\u0001z\u0001I\u0001i\u0001Y\u0001y\u0001T\u0001M\u0001A\u0001t\u0001m\u0001a\u0001F\u0001O\u0001T\u0001f\u0001o\u0001t\u0002z\u0001T\u0001D\u0001t\u0001d\u0001D\u0001X\u0001d\u0001x\u0001N\u0001W\u0001n\u0001w\u0001I\u0001i\u0001R\u0001r\u0001O\u0001o\u0001I\u0001i\u0003\uffff\u00019\u0001\uffff\u0001A\u0001S\u0001M\u0001U\u0001S\u0001B\u0001a\u0001s\u0001m\u0001u\u0001s\u0001b\u0001L\u0001\uffff\u0001E\u0001l\u0001e\u0001E\u0001\uffff\u0001e\u0001M\u0001m\u0003z\u0001L\u0001B\u0001R\u0001z\u0001l\u0001b\u0001r\u0001I\u0001P\u0001E\u0001i\u0001p\u0001e\u0001\uffff\u0001E\u0001z\u0001e\u0001z\u0001I\u0001z\u0001i\u0001z\u0001A\u0001z\u0001a\u0001z\u0001Q\u0001q\u0001C\u0001c\u0001B\u0001b\u0001S\u0001s\u0001\uffff\u0001\uffff\u0002T\u0001E\u0001M\u0001S\u0001z\u0002t\u0001e\u0001m\u0001s\u0001z\u0001E\u0001S\u0001e\u0001s\u0001X\u0001x\u0001A\u0001a\u0002\uffff\u0001z\u0001E\u0001C\u0001z\u0001e\u0001c\u0001U\u0001N\u0002z\u0001u\u0001n\u0002z\u0001R\u0001\uffff\u0001r\u0001F\u0001\uffff\u0001f\u0001M\u0001\uffff\u0001m\u0001U\u0001u\u0001H\u0001h\u0002z\u0001T\u0001t\u00029\u0001E\u0001R\u0002N\u0001z\u0001\uffff\u0001e\u0001r\u0002n\u0002z\u0001T\u0001z\u0001t\u0002z\u0001R\u0001r\u0001\uffff\u0001R\u0001H\u0001r\u0001h\u0001L\u0001E\u0001\uffff\u0001I\u0001\uffff\u0001l\u0001e\u0001i\u0002z\u0001Y\u0001y\u0001E\u0001e\u0001E\u0001e\u0001A\u0001a\u0001\uffff\u0001S\u0001s\u00019\u0001\uffff\u00019\u0001\uffff\u0001z\u0001A\u0001T\u0001z\u0001\uffff\u0001z\u0001a\u0001t\u0001z\u0001\uffff\u0001A\u0001a\u0001\uffff\u0001Y\u0001y\u0001z\u0001A\u0001z\u0001a\u0001T\u0001z\u0001M\u0001t\u0001z\u0001m\u0001\uffff\u0006z\u0001R\u0001r\u0002z\u00019\u0001\uffff\u0001I\u0001z\u0001\uffff\u0001i\u0001z\u0001M\u0001m\u0002z\u0001\uffff\u0001R\u0001r\u0001z\u0001\uffff\u0001E\u0001z\u0001e\u0003\uffff\u0002z\u0001\uffff\u0001N\u0001\uffff\u0001n\u0001P\u0001p\u0001\uffff\u0002z\u0001\uffff\u0002z\u0001\uffff\u0001T\u0001t\u0002z\u0002\uffff\u0002z\u0002\uffff");
    static final short[] DFA45_accept = DFA.unpackEncodedString((String)"\u001f\uffff\u0001\"\u0001#\u0002\uffff\u0001&\u0001'\u0001(\u0001)\u0001*\u0001\uffff\u0001-6\uffff\u0001%\u0001+\u0001$\u0001\uffff\u0001,\r\uffff\u0001\u0012\u0004\uffff\u0001 \u0013\uffff\u0001\u000b\u0015\uffff\u0001.\u0014\uffff\u0001\u0007\u0001\b\u000f\uffff\u0001\r\u0002\uffff\u0001\u001f\u0002\uffff\u0001\u001d\u0010\uffff\u0001\u001e\r\uffff\u0001\t\u0006\uffff\u0001\u000f\u0001\uffff\u0001\u0019\r\uffff\u0001\u001a\n\uffff\u0001\u0014\u0004\uffff\u0001\u0002\u0002\uffff\u0001\u0003\f\uffff\u0001\f\u000b\uffff\u0001\u0001\u0002\uffff\u0001\u0010\u0006\uffff\u0001\u0018\u0003\uffff\u0001\u0013\u0003\uffff\u0001\u000e\u0001\u0011\u0001\u0015\u0002\uffff\u0001!\u0001\uffff\u0001\u0006\u0003\uffff\u0001\u0005\u0002\uffff\u0001\n\u0002\uffff\u0001\u0016\u0004\uffff\u0001\u0017\u0001\u001b\u0002\uffff\u0001\u001c\u0001\u0004");
    static final short[] DFA45_special = DFA.unpackEncodedString((String)"\u00a0\uffff\u0001\u0002f\uffff\u0001\u0001\u0001\uffff\u0001\u0000S\uffff}>");
    static final short[][] DFA45_transition;

    public Lexer[] getDelegates() {
        return new Lexer[0];
    }

    public ANTLRLexer() {
    }

    public ANTLRLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public ANTLRLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String getGrammarFileName() {
        return "D:\\antlr4\\antlr(\ufffd\ufffd)\\SQL\\ANTLR.g";
    }

    public final void mCREATE() throws RecognitionException {
        int _type = 15;
        int _channel = 0;
        int alt1 = 2;
        int LA1_0 = this.input.LA(1);
        if (LA1_0 == 67) {
            alt1 = 1;
        } else if (LA1_0 == 99) {
            alt1 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 1, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt1) {
            case 1: {
                this.match("CREATE");
                break;
            }
            case 2: {
                this.match("create");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mTABLE() throws RecognitionException {
        int _type = 46;
        int _channel = 0;
        int alt2 = 2;
        int LA2_0 = this.input.LA(1);
        if (LA2_0 == 84) {
            alt2 = 1;
        } else if (LA2_0 == 116) {
            alt2 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 2, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt2) {
            case 1: {
                this.match("TABLE");
                break;
            }
            case 2: {
                this.match("table");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mINDEX() throws RecognitionException {
        int _type = 27;
        int _channel = 0;
        int alt3 = 2;
        int LA3_0 = this.input.LA(1);
        if (LA3_0 == 73) {
            alt3 = 1;
        } else if (LA3_0 == 105) {
            alt3 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 3, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt3) {
            case 1: {
                this.match("INDEX");
                break;
            }
            case 2: {
                this.match("index");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCONSTRAINT() throws RecognitionException {
        int _type = 14;
        int _channel = 0;
        int alt4 = 2;
        int LA4_0 = this.input.LA(1);
        if (LA4_0 == 67) {
            alt4 = 1;
        } else if (LA4_0 == 99) {
            alt4 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 4, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt4) {
            case 1: {
                this.match("CONSTRAINT");
                break;
            }
            case 2: {
                this.match("constraint");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mPRIMARY() throws RecognitionException {
        int _type = 40;
        int _channel = 0;
        int alt5 = 2;
        int LA5_0 = this.input.LA(1);
        if (LA5_0 == 80) {
            alt5 = 1;
        } else if (LA5_0 == 112) {
            alt5 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 5, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt5) {
            case 1: {
                this.match("PRIMARY");
                break;
            }
            case 2: {
                this.match("primary");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCOMMENT() throws RecognitionException {
        int _type = 13;
        int _channel = 0;
        int alt6 = 2;
        int LA6_0 = this.input.LA(1);
        if (LA6_0 == 67) {
            alt6 = 1;
        } else if (LA6_0 == 99) {
            alt6 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 6, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt6) {
            case 1: {
                this.match("COMMENT");
                break;
            }
            case 2: {
                this.match("comment");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mKEY() throws RecognitionException {
        int _type = 29;
        int _channel = 0;
        int alt7 = 2;
        int LA7_0 = this.input.LA(1);
        if (LA7_0 == 75) {
            alt7 = 1;
        } else if (LA7_0 == 107) {
            alt7 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 7, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt7) {
            case 1: {
                this.match("KEY");
                break;
            }
            case 2: {
                this.match("key");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNOT() throws RecognitionException {
        int _type = 34;
        int _channel = 0;
        int alt8 = 2;
        int LA8_0 = this.input.LA(1);
        if (LA8_0 == 78) {
            alt8 = 1;
        } else if (LA8_0 == 110) {
            alt8 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 8, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt8) {
            case 1: {
                this.match("NOT");
                break;
            }
            case 2: {
                this.match("not");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNULL() throws RecognitionException {
        int _type = 35;
        int _channel = 0;
        int alt9 = 2;
        int LA9_0 = this.input.LA(1);
        if (LA9_0 == 78) {
            alt9 = 1;
        } else if (LA9_0 == 110) {
            alt9 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 9, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt9) {
            case 1: {
                this.match("NULL");
                break;
            }
            case 2: {
                this.match("null");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDEFAULT() throws RecognitionException {
        int _type = 18;
        int _channel = 0;
        int alt10 = 2;
        int LA10_0 = this.input.LA(1);
        if (LA10_0 == 68) {
            alt10 = 1;
        } else if (LA10_0 == 100) {
            alt10 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 10, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt10) {
            case 1: {
                this.match("DEFAULT");
                break;
            }
            case 2: {
                this.match("default");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mON() throws RecognitionException {
        int _type = 38;
        int _channel = 0;
        int alt11 = 2;
        int LA11_0 = this.input.LA(1);
        if (LA11_0 == 79) {
            alt11 = 1;
        } else if (LA11_0 == 111) {
            alt11 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 11, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt11) {
            case 1: {
                this.match("ON");
                break;
            }
            case 2: {
                this.match("on");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mALTER() throws RecognitionException {
        int _type = 5;
        int _channel = 0;
        int alt12 = 2;
        int LA12_0 = this.input.LA(1);
        if (LA12_0 == 65) {
            alt12 = 1;
        } else if (LA12_0 == 97) {
            alt12 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 12, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt12) {
            case 1: {
                this.match("ALTER");
                break;
            }
            case 2: {
                this.match("alter");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mADD() throws RecognitionException {
        int _type = 4;
        int _channel = 0;
        int alt13 = 2;
        int LA13_0 = this.input.LA(1);
        if (LA13_0 == 65) {
            alt13 = 1;
        } else if (LA13_0 == 97) {
            alt13 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 13, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt13) {
            case 1: {
                this.match("ADD");
                break;
            }
            case 2: {
                this.match("add");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMODIFY() throws RecognitionException {
        int _type = 33;
        int _channel = 0;
        int alt14 = 2;
        int LA14_0 = this.input.LA(1);
        if (LA14_0 == 77) {
            alt14 = 1;
        } else if (LA14_0 == 109) {
            alt14 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 14, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt14) {
            case 1: {
                this.match("MODIFY");
                break;
            }
            case 2: {
                this.match("modify");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDROP() throws RecognitionException {
        int _type = 21;
        int _channel = 0;
        int alt15 = 2;
        int LA15_0 = this.input.LA(1);
        if (LA15_0 == 68) {
            alt15 = 1;
        } else if (LA15_0 == 100) {
            alt15 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 15, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt15) {
            case 1: {
                this.match("DROP");
                break;
            }
            case 2: {
                this.match("drop");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCOLUMN() throws RecognitionException {
        int _type = 11;
        int _channel = 0;
        int alt16 = 2;
        int LA16_0 = this.input.LA(1);
        if (LA16_0 == 67) {
            alt16 = 1;
        } else if (LA16_0 == 99) {
            alt16 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 16, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt16) {
            case 1: {
                this.match("COLUMN");
                break;
            }
            case 2: {
                this.match("column");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mRENAME() throws RecognitionException {
        int _type = 42;
        int _channel = 0;
        int alt17 = 2;
        int LA17_0 = this.input.LA(1);
        if (LA17_0 == 82) {
            alt17 = 1;
        } else if (LA17_0 == 114) {
            alt17 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 17, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt17) {
            case 1: {
                this.match("RENAME");
                break;
            }
            case 2: {
                this.match("rename");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mTO() throws RecognitionException {
        int _type = 48;
        int _channel = 0;
        int alt18 = 2;
        int LA18_0 = this.input.LA(1);
        if (LA18_0 == 84) {
            alt18 = 1;
        } else if (LA18_0 == 116) {
            alt18 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 18, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt18) {
            case 1: {
                this.match("TO");
                break;
            }
            case 2: {
                this.match("to");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDEFINE() throws RecognitionException {
        int _type = 19;
        int _channel = 0;
        int alt19 = 2;
        int LA19_0 = this.input.LA(1);
        if (LA19_0 == 68) {
            alt19 = 1;
        } else if (LA19_0 == 100) {
            alt19 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 19, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt19) {
            case 1: {
                this.match("DEFINE");
                break;
            }
            case 2: {
                this.match("define");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCLASS() throws RecognitionException {
        int _type = 9;
        int _channel = 0;
        int alt20 = 2;
        int LA20_0 = this.input.LA(1);
        if (LA20_0 == 67) {
            alt20 = 1;
        } else if (LA20_0 == 99) {
            alt20 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 20, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt20) {
            case 1: {
                this.match("CLASS");
                break;
            }
            case 2: {
                this.match("class");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mUNIQUE() throws RecognitionException {
        int _type = 49;
        int _channel = 0;
        int alt21 = 2;
        int LA21_0 = this.input.LA(1);
        if (LA21_0 == 85) {
            alt21 = 1;
        } else if (LA21_0 == 117) {
            alt21 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 21, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt21) {
            case 1: {
                this.match("UNIQUE");
                break;
            }
            case 2: {
                this.match("unique");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mVARCHAR() throws RecognitionException {
        int _type = 50;
        int _channel = 0;
        int alt22 = 2;
        int LA22_0 = this.input.LA(1);
        if (LA22_0 == 86) {
            alt22 = 1;
        } else if (LA22_0 == 118) {
            alt22 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 22, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt22) {
            case 1: {
                this.match("VARCHAR");
                break;
            }
            case 2: {
                this.match("varchar");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNVARCHAR() throws RecognitionException {
        int _type = 37;
        int _channel = 0;
        int alt23 = 2;
        int LA23_0 = this.input.LA(1);
        if (LA23_0 == 78) {
            alt23 = 1;
        } else if (LA23_0 == 110) {
            alt23 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 23, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt23) {
            case 1: {
                this.match("NVARCHAR");
                break;
            }
            case 2: {
                this.match("nvarchar");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNUMBER() throws RecognitionException {
        int _type = 36;
        int _channel = 0;
        int alt24 = 2;
        int LA24_0 = this.input.LA(1);
        if (LA24_0 == 78) {
            alt24 = 1;
        } else if (LA24_0 == 110) {
            alt24 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 24, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt24) {
            case 1: {
                this.match("NUMBER");
                break;
            }
            case 2: {
                this.match("number");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDATE() throws RecognitionException {
        int _type = 16;
        int _channel = 0;
        int alt25 = 2;
        int LA25_0 = this.input.LA(1);
        if (LA25_0 == 68) {
            alt25 = 1;
        } else if (LA25_0 == 100) {
            alt25 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 25, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt25) {
            case 1: {
                this.match("DATE");
                break;
            }
            case 2: {
                this.match("date");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mBLOB() throws RecognitionException {
        int _type = 6;
        int _channel = 0;
        int alt26 = 2;
        int LA26_0 = this.input.LA(1);
        if (LA26_0 == 66) {
            alt26 = 1;
        } else if (LA26_0 == 98) {
            alt26 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 26, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt26) {
            case 1: {
                this.match("BLOB");
                break;
            }
            case 2: {
                this.match("blob");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDATETIME() throws RecognitionException {
        int _type = 17;
        int _channel = 0;
        int alt27 = 2;
        int LA27_0 = this.input.LA(1);
        if (LA27_0 == 68) {
            alt27 = 1;
        } else if (LA27_0 == 100) {
            alt27 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 27, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt27) {
            case 1: {
                this.match("DATETIME");
                break;
            }
            case 2: {
                this.match("datetime");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mTIMESTAMP() throws RecognitionException {
        int _type = 47;
        int _channel = 0;
        int alt28 = 2;
        int LA28_0 = this.input.LA(1);
        if (LA28_0 == 84) {
            alt28 = 1;
        } else if (LA28_0 == 116) {
            alt28 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 28, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt28) {
            case 1: {
                this.match("TIMESTAMP");
                break;
            }
            case 2: {
                this.match("timestamp");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mRAW() throws RecognitionException {
        int _type = 41;
        int _channel = 0;
        int alt29 = 2;
        int LA29_0 = this.input.LA(1);
        if (LA29_0 == 82) {
            alt29 = 1;
        } else if (LA29_0 == 114) {
            alt29 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 29, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt29) {
            case 1: {
                this.match("RAW");
                break;
            }
            case 2: {
                this.match("raw");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCLOB() throws RecognitionException {
        int _type = 10;
        int _channel = 0;
        int alt30 = 2;
        int LA30_0 = this.input.LA(1);
        if (LA30_0 == 67) {
            alt30 = 1;
        } else if (LA30_0 == 99) {
            alt30 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 30, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt30) {
            case 1: {
                this.match("CLOB");
                break;
            }
            case 2: {
                this.match("clob");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMAX() throws RecognitionException {
        int _type = 31;
        int _channel = 0;
        int alt31 = 2;
        int LA31_0 = this.input.LA(1);
        if (LA31_0 == 77) {
            alt31 = 1;
        } else if (LA31_0 == 109) {
            alt31 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 31, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt31) {
            case 1: {
                this.match("MAX");
                break;
            }
            case 2: {
                this.match("max");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mIF() throws RecognitionException {
        int _type = 26;
        int _channel = 0;
        int alt32 = 2;
        int LA32_0 = this.input.LA(1);
        if (LA32_0 == 73) {
            alt32 = 1;
        } else if (LA32_0 == 105) {
            alt32 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 32, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt32) {
            case 1: {
                this.match("IF");
                break;
            }
            case 2: {
                this.match("if");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mEXISTS() throws RecognitionException {
        int _type = 24;
        int _channel = 0;
        int alt33 = 2;
        int LA33_0 = this.input.LA(1);
        if (LA33_0 == 69) {
            alt33 = 1;
        } else if (LA33_0 == 101) {
            alt33 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 33, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt33) {
            case 1: {
                this.match("EXISTS");
                break;
            }
            case 2: {
                this.match("exists");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCHAR_ID() throws RecognitionException {
        int _type = 8;
        int _channel = 0;
        if (!(this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122)) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        block3: while (true) {
            int alt34 = 2;
            int LA34_0 = this.input.LA(1);
            if (LA34_0 >= 48 && LA34_0 <= 57 || LA34_0 >= 65 && LA34_0 <= 90 || LA34_0 == 95 || LA34_0 >= 97 && LA34_0 <= 122) {
                alt34 = 1;
            }
            switch (alt34) {
                case 1: {
                    if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122) {
                        this.input.consume();
                        continue block3;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
            }
            break;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mSTRING() throws RecognitionException {
        int _type = 45;
        int _channel = 0;
        this.match(39);
        block4: while (true) {
            int alt35 = 3;
            int LA35_0 = this.input.LA(1);
            if (LA35_0 == 92) {
                alt35 = 1;
            } else if (LA35_0 >= 0 && LA35_0 <= 38 || LA35_0 >= 40 && LA35_0 <= 91 || LA35_0 >= 93 && LA35_0 <= 65535) {
                alt35 = 2;
            }
            switch (alt35) {
                case 1: {
                    this.mESC_SEQ();
                    continue block4;
                }
                case 2: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        continue block4;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(39);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mINT() throws RecognitionException {
        int _type = 28;
        int _channel = 0;
        int alt36 = 2;
        int LA36_0 = this.input.LA(1);
        if (LA36_0 == 43 || LA36_0 == 45) {
            alt36 = 1;
        }
        switch (alt36) {
            case 1: {
                if (this.input.LA(1) == 43 || this.input.LA(1) == 45) {
                    this.input.consume();
                    break;
                }
                MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                this.recover((RecognitionException)mse);
                throw mse;
            }
        }
        int cnt37 = 0;
        block6: while (true) {
            int alt37 = 2;
            int LA37_0 = this.input.LA(1);
            if (LA37_0 >= 48 && LA37_0 <= 57) {
                alt37 = 1;
            }
            switch (alt37) {
                case 1: {
                    if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57) {
                        this.input.consume();
                        break;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                default: {
                    if (cnt37 >= 1) break block6;
                    EarlyExitException eee = new EarlyExitException(37, (IntStream)this.input);
                    throw eee;
                }
            }
            ++cnt37;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mFLOAT() throws RecognitionException {
        int _type = 25;
        int _channel = 0;
        int alt38 = 2;
        int LA38_0 = this.input.LA(1);
        if (LA38_0 == 43 || LA38_0 == 45) {
            alt38 = 1;
        }
        switch (alt38) {
            case 1: {
                if (this.input.LA(1) == 43 || this.input.LA(1) == 45) {
                    this.input.consume();
                    break;
                }
                MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                this.recover((RecognitionException)mse);
                throw mse;
            }
        }
        this.mINT();
        int alt39 = 2;
        int LA39_0 = this.input.LA(1);
        if (LA39_0 == 46) {
            alt39 = 1;
        }
        switch (alt39) {
            case 1: {
                this.match(46);
                this.mINT();
            }
        }
        int alt41 = 2;
        int LA41_0 = this.input.LA(1);
        if (LA41_0 == 69 || LA41_0 == 101) {
            alt41 = 1;
        }
        switch (alt41) {
            case 1: {
                if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                this.input.consume();
                int alt40 = 2;
                int LA40_0 = this.input.LA(1);
                if (LA40_0 == 43 || LA40_0 == 45) {
                    alt40 = 1;
                }
                switch (alt40) {
                    case 1: {
                        if (this.input.LA(1) == 43 || this.input.LA(1) == 45) {
                            this.input.consume();
                            break;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                        this.recover((RecognitionException)mse);
                        throw mse;
                    }
                }
                this.mINT();
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDIGIT() throws RecognitionException {
        if (this.input.LA(1) < 48 || this.input.LA(1) > 57) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
    }

    public final void mCHAR() throws RecognitionException {
        if (!(this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122)) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
    }

    public final void mESC_SEQ() throws RecognitionException {
        int _type = 23;
        int _channel = 0;
        this.match(92);
        if (this.input.LA(1) != 34 && this.input.LA(1) != 39 && this.input.LA(1) != 92 && this.input.LA(1) != 110 && this.input.LA(1) != 114 && this.input.LA(1) != 116) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        _channel = 99;
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mWS() throws RecognitionException {
        int _type = 51;
        int _channel = 0;
        if ((this.input.LA(1) < 9 || this.input.LA(1) > 10) && this.input.LA(1) != 13 && this.input.LA(1) != 32) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        _channel = 99;
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLPAREN() throws RecognitionException {
        int _type = 30;
        int _channel = 0;
        this.match(40);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mRPAREN() throws RecognitionException {
        int _type = 43;
        int _channel = 0;
        this.match(41);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCOMMA() throws RecognitionException {
        int _type = 12;
        int _channel = 0;
        this.match(44);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mPLUS() throws RecognitionException {
        int _type = 39;
        int _channel = 0;
        this.match(43);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMINUS() throws RecognitionException {
        int _type = 32;
        int _channel = 0;
        this.match(45);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mEQ() throws RecognitionException {
        int _type = 22;
        int _channel = 0;
        this.match(61);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mSL_COMMENT() throws RecognitionException {
        int _type = 44;
        int _channel = 0;
        this.match("--");
        block9: while (true) {
            int alt42 = 2;
            int LA42_0 = this.input.LA(1);
            if (LA42_0 >= 0 && LA42_0 <= 9 || LA42_0 >= 11 && LA42_0 <= 12 || LA42_0 >= 14 && LA42_0 <= 65535) {
                alt42 = 1;
            }
            switch (alt42) {
                case 1: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        continue block9;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
            }
            break;
        }
        int alt43 = 2;
        int LA43_0 = this.input.LA(1);
        if (LA43_0 == 13) {
            alt43 = 1;
        }
        switch (alt43) {
            case 1: {
                this.match(13);
            }
        }
        int alt44 = 2;
        int LA44_0 = this.input.LA(1);
        if (LA44_0 == 10) {
            alt44 = 1;
        }
        switch (alt44) {
            case 1: {
                this.match(10);
            }
        }
        _channel = 99;
        this.state.type = _type;
        this.state.channel = _channel;
    }

    @Override
    public void mTokens() throws RecognitionException {
        int alt45 = 46;
        alt45 = this.dfa45.predict((IntStream)this.input);
        switch (alt45) {
            case 1: {
                this.mCREATE();
                break;
            }
            case 2: {
                this.mTABLE();
                break;
            }
            case 3: {
                this.mINDEX();
                break;
            }
            case 4: {
                this.mCONSTRAINT();
                break;
            }
            case 5: {
                this.mPRIMARY();
                break;
            }
            case 6: {
                this.mCOMMENT();
                break;
            }
            case 7: {
                this.mKEY();
                break;
            }
            case 8: {
                this.mNOT();
                break;
            }
            case 9: {
                this.mNULL();
                break;
            }
            case 10: {
                this.mDEFAULT();
                break;
            }
            case 11: {
                this.mON();
                break;
            }
            case 12: {
                this.mALTER();
                break;
            }
            case 13: {
                this.mADD();
                break;
            }
            case 14: {
                this.mMODIFY();
                break;
            }
            case 15: {
                this.mDROP();
                break;
            }
            case 16: {
                this.mCOLUMN();
                break;
            }
            case 17: {
                this.mRENAME();
                break;
            }
            case 18: {
                this.mTO();
                break;
            }
            case 19: {
                this.mDEFINE();
                break;
            }
            case 20: {
                this.mCLASS();
                break;
            }
            case 21: {
                this.mUNIQUE();
                break;
            }
            case 22: {
                this.mVARCHAR();
                break;
            }
            case 23: {
                this.mNVARCHAR();
                break;
            }
            case 24: {
                this.mNUMBER();
                break;
            }
            case 25: {
                this.mDATE();
                break;
            }
            case 26: {
                this.mBLOB();
                break;
            }
            case 27: {
                this.mDATETIME();
                break;
            }
            case 28: {
                this.mTIMESTAMP();
                break;
            }
            case 29: {
                this.mRAW();
                break;
            }
            case 30: {
                this.mCLOB();
                break;
            }
            case 31: {
                this.mMAX();
                break;
            }
            case 32: {
                this.mIF();
                break;
            }
            case 33: {
                this.mEXISTS();
                break;
            }
            case 34: {
                this.mCHAR_ID();
                break;
            }
            case 35: {
                this.mSTRING();
                break;
            }
            case 36: {
                this.mINT();
                break;
            }
            case 37: {
                this.mFLOAT();
                break;
            }
            case 38: {
                this.mESC_SEQ();
                break;
            }
            case 39: {
                this.mWS();
                break;
            }
            case 40: {
                this.mLPAREN();
                break;
            }
            case 41: {
                this.mRPAREN();
                break;
            }
            case 42: {
                this.mCOMMA();
                break;
            }
            case 43: {
                this.mPLUS();
                break;
            }
            case 44: {
                this.mMINUS();
                break;
            }
            case 45: {
                this.mEQ();
                break;
            }
            case 46: {
                this.mSL_COMMENT();
            }
        }
    }

    static {
        int numStates = DFA45_transitionS.length;
        DFA45_transition = new short[numStates][];
        for (int i = 0; i < numStates; ++i) {
            ANTLRLexer.DFA45_transition[i] = DFA.unpackEncodedString((String)DFA45_transitionS[i]);
        }
    }

    protected class DFA45
    extends DFA {
        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = DFA45_eot;
            this.eof = DFA45_eof;
            this.min = DFA45_min;
            this.max = DFA45_max;
            this.accept = DFA45_accept;
            this.special = DFA45_special;
            this.transition = DFA45_transition;
        }

        public String getDescription() {
            return "1:1: Tokens : ( CREATE | TABLE | INDEX | CONSTRAINT | PRIMARY | COMMENT | KEY | NOT | NULL | DEFAULT | ON | ALTER | ADD | MODIFY | DROP | COLUMN | RENAME | TO | DEFINE | CLASS | UNIQUE | VARCHAR | NVARCHAR | NUMBER | DATE | BLOB | DATETIME | TIMESTAMP | RAW | CLOB | MAX | IF | EXISTS | CHAR_ID | STRING | INT | FLOAT | ESC_SEQ | WS | LPAREN | RPAREN | COMMA | PLUS | MINUS | EQ | SL_COMMENT );";
        }

        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
                case 0: {
                    int LA45_265 = input.LA(1);
                    s = -1;
                    s = LA45_265 >= 0 && LA45_265 <= 47 || LA45_265 >= 58 && LA45_265 <= 65535 ? 161 : (LA45_265 >= 48 && LA45_265 <= 57 ? 265 : 96);
                    if (s < 0) break;
                    return s;
                }
                case 1: {
                    int LA45_263 = input.LA(1);
                    s = -1;
                    s = LA45_263 >= 0 && LA45_263 <= 47 || LA45_263 >= 58 && LA45_263 <= 68 || LA45_263 >= 70 && LA45_263 <= 100 || LA45_263 >= 102 && LA45_263 <= 65535 ? 161 : (LA45_263 == 69 || LA45_263 == 101 ? 216 : (LA45_263 >= 48 && LA45_263 <= 57 ? 263 : 96));
                    if (s < 0) break;
                    return s;
                }
                case 2: {
                    int LA45_160 = input.LA(1);
                    s = -1;
                    s = LA45_160 >= 0 && LA45_160 <= 45 || LA45_160 == 47 || LA45_160 >= 58 && LA45_160 <= 68 || LA45_160 >= 70 && LA45_160 <= 100 || LA45_160 >= 102 && LA45_160 <= 65535 ? 161 : (LA45_160 == 46 ? 215 : (LA45_160 == 69 || LA45_160 == 101 ? 216 : (LA45_160 >= 48 && LA45_160 <= 57 ? 160 : 96)));
                    if (s < 0) break;
                    return s;
                }
            }
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 45, _s, input);
            this.error(nvae);
            throw nvae;
        }
    }
}

