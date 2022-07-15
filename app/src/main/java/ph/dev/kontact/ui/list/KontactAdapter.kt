package ph.dev.kontact.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.transform.CircleCropTransformation
import ph.dev.kontact.common.BoundHolder
import ph.dev.kontact.common.viewBinding
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.databinding.KontactItemListBinding

class KontactAdapter(
    private val itemClickListener: (KontactDetail) -> Unit
) : ListAdapter<KontactDetail, KontactAdapter.ViewHolder>(KontactListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(
        parent: ViewGroup,
        private val itemClickListener: (KontactDetail) -> Unit
    ) : BoundHolder<KontactItemListBinding>(parent.viewBinding(KontactItemListBinding::inflate)) {

        fun bind(kontactDetail: KontactDetail) {
            itemView.setOnClickListener { itemClickListener(kontactDetail) }

            binding.profileImage.load(kontactDetail.profileImage) {
                transformations(CircleCropTransformation())
            }
            binding.contactNumberTextView.text = kontactDetail.contactNumber
            binding.displayNameTextView.text = kontactDetail.name
        }
    }

    private class KontactListDiff : DiffUtil.ItemCallback<KontactDetail>() {
        override fun areItemsTheSame(oldItem: KontactDetail, newItem: KontactDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: KontactDetail, newItem: KontactDetail): Boolean {
            return oldItem == newItem
        }
    }
}
