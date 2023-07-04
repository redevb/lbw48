package kz.attractor.java;

import kz.attractor.java.LabWork.VotingServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new VotingServer("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
