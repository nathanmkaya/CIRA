package dita.dev.cira.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dita.dev.cira.model.Issue

class AddIssueViewModel: ViewModel() {
    val issue = MutableLiveData<Issue>()
}