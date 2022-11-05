import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.CategoryUtility
import utils.StatusUtility
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput.readValidCategory
import utils.ValidateInput.readValidPriority
import utils.ValidateInput.readValidStatus
import java.io.File
import java.lang.System.exit
//The following are the imports for the colors used they were acquired from https://github.com/ajalt/mordant
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyles.*
import com.github.ajalt.mordant.rendering.TextColors.Companion.rgb


private val logger = KotlinLogging.logger {}
// private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))

fun main(args: Array<String>) {
    runMenu()
}

val style = (bold + rgb("#FF5733") ) //This creates a preset of a text style that can be used throughout

fun mainMenu(): Int {
    return ScannerInput.readNextInt(

        (style(""" 
         > -------------------------------------
         > |          NOTE KEEPER APP          |
         > -------------------------------------
         > | NOTE MENU                         |
         > |   0) Info on the App              |
         > |   1) Add a note                   |
         > |   2) List notes                   |
         > |   3) Update a note                |
         > |   4) Delete a note                |
         > |   5) Archive a note               |
         > |   6) Search note(by description)  |
         > -------------------------------------
         > |   20) Save notes                  |
         > |   21) Load notes                  |
         > -------------------------------------
         > |   100) Exit :(                    |
         > -------------------------------------
         > ==>> """.trimMargin(">")
    )))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            0 -> info()
            1 -> addNote()
            2 -> listNotes()
            3 -> updateNote()
            4 -> deleteNote()
            5 -> archiveNote()
            6 -> searchNotes()
            20 -> save()
            21 -> load()
            100 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun info() {


    println("---------------------------------------------------------------------------------------------")
    println("            _____ _          _  _     _              _                                       ")
    println("           |_   _| |_  ___  | \\| |___| |_ ___ ___   /_\\  _ __ _ __                         ")
    println("             | | | ' \\/ -_) | .` / _ |  _/ -_(_-<  / _ \\| '_ | '_ \\                       ")
    println("             |_| |_||_\\___| |_|\\_\\___/\\__\\___/__/ /_/ \\_| .__| .__/                    ")
    println("                                                        |_|  |_|                             ")
    println("---------------------------------------------------------------------------------------------")

    println("                         -  Welcome to the Notes App! -                                      ")
    println("           This app helps users to keep track of all their tasks and much more!              ")
    println("           A Host of many features, this page will give a brief run through of them          ")
    println("                   Add - This allows users to add notes                                      ")
    println("                   Delete - This enables users to get rid of unwanted notes                  ")
    println("                   List - This allows users to list their notes through various methods      ")
    println("                   Update - This enables users to update the content of their notes          ")
    println("                   Archive - This allows users to archive notes                              ")
    println("                   Search - This allows users to search for notes they created               ")
    println("---------------------------------------------------------------------------------------------")
    println("       Please be aware that the categories for add are: Home, Life, College, Work & Other    ")
    println("---------------------------------------------------------------------------------------------")

}
fun addNote() {
    // logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readValidPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readValidCategory("Enter a category for the note from ${CategoryUtility.categories}: ")
    val noteStatus = readValidStatus("Enter a status for the note from ${StatusUtility.statuses}: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, noteStatus,false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listNotes() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            (style("""
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > |   4) View by PRIORITY        |
                  > |   5) View by STATUS          |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )))

        when (option) {
            1 -> listAllNotes()
            2 -> listActiveNotes()
            3 -> listArchivedNotes()
            4 -> listPrior()
            5 -> listStatus()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}


// secondary menu for the priorities, the style is used once again
fun listPrior() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            (style("""
                  > ------------------------------------------------------
                  > |   Please enter a priority number (1 to 5)          |
                  > |   1) - Priority Level 1                            |
                  > |   2) - Priority Level 2                            |
                  > |   3) - Priority Level 3                            |
                  > |   4) - Priority Level 4                            |
                  > |   5) - Priority Level 5                            |
                  > ------------------------------------------------------
         > ==>> """.trimMargin(">")
            )))

        // Prints the notes of the priority by the number e.g the first only prints notes of prio. 1
        when (option) {
            1 -> println(noteAPI.listNotesBySelectedPriority(1))
            2 -> println(noteAPI.listNotesBySelectedPriority(2))
            3 -> println(noteAPI.listNotesBySelectedPriority(3))
            4 -> println(noteAPI.listNotesBySelectedPriority(4))
            5 -> println(noteAPI.listNotesBySelectedPriority(5))
            // 1 -> println(noteAPI.listNotesBySelectedStatus("Work"))
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listStatus() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            (style("""
                  > ------------------------------------------------------
                  > |   Please choose a status                           |
                  > |   1) - Todo                                        |
                  > |   2) - Doing                                       |
                  > |   3) - Done                                        |
                  > ------------------------------------------------------
         > ==>> """.trimMargin(">")
            )))

        // Prints the notes based off their status
        when (option) {

            1 -> println(noteAPI.listNotesBySelectedStatus("Todo"))
            2 -> println(noteAPI.listNotesBySelectedStatus("Doing"))
            3 -> println(noteAPI.listNotesBySelectedStatus("Done"))


            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllNotes() {
    println(noteAPI.listAllNotes())
}

fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

//fun listCategories() {
// println(noteAPI.listCategories())
// }

fun updateNote() {
    // logger.info { "updateNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        // only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readValidPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readValidCategory("Enter a category for the note from ${CategoryUtility.categories}: ")
            val noteStatus = readValidStatus("Enter a status for the note from ${StatusUtility.statuses}: ")
            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, noteStatus, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteNote() {
    // logger.info { "deleteNote() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        // only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

fun searchNotes() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = noteAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}
fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    logger.info { "exitApp() function invoked" }
    exit(0)
}