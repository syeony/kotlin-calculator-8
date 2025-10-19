package calculator

import camp.nextstep.edu.missionutils.Console

fun main() {
    println("덧셈할 문자열을 입력해 주세요.")
    val input = Console.readLine()
    val result = calculate(input)
    println("결과 : $result")
}

fun calculate(input: String): Int {
    if (input.isEmpty()) {
        return 0
    }

    val (delimiter, numbers) = parseInput(input)
    return numbers.split(delimiter)
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it.toPositiveInt() }
        .sum()
}

fun parseInput(input: String): Pair<Regex, String> {
    if (input.startsWith("//")) {
        // 실제 개행 문자(\n)와 리터럴 문자열(\n) 모두 처리
        var delimiterEndIndex = input.indexOf('\n')
        var skipLength = 1

        if (delimiterEndIndex == -1) {
            delimiterEndIndex = input.indexOf("\\n")
            skipLength = 2
        }

        if (delimiterEndIndex == -1) {
            throw IllegalArgumentException("잘못된 입력 형식입니다.")
        }

        val customDelimiter = input.substring(2, delimiterEndIndex)
        val numbers = input.substring(delimiterEndIndex + skipLength)
        return Regex(Regex.escape(customDelimiter)) to numbers
    }
    return Regex("[,:]") to input
}

fun String.toPositiveInt(): Int {
    val number = this.toIntOrNull() ?: throw IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: $this")
    if (number < 0) {
        throw IllegalArgumentException("음수는 허용되지 않습니다: $number")
    }
    return number
}
