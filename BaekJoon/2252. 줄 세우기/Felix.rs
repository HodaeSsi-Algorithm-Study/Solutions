fn parse<T1: std::str::FromStr, T2: std::iter::FromIterator<T1>>(line: &str) -> T2
where
    <T1 as std::str::FromStr>::Err: std::fmt::Debug,
{
    line.trim()
        .split_ascii_whitespace()
        .map(|word| T1::from_str(word).unwrap())
        .collect()
}

fn input<T1: std::str::FromStr, T2: std::iter::FromIterator<T1>, T3: std::iter::FromIterator<T2>>(
) -> T3
where
    <T1 as std::str::FromStr>::Err: std::fmt::Debug,
{
    std::io::read_to_string(std::io::stdin())
        .expect("PARSE_ERROR")
        .trim()
        .split('\n')
        .map(parse::<T1, T2>)
        .collect::<T3>()
}

#[derive(Debug)]
struct Compare {
    shorter: usize,
    taller: usize,
}

fn solve(n: usize, compares: Vec<Compare>) -> Vec<usize> {
    let mut requirements = vec![0; n + 1];
    let mut graph = vec![vec![]; n + 1];
    let mut ret = vec![];

    for Compare { shorter, taller } in compares {
        requirements[taller] += 1;
        graph[shorter].push(taller);
    }

    let mut queue = (0..(n + 1))
        .zip(requirements.iter())
        .skip(1)
        .filter(|&(_node, &req)| req == 0)
        .map(|(node, _req)| node)
        .collect::<std::collections::VecDeque<_>>();

    while let Some(node) = queue.pop_front() {
        ret.push(node);

        for &next_node in &graph[node] {
            requirements[next_node] -= 1;
            if requirements[next_node] == 0 {
                queue.push_back(next_node);
            }
        }
    }

    ret
}

fn main() {
    let mut input = input::<usize, Vec<_>, std::collections::VecDeque<_>>();
    let line = input.pop_front().unwrap();

    let (n, m) = (line[0], line[1]);

    let compares = input
        .drain(0..m)
        .map(|compare| Compare {
            shorter: compare[0],
            taller: compare[1],
        })
        .collect::<Vec<_>>();

    let answer = solve(n, compares);

    let output = answer
        .iter()
        .map(ToString::to_string)
        .collect::<Vec<_>>()
        .join(" ");

    println!("{}", output);
}
