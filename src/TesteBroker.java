import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class TesteBroker {

    private static MqttClient cliente = null;
    private static String brokerUrl = "tcp://lsdi.ufma.br:1883";
    private static String tmpDir = System.getProperty("java.io.tmpdir");
    private static MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

    public static void main(String[] args) {
        try {
            cliente = new MqttClient(brokerUrl, "SubscribreCliente", dataStore);
            cliente.connect();
            System.out.println("Conectou");
        } catch (MqttException e) {
            System.out.println("ERRO");
            e.printStackTrace();
        }

    }
}
