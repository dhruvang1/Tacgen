import java.awt.*;
import java.util.ArrayList;

public class LSD {
/*----------------------------------------------------------------------------*/

    /**
     * ln(10)
     */
    private double M_LN10 = 2.30258509299404568402;

    /**
     * PI
     */
    private double M_PI = 3.14159265358979323846;

    private int FALSE = 0;

    private int TRUE = 1;

    /**
     * Label for pixels with undefined gradient.
     */
    private double NOTDEF = -1024.0;

    /**
     * 3/2 pi
     */
    private double M_3_2_PI = 4.71238898038;

    /**
     * 2 pi
     */
    private double M_2__PI = 6.28318530718;

    /**
     * Label for pixels not used in yet.
     */
    private int NOTUSED = 0;

    /**
     * Label for pixels already used in detection.
     */
    private int USED = 1;

/*----------------------------------------------------------------------------*/

    /**
     * Chained list of coordinates.
     */
    class coorlist {
        int x, y;
        coorlist next;
    }

    ;

/*----------------------------------------------------------------------------*/
/*------------------------- Miscellaneous functions --------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/

    /**
     * Fatal error, print a message to standard-error output and exit.
     */
    private void error(String msg) {
        System.err.println("LSD Error: " + msg);
        System.exit(-1);
    }

/*----------------------------------------------------------------------------*/
    /**
     * Doubles relative error factor
     */
    double RELATIVE_ERROR_FACTOR = 100.0;

/*----------------------------------------------------------------------------*/

    /**
     * Compare doubles by relative error.
     * <p>
     * The resulting rounding error after floating point computations
     * depend on the specific operations done. The same number computed by
     * different algorithms could present different rounding errors. For a
     * useful comparison, an estimation of the relative rounding error
     * should be considered and compared to a factor times EPS. The factor
     * should be related to the cumulated rounding error in the chain of
     * computation. Here, as a simplification, a fixed factor is used.
     */
     private boolean double_equal(double a, double b) {
        double abs_diff, aa, bb, abs_max;

        /* trivial case */
        if (a == b) return true;

        abs_diff = Math.abs(a - b);
        aa = Math.abs(a);
        bb = Math.abs(b);
        abs_max = aa > bb ? aa : bb;

  /* DBL_MIN is the smallest normalized number, thus, the smallest
     number whose relative error is bounded by DBL_EPSILON. For
     smaller numbers, the same quantization steps as for DBL_MIN
     are used. Then, for smaller numbers, a meaningful "relative"
     error should be computed by dividing the difference by DBL_MIN. */
        if (abs_max < Double.MIN_VALUE) abs_max = Double.MIN_VALUE;

  /* equal if relative error <= factor x eps */
        return (abs_diff / abs_max) <= (RELATIVE_ERROR_FACTOR * Math.ulp(1.0));
    }

/*----------------------------------------------------------------------------*/

    /**
     * Computes Euclidean distance between point (x1,y1) and point (x2,y2).
     */
    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }


