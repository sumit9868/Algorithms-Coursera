import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/* *****************************************************************************
 *  Name:              Sumit Saurabh
 *  Coursera User ID:   sumits.co18@nsut.ac.in
 *  Last modified:     08/07/2020
 **************************************************************************** */
public class PercolationStats {
    private static final double FACT = 1.96;
    private final int t;
    private final double m;
    private final double stdDev;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        this.t = trials;
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        double[] percolateAt = new double[trials];
        double squares = n * n;
        for (int i = 0; i < trials; i++) {
            double count = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    count++;
                }
                percolation.open(row, col);

            }
            percolateAt[i] = count / squares;
        }
        this.m = StdStats.mean(percolateAt);
        this.stdDev = StdStats.stddev(percolateAt);
    }

    // sample mean of percolation threshold
    public double mean() {
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {


        return m - (FACT * stdDev) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return m + (FACT * stdDev) / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats pStats = new PercolationStats(n, t);
        System.out.println("mean\t\t\t\t=" + pStats.mean());
        System.out.println("stddev\t\t\t\t=" + pStats.stddev());
        System.out.println(
                "95% confidence interval\t\t=[" + pStats.confidenceLo() + "," + pStats
                        .confidenceHi() + "]");

    }


}
