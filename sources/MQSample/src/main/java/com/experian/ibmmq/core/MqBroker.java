package com.experian.ibmmq.core;

import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;

import java.io.IOException;

/**
 * @author Sachith Dickwella
 */
public class MqBroker {

    static {
        System.setProperty("javax.net.debug","ssl");
        System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "1qaz2wsx@");
        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "1qaz2wsx@");
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
    }

    private final MQQueue queue;

    public MqBroker() throws MQException {
        MQEnvironment.enableTracing(1, System.out);
        MQEnvironment.hostname = "localhost";
        MQEnvironment.port = 1414;
        MQEnvironment.channel = "S_OAS12_OWE";
        MQEnvironment.properties.put(CMQC.SSL_CIPHER_SUITE_PROPERTY, "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384");
        MQEnvironment.properties.put(CMQC.USER_ID_PROPERTY, "C57426A");
        MQEnvironment.properties.put(CMQC.PASSWORD_PROPERTY, "Skynet@2015");

        MQQueueManager queueManager = new MQQueueManager("QM_OAS12_OWE");
        queue = queueManager.accessQueue("ceas.request", CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT);
    }

    public void sendMessage(String message) throws IOException, MQException {
        MQMessage mqMessage = new MQMessage();
        mqMessage.writeUTF(message);

        queue.put(mqMessage, new MQPutMessageOptions());
    }
}
