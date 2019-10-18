package pl.patryk.springer.safetynetsample.feature.main

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.patryk.springer.safetynetsample.R
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get<MainActivityViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        attestResultTV.movementMethod = ScrollingMovementMethod()
        verificationResultTV.movementMethod = ScrollingMovementMethod()

        assessDeviceBtn.setOnClickListener { viewModel.assessDevice() }
        verifyResultBtn.setOnClickListener { viewModel.verifyResult(onlineVerificationChb.isChecked) }

        viewModel.attestationResult.observe(this, Observer {
            attestResultTV.text = it.result
        })

        viewModel.verificationResult.observe(this, Observer {
            verificationResultTV.text = it.toString()
        })
    }

}
