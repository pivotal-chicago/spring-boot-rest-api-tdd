import io.pivotal.chicago.Application;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class SubmittingAPost extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUpMockMvc() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void savesThePostInTheDatabase() throws Exception {
        int count = countRowsInTable("posts");

        mockMvc.perform(post("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\" : \"The title\"}"))
                .andExpect(status().is2xxSuccessful());

        assertThat(countRowsInTable("posts"), is(count + 1));
    }
}
