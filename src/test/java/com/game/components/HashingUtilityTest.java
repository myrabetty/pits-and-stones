/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elisabetta
 */
public class HashingUtilityTest {

    
    String message;
    
    @Before 
    public void setUp(){
        message = "This is a test mssage";
    }        
    /**
     * Test of generateMac method, of class HashingUtility.
     */
    @Test
    public void testGenerateMac() {
        System.out.println("generateMac");
        String result = HashingUtility.generateMac(message);
        String result1 = HashingUtility.generateMac(message);
        assertEquals(result1, result);
    }

    /**
     * Test of verifyMac method, of class HashingUtility.
     */
    @Test
    public void testVerifyMacSuccess() {
        System.out.println("verifyMac");
        String mac = HashingUtility.generateMac(message);
        HashingUtility.verifyMac(message, mac); 
    }
        /**
     * Test of verifyMac method, of class HashingUtility.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testVerifyMacFail() {
        System.out.println("verifyMac");
        String mac = HashingUtility.generateMac("This is a different test message");
        HashingUtility.verifyMac(message, mac); 
    }
    
}
