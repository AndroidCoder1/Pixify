package liz.agyei.pixify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import liz.agyei.pixify.data.api.FlickrAPI
import liz.agyei.pixify.databinding.ActivityMainBinding
import liz.agyei.pixify.preference.MyPreferenceActivity
import liz.agyei.pixify.utils.Utils
import liz.agyei.pixify.viewmodel.MainActivityViewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = MainActivityViewModel(FlickrAPI(), application)

        binding.model = viewModel
        binding.lifecycleOwner = this

        RxTextView.textChanges(binding.search)
            .debounce(600, TimeUnit.MILLISECONDS)
            .subscribe { query ->
                if (query.trim().isNotEmpty()){
                    this?.let { Utils.getPerPageLimit(it) }?.let {
                        viewModel._isProgressShowing.postValue(true)
                        viewModel.getAllPhotos(query.toString(), 1, it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                Consumer {
                                    viewModel._isProgressShowing.postValue(false)
                                    if (it.isNotEmpty()) {
                                        binding.recyclerView.adapter = PhotoRecyclerViewAdapter(it, this)
                                        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
                                    }
                                })
                    }
                }
            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this@MainActivity, MyPreferenceActivity::class.java))
                return true
            }
            R.id.action_bookmark -> {
                startActivity(Intent(this@MainActivity, BookmarksActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
