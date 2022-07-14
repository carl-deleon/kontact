package ph.dev.kontact.ui

import androidx.activity.viewModels
import ph.dev.kontact.R
import ph.dev.kontact.common.BaseActivity
import ph.dev.kontact.common.BaseViewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    override val viewModel: BaseViewModel by viewModels()
}