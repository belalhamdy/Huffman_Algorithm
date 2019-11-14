package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
//        System.out.println(TestCaseBuilder.buildCase(in,System.out));
        System.out.println("Welcome to Normal Huffman Encoder/Decoder");
        while (true) {
            System.out.println("\n1- Encode\n2- Decode\nAny other character will halt");
            String c = in.nextLine();
            if (c.equals("1")) {
                System.out.println("Enter the text line to encode");
                String text = in.nextLine();
                try {
                    String encoded = Huffman.Encode(text);
                    System.out.println("Encoded Text : " + encoded);
//                    BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
//                    writer.write(text + "\n" + encoded);
//                    writer.close();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    continue;
                }
            } else if (c.equals("2")) {
                System.out.println("Enter the encoded bits to decode");
                String text = in.nextLine();
                System.out.println("1- Use previous dictionary\n2- Enter your dictionary");
                String d = in.nextLine();
//                String text ="0010010010010010010010010010010010010010010010000000000000000000000000000000000000000000000000000000001010101010101010101010101010101010101010101010101010101010100010001" ;
//                String d = "1";
                try {
                    if (d.equals("2")) Huffman.EnterDictionary(System.in,System.out);
                    else if (!d.equals("1")) continue;
                    String decoded = Huffman.Decode(text);
                    System.out.println("Decoded Text : " + decoded);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    continue;
                }
            } else break;
            Huffman.PrintTree(System.out);
        }
    }
}
