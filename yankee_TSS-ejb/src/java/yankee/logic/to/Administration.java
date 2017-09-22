/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.to;

import yankee.entities.*;
import yankee.logic.ENUM.GermanyStatesEnum;


public class Administration extends Named {
    private static final long serialVersionUID = 1L;
    
    private GermanyStatesEnum germanState; 

    public Administration(String uuid, String name) {
        super(uuid, name);
    }
    
    public GermanyStatesEnum getGermanState() {
        return germanState;
    }

    public void setGermanState(GermanyStatesEnum germanState) {
        this.germanState = germanState;
    }
  

    
}
