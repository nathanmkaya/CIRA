package dita.dev.cira.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import dita.dev.cira.R
import dita.dev.cira.model.DataManager
import dita.dev.cira.model.Issue
import kotlinx.android.synthetic.main.fragment_add_issue_details.*
import org.jetbrains.anko.design.snackbar

class AddIssueDetailsFragment : Fragment(), BlockingStep {

    lateinit var dataManager: DataManager

    companion object {
        fun newInstance(): AddIssueDetailsFragment {
            return AddIssueDetailsFragment()
        }
    }

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
        activity?.finish()
    }

    override fun onSelected() {
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        callback?.complete()
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        var issue = Issue()
        issue.description = descriptionTxt.text.toString()
        issue.title = titleTxt.text.toString()
        issue.witnesses = witnessTxt.text.toString().toInt()
        issue.location = locationTxt.text.toString()

        dataManager.saveData(issue)
        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {
        if (TextUtils.isEmpty(titleTxt.text.toString()) || TextUtils.isEmpty(descriptionTxt.text.toString()) || TextUtils.isEmpty(
                witnessTxt.text.toString()
            ) || TextUtils.isEmpty(locationTxt.text.toString())
        ) {
            return VerificationError("Fill All Fields")
        }
        return null
    }

    override fun onError(error: VerificationError) {
        view?.snackbar(error.errorMessage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_issue_details, container, false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DataManager) {
            dataManager = context
        } else {
            throw IllegalStateException("Activity must implement DataManager interface!")
        }
    }
}
