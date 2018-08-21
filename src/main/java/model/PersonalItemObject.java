/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

import java.util.Objects;

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
    
    @Override
    public int hashCode() {
        return Objects.hash(line, checked);
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof PersonalItemObject) {
            PersonalItemObject that = (PersonalItemObject) o;
            
            return this.line.equals(that.getLine()) &&
                   this.checked == that.getIsChecked();
        } else {
            return false;
        }
    }

}
