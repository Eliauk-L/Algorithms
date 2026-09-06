import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = {123,24,66,21,123,27};
        int target = 66;
        Arrays.sort(nums);
        System.out.println(binarySearch(nums,target));
    }

    public static int binarySearch(int[] nums, int target){
        if (target < nums[0] || target > nums[nums.length - 1])
        {
            return -1;
        }
        // 左闭右闭
//        int left = 0, right = nums.length-1;
//        while(left <= right){ // 取等号时只有左闭右闭符合要求
//            int mid = left + ((right - left) >> 1);
//            if(nums[mid] == target){
//                return mid;
//            }else if(nums[mid] < target){
//                left = mid+1;
//            }else {
//                right = mid - 1;
//                // 当nums[mid] > target时已经明确mid不属于该区间内，故mid要减1，
//                // 因为如果right=mid的话，下次判断时仍会判断mid的值
//            }
//        }

        // 左闭右开
        int left = 0, right = nums.length;
        while(left < right){
            int mid = left + ((right - left) >> 1);
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] > target){
                right = mid;
            }else{
                left = mid + 1;
            }
        }

        return -1;
    }
}
