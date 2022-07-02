package com.linyi.zhcompus;

import com.linyi.zhcompus.utils.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Smartcompus01ApplicationTests {

    @Test
    void contextLoads() {
        String encrypt = MD5.encrypt("123456");
        System.out.println(encrypt);
    }

}
