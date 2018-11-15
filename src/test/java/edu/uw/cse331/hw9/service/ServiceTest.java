package edu.uw.cse331.hw9.service;

import edu.uw.cse331.hw9.model.Kernel;
import edu.uw.cse331.hw9.model.RequestModel;
import edu.uw.cse331.hw9.model.ServerSideException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private ConvolutionService convolutionService;

    @Test
    public void convolve() throws ServerSideException, IOException {
        RequestModel model= new RequestModel();
        model.setKernel(Kernel.BLUR);
        model.setUrl("https://upload.wikimedia.org/wikipedia/commons/6/64/The_Puppy.jpg");

        InputStream in = new FileInputStream(new File("src/test/resources/puppy_blur.jpg"));
        assertArrayEquals(convolutionService.convolve(model), IOUtils.toByteArray(in));
    }

}
