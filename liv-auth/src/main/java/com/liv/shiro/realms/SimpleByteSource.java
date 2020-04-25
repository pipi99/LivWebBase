package com.liv.shiro.realms;

import java.io.Serializable;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.realms
 * @Description: 序列化SimpleByteSource
 * @date 2020.4.24  16:40
 * @email 453826286@qq.com
 */
public class SimpleByteSource extends org.apache.shiro.util.SimpleByteSource implements Serializable {
    private static final long serialVersionUID = 5528101080905698238L;

    public SimpleByteSource(byte[] bytes) {
        super(bytes);
        // TODO 自动生成的构造函数存

    }
}