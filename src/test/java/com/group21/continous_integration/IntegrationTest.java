package com.group21.continous_integration;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class IntegrationTest
{

    // Self testing
    // ============
    @Test
    public void dogfood()
    {
        String repoName = "CONTINOUS_INTEGRATION";
        String cloneUrl = "https://github.com/perfah/CONTINOUS_INTEGRATION.git";
        String commit = "cbd65ee1ecc826a078a1fbd507f720a7a6a858b5";
        String branch = "master";

        String json = "{\"ref\": \"ref/heads/" + branch + "\",\"repository\":{\"name\":\"" + repoName + "\",\"statuses_url\":\"www.status.com\",\"clone_url\":\"" + cloneUrl + "\"},\"head_commit\":{\"id\":\"" + commit +  "\"},\"pusher\":{\"email\":\"test@test.com\",\"name\":\"johan\"}}";

        GitRequest req = new GitRequest(json);

        Integration integ = new Integration(req);

        // Test that compilation succeeds:
        BuildResult compilation = integ.build();
        assertTrue(compilation.status);

        // No self testing since that would infinite-loop..
    }

    // Testing of branch assesments
    // ============================
    public void assessment()
    {
        String repoName = "CONTINOUS_INTEGRATION";
        String cloneUrl = "https://github.com/perfah/CONTINOUS_INTEGRATION.git";
        String commit = "cbd65ee1ecc826a078a1fbd507f720a7a6a858b5";
        String branch = "assesments";

        String json = "{\"ref\": \"ref/heads/" + branch + "\",\"repository\":{\"name\":\"" + repoName + "\",\"statuses_url\":\"www.status.com\",\"clone_url\":\"" + cloneUrl + "\"},\"head_commit\":{\"id\":\"" + commit +  "\"},\"pusher\":{\"email\":\"test@test.com\",\"name\":\"johan\"}}";

        GitRequest req = new GitRequest(json);

        Integration integ = new Integration(req);

        // Test that compilation succeeds:
        BuildResult compilation = integ.build();
        assertTrue(compilation.status);

        // Test that testing succeeds:
        boolean testing = integ.test();
        assertTrue(testing);
    }
}