package dita.dev.cira.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import dita.dev.cira.R
import dita.dev.cira.model.DataManager
import dita.dev.cira.model.Issue
import dita.dev.cira.repository.IssueRepository
import kotlinx.android.synthetic.main.fragment_add_issue_confirm.*

class AddIssueConfirmFragment : Fragment(), BlockingStep {

    lateinit var dataManager: DataManager
    var issue: Issue? = null

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onSelected() {
        issue = dataManager.getData()
        issue?.let{
            titleTxt.text = it.title
            descriptionTxt.text = it.description
            locationTxt.text = it.location
            witnessTxt.text = it.witnesses.toString()
            Glide.with(this@AddIssueConfirmFragment).load(it.img).into(imgPreview)
        }

    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        callback?.stepperLayout?.showProgress("Uploading")
        IssueRepository.addIssue(issue, object: IssueRepository.Callback {
            override fun onError() {
                onError(VerificationError("Upload failed"))
            }

            override fun onComplete() {
                callback?.stepperLayout?.hideProgress()
                callback?.complete()
                activity?.finish()
            }
        })
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_issue_confirm, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DataManager) {
            dataManager = context
        } else {
            throw IllegalStateException("Activity must implement DataManager interface!") as Throwable
        }
    }
}
