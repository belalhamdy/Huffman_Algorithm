package com.company;

import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to Normal Huffman Encoder/Decoder");
        while (true) {
            System.out.println("\n1- Encode\n2- Decode\nAny other character will halt");
            String c = in.nextLine();
            if (c.equals("1")) {
                System.out.println("Enter the text line to encode");
                String text = in.nextLine();
                try {
                    System.out.println("Encoded Text : " + Huffman.Encode(text));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    continue;
                }
            } else if (c.equals("2")) {
                System.out.println("Enter the encoded bits to decode");
                String text = in.nextLine();
                System.out.println("1- Use previous dictionary\n2- Enter your dictionary");
                String d = in.nextLine();
                try {
                    if (d.equals("2")) Huffman.EnterDictionary(in,System.out);
                    else if (!d.equals("1")) continue;
                    System.out.println("Decoded Text : " + Huffman.Decode(text));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    continue;
                }
            } else break;
            Huffman.PrintTree(System.out);
        }
    }
}
