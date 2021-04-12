package com.lammon.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.enumeration.SerializerCode;
import com.lammon.exception.SerializeException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * kryo序列化实现
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class KryoSerializer implements CommonSerializer{

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
                Kryo kryo = new Kryo();
                kryo.register(RpcRequest.class);
                kryo.register(RpcResponse.class);
                //默认为true,是否开启注册行为
                kryo.setReferences(true);
                kryo.setRegistrationRequired(false);
                return kryo;
            });

    @Override
    public byte[] serialize(Object object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)){
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            kryo.writeObject(output, object);
            KRYO_THREAD_LOCAL.remove();
            return output.toBytes();
        }catch (Exception e){
            log.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            Object o = kryo.readObject(input, clazz);
            KRYO_THREAD_LOCAL.remove();
            return o;
        } catch (Exception e) {
            log.error("反序列化时有错误发生:", e);
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}
