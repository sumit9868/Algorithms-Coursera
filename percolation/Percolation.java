import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Sumit Saurabh
 *  Coursera User ID:   sumits.co18@nsut.ac.in
 *  Last modified:     08/07/2020
 **************************************************************************** */
public class Percolation {
    // creates n-by-n grid, with all sites initially blocked

    private int count;
    private final int n;
    private final WeightedQuickUnionUF topAndBottom;
    private final WeightedQuickUnionUF filled;
    private final int top;
    private final int bottom;
    // private WeightedQuickUnionUF opened;
    private boolean[] opened;

    public Percolation(int n) {
        count = 0;
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        topAndBottom = new WeightedQuickUnionUF((n * n) + 2);
        filled = new WeightedQuickUnionUF((n * n) + 1);
        top = n * n;
        bottom = n * n + 1;
        opened = new boolean[n * n];

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {


        validate(row, col);
        int lIdx = linearIndex(row, col);
        if (opened[lIdx]) {
            return;
        }
        opened[lIdx] = true;
        if (row == 1) {
            topAndBottom.union(top, lIdx);
            filled.union(top, lIdx);
        }

        if (row == n) {
            topAndBottom.union(linearIndex(row, col), bottom);
        }

        // check above
        if (isValidIdx(row - 1, col) && isOpen(row - 1, col)) {
            topAndBottom.union(linearIndex(row, col), linearIndex(row - 1, col));
            filled.union(linearIndex(row, col), linearIndex(row - 1, col));
        }

        // check below
        if (isValidIdx(row + 1, col) && isOpen(row + 1, col)) {
            topAndBottom.union(lIdx, linearIndex(row + 1, col));
            filled.union(lIdx, linearIndex(row + 1, col));
        }

        // check left
        if (isValidIdx(row, col - 1) && isOpen(row, col - 1)) {
            topAndBottom.union(lIdx, linearIndex(row, col - 1));
            filled.union(lIdx, linearIndex(row, col - 1));
        }

        // check right
        if (isValidIdx(row, col + 1) && isOpen(row, col + 1)) {
            topAndBottom.union(lIdx, linearIndex(row, col + 1));
            filled.union(lIdx, linearIndex(row, col + 1));
        }


        this.count++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int lr, lc;
        lr = row - 1;
        lc = col - 1;

        return opened[lr * n + lc];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        return topAndBottom.find(linearIndex(row, col)) == topAndBottom.find(top)
                && filled.find(linearIndex(row, col)) == filled.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return topAndBottom.find(top) == topAndBottom.find(bottom);
    }

    public static void main(String[] args) {
        // input file
        In in = new In(args[0]);
        int n = in.readInt();         // n-by-n percolation system

        Percolation percolate = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            System.out.println("adding row:" + i + ", col:" + j);
            percolate.open(i, j);
        }


        System.out.println(percolate.percolates());
    }

    private int linearIndex(int row, int col) {
        return (row - 1) * this.n + (col - 1);
    }

    private boolean isValidIdx(int row, int col) {
        return row <= this.n && row > 0 && col <= this.n && col > 0;
    }

    private void validate(int row, int col) {
        if (!isValidIdx(row, col)) {
            throw new IllegalArgumentException();
        }
    }


}
