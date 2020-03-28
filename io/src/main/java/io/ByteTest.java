package io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

/**
 * @author lili
 * @date 2020/3/28 15:34
 * @description
 * @notes byte,byte[],bytebuffer,bytebuf
 */
public class ByteTest {

    @Test
    public void test() {
        Byte[] bytes = new Byte[]{1,2,3,4};

        for (Byte aByte : bytes) {

        }

        byte[] my = {1,2,3,4};
        for (byte b : my) {

        }


        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{1,2,3,4,5});
        System.out.println(inputStream.available());
        System.out.println(inputStream.read());
        System.out.println(inputStream.read());
        System.out.println(inputStream.read());
        System.out.println(inputStream.read());
        System.out.println(inputStream.available());



        ByteBuffer byteBuffer = ByteBuffer.allocate(5);



    }

}
