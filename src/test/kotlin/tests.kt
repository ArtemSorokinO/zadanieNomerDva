import org.junit.jupiter.api.Test
import zadanie.Tail
import zadanie.main
import java.io.File
import kotlin.test.assertEquals
import zadanie.createFile
import kotlin.test.assertFails

class Tests {
    @Test
    fun fullCheck() {
        clear()
        createFile(
            "file1.txt",
            "Лучше быть последним — первым, чем первым — последним.\n" +
                    "Кем бы ты ни был, кем бы ты не стал, помни, где ты был и кем ты стал.\n" +
                    "На случай, если буду нужен, то я там же, где и был, когда был не нужен\n" +
                    "Делай как надо, как не надо не делай.\n" +
                    "Если волк молчит, то лучше его не перебивай.\n" + //5
                    "Работа не волк, работа это ворк, а волк — это ходить.\n" +
                    "Легко вставать, когда ты не ложился.\n" +
                    "Иногда жизнь — это жизнь, а ты в ней иногда.\n" +
                    "Запомните. А то забудете.\n" +
                    "Запомните волчья ягода не из волков.\n" + //10
                    "Громко — это как тихо, только громче.\n" +
                    "У батареек есть один минус, и один плюc.\n" +
                    "АУФ" //13
        )
        createFile(
            "file2.txt",
            "Не верь тому, кому не веришь, ведь вера в не веру рождает еще большее недоверие, чем доверие.\n" +
                    "Бесплатный сыр бывает только бесплатным.\n" +
                    "Сначала потом, затем, снова опять.\n" +
                    "Время не просто — оно просто время." //4
        )
        createFile(
            "file3.txt",
            "Лучше один раз упасть, чем сто раз упасть.\n" +
                    "В этой жизни ты либо волк, либо НЕ волк.\n" +
                    "Будь сильным, но не сильно будь." //3
        )
//////////////////////////////////////////FIRST////////////////////////////////////////////////
        //Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …

        var args =
            "-c 2 -o output.txt fileN file1.txt".split(" ")
        val ans1 = "УФ"

        main(args)
        assertEquals(File("output.txt").bufferedReader().readText(), ans1)
        File("output.txt").delete()

        //println("first test complete")
//////////////////////////////////////////SECOND////////////////////////////////////////////////
        //Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …
        args =
            ("-n " + "2 " + "-o " + "output.txt " + "fileN " + "file1.txt " + "file2.txt").split(" ")

        val ans2 = "Name: file1.txt\n" +
                "У батареек есть один минус, и один плюc.\n" +
                "АУФ\n" +
                "Name: file2.txt\n" +
                "Сначала потом, затем, снова опять.\n" +
                "Время не просто — оно просто время."

        main(args)
        assertEquals(File("output.txt").bufferedReader().readText(), ans2)
        File("output.txt").delete()
        //println("second test complete")
/////////////////////////////////////////////THIRD//////////////////////////////////////////////
        //Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …
        args =
            ("-c " + "6 " + "-o " + "output.txt " + "fileN " + "file1.txt " + "file2.txt").split(" ")

        val ans3 = "Name: file1.txt\n" +
                "юc.\n" +
                "АУФ\n" +
                "Name: file2.txt\n" +
                "время."

        main(args)
        assertEquals(File("output.txt").bufferedReader().readText(), ans3)
        File("output.txt").delete()
        println("third test complete")

//////////////////////////////////////////FOURTH////////////////////////////////////////////////
        args =
            ("-n " + "2 " + "-o " + "output.txt " + "fileN " + "file1.txt").split(" ")

        val ans4 = "У батареек есть один минус, и один плюc.\n" +
                "АУФ"

        main(args)
        assertEquals(File("output.txt").bufferedReader().readText(), ans4)
        File("output.txt").delete()
//////////////////////////////////PRINTLN TEST///////////////////////////////////////////////////
        args =
            ("-n " + "4 " + "fileN " + "file1.txt").split(" ")
        main(args)
        File("output.txt").delete()
//////////////////////////////////DEFAULT VALUE TEST/////////////////////////////////////////////
        args =
            ("-o " + "output.txt " + "fileN " + "file1.txt").split(" ")

        val ans5 = "Делай как надо, как не надо не делай.\n" +
                "Если волк молчит, то лучше его не перебивай.\n" +
                "Работа не волк, работа это ворк, а волк — это ходить.\n" +
                "Легко вставать, когда ты не ложился.\n" +
                "Иногда жизнь — это жизнь, а ты в ней иногда.\n" +
                "Запомните. А то забудете.\n" +
                "Запомните волчья ягода не из волков.\n" +
                "Громко — это как тихо, только громче.\n" +
                "У батареек есть один минус, и один плюc.\n" +
                "АУФ"

        main(args)
        assertEquals(File("output.txt").bufferedReader().readText(), ans5)
        File("output.txt").delete()
///////////////////////////////////////////END OF GOOD TESTS//////////////////////////////////////////

        args =
            ("-n " + "4003 " + "-o " + "output.txt " + "fileN " + "file1.txt").split(" ")
        assertFails { main(args) }
        args =
            ("-c " + "4003 " + "-o " + "output.txt " + "fileN " + "file1.txt").split(" ")
        assertFails { main(args) }


//////////////////////////////////////////END OF ALL//////////////////////////////////////////
        clear()
    }

    fun clear() {
        if (File("file1.txt").exists()) File("file1.txt").delete()
        if (File("file2.txt").exists()) File("file2.txt").delete()
        if (File("file3.txt").exists()) File("file3.txt").delete()
        if (File("output.txt").exists()) File("output.txt").delete()
    }
}