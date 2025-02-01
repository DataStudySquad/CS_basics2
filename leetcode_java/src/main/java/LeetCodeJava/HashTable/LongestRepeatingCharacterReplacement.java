package LeetCodeJava.HashTable;

// https://leetcode.com/problems/longest-repeating-character-replacement/

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

/**
 *  424. Longest Repeating Character Replacement
 * Solved
 * Medium
 * Topics
 * Companies
 * You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times.
 *
 * Return the length of the longest substring containing the same letter you can get after performing the above operations.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "ABAB", k = 2
 * Output: 4
 * Explanation: Replace the two 'A's with two 'B's or vice versa.
 * Example 2:
 *
 * Input: s = "AABABBA", k = 1
 * Output: 4
 * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
 * The substring "BBBB" has the longest repeating letters, which is 4.
 * There may exists other ways to achieve this answer too.
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 105
 * s consists of only uppercase English letters.
 * 0 <= k <= s.length
 *
 */
public class LongestRepeatingCharacterReplacement {

    // V0
    // IDEA : TWO POINTER + HASHMAP + FOR LOOP
    // https://github.com/yennanliu/CS_basics/blob/master/leetcode_python/Hash_table/longest-repeating-character-replacement.py
    public int characterReplacement(String s, int k) {
        Map<Character, Integer> table = new HashMap<>();
        int res = 0;
        int p1 = 0;

        /**
         *  NOTE !!!
         *
         *   slow pointer: p1
         *   fast pointer: p2
         */
        for (int p2 = 0; p2 < s.length(); p2++) {
            char c = s.charAt(p2);
            table.put(c, table.getOrDefault(c, 0) + 1);
            /**
             *  NOTE !!!
             *   instead of tracking `last different element freq`,
             *   we track MOST frequent element in window (elements between slow, fast pointer)
             *
             *   -> so we use a hashMap approach above
             *   -> in the while loop, keep check if max `different element count` is still < k
             *   (e.g.  (p2 - p1 + 1) - getMaxValue(table) > k)
             *
             *   -> update hashMap when new element is reached on the same time
             *   (e.g.  table.put(c1, table.get(c1) - 1);)
             *
             *
             *   NOTE !!!
             *   no need to modify `k`
             *   we count different element via `(p2 - p1 + 1) - getMaxValue(table)`
             */
            while ( (p2 - p1 + 1) - getMaxValue(table) > k) {
                char c1 = s.charAt(p1);
                table.put(c1, table.get(c1) - 1);
                p1++;
            }
            res = Math.max(res, p2 - p1 + 1);
        }
        return res;
    }

    private int getMaxValue(Map<Character, Integer> map) {
        int max = 0;
        for (int value : map.values()) {
            max = Math.max(max, value);
        }
        return max;
    }

    // V0'
    public int characterReplacement_0_1(String s, int k) {

        if (s.length() < k){
            return 0;
        }

        Map<String, Integer> map = new HashMap<>();
        String[] s_array = s.split("");
        int l = 0;
        int res = 0;
        for (int r = 0; r < s_array.length; r++){

            String key = s_array[r];
            /**
             *  NOTE !!!
             *
             *  we use hashmap for recording element, and its count (and keep updating it in for loop)
             *  e.g.
             *      key : element
             *      value : element count
             */
            // NOTE : map.getOrDefault(key,0) syntax :  if can find key, return its value, else, return default 0
            map.put(key, map.getOrDefault(key,0)+1);

            /** NOTE !!!
             *
             *  put "(r - l + 1) - getMaxValue_0_1(map) > k" in while loop
             *  -> keep checking if "remaining different elements count < k"
             *  -> e.g. keep if they can be changed into the same element <= k times
             */
            while ((r - l + 1) - getMaxValue_0_1(map) > k){
                map.put(s_array[l], map.get(s_array[l])-1);
                // map return key if val is 0
                if (map.get(s_array[l]) == 0){
                    map.remove(s_array[l]);
                }
                l += 1;
            }

            res = Math.max(res, r - l + 1);
        }

        return res;

    }

    private int getMaxValue_0_1(Map<String, Integer> map) {
        int max = 0;
        for (int value : map.values()) {
            max = Math.max(max, value);
        }
        return max;
    }

    // V0''
    // IDEA : TWO POINTER + HASHMAP (modified by GPT)
    // https://github.com/yennanliu/CS_basics/blob/master/leetcode_python/Hash_table/longest-repeating-character-replacement.py
    public int characterReplacement_0(String s, int k) {
        Map<Character, Integer> table = new HashMap<>();
        int res = 0;
        int p1 = 0, p2 = 0;

        while (p2 < s.length()) {
            char c = s.charAt(p2);
            table.put(c, table.getOrDefault(c, 0) + 1);
            p2++;

            while (p2 - p1 - getMaxValue_0(table) > k) {
                char c1 = s.charAt(p1);
                table.put(c1, table.get(c1) - 1);
                p1++;
            }
            res = Math.max(res, p2 - p1);
        }
        return res;
    }

