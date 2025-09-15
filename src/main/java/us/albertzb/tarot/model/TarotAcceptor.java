/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package us.albertzb.tarot.model;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public interface TarotAcceptor {
    void accept(TarotCardVisitor visitor);
}
