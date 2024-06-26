package core;

/*
TODO
- Implement UDP/IP protocol
- Implement TCP/IP protocol
- Spoof TCP Headers (Maybe UDP as well)
- Implement HTTP protocol XD
- Add user input
- Add proper loggers everywhere
- Refactor and fix bugs
 */

import network.http.HTTPSender;
import network.syn.SYNSender;
import network.tcp.TCPSender;
import network.udp.UDPSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private UDPSender udpSender;
    private TCPSender tcpSender;
    private SYNSender synSender;
    private HTTPSender httpSender;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private Main() {
        printLogo();
        printInfo();
        initiate();
    }

    private void printLogo() {
        System.out.println(" ______         ____     ,---.   .--.  ______          .-''-.     .---.      .-./`)      ,-----.     ,---.   .--.\n"
                + "|    _ `''.   .'  __ `.  |    \\  |  | |    _ `''.    .'_ _   \\    | ,_|      \\ .-.')   .'  .-,  '.   |    \\  |  |\n"
                + "| _ | ) _  \\ /   '  \\  \\ |  ,  \\ |  | | _ | ) _  \\  / ( ` )   ' ,-./  )      / `-' \\  / ,-.|  \\ _ \\  |  ,  \\ |  |\n"
                + "|( ''_'  ) | |___|  /  | |  |\\_ \\|  | |( ''_'  ) | . (_ o _)  | \\  '_ '`)     `-'`\"` ;  \\  '_ /  | : |  |\\_ \\|  |\n"
                + "| . (_) `. |    _.-`   | |  _( )_\\  | | . (_) `. | |  (_,_)___|  > (_)  )     .---.  |  _`,/ \\ _/  | |  _( )_\\  |\n"
                + "|(_    ._) ' .'   _    | | (_ o _)  | |(_    ._) ' '  \\   .---. (  .  .-'     |   |  : (  '\\_/ \\   ; | (_ o _)  |\n"
                + "|  (_.\\.' /  |  _( )_  | |  (_,_)\\  | |  (_.\\.' /   \\  `-'    /  `-'`-'|___   |   |   \\ `\"/  \\  ) /  |  (_,_)\\  |\n"
                + "|       .'   \\ (_ o _) / |  |    |  | |       .'     \\       /    |        \\  |   |    '. \\_/``\".'   |  |    |  |\n"
                + "'-----'`      '.(_,_).'  '--'    '--' '-----'`        `'-..-'     `--------`  '---'      '-----'     '--'    '--'\n");
    }

    private void printInfo() {
        System.out.println("List of protocols:\n" +
                "1 - UDP/IP Resource exhaustion.\n" +
                "2 - TCP/IP Test.\n" +
                "3 - SYN Flood.\n" +
                "4 - HTTP Flood");
        System.out.println("Choose the protocol: ");
    }

    private void initiate() {
        Thread inputThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (true) {
                    String input = reader.readLine();
                    if (input.trim().matches("[1-4]")) {
                        switch (input) {
                            case "1":
                                udpSender = new UDPSender(8888, 9999, 100000, 1000, 30);
                                break;
                            case "2":
                                tcpSender = new TCPSender();
                                break;
                            case "3":
                                synSender = new SYNSender(8888, 9999, 20, 50, 30);
                                break;
                            case "4":
                                httpSender = new HTTPSender(9999, 1000, 30);
                                break;
                            default:
                                if (udpSender != null) {
                                    udpSender.stop();
                                    udpSender = null;
                                } else if (tcpSender != null) {
                                    tcpSender.stop();
                                    tcpSender = null;
                                } else if (synSender != null) {
                                    synSender.stop();
                                    synSender = null;
                                } else if (httpSender != null) {
                                    httpSender.stop();
                                    httpSender = null;
                                }
                                break;
                        }
                        break;
                    } else if (input.equals("q") || input.equals("Q")) {
                        System.out.println("Exiting...");
                        break;
                    } else {
                        System.out.println("Please choose one of the given protocols!");
                    }
                }
            } catch (IOException e) {
                //System.out.println("Within \"initiate\" method, Main.java: " + e.getMessage());
                logger.log(Level.SEVERE, "Error within the \"initiate\" method, Main.java", e);
            }
        });

        inputThread.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
