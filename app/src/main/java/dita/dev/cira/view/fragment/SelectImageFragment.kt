package dita.dev.cira.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import dita.dev.cira.R
import dita.dev.cira.model.DataManager
import dita.dev.cira.model.Issue
import kotlinx.android.synthetic.main.fragment_select_image.*
import kotlinx.android.synthetic.main.fragment_select_image.view.*
import org.jetbrains.anko.design.snackbar

class SelectImageFragment : Fragment(), BlockingStep {

    lateinit var dataManager: DataManager
    var issue: Issue? = null
    lateinit var images: ArrayList<Image>

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onSelected() {
        issue = dataManager.getData()
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        callback?.complete()
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        issue?.img = images.get(0).path
        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {
        if (images.isEmpty()) {
            return VerificationError("Pick an Image")
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
        val view = inflater.inflate(R.layout.fragment_select_image, container, false)

        view.imgSelectBtn.setOnClickListener { _ ->
            ImagePicker
                .with(this)
                .setRequestCode(100)
                .start()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null){
            images = data.getParcelableArrayListExtra<Image>(Config.EXTRA_IMAGES)
            Glide.with(this).load(images[0].path).into(imgPreview)
        }
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
