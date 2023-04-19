import org.junit.jupiter.api.Test
import zadanie.main
import java.io.File
import kotlin.test.assertEquals
import zadanie.createFile

class Tests {
    @Test
    fun fullCheck() {
        var file1 = createFile(
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
        var file2 = createFile(
            "file2.txt",
            "Не верь тому, кому не веришь, ведь вера в не веру рождает еще большее недоверие, чем доверие.\n" +
                    "Бесплатный сыр бывает только бесплатным.\n" +
                    "Сначала потом, затем, снова опять.\n" +
                    "Время не просто — оно просто время." //4
        )
        var file3 = createFile(
            "file3.txt",
            "Лучше один раз упасть, чем сто раз упасть.\n" +
                    "В этой жизни ты либо волк, либо НЕ волк.\n" +
                    "Будь сильным, но не сильно будь." //3
        )

        //Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …
        var args =
            "tail " + "-c " + "2 " + "-o " + "output.txt " + "fileN " + "file1.txt"

        var ans1 = "УФ"

        main(args)

        assertEquals(File("output.txt").toString(), ans1)

        file1.delete()
        file2.delete()
        file3.delete()

        //Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …
        args = "tail " + "-o " + "output.txt " + "file1.txt " + "file2.txt " + "file3.txt " + "-out" + "out.txt"

        var ans2 = "УФ"

        main(args)

        File("out.txt").delete()

        file1 = File("file1.txt")
        file2 = File("file2.txt")
        file3 = File("file3.txt")

        assertEquals(File("output.txt").toString(), ans2)

        file1.delete()
        file2.delete()
        file3.delete()
    }

}