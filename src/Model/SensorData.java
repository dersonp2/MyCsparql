package Model;

import java.util.Map;

/**
 * Contains the sensor data received from the
 * Mobile Objects
 */
public class SensorData{
    /** DEBUG */
    private static final String TAG = SensorData.class.getSimpleName();

    /** Message Data */
	private String   mouuid;
    private Double   signal;


    private String   action;


	private String   sensorName;
	private Double[] sensorValue;
	private Map<String, String> properties;

    /** M-OBJ Actions */
	public static final String FOUND        = "found";
	public static final String CONNECTED    = "connected";
	public static final String DISCONNECTED = "disconnected";
	public static final String READ         = "read";
	//public static final String WRITE        = "write";

    public SensorData() {
        super();
    }

    /** Getters */
	public String getMouuid() {
	    return this.mouuid;
	}

    public Double getSignal() {
        return this.signal;
    }

    public String getAction() {
        return this.action;
    }

	public String getSensorName() {
		return this.sensorName;
	}
	
	public Double[] getSensorValue() {
		return this.sensorValue;
	}

    public Map<String, String> getProperties() {
        return properties;
    }

    /** Getters */
	
	/** Setters */
	public void setMouuid( String mouuid ) {
	    this.mouuid = mouuid;
	}

    public void setSignal( Double signal ) {
        this.signal = signal;
    }

    public void setAction( String action ) {
        this.action = action;
    }

	public void setSensorName( String sensorName ) {
		this.sensorName = sensorName;
	}
	
	public void setSensorValue( Double[] sensorValue ) {
		this.sensorValue = sensorValue;
	}

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /** Setters */
}
