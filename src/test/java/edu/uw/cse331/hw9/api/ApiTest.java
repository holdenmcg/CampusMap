package edu.uw.cse331.hw9.api;

import edu.uw.cse331.hw9.model.Kernel;
import edu.uw.cse331.hw9.model.RequestModel;
import edu.uw.cse331.hw9.service.ConvolutionService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PublicApi.class)
public class ApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConvolutionService service;

    @Test
    public void basicConvolveTest() throws Exception {

        RequestModel model = new RequestModel();
        model.setKernel(Kernel.BLUR);
        model.setUrl("https://upload.wikimedia.org/wikipedia/commons/6/64/The_Puppy.jpg");

        InputStream in = new FileInputStream(new File("src/test/resources/puppy_blur.jpg"));
        byte[] bytes = IOUtils.toByteArray(in);

        given(service.convolve(model)).willReturn(bytes);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/convolve")
                .content("{\"kernel\": \"BLUR\", \"stride\": 1, \"url\": \"https://upload.wikimedia.org/wikipedia/commons/6/64/The_Puppy.jpg\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

}