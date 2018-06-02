import io

fun main() : i64 {
    n : i64 = 10

    a : i64 = 1
    b : i64 = 1

    for i : i64 in 0..n {
        tmp : i64 = b
        b += a
        a = tmp
    }

    io.println("Fibonacci %d is %d.\n", n, b)
    return b
}