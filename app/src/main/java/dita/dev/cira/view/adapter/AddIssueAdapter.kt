package dita.dev.cira.view.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import dita.dev.cira.view.fragment.AddIssueConfirmFragment
import dita.dev.cira.view.fragment.AddIssueDetailsFragment
import dita.dev.cira.view.fragment.SelectImageFragment

class AddIssueAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {
    override fun getCount(): Int {
        return 3
    }

    override fun createStep(position: Int): Step {
        when (position) {
            0 -> return AddIssueDetailsFragment.newInstance()
            1 -> return SelectImageFragment()
            2 -> return AddIssueConfirmFragment()
            else -> throw IllegalArgumentException("Unsupported position: $position")
        }
    }

    override fun getViewModel(position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context)
        when (position) {
            0 -> builder
                .setTitle("Add details")
                .setBackButtonLabel("Cancel")
                .setEndButtonLabel("Images")
            1 -> builder
                .setTitle("Add Images")
                .setEndButtonLabel("Confirm")
                .setBackButtonLabel("Details")
            2 -> builder.setTitle("Confirm details").setBackButtonLabel("Images").setEndButtonLabel("Done")
        }

        return builder.create()
    }
}