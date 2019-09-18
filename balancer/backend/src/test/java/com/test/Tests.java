package com.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Tests {

    @Test
    public void testOcurrences(){
        long init = System.currentTimeMillis();
        parseOcurrences(new int[]{1,0,0,1,1,1,0,1},1);
        parseOcurrences(new int[]{1,0,0,1,1,0,1,1},2);
        parseOcurrences(new int[]{1,1,0,1,1,1,0,1},2);
        parseOcurrences(new int[]{1,0,1,0,1,0,1,0},0);
        parseOcurrences(new int[]{1,1,1,1,1,1,1,1},1);
        parseOcurrences(new int[]{0,0,0,0,0,0,0,0},0);
        parseOcurrences(new int[]{1,1,1,1,1,1,0,1},1);
        parseOcurrences(new int[]{1,1,0,1,1,0,1,1},3);
        float end = (System.currentTimeMillis() - init) / 1000F;

        System.out.printf("Total time %.5f s \n", end);
    }

    private static final String REGEX = "( )|(,)|(\\[)|(])";
    private static final String REPLACE = "";
    private static final String SPLIT = "0";
    private static final int COMPARE = 1;

    private void parseOcurrences(int[] array, int expected){

        String astext = Arrays.toString(array);
        System.out.println("Array to test: " + astext);

        astext = astext.replaceAll(REGEX, REPLACE);
        String[] occurs = astext.split(SPLIT);
        int quantity = 0;//occurs.parallelStream().mapToInt(value -> (value.length() > COMPARE ? 1 : 0)).sum();
        for(String a : occurs) // more performant for the challenge
            quantity += a.length() > COMPARE ? 1 : 0;

        System.out.println("number of ocurrences: " + quantity);

        Assert.assertEquals(expected,quantity);


    }
}
