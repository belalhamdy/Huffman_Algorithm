package com.company;

import java.io.PrintStream;
import java.util.Scanner;

public class TestCaseBuilder {
    public static String buildCase(Scanner in, PrintStream out) {
        out.println("Enter character and it's number of occurrences (0 0) to halt");
        StringBuilder ret = new StringBuilder();
        while (true)
        {
            String s = in.next().charAt(0)+"";
            int n = in.nextInt();
            if (n <= 0) return ret.toString();
            ret.append(new String(new char[n]).replace("\0", s));
        }
    }
}
