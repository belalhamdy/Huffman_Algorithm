package com.company;

import com.sun.istack.internal.NotNull;

import java.io.PrintStream;
import java.util.*;

public class Huffman {
    private static class Node{
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
            if (this.symbol.equals("")) return "root";
            return this.symbol+ " : " + this.code + " " + this.prob;
        }

    }

    final static int MAX_CHARACTERS = 256;
    static String codes[];
    static Node root;

    static void init() {
        root = new Node("",1);
        codes = new String[MAX_CHARACTERS];
    }
    static void printQueue(PriorityQueue<Node> queue)
    {
        PriorityQueue<Node> pq = new PriorityQueue<>(queue);
        while (pq.size() > 0) {
            System.out.println(pq.poll());
        }
    }
    public static String Encode(String data) throws Exception {
        init();
        build(data);
        StringBuilder ret = new StringBuilder();
        for (int i = 0 ; i<data.length(); ++i)
        {
            ret.append(getCode(data.charAt(i)));
        }
        return ret.toString();
    }
    public static String Decode (String data) throws Exception {
        StringBuilder ret = new StringBuilder();
        String curr = "";
        int idx;
        for (int i = 0 ; i<data.length(); ++i)
        {
            curr+= data.charAt(i);
            idx = getChar(curr);
            if (idx != -1)
            {
                ret.append((char) (idx+1));
                curr = "";
            }
        }
        if (!curr.equals(""))
        {
            throw new Exception("Invalid input.. there is a missing code");
        }
        return ret.toString();
    }
    static void build(String data) throws Exception {
        int[] freq = new int[MAX_CHARACTERS];
        int length = data.length();
        Arrays.fill(freq,0);
        for (int i = 0 ; i<length ; ++i)
        {
            int c = data.charAt(i) -1;
            if (c >= MAX_CHARACTERS) throw new Exception("Invalid input.. cannot handle this character");
            ++freq[c];
        }
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.prob,o2.prob);
            }
        });
        for (int i = 0 ; i<freq.length ; ++i)
        {
            if (freq[i]>0)
            {
                Node temp = new Node((char)(i+1) + "" , freq[i]*1.0/length); // change here to negative if you want to reverse the priority queue
                queue.add(temp);
            }
        }
        //printQueue(queue);
        if (queue.size() == 1) root.left = queue.poll();
        else{
            Node left,right;
            while (queue.size()>2)
            {
                right = queue.poll();
                left = queue.poll();
                Node temp = new Node(left.symbol+right.symbol,left.prob + right.prob);
                temp.left = left;
                temp.right = right;
                queue.add(temp);
            }
            right = queue.poll();
            left = queue.poll();
            root.left = left;
            root.right = right;
        }
        setCodes(root,"");

    }
    static void setCodes(Node curr,String code) throws Exception {
        if (curr.symbol.length() == 1) codes[curr.symbol.charAt(0)-1] = code;
        if (curr.right != null)
        {
            curr.right.setCode(code+"1");
            setCodes(curr.right,code+"1");
        }
        if (curr.left  != null)
        {
            curr.left.setCode(code+"0");
            setCodes(curr.left,code+"0");
        }
    }
    static String getCode(char s) throws Exception {
        int n = s - 1;
        if (n >= MAX_CHARACTERS) throw new Exception("Invalid input.. cannot handle this character");
        return codes[n];
    }

    static boolean setCode(char symbol, String code) throws Exception {
        if (getCode(symbol).length()>0) return false;
        codes[symbol-1] = code;
        return true;
    }

    static int getChar(String code) throws Exception {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < codes.length; ++i) {
            if (codes[i]!= null && codes[i].startsWith(code)) indexes.add(i);
        }
        if (indexes.size() == 1) return indexes.get(0);
        if (indexes.size() == 0) throw new Exception("Invalid input.. this code is not exists");
        else return -1; // Multiple codes are found
    }

    public static void EnterDictionary(Scanner in, PrintStream out) throws Exception {
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
