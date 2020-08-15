import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int t = 0;
    private double[] a;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Out of range");
        }
        t = trials;
        a = new double[t];
        int row = 0, col = 0;
        int ct = 0;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            ct = 0;
            while (!p.percolates()) {
                row = StdRandom.uniform(n);
                col = StdRandom.uniform(n);
                if (!p.isOpen(row + 1, col + 1)) {
                    p.open(row + 1, col + 1);
                    ct++;
                }
            }
            // System.out.println(ct);
            a[i] = (double) ct / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(a);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(a);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {

        int num = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(num, trials);
        System.out.println("The mean is: " + ps.mean());
        System.out.println("The standard deviation is: " + ps.stddev());
        System.out.println("The low endpoint of 95% confidence is: " + ps.confidenceLo());
        System.out.println("The high endpoint of 95% confidence is: " + ps.confidenceHi());
    }
}
