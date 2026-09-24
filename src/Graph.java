import java.util.*;
import java.util.LinkedList;

/**
 * @author Jay
 * @description
 * @date 2026/9/24
 */
public class Graph {

    //dfs O(mn) O(mn)
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    public void dfs(char[][] grid, int i, int j) {
        // 递归终止条件
        if (i >= grid.length || i < 0 || j < 0 || j >= grid[0].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
    }

    // bfs O(mn) O(min(m,n))
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i * n + j);
                    while (!queue.isEmpty()) {
                        int idx = queue.poll();
                        int row = idx / n;
                        int col = idx % n;
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            queue.offer((row - 1) * n + col);
                            grid[row - 1][col] = '0';
                        }
                        if (row + 1 < m && grid[row + 1][col] == '1') {
                            queue.offer((row + 1) * n + col);
                            grid[row + 1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            queue.offer(row * n + col - 1);
                            grid[row][col - 1] = '0';
                        }
                        if (col + 1 < n && grid[row][col + 1] == '1') {
                            queue.offer(row * n + col + 1);
                            grid[row][col + 1] = '0';
                        }
                    }
                }
            }
        }
        return ans;
    }

    // 拓扑排序
    // bfs
    List<List<Integer>> edges;
    int[] indeg;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for(int i = 0; i < numCourses; i++){
            edges.add(new ArrayList<>());
        }

        indeg = new int[numCourses];
        for(int[] info : prerequisites){
            edges.get(info[1]).add(info[0]);
            indeg[info[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < numCourses; i++) {
            if(indeg[i] == 0) {
                queue.offer(i);
            }
        }

        int visited = 0;
        while(!queue.isEmpty()){
            int u = queue.poll();
            visited++;
            for(int v : edges.get(u)){
                indeg[v]--;
                if(indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        return visited == numCourses;
    }

}
