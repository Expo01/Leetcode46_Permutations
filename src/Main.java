import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
//BACKTRACKING TOP USER SOLUTION. this is a lot closer to what i was trying to do, adding elements and then adding to
// return list when size == length
class Solution {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
        if(tempList.size() == nums.length){
            list.add(new ArrayList<>(tempList));
        } else{
            for(int i = 0; i < nums.length; i++){
                if(tempList.contains(nums[i])) continue; // element already exists, skip
                tempList.add(nums[i]);
                backtrack(list, tempList, nums);
                tempList.remove(tempList.size() - 1);
            }
            // supose set is (0,1,2) this would create new templist versions and pass those into recursive method for
            //(0), (1), (2) each time deleting itself before looping. then suppose we are pusuing recursion where
            // templist is (0). now we skip adding 0 since it exists, then add (0,1) and (0,2). lets assume we now are
            // pursuing (0,2) as passed temp list, 0 exists so skip, add 1 (0,2,1) 2 exists so skip. pass (0,2,1)
            // as the tempList and see that size 3 == length 3 so add it. This version feels much cleaner than swapping to me
        }
    }

}

//BACKTRACKING EDITORIAL. swapping has never been super intuitive to me and i dont inherently like needing to rely on
// the collections function to write a solution. try to avoid inbuilt functions...
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> output = new LinkedList();

        ArrayList<Integer> nums_lst = new ArrayList<Integer>();  // convert nums into lists so the permutation
        for (int num : nums) { // can just be added directly and not converted with each instance of new valid permutation
            nums_lst.add(num);
        }

        int n = nums.length;
        backtrack(n, nums_lst, output, 0);
        return output;
    }

    public void backtrack(int n, ArrayList<Integer> nums, List<List<Integer>> output, int first) {
        if (first == n) // first is a variable that identifies current index that will be swapped with another index
            output.add(new ArrayList<Integer>(nums)); // such that if first ever becomes OOB, then add the permutation

        for (int i = first; i < n; i++) { // i variable identifies the index that will be used to swap with first
            // where i begins == to first so first swaps with itself to add existing order as a permutation sequence
            Collections.swap(nums, first, i); // first and i are indexes. the swap functions swaps the values at the indexes
            backtrack(n, nums, output, first + 1); // pass the next index as new first to swap items in the remaining
            // sublist
            Collections.swap(nums, first, i); // return the values to where they were such that if we swap
            //(1,2,3,4) first = 0 and i = 1 --> (2,1,3,4) so when we swap first = 0 and i = 2, we need to revert to
            // (1,2,3,4) to swap to (3,2,1,4) not (2,3,1,4)
        }
    }
}

// two attempts at solution. not adding the way i hoped. but good info in here or how to convert a set to a list
class Solution {
    List<List<Integer>> permutations = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            Set<Integer> order = new HashSet<>();
            helper(num, i, nums, order);
            System.out.println("i index and value from permute " + i + " and " + num);
        }
        return permutations;
    }

    private void helper(int start, int curIndex, int[] nums, Set<Integer> order) {
        if (!order.contains(start)) {
            order.add(start);

            for (int i = curIndex - 1; i >= 0; i--) {
                helper(nums[i], i, nums, order);
                System.out.println("first for loop runs");
            }

            for (int i = curIndex + 1; i < nums.length; i++) {
                helper(nums[i], i, nums, order);
                System.out.println("second for loop runs");
            }

            if (order.size() == nums.length) {
                System.out.println("terminates");
                List<Integer> convert = new ArrayList<Integer>(order);
                permutations.add(convert);
            }
        }
    }

    private void helper(int start, int curIndex, int[] nums, Set<Integer> order) {
        if (!order.contains(start)) {
            order.add(start);

            while (curIndex > 0) {
                helper(nums[curIndex - 1], curIndex + 1, nums, order);
                System.out.println("first for loop runs");
            }

            while (curIndex < nums.length) {
                helper(nums[curIndex + 1], curIndex + 1, nums, order);
                System.out.println("second for loop runs");
            }

            if (order.size() == nums.length) {
                System.out.println("terminates");
                List<Integer> convert = new ArrayList<Integer>(order);
                permutations.add(convert);
            }
        }
    }
}
}

// for loop through nums. pass nums[i] into helper method. add the item to a set
// recurse i<current index and i > current index while in bounds and item not in set
// once all valid items searched, add the set to return list.

// given [1,2,3], starting on 1. it will go like
// helper instance on 1, add 1 [1] recurse 2
// helperr instancee on 2 add to set [1,2], search to L, 1 already in set, search 3
// helper on 3, add to set [1,2,3] no other seearch optons, add [1,2,3] to return
// now  back to helper on 2. but now set [1,2] and no valid searches but don't want to add so many only add to returrnr is set sizee == nums size. soo this instance of helper finishes
// back to instance of 1 where set still is [1] and we passed 2 last time, now pass 3 and ultimately get [1,3,2] added to set but don't add [1,3].
// finally back to 1 again with both 2 and 3 explored but set = [1] and not corrrect size so thats it