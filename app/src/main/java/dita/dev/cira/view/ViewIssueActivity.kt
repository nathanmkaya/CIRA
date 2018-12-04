package dita.dev.cira.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.app.NavUtils
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.firestore.FirebaseFirestore
import dita.dev.cira.R
import dita.dev.cira.model.Issue
import kotlinx.android.synthetic.main.activity_view_issue.*
import kotlinx.android.synthetic.main.confirmation.*

class ViewIssueActivity : AppCompatActivity() {

    val issue: Issue by lazy {
        intent.getParcelableExtra("issue") as Issue
    }

    val ref: String by lazy {
        intent.getStringExtra("doc_id")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.issue_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                NavUtils.navigateUpFromSameTask(this)
                return true
            }

            R.id.issue_confirm -> {
                var witness = issue.witness_accepted + 1
                FirebaseFirestore.getInstance().collection("issues").document(ref).update("witness_accepted", witness)
                true
            }
            R.id.issue_fixed -> {
                FirebaseFirestore.getInstance().collection("issues").document(ref).update("fixed", true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_issue)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isVisible = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.getTotalScrollRange()!!;
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.title = issue.title;
                    isVisible = true;
                } else if(isVisible) {
                    toolbar.title = "";
                    isVisible = false;
                }
            }
        })

        titleTxt.text = issue.title
        descriptionTxt.text = issue.description
        locationTxt.text = issue.location
        witnessTxt.text = "${issue.witness_accepted} Accepted of ${issue.witnesses}"
        Glide.with(this).load(issue.download_url).into(imgPreview)
    }
}
