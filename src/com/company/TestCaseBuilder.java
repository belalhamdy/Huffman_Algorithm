package com.company;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class TestCaseBuilder {
    public static String buildCase(InputStream input, PrintStream out) {
        Scanner in = new Scanner(input);
        out.println("Enter character and it's number of occurrences (0 0) to halt");
        StringBuilder ret = new StringBuilder();
        while (true)
        {
            String s = in.next().charAt(0)+"";
            int n = in.nextInt();
            if (n <= 0) return shuffle(ret.toString());
            ret.append(new String(new char[n]).replace("\0", s));
        }
    }
    static String shuffle(String data)
    {
        List<Character> arr = new ArrayList<>();
        for (char c : data.toCharArray()) {
            arr.add(c);
        }
        Collections.shuffle(arr);
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr)
        {
            builder.append(ch);
        }
        return builder.toString();
    }
}
