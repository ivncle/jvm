package com.zqq;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {

//        int sum = 0;
//        for (int i = 1; i <= 100; i++) {
//            sum += i;
//        }
//        System.out.println(sum);

//        long x = fibonacci(10);
//        System.out.println(x);


        for (String str : args) {
            System.out.println(str);
        }

    }

    //斐波那契数列（Fibonacci sequence）
    private static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}


