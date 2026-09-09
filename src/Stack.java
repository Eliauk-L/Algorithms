import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Stack {
    // 有效括号
    public boolean isValid(String s){
        Map<Character,Character> map = new HashMap<>();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');
        int n  = s.length();
        Deque<Character> stack = new LinkedList<>();
        for(int i = 0; i < n; i++){
            char c = s.charAt(i);
            if(c == '(' || c == '[' || c=='{')
                stack.push(c);
            else{
                if(stack.peek() == map.get(c)){
                    stack.pop();
                }else{
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // 最小栈
    // 辅助栈
    class MinStack1 {
        Deque<Integer> xStack;
        Deque<Integer> minStack;

        public MinStack1() {
            xStack = new LinkedList<Integer>();
            minStack = new LinkedList<Integer>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            xStack.push(x);
            minStack.push(Math.min(minStack.peek(), x));
        }

        public void pop() {
            xStack.pop();
            minStack.pop();
        }

        public int top() {
            return xStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    // 一个栈同时保存当前值和最小值
    class MinStack2{
        Deque<Integer> stack;// 栈中存放当前元素与栈中最小值元素（变化）的差值
        int minval;

        public MinStack2(){
            stack = new LinkedList<>();
            minval = 0;
        }

        public void push(int val){
            if(stack.isEmpty()){
                stack.push(0);
                minval = val;
            }else{
                int diff = val - minval;
                stack.push(diff);
                if(diff < 0)
                    minval = val;
            }
        }

        public void pop(){
            int diff = stack.pop();
            if(diff < 0){
                minval = minval - diff;
            }
        }

        public int top(){
            int diff = stack.peek();
            return diff > 0 ? minval + diff : minval;
        }

        public int getMin(){
            return minval;
        }
    }

    //单调栈
    //维护一个存储下标的单调栈，从栈底到栈顶的下标对应的列表中的数值依次递减
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        for (int i = 1; i < n; i++) {
            if(temperatures[stack.peek()] < temperatures[i]){
                while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]){
                    int idx = stack.pop();
                    ans[idx] = i - idx;
                }
            }
            //找到了合适的位置插入i
            stack.push(i);
        }
        return ans;
    }
}