    private int getMaxValue_0(Map<Character, Integer> map) {
        int max = 0;
        for (int value : map.values()) {
            max = Math.max(max, value);
        }
        return max;
    }


//    // V0
//    // IDEA : SLIDING WINDOW
//    public int characterReplacement(String s, int k) {
//
//        if (s.length() == 0 || s.equals(null)){
//            return 0;
//        }
//
//        int ans = 0;
//        char[] s_char = s.toCharArray();
//
//        for (int i = 0; i < s_char.length-1; i++){
//
//            Set set = new HashSet<>();
//            int j = i;
//            int tmp_k = k;
//
//            while (tmp_k >= 0 && j < s_char.length){
//                String cur = String.valueOf(s_char[j]);
//                if (set.isEmpty()){
//                    set.add(cur);
//                }else if(set.contains(cur)){
//                    j += 1;
//                    ans = Math.max(ans, j - i + 1);
//                }else {
//                    if (k > 0){
//                        j += 1;
//                        k -= 1;
//                        ans = Math.max(ans, j - i + 1);
//                    }else{
//                        ans = Math.max(ans, j - i + 1);
//                        break;
//                    }
//                }
//            }
//        }
//
//        return ans;
//    }

    // V1
    // IDEA : Sliding Window + Binary Search
    // https://leetcode.com/problems/longest-repeating-character-replacement/editorial/
    public int characterReplacement_2(String s, int k) {
        // binary search over the length of substring
        // lo contains the valid value, and hi contains the
        // invalid value
        int lo = 1;
        int hi = s.length() + 1;

        while (lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;

            // can we make a valid substring of length `mid`?
            if (canMakeValidSubstring(s, mid, k)) {
                // explore the right half
                lo = mid;
            } else {
                // explore the left half
                hi = mid;
            }
        }

        // length of the longest substring that satisfies
        // the given condition
        return lo;
    }

    private Boolean canMakeValidSubstring(
            String s,
            int substringLength,
            int k) {
        // take a window of length `substringLength` on the given
        // string, and move it from left to right. If this window
        // satisfies the condition of a valid string, then we return
        // true

        int[] freqMap = new int[26];
        int maxFrequency = 0;
        int start = 0;
        for (int end = 0; end < s.length(); end += 1) {
            freqMap[s.charAt(end) - 'A'] += 1;

            // if the window [start, end] exceeds substringLength
            // then move the start pointer one step toward right
            if (end + 1 - start > substringLength) {
                // before moving the pointer toward right, decrease
                // the frequency of the corresponding character
                freqMap[s.charAt(start) - 'A'] -= 1;
                start += 1;
            }

            // record the maximum frequency seen so far
            maxFrequency = Math.max(maxFrequency, freqMap[s.charAt(end) - 'A']);
            if (substringLength - maxFrequency <= k) {
                return true;
            }
        }

        // we didn't a valid substring of the given size
        return false;
    }


    // V2
    // IDEA : Sliding Window (Slow)
    // https://leetcode.com/problems/longest-repeating-character-replacement/editorial/
    public int characterReplacement_4(String s, int k) {
        HashSet<Character> allLetters = new HashSet();

        // collect all unique letters
        for (int i = 0; i < s.length(); i++) {
            allLetters.add(s.charAt(i));
        }

        int maxLength = 0;
        for (Character letter : allLetters) {
            int start = 0;
            int count = 0;
            // initialize a sliding window for each unique letter
            for (int end = 0; end < s.length(); end += 1) {
                if (s.charAt(end) == letter) {
                    // if the letter matches, increase the count
                    count += 1;
                }
                // bring start forward until the window is valid again
                while (!isWindowValid(start, end, count, k)) {
                    if (s.charAt(start) == letter) {
                        // if the letter matches, decrease the count
                        count -= 1;
                    }
                    start += 1;
                }
                // at this point the window is valid, update maxLength
                maxLength = Math.max(maxLength, end + 1 - start);
            }
        }
        return maxLength;
    }

    private Boolean isWindowValid(int start, int end, int count, int k) {
        // end + 1 - start - count is different element count
        return end + 1 - start - count <= k;
    }


    // V3
    // IDEA : Sliding Window (Fast)
    // https://leetcode.com/problems/longest-repeating-character-replacement/editorial/
    public int characterReplacement_5(String s, int k) {
        int start = 0;
        int[] frequencyMap = new int[26];
        int maxFrequency = 0;
        int longestSubstringLength = 0;

        for (int end = 0; end < s.length(); end += 1) {
            // if 'A' is 0, then what is the relative order
            // or offset of the current character entering the window
            // 0 is 'A', 1 is 'B' and so on
            int currentChar = s.charAt(end) - 'A';

            frequencyMap[currentChar] += 1;

            // the maximum frequency we have seen in any window yet
            maxFrequency = Math.max(maxFrequency, frequencyMap[currentChar]);

            // move the start pointer towards right if the current
            // window is invalid
            Boolean isValid = (end + 1 - start - maxFrequency <= k);
            if (!isValid) {
                // offset of the character moving out of the window
                int outgoingChar = s.charAt(start) - 'A';

                // decrease its frequency
                frequencyMap[outgoingChar] -= 1;

                // move the start pointer forward
                start += 1;
            }

            // the window is valid at this point, note down the length
            // size of the window never decreases
            longestSubstringLength = end + 1 - start;
        }

        return longestSubstringLength;
    }

}
