import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.Category
import com.example.myapplication.viewmodels.DetailViewModel
import java.text.SimpleDateFormat

class SpendingDetail : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var categoryTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spending_detail, container, false)

        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        val spending = detailViewModel.selectedSpending.value
        if (spending != null) {
            val dateTextView: TextView = view.findViewById(R.id.dateTextView)
            dateTextView.text = SimpleDateFormat("dd.MM.yyyy").format(spending.created)

            val expenseEditText: EditText = view.findViewById(R.id.expenseEditText)
            expenseEditText.setText(spending.value.toString())

            val noteEditText: EditText = view.findViewById(R.id.noteEditText)
            noteEditText.setText(spending.note)
        }

        categoryTextView = view.findViewById(R.id.categoryTextView)
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
}