/*----------------------------------------------------------------------------*/
/*----------------------- 'list of n-tuple' data type ------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
    /**
     * 'list of n-tuple' data type
     * <p>
     * The i-th component of the j-th n-tuple of an n-tuple list 'ntl'
     * is accessed with:
     * <p>
     * ntl->values[ i + j * ntl->dim ]
     * <p>
     * The dimension of the n-tuple (n) is:
     * <p>
     * ntl->dim
     * <p>
     * The number of n-tuples in the list is:
     * <p>
     * ntl->size
     * <p>
     * The maximum number of n-tuples that can be stored in the
     * list with the allocated memory at a given time is given by:
     * <p>
     * ntl->max_size
     */
    class ntuple_list {
        int dim;
        ArrayList<Double> values;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Free memory used in n-tuple 'in'.
     */
    private void free_ntuple_list(ntuple_list in) {
        if (in == null || in.values == null)
            error("free_ntuple_list: invalid n-tuple input.");
        else
            in.values.clear();
    }

/*----------------------------------------------------------------------------*/

    /**
     * Create an n-tuple list and allocate memory for one element.
     *
     * @param dim the dimension (n) of the n-tuple.
     */
    private ntuple_list new_ntuple_list(int dim) {
        ntuple_list n_tuple;

        /* check parameters */
        if (dim == 0) error("new_ntuple_list: 'dim' must be positive.");

        /* get memory for list structure */
        n_tuple = new ntuple_list();

        /* initialize list */
        n_tuple.dim = dim;

        /* get memory for tuples */
        n_tuple.values = new ArrayList<>();

        return n_tuple;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Add a 7-tuple to an n-tuple list.
     */
    private void add_7tuple(ntuple_list out, double v1, double v2, double v3,
                           double v4, double v5, double v6, double v7) {
        /* check parameters */
        if (out == null) error("add_7tuple: invalid n-tuple input.");
        if (out.dim != 7) error("add_7tuple: the n-tuple must be a 7-tuple.");

        if (out.values == null) error("add_7tuple: invalid n-tuple input.");

        /* add new 7-tuple */
        out.values.add(v1);
        out.values.add(v2);
        out.values.add(v3);
        out.values.add(v4);
        out.values.add(v5);
        out.values.add(v6);
        out.values.add(v7);
    }


/*----------------------------------------------------------------------------*/
/*----------------------------- Image Data Types -----------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
    /**
     * char image data type
     * <p>
     * The pixel value at (x,y) is accessed by:
     * <p>
     * image->data[ x + y * image->xsize ]
     * <p>
     * with x and y integer.
     */
    class image_char {
        char[] data;
        int xsize, ysize;
    }


/*----------------------------------------------------------------------------*/

    /**
     * Create a new image_char of size 'xsize' times 'ysize'.
     */
    private image_char new_image_char(int xsize, int ysize) {
        image_char image;

        /* check parameters */
        if (xsize == 0 || ysize == 0) error("new_image_char: invalid image size.");

        /* get memory */
        image = new image_char();

        /* initialize data */
        image.data = new char[xsize * ysize];

        /* set image size */
        image.xsize = xsize;
        image.ysize = ysize;

        return image;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Create a new image_char of size 'xsize' times 'ysize',
     * initialized to the value 'fill_value'.
     */
    private image_char new_image_char_ini(int xsize, int ysize,
                                         char fill_value) {
        image_char image = new_image_char(xsize, ysize); /* create image */
        int N = xsize * ysize;
        int i;

        /* check parameters */
        if (image == null || image.data == null)
            error("new_image_char_ini: invalid image.");


        /* initialize */
        for (i = 0; i < N; i++)
            image.data[i] = fill_value;

        return image;
    }

/*----------------------------------------------------------------------------*/
    /**
     * int image data type
     * <p>
     * The pixel value at (x,y) is accessed by:
     * <p>
     * image->data[ x + y * image->xsize ]
     * <p>
     * with x and y integer.
     */
    class image_int {
        int[] data;
        int xsize, ysize;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Create a new image_int of size 'xsize' times 'ysize'.
     */
    private image_int new_image_int(int xsize, int ysize) {
        image_int image;

        /* check parameters */
        if (xsize == 0 || ysize == 0) error("new_image_int: invalid image size.");

        /* get memory */
        image = new image_int();
        image.data = new int[xsize*ysize];

        /* set image size */
        image.xsize = xsize;
        image.ysize = ysize;

        return image;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Create a new image_int of size 'xsize' times 'ysize',
     * initialized to the value 'fill_value'.
     */
    private image_int new_image_int_ini(int xsize, int ysize,
                                       int fill_value) {
        image_int image = new_image_int(xsize, ysize); /* create image */
        int N = xsize * ysize;
        int i;

        /* initialize */
        for (i = 0; i < N; i++)
            image.data[i] = fill_value;

        return image;
    }

/*----------------------------------------------------------------------------*/
    /**
     * double image data type
     * <p>
     * The pixel value at (x,y) is accessed by:
     * <p>
     * image->data[ x + y * image->xsize ]
     * <p>
     * with x and y integer.
     */
    class image_double {
        double[] data;
        int xsize, ysize;
    }


/*----------------------------------------------------------------------------*/

    /**
     * Create a new image_double of size 'xsize' times 'ysize'.
     */
    private image_double new_image_double(int xsize, int ysize) {
        image_double image;

        /* check parameters */
        if (xsize == 0 || ysize == 0) error("new_image_double: invalid image size.");

        /* get memory */
        image = new image_double();
        image.data = new double[xsize*ysize];

        /* set image size */
        image.xsize = xsize;
        image.ysize = ysize;

        return image;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Create a new image_double of size 'xsize' times 'ysize'
     * with the data pointed by 'data'.
     */
    private image_double new_image_double_ptr(int xsize,
                                             int ysize, double[] data) {
        image_double image;

        /* check parameters */
        if (xsize == 0 || ysize == 0)
            error("new_image_double_ptr: invalid image size.");
        if (data == null) error("new_image_double_ptr: null data pointer.");

        /* get memory */
        image = new image_double();

        /* set image */
        image.xsize = xsize;
        image.ysize = ysize;
        image.data = data;

        return image;
    }


/*----------------------------------------------------------------------------*/
/*----------------------------- Gaussian filter ------------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/

    /**
     * Compute a Gaussian kernel of length 'kernel->dim',
     * standard deviation 'sigma', and centered at value 'mean'.
     * <p>
     * For example, if mean=0.5, the Gaussian will be centered
     * in the middle point between values 'kernel->values[0]'
     * and 'kernel->values[1]'.
     */
    private void gaussian_kernel(ntuple_list kernel, double sigma, double mean) {
        double sum = 0.0;
        double val;
        int i;

        /* check parameters */
        if (kernel == null || kernel.values == null)
            error("gaussian_kernel: invalid n-tuple 'kernel'.");
        if (sigma <= 0.0) error("gaussian_kernel: 'sigma' must be positive.");

        /* compute Gaussian kernel */
        for (i = 0; i < kernel.dim; i++) {
            val = ((double) i - mean) / sigma;
            kernel.values.add(Math.exp(-0.5 * val * val));
            sum += kernel.values.get(kernel.values.size()-1);
        }

        /* normalization */
        if (sum >= 0.0) for (i = 0; i < kernel.dim; i++)
            kernel.values.set(i,kernel.values.get(i)/sum);
    }

/*----------------------------------------------------------------------------*/

    /**
     * Scale the input image 'in' by a factor 'scale' by Gaussian sub-sampling.
     * <p>
     * For example, scale=0.8 will give a result at 80% of the original size.
     * <p>
     * The image is convolved with a Gaussian kernel
     *
     * @f[ G(x, y) = \frac{1}{2\pi\sigma^2} e^{-\frac{x^2+y^2}{2\sigma^2}}
     * @f] before the sub-sampling to prevent aliasing.
     * <p>
     * The standard deviation sigma given by:
     * -  sigma = sigma_scale / scale,   if scale <  1.0
     * -  sigma = sigma_scale,           if scale >= 1.0
     * <p>
     * To be able to sub-sample at non-integer steps, some interpolation
     * is needed. In this implementation, the interpolation is done by
     * the Gaussian kernel, so both operations (filtering and sampling)
     * are done at the same time. The Gaussian kernel is computed
     * centered on the coordinates of the required sample. In this way,
     * when applied, it gives directly the result of convolving the image
     * with the kernel and interpolated to that particular position.
     * <p>
     * A fast algorithm is done using the separability of the Gaussian
     * kernel. Applying the 2D Gaussian kernel is equivalent to applying
     * first a horizontal 1D Gaussian kernel and then a vertical 1D
     * Gaussian kernel (or the other way round). The reason is that
     * @f[ G(x, y) = G(x) * G(y)
     * @f] where
     * @f[ G(x) = \frac{1}{\sqrt{2\pi}\sigma} e^{-\frac{x^2}{2\sigma^2}}.
     * @f] The algorithm first applies a combined Gaussian kernel and sampling
     * in the x axis, and then the combined Gaussian kernel and sampling
     * in the y axis.
     */
    private image_double gaussian_sampler(image_double in, double scale,
                                         double sigma_scale) {
        image_double aux, out;
        ntuple_list kernel;
        int N, M, h, n, x, y, i;
        int xc, yc, j, double_x_size, double_y_size;
        double sigma, xx, yy, sum, prec;

        /* check parameters */
        if (in == null || in.data == null || in.xsize == 0 || in.ysize == 0)
            error("gaussian_sampler: invalid image.");
        if (scale <= 0.0) error("gaussian_sampler: 'scale' must be positive.");
        if (sigma_scale <= 0.0)
            error("gaussian_sampler: 'sigma_scale' must be positive.");

        /* compute new image size and get memory for images */
        if (in.xsize * scale > (double) Integer.MAX_VALUE ||
                in.ysize * scale > (double) Integer.MAX_VALUE)
            error("gaussian_sampler: the output image size exceeds the handled size.");
        N = (int)Math.ceil(in.xsize * scale);
        M = (int)Math.ceil(in.ysize * scale);
        aux = new_image_double(N, in.ysize);
        out = new_image_double(N, M);

        /* sigma, kernel size and memory for the kernel */
        sigma = scale < 1.0 ? sigma_scale / scale : sigma_scale;
        /*
         The size of the kernel is selected to guarantee that the
         the first discarded term is at least 10^prec times smaller
         than the central value. For that, h should be larger than x, with
           e^(-x^2/2sigma^2) = 1/10^prec.
         Then,
           x = sigma * sqrt( 2 * prec * ln(10) ).
        */
        prec = 3.0;
        h = (int)Math.ceil(sigma * Math.sqrt(2.0 * prec * Math.log(10.0)));
        n = 1 + 2 * h; /* kernel size */
        kernel = new_ntuple_list(n);

        /* auxiliary double image size variables */
        double_x_size = (int) (2 * in.xsize);
        double_y_size = (int) (2 * in.ysize);

        /* First subsampling: x axis */
        for (x = 0; x < aux.xsize; x++) {
      /*
         x   is the coordinate in the new image.
         xx  is the corresponding x-value in the original size image.
         xc  is the integer value, the pixel coordinate of xx.
       */
            xx = (double) x / scale;
      /* coordinate (0.0,0.0) is in the center of pixel (0,0),
         so the pixel with xc=0 get the values of xx from -0.5 to 0.5 */
            xc = (int)Math.floor(xx + 0.5);
            gaussian_kernel(kernel, sigma, (double) h + xx - (double) xc);
      /* the kernel must be computed for each x because the fine
         offset xx-xc is different in each case */

            for (y = 0; y < aux.ysize; y++) {
                sum = 0.0;
                for (i = 0; i < kernel.dim; i++) {
                    j = xc - h + i;

              /* symmetry boundary condition */
                    while (j < 0) j += double_x_size;
                    while (j >= double_x_size) j -= double_x_size;
                    if (j >= (int) in.xsize) j = double_x_size - 1 - j;

                    sum += in.data[j + y * in.xsize] * kernel.values.get(i);
                }
                aux.data[ x + y * aux.xsize ] = sum;
            }
        }

  /* Second subsampling: y axis */
        for (y = 0; y < out.ysize; y++) {
      /*
         y   is the coordinate in the new image.
         yy  is the corresponding x-value in the original size image.
         yc  is the integer value, the pixel coordinate of xx.
       */
            yy = (double) y / scale;
      /* coordinate (0.0,0.0) is in the center of pixel (0,0),
         so the pixel with yc=0 get the values of yy from -0.5 to 0.5 */
            yc = (int) Math.floor(yy + 0.5);
            gaussian_kernel(kernel, sigma, (double) h + yy - (double) yc);
      /* the kernel must be computed for each y because the fine
         offset yy-yc is different in each case */

            for (x = 0; x < out.xsize; x++) {
                sum = 0.0;
                for (i = 0; i < kernel.dim; i++) {
                    j = yc - h + i;

              /* symmetry boundary condition */
                    while (j < 0) j += double_y_size;
                    while (j >= double_y_size) j -= double_y_size;
                    if (j >= (int) in.ysize) j = double_y_size - 1 - j;

                    sum += aux.data[x + j * aux.xsize] * kernel.values.get(i);
                }
                out.data[x + y * out.xsize] = sum;
            }
        }

        /* free memory */
        free_ntuple_list(kernel);
        return out;
    }


/*----------------------------------------------------------------------------*/
/*--------------------------------- Gradient ---------------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/

    /**
     * Computes the direction of the level line of 'in' at each point.
     * <p>
     * The result is:
     * - an image_double with the angle at each pixel, or NOTDEF if not defined.
     * - the image_double 'modgrad' (a pointer is passed as argument)
     * with the gradient magnitude at each point.
     * - a list of pixels 'list_p' roughly ordered by decreasing
     * gradient magnitude. (The order is made by classifying points
     * into bins by gradient magnitude. The parameters 'n_bins' and
     * 'max_grad' specify the number of bins and the gradient modulus
     * at the highest bin. The pixels in the list would be in
     * decreasing gradient magnitude, up to a precision of the size of
     * the bins.)
     * - a pointer 'mem_p' to the memory used by 'list_p' to be able to
     * free the memory when it is not used anymore.
     */
    private image_double ll_angle(image_double in, double threshold,
                                  coorlist list_p,
                                 image_double modgrad, int n_bins) {
        image_double g;
        int n, p, x, y, adr, i;
        double com1, com2, gx, gy, norm, norm2;
        /* the rest of the variables are used for pseudo-ordering
         the gradient magnitude values */
        int list_count = 0;
        coorlist[] range_l_s; /* array of pointers to start of bin list */
        coorlist[] range_l_e; /* array of pointers to end of bin list */
        coorlist start;
        coorlist end;
        double max_grad = 0.0;

        /* check parameters */
        if (in == null || in.data == null || in.xsize == 0 || in.ysize == 0)
            error("ll_angle: invalid image.");
        if (threshold < 0.0) error("ll_angle: 'threshold' must be positive.");
        if (list_p == null) error("ll_angle: null pointer 'list_p'.");
        if (modgrad == null) error("ll_angle: null pointer 'modgrad'.");
        if (n_bins == 0) error("ll_angle: 'n_bins' must be positive.");

        /* image size shortcuts */
        n = in.ysize;
        p = in.xsize;

        /* allocate output image */
        g = new_image_double(in.xsize, in.ysize);

        /* get memory for the image of gradient modulus */
        modgrad = new_image_double(in.xsize, in.ysize);

        /* get memory for "ordered" list of pixels */

        range_l_s = new coorlist[n_bins];
        range_l_e = new coorlist[n_bins];
        for (i = 0; i < n_bins; i++)
            range_l_s[i] = range_l_e[i] = null;

        /* 'undefined' on the down and right boundaries */
        for (x = 0; x < p; x++) g.data[(n - 1) * p + x] = NOTDEF;
        for (y = 0; y < n; y++) g.data[p * y + p - 1] = NOTDEF;

        /* compute gradient on the remaining pixels */
        for (x = 0; x < p - 1; x++)
            for (y = 0; y < n - 1; y++) {
                adr = y * p + x;

        /*
           Norm 2 computation using 2x2 pixel window:
             A B
             C D
           and
             com1 = D-A,  com2 = B-C.
           Then
             gx = B+D - (A+C)   horizontal difference
             gy = C+D - (A+B)   vertical difference
           com1 and com2 are just to avoid 2 additions.
         */
                com1 = in.data[adr + p + 1] - in.data[adr];
                com2 = in.data[adr + 1] - in.data[adr + p];

                gx = com1 + com2; /* gradient x component */
                gy = com1 - com2; /* gradient y component */
                norm2 = gx * gx + gy * gy;
                norm = Math.sqrt(norm2 / 4.0); /* gradient norm */

                modgrad.data[adr] = norm; /* store gradient norm */

                if (norm <= threshold) /* norm too small, gradient no defined */
                    g.data[adr] = NOTDEF; /* gradient angle not defined */
                else {
            /* gradient angle computation */
                    g.data[adr] = Math.atan2(gx, -gy);

            /* look for the maximum of the gradient */
                    if (norm > max_grad) max_grad = norm;
                }
            }

        /* compute histogram of gradient values */
        for (x = 0; x < p - 1; x++)
            for (y = 0; y < n - 1; y++) {
                norm =  modgrad.data[y * p + x];

        /* store the point in the right bin according to its norm */
                i = (int)(norm * (double) n_bins / max_grad);
                if (i >= n_bins) i = n_bins - 1;
                if (range_l_e[i] == null)
                    range_l_s[i] = range_l_e[i] = new coorlist();
                else {
                    range_l_e[i].next = new coorlist();
                    range_l_e[i] = range_l_e[i].next;
                }
                range_l_e[i].x = (int) x;
                range_l_e[i].y = (int) y;
                range_l_e[i].next = null;
            }

  /* Make the list of pixels (almost) ordered by norm value.
     It starts by the larger bin, so the list starts by the
     pixels with the highest gradient value. Pixels would be ordered
     by norm value, up to a precision given by max_grad/n_bins.
   */
        for (i = n_bins - 1; i > 0 && range_l_s[i] == null; i--) ;
        start = range_l_s[i];
        end = range_l_e[i];
        if (start != null){
            while (i > 0) {
                --i;
                if (range_l_s[i] != null) {
                    end.next = range_l_s[i];
                    end = range_l_e[i];
                }
            }
        }
        list_p = start;

        return g;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Is point (x,y) aligned to angle theta, up to precision 'prec'?
     */
    private int isaligned(int x, int y, image_double angles, double theta,
                         double prec) {
        double a;

  /* check parameters */
        if (angles == null || angles.data == null)
            error("isaligned: invalid image 'angles'.");
        if (x < 0 || y < 0 || x >= (int) angles.xsize || y >= (int) angles.ysize)
            error("isaligned: (x,y) out of the image.");
        if (prec < 0.0) error("isaligned: 'prec' must be positive.");

  /* angle at pixel (x,y) */
        a = angles.data[x + y * angles.xsize];

  /* pixels whose level-line angle is not defined
     are considered as NON-aligned */
        if (a == NOTDEF) return FALSE;  /* there is no need to call the function
                                      'double_equal' here because there is
                                      no risk of problems related to the
                                      comparison doubles, we are only
                                      interested in the exact NOTDEF value */

  /* it is assumed that 'theta' and 'a' are in the range [-pi,pi] */
        theta -= a;
        if (theta < 0.0) theta = -theta;
        if (theta > M_3_2_PI) {
            theta -= M_2__PI;
            if (theta < 0.0) theta = -theta;
        }

        return theta <= prec ? TRUE: FALSE;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Absolute value angle difference.
     */
    private double angle_diff(double a, double b) {
        a -= b;
        while (a <= -M_PI) a += M_2__PI;
        while (a > M_PI) a -= M_2__PI;
        if (a < 0.0) a = -a;
        return a;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Signed angle difference.
     */
    private double angle_diff_signed(double a, double b) {
        a -= b;
        while (a <= -M_PI) a += M_2__PI;
        while (a > M_PI) a -= M_2__PI;
        return a;
    }


/*----------------------------------------------------------------------------*/
/*----------------------------- NFA computation ------------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/

    /**
     * Computes the natural logarithm of the absolute value of
     * the gamma function of x using the Lanczos approximation.
     * See http://www.rskey.org/gamma.htm
     * <p>
     * The formula used is
     *
     * @f[ \Gamma(x) = \frac{ \sum_{n=0}^{N} q_n x^n }{ \Pi_{n=0}^{N} (x+n) }
     * (x+5.5)^{x+0.5} e^{-(x+5.5)}
     * @f] so
     * @f[ \log\Gamma(x) = \log\left( \sum_{n=0}^{N} q_n x^n \right)
     * + (x+0.5) \log(x+5.5) - (x+5.5) - \sum_{n=0}^{N} \log(x+n)
     * @f] and
     * q0 = 75122.6331530,
     * q1 = 80916.6278952,
     * q2 = 36308.2951477,
     * q3 = 8687.24529705,
     * q4 = 1168.92649479,
     * q5 = 83.8676043424,
     * q6 = 2.50662827511.
     */
    private double log_gamma_lanczos(double x) {
        double q[] ={
            75122.6331530, 80916.6278952, 36308.2951477,
                    8687.24529705, 1168.92649479, 83.8676043424,
                    2.50662827511
        } ;
        double a = (x + 0.5) * Math.log(x + 5.5) - (x + 5.5);
        double b = 0.0;
        int n;

        for (n = 0; n < 7; n++) {
            a -= Math.log(x + (double) n);
            b += q[n] * Math.pow(x, (double) n);
        }
        return a + Math.log(b);
    }

/*----------------------------------------------------------------------------*/

    /**
     * Computes the natural logarithm of the absolute value of
     * the gamma function of x using Windschitl method.
     * See http://www.rskey.org/gamma.htm
     * <p>
     * The formula used is
     *
     * @f[ \Gamma(x) = \sqrt{\frac{2\pi}{x}} \left( \frac{x}{e}
     * \sqrt{ x\sinh(1/x) + \frac{1}{810x^6} } \right)^x
     * @f] so
     * @f[ \log\Gamma(x) = 0.5\log(2\pi) + (x-0.5)\log(x) - x
     * + 0.5x\log\left( x\sinh(1/x) + \frac{1}{810x^6} \right).
     * @f] This formula is a good approximation when x > 15.
     */
    private double log_gamma_windschitl(double x) {
        return 0.918938533204673 + (x - 0.5) * Math.log(x) - x
                + 0.5 * x * Math.log(x * Math.sinh(1 / x) + 1 / (810.0 * Math.pow(x, 6.0)));
    }

/*----------------------------------------------------------------------------*/
/** Computes the natural logarithm of the absolute value of
 the gamma function of x. When x>15 use log_gamma_windschitl(),
 otherwise use log_gamma_lanczos().
 */


    private double log_gamma(double x) {
        if(x > 15.0)
            return log_gamma_windschitl(x);
        else
            return log_gamma_lanczos(x);
    }

/*----------------------------------------------------------------------------*/
/** Size of the table to store already computed inverse values.
 */
private int TABSIZE = 100000;

/*----------------------------------------------------------------------------*/

    /**
     * Computes -log10(NFA).
     * <p>
     * NFA stands for Number of False Alarms:
     *
     * @param n,k,p binomial parameters.
     * @param logNT logarithm of Number of Tests
     *              <p>
     *              The computation is based in the gamma function by the following
     *              relation:
     * @f[ \mathrm{NFA} = NT \cdot B(n,k,p)
     * @f] - NT       - number of tests
     * - B(n,k,p) - tail of binomial distribution with parameters n,k and p:
     * @f[ B(n, k, p) = \sum_{j=k}^n
     * \left(\begin{array}{c}n\\j\end{array}\right)
     * p^{j} (1-p)^{n-j}
     * @f] The value -log10(NFA) is equivalent but more intuitive than NFA:
     * - -1 corresponds to 10 mean false alarms
     * -  0 corresponds to 1 mean false alarm
     * -  1 corresponds to 0.1 mean false alarms
     * -  2 corresponds to 0.01 mean false alarms
     * -  ...
     * <p>
     * Used this way, the bigger the value, better the detection,
     * and a logarithmic scale is used.
     * @f[ \left(\begin{array}{c}n\\k\end{array}\right)
     * = \frac{ \Gamma(n+1) }{ \Gamma(k+1) \cdot \Gamma(n-k+1) }.
     * @f] We use efficient algorithms to compute the logarithm of
     * the gamma function.
     * <p>
     * To make the computation faster, not all the sum is computed, part
     * of the terms are neglected based on a bound to the error obtained
     * (an error of 10% in the result is accepted).
     */
    private double nfa(int n, int k, double p, double logNT) {
        double[] inv = new double[TABSIZE];   /* table to keep computed inverse values */
        double tolerance = 0.1;       /* an error of 10% in the result is accepted */
        double log1term, term, bin_term, mult_term, bin_tail, err, p_term;
        int i;

  /* check parameters */
        if (n < 0 || k < 0 || k > n || p <= 0.0 || p >= 1.0)
            error("nfa: wrong n, k or p values.");

  /* trivial cases */
        if (n == 0 || k == 0) return -logNT;
        if (n == k) return -logNT - (double) n * Math.log10(p);

  /* probability term */
        p_term = p / (1.0 - p);

  /* compute the first term of the series */
  /*
     binomial_tail(n,k,p) = sum_{i=k}^n bincoef(n,i) * p^i * (1-p)^{n-i}
     where bincoef(n,i) are the binomial coefficients.
     But
       bincoef(n,k) = gamma(n+1) / ( gamma(k+1) * gamma(n-k+1) ).
     We use this to compute the first term. Actually the log of it.
   */
        log1term = log_gamma((double) n + 1.0) - log_gamma((double) k + 1.0)
                - log_gamma((double) (n - k) + 1.0)
                + (double) k * Math.log(p) + (double) (n - k) * Math.log(1.0 - p);
        term = Math.exp(log1term);

  /* in some cases no more computations are needed */
        if (double_equal(term, 0.0))              /* the first term is almost zero */ {
            if ((double) k > (double) n * p)     /* at begin or end of the tail?  */
                return -log1term / M_LN10 - logNT;  /* end: use just the first term  */
            else
                return -logNT;                      /* begin: the tail is roughly 1  */
        }

  /* compute more terms if needed */
        bin_tail = term;
        for (i = k + 1; i <= n; i++) {
      /*
         As
           term_i = bincoef(n,i) * p^i * (1-p)^(n-i)
         and
           bincoef(n,i)/bincoef(n,i-1) = n-1+1 / i,
         then,
           term_i / term_i-1 = (n-i+1)/i * p/(1-p)
         and
           term_i = term_i-1 * (n-i+1)/i * p/(1-p).
         1/i is stored in a table as they are computed,
         because divisions are expensive.
         p/(1-p) is computed only once and stored in 'p_term'.
       */
            bin_term = (double) (n - i + 1) * (i < TABSIZE ?
                    (inv[i] != 0.0 ? inv[i] : (inv[i] = 1.0 / (double) i)) :
                    1.0 / (double) i);

            mult_term = bin_term * p_term;
            term *= mult_term;
            bin_tail += term;
            if (bin_term < 1.0) {
          /* When bin_term<1 then mult_term_j<mult_term_i for j>i.
             Then, the error on the binomial tail when truncated at
             the i term can be bounded by a geometric series of form
             term_i * sum mult_term_i^j.                            */
                err = term * ((1.0 - Math.pow(mult_term, (double) (n - i + 1))) /
                        (1.0 - mult_term) - 1.0);

          /* One wants an error at most of tolerance*final_result, or:
             tolerance * abs(-log10(bin_tail)-logNT).
             Now, the error that can be accepted on bin_tail is
             given by tolerance*final_result divided by the derivative
             of -log10(x) when x=bin_tail. that is:
             tolerance * abs(-log10(bin_tail)-logNT) / (1/bin_tail)
             Finally, we truncate the tail if the error is less than:
             tolerance * abs(-log10(bin_tail)-logNT) * bin_tail        */
                if (err < tolerance * Math.abs(-Math.log10(bin_tail) - logNT) * bin_tail) break;
            }
        }
        return -Math.log10(bin_tail) - logNT;
    }


/*----------------------------------------------------------------------------*/
/*--------------------------- Rectangle structure ----------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
    /**
     * Rectangle structure: line segment with width.
     */
    class rect
    {
        double x1, y1, x2, y2;  /* first and second point of the line segment */
        double width;        /* rectangle width */
        double x, y;          /* center of the rectangle */
        double theta;        /* angle */
        double dx, dy;        /* (dx,dy) is vector oriented as the line segment */
        double prec;         /* tolerance angle */
        double p;            /* probability of a point with angle within 'prec' */
    }



/*----------------------------------------------------------------------------*/

    /**
     * Copy one rectangle structure to another.
     */
    private void rect_copy(rect in, rect out) {
  /* check parameters */
        if (in == null || out == null) error("rect_copy: invalid 'in' or 'out'.");

  /* copy values */
        out.x1 = in.x1;
        out.y1 = in.y1;
        out.x2 = in.x2;
        out.y2 = in.y2;
        out.width = in.width;
        out.x = in.x;
        out.y = in.y;
        out.theta = in.theta;
        out.dx = in.dx;
        out.dy = in.dy;
        out.prec = in.prec;
        out.p = in.p;
    }

/*----------------------------------------------------------------------------*/
    /**
     * Rectangle points iterator.
     * <p>
     * The integer coordinates of pixels inside a rectangle are
     * iteratively explored. This structure keep track of the process and
     * functions ri_ini(), ri_inc(), ri_end(), and ri_del() are used in
     * the process. An example of how to use the iterator is as follows:
     * \code
     * <p>
     * struct rect * rec = XXX; // some rectangle
     * rect_iter * i;
     * for( i=ri_ini(rec); !ri_end(i); ri_inc(i) )
     * {
     * // your code, using 'i->x' and 'i->y' as coordinates
     * }
     * ri_del(i); // delete iterator
     * <p>
     * \endcode
     * The pixels are explored 'column' by 'column', where we call
     * 'column' a set of pixels with the same x value that are inside the
     * rectangle. The following is an schematic representation of a
     * rectangle, the 'column' being explored is marked by colons, and
     * the current pixel being explored is 'x,y'.
     * \verbatim
     * <p>
     * vx[1],vy[1]
     * *
     * *
     * *
     * ye
     * :  *
     * vx[0],vy[0]           :     *
     * :        *
     * x,y          *
     * :              *
     * :            vx[2],vy[2]
     * :                *
     * y                     ys              *
     * ^                        *           *
     * |                           *       *
     * |                              *   *
     * +---> x                      vx[3],vy[3]
     * <p>
     * \endverbatim
     * The first 'column' to be explored is the one with the smaller x
     * value. Each 'column' is explored starting from the pixel of the
     * 'column' (inside the rectangle) with the smallest y value.
     * <p>
     * The four corners of the rectangle are stored in order that rotates
     * around the corners at the arrays 'vx[]' and 'vy[]'. The first
     * point is always the one with smaller x value.
     * <p>
     * 'x' and 'y' are the coordinates of the pixel being explored. 'ys'
     * and 'ye' are the start and end values of the current column being
     * explored. So, 'ys' < 'ye'.
     */
    class rect_iter
    {
        double []vx = new double[4];  /* rectangle's corner X coordinates in circular order */
        double []vy = new double[4];  /* rectangle's corner Y coordinates in circular order */
        double ys, ye;  /* start and end Y values of current 'column' */
        int x, y;       /* coordinates of currently explored pixel */
    }

/*----------------------------------------------------------------------------*/

    /**
     * Interpolate y value corresponding to 'x' value given, in
     * the line 'x1,y1' to 'x2,y2'; if 'x1=x2' return the smaller
     * of 'y1' and 'y2'.
     * <p>
     * The following restrictions are required:
     * - x1 <= x2
     * - x1 <= x
     * - x  <= x2
     */
    private double inter_low(double x, double x1, double y1, double x2, double y2) {
  /* check parameters */
        if (x1 > x2 || x < x1 || x > x2)
            error("inter_low: unsuitable input, 'x1>x2' or 'x<x1' or 'x>x2'.");

  /* interpolation */
        if (double_equal(x1, x2) && y1 < y2) return y1;
        if (double_equal(x1, x2) && y1 > y2) return y2;
        return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
    }

/*----------------------------------------------------------------------------*/

    /**
     * Interpolate y value corresponding to 'x' value given, in
     * the line 'x1,y1' to 'x2,y2'; if 'x1=x2' return the larger
     * of 'y1' and 'y2'.
     * <p>
     * The following restrictions are required:
     * - x1 <= x2
     * - x1 <= x
     * - x  <= x2
     */
    private double inter_hi(double x, double x1, double y1, double x2, double y2) {
  /* check parameters */
        if (x1 > x2 || x < x1 || x > x2)
            error("inter_hi: unsuitable input, 'x1>x2' or 'x<x1' or 'x>x2'.");

  /* interpolation */
        if (double_equal(x1, x2) && y1 < y2) return y2;
        if (double_equal(x1, x2) && y1 > y2) return y1;
        return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
    }


/*----------------------------------------------------------------------------*/

    /**
     * Check if the iterator finished the full iteration.
     * <p>
     * See details in \ref rect_iter
     */
    private int ri_end(rect_iter i) {
  /* check input */
        if (i == null) error("ri_end: null iterator.");

  /* if the current x value is larger than the largest
     x value in the rectangle (vx[2]), we know the full
     exploration of the rectangle is finished. */
        return ((double) (i.x) > i.vx[2] ? TRUE : FALSE);
    }

/*----------------------------------------------------------------------------*/

    /**
     * Increment a rectangle iterator.
     * <p>
     * See details in \ref rect_iter
     */
    private void ri_inc(rect_iter i) {
  /* check input */
        if (i == null) error("ri_inc: null iterator.");

  /* if not at end of exploration,
     increase y value for next pixel in the 'column' */
        if (ri_end(i) == FALSE) i.y++;

  /* if the end of the current 'column' is reached,
     and it is not the end of exploration,
     advance to the next 'column' */
        while ((double) (i.y) > i.ye && ri_end(i)==FALSE) {
      /* increase x, next 'column' */
            i.x++;

      /* if end of exploration, return */
            if (ri_end(i)== TRUE) return;

      /* update lower y limit (start) for the new 'column'.

         We need to interpolate the y value that corresponds to the
         lower side of the rectangle. The first thing is to decide if
         the corresponding side is

           vx[0],vy[0] to vx[3],vy[3] or
           vx[3],vy[3] to vx[2],vy[2]

         Then, the side is interpolated for the x value of the
         'column'. But, if the side is vertical (as it could happen if
         the rectangle is vertical and we are dealing with the first
         or last 'columns') then we pick the lower value of the side
         by using 'inter_low'.
       */
            if ((double) i.x < i.vx[3])
                i.ys = inter_low((double) i.x, i.vx[0], i.vy[0], i.vx[3], i.vy[3]);
            else
                i.ys = inter_low((double) i.x, i.vx[3], i.vy[3], i.vx[2], i.vy[2]);

      /* update upper y limit (end) for the new 'column'.

         We need to interpolate the y value that corresponds to the
         upper side of the rectangle. The first thing is to decide if
         the corresponding side is

           vx[0],vy[0] to vx[1],vy[1] or
           vx[1],vy[1] to vx[2],vy[2]

         Then, the side is interpolated for the x value of the
         'column'. But, if the side is vertical (as it could happen if
         the rectangle is vertical and we are dealing with the first
         or last 'columns') then we pick the lower value of the side
         by using 'inter_low'.
       */
            if ((double) i.x < i.vx[1])
                i.ye = inter_hi((double) i.x, i.vx[0], i.vy[0], i.vx[1], i.vy[1]);
            else
                i.ye = inter_hi((double) i.x, i.vx[1], i.vy[1], i.vx[2], i.vy[2]);

      /* new y */
            i.y = (int) Math.ceil(i.ys);
        }
    }

/*----------------------------------------------------------------------------*/
/** Create and initialize a rectangle iterator.

 See details in \ref rect_iter
 */
    private rect_iter ri_ini(rect r) {
        double[] vx = new double[4],vy = new double[4];
        int n, offset;
        rect_iter  i;

  /* check parameters */
        if (r == null) error("ri_ini: invalid rectangle.");

  /* get memory */
        i = new rect_iter();

  /* build list of rectangle corners ordered
     in a circular way around the rectangle */
        vx[0] = r.x1 - r.dy * r.width / 2.0;
        vy[0] = r.y1 + r.dx * r.width / 2.0;
        vx[1] = r.x2 - r.dy * r.width / 2.0;
        vy[1] = r.y2 + r.dx * r.width / 2.0;
        vx[2] = r.x2 + r.dy * r.width / 2.0;
        vy[2] = r.y2 - r.dx * r.width / 2.0;
        vx[3] = r.x1 + r.dy * r.width / 2.0;
        vy[3] = r.y1 - r.dx * r.width / 2.0;

  /* compute rotation of index of corners needed so that the first
     point has the smaller x.

     if one side is vertical, thus two corners have the same smaller x
     value, the one with the largest y value is selected as the first.
   */
        if (r.x1 < r.x2 && r.y1 <= r.y2) offset = 0;
        else if (r.x1 >= r.x2 && r.y1 < r.y2) offset = 1;
        else if (r.x1 > r.x2 && r.y1 >= r.y2) offset = 2;
        else offset = 3;

  /* apply rotation of index. */
        for (n = 0; n < 4; n++) {
            i.vx[n] = vx[(offset + n) % 4];
            i.vy[n] = vy[(offset + n) % 4];
        }

  /* Set an initial condition.

     The values are set to values that will cause 'ri_inc' (that will
     be called immediately) to initialize correctly the first 'column'
     and compute the limits 'ys' and 'ye'.

     'y' is set to the integer value of vy[0], the starting corner.

     'ys' and 'ye' are set to very small values, so 'ri_inc' will
     notice that it needs to start a new 'column'.

     The smallest integer coordinate inside of the rectangle is
     'ceil(vx[0])'. The current 'x' value is set to that value minus
     one, so 'ri_inc' (that will increase x by one) will advance to
     the first 'column'.
   */
        i.x = (int) Math.ceil(i.vx[0]) - 1;
        i.y = (int) Math.ceil(i.vy[0]);
        i.ys = i.ye = -Double.MAX_VALUE;

  /* advance to the first pixel */
        ri_inc(i);

        return i;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Compute a rectangle's NFA value.
     */
    private double rect_nfa(rect rec, image_double angles, double logNT) {
        rect_iter i;
        int pts = 0;
        int alg = 0;

  /* check parameters */
        if (rec == null) error("rect_nfa: invalid rectangle.");
        if (angles == null) error("rect_nfa: invalid 'angles'.");

  /* compute the total number of pixels and of aligned points in 'rec' */
        for (i = ri_ini(rec); ri_end(i)== FALSE; ri_inc(i)) /* rectangle iterator */
            if (i.x >= 0 && i.y >= 0 &&
                    i.x < (int) angles.xsize && i.y < (int) angles.ysize) {
                ++pts; /* total number of pixels counter */
                if (isaligned(i.x, i.y, angles, rec.theta, rec.prec) == TRUE)
                    ++alg; /* aligned points counter */
            }

        return nfa(pts, alg, rec.p, logNT); /* compute NFA value */
    }


/*----------------------------------------------------------------------------*/
/*---------------------------------- Regions ---------------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/

    /**
     * Compute region's angle as the principal inertia axis of the region.
     * <p>
     * The following is the region inertia matrix A:
     *
     * @f[ A = \left(\begin{array}{cc}
     * Ixx & Ixy \\
     * Ixy & Iyy \\
     * \end{array}\right)
     * @f] where
     * <p>
     * Ixx =   sum_i G(i).(y_i - cx)^2
     * <p>
     * Iyy =   sum_i G(i).(x_i - cy)^2
     * <p>
     * Ixy = - sum_i G(i).(x_i - cx).(y_i - cy)
     * <p>
     * and
     * - G(i) is the gradient norm at pixel i, used as pixel's weight.
     * - x_i and y_i are the coordinates of pixel i.
     * - cx and cy are the coordinates of the center of th region.
     * <p>
     * lambda1 and lambda2 are the eigenvalues of matrix A,
     * with lambda1 >= lambda2. They are found by solving the
     * characteristic polynomial:
     * <p>
     * det( lambda I - A) = 0
     * <p>
     * that gives:
     * <p>
     * lambda1 = ( Ixx + Iyy + sqrt( (Ixx-Iyy)^2 + 4.0*Ixy*Ixy) ) / 2
     * <p>
     * lambda2 = ( Ixx + Iyy - sqrt( (Ixx-Iyy)^2 + 4.0*Ixy*Ixy) ) / 2
     * <p>
     * To get the line segment direction we want to get the angle the
     * eigenvector associated to the smallest eigenvalue. We have
     * to solve for a,b in:
     * <p>
     * a.Ixx + b.Ixy = a.lambda2
     * <p>
     * a.Ixy + b.Iyy = b.lambda2
     * <p>
     * We want the angle theta = atan(b/a). It can be computed with
     * any of the two equations:
     * <p>
     * theta = atan( (lambda2-Ixx) / Ixy )
     * <p>
     * or
     * <p>
     * theta = atan( Ixy / (lambda2-Iyy) )
     * <p>
     * When |Ixx| > |Iyy| we use the first, otherwise the second (just to
     * get better numeric precision).
     */
    private double get_theta(Point[] reg, int reg_size, double x, double y,
                            image_double modgrad, double reg_angle, double prec) {
        double lambda, theta, weight;
        double Ixx = 0.0;
        double Iyy = 0.0;
        double Ixy = 0.0;
        int i;

  /* check parameters */
        if (reg == null) error("get_theta: invalid region.");
        if (reg_size <= 1) error("get_theta: region size <= 1.");
        if (modgrad == null || modgrad.data == null)
            error("get_theta: invalid 'modgrad'.");
        if (prec < 0.0) error("get_theta: 'prec' must be positive.");

  /* compute inertia matrix */
        for (i = 0; i < reg_size; i++) {
            weight = modgrad.data[reg[i].x + reg[i].y * modgrad.xsize];
            Ixx += ((double) reg[i].y - y) * ((double) reg[i].y - y) * weight;
            Iyy += ((double) reg[i].x - x) * ((double) reg[i].x - x) * weight;
            Ixy -= ((double) reg[i].x - x) * ((double) reg[i].y - y) * weight;
        }
        if (double_equal(Ixx, 0.0) && double_equal(Iyy, 0.0) && double_equal(Ixy, 0.0))
            error("get_theta: null inertia matrix.");

  /* compute smallest eigenvalue */
        lambda = 0.5 * (Ixx + Iyy - Math.sqrt((Ixx - Iyy) * (Ixx - Iyy) + 4.0 * Ixy * Ixy));

  /* compute angle */
        theta = Math.abs(Ixx) > Math.abs(Iyy) ? Math.atan2(lambda - Ixx, Ixy) : Math.atan2(Ixy, lambda - Iyy);

  /* The previous procedure doesn't cares about orientation,
     so it could be wrong by 180 degrees. Here is corrected if necessary. */
        if (angle_diff(theta, reg_angle) > prec) theta += M_PI;

        return theta;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Computes a rectangle that covers a region of points.
     */
    private void region2rect(Point [] reg, int reg_size,
                            image_double modgrad, double reg_angle,
                            double prec, double p, rect rec) {
        double x, y, dx, dy, l, w, theta, weight, sum, l_min, l_max, w_min, w_max;
        int i;

  /* check parameters */
        if (reg == null) error("region2rect: invalid region.");
        if (reg_size <= 1) error("region2rect: region size <= 1.");
        if (modgrad == null || modgrad.data == null)
            error("region2rect: invalid image 'modgrad'.");
        if (rec == null) error("region2rect: invalid 'rec'.");

  /* center of the region:

     It is computed as the weighted sum of the coordinates
     of all the pixels in the region. The norm of the gradient
     is used as the weight of a pixel. The sum is as follows:
       cx = \sum_i G(i).x_i
       cy = \sum_i G(i).y_i
     where G(i) is the norm of the gradient of pixel i
     and x_i,y_i are its coordinates.
   */
        x = y = sum = 0.0;
        for (i = 0; i < reg_size; i++) {
            weight = modgrad.data[reg[i].x + reg[i].y * modgrad.xsize];
            x += (double) reg[i].x * weight;
            y += (double) reg[i].y * weight;
            sum += weight;
        }
        if (sum <= 0.0) error("region2rect: weights sum equal to zero.");
        x /= sum;
        y /= sum;

  /* theta */
        theta = get_theta(reg, reg_size, x, y, modgrad, reg_angle, prec);

  /* length and width:

     'l' and 'w' are computed as the distance from the center of the
     region to pixel i, projected along the rectangle axis (dx,dy) and
     to the orthogonal axis (-dy,dx), respectively.

     The length of the rectangle goes from l_min to l_max, where l_min
     and l_max are the minimum and maximum values of l in the region.
     Analogously, the width is selected from w_min to w_max, where
     w_min and w_max are the minimum and maximum of w for the pixels
     in the region.
   */
        dx = Math.cos(theta);
        dy = Math.sin(theta);
        l_min = l_max = w_min = w_max = 0.0;
        for (i = 0; i < reg_size; i++) {
            l = ((double) reg[i].x - x) * dx + ((double) reg[i].y - y) * dy;
            w = -((double) reg[i].x - x) * dy + ((double) reg[i].y - y) * dx;

            if (l > l_max) l_max = l;
            if (l < l_min) l_min = l;
            if (w > w_max) w_max = w;
            if (w < w_min) w_min = w;
        }

  /* store values */
        rec.x1 = x + l_min * dx;
        rec.y1 = y + l_min * dy;
        rec.x2 = x + l_max * dx;
        rec.y2 = y + l_max * dy;
        rec.width = w_max - w_min;
        rec.x = x;
        rec.y = y;
        rec.theta = theta;
        rec.dx = dx;
        rec.dy = dy;
        rec.prec = prec;
        rec.p = p;

  /* we impose a minimal width of one pixel

     A sharp horizontal or vertical step would produce a perfectly
     horizontal or vertical region. The width computed would be
     zero. But that corresponds to a one pixels width transition in
     the image.
   */
        if (rec.width < 1.0) rec.width = 1.0;
    }

    class intHolder{
        int i;
        intHolder(int i){
            this.i = i;
        }
    }

    class doubleHolder {
        double d;

        doubleHolder(double d){
            this.d = d;
        }
    }
/*----------------------------------------------------------------------------*/

    /**
     * Build a region of pixels that share the same angle, up to a
     * tolerance 'prec', starting at point (x,y).
     */
    private void region_grow(int x, int y, image_double angles, Point []reg,
                            intHolder reg_size, doubleHolder reg_angle, image_char used,
                            double prec) {
        double sumdx, sumdy;
        int xx, yy, i;

  /* check parameters */
        if (x < 0 || y < 0 || x >= (int) angles.xsize || y >= (int) angles.ysize)
            error("region_grow: (x,y) out of the image.");
        if (angles == null || angles.data == null)
            error("region_grow: invalid image 'angles'.");
        if (reg == null) error("region_grow: invalid 'reg'.");
        if (reg_size == null) error("region_grow: invalid pointer 'reg_size'.");
        if (reg_angle == null) error("region_grow: invalid pointer 'reg_angle'.");
        if (used == null || used.data == null)
            error("region_grow: invalid image 'used'.");

  /* first point of the region */
        reg_size.i = 1;
        reg[0].x = x;
        reg[0].y = y;
        reg_angle.d = angles.data[x + y * angles.xsize];  /* region's angle */
        sumdx = Math.cos(reg_angle.d);
        sumdy = Math.sin(reg_angle.d);
        used.data[x + y * used.xsize] = (char)USED;

  /* try neighbors as new region points */
        for (i = 0; i <reg_size.i;i++)
            for (xx = reg[i].x - 1; xx <= reg[i].x + 1; xx++)
                for (yy = reg[i].y - 1; yy <= reg[i].y + 1; yy++)
                    if (xx >= 0 && yy >= 0 && xx < (int) used.xsize && yy < (int) used.ysize &&
                        used.data[xx + yy * used.xsize] != USED &&
                                isaligned(xx, yy, angles,reg_angle.d,prec) == TRUE )
        {
            /* add point */
            used.data[xx + yy * used.xsize] = (char)USED;
            reg[ reg_size.i].x = xx;
            reg[ reg_size.i].y = yy;
            ++( reg_size.i);

            /* update region's angle */
            sumdx += Math.cos(angles.data[xx + yy * angles.xsize]);
            sumdy += Math.sin(angles.data[xx + yy * angles.xsize]);
            reg_angle.d = Math.atan2(sumdy, sumdx);
        }
    }

/*----------------------------------------------------------------------------*/

    /**
     * Try some rectangles variations to improve NFA value. Only if the
     * rectangle is not meaningful (i.e., log_nfa <= log_eps).
     */
    private double rect_improve(rect rec, image_double angles,
                               double logNT, double log_eps) {
        rect r = new rect();
        double log_nfa, log_nfa_new;
        double delta = 0.5;
        double delta_2 = delta / 2.0;
        int n;

        log_nfa = rect_nfa(rec, angles, logNT);

        if (log_nfa > log_eps) return log_nfa;

  /* try finer precisions */
        rect_copy(rec, r);
        for (n = 0; n < 5; n++) {
            r.p /= 2.0;
            r.prec = r.p * M_PI;
            log_nfa_new = rect_nfa( r, angles, logNT);
            if (log_nfa_new > log_nfa) {
                log_nfa = log_nfa_new;
                rect_copy( r, rec);
            }
        }

        if (log_nfa > log_eps) return log_nfa;

  /* try to reduce width */
        rect_copy(rec, r);
        for (n = 0; n < 5; n++) {
            if ((r.width - delta) >= 0.5) {
                r.width -= delta;
                log_nfa_new = rect_nfa( r, angles, logNT);
                if (log_nfa_new > log_nfa) {
                    rect_copy( r, rec);
                    log_nfa = log_nfa_new;
                }
            }
        }

        if (log_nfa > log_eps) return log_nfa;

  /* try to reduce one side of the rectangle */
        rect_copy(rec, r);
        for (n = 0; n < 5; n++) {
            if ((r.width - delta) >= 0.5) {
                r.x1 += -r.dy * delta_2;
                r.y1 += r.dx * delta_2;
                r.x2 += -r.dy * delta_2;
                r.y2 += r.dx * delta_2;
                r.width -= delta;
                log_nfa_new = rect_nfa(  r, angles, logNT);
                if (log_nfa_new > log_nfa) {
                    rect_copy( r, rec);
                    log_nfa = log_nfa_new;
                }
            }
        }

        if (log_nfa > log_eps) return log_nfa;

  /* try to reduce the other side of the rectangle */
        rect_copy(rec, r);
        for (n = 0; n < 5; n++) {
            if ((r.width - delta) >= 0.5) {
                r.x1 -= -r.dy * delta_2;
                r.y1 -= r.dx * delta_2;
                r.x2 -= -r.dy * delta_2;
                r.y2 -= r.dx * delta_2;
                r.width -= delta;
                log_nfa_new = rect_nfa( r, angles, logNT);
                if (log_nfa_new > log_nfa) {
                    rect_copy( r, rec);
                    log_nfa = log_nfa_new;
                }
            }
        }

        if (log_nfa > log_eps) return log_nfa;

  /* try even finer precisions */
        rect_copy(rec, r);
        for (n = 0; n < 5; n++) {
            r.p /= 2.0;
            r.prec = r.p * M_PI;
            log_nfa_new = rect_nfa( r, angles, logNT);
            if (log_nfa_new > log_nfa) {
                log_nfa = log_nfa_new;
                rect_copy( r, rec);
            }
        }

        return log_nfa;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Reduce the region size, by elimination the points far from the
     * starting point, until that leads to rectangle with the right
     * density of region points or to discard the region if too small.
     */
    private int reduce_region_radius(Point []reg, intHolder reg_size,
                                    image_double modgrad, double reg_angle,
                                    double prec, double p,rect rec,
                                    image_char used, image_double angles,
                                    double density_th) {
        double density, rad1, rad2, rad, xc, yc;
        int i;

  /* check parameters */
        if (reg == null) error("reduce_region_radius: invalid pointer 'reg'.");
        if (reg_size == null)
            error("reduce_region_radius: invalid pointer 'reg_size'.");
        if (prec < 0.0) error("reduce_region_radius: 'prec' must be positive.");
        if (rec == null) error("reduce_region_radius: invalid pointer 'rec'.");
        if (used == null || used.data == null)
            error("reduce_region_radius: invalid image 'used'.");
        if (angles == null || angles.data == null)
            error("reduce_region_radius: invalid image 'angles'.");

  /* compute region points density */
        density = (double) reg_size.i /
                (dist(rec.x1, rec.y1, rec.x2, rec.y2) * rec.width);

  /* if the density criterion is satisfied there is nothing to do */
        if (density >= density_th) return TRUE;

  /* compute region's radius */
        xc = (double) reg[0].x;
        yc = (double) reg[0].y;
        rad1 = dist(xc, yc, rec.x1, rec.y1);
        rad2 = dist(xc, yc, rec.x2, rec.y2);
        rad = rad1 > rad2 ? rad1 : rad2;

  /* while the density criterion is not satisfied, remove farther pixels */
        while (density < density_th) {
            rad *= 0.75; /* reduce region's radius to 75% of its value */

      /* remove points from the region and update 'used' map */
            for (i = 0; i <reg_size.i;
            i++)
            if (dist(xc, yc, (double) reg[i].x, (double) reg[i].y) > rad) {
            /* point not kept, mark it as NOTUSED */
                used.data[reg[i].x + reg[i].y * used.xsize] = (char)NOTUSED;
            /* remove point from the region */
                reg[i].x = reg[ reg_size.i - 1].x; /* if i==*reg_size-1 copy itself */
                reg[i].y = reg[ reg_size.i - 1].y;
                --(reg_size.i);
                --i; /* to avoid skipping one point */
            }

      /* reject if the region is too small.
         2 is the minimal region size for 'region2rect' to work. */
            if (reg_size.i< 2 )return FALSE;

      /* re-compute rectangle */
            region2rect(reg, reg_size.i, modgrad, reg_angle, prec, p, rec);

      /* re-compute region points density */
            density = (double) reg_size.i /
                    (dist(rec.x1, rec.y1, rec.x2, rec.y2) * rec.width);
        }

  /* if this point is reached, the density criterion is satisfied */
        return TRUE;
    }

/*----------------------------------------------------------------------------*/

    /**
     * Refine a rectangle.
     * <p>
     * For that, an estimation of the angle tolerance is performed by the
     * standard deviation of the angle at points near the region's
     * starting point. Then, a new region is grown starting from the same
     * point, but using the estimated angle tolerance. If this fails to
     * produce a rectangle with the right density of region points,
     * 'reduce_region_radius' is called to try to satisfy this condition.
     */
    private int refine(Point [] reg, intHolder reg_size, image_double modgrad,
                      double reg_angle, double prec, double p, rect rec,
                      image_char used, image_double angles, double density_th) {
        double angle, ang_d, mean_angle, tau, density, xc, yc, ang_c, sum, s_sum;
        int i, n;

  /* check parameters */
        if (reg == null) error("refine: invalid pointer 'reg'.");
        if (reg_size == null) error("refine: invalid pointer 'reg_size'.");
        if (prec < 0.0) error("refine: 'prec' must be positive.");
        if (rec == null) error("refine: invalid pointer 'rec'.");
        if (used == null || used.data == null)
            error("refine: invalid image 'used'.");
        if (angles == null || angles.data == null)
            error("refine: invalid image 'angles'.");

  /* compute region points density */
        density = (double) reg_size.i /
                (dist(rec.x1, rec.y1, rec.x2, rec.y2) * rec.width);

  /* if the density criterion is satisfied there is nothing to do */
        if (density >= density_th) return TRUE;

  /*------ First try: reduce angle tolerance ------*/

  /* compute the new mean angle and tolerance */
        xc = (double) reg[0].x;
        yc = (double) reg[0].y;
        ang_c = angles.data[reg[0].x + reg[0].y * angles.xsize];
        sum = s_sum = 0.0;
        n = 0;
        for (i = 0; i <reg_size.i;
        i++)
        {
            used.data[reg[i].x + reg[i].y * used.xsize] = (char)NOTUSED;
            if (dist(xc, yc, (double) reg[i].x, (double) reg[i].y) < rec.width) {
                angle = angles.data[reg[i].x + reg[i].y * angles.xsize];
                ang_d = angle_diff_signed(angle, ang_c);
                sum += ang_d;
                s_sum += ang_d * ang_d;
                ++n;
            }
        }
        mean_angle = sum / (double) n;
        tau = 2.0 * Math.sqrt((s_sum - 2.0 * mean_angle * sum) / (double) n
                + mean_angle * mean_angle); /* 2 * standard deviation */

  /* find a new region from the same starting point and new angle tolerance */
        doubleHolder reg_angle_holder = new doubleHolder(reg_angle);
        region_grow(reg[0].x, reg[0].y, angles, reg, reg_size, reg_angle_holder, used, tau);

  /* if the region is too small, reject */
        if ( reg_size.i < 2 )return FALSE;

  /* re-compute rectangle */
        region2rect(reg, reg_size.i, modgrad, reg_angle, prec, p, rec);

  /* re-compute region points density */
        density = (double) reg_size.i /
                (dist(rec.x1, rec.y1, rec.x2, rec.y2) * rec.width);

  /*------ Second try: reduce region radius ------*/
        if (density < density_th)
            return reduce_region_radius(reg, reg_size, modgrad, reg_angle, prec, p,
                    rec, used, angles, density_th);

  /* if this point is reached, the density criterion is satisfied */
        return TRUE;
    }


/*----------------------------------------------------------------------------*/
/*-------------------------- Line Segment Detector ---------------------------*/
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/** LSD full interface.
 */
    ArrayList<Double> LineSegmentDetection(intHolder n_out,
                         double []img, int X, int Y,
                         double scale, double sigma_scale, double quant,
                         double ang_th, double log_eps, double density_th,
                         int n_bins,
                         int []reg_img, intHolder reg_x, intHolder reg_y) {
        image_double image;
        ntuple_list out = new_ntuple_list(7);
        ArrayList<Double> return_value;
        image_double scaled_image, angles, modgrad = new image_double();
        image_char used;
        image_int region = null;
        coorlist list_p = new coorlist();
        rect rec = new rect();
        Point []reg;
        int min_reg_size, i;
        intHolder reg_size = new intHolder(0);
        int xsize, ysize;
        double rho,  prec, p, log_nfa, logNT;
        doubleHolder reg_angle = new doubleHolder(0);
        int ls_count = 0;                   /* line segments are numbered 1,2,3,... */


  /* check parameters */
        if (img == null || X <= 0 || Y <= 0) error("invalid image input.");
        if (scale <= 0.0) error("'scale' value must be positive.");
        if (sigma_scale <= 0.0) error("'sigma_scale' value must be positive.");
        if (quant < 0.0) error("'quant' value must be positive.");
        if (ang_th <= 0.0 || ang_th >= 180.0)
            error("'ang_th' value must be in the range (0,180).");
        if (density_th < 0.0 || density_th > 1.0)
            error("'density_th' value must be in the range [0,1].");
        if (n_bins <= 0) error("'n_bins' value must be positive.");


  /* angle tolerance */
        prec = M_PI * ang_th / 180.0;
        p = ang_th / 180.0;
        rho = quant / Math.sin(prec); /* gradient magnitude threshold */


  /* load and scale image (if necessary) and compute angle at each pixel */
        image = new_image_double_ptr((int)X, (int)Y, img );
        if (scale != 1.0) {
            scaled_image = gaussian_sampler(image, scale, sigma_scale);
            angles = ll_angle(scaled_image, rho,list_p, modgrad,n_bins );
        } else
            angles = ll_angle(image, rho, list_p,modgrad,n_bins);
        xsize = angles.xsize;
        ysize = angles.ysize;

  /* Number of Tests - NT

     The theoretical number of tests is Np.(XY)^(5/2)
     where X and Y are number of columns and rows of the image.
     Np corresponds to the number of angle precisions considered.
     As the procedure 'rect_improve' tests 5 times to halve the
     angle precision, and 5 more times after improving other factors,
     11 different precision values are potentially tested. Thus,
     the number of tests is
       11 * (X*Y)^(5/2)
     whose logarithm value is
       log10(11) + 5/2 * (log10(X) + log10(Y)).
  */
        logNT = 5.0 * (Math.log10((double) xsize) + Math.log10((double) ysize)) / 2.0
                + Math.log10(11.0);
        min_reg_size = (int) (-logNT / Math.log10(p)); /* minimal number of points in region
                                             that can give a meaningful event */


  /* initialize some structures */
        if (reg_img != null && reg_x != null && reg_y != null) /* save region data */
            region = new_image_int_ini(angles.xsize, angles.ysize, 0);
        used = new_image_char_ini(xsize, ysize, (char)NOTUSED);
        reg = new Point[xsize * ysize];
        for(int k=0;k<reg.length;k++)
            reg[k] = new Point();


  /* search for line segments */
        for (; list_p != null; list_p = list_p.next)
            if (used.data[list_p.x + list_p.y * used.xsize] == NOTUSED &&
                    angles.data[list_p.x + list_p.y * angles.xsize] != NOTDEF)
       /* there is no risk of double comparison problems here
          because we are only interested in the exact NOTDEF value */ {
        /* find the region of connected point and ~equal angle */
                region_grow(list_p.x, list_p.y, angles, reg, reg_size,reg_angle, used, prec );

        /* reject small regions */
                if (reg_size.i < min_reg_size) continue;

        /* construct rectangular approximation for the region */
                region2rect(reg, reg_size.i, modgrad, reg_angle.d, prec, p,rec);

        /* Check if the rectangle exceeds the minimal density of
           region points. If not, try to improve the region.
           The rectangle will be rejected if the final one does
           not fulfill the minimal density condition.
           This is an addition to the original LSD algorithm published in
           "LSD: A Fast Line Segment Detector with a False Detection Control"
           by R. Grompone von Gioi, J. Jakubowicz, J.M. Morel, and G. Randall.
           The original algorithm is obtained with density_th = 0.0.
         */
                if (refine(reg, reg_size,modgrad, reg_angle.d,
                        prec, p, rec, used, angles, density_th ) == FALSE )continue;

        /* compute NFA value */
                log_nfa = rect_improve(rec, angles, logNT, log_eps);
                if (log_nfa <= log_eps) continue;

        /* A New Line Segment was found! */
                ++ls_count;  /* increase line segment counter */

        /*
           The gradient was computed with a 2x2 mask, its value corresponds to
           points with an offset of (0.5,0.5), that should be added to output.
           The coordinates origin is at the center of pixel (0,0).
         */
                rec.x1 += 0.5;
                rec.y1 += 0.5;
                rec.x2 += 0.5;
                rec.y2 += 0.5;

        /* scale the result values if a subsampling was performed */
                if (scale != 1.0) {
                    rec.x1 /= scale;
                    rec.y1 /= scale;
                    rec.x2 /= scale;
                    rec.y2 /= scale;
                    rec.width /= scale;
                }

        /* add line segment found to output */
                add_7tuple(out, rec.x1, rec.y1, rec.x2, rec.y2,
                        rec.width, rec.p, log_nfa);

        /* add region number to 'region' image if needed */
                if (region != null)
                    for (i = 0; i < reg_size.i; i++)
                        region.data[reg[i].x + reg[i].y * region.xsize] = ls_count;
            }


  /* return the result */
        if (reg_img != null && reg_x != null && reg_y != null) {
            if (region == null) error("'region' should be a valid image.");
          reg_img = region.data;
            if (region.xsize < 0 || region.ysize < 0)
                error("region image to big to fit in INT sizes.");
          reg_x.i = (int) (region.xsize);
          reg_y.i = (int) (region.ysize);

        }
        if (out.values.size() < 0 )         //
            error("too many detections to fit in an INT.");
//        n_out.i = (int) (out.values.size() / out.dim);

        return_value = out.values;

        return return_value;
    }

/*----------------------------------------------------------------------------*/
/** LSD Simple Interface with Scale and Region output.
 */
    private ArrayList<Double> lsd_scale_region(intHolder n_out,
                     double []img, int X, int Y, double scale,
                     int []reg_img, intHolder reg_x, intHolder reg_y) {
  /* LSD parameters */
        double sigma_scale = 0.6; /* Sigma for Gaussian filter is computed as
                                sigma = sigma_scale/scale.                    */
        double quant = 2.0;       /* Bound to the quantization error on the
                                gradient norm.                                */
        double ang_th = 22.5;     /* Gradient angle tolerance in degrees.           */
        double log_eps = 0.0;     /* Detection threshold: -log10(NFA) > log_eps     */
        double density_th = 0.7;  /* Minimal density of region points in rectangle. */
        int n_bins = 1024;        /* Number of bins in pseudo-ordering of gradient
                               modulus.                                       */

        return LineSegmentDetection(n_out, img, X, Y, scale, sigma_scale, quant,
                ang_th, log_eps, density_th, n_bins,
                reg_img, reg_x, reg_y);
    }

/*----------------------------------------------------------------------------*/
/** LSD Simple Interface with Scale.
 */
    private ArrayList<Double> lsd_scale(intHolder n_out, double []img, int X, int Y, double scale) {
        return lsd_scale_region(n_out, img, X, Y, scale, null, null, null);
    }

/*----------------------------------------------------------------------------*/
/** LSD Simple Interface.
 */
    ArrayList<Double> lsd(intHolder n_out, double [] img, int X, int Y) {
  /* LSD parameters */
        double scale = 0.8;       /* Scale the image by Gaussian filter to 'scale'. */

        return lsd_scale(n_out, img, X, Y, scale);
    }
/*----------------------------------------------------------------------------*/

}
