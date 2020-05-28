package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable () {
            public void run(){
                new Authentification().setVisible(true);
            }
        });
    }
}
