package zadanie

import org.kohsuke.args4j.Argument;
//import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream

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
    @Argument(required = true, metaVar = "Input file name")
    lateinit var filePaths: MutableList<String>
    //файлы преобразуются в массив названий файлов, обработка циклом
    //если нет fileN, то это просто строка, которую надо обработать

    @Option(name = "-o", metaVar = "output file name")
    var outFileN: String? = null
    //если есть, то createFile
    //если нет, то println

    @Option(name = "-c", metaVar = "count of last symbols", forbids = ["-n"])
    var countOfSymbols: Int? = null

    @Option(name = "-n", metaVar = "count of last strings (default 10)", forbids = ["-c"])
    var countOfStrings: Int? = null

    override fun run() {
        val isFile = isFile(filePaths)
        if (isFile) filePaths.removeFirst()

        if (countOfSymbols != null) {
            symbols(countOfSymbols!!, outFileN, filePaths)
        } else {
            if (countOfStrings == null) countOfStrings = 10
            strings(countOfStrings!!, outFileN)
        }
    }
}

fun isFile(args: List<String>): Boolean = args.first() == "fileN"


fun main(args: String) {
    CmdLineParser(Tail()).parseArgument(args)
}

fun symbols(count: Int, outFileN: String?, files: List<String>) {
    var tail = ""

    for (i in files) {
        var lst = 0
        var i = i
        tail += i
        while (lst != count) {
            tail += i.last()
            i.removeRange(i.lastIndex - 1, i.lastIndex)
            lst++
        }
    }


    if (outFileN != null) {
        createFile(outFileN, tail)
    } else {
        println(tail)
    }
}

fun strings(count: Int, outFileN: String?) {
    var tail = ""



    if (outFileN != null) {
        createFile(outFileN, tail)
    } else {
        println(tail)
    }
}



