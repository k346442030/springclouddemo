package com.platform.service.core.store;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Created by xuyn15828 on 2016/3/16.
 */
public enum FileType {
    JPG("ffd8ff", "jpg"),
    PNG("89504e470d0a1a0a0000", "png"),
    TIF("49492a00227105008037", "tif"),
    GIF("474946383961", "gif"),
    BMP("424d******0000000000", "bmp"),
    DWG("41433130313500000000", "dwg"),
    HTML("3c21444f435459504520", "html"),
    HTM("3c21646f637479706520", "htm"),
    CSS("48544d4c207b0d0a0942", "css"),
    JS("696b2e71623d696b2e71", "js"),
    RTF("7b5c727466315c616e73", "rtf"),
    PSD("38425053000100000000", "psd"),
    EML("46726f6d3a203d3f6762", "eml"),
    DOC("d0cf11e0a1b11ae10000", "doc"),
    VSD("d0cf11e0a1b11ae10000", "vsd"),
    MDB("5374616E64617264204A", "mdb"),
    PS("252150532D41646F6265", "ps"),
    PDF("255044462d312e350d0a", "pdf"),
    AAC("fff****0", "aac"),
    //fff1e0400a7ffc014022
    //   fff1ec4004bffc014042
    //   fff96c6001600000cc00
    RMVB("2e524d46000000120001", "rmvb"),
    FLV("464c5601050000000900", "flv"),
    MP4("00000020667479706d70", "mp4"),
    MP3("ffe318c4000", "mp3"),
    MP3_1("49443303000000002176", "mp3"),
    MPG("000001ba210001000180", "mpg"),
    WMV("3026b2758e66cf11a6d9", "wmv"),
    WAV("52494646******005741", "wav"),
    AVI("52494646d07d60074156", "avi"),
    MID("4d546864000000060001", "mid"),
    ZIP("504b0304140000000800", "zip"),
    RAR("526172211a0700cf9073", "rar"),
    INI("235468697320636f6e66", "ini"),
    JAR("504b03040a0000000000", "jar"),
    EXE("4d5a9000030000000400", "exe"),
    JSP("3c25402070616765206c", "jsp"),
    MF("4d616e69666573742d56", "mf"),
    XML("3c3f786d6c2076657273", "xml"),
    SQL("494e5345525420494e54", "sql"),
    JAVA("7061636b616765207765", "java"),
    BAT("406563686f206f66660d", "bat"),
    GZ("1f8b0800000000000000", "gz"),
    PROPERTIES("6c6f67346a2e726f6f74", "properties"),
    CLASS("cafebabe0000002e0041", "class"),
    CHM("49545346030000006000", "chm"),
    MXP("04000000010000001300", "mxp"),
    DOCX("504b0304140006000800", "docx"),
    WPS("d0cf11e0a1b11ae10000", "wps"),
    TORRENT("6431303a637265617465", "torrent"),
    MOV("6D6F6F76", "mov"),
    WPD("FF575043", "wpd"),
    DBX("CFAD12FEC5FD746F", "dbx"),
    PST("2142444E", "pst"),
    QDF("AC9EBD8F", "qdf"),
    PWL("E3828596", "pwl"),
    RAM("2E7261FD", "ram");
    
    /**
     * 文件头
     * */
    private String header;
    /**
     * 文件类型
     * */
    private String suffix;
    
    FileType(String header, String suffix) {
        this.header = header;
        this.suffix = suffix;
    }
    
    public String getHeader() {
        return header;
    }
    
    public void setHeader(String header) {
        this.header = header;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("header", header).append("suffix", suffix).toString();
    }
    
    public static String getByHeader(String fileCode) {
        for (FileType type : FileType.values()) {
            String key = type.getHeader();
            if (type.getHeader().toLowerCase().startsWith(fileCode.toLowerCase())
                    || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                return type.getSuffix();
            } else {
                //检查单个字符通配符
                fileCode = fileCode.toLowerCase();
                boolean pass = true;
                for (int i = 0; i < key.length(); i++) {
                    char c = key.charAt(i);
                    if (c == '*' || c == fileCode.charAt(i)) {
                        continue;
                    }
                    pass = false;
                    break;
                }
                if (pass) {
                    return type.getSuffix();
                }
            }
        }
        return StringUtils.EMPTY;
    }
}
