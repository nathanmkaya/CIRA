package dita.dev.cira.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dita.dev.cira.R
import dita.dev.cira.model.Issue
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity() {

    lateinit var adapter: FirestoreRecyclerAdapter<Issue, IssueHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        no_issues.visibility = View.GONE

        addIssue.setOnClickListener {
            startActivity<AddIssueActivity>()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        layoutManager.scrollToPosition(0)

        issueList.layoutManager = layoutManager

        val query = FirebaseFirestore.getInstance()
            .collection("issues")
            .orderBy("timeCreated", Query.Direction.DESCENDING)


        val options = FirestoreRecyclerOptions.Builder<Issue>()
            .setQuery(query, Issue::class.java)
            .setLifecycleOwner(this)
            .build()

        adapter = object: FirestoreRecyclerAdapter<Issue, IssueHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueHolder {
                val view = layoutInflater.inflate(R.layout.issue, parent, false)

                return IssueHolder(view)
            }

            override fun onBindViewHolder(holder: IssueHolder, position: Int, issue: Issue) {
                holder.bind(issue)
                holder.itemView.setOnClickListener {
                    it.snackbar("Test single click")
                    val snapshot = snapshots.getSnapshot(position)
                    startActivity<ViewIssueActivity>(
                        "issue" to issue,
                        "doc_id" to snapshot.id
                    )
                }

                holder.itemView.setOnLongClickListener {
                    it.snackbar("Test long click")
                    true
                }
            }
        }

        /*if (adapter.itemCount.equals(0)) {
            no_issues.visibility = View.VISIBLE
        }*/

        issueList.adapter = adapter
    }
}
