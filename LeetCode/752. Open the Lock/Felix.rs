struct Solution;

impl Solution {
    pub fn open_lock(deadends: Vec<String>, target: String) -> i32 {
        let target = target.parse::<usize>().unwrap();

        let mut queue = std::collections::VecDeque::new();

        queue.push_back(0);
        let mut dist = [i32::MAX; 10000];

        deadends
            .into_iter()
            .map(|deadend| deadend.parse::<usize>().unwrap())
            .for_each(|num| dist[num] = 0);

        if dist[0] == 0 {
            return -1;
        }

        if target == 0 {
            return 0;
        }

        dist[0] = 0;

        while let Some(num) = queue.pop_front() {
            for nxt in Solution::rotate_cadidate(num) {
                if dist[nxt] <= dist[num] + 1 {
                    continue;
                }

                if nxt == target {
                    return dist[num] + 1;
                }

                dist[nxt] = dist[num] + 1;
                queue.push_back(nxt);
            }
        }

        -1
    }

    pub fn rotate_cadidate(num: usize) -> [usize; 8] {
        let (one, two, three, four) = (
            (num / 1000) as isize,
            ((num % 1000) / 100) as isize,
            ((num % 100) / 10) as isize,
            (num % 10) as isize,
        );

        [
            ((one - 1).rem_euclid(10) * 1000 + two * 100 + three * 10 + four) as usize,
            ((one + 1).rem_euclid(10) * 1000 + two * 100 + three * 10 + four) as usize,
            (one * 1000 + (two - 1).rem_euclid(10) * 100 + three * 10 + four) as usize,
            (one * 1000 + (two + 1).rem_euclid(10) * 100 + three * 10 + four) as usize,
            (one * 1000 + two * 100 + (three - 1).rem_euclid(10) * 10 + four) as usize,
            (one * 1000 + two * 100 + (three + 1).rem_euclid(10) * 10 + four) as usize,
            (one * 1000 + two * 100 + three * 10 + (four - 1).rem_euclid(10)) as usize,
            (one * 1000 + two * 100 + three * 10 + (four + 1).rem_euclid(10)) as usize,
        ]
    }
}

fn main() {
    assert_eq!(
        6,
        Solution::open_lock(
            vec![
                "0201".to_string(),
                "0101".to_string(),
                "0102".to_string(),
                "1212".to_string(),
                "2002".to_string()
            ],
            "0202".to_string()
        )
    );

    assert_eq!(
        1,
        Solution::open_lock(vec!["8888".to_string()], "0009".to_string())
    );

    assert_eq!(
        -1,
        Solution::open_lock(
            vec![
                "8887".to_string(),
                "8889".to_string(),
                "8878".to_string(),
                "8898".to_string(),
                "8788".to_string(),
                "8988".to_string(),
                "7888".to_string(),
                "9888".to_string()
            ],
            "8888".to_string()
        )
    )
}
