package zadanie

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException
//import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.File
import java.util.logging.Logger.global

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
    lateinit var files: MutableList<String>

    fun parse(args: List<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(args)
            run()
        } catch (e: CmdLineException) {
            null
        }
    }

    private fun symbols(isFile: Boolean) {
        countOfSymbols = countOfSymbols!!.toInt()
        var tail = ""
        var timedTail = ""
        var timedCount = countOfSymbols!!
        if (isFile) {
            for (i in files.reversed()) {
                var file = File(i).bufferedReader().readText().reversed()
                timedTail = ""
                timedCount = countOfSymbols!!
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
            tail = notFile.takeLast(countOfSymbols!!)
        }
        if (outFileN != null) {
            File("$outFileN").delete()
            createFile(outFileN!!, tail.trimStart())
        } else {
            println(tail)
        }
    }

    private fun lines(isFile: Boolean) {
        var tail = ""
        countOfStrings = countOfStrings!!.toInt()
        if (isFile) {
            for (i in files.reversed()) {
                var file = File(i).bufferedReader().readText().reversed()
                var l = 0
                while (file.isNotEmpty()) {
                    tail = file.first() + tail
                    file = file.drop(1)
                    if (file.first() == '\n') l++
                    if (l == countOfStrings) break
                }
                if (l < countOfStrings!!) throw IllegalArgumentException("размер файла меньше, чем вы того хотите")
                if (files.size > 1) tail = "\nName: $i\n$tail"
            }
        } else {
            var notFile = files.joinToString(separator = " ")
            var l = 0
            while (notFile.isNotEmpty()) {
                tail = notFile.first() + tail
                notFile = notFile.drop(1)
                if (notFile.first() == '\n') l++
                if (l == countOfStrings) break
            }
            if (l < countOfStrings!!) throw IllegalArgumentException("размер файла меньше, чем вы того хотите")
        }

        if (outFileN != null) {
            File("$outFileN").delete()
            createFile(outFileN!!, tail.trimStart())
        } else {
            println(tail)
        }
    }

    private fun isFile(args: List<String>): Boolean = args.first() == "fileN"

    override fun run() {
        val isFile = isFile(files)
        if (isFile) files.removeFirst() else files = mutableListOf(readln())

        if (countOfSymbols != null) {
            symbols(isFile)
        } else {
            if (countOfStrings == null) countOfStrings = 10
            lines(isFile)
        }
    }
}


fun main(args: List<String>) {
    Tail().parse(args)
}







