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
     * Returns a bar that looks something like this:
     * 
     * [==========________________]
     *  |<-     barLength      ->|
     *  
     * = is the fillChar, and _ is the emmptyChar
     * 
     * @param barLength Length of the data portion of the bar (shown above). The total length of the string returned
     *                  will be barLength + 2 once brackets are added.
     * @param fillChar The character to use for representing a filled section of the bar
     * @param emptyChar The character to use to represent empty space
     * @param min The minimum value of the bar
     * @param max The maximum value of the bar
     * @param value The number for this bar to display
     * @return A string containing the beautiful bar
     */
    public static String buildInventoryBar(int barLength, char fillChar, char emptyChar, double min, double max, double value) {
        String bar = "";
        
        // validate the arguments first
        if((barLength > 0) && (min < max) && (min <= value) && (value <= max)) {
            // add an opening bracket
            bar += "[";
            
            double range = max - min; // guaranteed positive
            double charWorth = range / (double) barLength;
            
            double valueAccumulated = min; // to keep track of how much of the bar we've constructed.
            
            for(int i = 0; i < barLength; ++i) {
                valueAccumulated += charWorth;
                if(valueAccumulated <= value) {
                    bar += fillChar;
                } else {
                    bar += emptyChar;
                }
            }
            
            // closing bracket
            bar += "]"; 
        } else {
            bar =  "[    :(     ]";
        }
        
        return bar;
    }
    
    /**
     * Builds a beautiful bar (shown above) with a highlghted critical value.
     *  
     * [===!======________________]
     *  |<-     barLength      ->|
     *  
     * = is the fillChar, _ is the emmptyChar, and ! is the criticalChar
     * 
     * @param barLength Length of the data portion of the bar (shown above). The total length of the string returned
     *                  will be barLength + 2 once brackets are added.
     * @param fillChar The character to use for representing a filled section of the bar
     * @param emptyChar The character to use to represent empty space
     * @param criticalChar The character to use to represent the critical value
     * @param min The minimum value of the bar
     * @param max The maximum value of the bar
     * @param value The number for this bar to display
     * @param criticalValue The critical value to print the criticalChar at
     * @return A beautiful bar, guaranteed fresh
     */
    public static String buildInventoryBarWithCritical(int barLength, char fillChar, char emptyChar, char criticalChar, double min, double max, double value, double criticalValue) {

        String bar;
        // validate arguments
        if((barLength > 0) && (min < max) && (min <= value) && (value <= max) &&
                (criticalValue >= min) && (criticalValue <= max)) {
            bar = buildInventoryBar(barLength, fillChar, emptyChar, min, max, value);

            // let the other method do the hard work, and replace a char where appropriate.
            double range = max - min; // guaranteed positive
            double charWorth = range / (double) barLength;
            
            int indexToReplace = (int) (charWorth % criticalValue);
            indexToReplace += 1; // for the beginning bracket
            
            char[] explodedBar = bar.toCharArray();
            explodedBar[indexToReplace] = criticalChar;
            bar =  String.valueOf(explodedBar);
            
            
        } else {
            // you stupid
            bar =  "[    :(     ]";
        }
        
        return bar;
        
    }
    
}
