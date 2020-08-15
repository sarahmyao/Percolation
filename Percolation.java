import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] blockedArr;
    // private int[] id;
    // private int[] sz;
    private int num = 0;
    private int top = 0;
    private int bottom;

    private WeightedQuickUnionUF quFind;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Out of range");
        }
        else {
            num = n;
            quFind = new WeightedQuickUnionUF(num * num + 2);
            // id = new int[num * num + 2];
            // sz = new int[num * num + 2];
            // for (int i = 0; i < num * num; i++) {
            //     sz[i] = 0;
            //     if (i < num) {
            //         id[i] = root1;
            //     }
            //     else if (i >= (num * num) - num) {
            //         id[i] = root2;
            //     }
            //     else {
            //         id[i] = i;
            //     }
            //
            // }

            bottom = num * num + 1;


            blockedArr = new boolean[num][num];
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    blockedArr[i][j] = true;
                }
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) {
            throw new IllegalArgumentException("Out out range");
        }
        else if (blockedArr[row - 1][col - 1]) {
            blockedArr[row - 1][col - 1] = false;
            if (row == 1) {
                quFind.union(findIndex(row, col), top);
            }
            else if (row == num) {
                quFind.union(findIndex(row, col), bottom);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                quFind.union(findIndex(row, col), findIndex(row - 1, col));
            }
            if (row < num && isOpen(row + 1, col)) {
                quFind.union(findIndex(row, col), findIndex(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                quFind.union(findIndex(row, col), findIndex(row, col - 1));
            }
            if (col < num && isOpen(row, col + 1)) {
                quFind.union(findIndex(row, col), findIndex(row, col + 1));
            }
        }


    }

    private int findIndex(int i, int j) {
        return (i - 1) * num + j;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) {
            throw new IllegalArgumentException("Out of range");
        }
        else
            return !blockedArr[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) {
            throw new IllegalArgumentException("Out of range");
        }
        else
            return quFind.connected(top, findIndex(row, col));
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSites = 0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (isOpen(i + 1, j + 1)) {
                    openSites++;
                }
            }
        }
        return openSites;
    }

    // private int root(int i) {
    //     while (i != id[i]) {
    //         i = id[i];
    //     }
    //     return i;
    // }
    //
    // private boolean connected(int p, int q) {
    //     return root(p) == root(q);
    // }
    //
    // private void union(int p, int q) {
    //     int i = root(p);
    //     int j = root(q);
    //     if (i == j) {
    //         return;
    //     }
    //     if (sz[i] < sz[j]) {
    //         id[i] = j;
    //         sz[j] += sz[i];
    //     }
    //     else {
    //         id[j] = i;
    //         sz[i] += sz[j];
    //     }
    // }

    // does the system percolate?
    public boolean percolates() {
        return quFind.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
