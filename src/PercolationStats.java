/**
 * Created by young on 5/17/17.
 */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private int n;
    private double statsN;
    private int trials;
    private double[] threshold;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be greater than zero.");
        }
        this.n = n;
        this.statsN = n*n;
        this.trials = trials;
        this.threshold = new double[trials];

        int trialsTemp = this.trials;
        while (trialsTemp != 0) {
            Percolation p = new Percolation(this.n);
            while (!p.percolates()) {
                int temp3 = StdRandom.uniform(this.n);
                int temp4 = StdRandom.uniform(this.n);
                if (!p.isOpen(temp3+1, temp4+1)) {
                    p.open(temp3+1, temp4+1);
                }
            }
            threshold[trialsTemp-1] = p.numberOfOpenSites()/(this.statsN);
            trialsTemp--;
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold)/(trials*trials);
    }

    public double confidenceLo() {
        return mean() - (1.96*stddev())/(Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (1.96*stddev())/(Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int temp1;
        int temp2;
        temp1 = Integer.parseInt(args[0]);
        temp2 = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(temp1, temp2);
        System.out.println("% java PercolationStats " + stats.n + " " + stats.trials);
        System.out.println("mean: " + stats.mean());
        System.out.println("stddev: " + stats.stddev());
        System.out.println("95% confidence interval: [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
