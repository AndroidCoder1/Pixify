package liz.agyei.pixify

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import liz.agyei.pixify.databinding.ActivityBookmarksBinding
import liz.agyei.pixify.preference.MyPreferenceActivity
import liz.agyei.pixify.viewmodel.BookmarksViewModel
import java.util.*
import java.util.concurrent.TimeUnit

class BookmarksActivity : AppCompatActivity() {

    lateinit var binding: ActivityBookmarksBinding
    lateinit var viewModel: BookmarksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bookmarks)

        viewModel = BookmarksViewModel(application)

        viewModel.bookmarkedPhotos
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                        if (it.isNotEmpty()) {
                            binding.recyclerView.adapter = PhotoRecyclerViewAdapter(it, this@BookmarksActivity)
                            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
                        }
                }
            )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
