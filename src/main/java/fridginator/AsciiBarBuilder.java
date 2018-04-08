package fridginator;

/**
 * This class builds beautiful ascii bars for visualizing quantities, progress,
 * or whatever else your heart desires.
 * 
 * @author dcodeh
 *
 */
public class AsciiBarBuilder {

    /**
     * Returns a string with a beautiful bar that looks something like this:
     * 
     * [======!===________________]
     *  |<-     barLength      ->|
     *  
     * = is the fillChar, ! is the criticalChar, and _ is the emmptyChar
     * 
     * @param barLength Length of the data portion of the bar (shown above). The total length of the string returned
     *                  will be barLength + 2 once brackets are added.
     * @param fillChar The character to use for representing a filled section of the bar
     * @param emptyChar The character to use to represent empty space
     * @param criticalChar The character to use to represent the critical value
     * @param min The minimum value of the bar
     * @param max The maximum value of the bar
     * @param value The number for this bar to display
     * @param criticalValue The value to place the critical marker at. It will not be shown if it is outside of the bar's range
     * @return A string containing the beautiful bar
     */
    public static String buildInventoryBar(int barLength, char fillChar, char emptyChar, char criticalChar, double min, double max, double value, double criticalValue) {
        String bar = "";
        
        // validate the arguments first
        if((barLength > 0) && (min < max) ) {
            // add an opening bracket
            if(value < min) {
                bar += "<";
            } else {
                bar += "[";
            }
            
            double range = max - min; // guaranteed positive
            double charWorth = range / (double) barLength;
            
            double valueAccumulated = min; // to keep track of how much of the bar we've constructed.
            boolean hitCritical = false; // to prevent 2 criticalChars from being added
            
            for(int i = 0; i < barLength; ++i) {
                valueAccumulated += charWorth;
                
                if((Math.abs(valueAccumulated - criticalValue) < charWorth) && !hitCritical) {
                    // if the critical value is pretty close, just print the criticalChar
                    bar += criticalChar;
                    hitCritical = true;
                } else {
                    // not a critical value
                    if(valueAccumulated <= value) {
                        // not there yet
                        bar += fillChar;
                    } else {
                        // passed it
                        bar += emptyChar;
                    }
                }
            }
            
            // closing bracket
            if(value > max) {
                bar += ">";
            } else {
                bar += "[";
            }
        } else {
            bar =  "[    :(     ]";
        }
        
        return bar;
    }
}
