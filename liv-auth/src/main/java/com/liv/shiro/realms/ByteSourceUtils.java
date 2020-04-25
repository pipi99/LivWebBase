package com.liv.shiro.realms;

import org.apache.shiro.util.ByteSource;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.realms
 * @Description: 序列化工具类ByteSourceUtils
 * @date 2020.4.24  16:41
 * @email 453826286@qq.com
 */
public class ByteSourceUtils {
    public static ByteSource bytes(byte[] bytes) {
        return new SimpleByteSource(bytes);
    }

    public static ByteSource bytes(String arg0) {
        return new SimpleByteSource(arg0.getBytes());
    }
}
