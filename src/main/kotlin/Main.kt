import java.util.Scanner


const val SEPARATOR = "-----------------------------------------------------------------------------------------------"

fun main () {
    // меню Архивы

    val newArchive  = Archives()
    val newNote = Notes()
    val newTextFill = NoteFill()
    var archiveCycle= true//переменная для работы в цикле Archive
    var noteCycle = false//переменная для работы в цикле Note
    var noteFillCycle = false//переменная для работы в цикле noteFill
    var currentArchiveNumber = 0




    while (archiveCycle) {
        println("ARCHIVE MENU.")
        println("To select the archive saved , press the number:")
        if (listOfArchive.size == 0) {
            println("Archive's list is empty.")
        } else {
            listOfArchive.forEachIndexed { index, element -> println("'${index + 1}.' $element") }
        }
        printCommand("archive")


        val keyInPut:String  = Scanner(System.`in`).nextLine()
        //проверяем, что не введена пустая строка или пробел
        if (!isNumeric(keyInPut)||keyInPut.isNullOrBlank()||keyInPut.toInt()==0) {

            when (keyInPut) {

                'n'.toString() -> {
                    newArchive.createOfArchive()//создаем новый архив
                    currentArchiveNumber++
                }
                'q'.toString() -> {
                    println("Exit the program")
                    break
                }

                else -> println("Invalid input")
            }
        } else {
            //переход если выбрали сохраненный архив по номеру
            if (keyInPut.toInt() > listOfArchive.size) { //проверка, что номер есть в списке сохраненных архивов
                println("Invalid input")
            } else {
                archiveCycle = false
                noteCycle = true
                currentArchiveNumber=keyInPut.toInt()-1
                //перед выходом из цикла Archive записываем имя текущего архива
                //и перезаписываем архив заметок из текущего архива в listOfNotes
                //с которым будем работать в цикле Note
                currentArchiveName=listOfArchive.elementAt(keyInPut.toInt()-1)
                archiveAndNotes[currentArchiveName]?.forEach { listOfNotes.add(it) }

            }

        }

        //конец цикла while Archive и переход в меню Note
        // начало цикла Note
        while (noteCycle) {

            println("NOTE MENU.")
            println("To select the note saved , press the number:")
            println("archive name: ${currentArchiveName}")

            if (listOfNotes.size == 0) {
                println("Note's list is empty.")
            } else {

                listOfNotes.forEachIndexed { index, element -> println("'${index + 1}.' $element") }
            }
            printCommand("note")
            val j = Scanner(System.`in`).nextLine()
            //проверяем, что не введена пустая строка или пробел
            if (!isNumeric(j) || j.isNullOrBlank()||j.toInt()==0) {

                when (j) {
                    'n'.toString() -> { //создаем новую заметку
                        newNote.createNewNote()
                    }
                    'q'.toString() -> {
                        //перезаписываем содержимое архива listOfNotes с новыми заметками
                        // в архив archiveAndNotes и обнуляем содержимое listOfNotes
                        fillArchive(currentArchiveNumber,listOfNotes)
                        listOfNotes.clear()
                        numberOfNotes=0
                        //выходим в Archive menu
                        archiveCycle = true
                        noteCycle = false
                        noteFillCycle =false

                    }

                    else -> {
                        println("Invalid input")
                    }
                }
            } else {
                if (j.toInt() > listOfNotes.size) { //проверка правильности номера сохраненной заметки Note
                    println("Invalid input")
                } else {

                    noteCycle = false
                    noteFillCycle =true

                    //Переходим в выбранную Заметку Note ${listOfNotes[j.toInt() - 1]} и в цикл  ввода текста

                    //начало меню  Fill Note
                    while (noteFillCycle){

                        val noteTextKey:String = "${currentArchiveName}${j.toInt()-1}"
                        println("VIEW NOTE's text.")
                        //Печатаем на экране сохраненный текст заметки:
                        println(SEPARATOR)
                        println("Archive: ${currentArchiveName}\t Note: ${listOfNotes[j.toInt() - 1]}")
                        if (notesText[noteTextKey].isNullOrEmpty()) notesText.put(noteTextKey,"")
                        else {
                            println("${notesText[noteTextKey]}")
                        }
                        println(SEPARATOR)
                        printCommand("text")

                        val keyInPut:String  = Scanner(System.`in`).nextLine()
                        when (keyInPut) {
                            'n'.toString() -> { //редактируем или добавляем новый текст в заметке
                                //вызов метода ввода текста в консоль текст ниже вынести в отдельный метод
                                /*
                                 println("Enter the text in the line below: ")
                                 var newNoteText: String? = Scanner(System.`in`).nextLine()
                                 if (newNoteText.isNullOrEmpty()) newNoteText=""
                                 val savedNoteText: String? = notesText[noteTextKey]
                                 notesText.put(noteTextKey, "${savedNoteText}\n${newNoteText}")
                                 */
                                newTextFill.noteFill(noteTextKey)
                            }
                            'q'.toString() -> {
                                archiveCycle = false
                                noteCycle = true
                                noteFillCycle =false


                            }

                            else -> {
                                println("Invalid input")
                            }
                        }

                        //Возвращаемся в меню NOTES
                    }
                    noteFillCycle =false
                    noteCycle = true
                }

            }

        }//окончание цикла Note


    }////окончание цикла Archive

}




//если строка  - последовательность цифр: true, иначе false
fun isNumeric(s: String): Boolean {
    return s.chars().allMatch { Character.isDigit(it) }
}

fun fillArchive (x:Int,y:MutableList<String>) {
    archiveAndNotes.put(listOfArchive.elementAt(x), y.toMutableList() )
}

fun printCommand(name: String) {

    println("\nTo command press key:")
    println("'n' Create new $name")
    println("'q' Exit")
}


