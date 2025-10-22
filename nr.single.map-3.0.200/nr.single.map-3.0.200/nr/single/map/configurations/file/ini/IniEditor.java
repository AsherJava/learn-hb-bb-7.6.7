/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class IniEditor {
    private static boolean DEFAULT_CASE_SENSITIVITY = false;
    private Map<String, Section> sections = new HashMap<String, Section>();
    private List<String> sectionOrder = new LinkedList<String>();
    private String commonName;
    private char[] commentDelims;
    private boolean isCaseSensitive;
    private OptionFormat optionFormat;
    private static final Line BLANK_LINE = new Line(){

        @Override
        public String toString() {
            return "";
        }
    };

    public IniEditor() {
        this(null, null);
    }

    public IniEditor(boolean isCaseSensitive) {
        this(null, null, isCaseSensitive);
    }

    public IniEditor(String commonName) {
        this(commonName, null);
    }

    public IniEditor(String commonName, boolean isCaseSensitive) {
        this(commonName, null, isCaseSensitive);
    }

    public IniEditor(char[] delims) {
        this(null, delims);
    }

    public IniEditor(char[] delims, boolean isCaseSensitive) {
        this(null, delims, isCaseSensitive);
    }

    public IniEditor(String commonName, char[] delims) {
        this(commonName, delims, DEFAULT_CASE_SENSITIVITY);
    }

    public IniEditor(String commonName, char[] delims, boolean isCaseSensitive) {
        this.isCaseSensitive = isCaseSensitive;
        if (commonName != null) {
            this.commonName = commonName;
            this.addSection(this.commonName);
        }
        this.commentDelims = delims;
        this.optionFormat = new OptionFormat("%s %s %s");
    }

    public void setOptionFormatString(String formatString) {
        this.optionFormat = new OptionFormat(formatString);
    }

    public String get(String section, String option) {
        if (this.hasSection(section)) {
            Section sect = this.getSection(section);
            if (sect.hasOption(option)) {
                return sect.get(option);
            }
            if (this.commonName != null) {
                return this.getSection(this.commonName).get(option);
            }
        }
        return null;
    }

    public void set(String section, String option, String value) {
        if (!this.hasSection(section)) {
            throw new NoSuchSectionException(section);
        }
        this.getSection(section).set(option, value);
    }

    public boolean remove(String section, String option) {
        if (this.hasSection(section)) {
            return this.getSection(section).remove(option);
        }
        throw new NoSuchSectionException(section);
    }

    public boolean hasOption(String section, String option) {
        return this.hasSection(section) && this.getSection(section).hasOption(option);
    }

    public boolean hasSection(String name) {
        return this.sections.containsKey(this.normSection(name));
    }

    public boolean addSection(String name) {
        String normName = this.normSection(name);
        if (!this.hasSection(normName)) {
            Section section = new Section(normName, this.commentDelims, this.isCaseSensitive);
            section.setOptionFormat(this.optionFormat);
            this.sections.put(normName, section);
            this.sectionOrder.add(normName);
            return true;
        }
        return false;
    }

    public boolean removeSection(String name) {
        String normName = this.normSection(name);
        if (this.commonName != null && this.commonName.equals(normName)) {
            throw new IllegalArgumentException("Can't remove common section");
        }
        if (this.hasSection(normName)) {
            this.sections.remove(normName);
            this.sectionOrder.remove(normName);
            return true;
        }
        return false;
    }

    public List<String> sectionNames() {
        ArrayList<String> sectList = new ArrayList<String>(this.sectionOrder);
        if (this.commonName != null) {
            sectList.remove(this.commonName);
        }
        return sectList;
    }

    public List<String> optionNames(String section) {
        if (this.hasSection(section)) {
            return this.getSection(section).optionNames();
        }
        throw new NoSuchSectionException(section);
    }

    public void addComment(String section, String comment) {
        if (!this.hasSection(section)) {
            throw new NoSuchSectionException(section);
        }
        this.getSection(section).addComment(comment);
    }

    public void addBlankLine(String section) {
        if (!this.hasSection(section)) {
            throw new NoSuchSectionException(section);
        }
        this.getSection(section).addBlankLine();
    }

    public void save(String filename) throws IOException {
        this.save(new File(filename));
    }

    public void save(File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file);){
            this.save(out);
            ((OutputStream)out).close();
        }
    }

    public void save(OutputStream stream) throws IOException {
        this.save(new OutputStreamWriter(stream));
    }

    public void save(OutputStreamWriter streamWriter) throws IOException {
        Iterator<String> it = this.sectionOrder.iterator();
        PrintWriter writer = new PrintWriter(streamWriter, true);
        while (it.hasNext()) {
            Section sect = this.getSection(it.next());
            writer.println(sect.header());
            sect.save(writer);
        }
    }

    public void load(String filename) throws IOException {
        this.load(new File(filename));
    }

    public void load(File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file);){
            this.load(in);
        }
    }

    public void load(InputStream stream) throws IOException {
        this.load(new InputStreamReader(stream));
    }

    public void load(InputStreamReader streamReader) throws IOException {
        BufferedReader reader = new BufferedReader(streamReader);
        String curSection = null;
        String line = null;
        while (reader.ready()) {
            int endIndex;
            line = reader.readLine().trim();
            if (line.length() > 0 && line.charAt(0) == '[' && (endIndex = line.indexOf(93)) >= 0) {
                curSection = line.substring(1, endIndex);
                this.addSection(curSection);
            }
            if (curSection == null) continue;
            Section sect = this.getSection(curSection);
            sect.load(reader);
        }
    }

    private Section getSection(String name) {
        return this.sections.get(this.normSection(name));
    }

    private String normSection(String name) {
        if (!this.isCaseSensitive) {
            name = name.toLowerCase();
        }
        return name.trim();
    }

    private static String[] toStringArray(Collection coll) {
        Object[] objArray = coll.toArray();
        String[] strArray = new String[objArray.length];
        for (int i = 0; i < objArray.length; ++i) {
            strArray[i] = (String)objArray[i];
        }
        return strArray;
    }

    public static void mian(String[] argv) {
    }

    public static class NoSuchSectionException
    extends RuntimeException {
        public NoSuchSectionException() {
        }

        public NoSuchSectionException(String msg) {
            super(msg);
        }
    }

    private static class OptionFormat {
        private static final int EXPECTED_TOKENS = 4;
        private String[] formatTokens;

        public OptionFormat(String formatString) {
            this.formatTokens = this.compileFormat(formatString);
        }

        public String format(String name, String value, char separator) {
            String[] t = this.formatTokens;
            return t[0] + name + t[1] + separator + t[2] + value + t[3];
        }

        private String[] compileFormat(String formatString) {
            String[] tokens = new String[]{"", "", "", ""};
            int tokenCount = 0;
            boolean seenPercent = false;
            StringBuffer token = new StringBuffer();
            block4: for (int i = 0; i < formatString.length(); ++i) {
                switch (formatString.charAt(i)) {
                    case '%': {
                        if (seenPercent) {
                            token.append("%");
                            seenPercent = false;
                            continue block4;
                        }
                        seenPercent = true;
                        continue block4;
                    }
                    case 's': {
                        if (seenPercent) {
                            if (tokenCount >= 4) {
                                throw new IllegalArgumentException("Illegal option format. Too many %s placeholders.");
                            }
                            tokens[tokenCount] = token.toString();
                            ++tokenCount;
                            token = new StringBuffer();
                            seenPercent = false;
                            continue block4;
                        }
                        token.append("s");
                        continue block4;
                    }
                    default: {
                        if (seenPercent) {
                            throw new IllegalArgumentException("Illegal option format. Unknown format specifier.");
                        }
                        token.append(formatString.charAt(i));
                    }
                }
            }
            if (tokenCount != 3) {
                throw new IllegalArgumentException("Illegal option format. Not enough %s placeholders.");
            }
            tokens[tokenCount] = token.toString();
            return tokens;
        }
    }

    private static class Comment
    implements Line {
        private String comment;
        private char delimiter;
        private static final char DEFAULT_DELIMITER = '#';

        public Comment(String comment) {
            this(comment, '#');
        }

        public Comment(String comment, char delimiter) {
            this.comment = comment.trim();
            this.delimiter = delimiter;
        }

        @Override
        public String toString() {
            return this.delimiter + " " + this.comment;
        }
    }

    private static class Option
    implements Line {
        private String name;
        private String value;
        private char separator;
        private OptionFormat format;
        private static final String ILLEGAL_VALUE_CHARS = "\n\r";

        public Option(String name, String value, char separator, OptionFormat format) {
            if (!Option.validName(name, separator)) {
                throw new IllegalArgumentException("Illegal option name:" + name);
            }
            this.name = name;
            this.separator = separator;
            this.format = format;
            this.set(value);
        }

        public String name() {
            return this.name;
        }

        public String value() {
            return this.value;
        }

        public void set(String value) {
            if (value == null) {
                this.value = value;
            } else {
                StringTokenizer st = new StringTokenizer(value.trim(), ILLEGAL_VALUE_CHARS);
                StringBuffer sb = new StringBuffer();
                while (st.hasMoreTokens()) {
                    sb.append(st.nextToken());
                }
                this.value = sb.toString();
            }
        }

        @Override
        public String toString() {
            return this.format.format(this.name, this.value, this.separator);
        }

        private static boolean validName(String name, char separator) {
            if (name.trim().equals("")) {
                return false;
            }
            return name.indexOf(separator) < 0;
        }
    }

    private static interface Line {
        public String toString();
    }

    public static class Section {
        private String name;
        private Map<String, Option> options;
        private List<Line> lines;
        private char[] optionDelims;
        private char[] optionDelimsSorted;
        private char[] commentDelims;
        private char[] commentDelimsSorted;
        private boolean isCaseSensitive;
        private OptionFormat optionFormat;
        private static final char[] DEFAULT_OPTION_DELIMS = new char[]{'=', ':'};
        private static final char[] DEFAULT_COMMENT_DELIMS = new char[]{'#', ';'};
        private static final char[] OPTION_DELIMS_WHITESPACE = new char[]{' ', '\t'};
        private static final boolean DEFAULT_CASE_SENSITIVITY = false;
        public static final String DEFAULT_OPTION_FORMAT = "%s %s %s";
        public static final char HEADER_START = '[';
        public static final char HEADER_END = ']';
        private static final int NAME_MAXLENGTH = 1024;
        private static final char[] INVALID_NAME_CHARS = new char[]{'[', ']'};
        private static final String NEWLINE_CHARS = "\n\r";

        public Section(String name) {
            this(name, null);
        }

        public Section(String name, boolean isCaseSensitive) {
            this(name, null, isCaseSensitive);
        }

        public Section(String name, char[] delims) {
            this(name, delims, false);
        }

        public Section(String name, char[] delims, boolean isCaseSensitive) {
            if (!Section.validName(name)) {
                throw new IllegalArgumentException("Illegal section name:" + name);
            }
            this.name = name;
            this.isCaseSensitive = isCaseSensitive;
            this.options = new HashMap<String, Option>();
            this.lines = new LinkedList<Line>();
            this.optionDelims = DEFAULT_OPTION_DELIMS;
            this.commentDelims = delims == null ? DEFAULT_COMMENT_DELIMS : delims;
            this.optionFormat = new OptionFormat(DEFAULT_OPTION_FORMAT);
            this.optionDelimsSorted = new char[this.optionDelims.length];
            System.arraycopy(this.optionDelims, 0, this.optionDelimsSorted, 0, this.optionDelims.length);
            this.commentDelimsSorted = new char[this.commentDelims.length];
            System.arraycopy(this.commentDelims, 0, this.commentDelimsSorted, 0, this.commentDelims.length);
            Arrays.sort(this.optionDelimsSorted);
            Arrays.sort(this.commentDelimsSorted);
        }

        public void setOptionFormatString(String formatString) {
            this.setOptionFormat(new OptionFormat(formatString));
        }

        public void setOptionFormat(OptionFormat format) {
            this.optionFormat = format;
        }

        public List<String> optionNames() {
            LinkedList<String> optNames = new LinkedList<String>();
            for (Line line : this.lines) {
                if (!(line instanceof Option)) continue;
                optNames.add(((Option)line).name());
            }
            return optNames;
        }

        public boolean hasOption(String name) {
            return this.options.containsKey(this.normOption(name));
        }

        public String get(String option) {
            String normed = this.normOption(option);
            if (this.hasOption(normed)) {
                return this.getOption(normed).value();
            }
            return null;
        }

        public void set(String option, String value) {
            this.set(option, value, this.optionDelims[0]);
        }

        public void set(String option, String value, char delim) {
            String normed = this.normOption(option);
            if (this.hasOption(normed)) {
                this.getOption(normed).set(value);
            } else {
                Option opt = new Option(normed, value, delim, this.optionFormat);
                this.options.put(normed, opt);
                this.lines.add(opt);
            }
        }

        public boolean remove(String option) {
            String normed = this.normOption(option);
            if (this.hasOption(normed)) {
                this.lines.remove(this.getOption(normed));
                this.options.remove(normed);
                return true;
            }
            return false;
        }

        public void addComment(String comment) {
            this.addComment(comment, this.commentDelims[0]);
        }

        public void addComment(String comment, char delim) {
            StringTokenizer st = new StringTokenizer(comment.trim(), NEWLINE_CHARS);
            while (st.hasMoreTokens()) {
                this.lines.add(new Comment(st.nextToken(), delim));
            }
        }

        public void addBlankLine() {
            this.lines.add(BLANK_LINE);
        }

        public void load(BufferedReader reader) throws IOException {
            while (reader.ready()) {
                reader.mark(1024);
                String line = reader.readLine().trim();
                if (line.length() > 0 && line.charAt(0) == '[') {
                    reader.reset();
                    return;
                }
                int delimIndex = -1;
                if (line.equals("")) {
                    this.addBlankLine();
                    continue;
                }
                delimIndex = Arrays.binarySearch(this.commentDelimsSorted, line.charAt(0));
                if (delimIndex >= 0) {
                    this.addComment(line.substring(1), this.commentDelimsSorted[delimIndex]);
                    continue;
                }
                delimIndex = -1;
                int delimNum = -1;
                int lastSpaceIndex = -1;
                int l = line.length();
                for (int i = 0; i < l && delimIndex < 0; ++i) {
                    boolean isSpace;
                    delimNum = Arrays.binarySearch(this.optionDelimsSorted, line.charAt(i));
                    if (delimNum >= 0) {
                        delimIndex = i;
                        continue;
                    }
                    boolean bl = isSpace = Arrays.binarySearch(OPTION_DELIMS_WHITESPACE, line.charAt(i)) >= 0;
                    if (!isSpace && lastSpaceIndex >= 0) break;
                    if (!isSpace) continue;
                    lastSpaceIndex = i;
                }
                if (delimIndex == 0) continue;
                if (delimIndex < 0) {
                    if (lastSpaceIndex < 0) {
                        this.set(line, "");
                        continue;
                    }
                    this.set(line.substring(0, lastSpaceIndex), line.substring(lastSpaceIndex + 1));
                    continue;
                }
                this.set(line.substring(0, delimIndex), line.substring(delimIndex + 1), line.charAt(delimIndex));
            }
        }

        public void save(PrintWriter writer) throws IOException {
            Iterator<Line> it = this.lines.iterator();
            while (it.hasNext()) {
                writer.println(it.next().toString());
            }
            if (writer.checkError()) {
                throw new IOException();
            }
        }

        private Option getOption(String name) {
            return this.options.get(name);
        }

        private String header() {
            return '[' + this.name + ']';
        }

        private static boolean validName(String name) {
            if (name.trim().equals("")) {
                return false;
            }
            for (int i = 0; i < INVALID_NAME_CHARS.length; ++i) {
                if (name.indexOf(INVALID_NAME_CHARS[i]) < 0) continue;
                return false;
            }
            return true;
        }

        private String normOption(String name) {
            if (!this.isCaseSensitive) {
                name = name.toLowerCase();
            }
            return name.trim();
        }
    }
}

