import java.util.Random;

public class Sort {
    public static void main(String[] args) {
        int[] nums = {123,24,66,21,123,27};
        insertSort(nums);
//        bubbleSort(nums);
//        quickSort(nums);
//        heapSort(nums);
//        mergeSort(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //insertSort
    //
    public static void insertSort(int[] nums){
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            int j = i - 1;
            while(j >= 0 && num < nums[j]){
                nums[j+1] = nums[j];
                j--;
            }
            nums[j+1] = num;
        }
    }

    // bubbleSort
    public static void bubbleSort(int[] nums){
        int n = nums.length;
        boolean swaped = false;
        for (int i = 0; i < n - 1; i++) {
            swaped = false;
            for(int j = n - 1; j > i; j--){
                if(nums[j] < nums[j-1]){
                    swap(nums,j,j-1);
                    swaped = true;
                }
            }
            if(!swaped)
                return;
        }
    }

    // quickSort
    public static void quickSort(int[] nums){
        quickSort(nums,0,nums.length-1);
    }

    public static void quickSort(int[] nums, int left, int right){
        if(left < right){
            int pivot = partition(nums,left,right);
            quickSort(nums,left,pivot-1);
            quickSort(nums,pivot+1,right);
        }
    }

    public static int partition(int[] nums,int left, int right){
//        int i = new Random().nextInt(right - left) + left;
//        swap(nums,right,i);

        int pivot = nums[right];
        int i = left - 1;
        for(int j = left; j < right; j++){
            if(nums[j] < pivot){
                i++;
                swap(nums,i,j);
            }
        }
        swap(nums,i+1,right);
        return i+1;
    }

    //heapSort
    public static void heapSort(int[] nums){
        int n = nums.length;
        for(int i = n / 2 - 1; i>=0;i--){
            heapify(nums,n,i);
        }
        for(int i = n - 1; i >= 0;i--){
            swap(nums,0,i);
            heapify(nums,i,0);
        }
    }

    public static void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if(left < n && nums[left] > nums[largest]){
            largest = left;
        }
        if(right < n && nums[right] > nums[largest]){
            largest = right;
        }
        if(largest != i){
            swap(nums,largest,i);
            heapify(nums,n,largest);
        }
    }

    //mergeSort
    public static void mergeSort(int[] nums){
        mergeSort(nums,0,nums.length-1);
    }

    public static void mergeSort(int[] nums, int left, int right){
        if(left < right){
            int mid = (left + right) / 2;
            mergeSort(nums,left,mid);
            mergeSort(nums,mid+1,right);
            merge(nums,left,mid,right);
        }
    }

    public static void merge(int[] nums, int left, int mid, int right){
        int[] temp = new int[right-left+1];
        int i = left, j = mid + 1,k = 0;
        while(i <= mid && j <= right){
            if(nums[i] < nums[j]){
                temp[k++] = nums[i++];
            }else{
                temp[k++] = nums[j++];
            }
        }
        while(i <= mid)
            temp[k++] = nums[i++];
        while(j <= right)
            temp[k++] = nums[j++];
        k = 0;
        for(int t = left; t <= right; t++){
            nums[t] = temp[k++];
        }
    }

}
