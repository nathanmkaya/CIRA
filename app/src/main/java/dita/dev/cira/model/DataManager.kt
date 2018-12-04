package dita.dev.cira.model

interface DataManager {
    fun saveData(issue: Issue?)

    fun getData(): Issue?
}