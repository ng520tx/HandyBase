package com.handy.base.utils;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 编码解码相关工具类
 * <p>
 * Created by LiuJie on 2016/1/19.
 */
public class EncodeUT {

    private static EncodeUT encodeUT = null;
    /**
     * ===================================================================
     * String编码格式转换
     */
    private final String US_ASCII = "US-ASCII"; //7位 ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
    private final String ISO_8859_1 = "ISO-8859-1"; //ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
    private final String UTF_8 = "UTF-8"; //8位 UCS 转换格式
    private final String UTF_16BE = "UTF-16BE"; //16位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
    private final String UTF_16LE = "UTF-16LE"; //16位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
    private final String UTF_16 = "UTF-16"; //16位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
    private final String GBK = "GBK"; //中文超大字符集

    private EncodeUT() {
    }

    public synchronized static EncodeUT getInstance() {
        if (encodeUT == null) {
            encodeUT = new EncodeUT();
        }
        return encodeUT;
    }

    /**
     * URL编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符
     * @return 编码为UTF-8的字符串
     */
    public String urlEncode(String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * URL编码
     * <p>若系统不支持指定的编码字符集,则直接将input原样返回</p>
     *
     * @param input   要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public String urlEncode(String input, String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解码的字符串
     * @return URL解码后的字符串
     */
    public String urlDecode(String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL解码
     * <p>若系统不支持指定的解码字符集,则直接将input原样返回</p>
     *
     * @param input   要解码的字符串
     * @param charset 字符集
     * @return URL解码为指定字符集的字符串
     */
    public String urlDecode(String input, String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字符串
     * @return Base64编码后的字符串
     */
    public byte[] base64Encode(String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    public byte[] base64Encode(byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    public String base64Encode2String(byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字符串
     */
    public byte[] base64Decode(String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字符串
     */
    public byte[] base64Decode(byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64URL安全编码
     * <p>将Base64中的URL非法字符�?,/=转为其他字符, 见RFC3548</p>
     *
     * @param input 要Base64URL安全编码的字符串
     * @return Base64URL安全编码后的字符串
     */
    public byte[] base64UrlSafeEncode(String input) {
        return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }

    /**
     * Html编码
     *
     * @param input 要Html编码的字符串
     * @return Html编码后的字符串
     */
    public String htmlEncode(String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return Html.escapeHtml(input);
        } else {
            //参照Html.escapeHtml()中代码
            StringBuilder out = new StringBuilder();
            for (int i = 0, len = input.length(); i < len; i++) {
                char c = input.charAt(i);
                if (c == '<') {
                    out.append("&lt;");
                } else if (c == '>') {
                    out.append("&gt;");
                } else if (c == '&') {
                    out.append("&amp;");
                } else if (c >= 0xD800 && c <= 0xDFFF) {
                    if (c < 0xDC00 && i + 1 < len) {
                        char d = input.charAt(i + 1);
                        if (d >= 0xDC00 && d <= 0xDFFF) {
                            i++;
                            int codepoint = 0x010000 | (int) c - 0xD800 << 10 | (int) d - 0xDC00;
                            out.append("&#").append(codepoint).append(";");
                        }
                    }
                } else if (c > 0x7E || c < ' ') {
                    out.append("&#").append((int) c).append(";");
                } else if (c == ' ') {
                    while (i + 1 < len && input.charAt(i + 1) == ' ') {
                        out.append("&nbsp;");
                        i++;
                    }
                    out.append(' ');
                } else {
                    out.append(c);
                }
            }
            return out.toString();
        }
    }

    /**
     * Html解码
     *
     * @param input 待解码的字符串
     * @return Html解码后的字符串
     */
    public String htmlDecode(String input) {
        return Html.fromHtml(input).toString();
    }

    /**
     * 将字符编码转换成US-ASCII码
     */
    public String toASCII(String str) throws UnsupportedEncodingException {
        return changeCharset(str, US_ASCII);
    }

    /**
     * 将字符编码转换成ISO-8859-1码
     */
    public String toISO_8859_1(String str) throws UnsupportedEncodingException {
        return changeCharset(str, ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF-8码
     */
    public String toUTF_8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE码
     */
    public String toUTF_16BE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16BE);
    }

    /**
     * 将字符编码转换成UTF-16LE码
     */
    public String toUTF_16LE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16LE);
    }

    /**
     * 将字符编码转换成UTF-16码
     */
    public String toUTF_16(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16);
    }

    /**
     * 将字符编码转换成GBK码
     */
    public String toGBK(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GBK);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            //用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            //用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 将待测试字符串转换成所有编码格式
     *
     * @param content 测试字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public boolean ChangeAll(String content) throws UnsupportedEncodingException {
        String str = content;
        PrintfUT.getInstance().LogD("str: " + str);

        String gbk = toGBK(str);
        PrintfUT.getInstance().LogD("转换成GBK码: " + gbk);

        String ascii = toASCII(str);
        PrintfUT.getInstance().LogD("转换成US-ASCII码: " + ascii);
        gbk = changeCharset(ascii, US_ASCII, GBK);
        PrintfUT.getInstance().LogD("再把ASCII码的字符串转换成GBK码: " + gbk);

        String iso88591 = toISO_8859_1(str);
        PrintfUT.getInstance().LogD("转换成ISO-8859-1码: " + iso88591);
        gbk = changeCharset(iso88591, ISO_8859_1, GBK);
        PrintfUT.getInstance().LogD("再把ISO-8859-1码的字符串转换成GBK码: " + gbk);

        String utf8 = toUTF_8(str);
        PrintfUT.getInstance().LogD("转换成UTF-8码: " + utf8);
        gbk = changeCharset(utf8, UTF_8, GBK);
        PrintfUT.getInstance().LogD("再把UTF-8码的字符串转换成GBK码: " + gbk);

        String utf16be = toUTF_16BE(str);
        PrintfUT.getInstance().LogD("转换成UTF-16BE码:" + utf16be);
        gbk = changeCharset(utf16be, UTF_16BE, GBK);
        PrintfUT.getInstance().LogD("再把UTF-16BE码的字符串转换成GBK码: " + gbk);

        String utf16le = toUTF_16LE(str);
        PrintfUT.getInstance().LogD("转换成UTF-16LE码:" + utf16le);
        gbk = changeCharset(utf16le, UTF_16LE, GBK);
        PrintfUT.getInstance().LogD("再把UTF-16LE码的字符串转换成GBK码: " + gbk);

        String utf16 = toUTF_16(str);
        PrintfUT.getInstance().LogD("转换成UTF-16码:" + utf16);
        gbk = changeCharset(utf16, UTF_16, GBK);
        PrintfUT.getInstance().LogD("再把UTF-16码的字符串转换成GBK码: " + gbk);
        return true;
    }

    /**
     * ===================================================================
     * org.apache.http.util.EncodingUtils
     * <p>
     * 将HTTP内容字符的字节数组转换成一个字符串。如果不支持指定的字符集,默认使用系统编码。
     *
     * @param data    the byte array to be encoded
     * @param offset  the index of the first byte to encode
     * @param length  the number of bytes to encode
     * @param charset the desired character encoding
     * @return The result of the conversion.
     */
    public String getEncodeString(final byte[] data, final int offset, final int length, final String charset) {
        notNull(data, "Input");
        notEmpty(charset, "Charset");
        try {
            return new String(data, offset, length, charset);
        } catch (final UnsupportedEncodingException e) {
            return new String(data, offset, length);
        }
    }

    /**
     * 将HTTP内容字符的字节数组转换成一个字符串。如果不支持指定的字符集,默认使用系统编码。
     *
     * @param data    the byte array to be encoded
     * @param charset the desired character encoding
     * @return The result of the conversion.
     */
    public String getEncodeString(final byte[] data, final String charset) {
        notNull(data, "Input");
        return getEncodeString(data, 0, data.length, charset);
    }

    /**
     * 将指定的字符串转换为一个字节数组。如果不支持指定的字符集,默认使用系统编码。
     *
     * @param data    the string to be encoded
     * @param charset the desired character encoding
     * @return The resulting byte array.
     */
    public byte[] getEncodeBytes(final String data, final String charset) {
        notNull(data, "Input");
        notEmpty(charset, "Charset");
        try {
            return data.getBytes(charset);
        } catch (final UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

    /**
     * 将指定的字符串转换为ASCII字符的字节数组。
     *
     * @param data the string to be encoded
     * @return The string as a byte array.
     */
    public byte[] getEncodeAsciiBytes(final String data) {
        notNull(data, "Input");
        return data.getBytes(Charset.forName("US-ASCII"));
    }

    /**
     * 将ASCII字符的字节数组转换成一个字符串。这个方法是解码时使用HTTP内容元素。
     *
     * @param data   the byte array to be encoded
     * @param offset the index of the first byte to encode
     * @param length the number of bytes to encode
     * @return The string representation of the byte array
     */
    public String getEncodeAsciiString(final byte[] data, final int offset, final int length) {
        notNull(data, "Input");
        return new String(data, offset, length, Charset.forName("US-ASCII"));
    }

    /**
     * 将ASCII字符的字节数组转换成一个字符串。这个方法是解码时使用HTTP内容元素。
     *
     * @param data the byte array to be encoded
     * @return The string representation of the byte array
     */
    public String getAsciiString(final byte[] data) {
        notNull(data, "Input");
        return getEncodeAsciiString(data, 0, data.length);
    }

    private <T> T notNull(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        }
        return argument;
    }

    private <T extends CharSequence> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        }
        if (isEmpty(argument)) {
            throw new IllegalArgumentException(name + " may not be empty");
        }
        return argument;
    }

    private boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }
}
