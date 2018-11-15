package edu.uw.cse331.hw9.api;

import edu.uw.cse331.hw9.model.RequestModel;
import edu.uw.cse331.hw9.model.ServerSideException;
import edu.uw.cse331.hw9.service.ConvolutionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class controls the Rest API for the Application
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class PublicApi {

    /**
     * Created using the @Service on the Convolution Service
     */
    @Autowired
    ConvolutionService convolutionService;

    /**
     * Convolves the image with the provided settings
     *
     * @param model RequestModel representing the image URL and the settings
     * @return convolved image
     * @throws ServerSideException in case of a processing error
     */
    @ApiOperation("Convolves the provided image with the provided settings")
    @PostMapping(value = "/convolve", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] convolve(@RequestBody RequestModel model) throws ServerSideException {
        return convolutionService.convolve(model);
    }


}
