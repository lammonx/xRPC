package com.lammon.serializer;

import com.lammon.enumeration.SerializerCode;

/**
 * 通用的序列化反序列化接口
 *
 * @author lammon
 * @date 2021/4/12
 */
public interface CommonSerializer {

    int DEFALUT_SERIALIZER = SerializerCode.KRYO.getCode();

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code){
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
