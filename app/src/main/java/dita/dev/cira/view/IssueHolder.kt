package dita.dev.cira.view

import android.graphics.Color
import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import dita.dev.cira.model.Issue
import kotlinx.android.synthetic.main.issue.view.*
import kotlinx.android.synthetic.main.issue_mock.view.*
import java.util.Date

class IssueHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(issue: Issue) {
        with(issue) {
            itemView.titleText.text = issue.title
            itemView.descText.text = issue.description
            Glide.with(itemView).load(issue.download_url).into(itemView.img)
            if (!issue.fixed) {
                itemView.issue_holder.setCardBackgroundColor(Color.parseColor("#FF6347"))
            } else {
                itemView.issue_holder.setCardBackgroundColor(Color.parseColor("#ADFF2F"))
            }
            itemView.timestampTxt.text = TimeAgo.using(issue.timeCreated?.toDate()?.time!!)
            itemView.locationTxt.text = "@ ${issue.location}"
        }
    }
}