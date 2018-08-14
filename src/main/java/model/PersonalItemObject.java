package model;

/**
 * This class stores all of the information needed by the list page freemarker template.
 * @author dcodeh
 */
public class PersonalItemObject implements java.io.Serializable {

    /*
     * Used to determine if the serialized objects are compatible at deserialization time.
     * This should be changed any time serialized fields are changed!
     * 
     * History:
     * Version  Value       Notes
     * 0.1      1L          Initial version
     */
    private static final long serialVersionUID = 1L;
    
    private String line;
    private boolean checked;
    
    public PersonalItemObject(String line, boolean checked) {
        this.line = line;
    }
    
    public boolean getIsChecked() {
        return this.checked;
    }
    
    public String getLine() {
        return this.line;
    }

}
