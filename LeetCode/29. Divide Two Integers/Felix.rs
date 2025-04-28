struct Solution;

impl Solution {
    pub fn divide(dividend: i32, divisor: i32) -> i32 {
        let dividend = dividend as isize;
        let divisor = divisor as isize;
        let mut left = if dividend < 0 { -dividend } else { dividend };
        let divisr = if divisor < 0 { -divisor } else { divisor };

        let mut ret: isize = 0;

        while divisr <= left {
            let mut tmp = divisr;
            let mut curr = 1;
            while 2 * tmp < left {
                tmp += tmp;
                curr += curr;
            }

            ret += curr;
            left -= tmp;
        }

        if dividend.signum() == divisor.signum() {
            if ret < i32::MIN as isize {
                i32::MIN
            } else if (i32::MAX as isize) < ret {
                i32::MAX
            } else {
                ret as i32
            }
        } else {
            if -ret < i32::MIN as isize {
                i32::MIN
            } else if (i32::MAX as isize) < -ret {
                i32::MAX
            } else {
                -ret as i32
            }
        }
    }
}
fn main() {
    assert_eq!(2147483647, Solution::divide(-2147483648, -1));
    assert_eq!(3, Solution::divide(10, 3));
    assert_eq!(-2, Solution::divide(7, -3));
}
