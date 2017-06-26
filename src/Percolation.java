/**
 * Created by young on 5/17/17.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF grid;
    private int[][] sites;
    private boolean[][] isOpen;
    private int n;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }
        this.n = n;
        this.isOpen = new boolean[this.n][this.n];
        for (int i = 0; i < isOpen.length; i++) {
            for (int j = 0; j < isOpen[i].length; j++) {
                isOpen[i][j] = false;
            }
        }
        this.grid = new WeightedQuickUnionUF(this.n * this.n + 2);
        this.sites = new int[this.n][this.n];
        int counter = 0;
        for (int i = 0; i < sites.length; i++) {
            for (int j = 0; j < sites[i].length; j++) {
                sites[i][j] = counter;
                counter++;
            }
        }
    }

    private int arrayToObject(int row, int col) {
        return sites[row-1][col-1];
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IndexOutOfBoundsException("Row or Col index out of bounds");
        } // to throw an exception
    }

    public void open(int row, int col) {
        validate(row, col);
        // to open a site
        isOpen[row-1][col-1] = true;
        // to union sites on four sides
        if (row > 1 && isOpen(row-1, col)) { // connect to site above
            grid.union(arrayToObject(row-1, col), arrayToObject(row, col));
        }
        if (row < n && isOpen(row+1, col)) { // connect to site below
            grid.union(arrayToObject(row+1, col), arrayToObject(row, col));
        }
        if (col > 1 && isOpen(row, col-1)) { // connect to site to the left
            grid.union(arrayToObject(row, col-1), arrayToObject(row, col));
        }
        if (col < n && isOpen(row, col+1)) { // connect to site to the right
            grid.union(arrayToObject(row, col+1), arrayToObject(row, col));
        }
        // if any in the top row is open, union with n*n.
        if (row == 1 && isOpen(row, col)) {
            grid.union(arrayToObject(row, col), n*n);
        }
        // if any in the bottom row is open, union with n*n+1.
        if (row == n && isOpen(row, col)) {
            grid.union(arrayToObject(row, col), n*n+1);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        // is it open
        return (isOpen[row-1][col-1]);
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return grid.connected(arrayToObject(row, col), n*n);
    }

    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 0; i < isOpen.length; i++) {
            for (int j = 0; j < isOpen[i].length; j++) {
                if (isOpen[i][j]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean percolates() {
        return (grid.connected(n*n, n*n+1));
    }

    public static void main(String[] args) {

    }

}
