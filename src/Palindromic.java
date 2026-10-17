import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Palindromic {
    public static void main(String[] args) {
        Palindromic pd = new Palindromic();
        int ans = pd.countSubstrings3("aaa");
        System.out.println(ans);
    }
    // 1. 回文子串数量
    //https://leetcode.cn/problems/palindromic-substrings/description/
    public int countSubstrings(String s){
        int ans = 0;
        // 枚举回文中心
        for(int i = 0; i < s.length(); i++){
            // 回文中心可能是一个字符（字符串为奇数）也可能是两个字符（字符串为偶数）
            for(int j = 0; j <= 1; j++){
                int left = i;
                int right = i + j;
                while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
                    ans++;
                    left--;
                    right++;
                }
            }
        }
        return ans;
    }

    public int countSubstrings2(String s){
        // 动态规划
        int ans = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int j = 0; j < s.length(); j++){
            for(int i = 0; i <= j; i++){
                if(s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i+1][j-1])){
                    dp[i][j] = true;
                    ans++;
                }
            }
        }
        return ans;
    }

    public int countSubstrings3(String s){
        // 左右扩张
        int ans = 0;
        for(int center = 0; center < s.length(); center++){
            ans += expand(s,center,center) + expand(s,center,center+1);
        }
        return ans;
    }

    private int expand(String s, int left, int right){
        int ans = 0;
        while(left>=0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            ans++;
            left--;
            right++;
        }
        return ans;
    }

    // 2. 最长回文子串
    //https://leetcode.cn/problems/longest-palindromic-substring/description/
    public String longestPalindrome(String s) {
        String ans = "";
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int j = 0; j < s.length(); j++){
            for(int i = 0; i <= j; i++){
                if(s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i+1][j-1])){
                    dp[i][j] = true;
                    if(j - i + 1 > ans.length()){
                        ans = s.substring(i,j+1);
                    }
                }
            }
        }
        return ans;
    }

    public String longestPalindrome2(String s) {
        int start = 0, end = 0;
        for(int i = 0; i < s.length();i++){
            int len1 = expandAroundCenter(s,i,i);
            int len2 = expandAroundCenter(s,i,i+1);
            int len = Math.max(len1,len2);
            if(len > end - start){
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start,end+1);
    }

    private int expandAroundCenter(String s, int left, int right){
        while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }

    // 3. 最长回文子序列
    //https://leetcode.cn/problems/longest-palindromic-subsequence/description/
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for(int i = n - 1; i >= 0; i--){
            dp[i][i] = 1;
            char c1 = s.charAt(i);
            for(int j = i + 1; j < n; j++){
                char c2 = s.charAt(j);
                if(c1 == c2){
                    dp[i][j] = dp[i+1][j-1] + 2;
                }else{
                    dp[i][j] = Math.max(dp[i+1][j],dp[i][j-1]);
                }
            }
        }
        return dp[0][n-1];
    }

    //4. 分割回文串
    //给你一个字符串 s，请你将 s 分割成一些 子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
    //https://leetcode.cn/problems/palindrome-partitioning/description/
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        int n = s.length();
        // 预处理回文子串
        boolean[][] dp = new boolean[n][n];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i],true);
        }

        for(int i = n - 1; i >= 0; i--){
            for(int j = i + 1; j < n; j++){
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i+1][j-1];
            }
        }
        List<String> collection = new ArrayList<>();
        dfs(s,0,ans,collection,dp);
        return ans;
    }

    /*
    假设我们当前搜索到字符串的第 i 个字符，且 s[0..i−1] 位置的所有字符已经被分割成若干个回文串，并且分割结果被放入了答案数组 ans 中，
    那么我们就需要枚举下一个回文串的右边界 j，使得 s[i..j] 是一个回文串。
     */
    public void dfs(String s, int start, List<List<String>> ans, List<String> collection, boolean[][] dp){
        if(start == s.length()){
            ans.add(new ArrayList<>(collection));
            return;
        }

        for(int i = start; i < s.length(); i++){
            if(dp[start][i]){
                collection.add(s.substring(start,i+1));
                dfs(s,i+1,ans,collection,dp);
                collection.remove(collection.size()-1);
            }
        }
    }
}
