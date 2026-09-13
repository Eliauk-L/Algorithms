import java.util.*;
import java.util.LinkedList;

public class Tree {
    public class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 遍历
    // 先序遍历-递归
    public void preOrder(TreeNode root) {
        if (root != null) {
            System.out.println(root.val);
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    // 先序遍历-迭代
    // 根入栈，每次弹出即访问，先压右再压左（保证左先出）
    public void preOrder2(TreeNode root){
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            System.out.println(node.val);
            if(node.right != null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }
        }
    }

    // 中序遍历-递归
    public void inOrder(TreeNode root){
        if(root != null){
            inOrder(root.left);
            System.out.println(root.val);
            inOrder(root.right);
        }
    }

    // 中序遍历-迭代
    // 一路向左压栈，无左子节点时弹出访问，再转向右子树
    public void inOrder2(TreeNode root){
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode p = root;
        while(p !=  null || !stack.isEmpty()){
            while(p != null){
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            System.out.println(p.val);
            p = p.right;
        }
    }

    // 后序遍历-递归
    public void postOrder(TreeNode root){
        if (root != null){
            postOrder(root.left);
            postOrder(root.right);
            System.out.println(root.val);
        }
    }

    // 后序遍历-单栈迭代
    // 通过判断右子节点是否已访问，决定是否输出当前节点
    public void postOrder2(TreeNode root){
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        TreeNode prev = null;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.peek();
            // 右子树为空 或 右子树已访问，则访问当前节点
            if (cur.right == null || cur.right == prev){
                stack.pop();
                System.out.println(cur.val);
                prev = cur;
                cur = null;
            }else{
                cur = cur.right;
            }
        }
    }

    // 层序遍历-bfs
    public void levelOrder(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-->0){
                TreeNode node = queue.poll();
                System.out.println(node.val);
                if(node.left !=  null) queue.offer(node.left);
                if(node.right !=  null) queue.offer(node.right);
            }
        }
    }

    // 树的深度:从根节点到最远叶子节点的最长路径上的节点数
    // 树的高度：从根节点到最深叶子节点的路径长度。
    // dfs
    public int maxDepth(TreeNode root){
        if(root == null)
            return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left,right) + 1;
    }

    // bfs
    public int maxDepth2(TreeNode root){
        if(root == null)
            return 0;
        int ans = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-->0){
                TreeNode node = queue.poll();
                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
            ans++;
        }
        return ans;
    }

    // 树的直径：树中任意两个节点之间最长路径的长度 。这条路径可能经过也可能不经过根节点 root 。
    // 等效于求路径经过节点数的最大值减一。
    public int ans;
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        depth(root);
        return ans - 1;
    }

    public int depth(TreeNode root){
        if (root == null)
            return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        ans = Math.max(ans,left+right+1);
        return Math.max(left,right) + 1;
    }

    // 翻转树
    public TreeNode invertTree(TreeNode root) {
        if(root == null)
            return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // 对称树-递归
    public boolean isSymmetric(TreeNode root){
        return check(root.left,root.right);
    }

    public boolean check(TreeNode p, TreeNode q){
        if(p == null && q == null){
            return true;
        }

        if(p == null || q == null){
            return false;
        }

        return p.val == q.val &&  check(p.left,q.right) && check(p.right,q.left);
    }

    // 对称树-迭代
    // 需要对比的节点依次入队
    public boolean isSymmetric2(TreeNode root){
        return check2(root,root);
    }

    public boolean check2(TreeNode p, TreeNode q){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);
        while(!queue.isEmpty()){
            p = queue.poll();
            q = queue.poll();
            if(p == null && q == null){
                continue;
            }
            if(p == null || q == null || (p.val != q.val)){
                return false;
            }
            queue.offer(p.left);
            queue.offer(q.right);

            queue.offer(p.right);
            queue.offer(q.left);
        }
        return true;
    }

    // 二叉搜索树

    // 有序数组转二叉搜索树
    // 中序遍历序列
    public TreeNode sortedArrayToBST(int[] nums) {
        return generate(nums,0,nums.length - 1);
    }

    public TreeNode generate(int[] nums, int left, int right){
        if(left > right)
            return null;
        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = generate(nums,left,mid-1);
        root.right = generate(nums,mid+1,right);
        return root;
    }

    // 验证二叉搜索树-递归
    // 考虑以 root 为根的子树，判断子树中所有节点的值是否都在 (l,r) 的范围内 开区间
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }

    // 验证二叉搜索树-中序遍历
    // 在中序遍历的时候实时检查当前节点的值是否大于前一个中序遍历到的节点的值
    public boolean isValidBST2(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        double prev = -Double.MAX_VALUE;
        while(!stack.isEmpty() || root != null){
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.val <= prev)
                return false;
            prev = root.val;
            root = root.right;
        }
        return true;
    }

    // 前序、中序构造二叉树
    // 先在中序遍历中定位到根节点，然后得到左子树和右子树中的节点数目
    private Map<Integer,Integer> map = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        for(int i = 0; i < n; i++){
            map.put(inorder[i],i);
        }
        return build(preorder,0,n-1,inorder,0,n-1);
    }

    public TreeNode build(int[] preorder, int preleft, int preright, int[] inorder, int inleft, int inright){
        if(preleft > preright || inleft > inright)
            return null;

        int root = preorder[preleft];
        int rootidx = map.get(root);
        int leftsize = rootidx - inleft;
        TreeNode rootnode = new TreeNode(root);
        rootnode.left = build(preorder,preleft+1,preleft+leftsize,inorder,inleft,rootidx-1);
        rootnode.right = build(preorder,preleft+leftsize+1,preright,inorder,rootidx+1,inright);
        return rootnode;
    }

    // 树的路径总和问题： 给定二叉树的根节点 root 和一个表示目标和的整数 targetSum，找出路径和为targetSum的所有路径
    // 深搜 广搜 回溯 栈

    // 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null) return false;
        if(root.left == null && root.right == null)
            return targetSum == root.val;
        return hasPathSum(root.left,targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    public boolean hasPathSum2(TreeNode root, int targetSum) {
        if(root == null) return false;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> valQueue = new LinkedList<>();
        nodeQueue.offer(root);
        valQueue.offer(root.val);
        while(!nodeQueue.isEmpty()){
            TreeNode node = nodeQueue.poll();
            int nodeval = valQueue.poll();
            if(node.left == null && node.right == null){
                if(nodeval == targetSum)
                    return true;
                continue;
            }
            if(node.left != null){
                nodeQueue.offer(node.left);
                valQueue.offer(nodeval + node.left.val);
            }

            if(node.right != null){
                nodeQueue.offer(node.right);
                valQueue.offer(nodeval + node.right.val);
            }
        }
        return false;
    }

    // 找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return ret;
    }

    public void dfs(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            ret.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, targetSum);
        dfs(root.right, targetSum);
        path.pollLast();
    }

    // 求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
    public int allPathSum(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        int ret = rootSum(root, targetSum);
        ret += allPathSum(root.left, targetSum);
        ret += allPathSum(root.right, targetSum);
        return ret;
    }

    public int rootSum(TreeNode root, long targetSum) {
        int ret = 0;

        if (root == null) {
            return 0;
        }
        int val = root.val;
        if (val == targetSum) {
            ret++;
        }

        ret += rootSum(root.left, targetSum - val);
        ret += rootSum(root.right, targetSum - val);
        return ret;
    }

    public int allPathSum2(TreeNode root, long targetSum) {
        Map<Long,Integer> prefix = new HashMap<>();
        prefix.put(0L,1);
        return dfs(root,prefix,0,targetSum);
    }

    public int dfs(TreeNode root, Map<Long,Integer> prefix, long cur, long targetSum){
        if(root == null)
            return 0;

        int sum = 0;
        cur += root.val;

        sum = prefix.getOrDefault(cur - targetSum,0);
        prefix.put(cur,prefix.getOrDefault(cur,0)+1);

        sum += dfs(root.left,prefix,cur,targetSum);
        sum += dfs(root.right,prefix,cur,targetSum);

        prefix.put(cur,prefix.getOrDefault(cur,0)-1);
        return sum;
    }

    // 二叉树的最近公共祖先 （节点的val不同）
    Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
    Set<Integer> visited = new HashSet<Integer>();

    // 找出每个节点的父节点
    public void dfs(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            dfs(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            dfs(root.right);
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root);
        while (p != null) {
            visited.add(p.val);
            p = parent.get(p.val);
        }
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }
        return null;
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if(left == null) return right;
        if(right == null) return left;
        return root;
    }

    // 二叉树的最大路径和
    // 非空节点的最大贡献值等于节点值与其子节点中的最大贡献值之和（对于叶节点而言，最大贡献值等于节点值）。
    int res = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        getMaxPathSum(root);
        return res;
    }

    public int getMaxPathSum(TreeNode root){
        if(root == null)
            return 0;
        int val = root.val;
        int left = Math.max(0,getMaxPathSum(root.left));
        int right = Math.max(0,getMaxPathSum(root.right));
        // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        res = Math.max(res,val+left+right);
        // 返回节点的最大贡献值
        return Math.max(left,right)+val;
    }
}
