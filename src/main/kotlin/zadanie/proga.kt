package zadanie

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException
//import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.File

/*
fun readLine(inputStream: FileInputStream): String {
    var c = inputStream.read().toChar()
    var string = ""
    while (c != '\n') {
        string += c
        c = inputStream.read().toChar()
    }
    return string
}
*/

fun createFile(name: String, content: String): File {
    val file = File(name)
    file.createNewFile()
    file.bufferedWriter().use { it.write(content) }
    return file
}

/*
Выделение из текстового файла его конца некоторого размера:
● fileN задаёт имя входного файла. Если параметр отсутствует, следует
считывать входные данные с консольного ввода. Если параметров несколько,
то перед выводом для каждого файла следует вывести его имя в отдельной
строке.
● Флаг -o ofile задаёт имя выходного файла (в данном случае ofile). Если
параметр отсутствует, следует выводить результат на консольный вывод.
● Флаг -с num, где num это целое число, говорит о том, что из файла нужно
извлечь последние num символов.
● Флаг -n num, где num это целое число, говорит о том, что из файла нужно
извлечь последние num строк.

В случае, когда какое-нибудь из имён файлов неверно или указаны одновременно
флаги -c и -n, следует выдать ошибку. Если ни один из этих флагов не указан, следует
вывести последние 10 строк.
Кроме самой программы, следует написать автоматические тесты к ней.

Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …
 */

class Tail : Runnable {
    @Option(name = "-c", metaVar = "count of last symbols", forbids = ["-n"])
    var countOfSymbols: Int? = null

    @Option(name = "-n", metaVar = "count of last strings (default 10)", forbids = ["-c"])
    var countOfStrings: Int? = null

    @Option(name = "-o", metaVar = "output file name")
    var outFileN: String? = null

    @Argument(required = true, metaVar = "Input file name")
    lateinit var filePaths: MutableList<String>
    override fun run() {
        println("countOfStrings - $countOfStrings ; countOfSymbols - $countOfSymbols ; outFileName - $outFileN ; Args - $filePaths")

        val isFile = isFile(filePaths)
        if (isFile) filePaths.removeFirst() else filePaths = mutableListOf(readln())

        if (countOfSymbols != null) {
            symbols(countOfSymbols!!, outFileN, filePaths, isFile)
        } else {
            if (countOfStrings == null) countOfStrings = 10
            lines(countOfStrings!!, outFileN, filePaths, isFile)
        }
    }

    fun parse(args: List<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(args)
            run()
        } catch (e: CmdLineException) {
            null
        }
    }
}

fun isFile(args: List<String>): Boolean = args.first() == "fileN"


fun main(args: List<String>) {
    Tail().parse(args)
}

fun symbols(count: Int, outFileN: String?, files: List<String>, isFile: Boolean) {
    var tail = ""
    var timedTail = ""
    var timedCount = count
    if (isFile) {
        for (i in files.reversed()) {
            var file = File(i).bufferedReader().readText().reversed()
            timedTail = ""
            timedCount = count
            while (file.isNotEmpty()) {
                if (file.first() == '\n') {
                    timedCount++
                }
                timedTail = file.first() + timedTail
                file = file.drop(1)
                if (timedTail.length >= timedCount) break
            }


            if (timedTail.length < timedCount) throw IllegalArgumentException("размер файла меньше, чем вы того хотите")
            if (files.size > 1) {
                tail = "\nName: $i\n$timedTail$tail"
            } else tail = timedTail
        }
    } else {
        val notFile = files.joinToString(separator = " ")
        tail = notFile.takeLast(count)
    }
    if (outFileN != null) {
        File("$outFileN").delete()
        createFile(outFileN, tail.trimStart())
    } else {
        println(tail)
    }
}

fun lines(count: Int, outFileN: String?, files: List<String>, isFile: Boolean) {
    var tail = ""

    if (isFile) {
        for (i in files.reversed()) {
            var file = File(i).bufferedReader().readText().reversed()
            var l = 0
            while (file.isNotEmpty()) {
                tail = file.first() + tail
                file = file.drop(1)
                if (file.first() == '\n') l++
                if (l == count) break
            }
            if (l < count) throw IllegalArgumentException("размер файла меньше, чем вы того хотите")
            if (files.size > 1) tail = "\nName: $i\n$tail"
        }
    } else {
        var notFile = files.joinToString(separator = " ")
        var l = 0
        while (notFile.isNotEmpty()) {
            tail = notFile.first() + tail
            notFile = notFile.drop(1)
            if (notFile.first() == '\n') l++
            if (l == count) break
        }
        if (l < count) throw IllegalArgumentException("размер файла меньше, чем вы того хотите")
    }

    if (outFileN != null) {
        File("$outFileN").delete()
        createFile(outFileN, tail.trimStart())
    } else {
        println(tail)
    }
}



