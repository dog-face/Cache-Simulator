package com.vito;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Cache Simulator\n" +
                "###############");
        System.out.println("Configure setSize, numBlocks, and blockSize manually in CacheSimulator.java \n" +
                "Automatically runs as setSize-associative. \n" +
                "setSize = 1 => direct-mapped. \n" +
                "setSize = numBlocks => fully associative\n" +
                "###############");
        System.out.println("Input addresses now with spaces between: ");
        Scanner scanner = new Scanner(System.in);
        String addresses = scanner.nextLine();
        System.out.println(addresses);
        List<Integer> addr = parseAddresses(addresses);
        //for(int i : addr){
        //    System.out.print(i + ",");
        //}
        CacheSimulator sim = new CacheSimulator(addr);
        sim.simulate();
    }

    public static List<Integer> parseAddresses(String addresses){
        addresses += " ";

        int length = 0;
        boolean moreSpaces = true;
        List<Integer> addrs = new LinkedList<>();

        while(moreSpaces){
            //System.out.println("looping");
            int i = addresses.indexOf(' ');
            if(i == -1) {
                moreSpaces = false;
            }
            if(i == 0){
                addresses = addresses.substring(1);
                i = addresses.indexOf(' ');
            }
            //int j = addresses.indexOf(' ');
            if(i > 0){
                String str = addresses.substring(0, i);
                int nextInt = Integer.parseInt(str);
                addrs.add(nextInt);
                addresses = addresses.substring(i);
                length++;
            }

        }
        return addrs;
    }
}
