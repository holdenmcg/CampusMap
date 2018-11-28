package edu.uw.cse331.hw9.service;

import edu.uw.cse331.hw9.model.Kernel;
import edu.uw.cse331.hw9.model.RequestModel;
import edu.uw.cse331.hw9.model.ServerSideException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class ConvolutionService {
    private final int KERNEL_SIZE = 3;

    private Logger logger = LoggerFactory.getLogger(ConvolutionService.class);

    /**
     * Clones the provided image
     * @param image image to be cloned
     * @return cloned image
     */
    @SuppressWarnings("argument.type.incompatible")
    private BufferedImage clone(BufferedImage image) {
        BufferedImage clone = new BufferedImage(image.getWidth(),
                image.getHeight(), image.getType());
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return clone;
    }

    /**
     * Get pixel values for all channels, clamp row and col respectively
     * @param row 0 based index for the row number of the pixel
     * @param col 0 based index for the col number of the pixel
     * @return a double array, the ith element of which represents ith channel's value for the pixel
     */
    @SuppressWarnings("argument.type.incompatible")
    private double[] getPixel(BufferedImage imRead, int row, int col, SampleModel sm, DataBuffer dbuf) {
        int height = imRead.getHeight();
        int width = imRead.getWidth();

        if (row >= height) row = height - 1;
        if (row < 0) row = 0;
        if (col >= width) col = width - 1;
        if (col < 0) col = 0;

        return sm.getPixel(col, row, (double[])null, dbuf);
    }

    /**
     * Set pixel values for all channels
     * @param row 0 based index for the row number of the pixel
     * @param col 0 based index for the col number of the pixel
     * @param vals a double array, the ith element of which represents ith channel's value for the pixel
     * @throws IllegalArgumentException if row or col out of bounds
     */
    private void setPixel(BufferedImage imWrite, int row, int col, double[] vals) {
        int height = imWrite.getHeight();
        int width = imWrite.getWidth();

        if (row >= height || row < 0 || col >= width || col < 0) {
            throw new IllegalArgumentException("row and col out of bounds");
        }
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] < 0) vals[i] = 0;
            else if (vals[i] > 255) vals[i] = 255;
        }
        imWrite.getRaster().setPixel(col, row, vals);
    }


    /**
     * Do image convolution with the following parameters
     * @param imRead image to read pixels from
     * @param imWrite image to write pixels to
     * @param stride how much to move kernel during convolution
     * @param k the type of kernel to do convolution with
     * @param sm sample model of imRead
     * @param dbuf data buffer of imRead
     */
    private void convolve(BufferedImage imRead, BufferedImage imWrite, int stride, Kernel k, SampleModel sm, DataBuffer dbuf) {
        int height = imRead.getHeight();
        int width = imRead.getWidth();
        int numChannels = imRead.getSampleModel().getNumBands();

        for (int row = 0; row < height; row += stride) {
            for (int col = 0; col < width; col += stride) {
                SortedMap<Integer, double[]> pos2px = new TreeMap<>();
                for (int y = row - KERNEL_SIZE/2, pos = 0; y <= row + KERNEL_SIZE/2; y++) {
                    for (int x = col - KERNEL_SIZE/2; x <= col + KERNEL_SIZE/2; x++, pos++) {
                        pos2px.put(pos, getPixel(imRead, y, x, sm, dbuf));

                    }
                }
                assert pos2px.size() == KERNEL_SIZE*KERNEL_SIZE;
                double[] newPixel = new double[numChannels];
                for (int c = 0; c < numChannels; c++) {
                    double[] pixelsInKernel = new double[KERNEL_SIZE*KERNEL_SIZE];
                    for (Integer pos : pos2px.keySet()) {
                        double[] pixels = pos2px.get(pos);
                        pixelsInKernel[pos] = pixels[c];
                    }
                    newPixel[c] = multArray(pixelsInKernel, k.getKernel());
                }
                setPixel(imWrite, row, col, newPixel);
            }
        }
    }

    /**
     *
     * @param a1
     * @param a2
     * @return the dot product of two arrays a1 and a2
     */
    private double multArray(double[] a1, double[] a2) {
        assert a1.length == a2.length;
        double sum = 0.0;
        for (int i = 0; i < a1.length; i++) {
            sum += a1[i] * a2[i];
        }
        return sum;
    }

    /**
     * Convolves the image provided in the model with the other settings provided as
     * part of the model
     *
     * @param model model containing the image URL and settings for convolution
     * @return Byte Array representation of the convolved images
     * @throws ServerSideException if URL is malformed, image cannot be read, or
     * there are IO errors
     */
    public byte[] convolve(RequestModel model) throws ServerSideException {
        try {
            URL url = new URL(model.getUrl());
            BufferedImage im = ImageIO.read(url);
            BufferedImage imWrite = im;
            BufferedImage imRead = clone(im);
            SampleModel sm = im.getSampleModel();
            DataBuffer dbuf = im.getData().getDataBuffer();

            File tmpfile = File.createTempFile("file", ".jpg");
            convolve(imRead, imWrite, 1, model.getKernel(), sm, dbuf);
            ImageIO.write(im, "jpg", tmpfile);
            InputStream in = new FileInputStream(tmpfile);
            return IOUtils.toByteArray(in);
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            throw new ServerSideException(e.getMessage());
        }
    }
}

