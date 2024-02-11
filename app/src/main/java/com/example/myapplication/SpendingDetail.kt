import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.data.Category

class SpendingDetail : Fragment() {

    private lateinit var dateTextView: TextView
    private lateinit var categoryTextView: TextView
    private var selectedCategory: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spending_detail, container, false)

        // Retrieve selected date from arguments
        val selectedDate = arguments?.getString(ARG_SELECTED_DATE)

        // Initialize views
        dateTextView = view.findViewById(R.id.dateTextView)
        categoryTextView = view.findViewById(R.id.categoryTextView)

        // Set selected date to dateTextView
        dateTextView.text = selectedDate

        // Set onClickListener for categoryTextView
        categoryTextView.setOnClickListener {
            showCategoryDialog()
        }

        return view
    }

    private fun showCategoryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.category_dialog_layout, null)

        val categoryListView = view.findViewById<ListView>(R.id.categoryListView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, Category.values())
        categoryListView.adapter = adapter

        val dialog = builder.setView(view)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.create()

        categoryListView.setOnItemClickListener { parent, _, position, _ ->
            val selectedCategory = parent.getItemAtPosition(position) as Category
            categoryTextView.text = selectedCategory.toString()
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"

        @JvmStatic
        fun newInstance(selectedDate: String) =
            SpendingDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDate)
                }
            }
    }
}
