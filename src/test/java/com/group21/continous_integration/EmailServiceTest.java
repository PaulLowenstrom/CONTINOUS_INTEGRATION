package com.group21.continous_integration;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple Email Service.
 */
public class EmailServiceTest
{

    /*Checks if email functions work correctly */
    @Test
    public void EmailCheck()
    {
         // Checks that email service SendBuildSuccesfull works
         assertTrue(EmailService.SendBuildSuccessfull("noreply@test.se", "branch"));

         // Checks that email service SendBuildFailure works
         assertTrue(EmailService.SendBuildFailure("noreply@test.se", "branch"));
    }
}
