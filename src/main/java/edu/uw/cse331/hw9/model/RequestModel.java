package edu.uw.cse331.hw9.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Class that JSON requests for convolution are converted into
 */
public class RequestModel {

    /**
     * URL at which the image is stored
     */
    private String url;

    /**
     * Kernel to be used
     */
    private Kernel kernel;

    /**
     * Returns the URL of the image to be used
     * @return the URL of the image to be used
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the image to be used
     * @param url the URL of the image to be used
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Kernel getKernel() {
        return kernel;
    }

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestModel that = (RequestModel) o;
        return Objects.equal(url, that.url) &&
                kernel == that.kernel;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, kernel);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("kernel", kernel)
                .toString();
    }
}
