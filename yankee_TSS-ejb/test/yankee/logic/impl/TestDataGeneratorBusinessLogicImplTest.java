/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author T540P
 */
public class TestDataGeneratorBusinessLogicImplTest {
    
    public TestDataGeneratorBusinessLogicImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateTestData method, of class TestDataGeneratorBusinessLogicImpl.
     */
    @Test
    public void testGenerateTestData() throws Exception {
        System.out.println("generateTestData");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        TestDataGeneratorBusinessLogic instance = (TestDataGeneratorBusinessLogic)container.getContext().lookup("java:global/classes/TestDataGeneratorBusinessLogicImpl");
        instance.generateTestData();
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
