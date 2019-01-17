package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String sentence = new String("hello");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter in a sentence (100 CHAR MAX): ");
        System.out.println(" ");
        System.out.print("You: ");
        sentence = scanner.nextLine();
        scanner.close();
        char[] newSentence = new char[sentence.length()+1];

        for(int i = 0; i < sentence.length(); i++){
            Random random = new Random();
            double chance = random.nextDouble();

            char ch = sentence.charAt(i);

            if (chance < 0.45){
                ch = Character.toUpperCase(ch);
            }
            newSentence[i] = ch;
        }
        System.out.print("Spongebob: ");
        for(int i = 0; i < sentence.length(); i++) {
            System.out.print(newSentence[i]);
        }
    }

}
