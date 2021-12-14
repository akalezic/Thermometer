package Thermometer;
import java.util.Date;
/**
 * A class used to take temperature measurements from an external scanner and find the average of those measurements,
 * determine if the average measurements are above or below a predetermined fever limit, and convert the average
 * temperature if necessary
 */
public class Temperature {
    /**
    * An array of doubles which contains 10 scanned temperatures from the scanner
    */
    private final double[] measuredTemperatures;
    /**
    * A double which represents the average of all the measured temperatures in the measuredTemperatures array
    */
    private double averageTemperature;
    /**
    * A string which represents the output that is sent to the ****display*** class
    */
    private String toDisplay;
    /**
    * A settings object which determines which unit should be used for displaying the temperature and what
    * the fever limits are 
    */
    private final Settings settings;
    /**
    * A Date object which represents a timestamp of when the temperatures were taken
    */
    private final Date timestamp;

    public Temperature(double[] temps, Settings settings){
        this.measuredTemperatures = temps;
        this.settings = settings;
        this.timestamp = new Date();
        this.averageTemperature = calculateAverage();
        System.out.println(averageTemperature);
        calculateFever();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    /**
    * Calculate's the average of the measured temperatures in the measuredTemperatures array
    */
    private double calculateAverage(){
        double total = 0;
        for (double d : measuredTemperatures){
            total = d + total;
        }
        return Math.round(total/measuredTemperatures.length*100.0)/100.0;
    }

    /**
    * Converts the average temperature to the desired unit specified by the settings object
    */
    private void convert(){
        if(settings.getTempUnit() == Settings.getCelsius()){
            averageTemperature = Math.round((averageTemperature-32)*5/9 *100.00)/100.00;
        }
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    /**
    * A void which determines if an averageTemperature is above or below the fever limit and then set the toDisplay variable to reflect that
    */
    private void calculateFever(){
        if(settings.getUpperFeverLimit() <= averageTemperature && settings.getTempUnit() == Settings.getFahrenheit()){
            toDisplay = "Fever Alert!! The measured temperature of " + averageTemperature + " is above the fever limit of " + settings.getUpperFeverLimit();        }
        else if(settings.getUpperFeverLimit() <= averageTemperature && settings.getTempUnit() == Settings.getCelsius()){
            convert();
            toDisplay = "Fever Alert!! The measured temperature of " + averageTemperature + " is above the fever limit of " + ((settings.getUpperFeverLimit()-32)*5/9);
        }
        else if(settings.getLowerFeverLimit() >= averageTemperature && settings.getTempUnit() == Settings.getFahrenheit()){
            toDisplay = "Fever Alert!! The measured temperature of " + averageTemperature + " is below the fever limit of " + settings.getLowerFeverLimit();
        }
        else if(settings.getLowerFeverLimit() >= averageTemperature && settings.getTempUnit() == Settings.getCelsius()){
            convert();
            toDisplay = "Fever Alert!! The measured temperature of " + averageTemperature + " is below the fever limit of " + ((settings.getLowerFeverLimit()-32)*5/9);
        }
        else{
            toDisplay = "The measured temperature is within the fever limits";
        }
    }

    public String getToDisplay() {
        return toDisplay;
    }
}
