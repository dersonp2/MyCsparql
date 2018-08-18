package Start;

import Communication.Communication;
import ConfigLog.ConfigLog;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;

public class StartBroker {
    //Mqtt
    private static String brokerUrl = "tcp://lsdi.ufma.br:1883";
    private static MqttClient client = null;
    private String tmpDir = System.getProperty("java.io.tmpdir");
    private MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
    //Logger
    private static Logger logger = null;
    //Variável Singleton
    public static StartBroker instance = null;

    //Método Singleton
    public static StartBroker getInstance() {
        if (instance == null) {
            instance = new StartBroker();
            logger = new ConfigLog().log(StartBroker.class);
        }
        return instance;
    }

    public MqttClient Connection(String clientId) {
        if (client == null) {
            try {
                client = new MqttClient(brokerUrl, clientId, dataStore);
                client.connect();
                logger.info("Broker connected");
            } catch (MqttException e) {
                e.printStackTrace();
                logger.error("Error connecting to broker - ", e.getMessage());
            }
        }
        return client;
    }
}
