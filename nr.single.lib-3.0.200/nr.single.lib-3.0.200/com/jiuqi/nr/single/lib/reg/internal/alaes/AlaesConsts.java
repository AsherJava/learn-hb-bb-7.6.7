/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.lib.reg.internal.alaes;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.lib.reg.exception.SingleSecretException;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESBuffer;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESExpandedKey128;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESExpandedKey192;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESExpandedKey256;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESKey128;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESKey192;
import com.jiuqi.nr.single.lib.reg.internal.alaes.TAESKey256;
import java.io.File;
import java.util.Arrays;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlaesConsts {
    private static final Logger logger = LoggerFactory.getLogger(AlaesConsts.class);
    private static final String INVALID_BUFFER_SIZE = "Invalid buffer size for decryption";
    public static final int[] RCON = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};
    public static final int[] FORWARDTABLE = new int[]{-1520213050, -2072216328, -1720223762, -1921287178, 0xDF2F2FF, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 0xBF0F0FB, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 0x3EE3E3DD, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 0x55333366, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 0x66222244, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 0x18080810, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 0x33111122, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};
    public static final int[] LAST_FORWARDTABLE = new int[]{99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118, 202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192, 183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21, 4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117, 9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132, 83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207, 208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168, 81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210, 205, 12, 19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115, 96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219, 224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121, 231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8, 186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138, 112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158, 225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223, 140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22};
    public static final int[] INVERSETABLE = new int[]{1353184337, 1399144830, -1012656358, -1772214470, -882136261, -247096033, -1420232020, -1828461749, 1442459680, -160598355, -1854485368, 625738485, -52959921, -674551099, -2143013594, -1885117771, 1230680542, 1729870373, -1743852987, -507445667, 41234371, 317738113, -1550367091, -956705941, -413167869, -1784901099, -344298049, -631680363, 763608788, -752782248, 694804553, 1154009486, 1787413109, 2021232372, 1799248025, -579749593, -1236278850, 397248752, 1722556617, -1271214467, 407560035, -2110711067, 1613975959, 1165972322, -529046351, -2068943941, 480281086, -1809118983, 1483229296, 436028815, -2022908268, -1208452270, 601060267, -503166094, 1468997603, 715871590, 120122290, 63092015, -1703164538, -1526188077, -226023376, -1297760477, -1167457534, 1552029421, 723308426, -1833666137, -252573709, -1578997426, -839591323, -708967162, 526529745, -1963022652, -1655493068, -1604979806, 853641733, 1978398372, 971801355, -1427152832, 111112542, 1360031421, -108388034, 1023860118, -1375387939, 1186850381, -1249028975, 90031217, 1876166148, -15380384, 620468249, -1746289194, -868007799, 2006899047, -1119688528, -2004121337, 945494503, -605108103, 1191869601, -384875908, -920746760, 0, -2088337399, 1223502642, -1401941730, 1316117100, -67170563, 1446544655, 517320253, 658058550, 1691946762, 564550760, -783000677, 976107044, -1318647284, 266819475, -761860428, -1634624741, 1338359936, -1574904735, 1766553434, 370807324, 179999714, -450191168, 1138762300, 488053522, 185403662, -1379431438, -1180125651, -928440812, -2061897385, 1275557295, -1143105042, -44007517, -1624899081, -1124765092, -985962940, 880737115, 1982415755, -590994485, 1761406390, 1676797112, -891538985, 277177154, 1076008723, 538035844, 2099530373, -130171950, 288553390, 1839278535, 1261411869, -214912292, -330136051, -790380169, 1813426987, -1715900247, -95906799, 577038663, -997393240, 440397984, -668172970, -275762398, -951170681, -1043253031, -22885748, 906744984, -813566554, 685669029, 646887386, -1530942145, -459458004, 227702864, -1681105046, 1648787028, -1038905866, -390539120, 1593260334, -173030526, -1098883681, 2090061929, -1456614033, -1290656305, 999926984, -1484974064, 1852021992, 2075868123, 158869197, -199730834, 28809964, -1466282109, 1701746150, 2129067946, 147831841, -420997649, -644094022, -835293366, -737566742, -696471511, -1347247055, 824393514, 815048134, -1067015627, 935087732, -1496677636, -1328508704, 366520115, 1251476721, -136647615, 240176511, 804688151, -1915335306, 1303441219, 1414376140, -553347356, -474623586, 461924940, -1205916479, 2136040774, 82468509, 1563790337, 1937016826, 776014843, 1511876531, 1389550482, 861278441, 323475053, -1939744870, 2047648055, -1911228327, -1992551445, -299390514, 902390199, -303751967, 1018251130, 1507840668, 1064563285, 2043548696, -1086863501, -355600557, 1537932639, 342834655, -2032450440, -2114736182, 1053059257, 741614648, 1598071746, 1925389590, 203809468, -1958134744, 1100287487, 1895934009, -558691320, -1662733096, -1866377628, 1636092795, 1890988757, 1952214088, 1113045200};
    public static final int[] LAST_INVERSETABLE = new int[]{82, 9, 106, 213, 48, 54, 165, 56, 191, 64, 163, 158, 129, 243, 215, 251, 124, 227, 57, 130, 155, 47, 255, 135, 52, 142, 67, 68, 196, 222, 233, 203, 84, 123, 148, 50, 166, 194, 35, 61, 238, 76, 149, 11, 66, 250, 195, 78, 8, 46, 161, 102, 40, 217, 36, 178, 118, 91, 162, 73, 109, 139, 209, 37, 114, 248, 246, 100, 134, 104, 152, 22, 212, 164, 92, 204, 93, 101, 182, 146, 108, 112, 72, 80, 253, 237, 185, 218, 94, 21, 70, 87, 167, 141, 157, 132, 144, 216, 171, 0, 140, 188, 211, 10, 247, 228, 88, 5, 184, 179, 69, 6, 208, 44, 30, 143, 202, 63, 15, 2, 193, 175, 189, 3, 1, 19, 138, 107, 58, 145, 17, 65, 79, 103, 220, 234, 151, 242, 207, 206, 240, 180, 230, 115, 150, 172, 116, 34, 231, 173, 53, 133, 226, 249, 55, 232, 28, 117, 223, 110, 71, 241, 26, 113, 29, 41, 197, 137, 111, 183, 98, 14, 170, 24, 190, 27, 252, 86, 62, 75, 198, 210, 121, 32, 154, 219, 192, 254, 120, 205, 90, 244, 31, 221, 168, 51, 136, 7, 199, 49, 177, 18, 16, 89, 39, 128, 236, 95, 96, 81, 127, 169, 25, 181, 74, 13, 45, 229, 122, 159, 147, 201, 156, 239, 160, 224, 59, 77, 174, 42, 245, 176, 200, 235, 187, 60, 131, 83, 153, 97, 23, 43, 4, 126, 186, 119, 214, 38, 225, 105, 20, 99, 85, 33, 12, 125};

    private AlaesConsts() {
        throw new IllegalStateException("Utility class");
    }

    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    private static short gByte(long value) {
        return (short)(value % 256L);
    }

    private static long getNumberFormArray(short[] value, int fromId) {
        long r = 0L;
        for (int i = 0; i < 4; ++i) {
            r += (long)((double)value[fromId + i] * Math.pow(256.0, i));
        }
        return r;
    }

    private static void setNumberFormArray(short[] value, int fromId, long num) {
        long num1 = num;
        for (int i = 3; i >= 0; --i) {
            long aa = (long)Math.pow(256.0, i);
            long bb = num1 / aa;
            value[fromId + i] = i > 0 ? (short)bb : (short)num1;
            num1 -= aa * bb;
        }
    }

    public static void expandAESKeyForEncryption(TAESKey128 key, TAESExpandedKey128 expandedKey) {
        expandedKey.getValue()[0] = AlaesConsts.getNumberFormArray(key.getValue(), 0);
        expandedKey.getValue()[1] = AlaesConsts.getNumberFormArray(key.getValue(), 4);
        expandedKey.getValue()[2] = AlaesConsts.getNumberFormArray(key.getValue(), 8);
        expandedKey.getValue()[3] = AlaesConsts.getNumberFormArray(key.getValue(), 12);
        int i = 0;
        int j = 0;
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        long t = 0L;
        do {
            t = expandedKey.getValue()[i + 3] << 24 | expandedKey.getValue()[i + 3] >> 8;
            w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t)];
            w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 8)];
            w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 16)];
            w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 24)];
            expandedKey.getValue()[i + 4] = expandedKey.getValue()[i] ^ (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8)) ^ (long)RCON[j];
            ++j;
            expandedKey.getValue()[i + 5] = expandedKey.getValue()[i + 1] ^ expandedKey.getValue()[i + 4];
            expandedKey.getValue()[i + 6] = expandedKey.getValue()[i + 2] ^ expandedKey.getValue()[i + 5];
            expandedKey.getValue()[i + 7] = expandedKey.getValue()[i + 3] ^ expandedKey.getValue()[i + 6];
        } while ((i += 4) < 40);
    }

    public static void expandAESKeyForEncryption(TAESKey192 key, TAESExpandedKey192 expandedKey) {
        expandedKey.getValue()[0] = AlaesConsts.getNumberFormArray(key.getValue(), 0);
        expandedKey.getValue()[1] = AlaesConsts.getNumberFormArray(key.getValue(), 4);
        expandedKey.getValue()[2] = AlaesConsts.getNumberFormArray(key.getValue(), 8);
        expandedKey.getValue()[3] = AlaesConsts.getNumberFormArray(key.getValue(), 12);
        expandedKey.getValue()[4] = AlaesConsts.getNumberFormArray(key.getValue(), 16);
        expandedKey.getValue()[5] = AlaesConsts.getNumberFormArray(key.getValue(), 20);
        int i = 0;
        int j = 0;
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        long t = 0L;
        do {
            t = expandedKey.getValue()[i + 5] << 24 | expandedKey.getValue()[i + 5] >> 8;
            w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t)];
            w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 8)];
            w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 16)];
            w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 24)];
            expandedKey.getValue()[i + 6] = expandedKey.getValue()[i] ^ (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8)) ^ (long)RCON[j];
            ++j;
            expandedKey.getValue()[i + 7] = expandedKey.getValue()[i + 1] ^ expandedKey.getValue()[i + 6];
            expandedKey.getValue()[i + 8] = expandedKey.getValue()[i + 2] ^ expandedKey.getValue()[i + 7];
            expandedKey.getValue()[i + 9] = expandedKey.getValue()[i + 3] ^ expandedKey.getValue()[i + 8];
            expandedKey.getValue()[i + 10] = expandedKey.getValue()[i + 4] ^ expandedKey.getValue()[i + 9];
            expandedKey.getValue()[i + 11] = expandedKey.getValue()[i + 5] ^ expandedKey.getValue()[i + 10];
        } while ((i += 6) < 46);
    }

    public static void expandAESKeyForEncryption(TAESKey256 key, TAESExpandedKey256 expandedKey) {
        expandedKey.getValue()[0] = AlaesConsts.getNumberFormArray(key.getValue(), 0);
        expandedKey.getValue()[1] = AlaesConsts.getNumberFormArray(key.getValue(), 4);
        expandedKey.getValue()[2] = AlaesConsts.getNumberFormArray(key.getValue(), 8);
        expandedKey.getValue()[3] = AlaesConsts.getNumberFormArray(key.getValue(), 12);
        expandedKey.getValue()[4] = AlaesConsts.getNumberFormArray(key.getValue(), 16);
        expandedKey.getValue()[5] = AlaesConsts.getNumberFormArray(key.getValue(), 20);
        expandedKey.getValue()[6] = AlaesConsts.getNumberFormArray(key.getValue(), 24);
        expandedKey.getValue()[7] = AlaesConsts.getNumberFormArray(key.getValue(), 28);
        int i = 0;
        int j = 0;
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        long t = 0L;
        do {
            t = expandedKey.getValue()[i + 7] << 24 | expandedKey.getValue()[i + 7] >> 8;
            w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t)];
            w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 8)];
            w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 16)];
            w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t >> 24)];
            expandedKey.getValue()[i + 8] = expandedKey.getValue()[i] ^ (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8)) ^ (long)RCON[j];
            ++j;
            expandedKey.getValue()[i + 9] = expandedKey.getValue()[i + 1] ^ expandedKey.getValue()[i + 8];
            expandedKey.getValue()[i + 10] = expandedKey.getValue()[i + 2] ^ expandedKey.getValue()[i + 9];
            expandedKey.getValue()[i + 11] = expandedKey.getValue()[i + 3] ^ expandedKey.getValue()[i + 10];
            w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(expandedKey.getValue()[i + 11])];
            w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(expandedKey.getValue()[i + 11] >> 8)];
            w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(expandedKey.getValue()[i + 11] >> 16)];
            w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(expandedKey.getValue()[i + 11] >> 24)];
            expandedKey.getValue()[i + 12] = expandedKey.getValue()[i + 4] ^ (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8)) ^ (long)RCON[j];
            expandedKey.getValue()[i + 13] = expandedKey.getValue()[i + 5] ^ expandedKey.getValue()[i + 12];
            expandedKey.getValue()[i + 14] = expandedKey.getValue()[i + 6] ^ expandedKey.getValue()[i + 13];
            expandedKey.getValue()[i + 15] = expandedKey.getValue()[i + 7] ^ expandedKey.getValue()[i + 14];
        } while ((i += 8) < 52);
    }

    public static void encryptAES(TAESBuffer inBuf, TAESExpandedKey128 key, TAESBuffer outBuf) {
        long[] t0 = new long[4];
        long[] t1 = new long[4];
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        t0[0] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 0) ^ key.getValue()[0];
        t0[1] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 4) ^ key.getValue()[1];
        t0[2] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 8) ^ key.getValue()[2];
        t0[3] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 12) ^ key.getValue()[3];
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[4]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[5]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[6]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[7]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[8]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[9]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[10]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[11]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[12]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[13]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[14]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[15]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[16]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[17]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[18]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[19]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[20]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[21]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[22]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[23]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[24]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[25]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[26]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[27]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[28]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[29]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[30]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[31]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[32]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[33]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[34]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[35]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[36]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[37]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[38]) & 0xFFFFFFFFL;
        w0 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[39]) & 0xFFFFFFFFL;
        w0 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[40]) & 0xFFFFFFFFL;
        w0 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[41]) & 0xFFFFFFFFL;
        w0 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[42]) & 0xFFFFFFFFL;
        w0 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[43]) & 0xFFFFFFFFL;
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 0, t0[0]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 4, t0[1]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 8, t0[2]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 12, t0[3]);
    }

    public static void encryptAES(TAESBuffer inBuf, TAESExpandedKey192 key, TAESBuffer outBuf) {
        long[] t0 = new long[4];
        long[] t1 = new long[4];
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        t0[0] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 0) ^ key.getValue()[0];
        t0[1] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 4) ^ key.getValue()[1];
        t0[2] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 8) ^ key.getValue()[2];
        t0[3] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 12) ^ key.getValue()[3];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[4];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[5];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[6];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[7];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[8];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[9];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[10];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[11];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[12];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[13];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[14];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[15];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[16];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[17];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[18];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[19];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[20];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[21];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[22];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[23];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[24];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[25];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[26];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[27];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[28];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[29];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[30];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[31];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[32];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[33];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[34];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[35];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[36];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[37];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[38];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[39];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[40];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[41];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[42];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[43];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[44];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[45];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[46];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[47];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[48];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[49];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[50];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[51];
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 0, t0[0]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 4, t0[1]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 8, t0[2]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 12, t0[3]);
    }

    public static void encryptAES(TAESBuffer inBuf, TAESExpandedKey256 key, TAESBuffer outBuf) {
        long[] t0 = new long[4];
        long[] t1 = new long[4];
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        t0[0] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 0) ^ key.getValue()[0];
        t0[1] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 4) ^ key.getValue()[1];
        t0[2] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 8) ^ key.getValue()[2];
        t0[3] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 12) ^ key.getValue()[3];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[4];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[5];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[6];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[7];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[8];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[9];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[10];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[11];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[12];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[13];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[14];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[15];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[16];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[17];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[18];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[19];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[20];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[21];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[22];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[23];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[24];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[25];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[26];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[27];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[28];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[29];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[30];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[31];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[32];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[33];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[34];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[35];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[36];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[37];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[38];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[39];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[40];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[41];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[42];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[43];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[44];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[45];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[46];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[47];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[48];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[49];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[50];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[51];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[0])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[52];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[1])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[53];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[2])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[54];
        w0 = FORWARDTABLE[AlaesConsts.gByte(t0[3])];
        w1 = FORWARDTABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = FORWARDTABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = FORWARDTABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[55];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[56];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[57];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[58];
        w0 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[3])];
        w1 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = LAST_FORWARDTABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[59];
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 0, t0[0]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 4, t0[1]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 8, t0[2]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 12, t0[3]);
    }

    public static void expandAESKeyForDecryption(TAESExpandedKey128 expandedKey) {
        long u = 0L;
        long f2 = 0L;
        long f4 = 0L;
        long f8 = 0L;
        long f9 = 0L;
        for (int I = 1; I <= 9; ++I) {
            f9 = expandedKey.getValue()[I * 4];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 1];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 1] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 2];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 2] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 3];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 3] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
        }
    }

    public static void expandAESKeyForDecryption(TAESKey128 key, TAESExpandedKey128 expandedKey) {
        AlaesConsts.expandAESKeyForEncryption(key, expandedKey);
        AlaesConsts.expandAESKeyForDecryption(expandedKey);
    }

    public static void expandAESKeyForDecryption(TAESExpandedKey192 expandedKey) {
        long u = 0L;
        long f2 = 0L;
        long f4 = 0L;
        long f8 = 0L;
        long f9 = 0L;
        for (int I = 1; I <= 11; ++I) {
            f9 = expandedKey.getValue()[I * 4];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 1];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 1] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 2];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 2] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 3];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 3] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
        }
    }

    public static void expandAESKeyForDecryption(TAESKey192 key, TAESExpandedKey192 expandedKey) {
        AlaesConsts.expandAESKeyForEncryption(key, expandedKey);
        AlaesConsts.expandAESKeyForDecryption(expandedKey);
    }

    public static void expandAESKeyForDecryption(TAESExpandedKey256 expandedKey) {
        long u = 0L;
        long f2 = 0L;
        long f4 = 0L;
        long f8 = 0L;
        long f9 = 0L;
        for (int I = 1; I <= 13; ++I) {
            f9 = expandedKey.getValue()[I * 4];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 1];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 1] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 2];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 2] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
            f9 = expandedKey.getValue()[I * 4 + 3];
            u = f9 & 0xFFFFFFFF80808080L;
            f2 = (f9 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f2 & 0xFFFFFFFF80808080L;
            f4 = (f2 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            u = f4 & 0xFFFFFFFF80808080L;
            f8 = (f4 & 0x7F7F7F7FL) << 1 ^ u - (u >> 7) & 0x1B1B1B1BL;
            expandedKey.getValue()[I * 4 + 3] = (f2 ^ f4 ^ f8 ^ ((f2 ^ (f9 ^= f8)) << 24 | (f2 ^ f9) >> 8) ^ ((f4 ^ f9) << 16 | (f4 ^ f9) >> 16) ^ (f9 << 8 | f9 >> 24)) & 0xFFFFFFFFL;
        }
    }

    public static void expandAESKeyForDecryption(TAESKey256 key, TAESExpandedKey256 expandedKey) {
        AlaesConsts.expandAESKeyForEncryption(key, expandedKey);
        AlaesConsts.expandAESKeyForDecryption(expandedKey);
    }

    public static void decryptAES(TAESBuffer inBuf, TAESExpandedKey128 key, TAESBuffer outBuf) {
        long[] t0 = new long[4];
        long[] t1 = new long[4];
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        t0[0] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 0) ^ key.getValue()[40];
        t0[1] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 4) ^ key.getValue()[41];
        t0[2] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 8) ^ key.getValue()[42];
        t0[3] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 12) ^ key.getValue()[43];
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[36]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[37]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[38]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[39]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[32]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[33]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[34]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[35]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[28]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[29]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[30]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[31]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[24]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[25]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[26]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[27]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[20]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[21]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[22]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[23]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[16]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[17]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[18]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[19]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[12]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[13]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[14]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[15]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[8]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[9]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[10]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[11]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)] & 0xFFFFFFFFL;
        t1[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[4]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)] & 0xFFFFFFFFL;
        t1[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[5]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)] & 0xFFFFFFFFL;
        t1[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[6]) & 0xFFFFFFFFL;
        w0 = (long)INVERSETABLE[AlaesConsts.gByte(t0[3])] & 0xFFFFFFFFL;
        w1 = (long)INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)] & 0xFFFFFFFFL;
        t1[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[7]) & 0xFFFFFFFFL;
        w0 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[0])] & 0xFFFFFFFFL;
        w1 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)] & 0xFFFFFFFFL;
        t0[0] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[0]) & 0xFFFFFFFFL;
        w0 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[1])] & 0xFFFFFFFFL;
        w1 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)] & 0xFFFFFFFFL;
        t0[1] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[1]) & 0xFFFFFFFFL;
        w0 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[2])] & 0xFFFFFFFFL;
        w1 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)] & 0xFFFFFFFFL;
        t0[2] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[2]) & 0xFFFFFFFFL;
        w0 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[3])] & 0xFFFFFFFFL;
        w1 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)] & 0xFFFFFFFFL;
        w2 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)] & 0xFFFFFFFFL;
        w3 = (long)LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)] & 0xFFFFFFFFL;
        t0[3] = (w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[3]) & 0xFFFFFFFFL;
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 0, t0[0]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 4, t0[1]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 8, t0[2]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 12, t0[3]);
    }

    public static void decryptAES(TAESBuffer inBuf, TAESExpandedKey192 key, TAESBuffer outBuf) {
        long[] t0 = new long[4];
        long[] t1 = new long[4];
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        t0[0] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 0) ^ key.getValue()[48];
        t0[1] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 4) ^ key.getValue()[49];
        t0[2] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 8) ^ key.getValue()[50];
        t0[3] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 12) ^ key.getValue()[51];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[44];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[45];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[46];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[47];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[40];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[41];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[42];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[43];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[36];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[37];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[38];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[39];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[32];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[33];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[34];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[35];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[28];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[29];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[30];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[31];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[24];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[25];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[26];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[27];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[20];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[21];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[22];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[23];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[16];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[17];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[18];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[19];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[12];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[13];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[14];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[15];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[8];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[9];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[10];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[11];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[4];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[5];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[6];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[7];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[0];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[1];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[2];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[3];
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 0, t0[0]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 4, t0[1]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 8, t0[2]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 12, t0[3]);
    }

    public static void decryptAES(TAESBuffer inBuf, TAESExpandedKey256 key, TAESBuffer outBuf) {
        long[] t0 = new long[4];
        long[] t1 = new long[4];
        long w0 = 0L;
        long w1 = 0L;
        long w2 = 0L;
        long w3 = 0L;
        t0[0] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 0) ^ key.getValue()[56];
        t0[1] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 4) ^ key.getValue()[57];
        t0[2] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 8) ^ key.getValue()[58];
        t0[3] = AlaesConsts.getNumberFormArray(inBuf.getValue(), 12) ^ key.getValue()[59];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[52];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[53];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[54];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[55];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[48];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[49];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[50];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[51];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[44];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[45];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[46];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[47];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[40];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[41];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[42];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[43];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[36];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[37];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[38];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[39];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[32];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[33];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[34];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[35];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[28];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[29];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[30];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[31];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[24];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[25];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[26];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[27];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[20];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[21];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[22];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[23];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[16];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[17];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[18];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[19];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[12];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[13];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[14];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[15];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[8];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[9];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[10];
        w0 = INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[11];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[0])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 24)];
        t1[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[4];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[1])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 24)];
        t1[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[5];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[2])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[3] >> 24)];
        t1[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[6];
        w0 = INVERSETABLE[AlaesConsts.gByte(t0[3])];
        w1 = INVERSETABLE[AlaesConsts.gByte(t0[2] >> 8)];
        w2 = INVERSETABLE[AlaesConsts.gByte(t0[1] >> 16)];
        w3 = INVERSETABLE[AlaesConsts.gByte(t0[0] >> 24)];
        t1[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[7];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 24)];
        t0[0] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[0];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 24)];
        t0[1] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[1];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3] >> 24)];
        t0[2] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[2];
        w0 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[3])];
        w1 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[2] >> 8)];
        w2 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[1] >> 16)];
        w3 = LAST_INVERSETABLE[AlaesConsts.gByte(t1[0] >> 24)];
        t0[3] = w0 ^ (w1 << 8 | w1 >> 24) ^ (w2 << 16 | w2 >> 16) ^ (w3 << 24 | w3 >> 8) ^ key.getValue()[3];
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 0, t0[0]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 4, t0[1]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 8, t0[2]);
        AlaesConsts.setNumberFormArray(outBuf.getValue(), 12, t0[3]);
    }

    public static void encryptAESStreamECB(MemStream source, int count, TAESKey128 key, MemStream dest) throws SingleSecretException {
        TAESExpandedKey128 expandedKey = new TAESExpandedKey128();
        AlaesConsts.expandAESKeyForEncryption(key, expandedKey);
        AlaesConsts.encryptAESStreamECB(source, count, expandedKey, dest);
    }

    public static void encryptAESStreamECB(MemStream source, int count, TAESKey192 key, MemStream dest) throws SingleSecretException {
        TAESExpandedKey192 expandedKey = new TAESExpandedKey192();
        AlaesConsts.expandAESKeyForEncryption(key, expandedKey);
        AlaesConsts.encryptAESStreamECB(source, count, expandedKey, dest);
    }

    public static void encryptAESFile(String sourceFileName, String key, String destFileName) {
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.loadFromFile(sourceFileName);
            int size = (int)source.getSize();
            dest.writeInt(size);
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            AlaesConsts.encryptAESStreamECB(source, 0, aKey, dest);
            dest.seek(0L, 0);
            dest.saveToFile(destFileName);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void encryptAESFileEx(String fileName, String key) {
        File file2;
        String dest = "";
        dest = FilenameUtils.removeExtension(fileName) + ".$$$";
        AlaesConsts.encryptAESFile(fileName, key, dest);
        File file1 = new File(FilenameUtils.normalize(fileName));
        if (file1.exists()) {
            file1.deleteOnExit();
        }
        if ((file2 = new File(FilenameUtils.normalize(dest))).exists() && !file2.renameTo(file1)) {
            logger.info("\u91cd\u547d\u540d\u5931\u8d25\uff1a" + dest);
        }
    }

    public static void decryptAESFileEx(String fileName, String key) {
        File file2;
        String dest = "";
        dest = FilenameUtils.removeExtension(fileName) + ".$$$";
        AlaesConsts.decryptAESFile(fileName, key, dest);
        File file1 = new File(FilenameUtils.normalize(fileName));
        if (file1.exists()) {
            file1.deleteOnExit();
        }
        if ((file2 = new File(FilenameUtils.normalize(dest))).exists() && !file2.renameTo(file1)) {
            logger.info("\u91cd\u547d\u540d\u5931\u8d25\uff1a" + dest);
        }
    }

    private static String intToHex(int value, int digits) {
        return String.format("%0" + digits + "d", value);
    }

    private static String stringToHex(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            sb.append(AlaesConsts.intToHex(s.charAt(i), 2));
        }
        return sb.toString();
    }

    private static byte[] hexToByte(String S) {
        MemStream mem = new MemStream();
        try {
            for (int i = 0; i < S.length(); ++i) {
                if (i % 2 != 0) continue;
                mem.write((byte)Integer.parseInt(S.substring(i, i + 2), 16));
            }
            return mem.getBytes();
        }
        catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        catch (StreamException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static String getEndString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length() && s.charAt(i) != '\u0000'; ++i) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static void decryptAESFile(String sourceFileName, String key, String destFileName) {
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.loadFromFile(sourceFileName);
            source.seek(0L, 0);
            source.readInt();
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            AlaesConsts.decryptAESStreamECB(source, (int)(source.getSize() - source.getPosition()), aKey, dest);
            dest.seek(0L, 0);
            dest.saveToFile(destFileName);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static String encryptAESString(String sourceString, String key, boolean hex) {
        String destString = "";
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.writeString(sourceString);
            int size = (int)source.getSize();
            dest.writeInt(size);
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            AlaesConsts.encryptAESStreamECB(source, 0, aKey, dest);
            dest.seek(0L, 0);
            String code = dest.readString((int)dest.getSize());
            destString = hex ? AlaesConsts.stringToHex(code) : code;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destString;
    }

    public static byte[] encryptAESStringToBytes(String sourceString, String key, boolean hex) {
        byte[] destData = null;
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.writeString(sourceString);
            int size = (int)source.getSize();
            dest.writeInt(size);
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            AlaesConsts.encryptAESStreamECB(source, 0, aKey, dest);
            destData = new byte[(int)dest.getSize()];
            dest.seek(0L, 0);
            dest.readBuffer(destData, 0, destData.length);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destData;
    }

    public static String decryptAESString(String sourceString, int size, String key, boolean hex) {
        String destString = "";
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            if (hex) {
                byte[] aa = AlaesConsts.hexToByte(sourceString);
                if (aa != null) {
                    source.writeBuffer(aa, 0, aa.length);
                }
            } else {
                source.writeString(sourceString);
                source.write((byte)0);
            }
            source.seek(0L, 0);
            int size2 = source.readInt();
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            AlaesConsts.decryptAESStreamECB(source, (int)(source.getSize() - source.getPosition()), aKey, dest);
            dest.seek(0L, 0);
            String code = dest.readString((int)dest.getSize());
            destString = AlaesConsts.getEndString(code);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destString;
    }

    public static String decryptAESString(byte[] sourceData, String key, boolean hex) {
        String destString = "";
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.write(sourceData, 0, sourceData.length);
            source.seek(0L, 0);
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            dest.seek(0L, 0);
            AlaesConsts.decryptAESStreamECB(source, (int)(source.getSize() - source.getPosition()), aKey, dest);
            dest.seek(0L, 0);
            String code = dest.readString((int)dest.getSize());
            destString = AlaesConsts.getEndString(code);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destString;
    }

    public static String decryptAESString2(byte[] sourceData, String key, boolean hex) {
        String destString = "";
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.write(sourceData, 0, sourceData.length);
            source.seek(0L, 0);
            source.readInt();
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            dest.seek(0L, 0);
            AlaesConsts.decryptAESStreamECB(source, (int)(source.getSize() - source.getPosition()), aKey, dest);
            dest.seek(0L, 0);
            String code = dest.readString((int)dest.getSize());
            destString = AlaesConsts.getEndString(code);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destString;
    }

    public static byte[] decryptAESStringToBytes(byte[] sourceData, String key, boolean hex) {
        byte[] destData = null;
        MemStream source = new MemStream();
        MemStream dest = new MemStream();
        try {
            source.write(sourceData, 0, sourceData.length);
            source.seek(0L, 0);
            TAESKey128 aKey = new TAESKey128();
            Arrays.fill(aKey.getValue(), (short)0);
            int len = AlaesConsts.min(aKey.getValue().length, key.length());
            for (int i = 0; i < len; ++i) {
                aKey.getValue()[i] = (short)key.charAt(i);
            }
            dest.seek(0L, 0);
            AlaesConsts.decryptAESStreamECB(source, (int)(source.getSize() - source.getPosition()), aKey, dest);
            destData = new byte[(int)dest.getSize()];
            dest.seek(0L, 0);
            dest.readBuffer(destData, 0, destData.length);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destData;
    }

    public static String decryptAESStringFromFile(String sourceFileName, String key, boolean hex) {
        String destString = "";
        MemStream source = new MemStream();
        try {
            source.loadFromFile(sourceFileName);
            source.seek(0L, 0);
            int size = source.readInt();
            String s = source.readString((int)source.getSize());
            destString = AlaesConsts.decryptAESString(s, size, key, hex);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destString;
    }

    public static void encryptAESStreamECB(MemStream source, int count, TAESKey256 key, MemStream dest) throws SingleSecretException {
        TAESExpandedKey256 expandedKey = new TAESExpandedKey256();
        AlaesConsts.expandAESKeyForEncryption(key, expandedKey);
        AlaesConsts.encryptAESStreamECB(source, count, expandedKey, dest);
    }

    public static void encryptAESStreamECB(MemStream source, int count, TAESExpandedKey128 expandedKey, MemStream dest) throws SingleSecretException {
        try {
            byte b;
            short s;
            short s2;
            byte b2;
            int i;
            if (count == 0) {
                source.seek(0L, 0);
                count = (int)source.getSize();
            } else {
                count = AlaesConsts.min(count, (int)(source.getSize() - source.getPosition()));
            }
            if (count == 0) {
                return;
            }
            TAESBuffer tempIn = new TAESBuffer();
            TAESBuffer tempOut = new TAESBuffer();
            while (count >= 16) {
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    b2 = source.read();
                    s2 = b2;
                    if (s2 < 0) {
                        s2 = (short)(s2 + 256);
                    }
                    tempIn.getValue()[i] = s2;
                }
                AlaesConsts.encryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    s = (short)(tempOut.getValue()[i] % 256);
                    b = 0;
                    b = s >= 0 && s <= 127 ? (byte)s : (byte)(s - 256);
                    dest.write(b);
                }
                count -= 16;
            }
            if (count > 0) {
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    if (count >= 1) {
                        b2 = source.read();
                        s2 = b2;
                        if (s2 < 0) {
                            s2 = (short)(s2 + 256);
                        }
                        tempIn.getValue()[i] = s2;
                        --count;
                        continue;
                    }
                    tempIn.getValue()[i] = 0;
                }
                AlaesConsts.encryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    s = (short)(tempOut.getValue()[i] % 256);
                    b = 0;
                    b = s >= 0 && s <= 127 ? (byte)s : (byte)(s - 256);
                    dest.write(b);
                }
            }
        }
        catch (Exception ex) {
            throw new SingleSecretException(ex.getMessage(), ex);
        }
    }

    public static void encryptAESStreamECB(MemStream source, int count, TAESExpandedKey192 expandedKey, MemStream dest) throws SingleSecretException {
        try {
            int i;
            if (count == 0) {
                source.seek(0L, 0);
                count = (int)source.getSize();
            } else {
                count = AlaesConsts.min(count, (int)(source.getSize() - source.getPosition()));
            }
            if (count == 0) {
                return;
            }
            TAESBuffer tempIn = new TAESBuffer();
            TAESBuffer tempOut = new TAESBuffer();
            while (count >= 32) {
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    tempIn.getValue()[i] = source.readShort();
                }
                AlaesConsts.encryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    dest.writeShort(tempOut.getValue()[i]);
                }
                count -= 32;
            }
            if (count > 0) {
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    if (count >= 2) {
                        tempIn.getValue()[i] = source.readShort();
                        count -= 2;
                        continue;
                    }
                    tempIn.getValue()[i] = 0;
                }
                AlaesConsts.encryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    dest.writeShort(tempIn.getValue()[i]);
                }
            }
        }
        catch (Exception ex) {
            throw new SingleSecretException(ex.getMessage(), ex);
        }
    }

    public static void encryptAESStreamECB(MemStream source, int count, TAESExpandedKey256 expandedKey, MemStream dest) throws SingleSecretException {
        try {
            int i;
            if (count == 0) {
                source.seek(0L, 0);
                count = (int)source.getSize();
            } else {
                count = AlaesConsts.min(count, (int)(source.getSize() - source.getPosition()));
            }
            if (count == 0) {
                return;
            }
            TAESBuffer tempIn = new TAESBuffer();
            TAESBuffer tempOut = new TAESBuffer();
            while (count >= 32) {
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    tempIn.getValue()[i] = source.readShort();
                }
                AlaesConsts.encryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    dest.writeShort(tempOut.getValue()[i]);
                }
                count -= 32;
            }
            if (count > 0) {
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    if (count >= 2) {
                        tempIn.getValue()[i] = source.readShort();
                        count -= 2;
                        continue;
                    }
                    tempIn.getValue()[i] = 0;
                }
                AlaesConsts.encryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    dest.writeShort(tempOut.getValue()[i]);
                }
            }
        }
        catch (Exception ex) {
            throw new SingleSecretException(ex.getMessage(), ex);
        }
    }

    public static void decryptAESStreamECB(MemStream source, int count, TAESKey128 key, MemStream dest) throws SingleSecretException {
        TAESExpandedKey128 expandedKey = new TAESExpandedKey128();
        AlaesConsts.expandAESKeyForDecryption(key, expandedKey);
        AlaesConsts.decryptAESStreamECB(source, count, expandedKey, dest);
    }

    public static void decryptAESStreamECB(MemStream source, int count, TAESExpandedKey128 expandedKey, MemStream dest) throws SingleSecretException {
        try {
            if (count == 0) {
                source.seek(0L, 0);
                count = (int)source.getSize();
            } else {
                count = AlaesConsts.min(count, (int)(source.getSize() - source.getPosition()));
            }
            if (count == 0) {
                return;
            }
            if (count % 16 > 0) {
                throw new SingleSecretException(INVALID_BUFFER_SIZE);
            }
            TAESBuffer tempIn = new TAESBuffer();
            TAESBuffer tempOut = new TAESBuffer();
            while (count >= 1) {
                int i;
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    byte b = source.read();
                    short s = b;
                    if (s < 0) {
                        s = (short)(s + 256);
                    }
                    tempIn.getValue()[i] = s;
                }
                AlaesConsts.decryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    short s = (short)(tempOut.getValue()[i] % 256);
                    byte b = 0;
                    b = s >= 0 && s <= 127 ? (byte)s : (byte)(s - 256);
                    dest.write(b);
                }
                count -= 16;
            }
        }
        catch (Exception ex) {
            throw new SingleSecretException(ex.getMessage(), ex);
        }
    }

    public static void decryptAESStreamECB(MemStream source, int count, TAESKey192 key, MemStream dest) throws SingleSecretException {
        TAESExpandedKey192 expandedKey = new TAESExpandedKey192();
        AlaesConsts.expandAESKeyForDecryption(key, expandedKey);
        AlaesConsts.decryptAESStreamECB(source, count, expandedKey, dest);
    }

    public static void decryptAESStreamECB(MemStream source, int count, TAESExpandedKey192 expandedKey, MemStream dest) throws SingleSecretException {
        try {
            if (count == 0) {
                source.seek(0L, 0);
                count = (int)source.getSize();
            } else {
                count = AlaesConsts.min(count, (int)(source.getSize() - source.getPosition()));
            }
            if (count == 0) {
                return;
            }
            if (count % 32 > 0) {
                throw new SingleSecretException(INVALID_BUFFER_SIZE);
            }
            TAESBuffer tempIn = new TAESBuffer();
            TAESBuffer tempOut = new TAESBuffer();
            while (count >= 32) {
                int i;
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    tempIn.getValue()[i] = source.readShort();
                }
                AlaesConsts.decryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    dest.writeShort(tempOut.getValue()[i]);
                }
                count -= 32;
            }
        }
        catch (Exception ex) {
            throw new SingleSecretException(ex.getMessage(), ex);
        }
    }

    public static void decryptAESStreamECB(MemStream source, int count, TAESKey256 key, MemStream dest) throws SingleSecretException {
        TAESExpandedKey256 expandedKey = new TAESExpandedKey256();
        AlaesConsts.expandAESKeyForDecryption(key, expandedKey);
        AlaesConsts.decryptAESStreamECB(source, count, expandedKey, dest);
    }

    public static void decryptAESStreamECB(MemStream source, int count, TAESExpandedKey256 expandedKey, MemStream dest) throws SingleSecretException {
        try {
            if (count == 0) {
                source.seek(0L, 0);
                count = (int)source.getSize();
            } else {
                count = AlaesConsts.min(count, (int)(source.getSize() - source.getPosition()));
            }
            if (count == 0) {
                return;
            }
            if (count % 32 > 0) {
                throw new SingleSecretException(INVALID_BUFFER_SIZE);
            }
            TAESBuffer tempIn = new TAESBuffer();
            TAESBuffer tempOut = new TAESBuffer();
            while (count >= 32) {
                int i;
                for (i = 0; i < tempIn.getValue().length; ++i) {
                    tempIn.getValue()[i] = source.readShort();
                }
                AlaesConsts.decryptAES(tempIn, expandedKey, tempOut);
                for (i = 0; i < tempOut.getValue().length; ++i) {
                    dest.writeShort(tempOut.getValue()[i]);
                }
                count -= 32;
            }
        }
        catch (Exception ex) {
            throw new SingleSecretException(ex.getMessage(), ex);
        }
    }
}

