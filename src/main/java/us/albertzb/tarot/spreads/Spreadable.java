/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package us.albertzb.tarot.spreads;

import java.util.List;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public interface Spreadable {

    int getCardCount();

    List<String> getPositions();
    
    default int getPositionCount() {
        return getPositions().size();
    }
    
    String getSubTitle();
    
    String getTitle();
    
}
