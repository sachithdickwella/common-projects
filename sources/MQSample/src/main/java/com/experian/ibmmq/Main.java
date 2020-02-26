package com.experian.ibmmq;

import com.experian.ibmmq.core.MqBroker;

import javax.net.ssl.SSLServerSocketFactory;
import java.util.stream.Stream;

/**
 * @author Sachith Dickwella
 */
public class Main {

    public static void main(String[] args) throws Exception {
        MqBroker broker = new MqBroker();
        broker.sendMessage("Hello, World");
    }
}