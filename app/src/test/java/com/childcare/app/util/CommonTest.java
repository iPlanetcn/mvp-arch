package com.childcare.app.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * 普通单元测试
 *
 * @author john
 * @since 2017-05-12
 */
public class CommonTest {

    @Test
    public void testListSort() throws Exception {
        List<Integer> original = Arrays.asList(3, 2, 5, 1, 4);

        Collections.sort(original, (o1, o2) -> o1 > o2 ? 1 :
                (o1.intValue() == o2.intValue() ? 0 : -1));
        System.out.println(original);
        assertEquals(original.get(0).intValue(), 1);
        assertEquals(original.get(1).intValue(), 2);
        assertEquals(original.get(2).intValue(), 3);
        assertEquals(original.get(3).intValue(), 4);
        assertEquals(original.get(4).intValue(), 5);

    }

    @Test
    public void testCase() throws Exception {
        List<Integer> original = Arrays.asList(3, 2, 5, 1, 4);
        for (int i = 0; i < original.size(); i++) {
            switch (i) {
                case 0:
                    System.out.println(original.get(i));
                    break;
                case 1:
                    System.out.println(original.get(i));
                    break;
                case 2:
                    System.out.println(original.get(i));
                    break;
                case 3:
                    System.out.println(original.get(i));
                    break;
                case 4:
                    System.out.println(original.get(i));
                    break;
            }
        }

    }

    @Test
    public void testMd5() throws Exception {
        String assembleStr = "I love you";
        String encyptedStr = "e4f58a805a6e1fd0f6bef58c86f9ceb3";
        String s = Hashing.md5().hashString(assembleStr, Charsets.UTF_8).toString();

        assertEquals(encyptedStr, s);

    }
}
