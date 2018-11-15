package edu.uw.cse331.hw9.model;

/**
 * Kernel is a type of operation that can be performed on an image
 */
public enum Kernel {
    IDENTITY(new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0}),
    SHARP(new double[]{0.0, -1.0, 0.0, -1.0, 5.0, -1.0, 0.0, -1.0, 0.0}),
    BLUR(new double[]{1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9}),
    EDGE(new double[]{-1.0, -1.0, -1.0, -1.0, 8.0, -1.0, -1.0, -1.0, -1.0});

    /**
     * Internal representation of the kernel
     */
    private double[] kernel;

    /**
     * Creates a new Kernel with the given setup
     * @param kernel configuration with which the Kernel is setup
     */
    Kernel(double[] kernel) {
        this.kernel = kernel;
    }

    /**
     * Returns the kernel's mathematical representation
     *
     * @return the kernel's mathematical representation
     */
    public double[] getKernel() {
        return kernel;
    }
}