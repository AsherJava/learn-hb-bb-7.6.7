/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.antlr.runtime.BaseRecognizer
 *  org.antlr.runtime.CharStream
 *  org.antlr.runtime.DFA
 *  org.antlr.runtime.EarlyExitException
 *  org.antlr.runtime.IntStream
 *  org.antlr.runtime.MismatchedSetException
 *  org.antlr.runtime.NoViableAltException
 *  org.antlr.runtime.RecognitionException
 *  org.antlr.runtime.RecognizerSharedState
 */
package com.jiuqi.np.dataengine.parse.link;

import com.jiuqi.np.dataengine.parse.link.BaseANTLRLexer;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class ANTLRLexer
extends BaseANTLRLexer {
    public static final int LT = 13;
    public static final int MIXED_CHARACTER_ID_BEGIN_WITH_DIGIT = 48;
    public static final int MINUS = 19;
    public static final int ZB_APPEND = 41;
    public static final int SEMICOLON = 44;
    public static final int ELSE = 6;
    public static final int IF = 4;
    public static final int CHINESECHAR = 54;
    public static final int T_60 = 60;
    public static final int T_61 = 61;
    public static final int NULL = 29;
    public static final int IN = 17;
    public static final int LPAREN = 45;
    public static final int TRUE = 27;
    public static final int OPPOSE = 36;
    public static final int RPAREN = 46;
    public static final int EQ = 10;
    public static final int PERCENT_SIGN = 52;
    public static final int NOT = 9;
    public static final int AT = 43;
    public static final int LIKE = 16;
    public static final int RBRACE = 39;
    public static final int AND = 8;
    public static final int NE = 15;
    public static final int THEN = 5;
    public static final int DOLLAR = 37;
    public static final int T_62 = 62;
    public static final int MIXED_CHINESE_CHAR_ID_BEGIN_CHINESE = 49;
    public static final int T_63 = 63;
    public static final int T_64 = 64;
    public static final int PLUS = 18;
    public static final int LBRACE = 38;
    public static final int QUOTE = 56;
    public static final int FLOAT = 25;
    public static final int CHAR = 53;
    public static final int INT = 24;
    public static final int DOUBLEQUOTE = 57;
    public static final int DIVIDE = 22;
    public static final int MIXED_CHARACTER_ID_BEGIN_WITH_CHAR = 47;
    public static final int LINK = 20;
    public static final int ESC_SEQ = 50;
    public static final int CHARACTER = 42;
    public static final int WS = 55;
    public static final int EOF = -1;
    public static final int GE = 12;
    public static final int COMMA = 33;
    public static final int STRING1 = 31;
    public static final int OR = 7;
    public static final int POWER = 23;
    public static final int LBRACKET = 32;
    public static final int RBRACKET = 34;
    public static final int COLON = 40;
    public static final int GT = 11;
    public static final int DIGIT = 51;
    public static final int PERCENT = 30;
    public static final int ML_COMMENT = 59;
    public static final int SL_COMMENT = 58;
    public static final int LE = 14;
    public static final int STRING = 26;
    public static final int FALSE = 28;
    public static final int POINT = 35;
    public static final int MULTI = 21;
    protected DFA23 dfa23 = new DFA23((BaseRecognizer)this);
    static final String DFA23_EOTS = "\u0001\uffff\u0001/\u00010\u00012\u00013\u00014\u00016\u00018\u00016\u0001\uffff\u00066\u0001D\u0001F\u0001G\u00016\u00011\u0004\uffff\u0001O\u0001Q\u0001S\u0001\uffff\u0001U\u0001\uffff\u0001W\u0015\uffff\u00016\u0003\uffff\u0001\t\u00026\u0001[\u0001\\\u00056\u0005\uffff\u0001G\u0001\uffff\u0001c\u0001\uffff\u0001c\u0001\uffff\u00011\t\uffff\u00017\u0001f\u00016\u0002\uffff\u00056\u0001m\u0002\uffff\u0001m\u0001\uffff\u0001p\u0001q\u0001r\u0001s\u0001t\u00016\u0002\uffff\u0001m\u0005\uffff\u0001v\u0001\uffff";
    static final String DFA23_EOFS = "w\uffff";
    static final String DFA23_MINS = "\u0001\t\u0001=\u0005$\u0001&\u0001$\u0001\uffff\u0006$\u0002\u0000\u0001%\u0001$\u00010\u0004\uffff\u0003=\u0001\uffff\u0001-\u0001\uffff\u0001*\u0015\uffff\u0001$\u0003\uffff\n$\u0005\uffff\u0001%\u00010\u0001+\u0001\uffff\u00010\u0001\uffff\u00010\t\uffff\u0003$\u0002\uffff\u0005$\u0001%\u0001\uffff\u00010\u0001%\u0001\uffff\u0006$\u0001\uffff\u0001+\u0001%\u0005\uffff\u0001$\u0001\uffff";
    static final String DFA23_MAXS = "\u0001\ufa2d\u0001=\u0005z\u0001&\u0001z\u0001\uffff\u0006z\u0002\uffff\u0003z\u0004\uffff\u0002=\u0001>\u0001\uffff\u0001-\u0001\uffff\u0001/\u0015\uffff\u0001z\u0003\uffff\nz\u0005\uffff\u0001z\u00019\u0001z\u0001\uffff\u0001z\u0001\uffff\u0001z\t\uffff\u0003z\u0002\uffff\u0005z\u0001e\u0001\uffff\u00019\u0001z\u0001\uffff\u0006z\u0001\uffff\u00029\u0005\uffff\u0001z\u0001\uffff";
    static final String DFA23_ACCEPTS = "\t\uffff\u0001\u0007\u000b\uffff\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0003\uffff\u0001#\u0001\uffff\u0001%\u0001\uffff\u0001'\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u00013\u00014\u00015\u00016\u00017\u00018\u0001\"\u0001\u0001\u0001\u0002\u0001\u0018\u0001\u0003\u0001\u0004\u0001\u0005\u0001\uffff\u0001\u0017\u0001\u0006\u0001(\n\uffff\u0001\u0011\u00011\u0001\u0012\u00010\u0001\u0013\u0003\uffff\u0001\u0015\u0001\uffff\u0001\u0019\u0001\uffff\u00012\u0001\u001f\u0001\u001e\u0001!\u0001 \u00019\u0001$\u0001:\u0001&\u0003\uffff\u0001\t\u0001\r\u0006\uffff\u0001\u0016\u0002\uffff\u0001\b\u0006\uffff\u0001\u0014\u0002\uffff\u0001\u0010\u0001\n\u0001\u000e\u0001\u000b\u0001\f\u0001\uffff\u0001\u000f";
    static final String DFA23_SPECIALS = "\u0010\uffff\u0001\u0001\u0001\u0000e\uffff}>";
    static final String[] DFA23_TRANSITIONS = new String[]{"\u0002\u0017\u0002\uffff\u0001\u0017\u0012\uffff\u0001\u0017\u0001\u0001\u0001\u0010\u0001\uffff\u0001-\u0001+\u0001\u0007\u0001\u0011\u0001\"\u0001#\u0001\u001e\u0001\u001c\u0001)\u0001\u001d\u0001*\u0001\u001f\n\u0012\u0001\u0019\u0001(\u0001\u001b\u0001\u0018\u0001\u001a\u0001\uffff\u0001,\u0001\u0006\u0001\u0013\u0001\u0003\u0001\u0013\u0001\r\u0001\u000f\u0002\u0013\u0001\u000b\u0002\u0013\u0001\u000e\u0001\u0013\u0001\n\u0001\b\u0002\u0013\u0001\u0005\u0001\u0013\u0001\f\u0006\u0013\u0001$\u0001\u0016\u0001%\u0001 \u0001\u0014\u0001\uffff\u0001\u0006\u0001\u0013\u0001\u0002\u0001\u0013\u0001\r\u0001\u000f\u0002\u0013\u0001\u000b\u0002\u0013\u0001\u000e\u0001\u0013\u0001\n\u0001\b\u0002\u0013\u0001\u0004\u0001\u0013\u0001\f\u0006\u0013\u0001&\u0001\t\u0001'\u0001!\u4d81\uffff\u51a6\u0015\u595a\uffff\u012e\u0015", "\u0001.", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\r\u0013\u00015\f\u0013\u0004\uffff\u00011\u0001\uffff\r\u0013\u00015\f\u0013", "\u00017", "\u00011\u000b\uffff\n1\u0007\uffff\u0011\u0013\u00019\b\u0013\u0004\uffff\u00011\u0001\uffff\u0011\u0013\u00019\b\u0013", "", "\u00011\u000b\uffff\n1\u0007\uffff\u000e\u0013\u0001:\u0005\u0013\u0001;\u0005\u0013\u0004\uffff\u00011\u0001\uffff\u000e\u0013\u0001:\u0005\u0013\u0001;\u0005\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0005\u0013\u0001<\u0007\u0013\u0001=\f\u0013\u0004\uffff\u00011\u0001\uffff\u0005\u0013\u0001<\u0007\u0013\u0001=\f\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0007\u0013\u0001>\t\u0013\u0001?\b\u0013\u0004\uffff\u00011\u0001\uffff\u0007\u0013\u0001>\t\u0013\u0001?\b\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u000b\u0013\u0001@\u000e\u0013\u0004\uffff\u00011\u0001\uffff\u000b\u0013\u0001@\u000e\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\b\u0013\u0001A\u0011\u0013\u0004\uffff\u00011\u0001\uffff\b\u0013\u0001A\u0011\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0001B\u0019\u0013\u0004\uffff\u00011\u0001\uffff\u0001B\u0019\u0013", "\u0000C", "\u0000E", "\u0001K\b\uffff\u0001I\u0001\uffff\nH\u0007\uffff\u0004L\u0001J\u0015L\u0004\uffff\u0001M\u0001\uffff\u0004L\u0001J\u0015L", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\nN\u0007\uffff\u001aN\u0004\uffff\u0001N\u0001\uffff\u001aN", "", "", "", "", "\u0001\u0018", "\u0001P", "\u0001R\u0001.", "", "\u0001T", "", "\u0001V\u0004\uffff\u0001T", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "\u00011\u000b\uffff\n1\u0007\uffff\u0003\u0013\u0001X\u0016\u0013\u0004\uffff\u00011\u0001\uffff\u0003\u0013\u0001X\u0016\u0013", "", "", "", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0013\u0013\u0001Y\u0006\u0013\u0004\uffff\u00011\u0001\uffff\u0013\u0013\u0001Y\u0006\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u000b\u0013\u0001Z\u000e\u0013\u0004\uffff\u00011\u0001\uffff\u000b\u0013\u0001Z\u000e\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0004\u0013\u0001]\u0015\u0013\u0004\uffff\u00011\u0001\uffff\u0004\u0013\u0001]\u0015\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0014\u0013\u0001^\u0005\u0013\u0004\uffff\u00011\u0001\uffff\u0014\u0013\u0001^\u0005\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0012\u0013\u0001_\u0007\u0013\u0004\uffff\u00011\u0001\uffff\u0012\u0013\u0001_\u0007\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\n\u0013\u0001`\u000f\u0013\u0004\uffff\u00011\u0001\uffff\n\u0013\u0001`\u000f\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u000b\u0013\u0001a\u000e\u0013\u0004\uffff\u00011\u0001\uffff\u000b\u0013\u0001a\u000e\u0013", "", "", "", "", "", "\u0001K\b\uffff\u0001I\u0001\uffff\nH\u0007\uffff\u0004L\u0001J\u0015L\u0004\uffff\u0001M\u0001\uffff\u0004L\u0001J\u0015L", "\nb", "\u0001d\u0001\uffff\u0001d\u0002\uffff\ne\u0007\uffff\u001aM\u0004\uffff\u0001M\u0001\uffff\u001aM", "", "\nM\u0007\uffff\u001aM\u0004\uffff\u0001M\u0001\uffff\u001aM", "", "\nN\u0007\uffff\u001aN\u0004\uffff\u0001N\u0001\uffff\u001aN", "", "", "", "", "", "", "", "", "", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u000b\u0013\u0001g\u000e\u0013\u0004\uffff\u00011\u0001\uffff\u000b\u0013\u0001g\u000e\u0013", "", "", "\u00011\u000b\uffff\n1\u0007\uffff\r\u0013\u0001h\f\u0013\u0004\uffff\u00011\u0001\uffff\r\u0013\u0001h\f\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0004\u0013\u0001i\u0015\u0013\u0004\uffff\u00011\u0001\uffff\u0004\u0013\u0001i\u0015\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0004\u0013\u0001j\u0015\u0013\u0004\uffff\u00011\u0001\uffff\u0004\u0013\u0001j\u0015\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0004\u0013\u0001k\u0015\u0013\u0004\uffff\u00011\u0001\uffff\u0004\u0013\u0001k\u0015\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0012\u0013\u0001l\u0007\u0013\u0004\uffff\u00011\u0001\uffff\u0012\u0013\u0001l\u0007\u0013", "\u0001K\n\uffff\nb\u000b\uffff\u0001n\u001f\uffff\u0001n", "", "\no", "\u0001K\n\uffff\ne\u0007\uffff\u001aM\u0004\uffff\u0001M\u0001\uffff\u001aM", "", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", "\u00011\u000b\uffff\n1\u0007\uffff\u0004\u0013\u0001u\u0015\u0013\u0004\uffff\u00011\u0001\uffff\u0004\u0013\u0001u\u0015\u0013", "", "\u0001d\u0001\uffff\u0001d\u0002\uffff\no", "\u0001K\n\uffff\no", "", "", "", "", "", "\u00011\u000b\uffff\n1\u0007\uffff\u001a\u0013\u0004\uffff\u00011\u0001\uffff\u001a\u0013", ""};
    static final short[] DFA23_EOT = DFA.unpackEncodedString((String)"\u0001\uffff\u0001/\u00010\u00012\u00013\u00014\u00016\u00018\u00016\u0001\uffff\u00066\u0001D\u0001F\u0001G\u00016\u00011\u0004\uffff\u0001O\u0001Q\u0001S\u0001\uffff\u0001U\u0001\uffff\u0001W\u0015\uffff\u00016\u0003\uffff\u0001\t\u00026\u0001[\u0001\\\u00056\u0005\uffff\u0001G\u0001\uffff\u0001c\u0001\uffff\u0001c\u0001\uffff\u00011\t\uffff\u00017\u0001f\u00016\u0002\uffff\u00056\u0001m\u0002\uffff\u0001m\u0001\uffff\u0001p\u0001q\u0001r\u0001s\u0001t\u00016\u0002\uffff\u0001m\u0005\uffff\u0001v\u0001\uffff");
    static final short[] DFA23_EOF = DFA.unpackEncodedString((String)"w\uffff");
    static final char[] DFA23_MIN = DFA.unpackEncodedStringToUnsignedChars((String)"\u0001\t\u0001=\u0005$\u0001&\u0001$\u0001\uffff\u0006$\u0002\u0000\u0001%\u0001$\u00010\u0004\uffff\u0003=\u0001\uffff\u0001-\u0001\uffff\u0001*\u0015\uffff\u0001$\u0003\uffff\n$\u0005\uffff\u0001%\u00010\u0001+\u0001\uffff\u00010\u0001\uffff\u00010\t\uffff\u0003$\u0002\uffff\u0005$\u0001%\u0001\uffff\u00010\u0001%\u0001\uffff\u0006$\u0001\uffff\u0001+\u0001%\u0005\uffff\u0001$\u0001\uffff");
    static final char[] DFA23_MAX = DFA.unpackEncodedStringToUnsignedChars((String)"\u0001\ufa2d\u0001=\u0005z\u0001&\u0001z\u0001\uffff\u0006z\u0002\uffff\u0003z\u0004\uffff\u0002=\u0001>\u0001\uffff\u0001-\u0001\uffff\u0001/\u0015\uffff\u0001z\u0003\uffff\nz\u0005\uffff\u0001z\u00019\u0001z\u0001\uffff\u0001z\u0001\uffff\u0001z\t\uffff\u0003z\u0002\uffff\u0005z\u0001e\u0001\uffff\u00019\u0001z\u0001\uffff\u0006z\u0001\uffff\u00029\u0005\uffff\u0001z\u0001\uffff");
    static final short[] DFA23_ACCEPT = DFA.unpackEncodedString((String)"\t\uffff\u0001\u0007\u000b\uffff\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0003\uffff\u0001#\u0001\uffff\u0001%\u0001\uffff\u0001'\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u00013\u00014\u00015\u00016\u00017\u00018\u0001\"\u0001\u0001\u0001\u0002\u0001\u0018\u0001\u0003\u0001\u0004\u0001\u0005\u0001\uffff\u0001\u0017\u0001\u0006\u0001(\n\uffff\u0001\u0011\u00011\u0001\u0012\u00010\u0001\u0013\u0003\uffff\u0001\u0015\u0001\uffff\u0001\u0019\u0001\uffff\u00012\u0001\u001f\u0001\u001e\u0001!\u0001 \u00019\u0001$\u0001:\u0001&\u0003\uffff\u0001\t\u0001\r\u0006\uffff\u0001\u0016\u0002\uffff\u0001\b\u0006\uffff\u0001\u0014\u0002\uffff\u0001\u0010\u0001\n\u0001\u000e\u0001\u000b\u0001\f\u0001\uffff\u0001\u000f");
    static final short[] DFA23_SPECIAL = DFA.unpackEncodedString((String)"\u0010\uffff\u0001\u0001\u0001\u0000e\uffff}>");
    static final short[][] DFA23_TRANSITION;

    public ANTLRLexer() {
    }

    public ANTLRLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public ANTLRLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String getGrammarFileName() {
        return "D:\\work\\antlr3\\ANTLR.g";
    }

    public final void mT__60() throws RecognitionException {
        int _type = 60;
        int _channel = 0;
        this.match(33);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mT__61() throws RecognitionException {
        int _type = 61;
        int _channel = 0;
        this.match(99);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mT__62() throws RecognitionException {
        int _type = 62;
        int _channel = 0;
        this.match(67);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mT__63() throws RecognitionException {
        int _type = 63;
        int _channel = 0;
        this.match(114);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mT__64() throws RecognitionException {
        int _type = 64;
        int _channel = 0;
        this.match(82);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mAND() throws RecognitionException {
        int _type = 8;
        int _channel = 0;
        int alt1 = 2;
        int LA1_0 = this.input.LA(1);
        if (LA1_0 == 65 || LA1_0 == 97) {
            alt1 = 1;
        } else if (LA1_0 == 38) {
            alt1 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 1, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt1) {
            case 1: {
                if (this.input.LA(1) != 65 && this.input.LA(1) != 97) {
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                this.input.consume();
                if (this.input.LA(1) != 78 && this.input.LA(1) != 110) {
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                this.input.consume();
                if (this.input.LA(1) == 68 || this.input.LA(1) == 100) {
                    this.input.consume();
                    break;
                }
                MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                this.recover((RecognitionException)mse);
                throw mse;
            }
            case 2: {
                this.match("&&");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mOR() throws RecognitionException {
        int _type = 7;
        int _channel = 0;
        int alt2 = 2;
        int LA2_0 = this.input.LA(1);
        if (LA2_0 == 79 || LA2_0 == 111) {
            alt2 = 1;
        } else if (LA2_0 == 124) {
            alt2 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 2, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt2) {
            case 1: {
                if (this.input.LA(1) != 79 && this.input.LA(1) != 111) {
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                this.input.consume();
                if (this.input.LA(1) == 82 || this.input.LA(1) == 114) {
                    this.input.consume();
                    break;
                }
                MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                this.recover((RecognitionException)mse);
                throw mse;
            }
            case 2: {
                this.match("||");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNOT() throws RecognitionException {
        int _type = 9;
        int _channel = 0;
        if (this.input.LA(1) != 78 && this.input.LA(1) != 110) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 79 && this.input.LA(1) != 111) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 84 && this.input.LA(1) != 116) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mIF() throws RecognitionException {
        int _type = 4;
        int _channel = 0;
        if (this.input.LA(1) != 73 && this.input.LA(1) != 105) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 70 && this.input.LA(1) != 102) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mTHEN() throws RecognitionException {
        int _type = 5;
        int _channel = 0;
        if (this.input.LA(1) != 84 && this.input.LA(1) != 116) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 72 && this.input.LA(1) != 104) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 78 && this.input.LA(1) != 110) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mELSE() throws RecognitionException {
        int _type = 6;
        int _channel = 0;
        if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 76 && this.input.LA(1) != 108) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 83 && this.input.LA(1) != 115) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLIKE() throws RecognitionException {
        int _type = 16;
        int _channel = 0;
        if (this.input.LA(1) != 76 && this.input.LA(1) != 108) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 73 && this.input.LA(1) != 105) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 75 && this.input.LA(1) != 107) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mIN() throws RecognitionException {
        int _type = 17;
        int _channel = 0;
        if (this.input.LA(1) != 73 && this.input.LA(1) != 105) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 78 && this.input.LA(1) != 110) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mTRUE() throws RecognitionException {
        int _type = 27;
        int _channel = 0;
        if (this.input.LA(1) != 84 && this.input.LA(1) != 116) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 82 && this.input.LA(1) != 114) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 85 && this.input.LA(1) != 117) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mFALSE() throws RecognitionException {
        int _type = 28;
        int _channel = 0;
        if (this.input.LA(1) != 70 && this.input.LA(1) != 102) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 65 && this.input.LA(1) != 97) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 76 && this.input.LA(1) != 108) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 83 && this.input.LA(1) != 115) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNULL() throws RecognitionException {
        int _type = 29;
        int _channel = 0;
        if (this.input.LA(1) != 78 && this.input.LA(1) != 110) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 85 && this.input.LA(1) != 117) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 76 && this.input.LA(1) != 108) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        if (this.input.LA(1) != 76 && this.input.LA(1) != 108) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mSTRING() throws RecognitionException {
        int _type = 26;
        int _channel = 0;
        this.match(34);
        block5: while (true) {
            int alt3 = 4;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 92) {
                int LA3_2 = this.input.LA(2);
                if (LA3_2 == 34 || LA3_2 == 39 || LA3_2 == 92 || LA3_2 == 110 || LA3_2 == 114 || LA3_2 == 116) {
                    alt3 = 1;
                } else if (LA3_2 == 117) {
                    alt3 = 3;
                }
            } else if (LA3_0 >= 0 && LA3_0 <= 33 || LA3_0 >= 35 && LA3_0 <= 91 || LA3_0 >= 93 && LA3_0 <= 65535) {
                alt3 = 2;
            }
            switch (alt3) {
                case 1: {
                    this.mESC_SEQ();
                    continue block5;
                }
                case 2: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 33 || this.input.LA(1) >= 35 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        continue block5;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                case 3: {
                    this.match("\\u");
                    continue block5;
                }
            }
            break;
        }
        this.match(34);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mSTRING1() throws RecognitionException {
        int _type = 31;
        int _channel = 0;
        this.match(39);
        block5: while (true) {
            int alt4 = 4;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 92) {
                int LA4_2 = this.input.LA(2);
                if (LA4_2 == 34 || LA4_2 == 39 || LA4_2 == 92 || LA4_2 == 110 || LA4_2 == 114 || LA4_2 == 116) {
                    alt4 = 1;
                } else if (LA4_2 == 117) {
                    alt4 = 3;
                }
            } else if (LA4_0 >= 0 && LA4_0 <= 38 || LA4_0 >= 40 && LA4_0 <= 91 || LA4_0 >= 93 && LA4_0 <= 65535) {
                alt4 = 2;
            }
            switch (alt4) {
                case 1: {
                    this.mESC_SEQ();
                    continue block5;
                }
                case 2: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        continue block5;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                case 3: {
                    this.match("\\u");
                    continue block5;
                }
            }
            break;
        }
        this.match(39);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mINT() throws RecognitionException {
        int _type = 24;
        int _channel = 0;
        int cnt5 = 0;
        block3: while (true) {
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 >= 48 && LA5_0 <= 57) {
                alt5 = 1;
            }
            switch (alt5) {
                case 1: {
                    this.mDIGIT();
                    break;
                }
                default: {
                    if (cnt5 >= 1) break block3;
                    EarlyExitException eee = new EarlyExitException(5, (IntStream)this.input);
                    throw eee;
                }
            }
            ++cnt5;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mFLOAT() throws RecognitionException {
        int _type = 25;
        int _channel = 0;
        this.mINT();
        int alt6 = 2;
        int LA6_0 = this.input.LA(1);
        if (LA6_0 == 46) {
            alt6 = 1;
        }
        switch (alt6) {
            case 1: {
                this.match(46);
                this.mINT();
            }
        }
        int alt8 = 2;
        int LA8_0 = this.input.LA(1);
        if (LA8_0 == 69 || LA8_0 == 101) {
            alt8 = 1;
        }
        switch (alt8) {
            case 1: {
                if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                this.input.consume();
                int alt7 = 2;
                int LA7_0 = this.input.LA(1);
                if (LA7_0 == 43 || LA7_0 == 45) {
                    alt7 = 1;
                }
                switch (alt7) {
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

    public final void mPERCENT() throws RecognitionException {
        int _type = 30;
        int _channel = 0;
        this.mFLOAT();
        this.mPERCENT_SIGN();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mZB_APPEND() throws RecognitionException {
        int _type = 41;
        int _channel = 0;
        int cnt9 = 0;
        block3: while (true) {
            int alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 >= 48 && LA9_0 <= 57) {
                alt9 = 1;
            }
            switch (alt9) {
                case 1: {
                    this.mDIGIT();
                    break;
                }
                default: {
                    if (cnt9 >= 1) break block3;
                    EarlyExitException eee = new EarlyExitException(9, (IntStream)this.input);
                    throw eee;
                }
            }
            ++cnt9;
        }
        this.mCHAR();
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCHARACTER() throws RecognitionException {
        int _type = 42;
        int _channel = 0;
        int cnt10 = 0;
        block3: while (true) {
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 >= 65 && LA10_0 <= 90 || LA10_0 >= 97 && LA10_0 <= 122) {
                alt10 = 1;
            }
            switch (alt10) {
                case 1: {
                    this.mCHAR();
                    break;
                }
                default: {
                    if (cnt10 >= 1) break block3;
                    EarlyExitException eee = new EarlyExitException(10, (IntStream)this.input);
                    throw eee;
                }
            }
            ++cnt10;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMIXED_CHARACTER_ID_BEGIN_WITH_CHAR() throws RecognitionException {
        int _type = 47;
        int _channel = 0;
        int alt13 = 2;
        int LA13_0 = this.input.LA(1);
        if (LA13_0 >= 65 && LA13_0 <= 90 || LA13_0 >= 97 && LA13_0 <= 122) {
            alt13 = 1;
        } else if (LA13_0 == 95) {
            alt13 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 13, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt13) {
            case 1: {
                this.mCHARACTER();
                if (this.input.LA(1) != 36 && (this.input.LA(1) < 48 || this.input.LA(1) > 57) && this.input.LA(1) != 95) {
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
                this.input.consume();
                block10: while (true) {
                    int alt11 = 2;
                    int LA11_0 = this.input.LA(1);
                    if (LA11_0 == 36 || LA11_0 >= 48 && LA11_0 <= 57 || LA11_0 >= 65 && LA11_0 <= 90 || LA11_0 == 95 || LA11_0 >= 97 && LA11_0 <= 122) {
                        alt11 = 1;
                    }
                    switch (alt11) {
                        case 1: {
                            if (this.input.LA(1) == 36 || this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122) {
                                this.input.consume();
                                continue block10;
                            }
                            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                            this.recover((RecognitionException)mse);
                            throw mse;
                        }
                    }
                    break;
                }
                break;
            }
            case 2: {
                this.match(95);
                block11: while (true) {
                    int alt12 = 2;
                    int LA12_0 = this.input.LA(1);
                    if (LA12_0 >= 48 && LA12_0 <= 57 || LA12_0 >= 65 && LA12_0 <= 90 || LA12_0 == 95 || LA12_0 >= 97 && LA12_0 <= 122) {
                        alt12 = 1;
                    }
                    switch (alt12) {
                        case 1: {
                            if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122) {
                                this.input.consume();
                                continue block11;
                            }
                            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                            this.recover((RecognitionException)mse);
                            throw mse;
                        }
                    }
                    break;
                }
                break;
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMIXED_CHARACTER_ID_BEGIN_WITH_DIGIT() throws RecognitionException {
        int _type = 48;
        int _channel = 0;
        if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && this.input.LA(1) != 95) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
        block3: while (true) {
            int alt14 = 2;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 >= 48 && LA14_0 <= 57 || LA14_0 >= 65 && LA14_0 <= 90 || LA14_0 == 95 || LA14_0 >= 97 && LA14_0 <= 122) {
                alt14 = 1;
            }
            switch (alt14) {
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

    public final void mMIXED_CHINESE_CHAR_ID_BEGIN_CHINESE() throws RecognitionException {
        int _type = 49;
        int _channel = 0;
        this.mCHINESECHAR();
        block3: while (true) {
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 >= 40 && LA15_0 <= 41 || LA15_0 == 45 || LA15_0 >= 48 && LA15_0 <= 57 || LA15_0 >= 65 && LA15_0 <= 90 || LA15_0 == 95 || LA15_0 >= 97 && LA15_0 <= 122 || LA15_0 >= 19968 && LA15_0 <= 40869 || LA15_0 >= 63744 && LA15_0 <= 64045 || LA15_0 >= 65288 && LA15_0 <= 65289) {
                alt15 = 1;
            }
            switch (alt15) {
                case 1: {
                    if (this.input.LA(1) >= 40 && this.input.LA(1) <= 41 || this.input.LA(1) == 45 || this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122 || this.input.LA(1) >= 19968 && this.input.LA(1) <= 40869 || this.input.LA(1) >= 63744 && this.input.LA(1) <= 64045 || this.input.LA(1) >= 65288 && this.input.LA(1) <= 65289) {
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

    public final void mCHINESECHAR() throws RecognitionException {
        if (!(this.input.LA(1) >= 19968 && this.input.LA(1) <= 40869 || this.input.LA(1) >= 63744 && this.input.LA(1) <= 64045)) {
            MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
            this.recover((RecognitionException)mse);
            throw mse;
        }
        this.input.consume();
    }

    public final void mDIGIT() throws RecognitionException {
        this.matchRange(48, 57);
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
        int _type = 50;
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
        int _type = 55;
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

    public final void mEQ() throws RecognitionException {
        int _type = 10;
        int _channel = 0;
        int alt16 = 3;
        int LA16_0 = this.input.LA(1);
        if (LA16_0 == 61) {
            int LA16_1 = this.input.LA(2);
            alt16 = LA16_1 == 61 ? 3 : 1;
        } else if (LA16_0 == 58) {
            alt16 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 16, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt16) {
            case 1: {
                this.match(61);
                break;
            }
            case 2: {
                this.match(":=");
                break;
            }
            case 3: {
                this.match("==");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mGT() throws RecognitionException {
        int _type = 11;
        int _channel = 0;
        this.match(62);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mGE() throws RecognitionException {
        int _type = 12;
        int _channel = 0;
        this.match(">=");
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLT() throws RecognitionException {
        int _type = 13;
        int _channel = 0;
        this.match(60);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLE() throws RecognitionException {
        int _type = 14;
        int _channel = 0;
        this.match("<=");
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mNE() throws RecognitionException {
        int _type = 15;
        int _channel = 0;
        int alt17 = 2;
        int LA17_0 = this.input.LA(1);
        if (LA17_0 == 60) {
            alt17 = 1;
        } else if (LA17_0 == 33) {
            alt17 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 17, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt17) {
            case 1: {
                this.match("<>");
                break;
            }
            case 2: {
                this.match("!=");
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mPLUS() throws RecognitionException {
        int _type = 18;
        int _channel = 0;
        this.match(43);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMINUS() throws RecognitionException {
        int _type = 19;
        int _channel = 0;
        this.match(45);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mMULTI() throws RecognitionException {
        int _type = 21;
        int _channel = 0;
        this.match(42);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDIVIDE() throws RecognitionException {
        int _type = 22;
        int _channel = 0;
        this.match(47);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mPOWER() throws RecognitionException {
        int _type = 23;
        int _channel = 0;
        this.match(94);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLINK() throws RecognitionException {
        int _type = 20;
        int _channel = 0;
        this.match(38);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mOPPOSE() throws RecognitionException {
        int _type = 36;
        int _channel = 0;
        this.match(126);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLPAREN() throws RecognitionException {
        int _type = 45;
        int _channel = 0;
        this.match(40);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mRPAREN() throws RecognitionException {
        int _type = 46;
        int _channel = 0;
        this.match(41);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLBRACKET() throws RecognitionException {
        int _type = 32;
        int _channel = 0;
        this.match(91);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mRBRACKET() throws RecognitionException {
        int _type = 34;
        int _channel = 0;
        this.match(93);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mLBRACE() throws RecognitionException {
        int _type = 38;
        int _channel = 0;
        this.match(123);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mRBRACE() throws RecognitionException {
        int _type = 39;
        int _channel = 0;
        this.match(125);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mQUOTE() throws RecognitionException {
        int _type = 56;
        int _channel = 0;
        this.match(39);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDOUBLEQUOTE() throws RecognitionException {
        int _type = 57;
        int _channel = 0;
        this.match(34);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCOLON() throws RecognitionException {
        int _type = 40;
        int _channel = 0;
        this.match(58);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mSEMICOLON() throws RecognitionException {
        int _type = 44;
        int _channel = 0;
        this.match(59);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mCOMMA() throws RecognitionException {
        int _type = 33;
        int _channel = 0;
        this.match(44);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mPOINT() throws RecognitionException {
        int _type = 35;
        int _channel = 0;
        this.match(46);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mPERCENT_SIGN() throws RecognitionException {
        int _type = 52;
        int _channel = 0;
        this.match(37);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mAT() throws RecognitionException {
        int _type = 43;
        int _channel = 0;
        this.match(64);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mDOLLAR() throws RecognitionException {
        int _type = 37;
        int _channel = 0;
        this.match(36);
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mSL_COMMENT() throws RecognitionException {
        int _type = 58;
        int _channel = 0;
        int alt18 = 2;
        int LA18_0 = this.input.LA(1);
        if (LA18_0 == 47) {
            alt18 = 1;
        } else if (LA18_0 == 45) {
            alt18 = 2;
        } else {
            NoViableAltException nvae = new NoViableAltException("", 18, 0, (IntStream)this.input);
            throw nvae;
        }
        switch (alt18) {
            case 1: {
                this.match("//");
                break;
            }
            case 2: {
                this.match("--");
            }
        }
        block13: while (true) {
            int alt19 = 2;
            int LA19_0 = this.input.LA(1);
            if (LA19_0 >= 0 && LA19_0 <= 9 || LA19_0 >= 11 && LA19_0 <= 12 || LA19_0 >= 14 && LA19_0 <= 65535) {
                alt19 = 1;
            }
            switch (alt19) {
                case 1: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        continue block13;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, (IntStream)this.input);
                    this.recover((RecognitionException)mse);
                    throw mse;
                }
            }
            break;
        }
        int alt20 = 2;
        int LA20_0 = this.input.LA(1);
        if (LA20_0 == 13) {
            alt20 = 1;
        }
        switch (alt20) {
            case 1: {
                this.match(13);
            }
        }
        int alt21 = 2;
        int LA21_0 = this.input.LA(1);
        if (LA21_0 == 10) {
            alt21 = 1;
        }
        switch (alt21) {
            case 1: {
                this.match(10);
            }
        }
        _channel = 99;
        this.state.type = _type;
        this.state.channel = _channel;
    }

    public final void mML_COMMENT() throws RecognitionException {
        int _type = 59;
        int _channel = 0;
        this.match("/*");
        block3: while (true) {
            int alt22 = 2;
            int LA22_0 = this.input.LA(1);
            if (LA22_0 == 42) {
                int LA22_1 = this.input.LA(2);
                if (LA22_1 == 47) {
                    alt22 = 2;
                } else if (LA22_1 >= 0 && LA22_1 <= 46 || LA22_1 >= 48 && LA22_1 <= 65535) {
                    alt22 = 1;
                }
            } else if (LA22_0 >= 0 && LA22_0 <= 41 || LA22_0 >= 43 && LA22_0 <= 65535) {
                alt22 = 1;
            }
            switch (alt22) {
                case 1: {
                    this.matchAny();
                    continue block3;
                }
            }
            break;
        }
        this.match("*/");
        _channel = 99;
        this.state.type = _type;
        this.state.channel = _channel;
    }

    @Override
    public void mTokens() throws RecognitionException {
        int alt23 = 58;
        alt23 = this.dfa23.predict((IntStream)this.input);
        switch (alt23) {
            case 1: {
                this.mT__60();
                break;
            }
            case 2: {
                this.mT__61();
                break;
            }
            case 3: {
                this.mT__62();
                break;
            }
            case 4: {
                this.mT__63();
                break;
            }
            case 5: {
                this.mT__64();
                break;
            }
            case 6: {
                this.mAND();
                break;
            }
            case 7: {
                this.mOR();
                break;
            }
            case 8: {
                this.mNOT();
                break;
            }
            case 9: {
                this.mIF();
                break;
            }
            case 10: {
                this.mTHEN();
                break;
            }
            case 11: {
                this.mELSE();
                break;
            }
            case 12: {
                this.mLIKE();
                break;
            }
            case 13: {
                this.mIN();
                break;
            }
            case 14: {
                this.mTRUE();
                break;
            }
            case 15: {
                this.mFALSE();
                break;
            }
            case 16: {
                this.mNULL();
                break;
            }
            case 17: {
                this.mSTRING();
                break;
            }
            case 18: {
                this.mSTRING1();
                break;
            }
            case 19: {
                this.mINT();
                break;
            }
            case 20: {
                this.mFLOAT();
                break;
            }
            case 21: {
                this.mPERCENT();
                break;
            }
            case 22: {
                this.mZB_APPEND();
                break;
            }
            case 23: {
                this.mCHARACTER();
                break;
            }
            case 24: {
                this.mMIXED_CHARACTER_ID_BEGIN_WITH_CHAR();
                break;
            }
            case 25: {
                this.mMIXED_CHARACTER_ID_BEGIN_WITH_DIGIT();
                break;
            }
            case 26: {
                this.mMIXED_CHINESE_CHAR_ID_BEGIN_CHINESE();
                break;
            }
            case 27: {
                this.mESC_SEQ();
                break;
            }
            case 28: {
                this.mWS();
                break;
            }
            case 29: {
                this.mEQ();
                break;
            }
            case 30: {
                this.mGT();
                break;
            }
            case 31: {
                this.mGE();
                break;
            }
            case 32: {
                this.mLT();
                break;
            }
            case 33: {
                this.mLE();
                break;
            }
            case 34: {
                this.mNE();
                break;
            }
            case 35: {
                this.mPLUS();
                break;
            }
            case 36: {
                this.mMINUS();
                break;
            }
            case 37: {
                this.mMULTI();
                break;
            }
            case 38: {
                this.mDIVIDE();
                break;
            }
            case 39: {
                this.mPOWER();
                break;
            }
            case 40: {
                this.mLINK();
                break;
            }
            case 41: {
                this.mOPPOSE();
                break;
            }
            case 42: {
                this.mLPAREN();
                break;
            }
            case 43: {
                this.mRPAREN();
                break;
            }
            case 44: {
                this.mLBRACKET();
                break;
            }
            case 45: {
                this.mRBRACKET();
                break;
            }
            case 46: {
                this.mLBRACE();
                break;
            }
            case 47: {
                this.mRBRACE();
                break;
            }
            case 48: {
                this.mQUOTE();
                break;
            }
            case 49: {
                this.mDOUBLEQUOTE();
                break;
            }
            case 50: {
                this.mCOLON();
                break;
            }
            case 51: {
                this.mSEMICOLON();
                break;
            }
            case 52: {
                this.mCOMMA();
                break;
            }
            case 53: {
                this.mPOINT();
                break;
            }
            case 54: {
                this.mPERCENT_SIGN();
                break;
            }
            case 55: {
                this.mAT();
                break;
            }
            case 56: {
                this.mDOLLAR();
                break;
            }
            case 57: {
                this.mSL_COMMENT();
                break;
            }
            case 58: {
                this.mML_COMMENT();
            }
        }
    }

    static {
        int numStates = DFA23_TRANSITIONS.length;
        DFA23_TRANSITION = new short[numStates][];
        for (int i = 0; i < numStates; ++i) {
            ANTLRLexer.DFA23_TRANSITION[i] = DFA.unpackEncodedString((String)DFA23_TRANSITIONS[i]);
        }
    }

    class DFA23
    extends DFA {
        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_EOT;
            this.eof = DFA23_EOF;
            this.min = DFA23_MIN;
            this.max = DFA23_MAX;
            this.accept = DFA23_ACCEPT;
            this.special = DFA23_SPECIAL;
            this.transition = DFA23_TRANSITION;
        }

        public String getDescription() {
            return "1:1: Tokens : ( T__60 | T__61 | T__62 | T__63 | T__64 | AND | OR | NOT | IF | THEN | ELSE | LIKE | IN | TRUE | FALSE | NULL | STRING | STRING1 | INT | FLOAT | PERCENT | ZB_APPEND | CHARACTER | MIXED_CHARACTER_ID_BEGIN_WITH_CHAR | MIXED_CHARACTER_ID_BEGIN_WITH_DIGIT | MIXED_CHINESE_CHAR_ID_BEGIN_CHINESE | ESC_SEQ | WS | EQ | GT | GE | LT | LE | NE | PLUS | MINUS | MULTI | DIVIDE | POWER | LINK | OPPOSE | LPAREN | RPAREN | LBRACKET | RBRACKET | LBRACE | RBRACE | QUOTE | DOUBLEQUOTE | COLON | SEMICOLON | COMMA | POINT | PERCENT_SIGN | AT | DOLLAR | SL_COMMENT | ML_COMMENT );";
        }

        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
                case 0: {
                    int LA23_17 = input.LA(1);
                    s = -1;
                    s = LA23_17 >= 0 && LA23_17 <= 65535 ? 69 : 70;
                    if (s < 0) break;
                    return s;
                }
                case 1: {
                    int LA23_16 = input.LA(1);
                    s = -1;
                    s = LA23_16 >= 0 && LA23_16 <= 65535 ? 67 : 68;
                    if (s < 0) break;
                    return s;
                }
            }
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 23, _s, input);
            this.error(nvae);
            throw nvae;
        }
    }
}

