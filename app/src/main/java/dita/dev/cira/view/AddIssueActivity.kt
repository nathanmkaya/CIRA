package dita.dev.cira.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import dita.dev.cira.R
import dita.dev.cira.model.DataManager
import dita.dev.cira.model.Issue
import dita.dev.cira.view.adapter.AddIssueAdapter
import kotlinx.android.synthetic.main.activity_add_issue.*

class AddIssueActivity : AppCompatActivity(), StepperLayout.StepperListener, DataManager {

    private var issue: Issue? = null
    var currentStepPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_issue)
        val adapter = AddIssueAdapter(supportFragmentManager, this)
        addIssueStepper.adapter = adapter
        issue = savedInstanceState?.getParcelable<Issue>("issue")
        currentStepPosition = savedInstanceState?.getInt("position", 0) ?: 0

    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putParcelable("issue", issue)
        outState?.putInt("position", currentStepPosition)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onBackPressed() {
        if (currentStepPosition > 0) {
            addIssueStepper.onBackClicked()
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun saveData(issue: Issue?) {
        this.issue = issue
    }

    override fun getData(): Issue? {
        return issue
    }

    override fun onStepSelected(newStepPosition: Int) {
        currentStepPosition = newStepPosition
    }

    override fun onError(verificationError: VerificationError?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReturn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompleted(completeButton: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
