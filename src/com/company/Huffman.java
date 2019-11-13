package com.company;

import com.sun.istack.internal.NotNull;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Huffman {
    private static class Node implements Comparable<Node> {
        String code; // Code of encoding value of that symbol
        Node parent, left, right;
        String symbol;
        double prob;

        Node(String symbol, double prob) {
            this.symbol = symbol;
            this.prob = prob;
            left = right = parent = null;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.symbol + " : " + this.code + " " + this.prob;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.prob, other.prob);
        }
    }

    final static int MAX_CHARACTERS = 256;
    static String codes[];
    static Node root;

    static void init() {
        root = null;
        codes = new String[MAX_CHARACTERS];
    }
    public static String Encode(String data){
        String ret = "";
        return ret;
    }
    public static String Decode (String data){
        String ret = "";
        return ret;
    }
    String getCode(char s) throws Exception {
        int n = s - 1;
        if (n >= MAX_CHARACTERS) throw new Exception("Invalid input.. cannot handle this character");
        return codes[n];
    }

    boolean setCode(char symbol, String code) throws Exception {
        if (getCode(symbol).length()>0) return false;
        codes[symbol-1] = code;
        return true;
    }

    int getChar(String code) throws Exception {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < codes.length; ++i) {
            if (codes[i].contains(code)) indexes.add(i);
        }
        if (indexes.size() == 1) return indexes.get(0);
        if (indexes.size() == 0) throw new Exception("Invalid input.. this code is not exists");
        else return -1; // Multiple codes are found
    }

    public void enterDictionary(Scanner in, PrintStream out) throws Exception {
        init();
        String code;
        char symbol;
        out.println("Enter the dictionary data (code) (symbol) enter code = -1 to halt");
        while (true) {
            code = in.next();
            if (Integer.parseInt(code) == -1) break;
            symbol = in.nextLine().charAt(0);
            if (!setCode(symbol, code))
                throw new Exception("Invalid input.. you have entered this symbol before");
        }
    }

    static void PrintTree(@NotNull PrintStream out) {
        out.println();
        List<List<String>> lines = new ArrayList<>();

        List<Node> level = new ArrayList<>();
        List<Node> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.toString();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (line.get(j) != null) c = '└';
                        }
                    }
                    out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            out.print(j % 2 == 0 ? " " : "─");
                        }
                        out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                out.println();
            }

            // print line of numbers
            for (String f : line) {

                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    out.print(" ");
                }
                out.print(f);
                for (int k = 0; k < gap2; k++) {
                    out.print(" ");
                }
            }
            out.println();

            perpiece /= 2;
        }
    }
}
