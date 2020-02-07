package com.group21.continous_integration;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class GitRequestTest
{

    /*Test if the request class creates a json object and stores the variables correct */
    @Test
    public void requestTest()
    {

        String jsonCorrect = "{\"ref\": \"refs/heads/branch\",\"repository\":{\"name\":\"repo\",\"statuses_url\":\"www.status.com\",\"clone_url\":\"www.clone.com\"},\"head_commit\":{\"id\":\"hash\"},\"pusher\":{\"email\":\"test@test.com\",\"name\":\"johan\"}}";

        


        GitRequest correct = new GitRequest(jsonCorrect);

        System.out.println(correct.commit_hash);
        // The commit id is stored correctly
        assertEquals(correct.commit_hash, "hash");

         // The author is stored correctly
         assertEquals(correct.author, "j123asdasd2313ohan");

         // The clone url is stored correctly
         assertEquals(correct.cloneUrl, "www.clone.com");
         
         // The status url is stored correctly
         assertEquals(correct.statuses_url, "www.status.com");

         // The repository is stored correctly
         assertEquals(correct.repository,"repo");

         // The branch refference is stored correctly
         assertEquals(correct.branch, "refs/heads/branch");

         // The email is stored correctly
         assertEquals(correct.email_addr, "test@test.com");
    }
}
